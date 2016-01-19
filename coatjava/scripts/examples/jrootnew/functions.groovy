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

F1D f1 = new F1D("erf",25.0,100.0);
f1.setParameter(0,  1000.0);
f1.setParameter(1,  3000.0);
f1.setParameter(2,  60.0);
f1.setParameter(3,  4.8);

F1D f2 = new F1D("erf",-3.0,3.0);
f2.setParameter(0,  0.0);
f2.setParameter(1,  1.0);
f2.setParameter(2,  0.0);
f2.setParameter(3,  1.0);

double[]  y = [3086 ,3070 ,3058 ,3064 ,3059 ,3058 ,3059 ,3061 ,3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,
			   3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,3058 ,3057 ,3056 ,3057 ,3044 ,
			   3049 ,3040 ,3041 ,3024 ,3024 ,2994 ,2976 ,2960 ,2892 ,2823 ,2776 ,2669 ,2569 ,2425 ,
			   2284 ,2089 ,2036 ,1806 ,1679 ,1465 ,1272 ,1084 ,962 ,806 ,649 ,554 ,434 ,360 ,230 ,
			   198 ,133 ,118 ,77 ,62 ,37 ,25 ,28 ,11 ,10 ,7 ,1 ,2 ,1 ,0 ,1 ,0 ,0];

double[] x = new double[y.length];
for(int c = 0; c < x.length; c++){
	x[c] = 25.0+c;
}

GraphErrors  graph = new GraphErrors(x,y);

/*
c1.cd(0);
c1.draw(f2);

graph.setMarkerColor(2);
graph.setMarkerSize(8);
graph.setMarkerStyle(3);
*/
graph.fit(f1);
f1.show();
c1.cd(0);
c1.draw(graph);
c1.draw(f1,"same");
//c1.svae("error_fit.png");
