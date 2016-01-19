//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import org.root.data.*;
import java.lang.Math;


DataTable table = new DataTable();
table.readFile("geoMeanHist.txt");

//table.show();
//System.out.println(table.toString());

TSpectrum spectra = new TSpectrum(100);

DataSetXY dataset = table.getDataSet(0,1);
H1D  hist = new H1D("hist",0.0,3000.0,dataset.getDataY().getArray());

spectra.search(hist,1.0,"*");

TCanvas c1 = new TCanvas("c1","JROOT Demo",750,800,1,3);

c1.cd(0);
c1.draw(hist);

c1.cd(1);
spectra.getSearchGraph().setLineColor(4);
spectra.getSearchGraph().setLineWidth(2);
c1.draw(spectra.getSearchGraph(),"L");
c1.setAxisRange(0.0,3000.0,0.0,1.2);


c1.cd(2);
c1.draw(spectra.getSampleGraph());
//c1.setAxisRange(0.0,3000.0,0.0,1.2);
System.out.println(" min/max = " + spectra.getSampleGraph().getDataY().getMin() + "  " + spectra.getSampleGraph().getDataY().getMax());

/*
F1D landau = new F1D("landau+exp",100.0,2800.0);

landau.setParameter(0,800.0);
landau.setParameter(1,1500.0);
landau.setParameter(2,200.0);
landau.setParLimits(2,0.0,400.0);
landau.setParameter(3,20.0);
landau.setParameter(4,0.0);

landau.show();

hist.fit(landau);
hist.setLineColor(4);
landau.show();

c1.setFontSize(14);
c1.cd(0);
c1.draw(hist);
c1.draw(landau,"same");

*/