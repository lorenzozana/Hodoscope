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

// Define two histograms with Ttitle string
H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H1D h2 = new H1D("h2","Second Random Function",200,0.0,14.0);


// Set X and Y titles for both histograms
h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

h2.setXTitle("Random Generated Function");
h2.setYTitle("Counts");

// Define a function with gaus and polynomial and set parameters
F1D f1 = new F1D("gaus+p2",0.0,14.0);

f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

// Declare a random number generator based on the function
// and fill the histograms with rundomly generated numbers

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 34000; i++){
   h1.fill(rndm.random());
   if(i%2==0) h2.fill(rndm.random());
}

// Define a canvas with 1 column and two rows
TGCanvas c1 = new TGCanvas("c1","JROOT Demo",900,800,1,2);

// Set up the drawing properties for histograms
// Color scheme is similar to ROOT:
// 1 - black, 2 - red, 3 - green, 4 -blue .... 
h1.setLineWidth(2);
h1.setFillColor(4);

h2.setLineWidth(2);
h2.setLineColor(4);
h2.setFillColor(3);

// Go to first pad and draw histogram with default
// axis divisions for X and Y axis.
c1.cd(0);
c1.draw(h1);

// Go to second pad, change the axis divisions for 
// the pad and draw the histogram.

c1.cd(1);
c1.getCanvas().setDivisionsX(8);
c1.getCanvas().setDivisionsY(5);
c1.draw(h2);

c1.save("canvas_c1.png");
