//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.clasfx.jar.*;
import org.jlab.clasrec.main.*;

JarClassLoader<DetectorReconstruction>  loader = new JarClassLoader<DetectorReconstruction>();
loader.scan("/Users/gavalian/Work/Software/Release-8.0/COATJAVA/coatjava/lib/plugins/clasrec-ft.jar",
			"org.jlab.clasrec.main.DetectorReconstruction");
System.out.println("------------->");
loader.list();
