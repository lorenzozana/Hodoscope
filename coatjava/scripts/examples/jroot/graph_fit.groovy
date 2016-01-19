//-----------------------------------------------------------
//  Example groovy script for plotting graph and fitting
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.root.pad.*;
import org.root.data.*;
import org.root.func.*;
import org.root.histogram.*;

TCanvas c1 = new TCanvas("c1","My Plots",600,600);
c1.divide(1,1);
double[] x = [ 1.0, 2.0, 3.0, 4.0];
double[] y = [ 0.5, 0.7, 0.6, 0.4];

GraphErrors  graph = new GraphErrors(x,y);
//DataSetXY graph = new DataSetXY(x,y);

graph.setTitle("Example Graph");
graph.setXTitle("Energy [GeV]");
graph.setYTitle("Efficiency");
c1.cd(0);
c1.draw(graph);

F1D func = new F1D("p2",0.5,4.5);
func.setParameter(0,5.0);
func.setParameter(1,3.0);
func.show();
graph.fit(func);
func.show();
c1.draw(func,"same");
