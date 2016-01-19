//-----------------------------------------------------------
//  Example groovy script for plotting graph and fitting
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.root.pad.*;
import org.root.data.*;
import org.root.func.*;
import org.root.histogram.*;

TGCanvas c1 = new TGCanvas("c1","My Plots",600,400,1,1); // Create Canvas with (1,1) divisions

double[] x = [ 1.0, 2.0, 3.0, 4.0]; // this is groovy syntax for array initialization
double[] y = [ 0.5, 0.7, 0.6, 0.4]; // in java : double[] x = new double[]{1.0,2.0,3.0,4.0};

GraphErrors  graph = new GraphErrors(x,y); // define graph with X-Y points

graph.setTitle("Example Graph"); // Set the Title string for the PAD
graph.setXTitle("Energy [GeV]"); // X axis title
graph.setYTitle("Efficiency");   // Y axis title

graph.setMarkerColor(4); // color from 0-9 for given palette
graph.setMarkerSize(15); // size in points on the screen
graph.setMarkerStyle(2); // Style can be 1 or 2

c1.draw(graph);


F1D func = new F1D("p2",0.8,4.2);
func.setParameter(0,5.0);
func.setParameter(1,3.0);
func.show();
graph.fit(func);
func.show();
c1.draw(func,"same");
