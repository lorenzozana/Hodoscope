//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import javax.swing.JFrame;
import org.jlab.data.histogram.*;
import org.jlab.scichart.canvas.*;

HDirectory dir = new HDirectory();

dir.addGroup("FTOF1A");
dir.addGroup("FTOF1B");
dir.addGroup("FTOF2B");

for(int s = 1; s < 7;s++) dir.group("FTOF1A").add("Sector_" + s,26,0.0,26.0);
for(int s = 1; s < 7;s++) dir.group("FTOF1B").add("Sector_" + s,65,0.0,65.0);
for(int s = 1; s < 7;s++) dir.group("FTOF2B").add("Sector_" + s,7,0.0,7.0);

dir.show();


HDirectory dir2 = new HDirectory();

dir2.add("FTOF1A","Sector_1",100,0.0,100.0);

dir2.show();
