//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas
import org.jlab.data.func.F1D
import org.jlab.data.graph.DataTable
import org.jlab.data.graph.DataSetXY

import java.lang.Math

def canvas = new ScCanvas(1200,1000,1,4);
//canvas.setVisible(true)

F1D func = new F1D("gaus+p2",0.5,2.5);

func.parameter(0).setValue( 10.0);
func.parameter(1).setValue(  1.5);
func.parameter(2).setValue(  0.2);
func.parameter(3).setValue(  5.2);
func.parameter(4).setValue( -4.0);

func.show();

DataSetXY funcData = func.getDataSet();
funcData.show();

canvas.setLabelSize(18);

canvas.cd(0);
canvas.draw(funcData,"*",2);