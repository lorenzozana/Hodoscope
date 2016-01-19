import org.jlab.coda.jevio.*;

file = args[0];
EvioReader reader = new EvioReader(file,true,true);

int counter = 0;

while(true){
  try {
      EvioEvent  event = reader.parseNextEvent();
      counter++;
  } catch (Exception e){
    System.out.println("END OF FILE REACHED");
    break;
  }
  //if(event==null) break;
}

System.out.println("PROCESSED EVENTS = " + counter);
