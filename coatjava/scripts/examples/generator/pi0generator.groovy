//****************************************************
// Particle generator with decay module
//****************************************************
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;

//--------------------------------------------
// ParticleGenerator is a random particle 
// generator for given momenta and angles
// momenta is given in GeV and angles in degrees
//--

ParticleGenerator  pi0 = new ParticleGenerator(  111 , // particle lund id (111 - pi0)
		   1.0    ,   5.0, // minimum momenta, maximum momenta
		   7.0    ,  25.0, // minumum/maximum theta angle (degrees)
		   -180.0 , 180.0); // min/max phi angle (degrees)


PhysicsEvent  event = new PhysicsEvent();
event.setBeam(11.0);

//------------------------------------------
// TwoBodyDecay class defines a decay of
// parent particle into two daughter particles
// given by their lund PID. 

TwoBodyDecay  decay = new TwoBodyDecay(111, 22, 22);

File eventFile = new File("physics_generated_pi0.lund");
BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile));

int nEventsGenerated = 120000;

for(int loop = 0; loop < nEventsGenerated; loop++){
        event.clear();
        event.addParticle(pi0.getParticle());
	decay.decayParticles(event);
        writer.write (event.toLundString());
}

writer.close();
