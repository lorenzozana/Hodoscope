//****************************************************
// Particle generator with decay module
//****************************************************
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.fastmc.*;
import org.jlab.geom.prim.*;
import org.jlab.geom.*;

import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;


ParticleGenerator  pion = new ParticleGenerator(  -211 , // electron PID  
		   	     0.3  ,    1.2, // momentum min/max 
		   	     10.0 ,   125.0, // theta (deg) min/max
			  -180.0  ,  180.0 ); // phi (deg) min/max


CLAS12FastMC    fastMC = new CLAS12FastMC(-1.0,1.0);

H1D   hpiRec = new H1D("hpiRec",100,0.0,120.0);
H1D   hpiGen = new H1D("hpiGen",100,0.0,120.0);

int countrec = 0;
int count    = 0;


for(int loop = 0; loop < 15000; loop++){

  Particle ep = pion.getParticle();
  Particle eprec = fastMC.getParticle(ep);

  hpiGen.fill(Math.toDegrees(ep.theta()));
  count++;
  if(eprec.p()>0){
    hpiRec.fill(Math.toDegrees(eprec.theta()));
   countrec++;
 }
  //System.out.println("=========>  Event Printout: ");
  //System.out.println(" GEN : " + ep.toLundString());
  //System.out.println(" REC : " + eprec.toLundString());
}

System.out.println("\n\n Reconstructed event " + countrec + "/" + count);

TCanvas c1 = new TCanvas("c1","JROOT Demo",900,400,2,1);
c1.setFontSize(14);
hpiRec.setLineColor(2);
hpiRec.setLineWidth(2);
c1.draw(hpiGen);
c1.draw(hpiRec,"same");
