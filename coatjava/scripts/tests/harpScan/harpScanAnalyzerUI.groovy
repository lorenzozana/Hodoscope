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
import org.jlab.harp.*;

HarpScanDialog dialog = new HarpScanDialog("harp_electron_example.asc",2);
dialog.setVisible(true);

/*
HarpScanAnalyzer  analyzer = new HarpScanAnalyzer();
analyzer.openFile("harp_electron_example.asc");

analyzer.addWire(10,25.0,80.0);
analyzer.addWire(10,235.0,280.0);
analyzer.analyze();

TCanvas c1 = new TCanvas("c1","",900,500,1,2);

for(int loop = 0; loop < 2; loop++){
 c1.cd(loop);
 c1.draw(analyzer.getHarpWire(loop).getDataSet());
 c1.draw(analyzer.getHarpWire(loop).getFitFunction(),"same");
}*/
//c1.setLogY(true);
//c1.draw(graph);
