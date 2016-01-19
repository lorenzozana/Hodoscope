//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas;
import org.jlab.data.histogram.*;
import org.jlab.data.func.*;

H1D H100 = new H1D("H100","Random","",120,0.0,35.0);
F1D func = new F1D("landau+p1",1.0,34.0);

func.parameter(0).set( 1000.0,  0.0, 1000000.0);
func.parameter(1).set(   15.5,  0.0,      35.0);
func.parameter(2).set(    1.2,  0.0,       5.5);
func.parameter(3).set(  500.0,  0.0,   50000.0);
func.parameter(4).set(  -12.0,-50.0,      50.0);

RandomFunc rand = new RandomFunc(func);
for(int loop = 0; loop < 25000; loop++){
    H100.fill(rand.random());
}

F1D funcLandau = new F1D("landau+p2",1.0,34.0);
funcLandau.parameter(0).set( 1000.0);
funcLandau.parameter(1).set( 10.0  );
funcLandau.parameter(2).set( 2.0   );
H100.fit(funcLandau);

def canvas = new ScCanvas(800,600,1,2);

canvas.canvas().draw(0,H100);
canvas.canvas().draw(0,funcLandau);
