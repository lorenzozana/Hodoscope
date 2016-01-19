import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.dc.*;
import org.jlab.geom.base.*;
import org.jlab.geom.component.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;



ConstantProvider  dcDB  = DataBaseLoader.getDriftChamberConstants();

//DCFactory    factory    = new DCFactory();
DCFactoryUpdated    factory    = new DCFactoryUpdated();
DCDetector   detector  = factory.createDetectorTilted(dcDB);


Point3D midpoint0 = detector.getSector(0).getSuperlayer(0).getLayer(0).getComponent(0).getMidpoint();
Point3D midpoint1 = detector.getSector(0).getSuperlayer(0).getLayer(1).getComponent(0).getMidpoint();
System.out.println(midpoint0);
System.out.println(midpoint1);
