//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.attr.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;


//TStyle.setFrameFillColor(160,215,230);
TStyle.setFrameFillColor(215,235,245);
//TStyle.setAxisColor(255,255,255);
TStyle.setAxisFont("Helvetica",24);

//TGCanvas c1 = new TGCanvas("c1","JROOT Demo",900,800,1,3);
//TImageCanvas c1 = new TImageCanvas("c1","JROOT Demo",900,800,1,3);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H1D h2 = new H1D("h2","Random Function (Gaus + POL2)",200,0.0,14.0);
H1D h3 = new H1D("h3","Random Function (Gaus + POL2)",200,0.0,14.0);
H1D h4 = new H1D("h4","Random Function (Gaus + POL2)",200,0.0,14.0);

h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

F1D f2 = new F1D("gaus+p2",0.0,14.0);
f2.setParameter(0,120.0);
f2.setParameter(1,  4.2);
f2.setParameter(2,  1.0);
f2.setParameter(3, 24.0);
f2.setParameter(4,  7.0);

F1D f3 = new F1D("gaus+p2",0.0,14.0);
f3.setParameter(0,120.0);
f3.setParameter(1,  8.2);
f3.setParameter(2,  0.5);
f3.setParameter(3, 24.0);
f3.setParameter(4,  7.0);

RandomFunc rndm1 = new RandomFunc(f1);
RandomFunc rndm2 = new RandomFunc(f2);
RandomFunc rndm3 = new RandomFunc(f3);

for(int i = 0; i < 34000; i++){
   h1.fill(rndm1.random());
   h2.fill(rndm2.random());
}

for(int i = 0; i < 44000; i++){
   h3.fill(rndm2.random());
   h4.fill(rndm3.random());
}

TImageCanvas c1 = new TImageCanvas("c1","JROOT Demo",900,800,1,3);
h1.setLineWidth(2);
h1.setFillColor(24);

h2.setLineWidth(2);
h2.setFillColor(32);

h3.setLineWidth(2);
h3.setFillColor(33);

h4.setLineWidth(2);
h4.setFillColor(35);

c1.cd(0);
c1.draw(h1);
c1.draw(h2,"same");

c1.cd(1);
c1.draw(h1);
c1.draw(h3,"same");

c1.cd(2);
c1.draw(h4);
c1.draw(h2,"same");

c1.save("random_color.png");
