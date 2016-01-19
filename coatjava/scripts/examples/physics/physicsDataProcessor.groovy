import org.jlab.evio.clas12.*;
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.physics.*;
import org.jlab.physics.oper.*;


filename = args[0];

EventFilter  filter = new EventFilter("11:X+:X-:Xn");

EvioSource reader = new EvioSource();
reader.open(filename);


GenericKinematicFitter fitter = new GenericKinematicFitter(5.0,"11:X+:X-:Xn");
PhysicsEventProcessor processor = new PhysicsEventProcessor(5.0,"11:2212:X+:X-:Xn");

//processor.addParticle("w2","[b]+[t]-[11]","mass2");
//processor.addParticle("q2","[b]-[11]","mass2");
//processor.addCut("q2cut","q2",1.4,2.8);
//processor.addCut("w2cut","w2",2.4,3.8);
//processor.addHistogram("h1",120,0.2,3.4,"q2","q2cut&w2cut");

processor.parseLine("PARTICLE ettaP     [b]+[t]-[11,0] mass2");
processor.parseLine("PARTICLE ettaTheta [b]+[t]-[11,0] theta");
processor.parseLine("PARTICLE ettaPhi   [b]+[t]-[11,0] phi");
processor.parseLine("CUT      ettaPhiCut    ettaPhi 0.5 0.8");
processor.parseLine("CUT      ettaPhiCut2   ettaPhi 0.9 1.2");
processor.parseLine("HIST     h100          120 0.1 2.4 ettaPhi ettaPhiCut&ettaPhiCut2");

System.out.println(processor);

while(reader.hasEvent()){
  EvioDataEvent event     = reader.getNextEvent();
  PhysicsEvent  physEvent = fitter.getPhysicsEvent(event);
  processor.apply(physEvent);
  System.out.println(physEvent.toLundString());

}

