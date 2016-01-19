import org.jlab.geom.*;
import org.jlab.geom.prim.*;

//****************************************************
// Operations with Points
//****************************************************

Point3D p1 = new Point3D( 0.0, 0.5,  0.5);
Point3D p2 = new Point3D( 0.5, 0.5, -0.5);

System.out.println(p1);
System.out.println(p2);
System.out.println(" ditance =  " + p1.distance(p2));


//****************************************************
// Operations with Lines
//****************************************************

Line3D  line1 = new Line3D( p1, p2);
Line3D  line2 = new Line3D( -1.5, 0.0, 0.0, 1.5, 0.0, 0.0);
System.out.println(line1);
System.out.println(line2);

// returns line connecting point of closest approach
// of two lines.

Line3D  line3 = line1.distance(line2); 
System.out.println(line3);
System.out.println("CLOSEST APROACH  : " + line3.midpoint());
System.out.println("CLOSEST DISTANCE : " + line3.length());

//****************************************************
// Translations and Rotations
//****************************************************

Point3D p4 = new Point3D( 0.7, 0.7, 0.7);
Transformation3D  transform = new Transformation3D();
transform.translateXYZ(2.0,3.0,4.0).rotateZ(Math.toRadians(30.0));
transform.show();
transform.rotateY(Math.toRadians(45.0));
transform.show();
System.out.println("BEFORE TRANSFORM : " + p4);
transform.apply(p4);
System.out.println("AFTER  TRANSFORM : " + p4);
// Now after the transformation we can apply an
// inverse transformation to bring point to it's
// original position
Transformation3D  invtrans = transform.inverse();
invtrans.apply(p4);
System.out.println("INVERSE  TRANSFORM : " + p4);