//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.histogram.*;
import org.root.data.*;
import org.root.func.*;
import javax.swing.JFrame;
import java.lang.Math;

JFrame frame = new JFrame("Example Embeded Canvas");
RootCanvas canvas = new RootCanvas(700,400,2,1);


double[] x = [ 1.0, 2.0, 3.0, 4.0];
double[] y = [ 0.5, 0.7, 0.6, 0.4];

DataSetXY  graph = new DataSetXY(x,y);
graph.setTitle("Example Graph");
graph.setXTitle("Energy [GeV]");
graph.setYTitle("Efficiency");

canvas.draw(0,graph);
canvas.setAxisRange(0,1.0,4.0,0.45,0.65);
canvas.setXaxisDivisions(1,4);
canvas.draw(1,graph);

//c1.cd(0);
//c1.setNdivisionsX(3);
//c1.draw(graph);


frame.add(canvas);
frame.pack();
frame.setVisible(true);