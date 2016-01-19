//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clas.physics.*;
import org.jlab.clas.tools.utils.*;

/*
ParticleGenerator  electron = new ParticleGenerator(  11    ,0.3,7.0,7.0,35.0,-180.0,180.0);
ParticleGenerator  proton   = new ParticleGenerator(  2212  ,0.3,7.0,7.0,35.0,-180.0,180.0);
ParticleGenerator  pionm    = new ParticleGenerator( -211   ,0.3,7.0,7.0,35.0,-180.0,180.0);
ParticleGenerator  pionp     = new ParticleGenerator(  211  ,0.3,7.0,7.0,35.0,-180.0,180.0);
*/



ParticleGenerator  proton   = new ParticleGenerator(  2212 ,0.3,1.8 , 45.0, 115.0,-180.0,180.0);
ParticleGenerator  pionm    = new ParticleGenerator( -211  ,0.3,1.8 , 45.0, 115.0,-180.0,180.0);
ParticleGenerator  pionp    = new ParticleGenerator(  211  ,0.3,1.8 , 45.0, 115.0,-180.0,180.0);
ParticleGenerator  kaonm    = new ParticleGenerator( -321  ,0.3,1.8 , 45.0, 115.0,-180.0,180.0);

PhysicsEvent  event = new PhysicsEvent();
event.setBeam(11.0);


for(int num = 0; num < 5; num++){

  String number = StringUtils.numberString(num+1,4); 
  File eventFile = new File("multitrack_4_particles_central_" + number + ".lund");
  System.out.println("GENERATING FILE # " + (num + 1));
  BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile));

  for(int loop = 0; loop < 20000; loop++){
	event.clear();

	//event.addParticle(electron.getParticle());
	event.addParticle(proton.getParticle());
	event.addParticle(pionm.getParticle());
	event.addParticle(pionp.getParticle());
	event.addParticle(kaonm.getParticle());
	//System.out.print(event.toLundString());
	writer.write (event.toLundString());
   }

   writer.close();
}

