//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;


filename = args[0];
EvioSource reader = new EvioSource();
reader.open(filename);

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  //EvioDataBank bank = event.getBank("HEADER::info");
  //bank.show();
  event.show();
  counter++;
}
System.out.println("  procecessed " + counter + "  events");
