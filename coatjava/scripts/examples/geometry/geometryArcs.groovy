import org.jlab.geom.*;
import org.jlab.geom.prim.*;

//****************************************************
// Operations with Points
//****************************************************

Arc3D arc = new Arc3D(new Point3D(1.0,0.0,0.0), new Point3D(0.0,0.0,0.0), new Vector3D(0.0,0.0,1.0),Math.PI*2.0);
arc.show();
Cylindrical3D cylinder = new Cylindrical3D(arc,2.0);
cylinder.show();

ArrayList<Point3D> intersects = new ArrayList<Point3D>();

for(double angle = 0.0; angle < 180.0; angle+=5.0){
	double theta = Math.toRadians(angle);
	Line3D line = new Line3D(0.0,0.0,0.0,0.0,20.0*Math.sin(theta),20.0*Math.cos(theta));
	intersects.clear();
	int status = cylinder.intersectionRay(line,intersects);

	System.out.println(angle + "  =  " + intersects.size());
}
