//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;

outputFile = args[0];

EvioRawDataSource  reader = new EvioRawDataSource();
reader.open(outputFile);

TranslationTableSVT  svtTR = new TranslationTableSVT();
svtTR.readFile("../../etc/bankdefs/translation/SVT.table.run882");

while(reader.hasEvent()){
   EvioDataEvent event = reader.getNextEvent();
   //reader.list(event);
   List<RawDataEntry> banks = reader.getDataEntries(event,4);
   if(banks!=null){
    System.out.println("----------------->  EVENT START  SIZE = " + banks.size());
    svtTR.translateEntries(banks);
    for(RawDataEntry entry : banks){
       System.out.println(entry);
    }
  }
   //System.out.println(bank);
}

reader.close();
