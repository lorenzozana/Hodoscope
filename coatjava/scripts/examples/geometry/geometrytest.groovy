import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.base.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;

ConstantProvider  bstDB = DataBaseLoader.getConstantsBST();
ConstantProvider  ecDB  = DataBaseLoader.getConstantsEC();
BSTFactory   factory    = new BSTFactory();
BSTDetector  detector   = factory.createDetectorCLAS(bstDB);
ECFactory    ecfactory    = new ECFactory();
ECDetector   ecdetector   = ecfactory.createDetectorCLAS(ecDB);

DetectorTransformation trans = factory.getDetectorTransform(bstDB);

Transformation3D  tr = trans.get(1,0,0);
Point3D point = new Point3D(0.0,0.0,0.0);
point.show();
tr.show();
tr.apply(point);

point.show();
