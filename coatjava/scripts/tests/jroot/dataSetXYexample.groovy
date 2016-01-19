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
import java.lang.Math;

TCanvas c1 = new TCanvas("c1","JROOT Demo",600,400);
c1.setFontSize(18);

double[] x = [ 1.0, 2.0, 3.0, 4.0];
double[] y = [ 0.5, 0.7, 0.6, 0.4];

DataSetXY  graph = new GraphErrors(x,y);
graph.setTitle("Example Graph");
graph.setXTitle("Energy [GeV]");
graph.setYTitle("Efficiency");
graph.setLineColor(2);
graph.setLineWidth(3);

System.out.println(" line color = " + graph.getLineColor());
c1.cd(0);
c1.draw(graph,"L");
