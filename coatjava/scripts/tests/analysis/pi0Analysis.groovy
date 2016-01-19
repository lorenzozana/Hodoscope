//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import org.jlab.clas.physics.*;
import org.root.histogram.*;
import org.root.func.*;
import org.root.pad.*;
import org.jlab.clas12.physics.*;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);

GenericKinematicFitter fitter = new GenericKinematicFitter(11.0,"11:X+:X-:Xn");
fitter.setMatching(false);

EventFilter            filter = new EventFilter("11:22:22:X+:X-");
//EventFilter            filter = new EventFilter("11:22:X+:X-");
int counter = 0;

H1D H100 = new H1D("H100","M [GeV]","",200,0.05,0.25);
H1D H101 = new H1D("H101","Theta [Rad]","",240,-4.0,4.0);
H1D H102 = new H1D("H102",  "Phi [Rad]","",100,-3.15,3.15);
H2D H202 = new H2D("H202",100,-3.15,3.15,100,0.0,1.0);

H1D H110 = new H1D("H110","Q2 [GeV2]","",100,0.0,10.0);
H1D H111 = new H1D("H111","W2 [GeV2]","",100,0.0,20.0);

while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	PhysicsEvent  recEvent  = fitter.getPhysicsEvent(event);
	PhysicsEvent  genEvent  = fitter.getGeneratedEvent(event);
	if(filter.isValid(recEvent)==true){
	   //System.err.println("--------------> event start");
	   //System.out.println(genEvent.toLundString());
	   //System.out.println(recEvent.toLundString());
	   Particle genPhoton = genEvent.getParticle("[22]");
	   Particle recPhoton = recEvent.getParticle("[22]");

	   //Particle gamma1 = recEvent.getParticle("[22,0]");
	   //Particle gamma2 = recEvent.getParticle("[22,1]");

	   Particle gamma1 = genEvent.getParticle("[22,0]");
	   Particle gamma2 = genEvent.getParticle("[22,1]");
	   Particle pi0       = recEvent.getParticle("[22]+[22,1]");
	   //System.out.println(" pi 0 mass ----> " + pi0.mass());
	   if(Math.toDegrees(gamma1.theta())<5.0 && Math.toDegrees(gamma2.theta())<5.0){
	   //if(Math.toDegrees(gamma1.theta())<5.0){
	   //if(Math.toDegrees(gamma1.theta())>5.0 && Math.toDegrees(gamma2.theta())>5.0){
	      //System.out.println(" ---->  EVENT PRINTOUT ====");
	      //System.out.println(genEvent.toLundString());
	      //System.out.println(recEvent.toLundString());
	      //if(event.hasBank("FTRec::tracks")==true){
	      //   EvioDataBank bank = event.getBank("FTRec::tracks");
	      // bank.show();
	      //}
	      H100.fill(pi0.mass());
	   }
	   H101.fill(Math.toDegrees(genPhoton.theta()-recPhoton.theta()));
       }
}

System.err.println("processed = " + counter);

F1D func = new F1D("gaus+p1",0.05,0.2);

func.setParameter(0,30.0);
func.setParameter(1,0.139);
func.setParameter(2,0.03);
func.setParLimits(1,0.12,0.14);
func.setParLimits(2,0.003,0.1);
H100.fit(func);

TCanvas c1 = new TCanvas("c1","Physics Analysis",1000,800,1,1);

c1.cd(0);
H100.setTitle("Two Photon Mass");
H100.setLineWidth(2);
H100.setLineColor(4);
PaveText  stats = new PaveText(0.6,0.9);
stats.addText("Pi0 mass");
stats.addText(String.format("mean    =  %8.4f",func.getParameter(1)));
stats.addText(String.format("sigma   =  %8.4f",func.getParameter(2)));
stats.setFontSize(18);
//func.setLineColor(2);
//func.setLineWidth(2);

c1.draw(H100);
c1.draw(func,"same");
c1.draw(stats);
/*
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
*/