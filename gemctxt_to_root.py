#! /usr/bin/env python

# Jim Henderson January 2013
# James.Henderson@cern.ch
#
# Usage:
# lhe2root.py <input_file.lhe> <OPTIONAL: output_file_name.root>
#
# PLEASE NOTE: This conversion was generated to convert les houches 1.0, it may not work on other versions
#              Please check the les houches version # at the top of the .lhe file

import os, sys, math, re

import ROOT as r
from ROOT import TTree, TFile, AddressOf, gROOT


def RepresentsInt(s):
    try: 
        int(s)
        return True
    except ValueError:
        return False

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
else:                    output_file_name = sys.argv[1] + ".root"

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
Layer_v = r.vector('Int_t')()
Sector_v = r.vector('Int_t')()
Id_v  = r.vector('Int_t')()
Edep_v =r.vector('Double_t')()
X_v = r.vector('Double_t')()
Y_v =r.vector('Double_t')()
Z_v =r.vector('Double_t')()

# Create a struct which acts as the TBranch for non-vectors
gROOT.ProcessLine( "struct MyStruct{ Int_t n_hits; Int_t PID_v; Double_t P_X_v; Double_t P_Y_v; Double_t P_Z_v;};")
from ROOT import MyStruct

# Assign the variables to the struct
s = MyStruct() 
output_tree.Branch('N_hits',AddressOf(s,'n_hits'),'n_hits/I')
output_tree.Branch("PID",AddressOf(s,'PID_v'),'PID_v/I')
output_tree.Branch("P_X",AddressOf(s,'P_X_v'),'P_X_v/D')
output_tree.Branch("P_Y",AddressOf(s,'P_Y_v'),'P_Y_v/D')
output_tree.Branch("P_Z",AddressOf(s,'P_Z_v'),'P_Z_v/D')
output_tree.Branch("Edep",Edep_v)
output_tree.Branch("X",X_v)
output_tree.Branch("Y",Y_v)
output_tree.Branch("Z",Z_v)
output_tree.Branch("Layer",Layer_v)
output_tree.Branch("Sector",Sector_v)
output_tree.Branch("Id",Id_v)
in_ev = 0 # To know when to look for particles we must know when we are inside an event
in_ev_1 = 0 # The first line after <event> is information so we must skip that as well
s.n_hits = 0
s.PID_v = 0
s.P_X_v = 0.
s.P_Y_v = 0.
s.P_Z_v = 0.
for line in input_file:
#    if line[:1] == "#":
#        continue
    s.PID_v = 0
    s.P_X_v = 0.
    s.P_Y_v = 0.
    s.P_Z_v = 0.
    parts = line.split()
    if len(parts) > 5:
        if line.split()[4] == "(802," :
            in_ev_1 = 2
#            print "line > 5 and found hodo"
            continue
        else:
            pass
        pass
 
    if in_ev_1 == 2:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            id_val = line.split()[x]
            if RepresentsInt(id_val):
                if int(id_val) >=0 and int(id_val) <29 :
                    Id_v.push_back( int(id_val) )
                else :
                    Id_v.push_back(-1)
            else:
                Id_v.push_back(-1)

            
        in_ev_1 = 3
        continue

    if in_ev_1 == 3:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            layer_val = line.split()[x]
            if RepresentsInt(layer_val):
                if int(layer_val) == 0 or int(layer_val) == 1 :
                    Layer_v.push_back( int(layer_val) )
 #                   print "Layer found %s" % layer_val
                else:
                    Layer_v.push_back(-1)
            else: 
                Layer_v.push_back(-1)
        in_ev_1 = 4
        continue

    if in_ev_1 == 4:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            sector_val = line.split()[x]
            if RepresentsInt(sector_val):
                if int(sector_val) >=0 and int(sector_val) <4 :
                    Sector_v.push_back(int(sector_val))
 #                   print "Sector found %s" % sector_val
                else :
                    Sector_v.push_back(-1)
            else:
                Sector_v.push_back(-1) 
            
        in_ev_1 = 5
        continue

    if in_ev_1 == 5:
        in_ev_1 = 6
        continue

    if in_ev_1 == 6:
        in_ev_1 = 7
        continue

    if in_ev_1 == 7:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            Edep_v.push_back( float(line.split()[x]) )
            
        in_ev_1 = 8
        continue
        
    if in_ev_1 == 8:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            X_v.push_back( float(line.split()[x]) )
            
        in_ev_1 = 9
        continue

    if in_ev_1 == 9:
        parts = line.split()
        num_hit = len(parts)
        for x in range(4, num_hit):
            Y_v.push_back( float(line.split()[x]) )
            
        in_ev_1 = 10
        continue

    if in_ev_1 == 10:
        parts = line.split()
        num_hit = len(parts)
        s.n_hits = int(num_hit) - 4
        for x in range(4, num_hit):
            Z_v.push_back( float(line.split()[x]) )
            
        in_ev_1 = 0
        continue

 
    if len(parts) > 12:
        if line.split()[1] == "Particle" and RepresentsInt(line.split()[4]):
            
#            print "line > 1 Found generated PID=%d" % int(line.split()[4])
            momentum = line.split()[7]
            momentum = momentum.translate(None, '()')
            mom = momentum.split(",")
            s.P_X_v =  float(mom[0])
            s.P_Y_v =  float(mom[1])
            s.P_Z_v =  float(mom[2]) 
            s.PID_v = int(line.split()[4])
            
            output_tree.Fill()
            Layer_v.clear()
            Sector_v.clear()
            Id_v.clear()
            X_v.clear()
            Y_v.clear()
            Z_v.clear()
            Edep_v.clear()
            continue
        else:
            pass
        pass
    continue

        
            

output_tree.Write()
output_file.Close()
