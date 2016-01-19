//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.group.*;
import org.root.func.*;
import org.root.data.*;
import org.root.histogram.*;

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);
RandomFunc rndm = new RandomFunc(f1);

NTuple T = new NTuple("T","id:x:y");
double[] vars = new double[3];

for(int i = 0; i < 340000; i++){
   vars[0] = (double) i;
   vars[1] = rndm.random();
   vars[2] = rndm.random(); 
   T.addRow(vars);
}

T.write("myfirstntuple.evio");

NTuple R = new NTuple("R","a:b");
R.open("myfirstntuple.evio");
R.scan();

TBrowser browser = new TBrowser(R);
