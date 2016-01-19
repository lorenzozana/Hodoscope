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
import java.awt.Color;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.main.*;

public class ECMonitoring extends DetectorMonitoring {
  H2D H_ADC = null;
	 public ECMonitoring(){
        super("FTCAL","1.0","someone");
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
        if(event.hasBank("EC::dgtz")==true){
                EvioDataBank bank = event.getBank("EC::dgtz");
                int nrows = bank.rows();
                for(int loop = 0; loop < nrows; loop++){
                        //int id = 12*bank.getInt("idx",loop)*bank.getInt("idy",loop);
                        //int adc = bank.getInt("ADC",loop);
                        //H_ADC.fill(id,adc);
                }
        }
    }

	public Color getColor(int sector, int layer, int component){
		int r = 0;
		int g = layer*10;
		int b = component*10;
		//return new Color(0,200,0);
		return new Color(r,g,b);
	}

	void drawComponent(int sector, int layer, int component, EmbeddedCanvas canvas){
		H1D  hist1 = new H1D("hist1",200,0.0,24.0);
		hist1.setFillColor(3);
		hist1.setTitle("SECTOR " + sector + "  LAYER  " + layer + "  COMPONENT " + component);
		for(int loop =0; loop < 12000; loop++){
			hist1.fill(Math.random()*24.0);
		}
		canvas.divide(1,1);
		canvas.cd(0);
		canvas.draw(hist1);
	}

  public DetectorShape3D getHexagon(int x, int y, int size, int sector, int layer, int comp){

    	double[] xc= new double[6];
    	double[] yc= new double[6];
    	double start_angle = 120.0;
    	
    	for(int loop = 0; loop < 6; loop++){
    		double angle = start_angle + loop*60;
    		xc[loop] = x - size*Math.cos(Math.toRadians(angle));
    		yc[loop] = y - size*Math.sin(Math.toRadians(angle));
    	}

    	DetectorShape3D shape = new DetectorShape3D();
    	shape.SECTOR = sector;
    	shape.LAYER  = layer;
    	shape.COMPONENT = comp;
    	shape.setPoints(xc,yc);
    	return shape;
    }

   public static void main(String[] args){

   	  ECMonitoring monitor   = new  ECMonitoring();
      DetectorBrowserApp app = new DetectorBrowserApp();
      
      for(int rows = 0; rows < 20; rows++){
      	for(int cols = 0; cols < 20; cols++){
      		int x = cols * 300;
      		int y = rows * 300;
      		if(cols%2==0) y += 150;
      		DetectorShape3D shape = monitor.getHexagon(x,y,150,1,rows,cols);
      		app.addDetectorShape("EC Pixels",shape);
      		//System.out.println(shape);
      	}
      }

      for(int rows = 0; rows < 20; rows++){
        for(int cols = 0; cols < 20; cols++){
          int x = cols * 300;
          int y = rows * 300;
          if(cols%2==0) y += 150;
          DetectorShape3D shape = monitor.getHexagon(x,y,150,2,rows,cols);
          app.addDetectorShape("PCAL Pixels UV",shape);
          //System.out.println(shape);
        }
      }

      //app.setIntensityMap("DriftChambers",calib);
      //app.setHistogramDraw(calib);
      app.setPluginClass(monitor);
      app.updateDetectorView();

   }
}
