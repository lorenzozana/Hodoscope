/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clas.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import org.jlab.clas.detector.DetectorCollection;
import org.jlab.clas.detector.DetectorDescriptor;
import org.jlab.clas.detector.DetectorType;
import org.jlab.clas12.basic.IDetectorModule;
import org.jlab.clas12.basic.IDetectorProcessor;
import org.jlab.clas12.calib.DetectorShape2D;
import org.jlab.clas12.calib.DetectorShapeTabView;
import org.jlab.clas12.calib.DetectorShapeView2D;
import org.jlab.clas12.calib.IDetectorListener;
import org.jlab.clas12.detector.DetectorChannel;
import org.jlab.clas12.detector.DetectorCounter;
import org.jlab.clas12.detector.EventDecoder;
import org.jlab.clas12.detector.FADCBasicFitter;
import org.jlab.clas12.detector.IFADCFitter;
import org.jlab.clasrec.main.DetectorEventProcessorPane;
import org.jlab.data.io.DataEvent;
import org.jlab.evio.clas12.EvioDataEvent;
import org.root.attr.ColorPalette;
import org.root.attr.TStyle;
import org.root.func.F1D;
import org.root.histogram.GraphErrors;
import org.root.histogram.H1D;
import org.root.pad.EmbeddedCanvas;

/**
 *
 * @author gavalian
 */
public class FTHODOViewerModule implements IDetectorProcessor, IDetectorListener, IDetectorModule, ActionListener {
    
    DetectorCollection<H1D> H_fADC = new DetectorCollection<H1D>();
    DetectorCollection<H1D> H_WAVE = new DetectorCollection<H1D>();
    DetectorCollection<H1D> H_NOISE = new DetectorCollection<H1D>();
    DetectorCollection<H1D> H_COSMIC_fADC   = new DetectorCollection<H1D>();
    DetectorCollection<H1D> H_COSMIC_CHARGE = new DetectorCollection<H1D>();
    DetectorCollection<F1D> mylandau = new DetectorCollection<F1D>();
    H1D H_fADC_N = null;
    H1D H_WMAX   = null;
    H1D H_COSMIC_N = null;
    
    double[] crystalID;
    double[] noiseRMS;
    double[] cosmicCharge;
    
    int threshold = 25; // 10 fADC value <-> ~ 5mV
    int ped_i1 = 4;
    int ped_i2 = 24;
    int pul_i1 = 30;
    int pul_i2 = 70;
    double LSB = 0.4884;
    int[] cry_event = new int[484];
    int[] cry_max = new int[484];
    int[] cry_n = new int[22];
    
    double crystal_size = 15;
    
    DetectorEventProcessorPane evPane = new DetectorEventProcessorPane();
    DetectorShapeTabView view = new DetectorShapeTabView();
    EmbeddedCanvas canvas = new EmbeddedCanvas();
    EventDecoder decoder = new EventDecoder();
    int nProcessed = 0;
    
        // ColorPalette class defines colors
    ColorPalette palette = new ColorPalette();
    
    DetectorCollection<Integer> dcHits = new DetectorCollection<Integer>();
    JPanel detectorPanel = null;
    private int plotSelect = 0;  // 0 - waveforms, 1 - noise
    private int keySelect = 0;
    private int secSelect = 0;
    private int layerSelect = 0;
    
    public FTHODOViewerModule() {
        
        this.initDetector();                  //Initialise detector setup
        this.initHistograms();                //Initialise histograms
        this.initRawDataDecoder();
        JSplitPane splitPane = new JSplitPane(); //Split Canvas
        splitPane.setLeftComponent(this.view);   //Set left component of canvas to viewer
        
        JPanel canvasPane = new JPanel();
        
        canvasPane.setLayout(new BorderLayout());
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        buttonPane.add(resetBtn);
        
        
        
        JRadioButton wavesRb  = new JRadioButton("Waveforms");
        
        ButtonGroup group = new ButtonGroup();
        group.add(wavesRb);
        
        buttonPane.add(wavesRb);
        
        wavesRb.setSelected(true);
        wavesRb.addActionListener(this);
        
        
        canvasPane.add(this.canvas, BorderLayout.CENTER);
        canvasPane.add(buttonPane, BorderLayout.PAGE_END);
        splitPane.setRightComponent(canvasPane);     //Set right component of canvas to canvaspane for histograms
        
        this.detectorPanel = new JPanel();
        this.detectorPanel.setLayout(new BorderLayout());
        this.evPane.addProcessor(this);
        this.detectorPanel.add(splitPane, BorderLayout.CENTER);
        this.detectorPanel.add(this.evPane, BorderLayout.PAGE_END);
        
        TStyle.setAxisFont("Helevetica", 16);
        TStyle.setStatBoxFont("Courier", 12);
    }
    
    
    private void initDetector() {      //Initialising detector
        
        DetectorShapeView2D viewFTHODO = new DetectorShapeView2D("FTHODO");
        int sec_a;      //element sector 1-8 for each layer. The detector symmetry allows us to have 4 sectors with elements 0-28 for each layer.
        int crys_a;     //crystal element 1-9 for odd sectors 1-20 for even
        double[] p_layer = {180.0,-180.0};   //y-offset to place thin and thick layer on same pane
        double[] v_layer = {-1.0,1.0};       //reflecting elemnts in second layer so that detector view is downstream (looking at +z axis) --
        double[] p_size = {15.0,30.0,15.0,30.0,30.0,30.0,30.0,30.0,15.0,
            30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,
            15.0,15.0,15.0,15.0,15.0,15.0,15.0,15.0}; //size of elements of symmetry sector 0-28
        
        double[] p_R	= {16.2331,15.6642,16.2331,15.0076,13.0933,15.6642,13.0933,10.8102,7.5590,
            15.9022,15.3132,15.3132,15.9022,13.0258,12.2998,12.2998,13.0258,10.2395,9.2984,9.2984,
            10.2395,8.7322,7.8847,7.2650,6.9344,6.9344,7.2650,7.8847,8.7322}; //distance from center of each element in  symmetry sector 0-28
        
        double[] p_theta = {-0.65294,-0.50511,-0.91786,-0.78540,-0.61741,-1.06568,-0.95339,-0.78540,-0.78540,
            -0.29005,-0.09916,0.09916,0.29005,-0.35667,-0.12357,0.12357,0.35667,-0.46024,-0.16377,0.16377,
            0.46024,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118}; //theta angle in rad of each element in  symmetry sector 0-28 measure from vertical axis pointing up -- Positive y points down and positive x to the left
        
        for (int layer_c=0; layer_c<2; layer_c++){   //two layers: c==0 for thick and c==1 for thin
            for (int sec_c=0; sec_c<4; sec_c++) {    //4 symmetry sectors per layer (named sec_c) from 0-3
                for (int component = 0; component < 29; component++) {  //29 components per symmetry sector
                    if (component<9) {
                        sec_a = sec_c*2 +1;           //element sector is odd for first 9 elements and event for the rest
                        crys_a = component + 1;       //crystal number for odd sector is 1-9
                    }
                    else {
                        sec_a = sec_c*2 +2;          //element sector is odd for first 9 elements and event for the rest
                        crys_a = component + 1 -9;   //crystal number for even sector is 1-20
                    }
                    double xcenter = v_layer[layer_c] * p_R[component] * Math.sin(p_theta[component]+Math.PI /2 *sec_c)*10;  //calculate the x-component of the center of each crystal; Draw thick first (placed on the bottom)
                    double ycenter = -p_R[component] * Math.cos(p_theta[component]+Math.PI /2 *sec_c)*10 +p_layer[layer_c];  //calculate the y-component of the center of each crystal
                    DetectorShape2D shape = new DetectorShape2D(DetectorType.FTCAL, sec_a, layer_c+1,crys_a); //Sectors 1-8 (sect=1: upper left for thin clockwise; upper right for thick anticlockwise); layers 1-2 (thick==1, thin==2); chrystals (1-9 for odd and 1-20 for even sectors)
                    shape.createBarXY(p_size[component], p_size[component]);  //defines the 2D bars dimensions using the component size
                    shape.getShapePath().translateXYZ(xcenter, ycenter, 0.0); //defines the placements of the 2D bar according to the xcenter and ycenter calculated above
                    shape.setColor(0, 145, 0);  //sets the color
                    viewFTHODO.addShape(shape); //adds the shape to the viewer
                    
                }
            }
        }
        this.view.addDetectorLayer(viewFTHODO);
        view.addDetectorListener(this);
    }
    
    private void initRawDataDecoder() {
        decoder.addFitter(DetectorType.FTCAL,
                          new FADCBasicFitter(ped_i1, // first bin for pedestal
                                              ped_i2, // last bin for pedestal
                                              pul_i1, // first bin for pulse integral
                                              pul_i2  // last bin for pulse integral
                                              ));
    }
    
    private void initHistograms() {         //initialises histograms for each detector element
        
        for (int component = 0; component < 232; component++) { //loop over all elements
            int ilayer = component/116 + 1;           //layer 1: Thick (lower diagram), layer 2: Thin (upper diagram)
            String layer_s;
            if (ilayer==1) layer_s = "Thick";
            else layer_s = "Thin";
            
            int iy = (component-(ilayer-1)*116) / 29;     //used for calculation of sector in each layer: first 29 is is sec 1 and 2, second set of 29 are in sectors 3 and 4...
            int ix = component - iy * 29 -(ilayer-1)*116; //used for calculation of crystal number: ix is between 0 and 28; Odd sectors have elements 1-9 and even sectors have elements 1-20
            int sec_a;
            int crys_a;
            if (ix<9) {
                sec_a = iy*2 +1;
                crys_a = ix + 1;
            }
            else {
                sec_a = iy*2 +2;
                crys_a = ix + 1 -9;
            }
            String title = "Crystal " + component + " (layer" + ilayer + "(" + layer_s +"), sector" + sec_a + ", crystal" + crys_a + ")"; //layer 1: Thick; layer 2: Thin; sectors 1-8 upper left clockwise for thin, and upper right anticlockwise for Thick
            
                //Definition of histograms. These are associated with sect, layer and crystal
            H_fADC.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("fADC", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
            
            H_NOISE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Noise", sec_a,ilayer,crys_a), title, 200, 0.0, 10.0));
            H_NOISE.get(sec_a,ilayer, crys_a).setFillColor(4);
            H_NOISE.get(sec_a,ilayer, crys_a).setXTitle("RMS (mV)");
            H_NOISE.get(sec_a,ilayer, crys_a).setYTitle("Counts");
            
            H_WAVE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("WAVE", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
            H_WAVE.get(sec_a,ilayer, crys_a).setFillColor(5);
            H_WAVE.get(sec_a,ilayer, crys_a).setXTitle("fADC Sample");
            H_WAVE.get(sec_a,ilayer, crys_a).setYTitle("fADC Counts");
            
            H_COSMIC_fADC.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Cosmic fADC", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
            H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setFillColor(3);
            H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setXTitle("fADC Sample");
            H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setYTitle("fADC Counts");
            
            H_COSMIC_CHARGE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Cosmic Charge", sec_a,ilayer,crys_a), title, 80, 0.0, 80.0));
            H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setFillColor(2);
            H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setXTitle("Charge (pC)");
            H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setYTitle("Counts");
            
            mylandau.add(sec_a,ilayer, crys_a, new F1D("landau",0.0, 80.0));
            
        }
        H_fADC_N   = new H1D("fADC", 484, 0, 484);  //shows all elements need to change the binning and #of bins)
        H_WMAX     = new H1D("WMAX", 484, 0, 484);
        H_COSMIC_N = new H1D("EVENT", 484, 0, 484);
        
        crystalID       = new double[332];
        noiseRMS        = new double[332];
    }
    
        //Resets histograms
    private void resetHistograms() {
        for (int component = 0; component < 232; component++) { //loop over all elements
            int ilayer = component/116 + 1;  //layer 1: Thick (lower diagram), layer 2: Thin (upper diagram)
            int iy = (component-(ilayer-1)*116) / 29;  //used for calculation of sector in each layer: first 29 is is sec 1 and 2, second set of 29 are in sectors 3 and 4...
            int ix = component - iy * 29 -(ilayer-1)*116; //used for calculation of crystal number: ix is between 0 and 28; Odd sectors have elements 1-9 and even sectors have elements 1-20
            int sec_a;
            int crys_a;
            if (ix<9) {
                sec_a = iy*2 +1;
                crys_a = ix + 1;
            }
            else {
                sec_a = iy*2 +2;
                crys_a = ix + 1 -9;
            }
            H_fADC.get(sec_a,ilayer, crys_a).reset();
            H_NOISE.get(sec_a,ilayer, crys_a).reset();
            H_fADC_N.reset();
            H_COSMIC_fADC.get(sec_a,ilayer, crys_a).reset();
            H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).reset();
            H_COSMIC_N.reset();
        }
    }
    
    
        //Process event - it uses the decoder to get information from the FTHODO table and correlate it with info from the EVIO file. Correct table should be located in $COATJAVA/etc/bankdefs/translation/
    public void processEvent(DataEvent de) {
        EvioDataEvent event = (EvioDataEvent) de;
        
        decoder.decode(event);
        nProcessed++;
            //    System.out.println("event #: " + nProcessed);
        List<DetectorCounter> counters = decoder.getDetectorCounters(DetectorType.FTCAL);
        FTHODOViewerModule.MyADCFitter fadcFitter = new FTHODOViewerModule.MyADCFitter();
        H_WMAX.reset();
        
        for (DetectorCounter counter : counters) {   //Loops over all detector elements
            int key = counter.getDescriptor().getComponent(); //gets crystal 1-9 for odd or 1-20 for even sectors that correspond to the right CRATE SLOT CHANNEL
            int sec_k = counter.getDescriptor().getSector();  //gets Sector  1-8 (for Thin sec=1 upper left clockwise, for Thick sec=1 upper right anticlockwise);
            int layer_k = counter.getDescriptor().getLayer(); //gets layer 1 thick; 2 thin
            int sector_count[] = {0,9,29,38,58,67,87,96};
            int adcN_k = (layer_k -1 ) *116+sector_count[sec_k-1]+key;
                //             System.out.println(counters.size() + " " + icounter + " " + counter.getDescriptor().getComponent());
                //             System.out.println(counter);
            
            fadcFitter.fit(counter.getChannels().get(0));
            short pulse[] = counter.getChannels().get(0).getPulse();
            H_fADC_N.fill(adcN_k);
            H_WAVE.get(sec_k, layer_k, key).reset();
            for (int i = 0; i < Math.min(pulse.length, H_fADC.get(sec_k, layer_k, key).getAxis().getNBins()); i++) {
                H_fADC.get(sec_k, layer_k, key).fill(i, pulse[i] - fadcFitter.getPedestal() + 10.0);
                H_WAVE.get(sec_k, layer_k, key).fill(i, pulse[i]);
            }
            H_WMAX.fill(adcN_k,fadcFitter.getWave_Max()-fadcFitter.getPedestal());
            if(fadcFitter.getWave_Max()-fadcFitter.getPedestal()>threshold)
                    //            System.out.println("   Component #" + key + " is above threshold, max=" + fadcFitter.getWave_Max() + " ped=" + fadcFitter.getPedestal());
                H_NOISE.get(sec_k, layer_k, key).fill(fadcFitter.getRMS());
        }
        
        if (plotSelect == 0 && H_WAVE.hasEntry(secSelect, layerSelect, keySelect)) {
            this.canvas.draw(H_WAVE.get(secSelect, layerSelect, keySelect));
        }
            //this.dcHits.show();
        this.view.repaint();
    }
    
    public void detectorSelected(DetectorDescriptor desc) {
        System.out.println("SELECTED = " + desc);
        keySelect = desc.getComponent();
        secSelect = desc.getSector();
        layerSelect = desc.getLayer();
        System.out.println("Sector=" + secSelect + " Layer=" +layerSelect + " Component=" + keySelect);
        if (plotSelect == 0) {
            this.canvas.divide(1, 1);
            canvas.cd(0);
            canvas.draw(H_WAVE.get(secSelect, layerSelect, keySelect));
        }
    }
    
    public void update(DetectorShape2D shape) {
        int sector = shape.getDescriptor().getSector();
        int layer = shape.getDescriptor().getLayer();
        int paddle = shape.getDescriptor().getComponent();
        int sector_count[] = {0,9,29,38,58,67,87,96};
        int adcN_k = (layer -1 ) *116+sector_count[sector-1]+paddle;
            //shape.setColor(200, 200, 200);
            //     System.out.println("Bin Content n" +adcN_k + "="+ H_WMAX.getBinContent(adcN_k));
        if(plotSelect==0) {
            if(H_WMAX.getBinContent(adcN_k)>threshold) {
                shape.setColor(200, 0, 200);
            }
            else {
                shape.setColor(100, 100, 100);
            }
        }
    }
    
    public String getName() {
        return "FTHODOEventViewerModule";
    }
    
    public String getAuthor() {
        return "Zana";
    }
    
    public DetectorType getType() {
        return DetectorType.FTCAL;
    }
    
    public String getDescription() {
        return "FTHODO Display";
    }
    
    public JPanel getDetectorPanel() {
        return this.detectorPanel;
    }
    
    public static void main(String[] args) {
        FTHODOViewerModule module = new FTHODOViewerModule();
        JFrame frame = new JFrame();
        frame.add(module.getDetectorPanel());
        frame.pack();
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        System.out.println("ACTION = " + e.getActionCommand());
        if (e.getActionCommand().compareTo("Reset") == 0) {
            resetHistograms();
        }
        if (e.getActionCommand().compareTo("Fit") == 0) {
            fitHistograms();
        }
        
        if (e.getActionCommand().compareTo("Waveforms") == 0) {
            plotSelect = 0;
            resetCanvas();
        }
    }
    
    private void resetCanvas() {
        this.canvas.divide(1, 1);
        canvas.cd(0);
    }
    
    private void fitHistograms() {
        for(int key=0; key< 22*22; key++) {
            if(H_COSMIC_CHARGE.hasEntry(0, 0, key)) {
                if(H_COSMIC_CHARGE.get(0, 0, key).getEntries()>200) {
                    H1D hcosmic = H_COSMIC_CHARGE.get(0,0,key);
                    initLandauFitPar(key,hcosmic);
                    hcosmic.fit(mylandau.get(0, 0, key));
                }
            }
        }
        boolean flag_parnames=true;
        for(int key=0; key< 22*22; key++) {
            if(mylandau.hasEntry(0, 0, key)) {
                if(flag_parnames) {
                    System.out.println("Component\t amp\t mean\t sigma\t p0\t p1\t Chi2");
                    flag_parnames=false;
                }
                System.out.print(key + "\t\t ");
                for(int i=0; i<mylandau.get(0, 0, key).getNParams(); i++) System.out.format("%.2f\t ",mylandau.get(0, 0, key).getParameter(i));
                if(mylandau.get(0, 0, key).getNParams()==3) System.out.print("0.0\t 0.0\t");
                if(mylandau.get(0, 0, key).getParameter(0)>0)
                    System.out.format("%.2f\n",mylandau.get(0, 0, key).getChiSquare(H_COSMIC_CHARGE.get(0,0,key).getDataSet())
                                      /mylandau.get(0, 0, key).getNDF(H_COSMIC_CHARGE.get(0,0,key).getDataSet()));
                else
                    System.out.format("0.0\n");
            }
        }
    }
    
    private void initLandauFitPar(int key, H1D hcosmic) {
        if(hcosmic.getBinContent(0)==0) mylandau.add(0, 0, key, new F1D("landau",     0.0, 80.0));
        else                            mylandau.add(0, 0, key, new F1D("landau+exp", 0.0, 80.0));
        if(hcosmic.getBinContent(0)<10) {
            mylandau.get(0, 0, key).setParameter(0, hcosmic.getBinContent(hcosmic.getMaximumBin()));
        }
        else {
            mylandau.get(0, 0, key).setParameter(0, 10);
        }
        mylandau.get(0, 0, key).setParameter(1,hcosmic.getMean());
        mylandau.get(0, 0, key).setParameter(2,5);
        if(hcosmic.getBinContent(0)!=0) {
            mylandau.get(0, 0, key).setParameter(3,hcosmic.getBinContent(0));
            mylandau.get(0, 0, key).setParameter(4, -0.2);
        }
    }
    
    public class MyADCFitter implements IFADCFitter {
        
        double rms = 0;
        double pedestal = 0;
        double wave_max=0;
        
        public double getPedestal() {
            return pedestal;
        }
        
        public double getRMS() {
            return rms;
        }
        
        public double getWave_Max() {
            return wave_max;
        }
        
        public void fit(DetectorChannel dc) {
            short[] pulse = dc.getPulse();
            double ped = 0.0;
            double noise = 0;
            double wmax=0;
            for (int bin = ped_i1; bin < ped_i2; bin++) {
                ped += pulse[bin];
                noise += pulse[bin] * pulse[bin];
            }
            for (int bin=0; bin<pulse.length; bin++) {
                if(pulse[bin]>wmax) wmax=pulse[bin];
            }
            pedestal = ped / (ped_i2 - ped_i1);
            rms = LSB * Math.sqrt(noise / (ped_i2 - ped_i1) - pedestal * pedestal);
            wave_max=wmax;
        }
        
    }
}

