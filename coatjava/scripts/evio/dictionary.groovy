//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.evio.clas12.*
import org.jlab.data.utils.DictionaryLoader;

ArrayList<EvioDataDescriptor> descList = DictionaryLoader.getDescriptorsFromFile("../../etc/bankdefs/clas12/BSTNEW.xml");


for(EvioDataDescriptor desc : descList){
   //desc.show();
}
