//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-12 detector
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.rec.CLASReconstruction;
import org.jlab.clasrec.main.DetectorReconstruction;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.utils.*;

public class HTCCReconstruction extends DetectorReconstruction
{

    public HTCCReconstruction(){
       super("HTCC","me","1.0");
    }

    @Override
    public void processEvent(EvioDataEvent event){
       System.out.println("reconstructing HTCC event");
       if(event.hasBank("HTCC::dgtz")){
          EvioDataBank bank = event.getBank("HTCC::dgtz");
	  int rows = bank.rows();
	  bank.show();
       }
    }

    @Override
    public void init(){
    }

    @Override
    public void configure(ServiceConfiguration c){
    }

    public static void main(String[] args){

       String filename = args[0];

       CLASReconstruction   rec = new CLASReconstruction();

       // Initialize standard services (DCHB,DCTB,EC).

       rec.addDetector("org.jlab.rec.dc.services.HitBasedTracking");
       rec.addDetector("org.jlab.rec.dc.services.TimeBasedTracking");
       rec.addDetector("org.jlab.rec.ec.ECReconstruction");

       HTCCReconstruction  htcc = new HTCCReconstruction();
       rec.addDetector(htcc);

       rec.init();
       rec.run(filename,"output.evio");

    }
    
}
