//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.utils.*;

outputFile = args[0];

boolean printBRANCHES = false;
boolean printCRATES   = true;


EvioSource  reader = new EvioSource();
reader.open(outputFile);

EvioRawEventDecoder  decoder = new EvioRawEventDecoder();
System.out.println(decoder);
BoardDecoderVSCM     board   = new BoardDecoderVSCM();

int icounter = 0;

while(reader.hasEvent()){

   icounter++;
   EvioDataEvent event = reader.getNextEvent();
   if(printBRANCHES==true){
      ArrayList<EvioTreeBranch> branches = decoder.getEventBranches(event);
      for(EvioTreeBranch branch : branches){
         System.out.println(branch);
      }
   }

   if(printCRATES==true){
	System.out.println("PRINTOUT CRATE DATA [] BUFFER # " + icounter);
	ArrayList<RawDataEntry>  rawEntries = decoder.getDataEntries(event);
	for(RawDataEntry entry : rawEntries){
	  System.out.println(entry);
	}
   }

   //decoder.list(event);
   int[] buffer = decoder.getRawBuffer(event,4);
   System.out.println("BUFFER LENGTH = " + buffer.length);
   board.decode(buffer);
   System.out.println("RECORDS LENGTH = " + board.getRecords().size());
   int nrecords = board.getRecords().size();
   for(BoardRecordVSCM record : board.getRecords()){
      //System.out.println(record);
      System.out.println(" RECORD = " + record.slotid + " " + record.chipid + "  " 
      + "  BCO " + record.bco + "  " + record.bcostart + "  " + record.bcostop +
       "  " + record.channel + "  " + record.adc +  "  " + record.hfcbid );
   }


}

reader.close();
