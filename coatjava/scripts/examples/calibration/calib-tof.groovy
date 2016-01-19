import org.jlab.calib.tof.*;
import org.jlab.evio.clas12.*;
import org.root.pad.*;
import org.root.histogram.*;

file = args[0]

EvioSource reader = new EvioSource();
reader.open(file);

TOFGeometricMean  gm = new TOFGeometricMean();
gm.init();

while(reader.hasEvent()){
    EvioDataEvent event = reader.getNextEvent();
    gm.processEvent(event);
}

gm.show();

TGCanvas c1 = new TGCanvas("c1","Geometric Mean",900,800,2,4);
for(int index = 1; index<=8; index++){
    H1D h = gm.getH1D(5,0,index);
    h.setFillColor(24);
    c1.cd(index-1);
    c1.draw(h);
}

gm.customFit(5,0,12);
