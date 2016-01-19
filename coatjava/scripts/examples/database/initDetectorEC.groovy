import org.jlab.clasrec.utils.*;
import org.jlab.clas.detector.*;
import org.jlab.geom.base.ConstantProvider;
import org.jlab.geom.detector.ec.*;

ConstantProvider dbgEC   = DataBaseLoader.getGeometryConstants(DetectorType.EC);
ConstantProvider dbgFTOF = DataBaseLoader.getGeometryConstants(DetectorType.FTOF);

ConstantProvider calibFTOF = DataBaseLoader.getCalibrationConstants(DetectorType.FTOF,10,"default");

ECFactory  factory  = new ECFactory();
ECDetector detector = factory.createDetectorCLAS(dbgEC);
