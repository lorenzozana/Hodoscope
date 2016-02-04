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
    DetectorCollection<H1D> H_NPE = new DetectorCollection<H1D>();
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
    int[] crystalPointers;
    
    // fADC have 4096 bins and the range is 0.0 to 2.0 V (for run > 230 )
    int threshold = 1000; // 1000 = 500 mV
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

        this.initDetector();
        this.initHistograms();
        this.initRawDataDecoder();
        JSplitPane splitPane = new JSplitPane();
        splitPane.setLeftComponent(this.view);

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
        splitPane.setRightComponent(canvasPane);

        this.detectorPanel = new JPanel();
        this.detectorPanel.setLayout(new BorderLayout());
        this.evPane.addProcessor(this);
        this.detectorPanel.add(splitPane, BorderLayout.CENTER);
        this.detectorPanel.add(this.evPane, BorderLayout.PAGE_END);
        
        TStyle.setAxisFont("Helevetica", 16);
        TStyle.setStatBoxFont("Courier", 12);
    }

    private void initDetector() {
	
        DetectorShapeView2D viewFTHODO = new DetectorShapeView2D("FTHODO");
        int sec_a;
        int crys_a;
        
	// position of layer in y-direction
	// move (correctly labelled) thin layer to top
	double[] p_layer = {-180.0,180.0};
        // flip the (correctly labelled) thick layer
	double[] v_layer = {1.0,-1.0};
        double[] p_size = {15.0,30.0,15.0,30.0,30.0,30.0,30.0,30.0,15.0,
			   30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,
			   30.0,30.0,15.0,15.0,15.0,15.0,15.0,15.0,15.0,15.0};
        double[] p_R	= {16.2331,15.6642,16.2331,15.0076,13.0933,15.6642,13.0933,10.8102,7.5590,
			   15.9022,15.3132,15.3132,15.9022,13.0258,12.2998,12.2998,13.0258,10.2395,9.2984,
			   9.2984,10.2395,8.7322,7.8847,7.2650,6.9344,6.9344,7.2650,7.8847,8.7322};
        double[] p_theta = {-0.65294,-0.50511,-0.91786,-0.78540,-0.61741,-1.06568,-0.95339,-0.78540,-0.78540,
			    -0.29005,-0.09916,0.09916,0.29005,-0.35667,-0.12357,0.12357,0.35667,-0.46024,-0.16377,
			    0.16377,0.46024,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118};
        for (int layer_c=0; layer_c<2; layer_c++){
            for (int sec_c=0; sec_c<4; sec_c++) {
		
                for (int component = 0; component < 29; component++) {
                    if (component<9) {
                        sec_a = sec_c*2 +1;
                        crys_a = component + 1;
                    }
                    else {
                        sec_a = sec_c*2 +2;
                        crys_a = component + 1 -9;
                    }
		    double xcenter = v_layer[layer_c] * p_R[component] * Math.sin(p_theta[component]+Math.PI /2 *sec_c)*10;
		    double ycenter = -p_R[component] * Math.cos(p_theta[component]+Math.PI /2 *sec_c)*10 +p_layer[layer_c];
		    DetectorShape2D shape = new DetectorShape2D(DetectorType.FTOF, sec_a, layer_c+1,crys_a);
		    shape.createBarXY(p_size[component], p_size[component]);
		    shape.getShapePath().translateXYZ(xcenter, ycenter, 0.0);
		    shape.setColor(0, 145, 0);
		    viewFTHODO.addShape(shape);               
		    
                }   
            }
        }
        this.view.addDetectorLayer(viewFTHODO);
        view.addDetectorListener(this);
    }

    private void initRawDataDecoder() {
        decoder.addFitter(DetectorType.FTOF,
                new FADCBasicFitter(ped_i1, // first bin for pedestal
                        ped_i2, // last bin for pedestal
                        pul_i1, // first bin for pulse integral
                        pul_i2 // last bin for pulse integral
                ));
    }
    
        

    private void initHistograms() {

        for (int component = 0; component < 232; component++) {
                int ilayer = component/116 + 1;
                String layer_s;
                //  layer_s was the wrong way round and has now been switched
		if (ilayer==1) {
                    layer_s = "thin";
                }
                else {
                    layer_s = "thick";
                }
                // (map components in both layers to [0,115]) 
		// map components to quadrants [0,3] 
		int iy = (component-(ilayer-1)*116) / 29;
		// map components to [0,28] 
                int ix = component - iy * 29 -(ilayer-1)*116;
		
		// map iy to sectors [1,8]
		int sec_a;
		// map ix to crystals [1,9] or
		// map ix to crystals [1,20]
                int crys_a;
                if (ix<9) {
		    sec_a = iy*2 +1;
                    crys_a = ix + 1;
                }
                else {
                    sec_a = iy*2 +2;
                    crys_a = ix + 1 -9;
                }
		
		String title;
		    
		// The thick layer titles are written as if the numbers match the thin layer looking downstream
		if(ilayer==1)
		    title =  " " +layer_s +" layer, sector" + sec_a + ", crystal" + crys_a ;
		else
		    title =  " " + layer_s +" layer, sector" + matchingSector(sec_a) + ", crystal" + matchingElement(crys_a,sec_a);
		
                H_fADC.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("fADC", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
                H_NOISE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Noise", sec_a,ilayer,crys_a), title, 200, 0.0, 10.0));
                H_NOISE.get(sec_a,ilayer, crys_a).setFillColor(4);
                H_NOISE.get(sec_a,ilayer, crys_a).setXTitle("RMS (mV)");
                H_NOISE.get(sec_a,ilayer, crys_a).setYTitle("Counts");                        
                H_WAVE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("WAVE", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
                H_WAVE.get(sec_a,ilayer, crys_a).setFillColor(5);
                H_WAVE.get(sec_a,ilayer, crys_a).setXTitle("fADC Sample");
                H_WAVE.get(sec_a,ilayer, crys_a).setYTitle("fADC Counts");
                
		H_NPE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("NPE", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
                H_NPE.get(sec_a,ilayer, crys_a).setFillColor(5);
                H_NPE.get(sec_a,ilayer, crys_a).setXTitle("Time");
                H_NPE.get(sec_a,ilayer, crys_a).setYTitle("Number of photoelectrons");
                
		H_COSMIC_fADC.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Cosmic fADC", sec_a,ilayer,crys_a), title, 100, 0.0, 100.0));
                H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setFillColor(3);
                H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setXTitle("fADC Sample");
                H_COSMIC_fADC.get(sec_a,ilayer, crys_a).setYTitle("fADC Counts");
                H_COSMIC_CHARGE.add(sec_a,ilayer, crys_a, new H1D(DetectorDescriptor.getName("Cosmic Charge", sec_a,ilayer,crys_a), title, 80, 0.0, 80.0));
                H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setFillColor(2);
                H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setXTitle("Charge (pC)");
                H_COSMIC_CHARGE.get(sec_a,ilayer, crys_a).setYTitle("Counts");
                
                mylandau.add(sec_a,ilayer, crys_a, new F1D("landau",     0.0, 80.0));

        }
        H_fADC_N   = new H1D("fADC", 484, 0, 484);
        H_WMAX     = new H1D("WMAX", 484, 0, 484);
        H_COSMIC_N = new H1D("EVENT", 484, 0, 484);
        
        crystalID       = new double[332];
        noiseRMS        = new double[332];
        crystalPointers = new int[484];
        int ipointer=0;
        for(int i=0; i<484; i++) {
            if(doesThisCrystalExist(i)) {
                crystalPointers[i]=ipointer;
                crystalID[ipointer]=i;
                ipointer++;
            }
            else {
                crystalPointers[i]=-1;
            }
        }
    }

    private void resetHistograms() {
        for (int component = 0; component < 232; component++) {
            int ilayer = component/116 + 1;
            int iy = (component-(ilayer-1)*116) / 29;
            int ix = component - iy * 29 -(ilayer-1)*116;
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

    
    private boolean doesThisCrystalExist(int id) {
        
        boolean crystalExist=false;
        int iy = id / 22;
        int ix = id - iy * 22;
        
        double xcrystal = crystal_size * (22 - ix - 0.5);
        double ycrystal = crystal_size * (22 - iy - 0.5);
        double rcrystal = Math.sqrt(Math.pow(xcrystal - crystal_size * 11, 2.0) + Math.pow(ycrystal - crystal_size * 11, 2.0));
        if (rcrystal > crystal_size * 4 && rcrystal < crystal_size * 11) {
            crystalExist=true;
        }
        return crystalExist;
    }
    
    private int matchingElement(int element, int sector) {
	
        int   mirroringElement = 0;
	int[] elementsOdd      = {3,6,1,4,7,2,5,8,9};
	int[] elementsEven     = {4,3,2,1,8,7,6,5,12,11,10,9,20,19,18,17,16,15,14,13};
	
	if(sector!=0){
	    if(sector%2==0)
		mirroringElement = elementsEven[element-1];
	    else
 	    mirroringElement = elementsOdd[element-1];
	}
	
	return mirroringElement;
    }
    
    private int matchingSector(int sector) {
	
	int mirroringSector = 0;
 	int [] mirroredSectors = {3,2,1,8,7,6,5,4};
	
	if(sector!=0)
	    mirroringSector = mirroredSectors[sector-1]; 
	
	return mirroringSector;
    }
        
    public void processEvent(DataEvent de) {
        EvioDataEvent event = (EvioDataEvent) de;

        decoder.decode(event);
        nProcessed++;
        System.out.println("event #: " + nProcessed);
        List<DetectorCounter> counters = decoder.getDetectorCounters(DetectorType.FTOF);
        FTHODOViewerModule.MyADCFitter fadcFitter = new FTHODOViewerModule.MyADCFitter();
        H_WMAX.reset();
        for (DetectorCounter counter : counters) {
            int key = counter.getDescriptor().getComponent();
            int sec_k = counter.getDescriptor().getSector();
            int layer_k = counter.getDescriptor().getLayer();
            System.out.println("sector: " + sec_k + "  layer:" + layer_k + "  component:" + key);
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
	
	if (plotSelect == 0 ) {
            this.canvas.divide(1, 2);
	    canvas.cd(layerSelect-1);
	    if(H_WAVE.hasEntry(secSelect,layerSelect,keySelect))
		this.canvas.draw(H_WAVE.get(secSelect,layerSelect,keySelect));
	    canvas.cd(layerSelect%2);
	    if(H_WAVE.hasEntry(matchingSector(secSelect),(layerSelect%2)+1, matchingElement(keySelect,secSelect)))
		this.canvas.draw(H_WAVE.get(matchingSector(secSelect),(layerSelect%2)+1, matchingElement(keySelect,secSelect)));            
	}
	
        //this.dcHits.show();
        this.view.repaint();
    }

    public void detectorSelected(DetectorDescriptor desc) {
        System.out.println("SELECTED = " + desc);
        keySelect = desc.getComponent();
        secSelect = desc.getSector();
        layerSelect = desc.getLayer();
        //System.out.println("Sector=" + secSelect + " Layer=" +layerSelect + " Component=" + keySelect);
	
	if (plotSelect == 0 ) {
            this.canvas.divide(1, 2);
	    canvas.cd(layerSelect-1);
	    if(H_WAVE.hasEntry(secSelect,layerSelect,keySelect))
		this.canvas.draw(H_WAVE.get(secSelect,layerSelect,keySelect));
	    canvas.cd(layerSelect%2);
	    if(H_WAVE.hasEntry(matchingSector(secSelect),(layerSelect%2)+1, matchingElement(keySelect,secSelect)))
		this.canvas.draw(H_WAVE.get(matchingSector(secSelect),(layerSelect%2)+1, matchingElement(keySelect,secSelect)));            
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
        return DetectorType.FTOF;
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

