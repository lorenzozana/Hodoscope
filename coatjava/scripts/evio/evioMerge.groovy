//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;

outputFile = args[0];
inputDir   = args[1];

List<String> fileList = FileUtils.getFilesInDir(inputDir);
EvioDataSync  writer = new EvioDataSync();
writer.open(outputFile);
int icounter = 0;

for(String file : fileList){
   EvioSource reader = new EvioSource();
   reader.open(file);
   while(reader.hasEvent()){
      icounter++;
      if(icounter%1000==0){
         System.out.println("processed events = " + icounter);
      }
      EvioDataEvent event = reader.getNextEvent();
      //if(event.hasBank("DC::dgtz")==true){
      writer.writeEvent(event);
      //}
   }
   System.out.println("FILE -> " + file);
}

writer.close();

