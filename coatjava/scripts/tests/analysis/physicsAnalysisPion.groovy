//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import org.jlab.clas.physics.*;
import org.root.histogram.*;
import org.root.pad.*;
import org.root.func.*;
import org.jlab.clas12.physics.*;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
EventFilter  filter = new EventFilter("11:211:-211:X+:X-:Xn");
int counter = 0;

H1D H100 = new H1D("H100",120,0.0, 1.2);

while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event     = reader.getNextEvent();
	PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
	PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	if(filter.isValid(recEvent)==true){
	   Particle pionm = recEvent.getParticle("[-211]");
	   Particle pionp = recEvent.getParticle("[211]");
	   Particle proton = recEvent.getParticle("[b]+[t]-[11]-[211]-[-211]");
	   if(pionp.p()>1.0&&pionm.p()>1.0){
	     H100.fill(proton.mass());
	   }
	   //System.err.println("--------------> event start");
	   //System.out.println(genEvent.toLundString());
	   //System.out.println(recEvent.toLundString());
	}
}


F1D func = new F1D("gaus+p2",0.7,1.0);
func.setParameter(0,100);
func.setParameter(1,0.938);
func.setParameter(2,0.1);
H100.fit(func);

TCanvas c1 = new TCanvas("c1","Physics Analysis",800,600,1,1);
c1.cd(0);
c1.draw(H100);
c1.draw(func,"same");
