import org.jlab.geom.detector.ftof.*;
import org.jlab.geom.base.*;
import org.jlab.geom.*;
import org.jlab.geom.gui.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clasrec.ui.*;
import org.jlab.geom.prim.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.main.*;

public class MonitoringFTCAL extends DetectorMonitoring {

	H2D H_ADC = null;

	public MonitoringFTCAL(){
        super("EC","1.0","someone");
    }
    
	@Override
    public void init() {
    	H_ADC = new H2D("HISTOGRAM",420,0,420,200,0.0,5000.0);
    }

    @Override
    public void configure(ServiceConfiguration sc) {
        
    }

    @Override
    public void analyze() {
        
    }

    @Override
    public void processEvent(EvioDataEvent event) {
    	if(event.hasBank("FTCAL::dgtz")==true){
    		EvioDataBank bank = event.getBank("FTCAL::dgtz");
    		int nrows = bank.rows();
    		for(int loop = 0; loop < nrows; loop++){
    			int id = 12*bank.getInt("idx",loop)*bank.getInt("idy",loop);
    			int adc = bank.getInt("ADC",loop);
    			H_ADC.fill(id,adc);
    		}
    	}
    }

	void drawComponent(int sector, int layer, int component, EmbeddedCanvas canvas){
		canvas.divide(1,1);
		H1D h = H_ADC.sliceX(component);
 		h.setTitle("Crystal " + component);
        h.setXTitle("ADC");                
        canvas.cd(0);
        h.setFillColor(6);
        canvas.draw(h);
	}


   public static void main(String[] args){
   	  MonitoringFTCAL ftcal = new MonitoringFTCAL();
      	  DetectorViewApp app = new DetectorViewApp(ftcal);
   }
}
