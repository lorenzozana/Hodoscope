//-----------------------------------------------------------
// Example Groovy script for plotting 2D histograms
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;

TCanvas c1 = new TCanvas("c1","JROOT Demo",600,500,1,1);
c1.setFontSize(18);

H2D h2 = new H2D("h2",200,0.0,14.0,200,0.0,14.0);
h2.setTitle("Two Random Gaussians");
h2.setXTitle("Random Generated Function");
h2.setYTitle("Counts");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 4000000; i++){
   h2.fill(rndm.random(),rndm.random());
}

c1.cd(0);
c1.draw(h2);
