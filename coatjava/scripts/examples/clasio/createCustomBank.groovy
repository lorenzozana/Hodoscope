import org.jlab.evio.clas12.*;

EvioFactory.resetDictionary();
EvioFactory.loadDictionary(".");
EvioFactory.getDictionary().show();

EvioFactory.getDictionary().getDescriptor("CUSTOM::detector").show();

EvioDataBank   bank = (EvioDataBank) EvioFactory.getDictionary().createBank("CUSTOM::detector",2);
bank.show();
//************************************************************************
// Set values 
//************************************************************************
bank.setByte("detectorid", 0, (byte) 15);
bank.setByte("pindex",     0, (byte)  1);
bank.setFloat("X",         0,  2.34);
bank.setFloat("Y",         0,  3.45);
bank.setFloat("Y",         0,  4.78);
bank.setFloat("time",      0,  0.34);
bank.setFloat("energy",    0,  1.23);
bank.show();

//************************************************************************
//  Write events to a file
//************************************************************************

EvioDataSync  writer = new EvioDataSync();
writer.open("myfirstfile.evio");

for(int loop = 0; loop < 10; loop++){
  EvioDataEvent event = (EvioDataEvent) writer.createEvent();
  EvioDataBank   bankDET = (EvioDataBank) EvioFactory.getDictionary().createBank("CUSTOM::detector",2);
  EvioDataBank   bankPRT = (EvioDataBank) EvioFactory.getDictionary().createBank("CUSTOM::part",1);
  // Fill detector bank 
  bankDET.setByte("detectorid", 0, (byte) 15);
  bankDET.setByte("pindex",     0, (byte)  1);
  bankDET.setFloat("X",         0,  2.34);
  bankDET.setFloat("Y",         0,  3.45);
  bankDET.setFloat("Y",         0,  4.78);
  bankDET.setFloat("time",      0,  0.34);
  bankDET.setFloat("energy",    0,  1.23);
  // Fill particle bank
  bankPRT.setByte ("status",     0, (byte) 2);
  bankPRT.setShort("charge",     0, (short) +1);
  bankPRT.setInt  ("pid"   ,     0, 2212);
  bankPRT.setFloat("px",    0,  0.489 );
  bankPRT.setFloat("py",    0,  0.703 );
  bankPRT.setFloat("pz",    0,  0.982 );
  event.appendBanks(bankPRT,bankDET);
  writer.writeEvent(event);
}
writer.close();
