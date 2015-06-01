#! /usr/bin/env python

# Jim Henderson January 2013
# James.Henderson@cern.ch
#
# Usage:
# lhe2root.py <input_file.lhe> <OPTIONAL: output_file_name.root>
#
# PLEASE NOTE: This conversion was generated to convert les houches 1.0, it may not work on other versions
#              Please check the les houches version # at the top of the .lhe file

import os, sys, math
import ROOT as r
from ROOT import TTree, TFile, AddressOf, gROOT

# Get the input lhe file
if len(sys.argv) < 2:
    print "\nYou must enter the .lhe file you wish to convert as the first arguement. Exiting \n"
    sys.exit(1)

try:    input_file = file( sys.argv[1], 'r')
except:
    print "\nThe entered file cannot be opened, please enter a vaild .lhe file. Exiting. \n"
    sys.exit(1)
    pass

if len(sys.argv) > 2:    output_file_name = sys.argv[2]
else:                    output_file_name = "lhe.root"

try:    output_file = TFile(output_file_name, "RECREATE")
except:
    print "Cannot open output file named: " + output_file_name + "\nPlease enter a valid output file name as the 2nd arguement. Exiting"
    sys.exit(1)
    pass

output_tree = TTree("Physics", "Physics")
print "Setup complete \nOpened file " + str(sys.argv[1]) + "  \nConverting to .root format and outputing to " + output_file_name

# Set Beam Energy (in GeV)
Beam_ener = 5.0

# Setup output branches
PID_v = r.vector('Int_t')()
P_X_v = r.vector('Double_t')()
P_Y_v =r.vector('Double_t')()
P_Z_v =r.vector('Double_t')()
E_v =r.vector('Double_t')()
M_v =r.vector('Double_t')()

# Create a struct which acts as the TBranch for non-vectors
gROOT.ProcessLine( "struct MyStruct{ Int_t n_particles; };")
from ROOT import MyStruct

# Assign the variables to the struct
s = MyStruct() 
output_tree.Branch('n_particles',AddressOf(s,'n_particles'),'n_particles/I')
output_tree.Branch("PID",PID_v)
output_tree.Branch("P_X",P_X_v)
output_tree.Branch("P_Y",P_Y_v)
output_tree.Branch("P_Z",P_Z_v)
output_tree.Branch("E",E_v)
output_tree.Branch("M",M_v)

in_ev = 0 # To know when to look for particles we must know when we are inside an event
in_ev_1 = 0 # The first line after <event> is information so we must skip that as well
s.n_particles = 0
for line in input_file:
#    if line[:1] == "#":
#        continue
    
    if in_ev_1 == 1:
        in_ev_1 = 0
        in_ev = 1
        continue
    
    if line.startswith("<event>"):
        in_ev_1 = 2
        continue

    if in_ev_1 == 2:
        s.n_particles = int(line.split()[0]) + 1
        s.weight = float(line.split()[2])
        in_ev_1 = 1
        continue
    
    # Some versions of les houches have a pdf line that we don't care about here
    if line.startswith("#pdf"):
        continue
    
    if in_ev == 1 and line.startswith("</event>"):
        output_tree.Fill()
        # Reset variables
        s.n_particles = 0
        PID_v.clear()
        P_X_v.clear()
        P_Y_v.clear()
        P_Z_v.clear()
        E_v.clear()
        M_v.clear()
        in_ev = 0
        continue
    
    if in_ev == 1:
        if line.split()[0] == "#" and line.split()[1] == "14":
            nu = float(line.split()[2])
            Q2 = float(line.split()[3])
            phi = float(line.split()[5])
            Ep = Beam_ener - nu
            if not Ep == 0:
                sin2theta = Q2/(Beam_ener*Ep)
            else:
                sin2theta = 0.
            PID_v.push_back(11)
            E_v.push_back(Ep)
            M_v.push_back(0.000511)
            P_Z_v.push_back(Ep*((1-sin2theta)**-2))
            P_X_v.push_back(Ep*(sin2theta**-2)*math.cos(phi))
            P_Y_v.push_back(Ep*(sin2theta**-2)*math.sin(phi))
        else:    
            PID_v.push_back( int(line.split()[0]) )
            P_X_v.push_back( float(line.split()[6]) )
            P_Y_v.push_back( float(line.split()[7]) )
            P_Z_v.push_back( float(line.split()[8]) )
            E_v.push_back( float(line.split()[9]) )
            M_v.push_back( float(line.split()[10]) )
        pass
    pass

output_tree.Write()
output_file.Close()
