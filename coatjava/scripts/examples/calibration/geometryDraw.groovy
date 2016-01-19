import org.jlab.geom.detector.ftof.*;
import org.jlab.geom.base.*;
import org.jlab.geom.*;
import org.jlab.geom.gui.*;
import org.jlab.geom.prim.*;
import org.jlab.clasrec.utils.*;
import org.jlab.geom.prim.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;

ConstantProvider  ftofDB  = DataBaseLoader.getConstantsFTOF();
FTOFFactory       factory = new FTOFFactory();
FTOFDetector    detector  = factory.createDetectorCLAS(ftofDB);


ArrayList<DetectorComponentUI>  components = new ArrayList<DetectorComponentUI>();
for(int sector = 0; sector < 6; sector++){
   List<DetectorComponentUI>  comp = detector.getLayerUI(sector,0,0);
   components.addAll(comp);
}

ArrayList<DetectorComponentUI>  c2b = new ArrayList<DetectorComponentUI>();
for(int sector = 0; sector < 6; sector++){
   List<DetectorComponentUI>  comp = detector.getLayerUI(sector,2,0);
   c2b.addAll(comp);
}


System.out.println("SIZE = " + components.size());

DetectorLayerUI  layerUI = new DetectorLayerUI();
layerUI.setComponents(components);
layerUI.updateDrawRegion();

DetectorLayerUI  layerUI2B = new DetectorLayerUI();
layerUI2B.setComponents(c2b);
layerUI2B.updateDrawRegion();

JFrame  frame = new JFrame();
DetectorLayerPanel panel1a = new DetectorLayerPanel();
DetectorLayerPanel panel1b = new DetectorLayerPanel();

panel1a.layerUI = layerUI;
panel1b.layerUI = layerUI2B;

DetectorViewPanel view = new DetectorViewPanel();

view.addDetectorLayer("FTOF1A",panel1a);
view.addDetectorLayer("FTOF1B",panel1b);
frame.setLayout(new BorderLayout());
frame.add(view,BorderLayout.CENTER);
frame.pack();
frame.setVisible(true);
