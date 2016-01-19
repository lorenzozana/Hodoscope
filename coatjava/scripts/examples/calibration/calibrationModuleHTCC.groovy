//********************************************************************
// This is an example program showing how to extend 
// Calibration class and use it for Detector display and 
// analysis.
// This barebone should be used to fill the code with classes developed
// for specific detector. Please do not write entire code in one class.
// 
//********************************************************************


import org.jlab.clas12.calib.*;
import org.jlab.clasrec.main.*;
import org.jlab.evio.clas12.*;
import org.root.pad.EmbeddedCanvas;
import org.jlab.clasrec.ui.*;
import org.jlab.clas.detector.DetectorType;
import org.root.histogram.*;
import org.jlab.clas.detector.*;


public class HTCCCalibration extends DetectorCalibration {
       
       TreeMap<Integer,H1D> hTDC  = new TreeMap<Integer,H1D>();
       TreeMap<Integer,H2D> hOCC  = new TreeMap<Integer,H2D>();
       
       public HTCCCalibration(){
           // Initialize the calibration super class with a name
           // author and a version. Then set type of the detector.
       	   super("FTOF","me","1.0");
           setType(DetectorType.HTCC);
       }

       public String[] getOptions(){
         // This routine defines options that will be passed
         // to drawing routine. It is used to have ability
         // to draw different sets of histograms for each paddle
         // This is Groovy syntax, in Java use :
         // return new String[]{"Ocupancy","TDC"};
         return ["Ocupancy","TDC"];
       }

       public void init(){
          // Initialize histograms here. This routine will be
          // called for each file that wil then processed through
          // processEvent Method.
          for(int sector = 0 ; sector < 6; sector++){
             for(int layer = 0; layer<6; layer++){
                int key = sector*256 + layer;
                String title = "(SECTOR " + sector + " )  (LAYER  " + layer + " )";
                H1D tdc = new H1D("H1_"+key, "TDC " + title, 200,0.0,4000.0);
                hTDC.put(key,tdc);
                H2D occ = new H2D("H2_"+key, "OCCUPANCY " + title, 40,-0.5,39.5, 200,0.0,4000.0);
                occ.setTitle("OCCUPANCY " + title);
                occ.setXTitle("PADDLE #");
                hOCC.put(key,occ);
             }
          } 
       }

      public void processCalibration(){
        // This method is called by calibration framework within a Swing Thread.
        // It is designed to perform fits and calculations related to calibration
        // If not implemented the default method will be invoked.
      }
    
      public void writeOutput(){
        // This method is called by calibration framework to save the results of calibration
        // It's up to the developer to extend this method.
      }
      
      //System.out.println("processing events");
      // This is the part where the processing single
      // event happens. Filling histograms and status tables...
      public void processEvent(EvioDataEvent event){
         // event processing
      }
       
       public List<DetectorShapeView2D> getDetectorShapes(){
          List<DetectorShapeView2D>  detectorList = new ArrayList<DetectorShapeView2D>();

          DetectorShapeView2D  viewHTCC = new DetectorShapeView2D("HTCC");

          for(int sector = 0; sector < 6; sector++){
            for(int layer = 0; layer < 6; layer++){
              double midangle = 60*sector;
              double innnerRadius = (layer + 1)*50 + 80;
              DetectorShape2D shape = new DetectorShape2D(DetectorType.HTCC,sector,layer,1);
              // this part of code creates a shape as an arc with parameters
              // 1) inner radius, 2) outter radius, 3) start angle (deg) , 4) end angle (deg)
              shape.createArc(innnerRadius,innnerRadius + 40.0,midangle-25,midangle+25);
              // Also Shapes can be created as bars using the following segment of the code.
              // The bar is created in XY plane with the origin being the middle of the bar.
              // after creating it one should translate it to appropriate place with 
              // translation and rotation.
              //shape.createBarXY(80 + layer*20,10);
              //shape.getShapePath().translateXYZ(0.0,15*layer+150.0,0.0);
              //shape.getShapePath().rotateZ(Math.toRadians(midangle));
              //
              // For any custom shape one could just add points to the shape
              //shape.getShapePath().addPoint(-1,1,0);
              //shape.getShapePath().addPoint(-1,-1,0);
              //shape.getShapePath().addPoint(1,0,0);

              shape.setColor(0,145,0);
              viewHTCC.addShape(shape);
            }
          }
          detectorList.add(viewHTCC);
          return detectorList;
       }
       

       public  void  update(DetectorShape2D  shape){
        //System.out.println("-------------->>>>>> UPDATING SHAPE COLORS");
        if(shape.getDescriptor().getLayer()%2==0){
            shape.setColor(0,145,0);
          } else {
            shape.setColor(0,185,0);
          }

          // Let's say that the histograms indicate that there is a problem with 
          // component #1 of Layer #2 of Sector #3, the color of the paddle can be changed 
          // to indicate error (color is up to the user)
          if(shape.getDescriptor().getLayer()==2&&shape.getDescriptor().getSector()==3
            &&shape.getDescriptor().getComponent()==1){
            shape.setColor(255,145,0);
          }
          //shape.setColorByStatus(5);
       }


      public void draw(EmbeddedCanvas canvas, DetectorDescriptor desc, String option){ 
        // Calculate the KEY in the tree map based on the sector and layer
        // this key is defined in the initialization routine.
        int sector = desc.getSector();
        int layer  = desc.getLayer();
        int key    = sector*256 + layer;
        System.out.println("------------>>>>>> DRAWING DETECTOR COMPONENT OPTION = " + option + "  KEY = " + key);
        // Drawing distributions of TDC's
        if(option.compareTo("TDC")==0){
          if(hTDC.containsKey(key)==true){
            canvas.divide(1,1);
            canvas.cd(0);
            canvas.draw(hTDC.get(key));
          }
        }
        // Draw occupancy for given sector and layer.
        // Drawing two panels just for demonstration.
        if(option.compareTo("Ocupancy")==0){
          if(hOCC.containsKey(key)==true){
            canvas.divide(1,2);
            canvas.cd(0);
            canvas.draw(hOCC.get(key));
            canvas.cd(1);
            canvas.draw(hOCC.get(key));
          }
        }
      }
        

       public static void main(String[] args){
           HTCCCalibration calib   = new HTCCCalibration();
           calib.init();
	         CLAS12Desktop desktop = new CLAS12Desktop();
	         desktop.addCalibrationModule(calib);
       }
}
