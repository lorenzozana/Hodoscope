import org.jlab.geom.detector.bst.*;
import org.jlab.geom.detector.ec.*;
import org.jlab.geom.base.*;
import org.jlab.geom.*;
import org.jlab.geom.prim.*;
import org.jlab.geom.component.*;
import org.jlab.clasrec.utils.*;
import org.jlab.clas.physics.*;

ConstantProvider  ecDB  = DataBaseLoader.getConstantsEC();
ECFactory    ecfactory    = new ECFactory();
ECDetector   ecdetector   = ecfactory.createDetectorCLAS(ecDB);

for(int loop = 0; loop < 60; loop++){

  String color = "<Color red=\"0.2\" green=\"0.2\" blue=\"0.9\" opacity=\"1.0\"/>";
  if(loop%2==0){
     color = "<Color red=\"0.2\" green=\"0.9\" blue=\"0.2\" opacity=\"1.0\"/>";
  }
  String material = "<material>\n<PhongMaterial>\n<diffuseColor>\n"+
      color + "\n</diffuseColor>\n</PhongMaterial>\n</material>\n";


   ScintillatorPaddle  paddle = ecdetector.getSector(0).getSuperlayer(0).getLayer(0).getComponent(loop);
   Shape3D             shape  = paddle.getVolumeShape();
   
   System.out.println("<MeshView id=\"UPADDLE_" + loop + "\">");
   System.out.println(material);
   System.out.println(shape.getMeshFXML());
   System.out.println("</MeshView>");
}
