// Program reads geometry constants from CCDB database
import org.jlab.clasrec.utils.*;
import org.jlab.geom.base.ConstantProvider;
import org.root.pad.*;
import org.root.histogram.*;

// initialize database connection provider for RUN #=10 and variation = default
DatabaseConstantProvider  dbprovider = new DatabaseConstantProvider(10,"default");

// load table reads antire table and makes an array of variables for each column in the table.
dbprovider.loadTable("/calibration/ftof/attenuation");

// disconncect from databas.
dbprovider.disconnect();

// printout names of columns and lengths for each variable
dbprovider.show();


for(int loop = 0; loop < dbprovider.length("/calibration/ftof/attenuation/y_offset"); loop++){
	double value = dbprovider.getDouble("/calibration/ftof/attenuation/y_offset",loop);
	// for integer values use dbprovider.getInteger("/calibration/ftof/attenuation/y_offset",loop);
}

GraphErrors  graph = dbprovider.getGraph("/calibration/ftof/attenuation/y_offset");
TGCanvas    canvas = new TGCanvas("canvas","Calibration",600,300,1,1);

graph.setTitle("FTOF CALIBRATION CONSTANTS");
graph.setXTitle("FTOF PADDLE");
graph.setYTitle("Y OFFSET");

canvas.cd(0);
canvas.draw(graph);