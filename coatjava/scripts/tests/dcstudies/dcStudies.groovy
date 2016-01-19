//----------------------------------------------------------------
// Example Groovy script to read entries from the file and plot
// variables
//-----------------------------------------------------------------

import org.jlab.evio.clas12.*;
import org.jlab.data.histogram.*;
import org.jlab.scichart.canvas.ScCanvas;

filename = args[0];

EvioSource reader = new EvioSource();
reader.open(filename);

int counter = 0;

H1D DC_WIRES   = new H1D("DC_WIRES",140,-5.0,135.0);
H1D DC_SECTORS = new H1D("DC_SECTORS",8,0.0,8.0);
H1D DC_SUPL    = new H1D("DC_SUPL",8,0.0,8.0);
H1D DC_LAYER    = new H1D("DC_LAYER",8,0.0,8.0);

while(reader.hasEvent()==true){
	counter++;
	EvioDataEvent event = reader.getNextEvent();

	//if(event==null) System.err.println("event is null");
	//event.show();
	
	if(event.hasBank("DC::dgtz")==true){
		EvioDataBank  bank  = event.getBank("DC::dgtz");
		//System.err.println(" rows = " + bank.rows());
		int nrows = bank.rows();
		for(int i = 0; i < nrows; i++){
			int wire = bank.getInt("wire",i);
			int sect = bank.getInt("sector",i);
			int supl = bank.getInt("superlayer",i);
			int layr = bank.getInt("layer",i);
			DC_WIRES.fill(wire);
			DC_SECTORS.fill(sect);
			DC_SUPL.fill(supl);
			DC_LAYER.fill(layr);

			//System.err.println(" row = " + i + "  wire = " + wire);
		}
	}
}

System.err.println("processed = " + counter);

ScCanvas canvas = new ScCanvas(1000,800,2,2);
canvas.cd(0);
canvas.draw(DC_WIRES);

canvas.cd(1);
canvas.draw(DC_SECTORS,"*",3);

canvas.cd(2);
canvas.draw(DC_SUPL,"*",2);

canvas.cd(3);
canvas.draw(DC_LAYER);

canvas.canvas().exportPNG("dc_debug.png");
