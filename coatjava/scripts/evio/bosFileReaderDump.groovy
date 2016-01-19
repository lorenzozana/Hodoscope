//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.bos.clas6.*;
import org.jlab.data.utils.*;

String inputfile = args[0];
String eventnum  = args[1];

Integer nevent  = Integer.parseInt(eventnum);
BosDataSource reader = new BosDataSource();
reader.open(inputfile);

int eventNumberOLD = 0;

int icounter = 0;
int fileCounter = 0;
int dumpcounter = 0;

Bos2EvioEventBank  convertor = new Bos2EvioEventBank();

while(reader.hasEvent()){

   icounter++;
   if(icounter%10000==0) System.out.println("processed " + icounter);
   
   BosDataEvent event = reader.getNextEvent();
   int NEvent = event.getEventNumber();
   if(event.hasBank("TAGR:1")==true){
      BosDataBank tagr = event.getBank("TAGR:1");
      tagr.show();
   }
   //convertor.processBosEvent(event);
   if(NEvent==nevent){
         event.dumpBufferToFile();
	 event.showBankInfo("EVNT");
	 BosDataBank bankEVNT = event.getBank("EVNT");
	 BosDataBank bankHEVT = event.getBank("HEVT");
	 bankEVNT.show();
	 bankHEVT.show();
	 BosDataBank bankHEAD = event.getBank("HEAD");
	 bankHEAD.show();
   }
   //System.out.println("event number = " + NEvent);
   /*
   if(event.hasBank("HEAD")==true){
      BosDataBank HEAD = event.getBank("HEAD");
      int NEVNT = HEAD.getInt("NEVENT")[0];
      if(NEVNT==nevent&&dumpcounter==0){
         event.dumpBufferToFile();
	 dumpcounter++;
      }
   }*/
}

