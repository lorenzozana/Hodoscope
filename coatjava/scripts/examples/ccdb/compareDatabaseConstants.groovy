import org.jlab.clasrec.utils.*;
import org.root.histogram.*;
import org.root.pad.*;




GraphErrors graph = DatabaseConstantUtils.getGraph("/calibration/ftof/attenuation","paddle",10,"default",10,"default");

TGCanvas c1 = new TGCanvas("c1","CCDB Database Graph",600,400,1,1);
c1.cd(0);
c1.draw(graph);