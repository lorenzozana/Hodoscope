//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.coda.jevio.*;


String filename = args[0];

System.out.println("Wait for it:");

Console c = System.console();
c.readLine();
System.out.println("Openning file");

EvioCompactReader evioReader = new EvioCompactReader(new File(filename));

while(1){
}

System.out.println("  procecessed " + counter + "  events");
