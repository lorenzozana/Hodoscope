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

TGCanvas c1 = new TGCanvas("c1","JROOT Demo",900,800,2,3);
//c1.setFontSize(14);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H2D h2 = new H2D("h2",14,0.0,14.0,120,0.0,14.0);

h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

h2.setTitle("SECTOR 1 OCCUPANCY");
h2.setXTitle("TIME-OF-FLIGHT");
h2.setYTitle("PADDLES");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 840000; i++){
   h2.fill(rndm.random(),rndm.random());
}


for(int p = 0; p < 6; p++){
  c1.cd(p);
  if(p<3) c1.setLogZ();
  c1.draw(h2);
}