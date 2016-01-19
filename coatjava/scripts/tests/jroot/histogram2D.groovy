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

TCanvas c1 = new TCanvas("c1","JROOT Demo",600,500,1,1);
c1.setFontSize(18);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
h1.setXTitle("Random Generated Function");
h1.setYTitle("Counts");

F1D f1 = new F1D("gaus+p2",0.0,14.0);
f1.setParameter(0,120.0);
f1.setParameter(1,  8.2);
f1.setParameter(2,  1.2);
f1.setParameter(3, 24.0);
f1.setParameter(4,  7.0);

RandomFunc rndm = new RandomFunc(f1);

for(int i = 0; i < 54000; i++){
   h1.fill(rndm.random());
}

F1D func = new F1D("gaus+p2",0.2,13.8);
func.setParameter(0,200.0);
func.setParameter(1,5.0);
func.setParameter(2,0.4);

h1.fit(func);

F1D funcgaus = new F1D("gaus",0.2,13.8);
funcgaus.setParameter(0,func.getParameter(0));
funcgaus.setParameter(1,func.getParameter(1));
funcgaus.setParameter(2,func.getParameter(2));

PaveText info = new PaveText(0.05,0.75);
info.setFontSize(18);
info.addText("Fit Parameters : " );
info.addText("mean  = " + String.format("%8.3f",func.getParameter(1)));
info.addText("sigma = " + String.format("%8.3f",func.getParameter(2)));

c1.cd(0);
c1.draw(h1);
c1.draw(func);
c1.draw(funcgaus);
c1.draw(info);
