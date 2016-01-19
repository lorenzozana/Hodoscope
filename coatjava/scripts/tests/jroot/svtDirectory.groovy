//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.group.*;
import org.root.func.*;
import org.root.data.*;
import org.root.histogram.*;

TDirectory dir = new TDirectory();

dir.readFile("testT.evio");

//dir.scan();
//dir.ls();

DataSetXY data = (DataSetXY) dir.getDirectory("p35/scan1/u4/125").getObject("occ_075_125_data");
data.show();

TBrowser br = new TBrowser(dir);
