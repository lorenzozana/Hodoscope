#!/usr/bin/perl -w

use strict;
use lib ("$ENV{GEMC}/io");
use utils;
use bank;

use strict;
use warnings;

# Help Message
sub help()
{
	print "\n Usage: \n";
	print "   bank.pl <configuration filename>\n";
 	print "   Will define the CLAS12 Central Neutron Detector (CND) banks\n";
 	print "   Note: The passport and .visa files must be present to connect to MYSQL. \n\n";
	exit;
}

# If not pring the help
# Make sure the argument list is correct
if( scalar @ARGV != 1)
{
	help();
	exit;
}

# Loading configuration file and paramters
our %configuration = load_configuration($ARGV[0]);

# One can change the "variation" here if one is desired different from the config.dat
# $configuration{"variation"} = "myvar";

# Variable Type is two chars.
# The first char:
#  R for raw integrated variables
#  D for dgt integrated variables
#  S for raw step by step variables
#  M for digitized multi-hit variables
#  V for voltage(time) variables
#
# The second char:
# i for integers
# d for doubles

sub define_bank
{
	my $bankname = shift;
	my $bankId   = shift;
	
	# uploading the hit definition
	insert_bank_variable(\%configuration, $bankname, "bankid",   $bankId, "Di", "$bankname bank ID");
	insert_bank_variable(\%configuration, $bankname, "layer",          1, "Di", "sector number");
	insert_bank_variable(\%configuration, $bankname, "paddle",         2, "Di", "paddle number");
	insert_bank_variable(\%configuration, $bankname, "ADCD",           3, "Di", "ADC Direct");
	insert_bank_variable(\%configuration, $bankname, "ADCN",           4, "Di", "ADC Neighbor");
	insert_bank_variable(\%configuration, $bankname, "TDCD",           5, "Di", "TDC Direct");
	insert_bank_variable(\%configuration, $bankname, "TDCN",           6, "Di", "TDC Neighbor");
	insert_bank_variable(\%configuration, $bankname, "hitn",          99, "Di", "hit number");
}


sub define_banks
{
	define_bank("cnd", 300);
}


define_banks();


1;