import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.evio.decode.*;
import org.jlab.clas.detector.*;
import org.jlab.clas12.detector.*;

input = args[0];


EvioSource  reader = new EvioSource();
reader.open(input);
EventDecoder decoder = new EventDecoder();
int icounter = 0;

while(reader.hasEvent()){

   icounter++;
   EvioDataEvent event = (EvioDataEvent) reader.getNextEvent();
   System.out.println("---------------> event  # " + icounter);
   decoder.decode(event);
   // The name FTOF1A comes from TRANSLATION TABLE (look below)
   // For other detectors use decoder.getDataEntries("PCAL") for example
   List<DetectorBankEntry> counters =  decoder.getDataEntries();
   // The entire list of decoded data can be obtained by:
   // List<DetectorBankEntry> counters =  decoder.getDataEntries();
   //decoder.getDetectorCounters(DetectorType.BMT);

   for(DetectorBankEntry cnt : counters){
      System.out.println(cnt);
   }
}
System.out.println("done...");
decoder.showTimer();
