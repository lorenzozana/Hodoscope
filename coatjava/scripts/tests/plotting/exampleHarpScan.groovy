//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas
import org.jlab.data.histogram.H1D
import org.jlab.data.graph.DataTable
import org.jlab.data.graph.DataSetXY
import org.jlab.data.utils.PeakFinder
import java.lang.Math

def canvas = new ScCanvas(1200,1000,1,4);

//canvas.setVisible(true)

DataTable table = new DataTable();
table.readFile("harp_tagger_04-06-07_13:12:15.txt");

//table.show();

DataSetXY  data   = table.getDataSet(0,15);
//DataSetXY  dataPK = table.getDataSet(0,13,0,31.0,33.5);
DataSetXY  dataCI = data.getDataSetRieman();
DataSetXY  norm   = data.getDataSetRieman(100);

System.err.println("POINTS = " + data.getDataX().getSize() + "  " + norm.getDataX().getSize() );

PeakFinder  peak = new PeakFinder(2.0);
peak.doClustering(norm);
//norm.show();

canvas.setLabelSize(18);

canvas.cd(0);
canvas.draw(data,"*",2);
//canvas.canvas().setLogY(0,true);
canvas.cd(1);
canvas.draw(dataCI,"*",3);

canvas.cd(2);
canvas.draw(norm,"*",4);

//canvas.cd(3);
//canvas.draw(dataPK,"*",7);
//int npeaks = 
def plots  = new ScCanvas(1200,1000,1,4);
