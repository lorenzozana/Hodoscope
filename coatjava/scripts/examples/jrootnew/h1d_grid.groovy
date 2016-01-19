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

//c1.setFontSize(14);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
H2D h2 = new H2D("h2",120,0.0,14.0,120,0.0,14.0);

h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 34000; i++){
   h1.fill(rndm.random());
}

//c1.getCanvas().divide(1,2);


h1.setLineWidth(2);
h1.setFillColor(4);

int nx = 1;
int ny = 1;

TGCanvas c1 = new TGCanvas("c1","JROOT Demo",600,400,nx,ny);
TStyle.setAxisFont("Helvetica",18);
TStyle.setStatBoxFont("Courier",18);
for(int loop = 0; loop < nx*ny; loop++){
	c1.cd(loop);
	c1.draw(h1);
}


