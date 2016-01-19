//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.clasrec.utils.*;
import org.jlab.geom.detector.ftof.*;

DatabaseConstantProvider constants = DataBaseLoader.getConstantsFTOF();
System.out.println(constants);

FTOFFactory  factory  = new FTOFFactory();
FTOFDetector detector = factory.createDetectorCLAS(constants);



