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
		   	     10.0 ,   125.0, // theta (deg) min/max
			  -180.0  ,  180.0 ); // phi (deg) min/max


CLAS12FastMC    fastMC = new CLAS12FastMC(-1.0,1.0);

int countrec = 0;
int count    = 0;
for(int loop = 0; loop < 100; loop++){
  Particle ep = electron.getParticle();
  Particle eprec = fastMC.getParticle(ep);
  count++;
  if(eprec.p()>0) countrec++;
  System.out.println("=========>  Event Printout: ");
  System.out.println(" GEN : " + ep.toLundString());
  System.out.println(" REC : " + eprec.toLundString());
}

System.out.println("\n\n Reconstructed event " + countrec + "/" + count);

