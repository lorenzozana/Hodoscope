import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.base.*;
import org.jlab.geom.component.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.clas12.fastmc.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;

CLASGeometryLoader  loader = new CLASGeometryLoader();
loader.loadGeometry("BST");
loader.loadGeometry("FTOF");
loader.loadGeometry("EC");

ParticleGenerator  electron = new ParticleGenerator(  11 , // electron PID
                           1.0  ,    5.0, // momentum min/max
                           10.0 ,   35.0, // theta (deg) min/max
                        -180.0  ,  180.0 ); // phi (deg) min/max

ParticleSwimmer  swimmer = new ParticleSwimmer(-1.0,1.0);

Particle particle = electron.getParticle();
Path3D path = swimmer.particlePath(particle);

//System.out.println(path);

System.out.println(particle);

List<DetectorHit>  hits = loader.getDetectorHits(path);

for(DetectorHit hit : hits){
	System.out.println(hit);
}

