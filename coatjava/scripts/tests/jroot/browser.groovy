//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.group.*;
import org.root.func.*;
import org.root.histogram.*;


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

for(int i = 0; i < 940000; i++){
   h2.fill(rndm.random(),rndm.random());
}


TDirectory dir = new TDirectory();


//dir.cd("Particle");
//dir.add("h2",h2);

dir.mkdir("Generated/Random/Gauss");

dir.getDirectory("Generated/Random/Gauss").addObject("h1",h1);
dir.getDirectory("Generated/Random/Gauss").addObject("h2",h2);

dir.scan();
dir.ls();

TBrowser browser = new TBrowser(dir);
