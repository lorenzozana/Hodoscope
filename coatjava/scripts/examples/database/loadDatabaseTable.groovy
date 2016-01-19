import org.jlab.clasrec.utils.*;
import org.jlab.clas.detector.*;
import org.jlab.geom.base.ConstantProvider;

ConstantProvider dbgEC   = DataBaseLoader.getGeometryConstants(DetectorType.EC);
ConstantProvider dbgFTOF = DataBaseLoader.getGeometryConstants(DetectorType.FTOF);

ConstantProvider calibFTOF = DataBaseLoader.getCalibrationConstants(DetectorType.FTOF,10,"default");

