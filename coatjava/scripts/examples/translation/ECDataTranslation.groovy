import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.data.detector.*;
import org.jlab.clas.detector.*;
import org.jlab.evio.decode.*;
import org.jlab.clas.detector.DetectorType;
import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;

AbsDetectorTranslationTable table = new AbsDetectorTranslationTable();
table.readFile("CTOF.table");
System.out.println(table);


file = args[0];

EvioSource reader = new EvioSource();
reader.open(file);

EvioEventDecoder decoder = new EvioEventDecoder();
decoder.addTranslationTable(table);


int counter = 0;
while(reader.hasEvent()){
  EvioDataEvent event = reader.getNextEvent();


  System.out.println("---------> LISTING EVENT # " + counter);
  List<DetectorRawData>  rawdataALL = decoder.getDataEntries(event);  

  //decoder.decode(rawdataALL);

  for(DetectorRawData data : rawdataALL){
     System.out.println(" DATA SIZE = " + data.getDataSize());
     //System.out.println(data);
  }


  counter++;
}

System.out.println("processed " + counter);

