//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;


String filename = args[0];
String nevent_s   = args[1];
Integer nevent    = Integer.parseInt(nevent_s);

EvioDataChain reader = new EvioDataChain();

reader.addFile(filename);

//reader.addFile("../../../generator/chain/generated_gemc.2.evio");
//reader.addFile("../../../generator/chain/generated_gemc.3.evio");
//reader.addFile("../../../generator/chain/generated_gemc.4.evio");


reader.open();

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  if(event.hasBank("HEADER::info")==true){
     EvioDataBank  bank = event.getBank("HEADER::info");
     if(nevent==bank.getInt("nevt",0)){
       bank.show();
     }
     //bank.show();
     //System.out.println(bank.getInt("nrun",0) + "  " + bank.getInt("nevt",0));
  }
  counter++;
}

System.out.println("  procecessed " + counter + "  events");
