//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.bos.clas6.*;
import org.jlab.data.utils.*;

String inputfile = args[0];
String minevt_s  = args[1];
String maxevt_s  = args[2];

Integer minevent  = Integer.parseInt(minevt_s);
Integer maxevent  = Integer.parseInt(maxevt_s);

BosDataSource reader = new BosDataSource();
reader.open(inputfile);

int eventNumberOLD = 0;

int icounter = 0;
int fileCounter = 0;

Bos2EvioEventBank  convertor = new Bos2EvioEventBank();

int OLD_EVENT_NUMBER = 0;

String[] bankList = ["EVNT","SCPB","CCPB","HEVT"];

while(reader.hasEvent()){

   icounter++;
   if(icounter%10000==0) System.out.println("processed " + icounter);
   
   BosDataEvent event = reader.getNextEvent();
   System.out.println("-----------------> EVENT NUMBER # " + icounter);
   for(int loop = 0; loop < bankList.length; loop++){
      if(event.hasBank(bankList[loop])==true){
         BosDataBank bank = event.getBank(bankList[loop]);
	 bank.show();
      }
   }
}

