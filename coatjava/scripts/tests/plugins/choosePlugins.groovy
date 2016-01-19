//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------
import org.jlab.clasrec.loader.*;

ClasPluginChooseDialog dialog = new ClasPluginChooseDialog("org.jlab.clasrec.main.DetectorReconstruction");
dialog.setVisible(true);

