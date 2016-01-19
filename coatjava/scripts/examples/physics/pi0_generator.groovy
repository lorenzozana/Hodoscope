import org.jlab.evio.clas12.*;
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;
import org.jlab.physics.oper.*;


ParticleGenerator pi0gen = new ParticleGenerator(111,3.0,3.2,22.0,25.0,-4.0,4.0);
TwoBodyDecay      decay  = new TwoBodyDecay(111,22,22);

for(int loop = 0; loop < 2000; loop++){
   Particle pi0 = pi0gen.getParticle();
   PhysicsEvent event = new PhysicsEvent();
   event.addParticle(pi0);
   decay.decayParticles(event);
   System.out.print(event.toLundString());
}
