//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import org.jlab.clas.physics.*;
import org.root.histogram.*;
import org.root.pad.*;
import org.jlab.clas12.physics.*;

filename = args[0];

EventFilter  filter = new EventFilter("11:X+:X-:Xn");

EvioSource reader = new EvioSource();
reader.open(filename);


GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
int counter = 0;
int protoncount = 0;
int protoncountEVNT = 0;


while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	PhysicsEvent  physEvent = fitter.getPhysicsEvent(event);
	if(filter.isValid(physEvent)==true){
	protoncount += physEvent.countByPid(2212);
	EvioDataBank bank = event.getBank("EVENT::particle");
	for(int loop =0; loop < bank.rows(); loop++){
	   if(bank.getInt("pid",loop)==2212){
	     if(bank.getByte("dcstat",loop)>0&&bank.getByte("scstat",loop)>0&&bank.getByte("status",loop)>0){
	       protoncountEVNT++;
	     }
	   }
	}
	}
}

System.out.println("protons = " + protoncount + "  " + protoncountEVNT);

