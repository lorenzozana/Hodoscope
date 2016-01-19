import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.data.detector.*;
import org.jlab.clas.detector.*;
import org.jlab.evio.decode.*;


file = args[0];
AbsDetectorTranslationTable table = new AbsDetectorTranslationTable();
table.readFile(file);
System.out.println(table);


