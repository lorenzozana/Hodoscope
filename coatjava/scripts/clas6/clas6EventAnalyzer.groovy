//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.clas12.physics.*;
import org.jlab.clas12.physics.*;
import org.jlab.clas.physics.*;

filename = args[0];
EvioSource reader = new EvioSource();
reader.open(filename);

GenericKinematicFitter  fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event     = reader.getNextEvent();
  PhysicsEvent   physEvent = fitter.getPhysicsEventProcessed(event);
  //System.out.println(detectorEvent);
  System.out.println(physEvent.toLundString());
  counter++;
}
System.out.println("  procecessed " + counter + "  events");
