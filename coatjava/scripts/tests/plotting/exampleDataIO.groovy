//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.data.histogram.*
import org.jlab.data.graph.*
import javax.swing.JFrame;
import org.jlab.scichart.canvas.*;
import java.lang.Math

H1D H100 = new H1D("H100","Random","",80,-0.05,2.1)
H1D H200 = new H1D("H200",80,-0.05,2.1)
H1D H300 = new H1D("H300",80,1.95,4.1)

for(int i=0; i<3000; i++){ H100.fill(2.0*Math.random());}
for(int i=0; i<8000; i++){ H200.fill(Math.random()+Math.random()); }
for(int i=0; i<2000; i++){ H300.fill(2.0 + Math.random()+Math.random());}


double[]  X = [  1.0,  2.0,  3.0, 4.0, 5.0 ];
double[]  Y = [ 15.0, 16.0, 32.0, 8.0, 2.0 ];

DataSetXY  graphFTOF = new DataSetXY("FTOF_Sector",X,Y);

HGroup hgroup = new HGroup("Sector_1");

hgroup.add(H100,H200,H300);
hgroup.add(graphFTOF);

hgroup.list();
hgroup.save("histograms.evio");

HGroup ngrp = new HGroup();
ngrp.open("histograms.evio");
ngrp.list();

HDirectory dir = new HDirectory("CUSTOM");
dir.addGroup(ngrp);

JFrame frame = new JFrame();
DirectoryBrowser browser = new DirectoryBrowser(dir,2,3);
frame.add(browser);
frame.pack();
frame.setVisible(true);
