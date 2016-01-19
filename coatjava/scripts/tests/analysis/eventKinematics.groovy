//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.io.ClasEvioReader
import org.jlab.clas.physics.*
import org.jlab.data.histogram.*
import org.jlab.scichart.canvas.ScCanvas

String inputFile = args[0];

EventFilter  filter = new EventFilter("11:2212:22:22");

ClasEvioReader  reader = new ClasEvioReader();
reader.addFile(inputFile);
reader.open()

//----------------------------------------------------------------
// Declare histograms
H1D H1_EMOM = new H1D("H1_EMOM",100,0.0,5.5);
H1D H1_ETH  = new H1D("H1_ETH" ,100,0.0,60.0);
H1D H1_EPHI = new H1D("H1_EPHI",100,-185.0,185.0);
H1D H1_W2   = new H1D("H1_W2"  ,100,0.0,3.5);
//----------------------------------------------------------------

int icounter = 0;
while(reader.next()==true){
	
	icounter++;	
	PhysicsEvent physEvent = reader.getPhysicsEvent();
	if(filter.isValid(physEvent)==true){

		physEvent.setBeam(5.7);

		Particle electron = physEvent.getParticle("[e-,0]");
		Particle w2       = physEvent.getParticle("[b]+[t]-[e-,0]");
		Particle q2       = physEvent.getParticle("[b]-[e-,0]");

		H1_EMOM.fill(electron.p());
		H1_ETH.fill(electron.theta()*57.29);
		H1_EPHI.fill(electron.phi()*57.29);
		H1_W2.fill(w2.mass());
	}
}

System.err.println("Processed events = " + icounter);
//-----------------------------------------------------------------
// Plotting histograms

def canvas = new ScCanvas(800,600,2,2);
canvas.setLabelSize(14);

canvas.cd(0);
canvas.draw(H1_EMOM);

canvas.cd(1);
canvas.draw(H1_ETH,"*",4);

canvas.cd(2);
canvas.draw(H1_EPHI,"*",3);

canvas.cd(3);
canvas.draw(H1_W2,"*",2);
