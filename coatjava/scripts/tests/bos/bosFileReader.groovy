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
/*
for(int loop = 0; loop < 4000; loop++){
  reader.getNextEvent();
}*/

while(reader.hasEvent()){
  reader.getNextEvent();
}
/*
reader.updateEventIndex();
reader.showIndex();*/