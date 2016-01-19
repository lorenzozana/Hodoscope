//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.clas12.physics.*;

filename = args[0];
EvioSource reader = new EvioSource();
reader.open(filename);

GenericKinematicFitter  fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  DetectorEvent  detectorEvent = fitter.getDetectorEvent(event);
  System.out.println(detectorEvent);
  counter++;
}
System.out.println("  procecessed " + counter + "  events");
