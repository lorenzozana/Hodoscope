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
import org.jlab.data.func.RandomFunc
import org.jlab.data.fitter.DataFitter

import java.lang.Math

def canvas = new ScCanvas(600,800,1,2);

H1D H100 = new H1D("H100","Random","",120,0.0,35.0);

F1D func = new F1D("gaus+p1",1.0,34.0);

func.parameter(0).set( 1000.0,  0.0, 1000000.0);
func.parameter(1).set(   15.5, 31.0,      35.0);
func.parameter(2).set(    2.8,  0.0,       0.5);
func.parameter(3).set(  500.0,  0.0,      50.0);
func.parameter(4).set(  -12.0,-50.0,      50.0);

RandomFunc rand = new RandomFunc(func);

for(int loop = 0; loop < 25000; loop++){
    H100.fill(rand.random());
}

canvas.setLabelSize(18);

canvas.cd(0);
canvas.draw(func,"*",1);

canvas.cd(1);
canvas.draw(H100,"*",3);

F1D f2 = new F1D("gaus+p1",5.0,30.0);
f2.parameter(0).setValue(100.0);
f2.parameter(1).setValue(15.0);
f2.parameter(2).setValue(3.5);

DataFitter.fit(H100.getDataSet(),f2);
canvas.draw(f2,"*",2);

f2.show();
System.err.println("CHI = " + f2.getChiSquare(H100.getDataSet()) + "  NDF = " + f2.getNDF(H100.getDataSet()));

