import org.jlab.clasrec.utils.*;


DatabaseConstantProvider  dbc = new DatabaseConstantProvider(10,"default");
dbc.loadTable("/geometry/pcal/pcal");
dbc.loadTable("/geometry/pcal/Uview");
dbc.loadTable("/geometry/pcal/Vview");
dbc.loadTable("/geometry/pcal/Wview");
dbc.disconnect();
dbc.show();
