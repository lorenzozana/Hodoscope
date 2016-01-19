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

ParticleGenerator  proton   = new ParticleGenerator(  2212 ,1.0,5.0, 35.0,125.0,-180.0,180.0);

for(int loop = 0; loop < 20; loop++){
   Particle prand = proton.getParticle();
   System.out.println(prand); 
   Path3D  particlePath = new Path3D();
   particlePath.addPoint(0.0,0.0,0.0);
   particlePath.addPoint(1500.0*prand.px(),1500.0*prand.py(),1500.0*prand.pz());
   List<DetectorHit>  hits = detector.getHits(particlePath);
   System.out.println(" HIT ARRAY SIZE = " + hits.size());
   for(DetectorHit hit : hits){
      System.out.println("\t" + hit.toString());
   }
   List<DetectorHit>  echits = ecdetector.getHits(particlePath);
   System.out.println(" EC DETECTOR HIT ARRAY SIZE = " + echits.size());
   for(DetectorHit hit : echits){
      //System.out.println("\t" + hit.toString());
   }

}
