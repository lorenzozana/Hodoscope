//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;


filename = args[0];

EvioETSource reader = new EvioETSource("129.57.167.95");
reader.open("/tmp/et_sys_clasprod2");

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  event.show();
  counter++;
}
System.out.println("  procecessed " + counter + "  events");
