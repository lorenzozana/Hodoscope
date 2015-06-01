#!/usr/bin/perl -w

use strict;
use lib ("$ENV{GEMC}/io");
use utils;
use materials;

# Help Message
sub help()
{
	print "\n Usage: \n";
	print "   materials.pl <configuration filename>\n";
 	print "   Will create the CLAS12 Forward Tagger (ft) materials\n";
 	print "   Note: The passport and .visa files must be present to connect to MYSQL. \n\n";
	exit;
}

# Make sure the argument list is correct
# If not pring the help
if( scalar @ARGV != 1)
{
	help();
	exit;
}

# TODO:
# some materials that are now taken from the G4 DB should be checked:
# Tungsten, SteinlessSteel, Mylar
# also PCB composition should be checked and VM2000 added 


# Loading configuration file and paramters
our %configuration = load_configuration($ARGV[0]);

# One can change the "variation" here if one is desired different from the config.dat
# $configuration{"variation"} = "myvar";

sub print_materials
{
	# uploading the mat definition
	
	
	# peek
	my %mat = init_mat();
	$mat{"name"}          = "peek";
	$mat{"description"}   = "ft peek plastic 1.31 g/cm3";
	$mat{"density"}       = "1.31";
	$mat{"ncomponents"}   = "3";
	$mat{"components"}    = "G4_C 0.76 G4_H 0.08 G4_O 0.16";
	print_mat(\%configuration, \%mat);

	
	# epoxy
	%mat = init_mat();
	$mat{"name"}          = "epoxy";
	$mat{"description"}   = "epoxy glue 1.16 g/cm3";
	$mat{"density"}       = "1.16";
	$mat{"ncomponents"}   = "4";
	$mat{"components"}    = "H 32 N 2 O 4 C 15";
	print_mat(\%configuration, \%mat);
	
	
	# carbon fiber
	%mat = init_mat();
	$mat{"name"}          = "carbonFiber";
	$mat{"description"}   = "ft carbon fiber material is epoxy and carbon - 1.75g/cm3";
	$mat{"density"}       = "1.75";
	$mat{"ncomponents"}   = "2";
	$mat{"components"}    = "G4_C 0.745 epoxy 0.255";
	print_mat(\%configuration, \%mat);

	
	# pcboard
	%mat = init_mat();
	$mat{"name"}          = "pcboard";
	$mat{"description"}   = "ft pcb 1.86 g/cm3";
	$mat{"density"}       = "1.86";
	$mat{"ncomponents"}   = "3";
	$mat{"components"}    = "G4_Fe 0.3 G4_C 0.4 G4_Si 0.3";
	print_mat(\%configuration, \%mat);
	

	# ftinsfoam
	%mat = init_mat();
	$mat{"name"}          = "insfoam";
	$mat{"description"}   = "ft insulation foam 34 kg/m3";
	$mat{"density"}       = "0.034";
	$mat{"ncomponents"}   = "4";
	$mat{"components"}    = "G4_C 0.6 G4_H 0.1 G4_N 0.1 G4_O 0.2";
	print_mat(\%configuration, \%mat);
	

	# scintillator
	%mat = init_mat();
	$mat{"name"}          = "scintillator";
	$mat{"description"}   = "ft scintillator material C9H10 1.032 g/cm3";
	$mat{"density"}       = "1.032";
	$mat{"ncomponents"}   = "2";
	$mat{"components"}    = "C 9 H 10";
	print_mat(\%configuration, \%mat);


	# micromegas strips
	my $mmstriptransparency = 300./400.;
	my $mmstripdensity = 8.96*$mmstriptransparency;
	%mat = init_mat();
	$mat{"name"}          = "mmstrips";
	$mat{"description"}   = "ft micromegas strips";
	$mat{"density"}       = $mmstripdensity;
	$mat{"ncomponents"}   = "1";
	$mat{"components"}    = "G4_Cu 1";
	print_mat(\%configuration, \%mat);
#	G4Material *MMStrips = new G4Material("Copper", z=29, a=   63.55*g/mole, density = 8.960*MMStripTransparency*g/cm3);
	

	# micromegas mesh
#	my $mmmeshtransparency = (19./50.)*(19./50.);
	my $mmmeshtransparency = 1.0;
	my $mmmeshdensity = 8.02*$mmmeshtransparency;
	%mat = init_mat();
	$mat{"name"}          = "mmmesh";
	$mat{"description"}   = "ft micromegas mesh";
	$mat{"density"}       = $mmmeshdensity;
	$mat{"ncomponents"}   = "5";
	$mat{"components"}    = "G4_Mn 0.02 G4_Si 0.01 G4_Cr 0.19 G4_Ni 0.10 G4_Fe 0.68";
	print_mat(\%configuration, \%mat);

	
	# micromegas gas
	my $mmgasdensity = (1.662*0.95+2.489*0.05)*0.001; 
	%mat = init_mat();
	$mat{"name"}          = "mmgas";
	$mat{"description"}   = "ft micromegas gas";
	$mat{"density"}       = $mmgasdensity;
	$mat{"ncomponents"}   = "3";
	$mat{"components"}    = "G4_Ar 0.95 G4_H 0.0086707 G4_C 0.0413293";
	print_mat(\%configuration, \%mat);


	# micromegas mylar
	%mat = init_mat();
	$mat{"name"}          = "mmmylar";
	$mat{"description"}   = "ft micromegas mylar 1.40g/cm3";
	$mat{"density"}       = "1.4";
	$mat{"ncomponents"}   = "3";
	$mat{"components"}    = "G4_H 0.041958 G4_C 0.625017 G4_O 0.333025";
	print_mat(\%configuration, \%mat);
	
}

print_materials();

