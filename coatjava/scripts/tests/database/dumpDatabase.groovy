
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.jlab.ccdb.Assignment;
import org.jlab.ccdb.CcdbPackage;
import org.jlab.ccdb.JDBCProvider;
import org.jlab.ccdb.TypeTable;
import org.jlab.ccdb.TypeTableColumn;
//import org.jlab.geom.base.ConstantProvider;

JDBCProvider provider = CcdbPackage.createProvider("mysql://clas12reader@clasdb.jlab.org/clas12");
Assignment asgmt = provider.getData("/geometry/pcal/pcal");
int ncolumns = asgmt.getColumnCount();

System.out.println("COLUMNS = " + ncolumns);
