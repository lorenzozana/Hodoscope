import org.jlab.evio.clas12.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;

String inputfile = args[0];
EvioSource reader = new EvioSource();
reader.open(inputfile);
// create new kinematic fitter, with beam energy 11.0 GeV and electron filter
GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");

int counter = 0;
while(reader.hasEvent()==true){
     EvioDataEvent event = reader.getNextEvent();
     PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
     PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
     counter++;
     System.out.println("NEW EVENT # " + counter);
     System.out.println(genEvent.toLundString());
     System.out.println(recEvent.toLundString());
}
