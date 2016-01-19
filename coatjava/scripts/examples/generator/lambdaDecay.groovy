import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;

LundReader  reader = new LundReader();
reader.addFile("aeg.lund");
reader.open();
int icounter = 0;

TwoBodyDecay  decay = new TwoBodyDecay(3122,2212,-211);

while(reader.next()==true){
  PhysicsEvent lundEvent = reader.getEvent();
  //System.out.println("-----> Starting event");
  //System.out.println(lundEvent.toLundString());
  decay.decayParticles(lundEvent);
  System.out.print(lundEvent.toLundString());
  //Particle lambda = lundEvent.getParticle("[p]+[pi-]");
  //System.out.println(" mass = " + lambda.mass());  
  icounter++;
}
