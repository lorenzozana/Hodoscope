import clasgemc.viewer.*

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;


import org.jlab.geom.detector.ftof.*;
import org.jlab.clasrec.utils.DataBaseLoader;

ConstantProvider data = DataBaseLoader.getConstantsFTOF();
FTOFFactory   factory = new FTOFFactory();
FTOFDetector detector = factory.createDetectorLocal(data);

detframe = new DetectorFrame(detector);

/*
import org.jlab.geom.detector.ec.*;
import org.jlab.clasrec.utils.DataBaseLoader;

ConstantProvider data = DataBaseLoader.getConstantsEC();
ECFactory   factory = new ECFactory();
ECDetector detector = factory.createDetectorLocal(data);

detframe = new DetectorFrame(detector);
*/