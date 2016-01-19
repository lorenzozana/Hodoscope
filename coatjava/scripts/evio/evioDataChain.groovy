//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;


EvioDataChain reader = new EvioDataChain();
/*
for(String filename : args){
   reader.addFile(filename);
}
*/
reader.addDir("../../../data/");
reader.open();

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  counter++;
}

System.out.println("  procecessed " + counter + "  events");
