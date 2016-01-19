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


public class DetectorView implements IDetectorHistogramDraw,IDetectorShapeIntensity {

	
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

    void drawLayer(int sector, int layer, EmbeddedCanvas canvas){

    }
    void drawSector(int sector, EmbeddedCanvas canvas){

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

   	  DetectorView calib = new  DetectorView();
      DetectorBrowserApp app = new DetectorBrowserApp();
      
      for(int rows = 0; rows < 20; rows++){
      	for(int cols = 0; cols < 20; cols++){
      		int x = cols * 300;
      		int y = rows * 300;
      		if(cols%2==0) y += 150;
      		DetectorShape3D shape = calib.getHexagon(x,y,150,1,rows,cols);
      		app.addDetectorShape("DriftChambers",shape);
      		//System.out.println(shape);
      	}
      }


      app.setIntensityMap("DriftChambers",calib);
      app.setHistogramDraw(calib);

      app.updateDetectorView();

   }
}
