//-----------------------------------------------------------
//  Example groovy script for plotting graph and fitting
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.root.pad.*;
import org.root.data.*;
import org.root.func.*;
import org.root.histogram.*;

TGCanvas c1 = new TGCanvas("c1","My Plots",900,700,2,2); // Create Canvas with (1,1) divisions

double[] x  = [ 1.0, 2.0, 3.0, 4.0]; // this is groovy syntax for array initialization
double[] y  = [ 0.5, 0.7, 0.6, 0.4]; // in java : double[] x = new double[]{1.0,2.0,3.0,4.0};
double[] ex = [ 0.2, 0.2, 0.2, 0.2]; // errors in X
double[] ey = [ 0.5, 0.3, 0.2, 0.1]; // errors in Y

double[] x1  = [ 1.4, 2.4,  3.4, 4.4]; // this is groovy syntax for array initialization
double[] y1  = [ 1.5, 0.1, 0.55, 0.75]; // in java : double[] x = new double[]{1.0,2.0,3.0,4.0};
double[] ez  = [ 0.0, 0.0, 0.0, 0.0]; // errors in X all set to 0
double[] ey1 = [ 0.1, 0.4,  0.3, 0.2]; // errors in Y

GraphErrors  graph  = new GraphErrors("MyGraphErrors_1",x,y,ex,ey); // define graph with X-Y points and EX-EY errors
GraphErrors  graph2 = new GraphErrors("MyGraphErrors_2",x1,y1,ex,ey1); // define graph with X-Y points and EX-EY errors
GraphErrors  graph3 = new GraphErrors("MyGraphErrors_3",x1,y1,ex,ey1); // define graph with X-Y points and EX-EY errors
GraphErrors  graph4 = new GraphErrors("MyGraphErrors_4",x1,y1,ex,ey1); // define graph with X-Y points and EX-EY errors
GraphErrors  graph5 = new GraphErrors("MyGraphErrors_5",x1,y1,ez,ey1); // define graph with X-Y points and EX-EY errors

graph.setMarkerColor(4); // color from 0-9 for given palette
graph.setMarkerSize(8); // size in points on the screen
graph.setMarkerStyle(1); // Style can be 1 or 2

c1.cd(0);
c1.draw(graph);
c1.draw(graph2,"same");

c1.cd(1);
graph3.setTitle("Graph with Squares");
graph3.setLineColor(2);
graph3.setMarkerStyle(2);
graph3.setMarkerColor(4);
graph3.setFillColor(5);
c1.draw(graph3);

c1.cd(2);
c1.setLogY(true);
graph4.setTitle("Graph with Crosses");
graph4.setLineColor(1);
graph4.setLineWidth(2);
graph4.setMarkerStyle(4);
graph4.setMarkerColor(1);
graph4.setMarkerSize(24);
graph4.setFillColor(6);
c1.draw(graph4);

c1.cd(3);
graph5.setTitle("Graph Errors with Triangles");
graph5.setLineColor(1);
graph5.setLineWidth(2);
graph5.setMarkerStyle(3);
graph5.setMarkerColor(1);
graph5.setMarkerWidth(1);
graph5.setMarkerSize(24);
graph5.setFillColor(8);
c1.draw(graph5);

c1.save("grapherrors_demo.png");
