//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.utils.*;
import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;


outputFile = args[0];

H1D hPULSE = new H1D("hPULSE",100,0.0,100.0);

EvioSource  reader = new EvioSource();
reader.open(outputFile);

EvioRawEventDecoder  decoder = new EvioRawEventDecoder();

int icounter = 0;

while(reader.hasEvent()){

   icounter++;
   EvioDataEvent event = reader.getNextEvent();

   System.out.println("=============>> PROCESSING BUFFER # " + icounter);
	ArrayList<RawDataEntry>  rawEntries = decoder.getDataEntries(event);
	for(RawDataEntry entry : rawEntries){
	  System.out.println(entry);
	}

   for(RawDataEntry entry : rawEntries){
     //System.out.println(entry);
     int pedistal = entry.getIntegral(4,12);
     int pulse    = entry.getIntegral(25,60);
     System.out.println(" CRATE = " + entry.getCrate() + "  SLOT = " + entry.getSlot() +
      "  CHANNEL = "  +  entry.getChannel() + "  PEDISTAL = " + pedistal + "   PULSE = " + pulse);
     short[] rawpulse = entry.getRawPulse();
     for(int loop = 0; loop < rawpulse.length; loop++){
        double w = rawpulse[loop];
        hPULSE.fill(loop,w);
     }
   }

}

reader.close();


TCanvas c1 = new TCanvas("c1","JROOT Demo",800,800,1,1);
c1.setFontSize(14);
c1.cd(0);
c1.draw(hPULSE);
