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
import org.root.pad.*;

TDirectory dir = new TDirectory();
dir.readFile("monitoring.evio");

TCanvas c1 = new TCanvas("c1","JROOT Demo",800,500,2,1);
c1.setFontSize(18);

H2D HP = (H2D) dir.getDirectory("Particles/Resolution/e-").getObject("momentumVSmomentum");

c1.cd(0);
c1.draw(HP);

F1D func = new F1D("gaus+p1",-0.2,0.2);
func.setParameter(0,100.0);
func.setParameter(1,0.0);
func.setParameter(2,0.014);

H1D sliceY = HP.sliceY(20);

c1.cd(1);
c1.draw(sliceY);
sliceY.fit(func);
c1.draw(func,"same");
