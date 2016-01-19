//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;
import org.jlab.clas.tools.benchmark.*;
import org.root.histogram.*;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);

Benchmark bench = new Benchmark();


bench.addTimer("READING");
bench.addTimer("GETBANK");
bench.addTimer("FILLHISTO");

H1D  h1TDC = new H1D("h1TDC",200,0.0,4000.0);
H1D  h2TDC = new H1D("h2TDC",112,0.0,112.0,200,0.0,4000.0);

int counter = 0;

while(reader.hasEvent()==true){
  bench.resume("READING");
  EvioDataEvent  event = reader.getNextEvent();
  bench.pause("READING");
  EvioDataBank bank = null;
  
  bench.resume("GETBANK");
  if(event.hasBank("DC::dgtz")==true){
  	 bank = event.getBank("DC::dgtz");
  }
  bench.pause("GETBANK");

  bench.resume("FILLHISTO");

  for(int cg = 0; cg < 100; cg++){
  if(bank!=null){
  	int rows = bank.rows();
  	//for(int loop = 0; loop < rows; loop++){
  		//double time = bank.getDouble("time",loop);
  		//int    wire = bank.getInt("wire",loop);
  		//h2TDC.fill(wire,time);
  		//h1TDC.fill(time);
  		h1TDC.fill(2675.0);
  		h2TDC.fill(87.0,2675.0);

    //}
  }
  }
  bench.pause("FILLHISTO");

  counter++;
}

System.out.println("  procecessed " + counter + "  events");
System.out.println(bench);