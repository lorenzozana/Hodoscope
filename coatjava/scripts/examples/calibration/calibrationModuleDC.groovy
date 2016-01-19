import org.jlab.clas12.calib.*;
import org.jlab.clasrec.main.*;
import org.jlab.evio.clas12.*;
import org.root.pad.EmbeddedCanvas;
import org.jlab.clasrec.ui.*;
import org.jlab.clas.detector.DetectorType;
import org.root.histogram.*;
import org.jlab.clas.detector.*;

public class DCCalibration extends DetectorCalibration {
       
       TreeMap<Integer,H2D> hTDC = new TreeMap<Integer,H2D>();
       
       public DCCalibration(){
       	   super("FTOF","","");
           setType(DetectorType.DC);
       }

       public String[] getOptions(){
         return ["Ocupancy","TDC"];
       }

       public void init(){
          hTDC.clear();
          for(int loop = 0; loop < 6; loop++){
            hTDC.put(loop,new H2D("TDC_SECTOR_"+loop,"TDC vs WIRE (SECTOR " + loop + " )",112,-0.5,111.5,120,0.0,200.0));
          }
       }

       public void processEvent(EvioDataEvent event){

          if(event.hasBank("DC::dgtz")==true){
             EvioDataBank bankDC = event.getBank("DC::dgtz");
             int rows = bankDC.rows();
             for(int loop = 0; loop < rows; loop++){
                int    sector = bankDC.getInt("sector",loop);
                int    wire   = bankDC.getInt("wire",loop);
                double time   = bankDC.getDouble("time",loop);
                hTDC.get(sector-1).fill(wire,time);
             }
          }

       }


       public List<DetectorShapeView2D>  getDetectorShapes(){
          List<DetectorShapeView2D>  detectorList = new ArrayList<DetectorShapeView2D>();
          DetectorShapeView2D  dcSectorView = new DetectorShapeView2D("DC Sectors");
          for(int loop = 0; loop < 6; loop++){
            DetectorShape2D shape = new DetectorShape2D();
            shape.createBarXY(28,80);
            shape.getShapePath().translateXYZ(30*loop,0,0);
            shape.setColor(0,200,0);
            shape.getDescriptor().setSectorLayerComponent(loop,0,0);
            dcSectorView.addShape(shape);
          }
          detectorList.add(dcSectorView);
          return detectorList;
       }

       
        public void draw(EmbeddedCanvas canvas, DetectorDescriptor desc, String option){
          System.out.println("DRAWING ON CANVAS : " + desc.toString() );
          System.out.println("\t OPTIONS =      : " + option );
          int component = desc.getComponent();
          int sector    = desc.getSector();

          if(option.compareTo("TDC")==0){
            canvas.divide(1, 1);
            canvas.cd(0);
            canvas.draw(hTDC.get(sector));
          }

        }
        

       public static void main(String[] args){
           DCCalibration calib   = new DCCalibration();
	         CLAS12Desktop desktop = new CLAS12Desktop();
	         desktop.addCalibrationModule(calib);
       }
}
