import org.jlab.evio.clas12.*;
import org.jlab.clas12.raw.*;
import org.jlab.io.decode.*;
import org.jlab.data.detector.*;

AbsDetectorTranslationTable table = new AbsDetectorTranslationTable();
table.readFile("CTOF.table");

System.out.println(table);
