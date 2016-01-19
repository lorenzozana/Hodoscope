//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.scichart.canvas.ScCanvas
import org.jlab.data.histogram.H1D
import java.lang.Math

def canvas = new ScCanvas(1200,1200,1,3);

def H100 = new H1D("H100","Random","",80,-0.05,2.1)
def H200 = new H1D("H200",80,-0.05,2.1)
def H300 = new H1D("H200",80,1.95,4.1)

for(int i=0; i<3000; i++){ H100.fill(2.0*Math.random());}
for(int i=0; i<8000; i++){ H200.fill(Math.random()+Math.random()); }
for(int i=0; i<2000; i++){ H300.fill(2.0 + Math.random()+Math.random());}

canvas.setLabelSize(18);

canvas.cd(0);
canvas.draw(H100);

canvas.cd(1);
canvas.draw(H300,"*",4);

canvas.cd(2);

canvas.draw(H200,"*",8);
canvas.draw(H100,"same",2);
canvas.draw(H300,"same",5);
