//-----------------------------------------------------------
//  Example groovy script for plotting graph and fitting
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import org.root.pad.*;
import org.root.data.*;
import org.root.func.*;
import org.root.histogram.*;

TGCanvas c1 = new TGCanvas("c1","My Plots",900,700,2,2); // Create Canvas with (1,1) divisions

double[] x   = [1.0, 2.75,  3.0,  3.5, 5.0, 6.0];
double[] y   = [1.2, 1.2 , 0.78, 0.96, 0.4, 0.2];
double[] ex  = [0.0,0.0,0.0,0.0,0.0,0.0];
double[] ey  = [0.05, 0.2, 0.2, 0.2, 0.2, 0.05];

double[] eyZ  = [   0.05, 0.2, 0.2, 0.2, 0.0, 0.0];
double[] eyAZ  = [   0.0, 0.0, 0.0, 0.0, 0.0, 0.0];

GraphErrors  graph  = new GraphErrors("GraphErrors",x,y,ex,ey); // define graph with X-Y points
GraphErrors  graph2 = new GraphErrors("GraphErrors",x,y,ex,eyZ); // define graph with X-Y points
GraphErrors  graph3 = new GraphErrors("GraphErrors",x,y,ex,eyAZ); // define graph with X-Y points

F1D          func   = new F1D("p1",0.5,6.5);
F1D          func2  = new F1D("p1",0.5,6.5);
F1D          func3  = new F1D("p1",0.5,6.5);

//func.setParameter(0,1.42082);
//func.setParameter(1,-0.201264);

graph.fit(func);
graph2.fit(func2);
graph3.fit(func3);

graph.setFillColor(4);
graph.setLineWidth(2);
graph.setMarkerSize(8);

graph2.setFillColor(4);
graph2.setLineWidth(2);
graph2.setMarkerSize(8);

graph3.setFillColor(4);
graph3.setLineWidth(2);
graph3.setMarkerSize(8);

func.show();
func2.show();

double chi2 = func.getChiSquare(graph);
System.out.println("chi2 = " + chi2 + "  NDF = " + func.getNDF());

double chi22 = func2.getChiSquare(graph2);
System.out.println("chi2 = " + chi22 + "  NDF = " + func2.getNDF());

double chi23 = func3.getChiSquare(graph3);
System.out.println("chi2 = " + chi23 + "  NDF = " + func3.getNDF());

c1.cd(1);
c1.draw(graph);
c1.draw(func,"same");

c1.cd(2);
c1.draw(graph2);
c1.draw(func2,"same");

c1.cd(3);
c1.draw(graph3);
c1.draw(func3,"same");

c1.cd(0);
c1.draw(graph3);
c1.draw(func3,"same");


func.show();
func2.show();
func3.show();

c1.save("jrootfits.png");
