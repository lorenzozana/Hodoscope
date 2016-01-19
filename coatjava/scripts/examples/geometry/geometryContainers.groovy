import org.jlab.geom.*;
import org.jlab.geom.prim.*;

//****************************************************
// Triangles
//****************************************************

Triangle3D tri = new Triangle3D(  0.0, 0.0, 0.0, // first point
                                  1.0, 0.0, 0.0, //second point
                                  0.0, 0.0, 1.0 ); // third point
tri.show();

//****************************************************
//  BOX object
//****************************************************
// Creating a BOX with side=2
Shape3D  box = new Shape3D();
// Bottom Square at X=0 plane
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 0.0,-1.0, 1.0, 0.0,-1.0,-1.0));
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 0.0, 1.0,-1.0, 0.0,-1.0,-1.0));
// Top Square at X=2 plane
box.addFace(new Triangle3D( 2.0, 1.0, 1.0, 2.0,-1.0, 1.0, 2.0,-1.0,-1.0));
box.addFace(new Triangle3D( 2.0, 1.0, 1.0, 2.0, 1.0,-1.0, 2.0,-1.0,-1.0));
// Side face XY-Plane at Z=1
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0,-1.0, 1.0));
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 2.0,-1.0, 1.0, 0.0,-1.0, 1.0));
// Side face XY-Plane at Z=-1
box.addFace(new Triangle3D( 0.0, 1.0,-1.0, 2.0, 1.0,-1.0, 2.0,-1.0,-1.0));
box.addFace(new Triangle3D( 0.0, 1.0,-1.0, 2.0,-1.0,-1.0, 0.0,-1.0,-1.0));
// Side face XZ plane  Y=1
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 0.0, 1.0,-1.0, 2.0, 1.0,-1.0));
box.addFace(new Triangle3D( 0.0, 1.0, 1.0, 2.0, 1.0, 1.0, 2.0, 1.0,-1.0));
// Side face XZ plane  Y=-1
box.addFace(new Triangle3D( 0.0,-1.0, 1.0, 0.0,-1.0,-1.0, 2.0,-1.0,-1.0));
box.addFace(new Triangle3D( 0.0,-1.0, 1.0, 2.0,-1.0, 1.0, 2.0,-1.0,-1.0));
box.show();

Transformation3D  transform = new Transformation3D();
transform.translateXYZ(0.0,0.0,8.0);
transform.apply(box);

box.show();

Line3D line = new Line3D(0.0,0.0,0.0, 0.0, 0.0, 5.0);

System.out.println("Line    Intersection : " + box.hasIntersection(line));
System.out.println("Ray     Intersection : " + box.hasIntersectionRay(line));
System.out.println("Segment Intersection : " + box.hasIntersectionSegment(line));


Line3D segment = new Line3D(0.0,0.0,0.0, 0.0, 0.0, 8.0);

ArrayList<Point3D> ip = new ArrayList<Point3D>();

box.intersectionRay(segment,ip);
System.out.println("INTERSECTION POINTS FOR RAY : ");
for(Point3D point : ip){
   System.out.println(point);
}

ip.clear();
box.intersectionSegment(segment,ip);
System.out.println("INTERSECTION POINTS FOR SEGMENT : ");
for(Point3D point : ip){
   System.out.println(point);
}
