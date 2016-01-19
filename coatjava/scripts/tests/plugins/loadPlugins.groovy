//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.rec.CLASReconstruction
import org.jlab.clasrec.main.DetectorReconstruction

CLASReconstruction rec = new CLASReconstruction();
rec.setDetectors("FTOF:EC");
rec.initPlugins();
rec.initDetectors();
