import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.data.detector.*;

public class DCTranslationTable extends AbsDetectorTranslationTable {


  int[] crate_sector = [1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6];
  int[] crate_region = [1,2,3,1,2,3,1,2,3,1,2,3,1,2,3,1,2,3];

  int[] chan_loclayer =[2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,
      2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,
      2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,
      2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,
      2,4,6,1,3,5];

  int[] chan_locwire = [1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3,
      4,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6,6,6,7,7,7,7,7,7,
      8,8,8,8,8,8,9,9,9,9,9,9,10,10,10,10,10,10,
      11,11,11,11,11,11,12,12,12,12,12,12,13,13,13,13,13,13,
      14,14,14,14,14,14,15,15,15,15,15,15,16,16,16,16,16,1];

  int[] slot_stb = [0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0];
  int[] slot_locsuplayer = [0,0,0,1,1,1,1,1,1,1,0,0,2,2,2,2,2,2,2,0];


  public DCTranslationTable(){
    super("DC",100); // This defines name of the detector and tag=900 for final bank
    // TAG is uded by automated convertor to create event with proper structure
  }

  @Override
  public Integer getSector(int crate, int slot, int channel){
      //System.out.println("CRATE = " + crate + "  sector = " + crate_sector[crate-67]);
     return crate_sector[crate-67];
  }

  @Override
  public Integer getLayer(int crate, int slot, int channel){
    
    int region = crate_region[crate-67];
    int loclayer=chan_loclayer[channel];
    int locsuplayer=slot_locsuplayer[slot];
    int suplayer=(region-1)*2 + locsuplayer;
    int layer=(suplayer)*6 + loclayer;
    //System.out.println(region + " " + loclayer + " " + locsuplayer + " " + suplayer);
    return layer;
    //return 1;
  }

  @Override
  public Integer getComponent(int crate, int slot, int channel){
     int  nstb=slot_stb[slot];
     int wire=(nstb)*16+1;
     return wire;
     //return 0;
  }

  /*
  * MAIN PROGRAM
  */
  public static void main(String[] args){
    String filename = args[0];

    EvioRawEventDecoder    decoder = new  EvioRawEventDecoder();
      DCTranslationTable     table   = new DCTranslationTable();
    //AbsDetectorTranslationTable     table   = new AbsDetectorTranslationTable();

    EvioSource             reader  = new EvioSource();
    reader.open(filename);
    int counter = 0;

    while(reader.hasEvent()){
      EvioDataEvent event = reader.getNextEvent();
      List<RawDataEntry> dataEntries = decoder.getDataEntries(event);
      System.out.println("\n\n***************************************************************");
      System.out.println("=======> PROCESSING EVENT BUFFER # " + counter);
      System.out.println("================>  RAW DATA ENTRIES");
      if(dataEntries!=null){
        for(RawDataEntry data : dataEntries){
          //System.out.println(data);
        }
      }
      
      System.out.println("================>  DECODED DATA ENTRIES");
      List<RawDataEntry>  dcdata = decoder.getDecodedData(dataEntries,table);
      for(RawDataEntry  entry : dcdata){
          System.out.println(entry);
      }

    }

  }
}