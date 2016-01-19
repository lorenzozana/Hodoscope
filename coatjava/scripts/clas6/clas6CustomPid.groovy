//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.clas12.physics.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.detector.DetectorType;

public class EG2ElectronSelection implements IDetectorEventProcessor {
	
	public void processDetectorEvent(DetectorEvent event){

		if(event.getParticles().size()==0) return;

		DetectorResponse  ecin  = event.getParticles().get(0).getResponse(DetectorType.EC,0);
        DetectorResponse  ecout = event.getParticles().get(0).getResponse(DetectorType.EC,1);
        DetectorResponse  ectot = event.getParticles().get(0).getResponse(DetectorType.EC,2);
        
         if(ecin!=null&&ecout!=null&&ectot!=null){
             double momentum = event.getParticles().get(0).vector().mag();
             double samplingFraction = ectot.getEnergy()/momentum;
             if(ecin.getEnergy()>0.05&&ecout.getEnergy()>0.05&&
                     samplingFraction>0.27){
                 event.getParticles().get(0).setPid(11);
             } else {
                 event.getParticles().get(0).setPid(0);
             }
         }
	}


	public static void main(String[] args){
		
		String inputfile = args[0];
		GenericKinematicFitter  sebFitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
		GenericKinematicFitter  eg2Fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
		
		EventFilter    eventFilter = new EventFilter("11:2212:22:22");

		EG2ElectronSelection  electronSelection = new EG2ElectronSelection();

		eg2Fitter.addEventProcessor(electronSelection);

		EvioSource reader = new EvioSource();
		reader.open(inputfile);

		int counterSEB = 0;
		int counterEG2 = 0;
		int counter    = 0;

		while(reader.hasEvent()==true){
  			EvioDataEvent  event = reader.getNextEvent();
  			
  			PhysicsEvent  eventSEB = sebFitter.getPhysicsEventProcessed(event);
  			PhysicsEvent  eventEG2 = eg2Fitter.getPhysicsEventProcessed(event);

  			//System.out.println(detectorEvent);
  			if(eventFilter.isValid(eventSEB)==true){
  				counterSEB++;
  			}

  			if(eventFilter.isValid(eventEG2)==true){
  				counterEG2++;
  			}
  			counter++;
		}

		System.out.print  (" processed events " + counter);
		System.out.print  ("  SEB (11:2212:22:22) # = " + counterSEB );
		System.out.println("  EG2 (11:2212:22:22) # = " + counterEG2 );
	}
}
