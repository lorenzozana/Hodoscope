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

EvioSource reader = new EvioSource();
reader.open(filename);

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
EventFilter            filter = new EventFilter("11:2212:22:Xn:X+:X-");
int counter = 0;


while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
	PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	System.out.println(genEvent.toLundString());
	genEvent.setTargetParticle(new Particle(45,0.0,0.0,0.0,0.0,0.0,0.0));

	Particle beam = genEvent.getParticle("[2212]+[-11]+[11]+[22]-[t]");
	//beam.changePid(22);

	genEvent.setBeamParticle(beam);

	Particle  eta = genEvent.getParticle("[b]+[t]-[2212]");
	System.out.println(beam.toLundString());
	System.out.println(genEvent.targetParticle().toLundString());
	System.out.println(" ETA MASS = " + eta.mass());
}
