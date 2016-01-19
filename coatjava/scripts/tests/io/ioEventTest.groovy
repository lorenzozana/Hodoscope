//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import org.jlab.clas.physics.*;
import org.root.histogram.*;
import org.root.pad.*;
import org.jlab.clas12.physics.*;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);
int counter = 0;
while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();
	int[] pids = event.getInt("BST::true.pid");
	if(pids!=null){
	  System.out.println("---->  length = " + pids.length);
	} else {
	    System.out.println(" NULL BANK " + event.hasBank("BST::true"));
	}
}

