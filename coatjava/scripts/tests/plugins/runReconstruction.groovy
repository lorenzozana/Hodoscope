//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.rec.CLASReconstruction
import org.jlab.clasrec.main.DetectorReconstruction

String filename = args[0];

CLASReconstruction rec = new CLASReconstruction();
rec.setDetectors("DCHB:DCTB:FTOF:EC:EB");
rec.initPlugins();
rec.run(filename,"rec_output.evio");
