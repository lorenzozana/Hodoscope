//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas;
import org.jlab.data.histogram.*;
import org.jlab.data.func.*;

double[]  X = [ 1.0, 2.0, 3.0, 4.0, 5.0 ];
double[]  Y = [ 15.0, 16.0, 32.0, 42.0, 51.0 ];

GraphErrors  graph = new GraphErrors(X,Y);

graph.setXTitle("Some Data");
graph.setMarkerStyle(3);
graph.setMarkerColor(3);
graph.setMarkerSize(10);


F1D func_p1 = new F1D("p1",0.5,5.5);
F1D func_p2 = new F1D("p2",0.5,5.5);
F1D func_p3 = new F1D("p3",0.5,5.5);

graph.fit(func_p1);
graph.fit(func_p2);
graph.fit(func_p3);


def canvas = new ScCanvas(400,900,1,3);

canvas.canvas().draw(0,graph);
canvas.canvas().draw(0,func_p1);

canvas.canvas().draw(1,graph);
canvas.canvas().draw(1,func_p2);

canvas.canvas().draw(2,graph);
canvas.canvas().draw(2,func_p3);
