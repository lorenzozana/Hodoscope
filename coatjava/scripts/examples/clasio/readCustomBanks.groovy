import org.jlab.evio.clas12.*;

EvioFactory.resetDictionary();
EvioFactory.loadDictionary(".");
EvioFactory.getDictionary().show();

EvioSource  reader = new EvioSource();
reader.open("myfirstfile.0.evio");

while(reader.hasEvent()==true){
  EvioDataEvent event = (EvioDataEvent) reader.getNextEvent();
  if(event.hasBank("CUSTOM::detector")==true){
     EvioDataBank  bank = (EvioDataBank) event.getBank("CUSTOM::detector");
     bank.show();
  }
}

