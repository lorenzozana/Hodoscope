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

public class CalibrationClass implements IDetectorHistogramDraw{

	void drawComponent(int sector, int layer, int component, EmbeddedCanvas canvas){
		System.out.println("DRAWING SECTOR/LAYER/COMPONENT " + sector + "  " + layer + " " + component);
		H1D  hist1 = new H1D("hist1",200,0.0,24.0);
		H1D  hist2 = new H1D("hist2",200,0.0,24.0);
		hist1.setFillColor(3);
		hist2.setFillColor(4);

		for(int loop =0; loop < 12000; loop++){
			hist1.fill(Math.random()*24.0);
			if(loop%2==0) hist2.fill(Math.random()*24.0);
		}

		H1D  hist3 = new H1D("hist2",200,0.0,48.0);
		H1D  hist4 = new H1D("hist2",200,0.0,48.0);

		for(int loop =0; loop < 12000; loop++){
			hist3.fill(Math.random()*24.0+Math.random()*24.0);
			if(loop%2==0) hist4.fill(Math.random()*24.0+Math.random()*24.0);
		}		
		hist3.setFillColor(6);
		hist4.setFillColor(7);

		canvas.divide(3,1);
		canvas.cd(0);
		canvas.draw(hist1);
		canvas.draw(hist2);
		canvas.cd(1);
		canvas.draw(hist3);
		canvas.cd(2);
		canvas.draw(hist4);

	}

    void drawLayer(int sector, int layer, EmbeddedCanvas canvas){

    }
    void drawSector(int sector, EmbeddedCanvas canvas){

    }

   public static void main(String[] args){
   	CalibrationClass calib = new  CalibrationClass();
      DetectorViewApp app = new DetectorViewApp();
        app.initDetector("FTCAL");
        app.setHistogramDrawer(calib);
   }
}
