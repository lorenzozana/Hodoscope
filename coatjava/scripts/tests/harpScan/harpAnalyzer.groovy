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

DataTable table = new DataTable();
table.readFile("harp_electron_example.asc");

DataSetXY  graph = table.getDataSet(1,10);
TCanvas c1 = new TCanvas("c1","",900,500,1,3);

c1.cd(0);
c1.setLogY(true);
c1.draw(graph);
