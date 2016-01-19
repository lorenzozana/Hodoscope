//****************************************************
// Particle generator with decay module
//****************************************************
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.fastmc.*;
import org.jlab.geom.prim.*;
import org.jlab.geom.*;

ParticleGenerator  electron = new ParticleGenerator(  11 , // electron PID  
		   	     1.0  ,    5.0, // momentum min/max 
		   	     7.0  ,   25.0, // theta (deg) min/max
			  -180.0  ,  180.0 ); // phi (deg) min/max

CLAS12FastMC    fastMC = new CLAS12FastMC(-1.0,1.0);
ParticleSwimmer pswim = new ParticleSwimmer();

for(int loop = 0; loop < 20; loop++){
  Particle ep = electron.getParticle();
  Path3D   pp = pswim.particlePath(ep);
  
  System.out.println("\n\n------> event # " + loop);
  System.out.println(ep);
  System.out.println(fastMC.isParticleDetected(ep));
  //pp.show();
/*
  List<DetectorHit>  hitsDC = fastMC.getDetectorHits("DC",pp);
  System.out.println("DC HITS = " + hitsDC.size());

  List<DetectorHit>  hitsFTOF = fastMC.getDetectorHits("FTOF",pp);
  System.out.println("FTOF HITS = " + hitsFTOF.size());
*/
}

