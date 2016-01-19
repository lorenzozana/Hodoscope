#!/usr/bin/groovy

////=========================================================================
// 
//=========================================================================

package monitoring.dc;

import org.jlab.clasrec.main.*; 
import org.jlab.clasrec.utils.*;
import org.jlab.evio.clas12.*;
import org.jlab.clasrec.rec.*;
import org.jlab.data.histogram.*
import org.jlab.scichart.canvas.*;
import javax.swing.JFrame;
import org.jlab.clas.physics.*

class CLASMonitoringDCREC extends DetectorMonitoring {

    private HashMap hgens;
    private List hlist;
    private HashMap plist;
    private HashMap gennum;
    private HashMap recnum;
    private HashMap pidnum;

    public CLASMonitoringDCREC(){
        super("DCREC","1.0","kenjo");
        hgens = new HashMap();
    }

    public void init(){
                
        ////////////////////////////Set Histograms names and ranges///////////////////////////////////////
        plist = ["11":"electron", "2212":"proton", "-211":"pi-"]
        hlist =             ["phires","thetares","pres",  "effvsp",  "effvstheta",    "effvsphi",     "efficiency",   "costheta"]
        HashMap hargs =     [ "11":[[100,-0.01,0.01],[100,-0.005,0.005],[100,-0.2,0.2], [100,0,7], [100,0,0.45],    [100,-3.5,3.5], [5,0,5],        [300,0.71,1.01]],
                "2212":[[100,-0.01,0.01],[100,-0.005,0.005],[100,-0.2,0.2], [100,0,7], [100,0,0.45],    [100,-3.5,3.5], [5,0,5],        [300,0.71,1.01]],
                "-211":[[100,-0.01,0.01],[100,-0.005,0.005],[100,-0.2,0.2], [100,0,7], [100,0,0.45],    [100,-3.5,3.5], [5,0,5],        [300,0.71,1.01]]]

        ////////////////////////////Create histrograms(rec and gen)////////////////////////////////////////////////////
        plist.each{pid, pname->
            HashMap hset = new HashMap();
            hargs[pid].eachWithIndex{args,ind->
                add(pname,hlist[ind], *args)
                hset.put(hlist[ind], new H1D(hlist[ind], *args));
            }
            hgens.put(pname,hset);
        }

        gennum=plist.collectEntries{key, val -> [val,0]};
        recnum=plist.collectEntries{key, val -> [val,0]};
        pidnum=plist.collectEntries{key, val -> [val,0]};

        System.err.println("***********************************");
        getDirectory().show();
        System.err.println("***********************************");
    }

    void analyze(){

    }

    List ExtractParticles(EvioDataBank dbank){
        List parts=[];

        double GeV=1;
        def getVal = dbank.&getFloat;
        if(dbank.getDescriptor().getName().equals("GenPart::true")){
            GeV=0.001;
            getVal = dbank.&getDouble;
        }

        int nrows=dbank.rows();
        for(int irow=0; irow<nrows; irow++){
            Particle part = new Particle(dbank.getInt("pid",irow),
                getVal("px",irow)*GeV, getVal("py",irow)*GeV, getVal("pz",irow)*GeV,
                getVal("vx",irow)*GeV, getVal("vy",irow)*GeV, getVal("vz",irow)*GeV);
            parts.add(part)
        }
        return parts;
    }
    
    void processEvent(EvioDataEvent event){
        if(event.hasBank("GenPart::true") && event.hasBank("EVENTHB::particle")){
            Particle[] pgens = ExtractParticles(event.getBank("GenPart::true"));
            Particle[] precs = ExtractParticles(event.getBank("EVENTHB::particle"));

            for(pgen in pgens){
                def pname = plist[pgen.pid().toString()]    //particle name //implement in Particle class!!!!
            
                if(plist.containsValue(pname)){
                    ///Fill generated eventss
                    [0,0,0, pgen.p(), pgen.theta(), pgen.phi(), 1, 0].eachWithIndex{val, ind->
                        hgens[pname][hlist[ind]].fill(val)}

                    gennum[pname]++

                    def recvals=[];
                    double mincos = -1;
                    double pnum=0, rnum=0;
                
                    for(prec in precs){
                        if(prec.charge()==pgen.charge()){
                            double cost = prec.cosTheta(pgen)
                            if(cost>mincos){
                                recvals=[prec.phi()-pgen.phi(),prec.theta()-pgen.theta(),(prec.p()-pgen.p())/pgen.p(), pgen.p(), pgen.theta(), pgen.phi(), 2, cost]
                                mincos = cost;
                            
                                rnum=1;
                                pnum=prec.pid()==pgen.pid() ? 1:0
                            }
                        }
                    }
                    recvals.eachWithIndex{val,ind -> fill(pname,hlist[ind],val)}
                    recnum[pname]+=rnum
                    pidnum[pname]+=pnum
                }
            }
        }
    }
    
    void processEfficiency(){

        plist.values().each{ pname->
            ["effvsp",  "effvstheta",    "effvsphi"].each{hname->
                getDirectory().group(pname).getH1D(hname).divide(hgens[pname][hname])}

            getDirectory().group(pname).getH1D("efficiency").setBinContent(1,gennum[pname]);
            getDirectory().group(pname).getH1D("efficiency").setBinContent(3,pidnum[pname]);
        }
    }

    void configure(ServiceConfiguration config){

    }

    public static void main(String[] args){
        if(args.length>0){
            String inputFile = args[0];
            System.err.println(" \n[PROCESSING FILE] : " + inputFile);
            CLASMonitoringDCREC  dcrecMonitor = new CLASMonitoringDCREC();
            dcrecMonitor.init();
            CLASMonitoring  monitor = new CLASMonitoring(inputFile, dcrecMonitor);
            monitor.process();
            dcrecMonitor.processEfficiency();

            JFrame frame = new JFrame();
            DirectoryBrowser browser = new DirectoryBrowser(dcrecMonitor.getDirectory(),1,1);
            frame.add(browser);
            frame.pack();
            frame.setVisible(true);
        }
    }
}

