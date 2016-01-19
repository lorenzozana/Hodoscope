//****************************************************
// Particle generator with decay module
//****************************************************
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.fastmc.*;


String inputFile = args[0];

CLAS12FastMC    fastMC = new CLAS12FastMC(-1.0,1.0); // Torus = -1, Solenoid = 1.0

LundReader      reader = new LundReader();
reader.addFile(inputFile);
reader.open();

while(reader.next()==true){
   PhysicsEvent genEvent = reader.getEvent();
   PhysicsEvent recEvent = fastMC.getEvent(genEvent);
   if(recEvent.countByPid(11)>0){
      System.out.println("==============>  EVENT PRINTOUT");
      System.out.println("----->  GENERATED EVENT");
      System.out.println(genEvent.toLundString());
      System.out.println("----->  RECONSTRUCTED EVENT");
      System.out.println(recEvent.toLundString());   
   }
}
