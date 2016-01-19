//-----------------------------------------------------------
// Example Groovy script for plotting 2D histograms
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.data.*;
import org.root.group.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;

TDirectory  root    = new TDirectory();
TDirectory  sector  = new TDirectory("sector1/panel2/layer3");

//**********************************************
// Add a histogram to the directory
//**********************************************
H1D h100 = new H1D("paddles",20, 0.0, 20.0);
for(int loop = 0; loop < 120; loop++){
  h100.fill(Math.random()*20.0);
}
sector.add(h100);

//**********************************************
// Create and add a dataset to the dirctory
//**********************************************
double[] xv = [ 1.0, 2.0,  3.0,  4.0,  5.0 ];
double[] yv = [ 12.0, 9.7, 10.2,  4.5,  2.3 ];
DataSetXY dataset = new DataSetXY("occupancy", xv,yv);
sector.add(dataset);

//**********************************************
// Create and Add a function to directory
//**********************************************
F1D func = new F1D("gaus",0.4,2.4);
func.setParameter(0, 24.0);
func.setParameter(1,  1.5);
func.setParameter(2,  0.23);
func.setName("gausFunc");

sector.add(func);
root.addDirectory(sector);

root.write("testDirectory.evio");

TBrowser br = new TBrowser(root);

