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

TGCanvas c1 = new TGCanvas("c1","JROOT Demo",900,800,1,2);
//c1.setFontSize(14);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",6,0.0,6.0);

for(int i = 0; i < 3400; i++){
   h1.fill(Math.random()*6);
}

//c1.getCanvas().divide(1,2);

h1.setLineWidth(2);
h1.setFillColor(4);

c1.cd(0);
c1.draw(h1);


