//-----------------------------------------------------------
// Example Groovy class that extends the ClasReconstruction
// class and implements all abstruct methods. The reader runs
// the processEvent method for each event in the file. 
//
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

package org.clas.calib;

import org.root.pad.*;
import org.root.histogram.*;
import org.root.func.*;
import java.lang.Math;
import org.clasfx.calib.*;
import org.clasfx.root.*;
import org.jlab.clas12.calib.*;
import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;

public class FTOFDetectorCalibration extends DetectorCalibration {
       
       public FTOFDetectorCalibration(){
           super(DetectorType.FTOF);
       }

       public void processEvent(EvioDataEvent event){
       	if(event.hasBank("FTOF1A::dgtz")==true){
	          EvioDataBank bank = event.getBank("FTOF1A::dgtz");
                int rows   =  bank.rows();
                for(int loop = 0; loop < rows; loop++){
		        int sector =  bank.getInt("sector",loop);
                    int paddle =  bank.getInt("paddle",loop);
                    fill("ADC_LEFT", sector,0,paddle,  (double) bank.getInt("ADCL",loop));
                    fill("ADC_RIGHT", sector,0,paddle, (double) bank.getInt("ADCR",loop));
                } 
	      }
       }
       
       public void init(){
            for(int sector = 0; sector < 6; sector++){
                for(int layer = 0; layer < 3; layer++){
                  for(int component = 0; component < 67; component++){
                        createHistogram("ADC_LEFT"  ,sector,layer,component,100,0.0,3000.0,"gaus+p2");
                        createHistogram("ADC_RIGHT" ,sector,layer,component,100,0.0,3000.0,"gaus+p2");
                        createHistogram("TDC_LEFT"  ,sector,layer,component,100,0.0,3000.0,"gaus+p2");
                        createHistogram("TDC_RIGHT" ,sector,layer,component,100,0.0,3000.0,"gaus+p2");                        
                  }
                }
            }
       }

       @Override
       public void draw(int sector, int layer, int component, TEmbeddedCanvas canvas) {
       	      canvas.divide(2,2);
                  canvas.cd(0);
                  ComponentH1D  chADCL = getHistogram("ADC_LEFT",sector,layer,component);
                  if(chADCL!=null){
                        canvas.draw(chADCL);
                  }
       }
}
