//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.attr.*;
import org.root.histogram.*;
//import org.root.func.*;
import java.lang.Math;
import java.awt.Graphics2D;
import de.erichseifert.vectorgraphics2d.PDFGraphics2D;

//TImageCanvas c1 = new TImageCanvas("c1","JROOT Demo",900,800,1,3);

H1D h1 = new H1D("h1","Random Function (Gaus + POL2)",200,0.0,14.0);
