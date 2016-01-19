//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------
import javax.swing.JFrame;
import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import org.root.data.*;
import org.root.group.*;
import org.root.pad.*;
import java.lang.Math;

PlotDirectory  dir   = new PlotDirectory();
dir.setName("SVT");

PlotGroup      group01 = new PlotGroup("barrel_1/efficiency/001");
PlotGroup      group02 = new PlotGroup("barrel_1/efficiency/002");
PlotGroup      group03 = new PlotGroup("barrel_1/efficiency/003");

double[] x = [1.0, 2.0, 3.0, 4.0];
double[] y = [1.0, 2.0, 3.0, 4.0];

H1D heff = new H1D("EFFHist",100,0.0,1.2);

for(int l = 0; l < 100; l++){
   heff.fill(Math.random());
}

group01.add("EFFHist", heff);
group01.add("Recon", new H1D("Recon",100,0.0,1.2));
group01.add("Occupancy", new DataSetXY("Occupancy",x,y));

group02.add("EFFHist", new H1D("EFFHist",100,0.0,1.2));
group02.add("Recon", new H2D("Recon",100,0.0,1.2,100,0.0,1.2));

group03.add("EFFHist", new H1D("EFFHist",100,0.0,1.2));
group03.add("Recon", new H1D("Recon",100,0.0,1.2));

dir.addGroup(group01);
dir.addGroup(group02);
dir.addGroup(group03);

//dir.list();
dir.write("outputHistogram.evio");

PlotDirectory dir2 = new PlotDirectory("aaaa");
dir2.readFile("outputHistogram.0.evio");
dir2.list();

JFrame frame = new JFrame();
RootCanvas canvas = new RootCanvas(800,800,1,1);
frame.add(canvas);
frame.pack();
frame.setVisible(true);
canvas.divide(2,2);
H1D hist = (H1D) dir.getGroup("barrel_1/efficiency/001").getObjects().get("EFFHist");
DataSetXY occ = (DataSetXY) dir.getGroup("barrel_1/efficiency/001").getObjects().get("Occupancy");
canvas.draw(0,hist);
canvas.draw(1,occ);
