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

def canvas = new ScCanvas(1200,1200,1,1);
//canvas.setVisible(true)

def H100 = new H1D("H100","Random","",40,-0.05,2.1)
def H200 = new H1D("H200",80,-0.05,2.1)

def H300 = new H1D("H200",80,1.95,4.1)


for(int i=0; i<3000; i++){
   H100.fill(Math.random()+Math.random());
}

for(int i=0; i<8000; i++){
   H200.fill(Math.random()+Math.random());
}

for(int i=0; i<2000; i++){
   H300.fill(2.0+ Math.random()+Math.random());
}

//canvas.setMargins(0.3,0.05,0.05,0.3);
canvas.setLabelSize(18);

canvas.canvas().addPoints(0, H100.getAxis().getBinCenters(),H100.getData(),4,1,8,5)
canvas.canvas().addPoints(0, H200.getAxis().getBinCenters(),H200.getData(),2,7,14,6)
