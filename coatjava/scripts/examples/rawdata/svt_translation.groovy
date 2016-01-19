import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.data.detector.*;

public class SVTTranslationTable extends AbsDetectorTranslationTable {

  public SVTTranslationTable(){
    super("BST",100); // This defines name of the detector and tag=900 for final bank
    // TAG is uded by automated convertor to create event with proper structure
  }

  public int getChipID(int channel){
    return (channel & 0x00000700)>>8;
  }

  public int getChannel(int channel){
    return (channel & 0x0000007F);
  }

  public int getHalf(int channel){
    return (channel & 0x00000800)>>11;
  }

  @Override
  public Integer getSector(int crate, int slot, int channel){

    int ch     = this.getChannel(channel);
    int half   = this.getHalf(channel);
    int chipID = this.getChipID(channel);

    if(this.hasEntry(crate,slot,half)==true){
      return this.getEntry(crate,slot,half).sector;
    }
    return -1;
  }

  @Override
  public Integer getLayer(int crate, int slot, int channel){
    int ch     = this.getChannel(channel);
    int half   = this.getHalf(channel);
    int chipID = this.getChipID(channel);
    if(this.hasEntry(crate,slot,half)==true){
      int sector = this.getEntry(crate,slot,half).sector;
      int layer  = this.getEntry(crate,slot,half).layer;
      int comp  = this.getEntry(crate,slot,half).component;
      return layer;
    }
    return -1;
  }

  @Override
  public Integer getComponent(int crate, int slot, int channel){
    int ch     = this.getChannel(channel);
    int half   = this.getHalf(channel);
    int chipID = this.getChipID(channel);
    if(this.hasEntry(crate,slot,half)==true){
      int sector = this.getEntry(crate,slot,half).sector;
      int layer  = this.getEntry(crate,slot,half).layer;
      int comp  = this.getEntry(crate,slot,half).component;
      return ch+chipID*2;
    }
    return -1;
  }

  /*
  * MAIN PROGRAM
  */
  public static void main(String[] args){
    String filename = args[0];

    EvioRawEventDecoder    decoder = new  EvioRawEventDecoder();
    EvioRawDataSource       reader = new EvioRawDataSource();
    SVTTranslationTable    table   = new SVTTranslationTable();
    table.readFile("SVT.table");

    reader.open(filename);
    int counter = 0;
    while(reader.hasEvent()){
      EvioDataEvent event = reader.getNextEvent();
      List<RawDataEntry> dataEntries = reader.getDataEntries(event);
      System.out.println("\n\n***************************************************************");
      System.out.println("=======> PROCESSING EVENT BUFFER # " + counter);
      System.out.println("================>  RAW DATA ENTRIES");
      if(dataEntries!=null){
        for(RawDataEntry data : dataEntries){
          System.out.println(data);
        }
      }
      System.out.println("================>  DECODED DATA ENTRIES");
      List<RawDataEntry>  svtdata = decoder.getDecodedData(dataEntries,table);
      for(RawDataEntry  entry : svtdata){
          System.out.println(entry);
      }
      DetectorDataBank  bankSVT = decoder.getDetectorBank("BST",svtdata);
      System.out.println("DETECTOR BANK : \n" + bankSVT); 
      counter++;
    }

  }


}