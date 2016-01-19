//****************************************************
// Particle generator with decay module
//****************************************************
import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;
import org.jlab.clas12.fastmc.*;
import org.jlab.geom.prim.*;
import org.jlab.geom.*;
import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;
import org.jlab.clas.tools.utils.*;

ParticleGenerator  electron = new ParticleGenerator(  -211 , // electron PID  
		   	     0.5  ,    1.5, // momentum min/max 
		   	     89.0 ,   91.0, // theta (deg) min/max
			   89.0  ,  91.0 ); // phi (deg) min/max


BSTDetectorFastMC  bstDet = new BSTDetectorFastMC();

CLAS12FastMC    fastMC = new CLAS12FastMC(-1.0,1.0);
ParticleSwimmer pswim = new ParticleSwimmer();

int countrec = 0;
int count    = 0;

EvioDataSync  writer = new EvioDataSync();
writer.open("bstoutput.evio");

for(int loop = 0; loop < 1; loop++){
  
  EvioDataEvent event = writer.createEvent(EvioFactory.getDictionary());

  Particle ep = electron.getParticle();
  Path3D   epath = pswim.particlePath(ep);
  //epath.show();
  count++;
  List<DetectorHit> hits = bstDet.getHits(epath);
  System.out.println("   SIZE = " + hits.size());
  EvioDataBank bank = EvioFactory.createBank("BST::dgtz",hits.size());
  EvioDataBank gen  = EvioFactory.createBank("GenPart::true",1);
  gen.setInt("pid",0,-211);
  gen.setDouble("px",0,ep.px()*1000.0);
  gen.setDouble("py",0,ep.py()*1000.0);
  gen.setDouble("pz",0,ep.pz()*1000.0);

  for(int bv = 0; bv < hits.size(); bv++){
    int region = hits.get(bv).getSuperlayerId();
    int layer  = hits.get(bv).getLayerId();
    int comp   = hits.get(bv).getComponentId();
    int nlayer = region*2 + layer;
    bank.setInt("sector",bv,hits.get(bv).getSectorId()+1);
    bank.setInt("layer",bv,nlayer+1);
    bank.setInt("strip",bv,comp+1);
    bank.setInt("bco",bv,128);
    bank.setInt("ADC",bv,7);
  }

  event.appendBanks(gen);
  event.appendBanks(bank);
  writer.writeEvent(event);
  //gen.show();
  //bank.show();
  for(DetectorHit hit : hits){
    //System.out.println(hit);
  }

}
writer.close();
System.out.println("\n\n Reconstructed event " + countrec + "/" + count);


double theta = Math.toRadians(89.27);
double phi   = Math.toRadians(90.195);
double mom   = 1.326;
double px = mom*Math.sin(theta)*Math.cos(phi);
double py = mom*Math.sin(theta)*Math.sin(phi);
double pz = mom*Math.cos(theta);

Particle pion = new Particle(-211,px,py,pz,0.0,0.0,0.0);
piPath = pswim.particlePath(pion);
List<DetectorHit> bhits = bstDet.getHits(piPath);
for(DetectorHit hit : bhits){
    System.out.println(hit);
}
