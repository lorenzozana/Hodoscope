//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;

EvioDataSync writer = new EvioDataSync();
writer.open("output_file.evio");

int counter = 0;
for(int loop = 0; loop < 2500; loop++){

   EvioDataEvent  event = writer.createEvent();
   EvioDataBank   bank  = event.getDictionary().createBank("DC::dgtz",8);
   event.appendBanks(bank);
   writer.writeEvent(event);
   counter++;
}

writer.close();
System.out.println("  procecessed " + counter + "  events");
