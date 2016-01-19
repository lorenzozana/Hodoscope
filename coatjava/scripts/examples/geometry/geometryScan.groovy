import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.base.*;
import org.jlab.geom.component.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;

double particle_p      = Double.parseDouble(args[0]);
double particle_theta  = Double.parseDouble(args[1]);
double particle_phi    = Double.parseDouble(args[2]);

ConstantProvider  bstDB = DataBaseLoader.getConstantsBST();
ConstantProvider  ecDB  = DataBaseLoader.getConstantsEC();

BSTFactory   factory    = new BSTFactory();
BSTDetector  detector   = factory.createDetectorCLAS(bstDB);
BSTLayer     layerA      = factory.createRingLayer(bstDB,0,0,0);
BSTLayer     layerB      = factory.createRingLayer(bstDB,0,0,1);


Shape3D boundary = layerA.getBoundary();

layerA.show();
layerA.getComponent(0).getLine().show();
layerB.getComponent(0).getLine().show();
boundary.show();
/*
DetectorTransformation  bstTransform = factory.getDetectorTransform(bstDB);
Vector3  vector = new Vector3();
vector.setMagThetaPhi(particle_p, Math.toRadians(particle_theta),Math.toRadians(particle_phi));

Path3D   path = new Path3D();
path.addPoint(0.0,0.0,0.0);
path.addPoint(15000.0 * vector.x(),15000.0 * vector.y(),15000.0 * vector.z());
Line3D  traj = path.getLine(0);

for(int sector = 0; sector < 4; sector++){

   Transformation3D transA = bstTransform.get(0,sector,0);
   Transformation3D transB = bstTransform.get(0,sector,1);

   List<SiStrip>    strips = layerA.getAllComponents();
   double distance = 1000.0;
   Point3D  intersect = new Point3D();
   int    cid      = -1;
   for(SiStrip strip : strips){
     Line3D siLine = new Line3D();
     siLine.copy(strip.getLine());
     transA.apply(siLine);
     //siLine.show();
     if(siLine.distance(traj).length()<distance){
         cid = strip.getComponentId();
         distance = siLine.distance(traj).length();
         intersect.copy(siLine.distance(traj).midpoint());
     }

   }

   System.out.println("LAYER A SECTOR = " + sector + " DIST = " + distance + "  CID = " + cid);
   intersect.show();
   distance = 1000.0;
   cid      = -1;
   for(SiStrip strip : strips){
     Line3D siLine = new Line3D();
     siLine.copy(strip.getLine());
     transB.apply(siLine);
     //siLine.show();
     if(siLine.distance(traj).length()<distance){
         cid = strip.getComponentId();
         distance = siLine.distance(traj).length();
         intersect.copy(siLine.distance(traj).midpoint());
     }

   }
   System.out.println("LAYER B SECTOR = " + sector + " DIST = " + distance + "  CID = " + cid);
   intersect.show();
}
//System.out.println(vector.mag() + "  " + vector.theta() + "  " + vector.phi());
//List<DetectorHit>  bsthits = detector.getHits(path);
//List<DetectorHit>  bsthits = detector.getHits(path);
//for(DetectorHit hit : bsthits){
//   System.out.println("\t" + hit.toString());
//}


Transformation3D transAL = bstTransform.get(0,0,0);
Transformation3D transBL = bstTransform.get(0,0,1);

SiStrip  strip100A = layerA.getComponent(99);
SiStrip  strip100B = layerB.getComponent(99);

Line3D   line100A  = new Line3D();
Line3D   line100B  = new Line3D();

line100A.copy(strip100A.getLine());
line100B.copy(strip100B.getLine());

transAL.apply(line100A);
transBL.apply(line100B);

Line3D  intersect = line100A.distance(line100B);

line100A.show();
line100B.show();
intersect.show();
intersect.midpoint().show();
*/