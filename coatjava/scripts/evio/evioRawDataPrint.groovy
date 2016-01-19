//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.evio.decode.*;
import org.jlab.clas.detector.*;

outputFile = args[0];

EvioSource  reader = new EvioSource();
reader.open(outputFile);

EvioEventDecoder decoder = new EvioEventDecoder();

int counter = 0;

while(reader.hasEvent()){

   EvioDataEvent event = reader.getNextEvent();   
   event.getHandler().list();

   List<DetectorRawData> rawData = decoder.getDataEntries(event);
   for(DetectorRawData data : rawData){
      //System.out.println(data);
   }
   counter++;
}

reader.close();
