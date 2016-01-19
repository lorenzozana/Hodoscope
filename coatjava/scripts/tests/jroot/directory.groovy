//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.group.*;
import org.root.func.*;
import org.root.histogram.*;

TDirectory dir = new TDirectory();

dir.mkdir("Generated/Random/Gauss");
dir.mkdir("Generated/Random/Polynomial");
dir.mkdir("Generated/Random/Exponential");
dir.mkdir("Reconstructed/Random/Gauss");

dir.scan();

