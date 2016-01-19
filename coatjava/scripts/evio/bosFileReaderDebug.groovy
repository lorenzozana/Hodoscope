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

int eventNumberOLD = 0;

int icounter = 0;
int fileCounter = 0;

while(reader.hasEvent()){

   icounter++;
   if(icounter%10000==0) System.out.println("processed " + icounter);
   BosDataEvent event = reader.getNextEvent();
   System.out.println("----------------------->   BUFFER NUMBER " + icounter);
   if(event.hasBank("EVNT")==true){
      BosDataBank EVNT = event.getBank("EVNT");
      //EVNT.show();
   }
   System.out.println(event.hasBank("HEVT"));
   System.out.println(event.hasBank("EVNT"));
   System.out.println(event.hasBank("SCPB"));

   if(event.hasBank("HEVT")==true){
     //event.dumpBufferToFile();
      BosDataBank HEVT = event.getBank("HEVT");
      HEVT.show();
   }

   if(event.hasBank("SCPB")==true){
     //event.dumpBufferToFile();
      BosDataBank HEVT = event.getBank("SCPB");
      HEVT.show();
   }

   if(event.hasBank("CCPB")==true){
     //event.dumpBufferToFile();
      BosDataBank HEVT = event.getBank("CCPB");
      HEVT.show();
   }
   
   // + "  " + event.hasBank("EVNT") + "  " + event.hasBank("SCPB"));
   /*
   List<BosBankStructure> structures = event.getBankStructures("EVNT",0);
   if(structures.size()>1){
     BosBankStructure bank = BosBankStructure.combine(structures.get(0),structures.get(1));
     System.out.println("----------------------->   BUFFER NUMBER " + icounter);
     System.out.println(bank);
   }*/
}

