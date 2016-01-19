//-----------------------------------------------------------
// Example Groovy script running analysis on CLAS-6 data
// Just printout events from the file
// author: gavalian date: 10/02/2014
//-----------------------------------------------------------

import org.jlab.io.decode.*

TranslationTable table = new TranslationTable();
table.readFile("../../etc/bankdefs/translation/SVT.table");
System.err.println(table);
