//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.root.data.*;
import org.root.pad.*;
import org.root.group.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;

NTuple T = new NTuple("T",28);

T.readFile("harp_electron_example.asc");
T.scan();
T.draw("a:b","b<65&a<37");

TBrowser browser = new TBrowser(T);

//System.out.println(T.toString());
//DataVector vec_a = T.getVector("b","b<73.0");
//DataVector vec_b = T.getVector("m","b<73.0");
//DataSetXY dataSet = new DataSetXY("HARP_SCAN",vec_a,vec_b);
//System.out.println(vec_a.toString());
//TCanvas c1 = new TCanvas("c1","JROOT Demo",600,400);
//c1.setFontSize(18);
//c1.cd(0);
//c1.draw(dataSet);
