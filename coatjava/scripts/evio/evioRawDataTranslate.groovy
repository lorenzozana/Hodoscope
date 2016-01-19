//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;


public class EvioDecoder {

    EvioRawDataSource    reader  =  new EvioRawDataSource();
    EvioRawEventDecoder  decoder =  new EvioRawEventDecoder();

    public EvioDecoder(){
       
       AbsDetectorTranslationTable  table = new AbsDetectorTranslationTable(){

       	   @Override
	   public Integer getSector(int crate, int slot, int channel){
	     System.out.println(" LOOKING FOR " + crate + " " + slot + " " + channel);
	     if(hasEntry(crate,slot,channel)){
	        return getEntry(crate,slot,channel).sector;		
	     }
	     return -1;
	   }

	   @Override
           public Integer getLayer(int crate, int slot, int channel){
             if(hasEntry(crate,slot,channel)){
		return getEntry(crate,slot,channel).layer;
             }
             return -1;
           }

	   @Override
           public Integer getComponent(int crate, int slot, int channel){
             if(hasEntry(crate,slot,channel)){
                return getEntry(crate,slot,channel).component;
             }
             return -1;
           }
       }

       table.readFile("SVT.table");
       table.setName("SVT");
       decoder.addTranslationTable(table);          
    }

    public void process(String input, String output){

       Integer maxEvents = 100;
       reader.open(input);
       int counter = 0;
       while(reader.hasEvent()==true && counter<maxEvents){
          counter++;
	  System.out.println(" ---------------------->  EVENT # " + counter);
	  EvioDataEvent  evioEvent = reader.getNextEvent();
	  List<RawDataEntry> dataEntries = reader.getDataEntries(evioEvent);
	  decoder.decode(dataEntries);
	  if(dataEntries != null){
	     for(RawDataEntry data : dataEntries){
	        System.out.println(data);
	     }
	  }
       }
    }

    public static main(String[] args){
       String inputFile  = args[0];    	   
       EvioDecoder evDecode = new EvioDecoder();
       evDecode.process(inputFile,"myoutput");
    }
}
