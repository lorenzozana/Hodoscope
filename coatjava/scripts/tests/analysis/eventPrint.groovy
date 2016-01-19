//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.clasrec.io.ClasEvioReader
import org.jlab.clas.physics.PhysicsEvent
import org.jlab.clas.physics.Particle

String inputFile = args[0];

ClasEvioReader  reader = new ClasEvioReader();
//reader.addFile("../../../rec_output.evio")
reader.addFile(inputFile);
reader.open()

int icounter = 0;
while(reader.next()==true){
	icounter++;	
	PhysicsEvent physEvent = reader.getPhysicsEvent();
	System.err.print(physEvent.toLundString());
}

System.err.println("Processed events = " + icounter);
