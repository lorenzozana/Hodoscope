//----------------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Uses event filter to select events with electron and proton 
// only as charged particles and any number of neutral paritcles
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------------

import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.data.io.*;
import org.jlab.evio.clas12.*;

public class Eg1DVCSEventMaker implements IEventMaker {

      TreeMap<String,EvioDataBank> bankStore = new TreeMap<String,EvioDataBank>();

      public void initEvent(DataEvent event){
            bankStore.clear();
            if(event.hasBank("EVENT::particle")){
                  bankStore.put("EVNT",event.getBank("EVENT::particle"));
            }
            if(event.hasBank("DETECTOR::ecpb")){
                  bankStore.put("ECPB",event.getBank("DETECTOR::ecpb"));
            }
      }
      //
      // This method needs to be overwritten in the implementation
      //
      public PhysicsEvent createEvent(DataEvent event){ 
            initEvent(event);
            byte[] array = event.getByte(4001,11);
            if(array!=null && bankStore.containsKey("EVNT")){
                  System.err.println(" BYTES = " + array[0] + "  length = " + array.length
                        + " EVNT rows = " + bankStore.get("EVNT").rows());
                  bankStore.get("EVNT").show();
            }           

            PhysicsEvent physEvent = new PhysicsEvent();
            physEvent.setBeam(5.0);
            if(checkElectronID()==true){
                  EvioDataBank bankEVNT = bankStore.get("EVNT");
                  Particle electron = new Particle(11,
                        bankEVNT.getFloat("px",0),
                        bankEVNT.getFloat("py",0),
                        bankEVNT.getFloat("pz",0),
                        bankEVNT.getFloat("vx",0),
                        bankEVNT.getFloat("vy",0),
                        bankEVNT.getFloat("vz",0),
                        );
                  physEvent.addParticle(electron);
            }
            return physEvent;
      }

      public boolean checkElectronID(DataEvent event){
            if(bankStore.containsKey("ECPB")&&bankStore.containsKey("EVNT")){
                  int ecindex  = bankStore.get("EVNT").getByte("ecstat",0);
                  ecindex = ecindex - 1;
                  int ecpbrows = bankStore.get("ECPB").rows();
                  System.err.println(" index = " + ecindex + "   byte = " + bankStore.get("EVNT").getByte("scstat",0));
                  if(ecindex>=0&&ecindex<ecpbrows){
                        float ectotal = bankStore.get("ECPB").getFloat("etot",ecindex);
                        System.err.println(" etotal = " + ectotal);
                  }
            }
            return false;
      }
}

String inputFile = args[0];

EventFilter  filter = new EventFilter("11:2212:2n");

Eg1DVCSEventMaker  eventMaker = new Eg1DVCSEventMaker();

//ClasEvioReader  reader = new ClasEvioReader();
ClasEvioReader  reader = new ClasEvioReader(eventMaker);

reader.addFile(inputFile);
reader.open()

int icounter = 0;
while(reader.next()==true){
	icounter++;	
	PhysicsEvent physEvent = reader.getPhysicsEvent();
	System.err.print(physEvent.toLundString());
}

System.err.println("Processed events = " + icounter);
