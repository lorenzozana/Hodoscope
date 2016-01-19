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
  if(event.hasBank("GenPart::true")==true){
  	EvioDataBank bank = event.getBank("GenPart::true");
  	bank.show();
  	counter++;
  }
}
System.out.println("  procecessed " + counter + "  events");
