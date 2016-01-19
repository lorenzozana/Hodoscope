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

H2D h2TDCL = new H2D("TDCL",48,-0.5,47.5,120,0.0,80000.0);
H2D h2TDCR = new H2D("TDCR",48,-0.5,47.5,120,0.0,80000.0);

int counter = 0;
while(reader.hasEvent()){
  EvioDataEvent event = reader.getNextEvent();
  //System.out.println("EVENT # " + counter);
  //decoder.list(event);


  List<DetectorRawData>  rawdataALL = decoder.getDataEntries(event);  
  decoder.decode(rawdataALL);

  //System.out.println("BEFORE SELECTION EVENT # " + counter);
  for(DetectorRawData data : rawdataALL){
     System.out.println(data);
  }

  //System.out.println("AFTER SELECTION EVENT # " + counter);
  List<DetectorRawData>  ctofdata = decoder.getDetectorData(rawdataALL, DetectorType.CTOF);
  Collections.sort(ctofdata);
  for(DetectorRawData data : ctofdata){
    //System.out.println(data);
    if(data.getDescriptor().getOrder()==3){
	int paddle  = data.getDescriptor().getComponent();
	int tdc     = data.getTDC();
	h2TDCR.fill(paddle,tdc);
	//System.out.println("TDC = " + data.getTDC());
    }
    if(data.getDescriptor().getOrder()==2){
        int paddle  = data.getDescriptor().getComponent();
        int tdc     = data.getTDC();
        h2TDCL.fill(paddle,tdc);
        //System.out.println("TDC = " + data.getTDC());
    }
  }

  counter++;
}

System.out.println("processed " + counter);

TGCanvas c1 = new TGCanvas("c1","CTOF TDC",600,600,1,2);
h2TDCL.setXTitle("CTOF Counter");
h2TDCL.setYTitle("TDC LEFT VALUE");

c1.cd(0);
c1.draw(h2TDCL);

h2TDCR.setXTitle("CTOF Counter");
h2TDCR.setYTitle("TDC RIGHT VALUE");

c1.cd(1);
c1.draw(h2TDCR);