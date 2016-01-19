import org.jlab.clas12.calib.*;
import org.jlab.clasrec.main.*;
import org.jlab.evio.clas12.*;
import org.root.pad.EmbeddedCanvas;
import org.jlab.clasrec.ui.*;
import org.jlab.clas.detector.DetectorType;
import org.root.histogram.*;
import org.jlab.clas.detector.*;

public class FTOFCalibration extends DetectorCalibration {
       
       ArrayList<H1D> hADCL = new ArrayList<H1D>();
       ArrayList<H1D> hADCR = new ArrayList<H1D>();
       ArrayList<H1D> hTDCL = new ArrayList<H1D>();
       ArrayList<H1D> hTDCR = new ArrayList<H1D>();
       
       public FTOFCalibration(){
       	   super("FTOF","","");
           setType(DetectorType.FTOF);
       }

       public String[] getOptions(){
         return ["ADC","TDC"];
       }

       public void init(){
          hADCL.clear();
          hADCR.clear();
          hTDCL.clear();
          hTDCR.clear();
          for(int loop = 0; loop < 23; loop++){
            hADCL.add(new H1D("ADCL_PADDLE_"+loop,200,0.0,4000.0));
            hADCR.add(new H1D("ADCR_PADDLE_"+loop,200,0.0,4000.0));
            hTDCL.add(new H1D("TDCL_PADDLE_"+loop,200,0.0,4000.0));
            hTDCR.add(new H1D("TDCR_PADDLE_"+loop,200,0.0,4000.0));                                    
          }
       }

       public void processEvent(EvioDataEvent event){
       	      System.out.println("processing event");
       }

        public void draw(EmbeddedCanvas canvas, DetectorDescriptor desc, String option){
          System.out.println("DRAWING ON CANVAS : " + desc.toString() );
          System.out.println("\t OPTIONS =      : " + option );
          int component = desc.getComponent();
          if(option.compareTo("ADC")==0){
            canvas.divide(1, 2);
            canvas.cd(0);
            canvas.draw(hADCL.get(component));
            canvas.cd(1);
            canvas.draw(hADCR.get(component));
          }
          if(option.compareTo("TDC")==0){
            canvas.divide(1, 2);
            canvas.cd(0);
            canvas.draw(hTDCL.get(component));
            canvas.cd(1);
            canvas.draw(hTDCR.get(component));
          }

        }
        

       public static void main(String[] args){
           FTOFCalibration calib = new FTOFCalibration();
	         CLAS12Desktop desktop = new CLAS12Desktop();
           
	         desktop.addCalibrationModule(calib);
       }
}
