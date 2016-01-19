//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.clasrec.utils.*;
import org.jlab.clas.tools.utils.*;

CommandLineTools parser = new CommandLineTools();

parser.parse(args);
System.out.println(parser.toString());

ArrayList<String>  options = parser.getConfigItems();

for(String item : options){
   System.out.println("item = " + item);
}

ServiceConfiguration  config = new ServiceConfiguration();

config.addItem("DCHB","torus",1.0);
config.addItem("DCHB","solenoid","0.7");
config.addItem("DCHB","debug",0);

config.show();
