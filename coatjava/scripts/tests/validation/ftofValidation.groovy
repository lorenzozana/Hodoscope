//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import javax.swing.JFrame;
import org.jlab.data.histogram.*;
import org.jlab.scichart.canvas.*;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);

int counter = 0;

HDirectory dir = new HDirectory();

dir.addGroup("FTOF1A");
dir.addGroup("FTOF1B");
dir.addGroup("FTOF2B");
dir.addGroup("FTOFHITS");
dir.addGroup("FTOFCLUSTERS");

for(int s = 1; s < 7;s++) dir.group("FTOF1A").add("Sector_" + s,26,0.0,26.0);
for(int s = 1; s < 7;s++) dir.group("FTOF1B").add("Sector_" + s,65,0.0,65.0);
for(int s = 1; s < 7;s++) dir.group("FTOF2B").add("Sector_" + s,7,0.0,7.0);

for(int s = 1; s < 7;s++) dir.group("FTOFHITS").add("Sector_" + s,20,0.0,20.0);
for(int s = 1; s < 7;s++) dir.group("FTOFCLUSTERS").add("Sector_" + s,7,0.0,20.0);

while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();

	//if(event==null) System.err.println("event is null");
	//event.show();
	
	if(event.hasBank("FTOF1A::dgtz")==true){
		EvioDataBank  bank  = event.getBank("FTOF1A::dgtz");
		//System.err.println(" rows = " + bank.rows());
		int nrows = bank.rows();
		for(int i = 0; i < nrows; i++){
			int sect  = bank.getInt("sector",i);
			int paddl = bank.getInt("paddle",i);
			String name = "Sector_" + sect;
			dir.group("FTOF1A").getH1D(name).fill(paddl);

			//System.err.println(" row = " + i + "  wire = " + wire);
		}
	}

	if(event.hasBank("FTOF1B::dgtz")==true){
		EvioDataBank  bank  = event.getBank("FTOF1B::dgtz");
		//System.err.println(" rows = " + bank.rows());
		int nrows = bank.rows();
		for(int i = 0; i < nrows; i++){
			int sect  = bank.getInt("sector",i);
			int paddl = bank.getInt("paddle",i);
			String name = "Sector_" + sect;
			dir.group("FTOF1B").getH1D(name).fill(paddl);

			//System.err.println(" row = " + i + "  wire = " + wire);
		}
	}

	if(event.hasBank("FTOF2B::dgtz")==true){
		EvioDataBank  bank  = event.getBank("FTOF2B::dgtz");
		//System.err.println(" rows = " + bank.rows());
		int nrows = bank.rows();
		for(int i = 0; i < nrows; i++){
			int sect  = bank.getInt("sector",i);
			int paddl = bank.getInt("paddle",i);
			String name = "Sector_" + sect;
			dir.group("FTOF2B").getH1D(name).fill(paddl);

			//System.err.println(" row = " + i + "  wire = " + wire);
		}
	}


}

JFrame frame = new JFrame();
DirectoryBrowser browser = new DirectoryBrowser(dir,2,3);
frame.add(browser);
frame.pack();
frame.setVisible(true);
