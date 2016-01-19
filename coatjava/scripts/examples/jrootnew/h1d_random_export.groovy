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

//c1.setFontSize(14);

H1D h1  = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H1D h11 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H2D h2 = new H2D("h2",80,0.0,14.0,80,0.0,14.0);

h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

h11.setXTitle("Function");
h11.setYTitle("Number Of Events");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 340000; i++){
   h1.fill(rndm.random());
   h11.fill(rndm.random());
   h2.fill(rndm.random(),rndm.random());
}


//c1.getCanvas().divide(1,2);


h1.setLineWidth(2);
h1.setFillColor(4);

h11.setLineWidth(2);
h11.setFillColor(2);

f1.setLineWidth(4);
f1.setLineColor(5);
f1.setLineStyle(2);
/*
c1.cd(0);
c1.getCanvas().setAxisRange(0.0,0.0,-100.0,500);
c1.getCanvas().setDivisionsX(3);
c1.draw(h1);

c1.cd(1);
c1.draw(h1);

c1.cd(2);
c1.getCanvas().setDivisionsX(10);
c1.getCanvas().setDivisionsY(10);
c1.draw(h1);
*/
TImageCanvas c1 = new TImageCanvas();
c1.setSize(800,800);
c1.divide(1,2);
c1.cd(0);
c1.draw(h1);
c1.cd(1);
c1.draw(h2);
/*
c1.cd(2);
c1.draw(f1);

c1.cd(3);
c1.draw(h2);
*/
c1.save("histogram.png");
c1.savePDF("histogram.pdf");
c1.saveSVG("histogram.svg");

