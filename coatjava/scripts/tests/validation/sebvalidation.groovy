#!/usr/bin/groovy

////=========================================================================
// 
//=========================================================================

package monitoring.dc;

import org.jlab.clasrec.main.*; 
import org.jlab.clasrec.utils.*;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.rec.*;
import org.jlab.clas.physics.*;
import org.root.group.*;
import org.root.group.*;
import org.root.pad.*;
import org.root.histogram.*;
import org.jlab.mon.eb.*;

String inputFile = args[0];
System.err.println(" \n[PROCESSING FILE] : " + inputFile);
CLASMonitoringSEB  dcrecMonitor = new CLASMonitoringSEB();
dcrecMonitor.init();
CLASMonitoring  monitor = new CLASMonitoring(inputFile, dcrecMonitor);
monitor.process();
DirectoryViewer browser = new DirectoryViewer(dcrecMonitor.getDirectory());
