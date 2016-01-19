#!/usr/bin/groovy

////=========================================================================
// 
//=========================================================================

package monitoring.dc;

import org.jlab.clasrec.main.*; 
import org.jlab.clasrec.utils.*;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.rec.*;
import org.jlab.clas.physics.*;
import org.root.group.*;
import org.root.group.*;
import org.root.pad.*;
import org.root.histogram.*;

class CLASMonitoringSEB extends DetectorMonitoring {
    private PlotGroup hSECT_ADC;

    public CLASMonitoringSEB(){
       super("SEBMON","1.0","gavalian");
    }

    public void init(){
      hSECT_ADC = new PlotGroup("FTOF1A_ADC",2,2);
      hSECT_ADC.add("SECTOR_1", new H1D("SECTOR_1",200,-2000.0,2000.0));
      hSECT_ADC.add("SECTOR_2", new H1D("SECTOR_2",200,0.0,200.0));
      hSECT_ADC.add("SECTOR_3", new H1D("SECTOR_3",200,0.0,200.0));
      hSECT_ADC.add("SECTOR_4", new H2D("SECTOR_4",200,-2000.0,2000.0,200,-2000.0,2000.0));
      hSECT_ADC.addDescriptor(0,"SECTOR_1");
      hSECT_ADC.addDescriptor(1,"SECTOR_3");
      hSECT_ADC.addDescriptor(2,"SECTOR_4");
      //hSECT_ADC.addDescriptor(1,"SECTOR_2");
      //hSECT_ADC.addDescriptor(2,"SECTOR_3");

      addGroup(hSECT_ADC);
    }

    void analyze(){

    }
    
    void processEvent(EvioDataEvent event){
        //if(event.hasBank("GenPart::true") && event.hasBank("EVENTHB::particle")){
	if(event.hasBank("FTOF1A::dgtz")){
		//System.out.println("--- found one ---");
	    EvioDataBank bank = event.getBank("FTOF1A::dgtz");
	    int rows = bank.rows();
	    for(int loop = 0; loop < rows; loop++){
	        int adcl = bank.getInt("ADCL",loop);
	        int tdcr = bank.getInt("TDCR",loop);
	        int tdcl = bank.getInt("TDCL",loop);
	        int adcr = bank.getInt("ADCR",loop);
		//System.out.println("--- found one --- " + tdcl + " " + tdcr + " " + (tdcl+tdcr));
		if(adcr!=0&&adcl!=0){
			fill("FTOF1A_ADC","SECTOR_1",(float) tdcl-tdcr);
			fill("FTOF1A_ADC","SECTOR_2",(float) adcr);
			fill("FTOF1A_ADC","SECTOR_3",(float) adcr + adcl);
			fill("FTOF1A_ADC","SECTOR_4",(float) tdcr - tdcl , (float) tdcl - tdcr);
		}
	    }
    	}
    }
    
    void configure(ServiceConfiguration config){

    }

    public static void main(String[] args){
        if(args.length>0){
            String inputFile = args[0];
            System.err.println(" \n[PROCESSING FILE] : " + inputFile);
            CLASMonitoringSEB  dcrecMonitor = new CLASMonitoringSEB();
            dcrecMonitor.init();
            CLASMonitoring  monitor = new CLASMonitoring(inputFile, dcrecMonitor);
	    monitor.process();

	    //TCanvas c1 = new TCanvas("c1","FTOF Monitoring",800,800,2,3);
	    //c1.draw(dcrecMonitor.getDirectory().getGroup("FTOF1A_ADC"));
	    
            DirectoryViewer browser = new DirectoryViewer(dcrecMonitor.getDirectory());
            
        }
    }
}

