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

EventFilter  filter = new EventFilter("11:211:-211:X+:X-:Xn");

EvioSource reader = new EvioSource();
reader.open(filename);

H1D H100 = new H1D("H100","M [GeV]","",60,0.25,0.8);

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
int counter = 0;


while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	PhysicsEvent  physEvent = fitter.getPhysicsEvent(event);
	if(filter.isValid(physEvent)==true){
	    Particle pipi = physEvent.getParticle("[211]+[-211]");
	    //System.out.println(physEvent.toLundString());
	    //System.out.println(" " + pipi.mass());
	    H100.fill(pipi.mass());
	}
}

TCanvas c1 = new TCanvas("c1","Physics Analysis",800,600,1,1);
c1.setFontSize(18);
c1.cd(0);
H100.setTitle("Two Pion Invariant Mass");
H100.setLineWidth(2);
H100.setLineColor(2);
c1.draw(H100);

