import org.jlab.clasrec.io.*;
import org.jlab.clas.physics.*;
import org.jlab.clas.reactions.*;
import org.jlab.clas.physics.*;


double theta = 20.0/57.29;
double epmom = 5.0;

Particle gamma = new Particle( 22, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0);

Particle pion  = new Particle(211, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0);

Particle beam  = new Particle(11, 0.0, 0.0, 11.0, 0.0,0.0,0.0);
Particle eprime = new Particle(11, epmom*Math.sin(theta),0.0, epmom*Math.cos(theta),0.0,0.0,0.0);
Particle gammaStar = new Particle();
Particle target    = new Particle(2212,0.0,0.0,0.0,0.0,0.0,0.0);

gammaStar.combine(beam,+1);
gammaStar.combine(eprime,-1);

System.out.println(gammaStar);
System.out.println("--");

Particle pion_f = PhysicsKinematics.getParticleUnBoosted(gammaStar,pion,false);

System.out.println(pion);
System.out.println(pion_f);
