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

TDirectory dir1 = new TDirectory();
dir1.readFile("monitoring_t1_s1.evio");

TDirectory dir2 = new TDirectory();
dir2.readFile("monitoring_t0.5_s1.evio");

TCanvas c1 = new TCanvas("c1","JROOT Demo",800,700,1,2);
c1.setFontSize(18);

H1D HP1 = (H1D) dir1.getDirectory("Particles/Resolution/pi-").getObject("theta");
H1D HP2 = (H1D) dir2.getDirectory("Particles/Resolution/pi-").getObject("theta");

c1.cd(0);
c1.draw(HP1);

F1D func = new F1D("gaus+p2",-9.8,9.8);
HP1.fit(func);
c1.draw(func,"same");

F1D func2 = new F1D("gaus+p2",-9.8,9.8);
func2.setParameter(0,  100.0);
func2.setParLimits(0,    0.0, 100000.0);
func2.setParameter(2,    0.2);

HP1.fit(func2);

c1.cd(1);
c1.draw(HP1);
c1.draw(func2,"same");
/*
func.setParameter(0,100.0);
func.setParameter(1,0.0);
func.setParameter(2,0.14);

//H1D sliceY = HP.sliceY(20);
HP1.fit(func);
c1.draw(func,"same");

F1D func2 = new F1D("gaus+p2",-1.0,1.0);
func2.setParameter(0,100.0);
func2.setParLimits(0,0.0,10000.0);
func2.setParameter(1,0.0);
func2.setParameter(2,0.14);

c1.cd(1);
c1.draw(HP2);
HP2.fit(func2);
c1.draw(func2,"same");

//sliceY.fit(func);
//c1.draw(func,"same");

print HP1.toString();
*/