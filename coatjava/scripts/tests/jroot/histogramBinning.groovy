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

TCanvas c1 = new TCanvas("c1","JROOT Demo",900,400,2,1);
c1.setFontSize(14);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",6,0.0,6.0);
H2D h2 = new H2D("h2", 12, 0.0, 6.0, 12, 0.0, 6.0);


for(int i = 0; i < 1200000; i++){
   h1.fill(Math.random()*3.0 + Math.random()*3.0);
   h2.fill(Math.random()*3.0+Math.random()*3.0,Math.random()*3.0+Math.random()*3.0);
}


c1.cd(0);
c1.draw(h1);

c1.cd(1);
c1.draw(h2);