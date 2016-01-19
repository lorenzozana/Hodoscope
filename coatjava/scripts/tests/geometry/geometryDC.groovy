import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.detector.dc.*;
import org.jlab.geom.base.*;
import org.jlab.geom.component.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;


ConstantProvider  dcDB = DataBaseLoader.getConstantsDC();
DCFactory   factory    = new DCFactory(); 
DCDetector  detector   = factory.createDetectorCLAS(dcDB);


DCLayer     layer   = detector.getSector(0).getSuperlayer(0).getLayer(5);

Transformation3D  transform = new Transformation3D();
//transform.rotateZ(-90.0);
//transform.rotateX(-90.0);



transform.rotateX(Math.toRadians(-90.0));
transform.rotateZ(Math.toRadians(25.0));


for(int loop = 0; loop < layer.getNumComponents(); loop++){
	List<Line3D>  path = layer.getComponent(loop).getVolumeCrossSection(transform);
	System.out.println("component " + loop + "   SIZE = " + path.size() );
	if(path.size()!=6){
	   System.out.println("COMPONENT = " + loop);
	   for(Line3D line : path){
	   	      System.out.println(line);
	   }
	}
}
