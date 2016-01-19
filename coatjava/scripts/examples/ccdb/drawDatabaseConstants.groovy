import org.jlab.clasrec.utils.*;
import org.root.histogram.*;
import org.root.pad.*;


DatabaseConstantProvider  dbc = new DatabaseConstantProvider(10,"default");
dbc.loadTable("/calibration/ftof/attenuation");
dbc.disconnect();
dbc.show();


GraphErrors graph = dbc.getGraph("/calibration/ftof/attenuation/paddle");

TGCanvas c1 = new TGCanvas("c1","CCDB Database Graph",600,400,1,1);
c1.cd(0);
c1.draw(graph);