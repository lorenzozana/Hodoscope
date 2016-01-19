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


while(reader.hasEvent()){
   EvioDataEvent event = reader.getNextEvent();
   reader.list(event);
   List<RawDataEntry> banks = reader.getDataEntries(event);
   if(banks!=null){
    System.out.println("----------------->  EVENT START  SIZE = " + banks.size());    
    for(RawDataEntry entry : banks){
       System.out.println(entry);
       int tdc = entry.getTDC();
       int adc = entry.getADC();
       int crate = entry.getCrate();
       int slot  = entry.getSlot();
       int chan  = entry.getChannel();
       int pulseMin = entry.getPulseMin();
       int pulseMax = entry.getPulseMax();
       if(entry.getMode()==7){
	System.out.print(" MY PRINTOUT ---> ");
      	System.out.println(crate + " " + slot + " " + chan + "  " + adc + "  " + tdc);
       }
    }
  }
   //System.out.println(bank);
}

reader.close();
