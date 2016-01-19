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
   event.getBankMultiIndex("HEVT",0);

   event.showBankInfo("SCPB");
   event.showBankInfo("EVNT");
   event.showBankInfo("TGBI");
   //byte[] buffer = event.getBankData("HEVT",0);
   //System.out.println(" BUFFER SIZE =  " + buffer.length);

   if(event.hasBank("HEVT")==true){
     BosDataBank bank = event.getBank("HEVT");
     bank.show();
   }


   if(event.hasBank("EVNT")==true){
     BosDataBank bank = event.getBank("EVNT");
     bank.show();
   }

   /*
   System.out.println("----------------------->   BUFFER NUMBER " + icounter);
   event.showBank("HEAD",0);
   event.showBank("HEVT",0);
   event.showBank("EVNT",0);
   event.showBank("ECPB",0);
   
   BosDataBank evntBank = event.getBank("EVNT");
   evntBank.show();
   if(event.hasBank("EVNT")==true&&event.hasBank("HEVT")==false){
     event.dumpBufferToFile();
   }
   if(event.hasBank("EVNT")==true&&event.hasBank("ECPB")==false){
     event.dumpBufferToFile();
   }*/
   /*
   if(event.hasBank("EVNT")==false&&event.hasBank("HEVT")==true){
      BosDataBank bank = event.getBank("HEVT");
      int[] nevents = bank.getInt("NEVENT");
      System.out.println(" has bank " + nevents[0] );
      if(nevents[0]==553183){
         reader.dumpBufferToFile("sample.bos");
      }
   }*/
   /*
   if(event.hasBank("HEVT")==true){
	BosDataBank bank = event.getBank("HEVT");
	int[] nevents = bank.getInt("NEVENT");
	if(nevents[0]==eventNumberOLD) System.out.println(" SAME EVENT NUMBER");
   	System.out.println(" has bank " + nevents[0] + "  " +  event.hasBank("EVNT") + " " + event.hasBank("HEVT") + " " + "  ");
   	eventNumberOLD = nevents[0];
   }*/



}

