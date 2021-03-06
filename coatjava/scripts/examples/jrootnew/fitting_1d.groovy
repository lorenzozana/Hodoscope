//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;

TGCanvas c1 = new TGCanvas("c1","JROOT Demo",900,800,1,1);
//c1.setFontSize(14);

H1D h1 = new H1D("h1","ep #rarrow ep#gamma",200,0.0,14.0);
H1D h2 = new H1D("h2","ep #rarrow ep#gamma",200,0.0,14.0);

h1.setXTitle("M^2 [GeV^2]");
h1.setYTitle("Counts");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 24000; i++){
   h1.fill(rndm.random());
   if(i%2==0) h2.fill(rndm.random());
}

//c1.getCanvas().divide(1,2);


h1.setLineWidth(2);
h2.setLineWidth(2);
//h1.setFillColor(4);
h2.setFillColor(3);

f1.setLineColor(4);
f1.setLineWidth(3);
f1.setLineStyle(3);

c1.cd(0);
c1.getCanvas().setDivisionsX(10);
c1.getCanvas().setDivisionsY(10);
c1.draw(h1);
c1.draw(f1,"same");
//c1.draw(f1);
