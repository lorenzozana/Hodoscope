//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas
import org.jlab.data.histogram.H1D
import org.jlab.data.func.F1D
import org.jlab.data.graph.DataTable
import org.jlab.data.graph.DataSetXY
import org.jlab.data.fitter.DataFitter

import java.lang.Math

def canvas = new ScCanvas(600,900,1,3);
//canvas.setVisible(true)

DataTable table = new DataTable();
table.readFile("sampleTable.data");

//table.show();

DataSetXY  data   = table.getDataSet(0,13);
DataSetXY  dataPK = table.getDataSet(0,13,0,31.0,33.5);
DataSetXY  dataSC = table.getDataSet(0,13,0,8.7,10.6);
//DataSetXY  dataCI = data.getDataSetRieman();
//DataSetXY  norm   = data.getDataSetRieman(100);

F1D func = new F1D("gaus+p1",31.0,33.5);

func.parameter(0).set( 1000.0,  0.0, 1000000.0);
func.parameter(1).set(   31.5, 31.0,      35.0);
func.parameter(2).set(    0.2,  0.0,       0.5);
func.parameter(3).set(    5.2,  0.0,      50.0);
func.parameter(4).set(    0.1,-50.0,      50.0);

//norm.show();

F1D funcSC = new F1D("gaus+p2",8.7,10.6);
funcSC.parameter(0).set( 1000.0,  0.0, 1000000.0);
funcSC.parameter(1).set(   9.5,   8.5,      10.5);
funcSC.parameter(2).set(    0.2,  0.0,       0.5);
funcSC.parameter(3).set(    5.2,  0.0,      50.0);
funcSC.parameter(4).set(    0.1,-50.0,      50.0);
//funcSC.parameter(5).set(    0.1,-50.0,      50.0);



DataFitter.fit(dataPK,func);
DataFitter.fit(dataSC,funcSC);

canvas.setLabelSize(18);

canvas.cd(0);
//canvas.canvas().setLogY(0,true);
canvas.draw(dataSC,"*",2);
canvas.draw(funcSC,"*",1);
//canvas.cd(1);
//canvas.draw(dataCI,"*",3);

//canvas.cd(2);
//canvas.draw(norm,"*",4);

canvas.cd(1);
//canvas.canvas().setLogY(1,true);
canvas.draw( dataPK,"*",4);
canvas.draw(   func,"*",1);

func.show();
funcSC.show();

canvas.cd(2);
canvas.draw(data,"*",5);
