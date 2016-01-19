//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.io.ClasEvioReader
import org.jlab.clas.physics.*

String inputFile = args[0];

EventFilter  filter = new EventFilter("11:2212:2n");

ClasEvioReader  reader = new ClasEvioReader();

reader.addFile(inputFile);
reader.open()

int icounter = 0;
while(reader.next()==true){
	icounter++;	
	PhysicsEvent physEvent = reader.getPhysicsEvent();
	if(filter.isValid(physEvent)==true){
		System.err.print(physEvent.toLundString());
	}
}

System.err.println("Processed events = " + icounter);
