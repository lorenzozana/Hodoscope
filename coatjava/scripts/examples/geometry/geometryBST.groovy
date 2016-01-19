import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.base.*;
import org.jlab.geom.component.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;


public class BSTGeometry {
  BSTLayer  layerUP   = null;
  BSTLayer  layerDOWN = null;
  DetectorTransformation  bstTransform = null;
  public BSTGeometry(){
     ConstantProvider  bstDB = DataBaseLoader.getConstantsBST();
     BSTFactory   factory    = new BSTFactory();     
     bstTransform = factory.getDetectorTransform(bstDB);
     layerDOWN    = factory.createRingLayer(bstDB,0,0,0);
     layerUP      = factory.createRingLayer(bstDB,0,0,1);
  }

  public Point3D intersection(int sector, int region, int stripid_up, int stripid_down){
  	 Transformation3D transUP   = bstTransform.get(sector,region,0);
  	 Transformation3D transDOWN = bstTransform.get(sector,region,1);
	 Line3D stripUP = new Line3D();
	 stripUP.copy(layerUP.getComponent(stripid_up).getLine());
	 Line3D stripDOWN = new Line3D();
	 stripDOWN.copy(layerDOWN.getComponent(stripid_down).getLine());
	 transUP.apply(stripUP);
	 transDOWN.apply(stripDOWN);
	 Line3D  inter = stripUP.distance(stripDOWN);
	 Point3D midp  = inter.midpoint();
	 //System.out.println( sector + " " + region + "  " + stripid_up + "  " + stripid_down);
	 //midp.show();
	 return midp;
	 //System.out.println( midp);
  }

  public int getStripFired(Line3D line, int layer){
     Transformation3D trans   = bstTransform.get(0,0,layer);
     List<SiStrip>  strips = null;
     if(layer==0){
         strips = layerDOWN.getAllComponents();
     } else {
         strips = layerUP.getAllComponents();
     }
     int cid = -1;
     double distance = 100.0;
     Line3D stripLine = new Line3D();

     for(SiStrip strip : strips){
        stripLine.copy(strip.getLine());
	trans.apply(stripLine);
	if(stripLine.distance(line).length()<distance){
           distance = stripLine.distance(line).length();
	   cid      = strip.getComponentId();
	}
     }
     return cid;
  }

  public void coordinateTransform(int sector, int region, double x, double y, double z){
      Transformation3D transUP   = bstTransform.get(sector,region,0);
      transUP.show();
      Transformation3D transINV  = transUP.inverse();
      transINV.show();
      Point3D  point = new Point3D(x,y,z);
      Transformation3D  transRotate = new Transformation3D();
      transRotate.rotateZ(-5.654866776461628).translateXYZ(-0.0, -6.5285, 21.9856);

      //transINV.apply(point);
      transRotate.apply(point);
      point.show(); 
  }

  public static main(String[] args){
  
     BSTGeometry  geom = new BSTGeometry();
  /*   Line3D  line = new Line3D(0.0,0.0,0.0,0.0,1000.0,0.0);
     double length = 40.0;
     for(int loop = 90.0; loop > 30; loop-=5){
        Point3D point = new Point3D(0.0,6.3,0.0);
	double angle = (double) loop;
	double angleRad = Math.toRadians(angle);
	line.set(point.x()-length*Math.cos(angleRad), 
		 point.y()-length*Math.sin(angleRad),
		 0.0,
		 point.x()+length*Math.cos(angleRad),
                 point.y()+length*Math.sin(angleRad),
                 0.0,
		 );
        int cidDW = geom.getStripFired(line,0);
        int cidUP = geom.getStripFired(line,1);
	Point3D  midp = geom.intersection(0,0,cidDW, cidUP);
	System.out.println(loop + " " + cidDW + "   " + cidUP + " " + midp.z() );
     }


     return;
*/


     int  sector = Integer.parseInt(args[0]); 
     int  region = Integer.parseInt(args[1]);
     if(args.length>4){
       double x = Double.parseDouble(args[2]);
       double y = Double.parseDouble(args[3]);
       double z = Double.parseDouble(args[4]);
       geom.coordinateTransform(sector,region,x,y,z);
     } else { 
       int  up     = Integer.parseInt(args[2]);
       int  down   = Integer.parseInt(args[3]); 
       geom.intersection(sector,region,up,down);
     }
  }
}
