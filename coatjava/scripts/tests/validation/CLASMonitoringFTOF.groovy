//=========================================================================
// 
//=========================================================================

package monitoring;

import org.jlab.clasrec.main.*;
import org.jlab.clasrec.utils.*;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.rec.*;
import org.jlab.scichart.canvas.*;
import javax.swing.JFrame;

class CLASMonitoringFTOF extends DetectorMonitoring {

	public CLASMonitoringFTOF(){
	   super("FTOF","1.0","gavalian");
	}

	public void init(){
		for(int loop = 0; loop < 23; loop++){
			String hname = "PADDLE_" + (loop+1);
			add("ADCL",hname,200,-5,200.0);
		}

		for(int loop = 0; loop < 6; loop++){
			String hnameTime = "TIME_SECTOR_" + (loop);
			add("FTOFHITS",hnameTime,100,15.0,65.0);
			String hnameEnergy = "ENERGY_SECTOR_" + (loop);
			add("FTOFHITS",hnameEnergy,100,-0.1,0.4);
		}
		System.err.println("***********************************");
		getDirectory().show();
		System.err.println("***********************************");
	}

	void analyze(){

	}

	void processEvent(EvioDataEvent event){
		if(event.hasBank("FTOF1A::dgtz")){
			EvioDataBank bank = event.getBank("FTOF1A::dgtz");
			int rows = bank.rows();
			for(int loop = 0; loop < rows; loop++){
				int paddle = bank.getInt("paddle",loop);
				int adcL   = bank.getInt("ADCL",loop);
				int adcR   = bank.getInt("ADCR",loop);
				String hname = "PADDLE_" + paddle;
				if(adcL>20) fill("ADCL",hname,adcL);
			}
		}

		if(event.hasBank("FTOFRec::ftofhits")){
			EvioDataBank bank = event.getBank("FTOFRec::ftofhits");
			int rows = bank.rows();
			for(int loop = 0; loop < rows; loop++){
				int sector   = bank.getInt("sector",loop);
				float time   = bank.getFloat("time",loop);
				float energy = bank.getFloat("energy",loop);
				String hnameTime = "TIME_SECTOR_" + sector;
				fill("FTOFHITS",hnameTime,time);
				String hnameEnergy = "ENERGY_SECTOR_" + sector;
				fill("FTOFHITS",hnameEnergy,energy);

			}
		}
	}

	void configure(ServiceConfiguration config){

	}

	public static void main(String[] args){
		if(args.length>0){
			String inputFile = args[0];
			System.err.println(" \n[PROCESSING FILE] : " + inputFile);
			CLASMonitoringFTOF  ftofMonitor = new CLASMonitoringFTOF();
			ftofMonitor.init();
			CLASMonitoring  monitor = new CLASMonitoring(inputFile, ftofMonitor);
			monitor.process();

			JFrame frame = new JFrame();
			DirectoryBrowser browser = new DirectoryBrowser(ftofMonitor.getDirectory(),2,6);
			frame.add(browser);
			frame.pack();
			frame.setVisible(true);
		}
		System.err.println(" \n HELLO MONITORING \n");
	}
}

