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
   if(event!=null){
     System.out.println(" EVENT IS FINE ");
   }

    event.dumpBufferToFile();
    event.showBankInfo("EVNT");
	 BosDataBank bankEVNT = event.getBank("EVNT");
	 BosDataBank bankHEVT = event.getBank("HEVT");
	 bankEVNT.show();
	 bankHEVT.show();
	 BosDataBank bankHEAD = event.getBank("HEAD");
	 bankHEAD.show();

}


