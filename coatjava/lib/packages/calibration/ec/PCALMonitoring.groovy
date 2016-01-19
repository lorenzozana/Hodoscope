import org.jlab.evio.clas12.*;
import org.jlab.clasrec.main.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clasrec.ui.*;
import org.jlab.geom.detector.ftof.*;
import org.jlab.geom.base.*;
import org.jlab.geom.*;
import org.jlab.geom.gui.*;
import org.jlab.geom.prim.*;
import org.jlab.utils.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;


public class PCALMonitoring extends DetectorMonitoring {

    public AbsDetectorTranslationTable ttPCAL = new AbsDetectorTranslationTable("PCAL",900);
    public TreeMap<Integer,H2D>      PCAL_ADC = new TreeMap<Integer,H2D>();
    public TreeMap<Integer,H2D>      PCAL_TDC = new TreeMap<Integer,H2D>();
    public EvioRawEventDecoder        decoder = new EvioRawEventDecoder();
    
    public PCALMonitoring(){
        super("EC","1.0","someone");
    }

    @Override
    public void init() {
    	   ttPCAL.readFile("PCAL.table");
       	   for (int lay=1 ; lay<4 ; lay++) {
    	       PCAL_ADC.put(lay, new H2D("ADC_LAYER_"+lay,68,1.0,69.0,50,0.0,300.0));
    	       PCAL_TDC.put(lay, new H2D("TDC_LAYER_"+lay,68,1.0,69.0,50,0.0,1000.0));
	       }
    }

    @Override
    public void configure(ServiceConfiguration sc) {
        
    }

    @Override
    public void analyze() {
        
    }

    @Override
    public void processEvent(EvioDataEvent event) {

      if(event.hasBank("PCAL::dgtz")==true){

    		EvioDataBank bank = event.getBank("PCAL::dgtz");
    		int nrows = bank.rows();
    		for(int loop = 0; loop < nrows; loop++){
    			int id  = bank.getInt("strip",loop);
    			int adc = bank.getInt("ADC",loop);
			int tdc = bank.getInt("TDC",loop);
			int lay = bank.getInt("view",loop);
    			PCAL_ADC.get(lay).fill(id,adc);
			PCAL_TDC.get(lay).fill(id,tdc);
    		}
    	}


	ArrayList<RawDataEntry>  rawEntries       = decoder.getDataEntries(event);	
        ArrayList<RawDataEntry>  rawDecodedPCAL   = decoder.getDecodedData(rawEntries,ttPCAL);
	
        for(RawDataEntry entry : rawDecodedPCAL){
	  System.out.println(entry);
	
	  short[] rawpulse = entry.getRawPulse();
	  
	  int ped          = entry.getIntegral(4,12)/8;
          int adc          = entry.getIntegral(25,60)-ped*35;
	  
	  System.out.println("PED = "+ped+" ADC = "+adc);
	  
// Throws exception starting here

          int slot         = entry.getSlot();
          int chan         = entry.getChannel();
          int crate        = entry.getCrate();

          System.out.println(" CRATE = " + crate + "  SLOT = " + slot + "  CHAN = "  + chan + "  PED = " + ped + "   PULSE = " + pulse);
	  /*
	  int sector       = entry.getSector();
	  int lay          = entry.getLayer();
	  int  id          = entry.getComponent();
	  PCAL_ADC.get(lay).fill(id,adc);
	  */
	  
	  
	  }
	  
    }

	void drawComponent(int sector, int layer, int component, EmbeddedCanvas canvas){
	canvas.divide(2,3);
	int ll=layer+1;
	H1D h1 = PCAL_ADC.get(ll).projectionX();
	H1D h2 = PCAL_TDC.get(ll).projectionX();
	H1D h3 = PCAL_ADC.get(ll).projectionY();
	H1D h4 = PCAL_TDC.get(ll).projectionY();
	H1D h5 = PCAL_ADC.get(ll).sliceX(component);
	H1D h6 = PCAL_TDC.get(ll).sliceX(component);
	h1.setTitle("ADC HIT DISTRIBUTION");
	h2.setTitle("TDC HIT DISTRIBUTION");
	h3.setTitle("ADC DISTRIBUTION");
	h4.setTitle("TDC DISTRIBUTION");
 	h5.setTitle("LAYER "+ll+" STRIP " + component);
 	h6.setTitle("LAYER "+ll+" STRIP " + component);
        h1.setXTitle("STRIP NUMBER")    ; h2.setXTitle("STRIP NUMBER");
        h3.setXTitle("ADC CHAN. NO.")   ; h4.setXTitle("TDC CHAN. NO.");
        h5.setXTitle("ADC CHAN. NO.")   ; h6.setXTitle("TDC CHAN. NO.");
	canvas.cd(0) ; h1.setFillColor(2) ; canvas.draw(h1);
	canvas.cd(1) ; h2.setFillColor(3) ; canvas.draw(h2);
	canvas.cd(2) ; h3.setFillColor(2) ; canvas.draw(h3);
	canvas.cd(3) ; h4.setFillColor(3) ; canvas.draw(h4);
	canvas.cd(4) ; h5.setFillColor(2) ; canvas.draw(h5);
	canvas.cd(5) ; h6.setFillColor(3) ; canvas.draw(h6);
	}


   public static void main(String[] args){
   	  PCALMonitoring pcal = new PCALMonitoring();
      	  DetectorViewApp app = new DetectorViewApp(pcal);
   }
}
