//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.bos.clas6.*;
import org.jlab.data.utils.*;

String inputfile = args[0];

BosDataSource reader = new BosDataSource();
reader.open(inputfile);

Bos2EvioPartBank convertor = new Bos2EvioPartBank();

int icounter = 0;
while(reader.hasEvent()){
   icounter++;
   if(icounter%10000==0) System.out.println("processed " + icounter);
   BosDataEvent event = reader.getNextEvent();
   convertor.processBosEvent(event);
}

