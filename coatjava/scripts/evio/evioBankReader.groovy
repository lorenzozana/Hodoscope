//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.coda.jevio.*;

filename = args[0];
EvioSource reader = new EvioSource();
reader.open(filename);

int counter = 0;
while(reader.hasEvent()==true){
  EvioDataEvent  event = reader.getNextEvent();
  // System.out.println("  LOOKING FOR BANK = " +  event.hasBank("TimeBasedTrkg::TBTracks"));
  System.out.println("--------------------->  event # " + counter);
/*
  EvioNode root = event.getHandler().getRootNode(1320,0,DataType.ALSOBANK);
  if(root==null) event.getHandler().list();
  if(root!=null){
     EvioNode child = event.getHandler().getChildNode(root,1325,0,DataType.ALSOBANK);
     if(child==null) event.getHandler().list();
     if(child!=null) {
        System.out.println("  ********>>>>> FOUND BANK ");
     }
  }*/
  System.out.println("  LOOKING FOR BANK = " +  event.hasBank("TimeBasedTrkg::TBTracks"));
  //System.out.println( event.getHandler().getRootNode(1320,0,DataType.ALSOBANK));
  counter++;
}
System.out.println("  procecessed " + counter + "  events");
