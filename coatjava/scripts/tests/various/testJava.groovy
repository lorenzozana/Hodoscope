//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.clasrec.utils.*;
import org.jlab.clas.tools.utils.*;

int side   = 1;
int region = 4;


int layer = 2*region - (side==0 ? 1 : 0);
int sector = 4;

//sector = (side==0 ? sector + 1 : sector);
side==0 ? sector++:sector;

System.out.println("LAYER = " + layer + "  SECTOR = " + sector);
