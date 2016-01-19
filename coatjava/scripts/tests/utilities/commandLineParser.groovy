//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clas.tools.utils.*;


CommandLineTools cmdLine = new CommandLineTools();

cmdLine.addDescription("-i","input file name (evio file)");
cmdLine.addDescription("-o","output file name (optional)");
cmdLine.addDescription("-s","service list separated by ':' (e.g. -s DCHB:FTOF:EC)");
cmdLine.addRequired("-i");
cmdLine.addRequired("-s");

cmdLine.setOptions("-r:-t");
cmdLine.parse(args);

System.err.println(cmdLine.toString());

cmdLine.showUsage();

cmdLine.isComplete();
