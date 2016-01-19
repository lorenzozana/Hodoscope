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

TCanvas c1 = new TCanvas("c1","JROOT Demo",600,400);
c1.setFontSize(18);

double[] x = [ 1.0, 2.0, 3.0, 4.0];
double[] y = [ 0.5, 0.7, 0.6, 0.4];

GraphErrors  graph = new GraphErrors(x,y);
graph.setTitle("Example Graph");
graph.setXTitle("Energy [GeV]");
graph.setYTitle("Efficiency");

F1D func = new F1D("gaus",-2.0,6.0);
func.setParameter(0,1.0);
func.setParameter(1,3.0);
func.setParameter(2,0.5);

c1.cd(0);
//c1.setNdivisionsX(3);
c1.draw(graph);
c1.draw(func,"same");