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

int counter = 0;

H1D H100 = new H1D("H100","P [GeV]","",100,0.0,6.0);
H1D H101 = new H1D("H101","Theta [Rad]","",100,0.0,1.0);
H1D H102 = new H1D("H102",  "Phi [Rad]","",100,-3.15,3.15);
H2D H202 = new H2D("H202",100,-3.15,3.15,100,0.0,1.0);

H1D H110 = new H1D("H110","Q2 [GeV2]","",100,0.0,10.0);
H1D H111 = new H1D("H111","W2 [GeV2]","",100,0.0,20.0);

while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	PhysicsEvent  physEvent = fitter.getPhysicsEvent(event);
	PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	System.err.println("--------------> event start");
	System.out.println(genEvent.toLundString());
	System.out.println(physEvent.toLundString());
	fitter.matchGenerated(genEvent,physEvent);
	System.out.println(physEvent.toLundString());
	if(physEvent.countByPid(11)>0){
	    H100.fill(physEvent.getParticleByPid(11,0).p());
	    H101.fill(physEvent.getParticleByPid(11,0).theta());
	    H102.fill(physEvent.getParticleByPid(11,0).phi());
	    H202.fill(physEvent.getParticleByPid(11,0).phi(),physEvent.getParticleByPid(11,0).theta());
	    double q2 = -physEvent.getParticle("[b]-[11]").get("mass2");
	    double w2 =  physEvent.getParticle("[b]+[t]-[11]").get("mass2");
	    H110.fill(q2);
	    H111.fill(w2);
	    //System.out.println(q2 + "  " + w2);
	}
}

System.err.println("processed = " + counter);

TCanvas c1 = new TCanvas("c1","Physics Analysis",1000,800,2,3);

c1.cd(0);
H100.setTitle("Electron Momentum");
c1.draw(H100);

c1.cd(1);
H101.setTitle("Electron Polar Angle");
c1.draw(H101);

c1.cd(2);
H102.setTitle("Electron Azimuthal Angle");
c1.draw(H102);

c1.cd(3);
//H100.setTitle("Electron Momentum");
c1.draw(H202);

c1.cd(4);
c1.draw(H110);

c1.cd(5);
c1.draw(H111);
