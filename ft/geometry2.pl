#!/usr/bin/perl 
use strict;
use warnings;
use Math::Trig;

our %configuration;




###########################################################################################
###########################################################################################
# Define the relevant parameters of FT Geometry
#
# the FT geometry will be defined starting from these parameters 
# and the position on the torus inner ring
#
# all dimensions are in mm
#
# the geometry parameters are divided in 4 groups:
#     beamline and shield
#     calorimeter
#     hodoscope
#     tracker

my $degrad    = 57.27;
my $torus_z   = 2663.; # position of the front face of the Torus ring (set the limit in z)



###########################################################################################
# CALORIMETER
#
# Define the number, dimensions and position of the crystals
my $Nx = 22;                          # Number of crystals in horizontal directions
my $Ny = 22;                          # Number of crystals in horizontal directions
my $Cfront  =  1898.0;                # Position of the front face of the crystals
my $Cwidth  =    15.0;                # Crystal width in mm (side of the squared front face)
my $Clength =   200.0;                # Crystal length in mm
my $VM2000  =   0.130;                # Thickness of the VM2000 wrapping
my $AGap    =   0.170;                # Air Gap between Crystals, total wodth of crystal including wrapping and air gap is 15.3 mm
my $Flength =     8.0;                # Length of the crystal front support
my $Fwidth  = $Cwidth;                # Width of the crystal front support
my $Wwidth  = $Cwidth+$VM2000;        # Width of the wrapping volume
my $Vwidth  = $Cwidth+$VM2000+$AGap;  # Width of the crystal mother volume, total width of crystal including wrapping and air gap is 15.3 mm
my $Vlength = $Clength+$Flength;      # Length of the crystal mother volume
my $Vfront  = $Cfront-$Flength;       # z position of the volume front face
my $Slength =     7.0;                # Length of the sensor "box"
my $Swidth  = $Cwidth;                # Width of the sensor "box"
my $Sgap    =     1.0;                # Gap for flux detector
my $Sfront  = $Vfront+$Vlength+$Sgap; # z position of the sensor front face

# Define the copper thermal shield parameters
# back disk
my $Bdisk_TN = 4.;                                           # half thickness of the copper back disk 
my $Bdisk_IR = 55.;                                          # inner radius of the copper back disk 
my $Bdisk_OR = 178.5;                                        # outer radius of the copper back disk 
my $Bdisk_Z  = $Sfront+$Slength+$Bdisk_TN+0.1;               # z position of the copper back disk 
# front disk
my $Fdisk_TN = 1.;                                           # half thickness of the copper front disk supporting the crystal assemblies
my $Fdisk_IR = $Bdisk_IR;                                    # inner radius of the copper front disk 
my $Fdisk_OR = $Bdisk_OR;                                    # outer radius of the copper front disk 
my $Fdisk_Z  = $Vfront-$Fdisk_TN-0.1;                        # z position of the copper front disk 
# space for preamps
my $BPlate_TN = 25.;                                         # half thickness of the preamps volume
my $BPlate_IR = $Bdisk_IR;                                   # inner radius of the preamps volume
my $BPlate_OR = $Bdisk_OR;                                   # outer radius of the preamps volume
my $BPlate_Z  = $Bdisk_Z+$Bdisk_TN+$BPlate_TN+0.1;           # z position of the preamps volume
# inner copper tube
my $Idisk_LT = ($BPlate_Z+$BPlate_TN-$Fdisk_Z+$Fdisk_TN)/2.; # length of the inner copper tube 
my $Idisk_TN = 4;                                            # thickness of the inner copper tube
my $Idisk_OR = $Fdisk_IR;                                    # outer radius of the inner copper tube matches inner radius of front and back disks
my $Idisk_IR = $Fdisk_IR-$Idisk_TN;                          # inner radius of the inner copper tube
my $Idisk_Z  = ($BPlate_Z+$BPlate_TN+$Fdisk_Z-$Fdisk_TN)/2.; # z position of inner copper tube
# outer copper tube
my $Odisk_LT = ($BPlate_Z+$BPlate_TN-$Fdisk_Z+$Fdisk_TN)/2.; # length of the outer copper tube 
my $Odisk_TN = 2;                                            # thickness of the outer copper tube
my $Odisk_IR = $Fdisk_OR;                                    # inner radius of the outer copper tube matches outer radius of front and back disks
my $Odisk_OR = $Fdisk_OR+$Odisk_TN;                          # outer radius of the outer copper tube
my $Odisk_Z  = $Idisk_Z;                                     # z position of the outer copper tube

# Define the motherboard parameters
my $Bmtb_TN = 1.;                                            # half thickness of the motherboard
my $Bmtb_IR = $Idisk_IR;                                     # inner radius of the motherboard
my $Bmtb_OR = $Odisk_OR;                                     # outer radius of the motherboard
my $Bmtb_Z  = $BPlate_Z + $BPlate_TN + $Bmtb_TN + 0.1;       # z position of the motherboard
my $Bmtb_hear_WD = 80./2.;                                   # half width of the motherboard extensions
my $Bmtb_hear_LN = 225./2;                                   # half length of the motherboard extensions
my $Bmtb_hear_D0 = 0.;                                       # displacement of the motherboard extensions        
my @Bmtb_angle = ( 30., 150., 210., 330.);                   # angles of the motherboard extensions

# Define LED plate geometry parameters
my $LED_TN =   5.;                                           # half thickness of the pcb and pastic plate hosting the LEDs
my $LED_IR = $Fdisk_IR;                                      # inner radius of the pcb and pastic plate hosting the LEDs
my $LED_OR = $Fdisk_OR;                                      # outer radius of the pcb and pastic plate hosting the LEDs
my $LED_Z  = $Fdisk_Z - $Fdisk_TN - $LED_TN - 0.1;           # z position of the pcb and pastic plate hosting the LEDs

# bline: tungsten pipe inside the ft_cal
my $BLine_IR = 30.;                                          # pipe inner radius;
my $BLine_TN = 10.;                                          # pipe thickness
my $BLine_FR = 59.;                                          # radius in the front part, connecting to moller shield
my $BLine_OR = 100.;                                         # radius of the back flange
my $BLine_BG = 1810.;                                        # z location of the beginning of the beamline (to be matched to moller shield)

# back tungsten cup
my $BCup_tang = 0.0962;                                      # tangent of 5.5 degrees
my $BCup_TN = 5.;                                            # thickness of the flat part of the cup
my $BCup_ZM = $Bmtb_Z+$Bmtb_TN+0.1+42.2;                     # z of the downstream face of the cup
my $BCup_Z1 = $Bmtb_Z+$Bmtb_TN+0.1+1;                        # z of the side close to the motherboard (downstream)
my $BCup_Z2 = $Bmtb_Z-$Bmtb_TN-0.1-1;                        # z of the side close to the motherboard (upstream)
my $BCup_ZE = $BCup_ZM+$BCup_TN;                             # z of the downstream face of the cup
my $BCup_ZB = $BCup_ZM-120. ;                                # z beginning of the conical part
my $BCup_IRM = 190. ;                                        # inner radius at the beginning of the cone
my $BCup_ORB = $BCup_ZB*$BCup_tang;                          # outer radius at the beginning of the cone
my $BCup_OR1 = $BCup_Z1*$BCup_tang;                          # outer radius close to the MTB
my $BCup_OR2 = $BCup_Z2*$BCup_tang;                          # outer radius close to the MTB
my $BCup_ORM = $BCup_ZM*$BCup_tang;                          # outer radius at the front face of the plate
my $BCup_ORE = $BCup_ZE*$BCup_tang;                          # outer radius at the back face of the plate
my $BCup_angle = int(atan($Bmtb_hear_WD/$Bmtb_OR)*$degrad*10)/10+0.5;
my @BCup_iangle = (30.+$BCup_angle, 150.+$BCup_angle, 210.+$BCup_angle, 330.+$BCup_angle);
my @BCup_dangle = ((90.-$BCup_iangle[0])*2., (180.-$BCup_iangle[1])*2., (90.-$BCup_iangle[0])*2.,(180.-$BCup_iangle[1])*2.);

my $BL_IR=30;
my $BL_TN=10;



###########################################################################################
# OUTER INSULATION
my $O_Ins_TN = 20.;
my $O_Ins_Z1 = $Fdisk_Z - $Fdisk_TN - 22.3 - $O_Ins_TN;
my $O_Ins_Z2 = $O_Ins_Z1 + $O_Ins_TN;
my $O_Ins_Z3 = $BCup_ZB;
my $O_Ins_Z4 = $BCup_Z2;
my $O_Ins_Z5 = $BCup_Z1;
my $O_Ins_Z6 = $BCup_ZM;
my $O_Ins_Z7 = $BCup_ZE;
my $O_Ins_Z8 = $BCup_ZE + 0.1;
my $O_Ins_Z9 = $O_Ins_Z8 + $O_Ins_TN;

my $O_Ins_I1 = $BL_IR + $BL_TN + 0.1;
my $O_Ins_I2 = $O_Ins_Z2*$BCup_tang +0.1;
my $O_Ins_I3 = $O_Ins_Z3*$BCup_tang +0.1;
my $O_Ins_I4 = $O_Ins_Z4*$BCup_tang +0.1;
my $O_Ins_I5 = $O_Ins_Z5*$BCup_tang +0.1;
my $O_Ins_I6 = $O_Ins_Z6*$BCup_tang +0.1;
my $O_Ins_I7 = $O_Ins_Z7*$BCup_tang +0.1;
my $O_Ins_I8 = $O_Ins_Z8*$BCup_tang +0.1;
my $O_Ins_I9 = $O_Ins_I1;

my $O_Ins_O1 = $O_Ins_Z1*$BCup_tang +0.1 + $O_Ins_TN;
my $O_Ins_O2 = $O_Ins_I2 + $O_Ins_TN; 
my $O_Ins_O3 = $O_Ins_I3 + $O_Ins_TN; 
my $O_Ins_O4 = $O_Ins_I4 + $O_Ins_TN; 
my $O_Ins_O5 = $O_Ins_I5 + $O_Ins_TN; 
my $O_Ins_O6 = $O_Ins_I6 + $O_Ins_TN; 
my $O_Ins_O7 = $O_Ins_I7 + $O_Ins_TN; 
my $O_Ins_O8 = $O_Ins_I8 + $O_Ins_TN; 
my $O_Ins_O9 = $O_Ins_Z9*$BCup_tang +0.1 + $O_Ins_TN; 

$O_Ins_I4 = $O_Ins_Z4*$BCup_tang +0.5;
$O_Ins_I5 = $O_Ins_Z5*$BCup_tang +0.5;

###########################################################################################
# INNER INSULATION
my $I_Ins_LT = ($BCup_ZE - $O_Ins_Z2 -0.1)/2.;
my $I_Ins_OR =  $Idisk_IR - 0.1;
my $I_Ins_IR =  $O_Ins_I1;
my $I_Ins_Z  = ($BCup_ZE + $O_Ins_Z2)/2.;

###########################################################################################
# OUTER SHELL
my $O_Shell_TN = 1.;
my $O_Shell_Z1 = $O_Ins_Z1-$O_Shell_TN-0.1;
my $O_Shell_Z2 = $O_Shell_Z1+$O_Shell_TN;
my $O_Shell_Z3 = $O_Ins_Z3;
my $O_Shell_Z4 = $BCup_Z2;
my $O_Shell_Z5 = $BCup_Z1;
my $O_Shell_Z6 = $O_Ins_Z6 ;
my $O_Shell_Z7 = $O_Ins_Z7 ;
my $O_Shell_Z8 = $O_Ins_Z8 ;
my $O_Shell_Z9 = $O_Ins_Z9 ;
my $O_Shell_Z10 = $O_Ins_Z9 + 0.1;
my $O_Shell_Z11= $O_Shell_Z9+$O_Shell_TN;

my $O_Shell_I1 = $O_Ins_I1;
my $O_Shell_I2 = $O_Shell_Z2*$BCup_tang + $O_Ins_TN + 0.1;
my $O_Shell_I3 = $O_Shell_Z3*$BCup_tang + $O_Ins_TN + 0.1;
my $O_Shell_I4 = $O_Shell_Z4*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I5 = $O_Shell_Z5*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I6 = $O_Shell_Z6*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I7 = $O_Shell_Z7*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I8 = $O_Shell_Z8*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I9 = $O_Shell_Z9*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I10 = $O_Shell_Z10*$BCup_tang + $O_Ins_TN + 0.1; 
my $O_Shell_I11 = $O_Ins_I1; 

my $O_Shell_O1 = $O_Shell_Z1*$BCup_tang + $O_Ins_TN + 0.1 + $O_Shell_TN;
my $O_Shell_O2 = $O_Shell_I2 + $O_Shell_TN; 
my $O_Shell_O3 = $O_Shell_I3 + $O_Shell_TN; 
my $O_Shell_O4 = $O_Shell_I4 + $O_Shell_TN; 
my $O_Shell_O5 = $O_Shell_I5 + $O_Shell_TN; 
my $O_Shell_O6 = $O_Shell_I6 + $O_Shell_TN; 
my $O_Shell_O7 = $O_Shell_I7 + $O_Shell_TN; 
my $O_Shell_O8 = $O_Shell_I8 + $O_Shell_TN; 
my $O_Shell_O9 = $O_Shell_I9 + $O_Shell_TN; 
my $O_Shell_O10 = $O_Shell_I10 + $O_Shell_TN; 
my $O_Shell_O11 = $O_Shell_Z11*$BCup_tang + $O_Ins_TN + 0.1 + $O_Shell_TN; 

$O_Shell_I4 = $O_Shell_Z4*$BCup_tang + $O_Ins_TN + 0.7; 
$O_Shell_I5 = $O_Shell_Z5*$BCup_tang + $O_Ins_TN + 0.7; 

###########################################################################################
# FT BEAMLINE COMPONENTS

# ft to torus pipe
my $Tube_OR         =  75.0;
my $back_flange_OR  = 126.0;
my $front_flange_OR = 148.0;
my $flange_TN       =  15.0;
my $TPlate_TN= 20.; # thickness of the tungsten plate on the back of the FT-Cal

	my $TPlate_Z1  = $O_Shell_Z11 + 0.1;
	my $TPlate_Z2  = $TPlate_Z1 + $TPlate_TN;

        my $BLine_MR  = $BLine_IR + $BLine_TN;    # outer radius in the calorimeter section
	my $BLine_Z1  = $BLine_BG;
	my $BLine_Z2  = $O_Shell_Z1 - 0.1;
	my $BLine_Z3  = $TPlate_Z2  + 0.1;
	my $BLine_Z4  = $BLine_Z3   + 18.;


###########################################################################################
# Hodoscope Dimension and Parameters
my $VETO_TN = 38./2.; # thickness of the hodoscope volume
my $VETO_OR = 178.5;  # outer radius
my $VETO_IR = 60.;    # inner radius
my $VETO_Z  = $O_Shell_Z1 - $VETO_TN - 1.0; # position along z

my $paint_thick = 0.0; 

my @LS_TN= (0.5/2., 1.0/2., 0.5/2.); # thickness of three surrounding layers of Carbon fiber
my @LS_ZN= ($VETO_TN - 0.5/2. , $VETO_TN-0.5-7.5-15.0-1.0/2. , -$VETO_TN + 0.5/2.); # Position of 3 layers of carbon fiber, edit if you edit the thickness

my $p15_WW=15./2.;
my @p15_WT= (15./2. , 7./2.) ;
my @p_air_WT = (7.5/2. , 6.5/2.); 
#
my $p30_WW=30./2.;
my @p30_WT= (15./2. , 7./2.) ;
#



my $p15_SW=$p15_WW-0.1;
#
my $p30_SW=$p30_WW-0.1;
#
my $p15_N = 11;
my @p15_X = (  7.5,  22.5,  37.5,  52.5,  52.5,  67.5,  67.5,  67.5,  67.5,  97.5, 127.5);
my @p15_Y = ( 67.5,  67.5,  67.5,  52.5,  67.5,   7.5,  22.5,  37.5,  52.5, 127.5,  97.5);
#
my $p30_N = 18;
my @p30_X = (  15.,  15.,  15.,  45.,  45.,  45.,  75.,  75.,  75.,  90.,  90., 105., 105., 120., 120., 135., 150., 150.);
my @p30_Y = (  90., 120., 150.,  90., 120., 150.,  75., 105., 135.,  15.,  45.,  75., 105.,  15.,  45.,  75.,  15.,  45.);
my @q_X = (1., -1., -1.,  1.);
my @q_Y = (1.,  1., -1., -1.);

my $p_Ntiles = 116;

#0.000mm paint thickness tyles (and 0.0 air gap)
#my @p_R	= (16.0507,15.4434,16.0507,14.8492,12.9035,15.4434,12.9035,10.6066,7.4246,15.6605,15.0748,15.0748,15.6605,12.8160,12.0934,12.0934,12.8160,10.0623,9.1241,9.1241,10.0623,8.5513,7.7217,7.1151,6.7915,6.7915,7.1151,7.7217,8.5513) ;
#my @p_theta =	(-0.65285,-0.50710,-0.91795,-0.78540,-0.62025,-1.06370,-0.95055,-0.78540,-0.78540,-0.29146,-0.09967,0.09967,0.29146,-0.35877,-0.12435,0.12435,0.35877,-0.46365,-0.16515,0.16515,0.46365,-0.66104,-0.50710,-0.32175,-0.11066,0.11066,0.32175,0.50710,0.66104) ;

# 0.075mm paint thickness tyles
#my @p_R	= (16.2331,15.6642,16.2331,15.0076,13.0933,15.6642,13.0933,10.8102,7.5590,15.9022,15.3132,15.3132,15.9022,13.0258,12.2998,12.2998,13.0258,10.2395,9.2984,9.2984,10.2395,8.7322,7.8847,7.2650,6.9344,6.9344,7.2650,7.8847,8.7322) ;
#my @p_theta = (-0.65294,-0.50511,-0.91786,-0.78540,-0.61741,-1.06568,-0.95339,-0.78540,-0.78540,-0.29005,-0.09916,0.09916,0.29005,-0.35667,-0.12357,0.12357,0.35667,-0.46024,-0.16377,0.16377,0.46024,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118 ) ;

# 0.10mm paint thickness 
#my @p_R	= (16.1761,15.5952,16.1761,14.9581,13.0339,15.5952,13.0339,10.7466,7.5165,15.8261,15.2381,15.2381,15.8261,12.9596,12.2347,12.2347,12.9596,10.1835,9.2433,9.2433,10.1835,8.6752,7.8332,7.2176,6.8892,6.8892,7.2176,7.8332,8.6752);
#my @p_theta = (-0.65291,-0.50573,-0.91789,-0.78540,-0.61829,-1.06507,-0.95251,-0.78540,-0.78540,-0.29049,-0.09932,0.09932,0.29049,-0.35733,-0.12382,0.12382,0.35733,-0.46132,-0.16421,0.16421,0.46132,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118);

# 0.15mm paint thickness
my @p_R	= (16.2331,15.6642,16.2331,15.0076,13.0933,15.6642,13.0933,10.8102,7.5590,15.9022,15.3132,15.3132,15.9022,13.0258,12.2998,12.2998,13.0258,10.2395,9.2984,9.2984,10.2395,8.7322,7.8847,7.2650,6.9344,6.9344,7.2650,7.8847,8.7322);
my @p_theta = (-0.65294,-0.50511,-0.91786,-0.78540,-0.61741,-1.06568,-0.95339,-0.78540,-0.78540,-0.29005,-0.09916,0.09916,0.29005,-0.35667,-0.12357,0.12357,0.35667,-0.46024,-0.16377,0.16377,0.46024,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118);

# 0.20mm paint thickness
#my @p_R	= (16.2901,15.7331,16.2901,15.0571,13.1526,15.7331,13.1526,10.8739,7.6014,15.9784,15.3884,15.3884,15.9784,13.0919,12.3649,12.3649,13.0919,10.2954,9.3535,9.3535,10.2954,8.7892,7.9362,7.3125,6.9797,6.9797,7.3125,7.9362,8.7892);
#my @p_theta = (-0.65297,-0.50451,-0.91783,-0.78540,-0.61654,-1.06629,-0.95426,-0.78540,-0.78540,-0.28960,-0.09900,0.09900,0.28960,-0.35601,-0.12332,0.12332,0.35601,-0.45917,-0.16334,0.16334,0.45917,-0.66118,-0.50722,-0.32184,-0.11069,0.11069,0.32184,0.50722,0.66118);

#############
### reading the coordinates from two text files, hodo_position_thin.txt and hodo_position_thick.txt
#############
my @abc;
my @sector_hodo_thin;
my @tyle_hodo_thin;
my @tyleN_hodo_thin;
my @x_hodo_thin;
my @y_hodo_thin;
my @size_x_hodo_thin;
my @size_y_hodo_thin;
my @cutA_x_hodo_thin;
my @cutA_y_hodo_thin;
my @cutB_x_hodo_thin;
my @cutB_y_hodo_thin;
my @sector_hodo_thick;
my @tyle_hodo_thick;
my @tyleN_hodo_thick;
my @x_hodo_thick;
my @y_hodo_thick;
my @size_x_hodo_thick;
my @size_y_hodo_thick;
my @cutA_x_hodo_thick;
my @cutA_y_hodo_thick;
my @cutB_x_hodo_thick;
my @cutB_y_hodo_thick;
my $iter = 0;
my $line;


open (FILE,"hodo_position_thin.txt");
while ($line = <FILE>) {

    @abc = split '\t', $line;
    $sector_hodo_thin[$iter] = substr($line,0,1);
    push (@tyle_hodo_thin,$abc[1]);
    push (@tyleN_hodo_thin,$abc[2]);
    push (@x_hodo_thin, $abc[3]);
    push (@y_hodo_thin, $abc[4]);
    push (@size_x_hodo_thin, $abc[5]);
    push (@size_y_hodo_thin, $abc[6]);
    push (@cutA_x_hodo_thin, $abc[7]);
    push (@cutA_y_hodo_thin, $abc[8]);
    push (@cutB_x_hodo_thin, $abc[9]);
    push (@cutB_y_hodo_thin, $abc[10]);
#    print "$sector_hodo_thin[$iter] \t $tyle_hodo_thin[$iter] \t $tyleN_hodo_thin[$iter] \t $x_hodo_thin[$iter] \t $y_hodo_thin[$iter] \t $size_x_hodo_thin[$iter] \t $size_y_hodo_thin[$iter]";

    $iter = $iter+1;

}

close FILE;


open (FILE,"hodo_position_thick.txt");
while ($line = <FILE>) {

    @abc = split '\t', $line;
    $sector_hodo_thick[$iter] = substr($line,0,1);
    push (@tyle_hodo_thick,$abc[1]);
    push (@tyleN_hodo_thick,$abc[2]);
    push (@x_hodo_thick, $abc[3]);
    push (@y_hodo_thick, $abc[4]);
    push (@size_x_hodo_thick, $abc[5]);
    push (@size_y_hodo_thick, $abc[6]);
    push (@cutA_x_hodo_thick, $abc[7]);
    push (@cutA_y_hodo_thick, $abc[8]);
    push (@cutB_x_hodo_thick, $abc[9]);
    push (@cutB_y_hodo_thick, $abc[10]);
#    print "$sector_hodo_thick[$iter] \t $tyle_hodo_thick[$iter] \t $tyleN_hodo_thick[$iter] \t $x_hodo_thick[$iter] \t $y_hodo_thick[$iter] \t $size_x_hodo_thick[$iter] \t $size_y_hodo_thick[$iter]";

    $iter = $iter+1;

}

close FILE;


my @p_size = (15.0,30.0,15.0,30.0,30.0,30.0,30.0,30.0,15.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,30.0,15.0,15.0,15.0,15.0,15.0,15.0,15.0,15.0);



###########################################################################################
# Tracker Dimension and Parameters

# Tracker
my @starting_point =();

my $ftm_ir 		= 64.0;
my $ftm_or 		= 161.0;
my $nlayer		= 2;
$starting_point[0] 	= 1763.0;
$starting_point[1] 	= 1783.0;
my $InnerRadius 	= 65.0;
my $OuterRadius 	= 142.0;
my $Epoxy_Dz 		= 0.5*0.3;
my $PCB_Dz 		= 0.5*0.1;
my $Strips_Dz 		= 0.5*0.015;
my $Gas1_Dz 		= 0.5*0.128;
my $Mesh_Dz 		= 0.5*0.030;
my $Gas2_Dz 		= 0.5*5.350;
my $Drift_Dz 		= 0.5*0.1;

# G4 materials
my $epoxy_material   = 'epoxy';
my $pcboard_material = 'epoxy';
my $strips_material  = 'mmstrips';
my $gas_material     = 'mmgas';
my $mesh_material    = 'mmmesh';
my $drift_material   = 'mmmylar';


# G4 colors
my $epoxy_color      = 'e200e1';
my $pcboard_color    = '0000ff';
my $strips_color     = '353540';
my $gas_color        = 'e10000';
my $mesh_color       = '252020';
my $drift_color      = 'fff600';

# FTM is a Tube containing all SLs
my $ftm_dz = ($starting_point[1] - $starting_point[0])/2.0 + $Epoxy_Dz*2.0 + $PCB_Dz*4.0 + $Strips_Dz*4.0 + $Gas1_Dz*4.0 + $Mesh_Dz*4.0 + $Gas2_Dz*4.0 + $Drift_Dz*4.0+1.0;
my $ftm_starting = ($starting_point[1] + $starting_point[0])/2.0;

#  FTM FEE Boxes
my $FEE_Disk_OR = 200.;
my $FEE_Disk_LN = 2.;
my $FEE_ARM_LN  = 530./2.;
my $FEE_ARM_WD  = 90./2.;

# size of crate
my $FEE_WD = 91./2.; 
my $FEE_HT = 265.5/2.;
my $FEE_LN = 242./2.; 
my $FEE_TN = 1.5;

my $FEE_PVT_TN=10.;

my $FEE_PVT_WD = $FEE_WD+$FEE_PVT_TN;
my $FEE_PVT_HT = $FEE_HT+$FEE_PVT_TN;
my $FEE_PVT_LN = $FEE_LN+$FEE_PVT_TN;

my $FEE_A_WD = $FEE_WD-$FEE_TN;
my $FEE_A_HT = $FEE_HT-$FEE_TN;
my $FEE_A_LN = $FEE_LN-$FEE_TN;
my @FEE_azimuthal_angle = (210., 270., 330.);
my $FEE_polar_angle = -22.;


# define crystals mother volume
my $nplanes_FT_CRY = 2;
my @z_plane_FT_CRY = ( $Idisk_Z-$Idisk_LT, $Idisk_Z+$Idisk_LT);
my @iradius_FT_CRY = (          $Idisk_IR,          $Idisk_IR);
my @oradius_FT_CRY = (          $Odisk_OR,          $Odisk_OR);


###########################################################################################
# Define FTCAL Mother Volume
my $nplanes_FT = 7;
my @z_plane_FT = ($BLine_BG, $O_Shell_Z1, $O_Shell_Z1, 2098., 2276., 2276., $torus_z);
my @oradius_FT = ($BLine_FR,   $BLine_FR,        700.,  700.,  238.,  149., $back_flange_OR);
my @iradius_FT = (       0.,          0.,          0.,     0.,    0.,   0.,       0.);
###########################################################################################


###########################################################################################
# Build FT-CAL Mother Volume
###########################################################################################

sub make_ft_cal_mother_volume
{
	my %detector = init_det();
	$detector{"name"}        = "ft_cal";
	$detector{"mother"}      = "root";
	$detector{"description"} = "ft calorimeter";
	$detector{"color"}       = "1437f4";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_FT*counts";
	for(my $i = 0; $i <$nplanes_FT; $i++) {$dimen = $dimen ." $iradius_FT[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FT; $i++) {$dimen = $dimen ." $oradius_FT[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FT; $i++) {$dimen = $dimen ." $z_plane_FT[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "Air";
	$detector{"style"}       = 0;
	print_det(\%configuration, \%detector);


}



###########################################################################################
# Build Crystal Volume and Assemble calorimeter
###########################################################################################

sub make_ft_cal_crystals_volume
{
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_crystal_volume";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft calorimeter crystal volume";
	$detector{"color"}       = "1437f4";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_FT_CRY*counts";
	for(my $i = 0; $i <$nplanes_FT_CRY; $i++) {$dimen = $dimen ." $iradius_FT_CRY[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FT_CRY; $i++) {$dimen = $dimen ." $oradius_FT_CRY[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FT_CRY; $i++) {$dimen = $dimen ." $z_plane_FT_CRY[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "Air";
	$detector{"style"}       = 0;
	print_det(\%configuration, \%detector);


}

# Loop over all crystals and define their positions
sub make_ft_cal_crystals
{
        my $centX = ( int $Nx/2 )+0.5;
	my $centY = ( int $Ny/2 )+0.5;
	my $locX=0.;
	my $locY=0.;
	my $locZ=0.;
	my $dX=0.;
	my $dY=0.;
	my $dZ=0.;
	for ( my $iX = 1; $iX <= $Nx; $iX++ )
	{
		for ( my $iY = 1; $iY <= $Ny; $iY++ )
		{
			$locX=($iX-$centX)*$Vwidth;
			$locY=($iY-$centY)*$Vwidth;
			my $locR=sqrt($locX*$locX+$locY*$locY);
			if($locR>60. && $locR<$Vwidth*11)
			{
				# crystal mother volume
				my %detector = init_det();
				$detector{"name"}        = "ft_cr_volume_" . $iX . "_" . $iY ;
				$detector{"mother"}      = "ft_cal_crystal_volume";
				$detector{"description"} = "ft crystal mother volume (h:" . $iX . ", v:" . $iY . ")";
				$locZ=$Vfront+$Vlength/2.;
				$detector{"pos"}         = "$locX*mm $locY*mm $locZ*mm";
				$detector{"color"}       = "838EDE";
				$detector{"type"}        = "Box" ;
				$dX=$Vwidth/2.0;
				$dY=$Vwidth/2.0;
				$dZ=$Vlength/2.0;
				$detector{"dimensions"}  = "$dX*mm $dY*mm $dZ*mm";
				$detector{"material"}    = "G4_AIR";
				print_det(\%configuration, \%detector);
				
				# APD housing
				%detector = init_det();
				$detector{"name"}        = "ft_cr_apd_" . $iX . "_" . $iY ;
				$detector{"mother"}      = "ft_cal_crystal_volume";
				$detector{"description"} = "ft crystal apd (h:" . $iX . ", v:" . $iY . ")";
				$locZ=$Sfront+$Slength/2.;
				$detector{"pos"}         = "$locX*mm $locY*mm $locZ*mm";
				$detector{"color"}       = "99CC66";
				$detector{"type"}        = "Box" ;
				$dX=$Swidth/2.0;
				$dY=$Swidth/2.0;
				$dZ=$Slength/2.0;
				$detector{"dimensions"}  = "$dX*mm $dY*mm $dZ*mm";
				$detector{"material"}    = "peek";
				$detector{"style"}       = "1" ;
				print_det(\%configuration, \%detector);
				
				# Wrapping Volume;				
				%detector = init_det();
				$detector{"name"}        = "ft_cr_wr_" . $iX . "_" . $iY ;
				$detector{"mother"}      = "ft_cr_volume_" . $iX . "_" . $iY ;
				$detector{"description"} = "ft wrapping (h:" . $iX . ", v:" . $iY . ")";
				$locX=0.;
				$locY=0.;
				$locZ=0.;
				$detector{"pos"}         = "$locX*mm $locY*mm $locZ*mm";
				$detector{"color"}       = "838EDE";
				$detector{"type"}        = "Box" ;
				$dX=$Wwidth/2.0;
				$dY=$Wwidth/2.0;
				$dZ=$Vlength/2.0;
				$detector{"dimensions"}  = "$dX*mm $dY*mm $dZ*mm";
				$detector{"material"}    = "G4_MYLAR";
				$detector{"style"}       = "1" ;
				print_det(\%configuration, \%detector);
				
				# PbWO4 Crystal;
				%detector = init_det();
				$detector{"name"}        = "ft_cr_" . $iX . "_" . $iY ;
				$detector{"mother"}      = "ft_cr_wr_" . $iX . "_" . $iY ;
				$detector{"description"} = "ft crystal (h:" . $iX . ", v:" . $iY . ")";
				$locX=0.;
				$locY=0.;
				$locZ=$Flength/2.;
				$detector{"pos"}         = "$locX*mm $locY*mm $locZ*mm";
				$detector{"color"}       = "836FFF";
				$detector{"type"}        = "Box" ;
				$dX=$Cwidth/2.0;
				$dY=$Cwidth/2.0;
				$dZ=$Clength/2.0;
				$detector{"dimensions"}  = "$dX*mm $dY*mm $dZ*mm";
				$detector{"material"}    = "G4_PbWO4";
				$detector{"style"}       = "1" ;
				$detector{"sensitivity"} = "ft_cal"; 
				$detector{"hit_type"}    = "ft_cal";
				$detector{"identifiers"} = "ih manual $iX iv manual $iY";
				print_det(\%configuration, \%detector);
				
				# LED housing
				%detector = init_det();
				$detector{"name"}        = "ft_cr_led_" . $iX . "_" . $iY ;
				$detector{"mother"}      = "ft_cr_wr_" . $iX . "_" . $iY ;
				$detector{"description"} = "ft crystal led (h:" . $iX . ", v:" . $iY . ")";
				$locX=0.;
				$locY=0.;
				$locZ=-$Vlength/2.+$Flength/2.;
				$detector{"pos"}         = "$locX*mm $locY*mm $locZ*mm";
				$detector{"color"}       = "EEC900";
				$detector{"type"}        = "Box" ;
				$dX=$Fwidth/2.0;
				$dY=$Fwidth/2.0;
				$dZ=$Flength/2.0;
				$detector{"dimensions"}  = "$dX*mm $dY*mm $dZ*mm";
				$detector{"material"}    = "peek";
				$detector{"style"}       = "1" ;
				print_det(\%configuration, \%detector);
			}
		}
	}
}


###########################################################################################
# Define the Flux Detector on the back of the crystals
sub make_ft_cal_flux
{
        # flux on the back of the crystals
        my $Flux_TN = $Sgap/2;         # flux detector half thickness defined as a function of the gap between sensor and crystals
	my $Flux_IR = $Bdisk_IR;       # inner radius is defined equal to copper back disk
	my $Flux_OR = $Bdisk_OR;       # outer radius is defined equal to copper front disk
	my $Flux_Z =  $Vfront+$Vlength+$Flux_TN; 
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_flux";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft flux";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Flux_Z*mm";
	$detector{"color"}       = "aa0088";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Flux_IR*mm $Flux_OR*mm $Flux_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Galactic";
	$detector{"style"}       = 1;
	$detector{"sensitivity"}  = "flux";
	$detector{"hit_type"}     = "flux";
	$detector{"identifiers"}  = "id manual 3";
	print_det(\%configuration, \%detector);
}

# Define the Flux Detectors in front of the Tagger
sub make_ft_moellerdisk
{
    # flux in front of tagger
    my @disk_zpos   = ( 281.0 , 1809.6 );
    my @disk_iradius = (   2.0 ,   56.0 );
    my @disk_oradius = ( 150.0 ,  150.0 );

    for(my $n=0; $n<2; $n++)	
    {
        my $idisk = $n +1;
	my %detector = init_det();
	$detector{"name"}         = "moller_disk_$n";
	$detector{"mother"}       = "root";
	$detector{"description"}  = "Moller Disk $n";
	$detector{"pos"}          = "0*mm 0.0*mm $disk_zpos[$n]*mm";
	$detector{"rotation"}     = "0*deg 0*deg 0*deg";
	$detector{"color"}        = "aa0088";
	$detector{"type"}         = "Tube";
	$detector{"dimensions"}   = "$disk_iradius[$n]*mm $disk_oradius[$n]*mm 0.4*mm 0.0*deg 360*deg";
	$detector{"material"}     = "G4_Galactic";
	$detector{"visible"}      = 0;
	$detector{"sensitivity"}  = "flux";
	$detector{"hit_type"}     = "flux";
	$detector{"identifiers"}  = "id manual $idisk";
	print_det(\%configuration, \%detector);
   }
}

###########################################################################################
# Define the Copper Disk in front of the Crystals
sub make_ft_cal_copper
{
	# back
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_back_copper";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft back_copper";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Bdisk_Z*mm";
	$detector{"color"}       = "CC6600";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Bdisk_IR*mm $Bdisk_OR*mm $Bdisk_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Cu";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	# front
	%detector = init_det();
	$detector{"name"}        = "ft_cal_front_copper";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft front_copper";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Fdisk_Z*mm";
	$detector{"color"}       = "CC6600";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Fdisk_IR*mm $Fdisk_OR*mm $Fdisk_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Cu";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	# inner
	%detector = init_det();
	$detector{"name"}        = "ft_cal_inner_copper";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft inner_copper";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Idisk_Z*mm";
	$detector{"color"}       = "CC6600";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Idisk_IR*mm $Idisk_OR*mm $Idisk_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Cu";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	# outer
	%detector = init_det();
	$detector{"name"}        = "ft_cal_outer_copper";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft outer_copper";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Odisk_Z*mm";
	$detector{"color"}       = "CC6600";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Odisk_IR*mm $Odisk_OR*mm $Odisk_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Cu";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	
	# Preamp Space
	%detector = init_det();
	$detector{"name"}        = "ft_cal_back_plate";
	$detector{"mother"}      = "ft_cal_crystal_volume";
	$detector{"description"} = "ft back_plate";
	$detector{"pos"}         = "0.0*cm 0.0*cm $BPlate_Z*mm";
	$detector{"color"}       = "7F9A65";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$BPlate_IR*mm $BPlate_OR*mm $BPlate_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_AIR";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	
}


###########################################################################################
# Define the Space for the Preamps  and the MotherBoard on the back
sub make_ft_cal_motherboard
{
	# MotherBoard
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_back_mtb";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft back_mtb disk";
	$detector{"pos"}         = "0.0*cm 0.0*cm $Bmtb_Z*mm";
	$detector{"color"}       = "0B3B0B";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$Bmtb_IR*mm $Bmtb_OR*mm $Bmtb_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "pcboard";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	for ( my $i = 0; $i < 4; $i++ ) {
		my $Bmtb_hear_DX = ($Bmtb_OR+$Bmtb_hear_LN-$Bmtb_hear_D0)*cos($Bmtb_angle[$i]/$degrad);
		my $Bmtb_hear_DY =-($Bmtb_OR+$Bmtb_hear_LN-$Bmtb_hear_D0)*sin($Bmtb_angle[$i]/$degrad);
		%detector = init_det();
		$detector{"name"}        = "ft_back_mtb_h$i";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft back_mtb hear$i";
		$detector{"pos"}         = "$Bmtb_hear_DX*mm $Bmtb_hear_DY*mm $Bmtb_Z*mm";
		$detector{"rotation"}    = "0*deg 0*deg $Bmtb_angle[$i]*deg";
		$detector{"color"}       = "0B3B0B";
		$detector{"type"}        = "Box";
		$detector{"dimensions"}  = "$Bmtb_hear_LN*mm $Bmtb_hear_WD*mm $Bmtb_TN*mm";
		$detector{"material"}    = "PCBoardM";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);
	}
}


###########################################################################################
# Define the LED assembly
sub make_ft_cal_led
{
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_led";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft led";
	$detector{"pos"}         = "0.0*cm 0.0*cm $LED_Z*mm";
	$detector{"color"}       = "333333";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$LED_IR*mm $LED_OR*mm $LED_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_TEFLON";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}


###########################################################################################
# Define the Back Tungsten Cup

sub make_ft_cal_tcup
{
	my $nplanes_TCup = 4;
	my @z_plane_TCup = ( $BCup_Z1, $BCup_ZM, $BCup_ZM, $BCup_ZE);
	my @oradius_TCup = ( $BCup_OR1, $BCup_ORM, $BCup_ORM, $BCup_ORE,);
	my @iradius_TCup = ( $BCup_IRM, $BCup_IRM, $I_Ins_OR, $I_Ins_OR);
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_tcup_back";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "tungsten cup and cone at the back of the ft, back part";
	$detector{"color"}       = "ccff00";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_TCup*counts";
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $iradius_TCup[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $oradius_TCup[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $z_plane_TCup[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_W";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	$nplanes_TCup = 2;
	@z_plane_TCup = ( $BCup_ZB,  $BCup_Z2 );
	@oradius_TCup = ( $BCup_ORB, $BCup_OR2);
	@iradius_TCup = ( $BCup_IRM, $BCup_IRM);
	%detector = init_det();
	$detector{"name"}        = "ft_cal_tcup_front";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "tungsten cup and cone at the back of the ft, front part";
	$detector{"color"}       = "ccff00";
	$detector{"type"}        = "Polycone";
	$dimen = "0.0*deg 360*deg $nplanes_TCup*counts";
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $iradius_TCup[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $oradius_TCup[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCup; $i++) {$dimen = $dimen ." $z_plane_TCup[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_W";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
 
	$nplanes_TCup = 2;
	@z_plane_TCup = ( $BCup_Z1,  $BCup_Z2 );
	@oradius_TCup = ( $BCup_OR1, $BCup_OR2);
	@iradius_TCup = ( $BCup_IRM, $BCup_IRM);
	for(my $i=0; $i<4 ; $i++)
	{
		my $s = $i + 1;
		%detector = init_det();
		$detector{"name"}        = "ft_cal_tcup_m$s";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "tungsten cup and cone at the back of the ft, medium part $s";
		$detector{"color"}       = "ccff00";
		$detector{"type"}        = "Polycone";
		my $biangle=$BCup_iangle[$i];
		my $bdangle=$BCup_dangle[$i];
		$dimen = "$biangle*deg $bdangle*deg $nplanes_TCup*counts";
		for(my $i = 0; $i <$nplanes_TCup; $i++) { $dimen = $dimen ." $iradius_TCup[$i]*mm";}
		for(my $i = 0; $i <$nplanes_TCup; $i++) { $dimen = $dimen ." $oradius_TCup[$i]*mm";}
		for(my $i = 0; $i <$nplanes_TCup; $i++) { $dimen = $dimen ." $z_plane_TCup[$i]*mm";}
		$detector{"dimensions"}  = $dimen;
		$detector{"material"}    = "G4_W";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);
	}
}

############################################################################################
## Define the Calorimeter Insulation
sub make_ft_cal_insulation
{
	# inner
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_inner_ins";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft inner_ins";
	$detector{"pos"}         = "0.0*cm 0.0*cm $I_Ins_Z*mm";
	$detector{"color"}       = "F5F6CE";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$I_Ins_IR*mm $I_Ins_OR*mm $I_Ins_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "insfoam";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	
	# outer f
	my $nplanes_O_Ins = 5;
	my @z_plane_O_Ins = ($O_Ins_Z1, $O_Ins_Z2, $O_Ins_Z2, $O_Ins_Z3, $O_Ins_Z4);
	my @iradius_O_Ins = ($O_Ins_I1, $O_Ins_I1, $O_Ins_I2, $O_Ins_I3, $O_Ins_I4);
	my @oradius_O_Ins = ($O_Ins_O1, $O_Ins_O2, $O_Ins_O2, $O_Ins_O3, $O_Ins_O4);
	%detector = init_det();
	$detector{"name"}        = "ft_cal_outer_ins_f";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft outer_ins_f";
	$detector{"color"}       = "F5F6CE";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_O_Ins*counts";
	for(my $i = 0; $i <$nplanes_O_Ins ; $i++) { $dimen = $dimen ." $iradius_O_Ins[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Ins; $i++)  { $dimen = $dimen ." $oradius_O_Ins[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Ins ; $i++) { $dimen = $dimen ." $z_plane_O_Ins[$i]*mm";}
	$detector{"dimensions"}  = $dimen; 
	$detector{"material"}    = "insfoam";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	# outer b
	$nplanes_O_Ins = 6;
	@z_plane_O_Ins = ($O_Ins_Z5, $O_Ins_Z6, $O_Ins_Z7, $O_Ins_Z8, $O_Ins_Z8, $O_Ins_Z9);
	@iradius_O_Ins = ($O_Ins_I5, $O_Ins_I6, $O_Ins_I7, $O_Ins_I8, $O_Ins_I9, $O_Ins_I9);
	@oradius_O_Ins = ($O_Ins_O5, $O_Ins_O6, $O_Ins_O7, $O_Ins_O8, $O_Ins_O8, $O_Ins_O9);
	%detector = init_det();
	$detector{"name"}        = "ft_cal_outer_ins_b";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft outer_ins_b";
	$detector{"color"}       = "F5F6CE";
	$detector{"type"}        = "Polycone";	
	$dimen = "0.0*deg 360*deg $nplanes_O_Ins*counts";
	for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $iradius_O_Ins[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $oradius_O_Ins[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $z_plane_O_Ins[$i]*mm";}
	$detector{"dimensions"}  = $dimen; 
	$detector{"material"}    = "insfoam";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	# medium
	$nplanes_O_Ins = 2;
	@z_plane_O_Ins = ($O_Ins_Z5, $O_Ins_Z4);
	@iradius_O_Ins = ($O_Ins_I5, $O_Ins_I4);
	@oradius_O_Ins = ($O_Ins_O5, $O_Ins_O4);
	for(my $i=0; $i<4 ; $i++)
	{
		my $s = $i + 1;
		%detector = init_det();
		$detector{"name"}        = "ft_cal_outer_ins_m$s";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft outer_ins_m$s";
		$detector{"color"}       = "F5F6CE";
		$detector{"type"}        = "Polycone";
		my $biangle=$BCup_iangle[$i];
		my $bdangle=$BCup_dangle[$i];
		$dimen = "$biangle*deg $bdangle*deg $nplanes_O_Ins*counts";
		for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $iradius_O_Ins[$i]*mm";}
		for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $oradius_O_Ins[$i]*mm"; }
		for(my $i = 0; $i <$nplanes_O_Ins; $i++) {$dimen = $dimen ." $z_plane_O_Ins[$i]*mm";}
		$detector{"dimensions"}  = $dimen; 
		$detector{"material"}    = "insfoam";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);
	}
}

###########################################################################################
# Define the Outer Shell
sub make_ft_cal_shell
{
	# outer front
	my $nplanes_O_Shell = 5;
	my @z_plane_O_Shell = ($O_Shell_Z1, $O_Shell_Z2, $O_Shell_Z2, $O_Shell_Z3, $O_Shell_Z4);
	my @iradius_O_Shell = ($O_Shell_I1, $O_Shell_I1, $O_Shell_I2, $O_Shell_I3, $O_Shell_I4);
	my @oradius_O_Shell = ($O_Shell_O1, $O_Shell_O2, $O_Shell_O2, $O_Shell_O3, $O_Shell_O4);
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_outer_shell_f";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft outer_shell_f";
	$detector{"color"}       = "F5DA81";
	$detector{"type"}        = "Polycone";	
	my $dimen = "0.0*deg 360*deg $nplanes_O_Shell*counts";
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $iradius_O_Shell[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $oradius_O_Shell[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $z_plane_O_Shell[$i]*mm";}
	$detector{"dimensions"}  = $dimen; 
	$detector{"material"}    = "carbonFiber";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	# outer back
	$nplanes_O_Shell = 8;
	@z_plane_O_Shell = ($O_Shell_Z5, $O_Shell_Z6, $O_Shell_Z7, $O_Shell_Z8, $O_Shell_Z9, $O_Shell_Z10, $O_Shell_Z10, $O_Shell_Z11);
	@iradius_O_Shell = ($O_Shell_I5, $O_Shell_I6, $O_Shell_I7, $O_Shell_I8, $O_Shell_I9, $O_Shell_I10, $O_Shell_I11, $O_Shell_I11);
	@oradius_O_Shell = ($O_Shell_O5, $O_Shell_O6, $O_Shell_O7, $O_Shell_O8, $O_Shell_O9, $O_Shell_O10, $O_Shell_O10, $O_Shell_O11);
	%detector = init_det();
	$detector{"name"}        = "ft_cal_outer_shell_b";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft outer_shell_b";
	$detector{"color"}       = "F5DA81";
	$detector{"type"}        = "Polycone";	
	$dimen = "0.0*deg 360*deg $nplanes_O_Shell*counts";
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $iradius_O_Shell[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $oradius_O_Shell[$i]*mm";}
	for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $z_plane_O_Shell[$i]*mm";}
	$detector{"dimensions"}  = $dimen; 
	$detector{"material"}    = "carbonFiber";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	# outer medium
	$nplanes_O_Shell = 2;
	@z_plane_O_Shell = ($O_Shell_Z5, $O_Shell_Z4);
	@iradius_O_Shell = ($O_Shell_I5, $O_Shell_I4);
	@oradius_O_Shell = ($O_Shell_O5, $O_Shell_O4);
	for(my $i=0; $i<4 ; $i++)
	{
		my $s = $i + 1;
		%detector = init_det();
		$detector{"name"}        = "ft_cal_outer_shell_m$s";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft outer_shell $s";
		$detector{"color"}       = "F5DA81";
		$detector{"type"}        = "Polycone";
		my $biangle=$BCup_iangle[$i];
		my $bdangle=$BCup_dangle[$i];
		$dimen = "$biangle*deg $bdangle*deg $nplanes_O_Shell*counts";
		for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $iradius_O_Shell[$i]*mm";}
		for(my $i = 0; $i <$nplanes_O_Shell; $i++) {$dimen = $dimen ." $oradius_O_Shell[$i]*mm"; }
		for(my $i = 0; $i <$nplanes_O_Shell; $i++){$dimen = $dimen ." $z_plane_O_Shell[$i]*mm";}
		$detector{"dimensions"}  = $dimen; 
		$detector{"material"}    = "carbonFiber";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);
	}	
}

###########################################################################################
# Define the Calorimeter Beam line
sub make_ft_cal_beamline
{
	# forward tagger tungsten beamline
	my $nplanes_BLine = 6;
	my @z_plane_BLine = ($BLine_Z1, $BLine_Z2, $BLine_Z2, $BLine_Z3, $BLine_Z3, $BLine_Z4);  
	my @oradius_BLine = ($BLine_MR, $BLine_MR, $BLine_MR, $BLine_MR, $BLine_OR, $BLine_OR);  
	my @iradius_BLine = ($BLine_IR, $BLine_IR, $BLine_IR, $BLine_IR, $BLine_IR, $BLine_IR);  
	my %detector = init_det();
	$detector{"name"}        = "ft_cal_bline";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft beam line";
	$detector{"color"}       = "ccff00";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_BLine*counts";
	for(my $i = 0; $i <$nplanes_BLine ; $i++) {$dimen = $dimen ." $iradius_BLine[$i]*mm";}
	for(my $i = 0; $i <$nplanes_BLine ; $i++) {$dimen = $dimen ." $oradius_BLine[$i]*mm";}
	for(my $i = 0; $i <$nplanes_BLine ; $i++) {$dimen = $dimen ." $z_plane_BLine[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_W";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);



my $TPlate_IR= $BLine_IR + $BLine_TN;
my $TPlate_OR= $BCup_ORE;

	my $nplanes_TPlate = 2;
	my @z_plane_TPlate = ($TPlate_Z1, $TPlate_Z2);  
	my @oradius_TPlate = ($TPlate_OR, $TPlate_OR);  
	my @iradius_TPlate = ($TPlate_IR, $TPlate_IR);  
	
	# tungsten plate on the back of the calorimeter
	%detector = init_det();
	$detector{"name"}        = "ft_cal_tplate";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft tungsten plate";
	$detector{"color"}       = "ccff00";
	$detector{"type"}        = "Polycone";
	$dimen = "0.0*deg 360*deg $nplanes_TPlate*counts";
	for(my $i = 0; $i <$nplanes_TPlate ; $i++) {$dimen = $dimen ." $iradius_TPlate[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TPlate ; $i++) {$dimen = $dimen ." $oradius_TPlate[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TPlate ; $i++) {$dimen = $dimen ." $z_plane_TPlate[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_W";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);





	# steel collar on the front
	my $nplanes_TCollar = 2;
	my @z_plane_TCollar = ($BLine_Z1, $BLine_Z2);  
	my @oradius_TCollar = ($BLine_FR, $BLine_FR);  
	my @iradius_TCollar = ($BLine_MR, $BLine_MR);  
	%detector = init_det();
	$detector{"name"}        = "ft_cal_collar";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft beam collar";
	$detector{"color"}       = "cccccc";
	$detector{"type"}        = "Polycone";
	$dimen = "0.0*deg 360*deg $nplanes_TCollar*counts";
	for(my $i = 0; $i <$nplanes_TCollar ; $i++) {$dimen = $dimen ." $iradius_TCollar[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCollar ; $i++) {$dimen = $dimen ." $oradius_TCollar[$i]*mm";}
	for(my $i = 0; $i <$nplanes_TCollar ; $i++) {$dimen = $dimen ." $z_plane_TCollar[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_STAINLESS-STEEL";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	# Define the tube between Calorimeter and Torus Inner Ring
	my $Tube_IR         =  $BLine_IR;
	# define positions based on z of torus inner ring front face
	my $Tube_end  = $torus_z  - 0.1;    # leave 0.1 mm to avoid overlaps
	my $Tube_beg  = $BLine_Z4 + 0.1 + $FEE_Disk_LN + 0.1;
	my $Tube_z1   = $Tube_end - $flange_TN;
	my $Tube_z2   = $Tube_beg + $flange_TN;
	
	my $nplanes_Tube = 6;
	my @z_plane_Tube = (        $Tube_beg,         $Tube_z2, $Tube_z2, $Tube_z1,        $Tube_z1,       $Tube_end);
	my @oradius_Tube = ( $front_flange_OR, $front_flange_OR, $Tube_OR, $Tube_OR, $back_flange_OR, $back_flange_OR);
	my @iradius_Tube = (         $Tube_IR,         $Tube_IR, $Tube_IR, $Tube_IR,        $Tube_IR,        $Tube_IR);
	
	%detector = init_det();
	$detector{"name"}        = "ft_2_torus_tube";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "tube from ft to torus";
	$detector{"color"}       = "99cc00";
	$detector{"type"}        = "Polycone";
	$dimen = "0.0*deg 360*deg $nplanes_Tube*counts";
	for(my $i = 0; $i <$nplanes_Tube ; $i++) {$dimen = $dimen ." $iradius_Tube[$i]*mm";}
	for(my $i = 0; $i <$nplanes_Tube ; $i++) {$dimen = $dimen ." $oradius_Tube[$i]*mm";}
	for(my $i = 0; $i <$nplanes_Tube ; $i++) {$dimen = $dimen ." $z_plane_Tube[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_STAINLESS-STEEL";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

	
	# Define Aluminum Beam Pipe and Vacuum
	my $AL_BLine_TN=1.0;
	my $AL_BLine_IR=25.4;
	my $AL_BLine_OR=$AL_BLine_IR+$AL_BLine_TN;
	my $AL_BLine_LT=($Tube_end-$BLine_BG)/2.;
	my $AL_BLine_Z =($Tube_end+$BLine_BG)/2.;
	
	%detector = init_det();
	$detector{"name"}        = "ft_cal_al_bline";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft aluminum beam line";
	$detector{"pos"}         = "0.0*cm 0.0*cm $AL_BLine_Z*mm";
	$detector{"color"}       = "F2F2F2";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$AL_BLine_IR*mm $AL_BLine_OR*mm $AL_BLine_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Al";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
	
	%detector = init_det();
	$detector{"name"}        = "ft_cal_al_bline_vacuum";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft aluminum beam line vacuum";
	$detector{"pos"}         = "0.0*cm 0.0*cm $AL_BLine_Z*mm";
	$detector{"color"}       = "F2F2F2";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "0.0*mm $AL_BLine_IR*mm $AL_BLine_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_Galactic";
	$detector{"visible"}     = 0;
	print_det(\%configuration, \%detector);
}


sub make_ft_cal
{
	make_ft_cal_mother_volume();
	make_ft_cal_crystals_volume();
	make_ft_cal_crystals();
	make_ft_cal_flux();
	make_ft_cal_copper();
	make_ft_cal_motherboard();
	make_ft_cal_led();
	make_ft_cal_tcup();
	make_ft_cal_insulation();
	make_ft_cal_shell();
	make_ft_cal_beamline();
	make_ft_moellerdisk();
}





sub make_ft_hodo
{
	my %detector = init_det();
	$detector{"name"}        = "ft_hodo";
	$detector{"mother"}      = "root";
	$detector{"description"} = "ft scintillation hodoscope";
	$detector{"pos"}         = "0.0*cm 0.0*cm $VETO_Z*mm";
	$detector{"color"}       = "3399FF";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$VETO_IR*mm $VETO_OR*mm $VETO_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "G4_AIR";
	$detector{"visible"}     = 0;
	print_det(\%configuration, \%detector);

	for ( my $l = 0; $l < 3; $l++ ) { # loop over layers
 
		
		%detector = init_det();
		$detector{"name"}        = "ft_hodo_ls$l";
		$detector{"mother"}      = "ft_hodo";
		$detector{"description"} = "ft_hodo layer $l support";
		$detector{"pos"}         = "0.0*cm 0.0*cm $LS_ZN[$l]*mm";
		$detector{"color"}       = "EFEFFB";
		$detector{"type"}        = "Tube";
		$detector{"dimensions"}  = "$VETO_IR*mm $VETO_OR*mm $LS_TN[$l]*mm 0.*deg 360.*deg";
		$detector{"material"}    = "carbonFiber";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);
	}
	
	my $l=0; # thick layer 
	my $p_X=0.;
	my $p_Y=0.;
	my $p_nsize_ox = 0.;
	my $p_nsize_oy = 0.;
	my $p_nsize_ix = 0.;
	my $p_nsize_iy = 0.;
	my $p_tsize_o = 0.;
	my @p_Z= ($LS_ZN[1]+$LS_TN[1] +$p15_WT[$l] ,$LS_ZN[1]-$LS_TN[1] -$p15_WT[$l]) ;
	my $p_i=0;
	my $p_sec=0;
	my $p_atsec=0;
	my $sec = 0;
	for ( my $i = 0; $i < $p_Ntiles; $i++ ) {
	    $p_i=$i+($l+1)*1000;
	    $p_X = $x_hodo_thick[$i]; # R is in cm
	    $p_Y = $y_hodo_thick[$i]; # R is in cm
	    $sec = $sector_hodo_thick[$i];
	    print "$sec, \t P$p_size[$i], \t $p_atsec, \t $p_X,  \t $p_Y \n";
	    $p_nsize_ox = $size_x_hodo_thick[$i] / 2. + $paint_thick /10; # outer layer with paint
	    $p_nsize_oy = $size_y_hodo_thick[$i] / 2. + $paint_thick /10; # outer layer with paint
	    $p_nsize_ix = $size_x_hodo_thick[$i] / 2. + $paint_thick /10;  # inner cube with scintillator
	    $p_nsize_iy = $size_y_hodo_thick[$i] / 2. + $paint_thick /10;  # inner cube with scintillator
	    $p_tsize_o = $p15_WT[$l] + $paint_thick; # thicness with paint 
	    # define tile mother volume
	    %detector = init_det();
	    $detector{"name"}        = "ft_hodo_$p_i";
	    $detector{"mother"}      = "ft_hodo";
	    $detector{"description"} = "ft_hodo paint layer $l tyle $i rot $q";
	    $detector{"pos"}         = "$x_hodo_thick[$i]*cm $y_hodo_thick[$i]*cm $p_Z[$l]*mm";
	    $detector{"rotation"}    = "0*deg 0*deg 0*deg";
	    $detector{"color"}       = "3399FF";
	    $detector{"type"}        = "Box";
	    $detector{"dimensions"}  = "$p_nsize_ox*cm $p_nsize_oy*cm $p_tsize_o*mm";
	    $detector{"material"}    = "tidioxi";
	    $detector{"ncopy"}       = $p_i;
	    $detector{"style"}       = 1;
	    print_det(\%configuration, \%detector);

	    %detector = init_det();
	    $detector{"name"}        = "ft_hodo_tyle_$p_i";
	    $detector{"mother"}      = "ft_hodo_$p_i";
	    $detector{"description"} = "ft_hodo tyle layer $l tyle $i sec $sec";
	    $detector{"pos"}         = "0.0*mm 0.0*mm 0.0*mm";
	    $detector{"rotation"}    = "0*deg 0*deg 0*deg";
	    $detector{"color"}       = "BCA9F5";
	    $detector{"type"}        = "Box";
	    $detector{"dimensions"}  = "$p_nsize_ix*cm $p_nsize_iy*cm $p15_WT[$l]*mm";
	    $detector{"material"}    = "eljen204";
	    $detector{"style"}       = 1;
	    $detector{"sensitivity"} = "ft_hodo";
	    $detector{"hit_type"}    = "ft_hodo";
	    $detector{"identifiers"} = "ih manual $l iv manual $q id manual $i";	
	    print_det(\%configuration, \%detector);
	    
	}

	$l = 1;
	for ( my $i = 0; $i < $p_Ntiles; $i++ ) {
	    $p_i=$i+($l+1)*1000;
	    $p_X = $x_hodo_thin[$i]; # R is in cm
	    $p_Y = $y_hodo_thin[$i]; # R is in cm
	    $sec = $sector_hodo_thin[$i];
	    print "$sec, \t P$p_size[$i], \t $p_atsec, \t $p_X,  \t $p_Y \n";
	    $p_nsize_ox = $size_x_hodo_thin[$i] / 2. + $paint_thick /10; # outer layer with paint
	    $p_nsize_oy = $size_y_hodo_thin[$i] / 2. + $paint_thick /10; # outer layer with paint
	    $p_nsize_ix = $size_x_hodo_thin[$i] / 2. + $paint_thick /10;  # inner cube with scintillator
	    $p_nsize_iy = $size_y_hodo_thin[$i] / 2. + $paint_thick /10;  # inner cube with scintillator
	    $p_tsize_o = $p15_WT[$l] + $paint_thick; # thicness with paint 
	    # define tile mother volume
	    %detector = init_det();
	    $detector{"name"}        = "ft_hodo_$p_i";
	    $detector{"mother"}      = "ft_hodo";
	    $detector{"description"} = "ft_hodo paint layer $l tyle $i rot $q";
	    $detector{"pos"}         = "$x_hodo_thin[$i]*cm $y_hodo_thin[$i]*cm $p_Z[$l]*mm";
	    $detector{"rotation"}    = "0*deg 0*deg 0*deg";
	    $detector{"color"}       = "3399FF";
	    $detector{"type"}        = "Box";
	    $detector{"dimensions"}  = "$p_nsize_ox*cm $p_nsize_oy*cm $p_tsize_o*mm";
	    $detector{"material"}    = "tidioxi";
	    $detector{"ncopy"}       = $p_i;
	    $detector{"style"}       = 1;
	    print_det(\%configuration, \%detector);

	    %detector = init_det();
	    $detector{"name"}        = "ft_hodo_tyle_$p_i";
	    $detector{"mother"}      = "ft_hodo_$p_i";
	    $detector{"description"} = "ft_hodo tyle layer $l tyle $i sec $sec";
	    $detector{"pos"}         = "0.0*mm 0.0*mm 0.0*mm";
	    $detector{"rotation"}    = "0*deg 0*deg 0*deg";
	    $detector{"color"}       = "BCA9F5";
	    $detector{"type"}        = "Box";
	    $detector{"dimensions"}  = "$p_nsize_ix*cm $p_nsize_iy*cm $p15_WT[$l]*mm";
	    $detector{"material"}    = "eljen204";
	    $detector{"style"}       = 1;
	    $detector{"sensitivity"} = "ft_hodo";
	    $detector{"hit_type"}    = "ft_hodo";
	    $detector{"identifiers"} = "ih manual $l iv manual $q id manual $i";	
	    print_det(\%configuration, \%detector);
	    
	}


}



###########################################################################################
# Define the FT Tracker Geometry Components
sub make_ft_trk_mother
{
        my $zpos      = $ftm_starting;
    	my %detector = init_det();
	$detector{"name"}        = "ft_trk";
	$detector{"mother"}      = "root";
	$detector{"description"} = "ft tracker micromegas";
	$detector{"pos"}         = "0*mm 0*mm $zpos*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = "aaaaff";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$ftm_ir*mm $ftm_or*mm $ftm_dz*mm 0*deg 360*deg";
	$detector{"material"}    = "Air";
	$detector{"visible"}     = 0;
	print_det(\%configuration, \%detector);
}



sub place_epoxy
{
        my $l    = shift;
	my $layer_no       = $l + 1;
    
	my $z          = - $ftm_starting + $starting_point[$l];
	my $vname      = "ft_trk_epoxy";
	my $descriptio = "epoxy, layer $layer_no";
    
	# names
	my $r         = 0.000;
	my $PDz       = $Epoxy_Dz;
	my $PSPhi     = 0.0;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $epoxy_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $epoxy_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}

sub place_pcboard
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no   = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;

	if($type == 1)
	{
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz;
		$vname       = "ft_trk_pcboard_X_L";
		$descriptio  = "pc board X, layer $layer_no";
	 }
    
	if($type == 2) 
	{
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz;
		$vname       = "ft_trok_pcboard_Y_L";
		$descriptio  = "pc board Y, layer $layer_no";
        }
    
	# names
	my $r         = 0.000;
	my $PDz       = $PCB_Dz;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $pcboard_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $pcboard_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}

sub place_strips
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no       = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;
	
	if($type == 1)
        {
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz*2. - $Strips_Dz;
		$vname       = "ft_trk_strips_X_L";
		$descriptio  = "strips X, layer $layer_no";
	}
    
	if($type == 2) 
        {
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz*2. + $Strips_Dz;
		$vname       = "ft_trk_strips_Y_L";
		$descriptio  = "strips Y, layer $layer_no";
         }
    
	# names
	my $r         = 0.000;
	my $PDz       = $Strips_Dz;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $strips_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $strips_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}

sub place_gas1
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no       = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;
	
	if($type == 1)
        {
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz*2. - $Strips_Dz*2. - $Gas1_Dz;
		$vname       = "ft_trk_gas1_X_L";
		$descriptio  = "gas1 X, layer $layer_no";
        }
    
	if($type == 2) 
        {
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz*2. + $Strips_Dz*2. + $Gas1_Dz;
		$vname       = "ft_trk_gas1_Y_L";
		$descriptio  = "gas1 Y, layer $layer_no";
        }
    
	# names
	my $r         = 0.000;
	my $PDz       = $Gas1_Dz;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $gas_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $gas_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}

sub place_mesh
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no       = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;
	
	if($type == 1)
        {  
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz*2. - $Strips_Dz*2. - $Gas1_Dz*2. - $Mesh_Dz;
		$vname       = "ft_trk_mesh_X_L";
		$descriptio  = "mesh X, layer $layer_no";
        }  

	if($type == 2)
        {  
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz*2. + $Strips_Dz*2. + $Gas1_Dz*2. + $Mesh_Dz;
		$vname       = "ft_trk_mesh_Y_L";
		$descriptio  = "mesh Y, layer $layer_no";
        }
	# names
	my $r         = 0.000;
	my $PDz       = $Mesh_Dz;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $mesh_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $mesh_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}


sub place_gas2
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no       = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;
    
	if($type == 1)
	{
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz*2. - $Strips_Dz*2. - $Gas1_Dz*2. - $Mesh_Dz*2. - $Gas2_Dz;
		$vname       = "ft_trk_gas2_X_L";
		$descriptio  = "gas2 X, layer $layer_no";
	}
    
	if($type == 2) 
	{
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz*2. + $Strips_Dz*2. + $Gas1_Dz*2. + $Mesh_Dz*2. + $Gas2_Dz;
		$vname       = "ft_trk_gas2_Y_L";
		$descriptio  = "gas2 Y, layer $layer_no";
	}
    
	# names
	my $r         = 0.000;
	my $PDz       = $Gas2_Dz;
	my $PDPhi     = 360.000;

    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $gas_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    =  $gas_material;
	$detector{"style"}       = 1;
	$detector{"sensitivity"} = "ftm";
	$detector{"hit_type"}    = "ftm";
	$detector{"identifiers"} ="superlayer manual $layer_no type manual $type segment manual $detector{'ncopy'} strip manual 1";
	print_det(\%configuration, \%detector);
}


sub place_drift
{
        my $l    = shift; 
	my $type = shift;
	my $layer_no       = $l + 1;
	my $z          = 0;
	my $vname      = 0;
	my $descriptio = 0;
	my $PSPhi     = 0.0;
	
    
	if($type == 1)
        {
	        $z           =   - $ftm_starting + $starting_point[$l] - $Epoxy_Dz - $PCB_Dz*2. - $Strips_Dz*2. - $Gas1_Dz*2. - $Mesh_Dz*2. - $Gas2_Dz*2. - $Drift_Dz;
		$vname       = "ft_trk_drift_X_L";
		$descriptio  = "drift X, layer $layer_no";
        }

	if($type == 2)
        {
	        $z           =   - $ftm_starting + $starting_point[$l] + $Epoxy_Dz + $PCB_Dz*2. + $Strips_Dz*2. + $Gas1_Dz*2. + $Mesh_Dz*2. + $Gas2_Dz*2. + $Drift_Dz;
		$vname       = "ft_trk_drift_Y_L";
		$descriptio  = "drift Y, layer $layer_no";
        }

	# names
	my $r         = 0.000;
	my $PDz       = $Drift_Dz;
	my $PDPhi     = 360.000;
	
    	my %detector = init_det();
	$detector{"name"}        = "$vname$layer_no";
	$detector{"mother"}      = "ft_trk";
	$detector{"description"} = "$descriptio";
	$detector{"pos"}         = "0.000*mm 0.000*mm $z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = $drift_color;
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$InnerRadius*mm $OuterRadius*mm $PDz*mm $PSPhi*deg $PDPhi*deg";
	$detector{"material"}    = $drift_material;
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);
}



###########################################################################################
# Define the FTM Electronic Boxes and Support Structure
sub make_ft_trk_fee_boxes
{
        # create disk
        my $nplanes_FEE_Disk = 2;
	my @z_FEE_Disk = ($BLine_Z4 + 0.1, $BLine_Z4 + 0.1 + $FEE_Disk_LN);
	my @oradius_FEE_Disk = ($FEE_Disk_OR   , $FEE_Disk_OR);
	my @iradius_FEE_Disk = ($BLine_OR + 0.1, $BLine_OR + 0.1);
	my %detector = init_det();
	$detector{"name"}        = "ft_trk_fee_disk";
	$detector{"mother"}      = "ft_cal";
	$detector{"description"} = "ft-trk fee support disk";
	$detector{"color"}       = "999999";
	$detector{"type"}        = "Polycone";
	my $dimen = "0.0*deg 360*deg $nplanes_FEE_Disk*counts";
	for(my $i = 0; $i <$nplanes_FEE_Disk; $i++) {$dimen = $dimen ." $iradius_FEE_Disk[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FEE_Disk; $i++) {$dimen = $dimen ." $oradius_FEE_Disk[$i]*mm";}
	for(my $i = 0; $i <$nplanes_FEE_Disk; $i++) {$dimen = $dimen ." $z_FEE_Disk[$i]*mm";}
	$detector{"dimensions"}  = $dimen;
	$detector{"material"}    = "G4_Al";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);

        # create arms
	for ( my $i = 0; $i < 3; $i++ ) {
		my $FEE_ARM_X =   ($FEE_Disk_OR+ 2.+ $FEE_ARM_LN*cos($FEE_polar_angle/$degrad))*cos($FEE_azimuthal_angle[$i]/$degrad);
		my $FEE_ARM_Y = - ($FEE_Disk_OR+ 2.+ $FEE_ARM_LN*cos($FEE_polar_angle/$degrad))*sin($FEE_azimuthal_angle[$i]/$degrad);
		my $FEE_ARM_Z =   $z_FEE_Disk[0] + $FEE_Disk_LN/2. + $FEE_ARM_LN*sin($FEE_polar_angle/$degrad);
		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_arm_$i";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft-trk arm $i";
		$detector{"color"}       = "999999";
		$detector{"type"}        = "Box";
		$detector{"pos"}         = "$FEE_ARM_X*mm $FEE_ARM_Y*mm $FEE_ARM_Z*mm";
		$detector{"rotation"}    = "ordered: zyx $FEE_azimuthal_angle[$i]*deg $FEE_polar_angle*deg 0*deg ";
		$detector{"dimensions"}  = "$FEE_ARM_LN*mm $FEE_ARM_WD*mm $FEE_Disk_LN*mm";
		$detector{"material"}    = "G4_Al";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);


		my $FEE_R = ($FEE_ARM_LN - $FEE_PVT_HT)*cos($FEE_polar_angle/$degrad) + ($FEE_PVT_LN+$FEE_Disk_LN+0.2)*sin($FEE_polar_angle/$degrad);
		my $FEE_X = $FEE_ARM_X + $FEE_R*cos($FEE_azimuthal_angle[$i]/$degrad);
		my $FEE_Y = $FEE_ARM_Y - $FEE_R*sin($FEE_azimuthal_angle[$i]/$degrad);
		my $FEE_Z=  $FEE_ARM_Z + ($FEE_ARM_LN - $FEE_PVT_HT)*sin($FEE_polar_angle/$degrad) - ($FEE_PVT_LN+$FEE_Disk_LN+0.2)*cos($FEE_polar_angle/$degrad);
		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_pvt_box_$i";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft-trk fee pvt box $i";
		$detector{"color"}       = "EFFBFB";
		$detector{"type"}        = "Box";
		$detector{"pos"}         = "$FEE_X*mm $FEE_Y*mm $FEE_Z*mm";
		$detector{"rotation"}    = "ordered: zyx $FEE_azimuthal_angle[$i]*deg $FEE_polar_angle*deg 0*deg ";
		$detector{"dimensions"}  = "$FEE_PVT_HT*mm $FEE_PVT_WD*mm $FEE_PVT_LN*mm";
		$detector{"material"}    = "G4_WATER";
		$detector{"style"}       = 1;
#		print_det(\%configuration, \%detector);


		$FEE_R = ($FEE_ARM_LN - $FEE_HT)*cos($FEE_polar_angle/$degrad) + ($FEE_LN+$FEE_Disk_LN+0.2)*sin($FEE_polar_angle/$degrad);
		$FEE_X = $FEE_ARM_X + $FEE_R*cos($FEE_azimuthal_angle[$i]/$degrad);
		$FEE_Y = $FEE_ARM_Y - $FEE_R*sin($FEE_azimuthal_angle[$i]/$degrad);
		$FEE_Z=  $FEE_ARM_Z + ($FEE_ARM_LN - $FEE_HT)*sin($FEE_polar_angle/$degrad) - ($FEE_LN+$FEE_Disk_LN+0.2)*cos($FEE_polar_angle/$degrad);
		my %detector = init_det();
		$detector{"name"}        = "ft_trk_fee_box_$i";
		#    $detector{"mother"}      = "ft_trk_fee_pvt_box_$i";
		$detector{"mother"}      = "ft_cal";
		$detector{"description"} = "ft-trk fee box $i";
		$detector{"color"}       = "999999";
		$detector{"type"}        = "Box";
		$detector{"pos"}         = "$FEE_X*mm $FEE_Y*mm $FEE_Z*mm";
		#    $detector{"pos"}         = "0.0*mm 0.0*mm 0.0*mm";
		$detector{"rotation"}    = "ordered: zyx $FEE_azimuthal_angle[$i]*deg $FEE_polar_angle*deg 0*deg ";
		#    $detector{"rotation"}    = "0*deg 0*deg 0*deg";
		$detector{"dimensions"}  = "$FEE_HT*mm $FEE_WD*mm $FEE_LN*mm";
		$detector{"material"}    = "G4_Al";
		#    $detector{"material"}    = "Gadolinium";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);

		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_air_$i";
		$detector{"mother"}      = "ft_trk_fee_box_$i";
		$detector{"description"} = "ft-trk fee air $i";
		$detector{"pos"}         = "0.*mm 0.*mm 0.*mm";
		$detector{"rotation"}    = "0*deg 0*deg 0*deg";
		$detector{"color"}       = "CCFFFF";
		$detector{"type"}        = "Box";
		$detector{"dimensions"}  = "$FEE_A_HT*mm $FEE_A_WD*mm $FEE_A_LN*mm";
		$detector{"material"}    = "G4_AIR";
		$detector{"style"}       = 1;
		print_det(\%configuration, \%detector);


		my $flux_FEE_TN = 0.5;
		my $flux_FEE_HT = $FEE_A_HT - 2.*$flux_FEE_TN;
		my $flux_FEE_WD = $FEE_A_WD - 2.*$flux_FEE_TN;
		my $flux_FEE_LN = $flux_FEE_TN;
		my $flux_FEE_Z  = -$FEE_A_LN + $flux_FEE_TN;
		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_flux_1_$i";
		$detector{"mother"}      = "ft_trk_fee_air_$i";
		$detector{"description"} = "ft-trk fee flux 1";
		$detector{"pos"}         = "0.*mm 0.*mm $flux_FEE_Z*mm";
		$detector{"rotation"}    = "0*deg 0*deg 0*deg";
		$detector{"color"}       = "aa0088";
		$detector{"type"}        = "Box";
		$detector{"dimensions"}  = "$flux_FEE_HT*mm $flux_FEE_WD*mm $flux_FEE_LN*mm";
		$detector{"material"}    = "G4_Galactic";
		$detector{"style"}       = 1;
		$detector{"sensitivity"} = "flux";
		$detector{"hit_type"}    = "flux";
		$detector{"identifiers"} = "id manual 4";
		print_det(\%configuration, \%detector);

		$flux_FEE_HT = $FEE_A_HT - 2.*$flux_FEE_TN;
		$flux_FEE_WD = $flux_FEE_TN;
		$flux_FEE_LN = $FEE_A_LN - 2.*$flux_FEE_TN;
		my $flux_FEE_Y  = - $FEE_WD*0.6;
		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_flux_2_$i";
		$detector{"mother"}      = "ft_trk_fee_air_$i";
		$detector{"description"} = "ft-trk fee flux 2";
		$detector{"pos"}         = "0.*mm $flux_FEE_Y*mm 0.*mm";
		$detector{"rotation"}    = "0*deg 0.*deg 0*deg";
		$detector{"color"}       = "aa0088";
		$detector{"type"}        = "Box";
		$detector{"dimensions"}  = "$flux_FEE_HT*mm $flux_FEE_WD*mm $flux_FEE_LN*mm";
		$detector{"material"}    = "G4_Galactic";
		$detector{"style"}       = 1;
		$detector{"sensitivity"} = "flux";
		$detector{"hit_type"}    = "flux";
		$detector{"identifiers"} = "id manual 5";
		print_det(\%configuration, \%detector);

		my $dose_FEE_TN=5.;
		my $dose_FEE_HT = $FEE_A_HT - $dose_FEE_TN;
		my $dose_FEE_WD = $dose_FEE_TN;
		my $dose_FEE_LN = $FEE_A_LN - $dose_FEE_TN;
		my $dose_FEE_Y  = 0.;
		%detector = init_det();
		$detector{"name"}        = "ft_trk_fee_dose_$i";
		$detector{"mother"}      = "ft_trk_fee_air_$i";
		$detector{"description"} = "ft-trk fee dose";
		$detector{"pos"}         = "0.*mm $dose_FEE_Y*mm 0.*mm";
		$detector{"rotation"}    = "0*deg 0.*deg 0*deg";
		$detector{"color"}       = "003300";
		$detector{"type"}        = "Box";
		$detector{"dimensions"}  = "$dose_FEE_HT*mm $dose_FEE_WD*mm $dose_FEE_LN*mm";
		$detector{"material"}    = "scintillator";
		$detector{"mfield"}      = "no";
		$detector{"style"}       = 1;
		$detector{"sensitivity"} = "ft_hodo";
		$detector{"hit_type"}    = "ft_hodo";
		$detector{"identifiers"} = "id manual 5000";
		print_det(\%configuration, \%detector);

	}
}




sub make_ft_trk
{
        make_ft_trk_mother();
        for(my $l = 0; $l < $nlayer; $l++) 
	{
		my $layer_no       = $l + 1;
		place_epoxy($l);
		
		# X layer type
		place_pcboard($l,1);
		place_strips($l,1);
		place_gas1($l,1);
		place_mesh($l,1);
		place_gas2($l,1);
		place_drift($l,1);
			
		# Y layer type
		place_pcboard($l,2);
		place_strips($l,2);
		place_gas1($l,2);
		place_mesh($l,2);
		place_gas2($l,2);
		place_drift($l,2);
        }
	make_ft_trk_fee_boxes();
}



# Tungsten Cone
my $nplanes_cone = 4;
my @zplane_cone   = (  750.0, 1750.0, 1750.0, 1809.2);
my @oradius_cone  = (   32.0,   76.0,   59.0,   59.0);
my @mradius_cone  = (   31.0,   40.0,   40.0,   40.0); 
my @iradius_cone  = (   30.0,   30.0,   30.0,   30.0); 


# Aluminum Beam Pipe
my $al_tube_TN=1.0;
my $al_tube_IR=25.4;
my $al_tube_OR=$al_tube_IR+$al_tube_TN;
my $al_tube_LT=($zplane_cone[3]-($zplane_cone[0]+300.))/2.;
my $al_tube_Z =($zplane_cone[3]+($zplane_cone[0]+300.))/2.;

# Aluminum Beam Pipe Window
my $al_window_TN=0.05;
my $al_window_OR=$al_tube_OR;
my $al_window_Z =$al_tube_Z-$al_tube_LT-$al_window_TN;

# HTCC Moller Cup
my $nplanes_cup = 4;
my @zplane_cup   = (350.0, 1318.4, 1415.2, 1724.1);
my @oradius_cup  = ( 30.0,  114.8,  114.8,  139.0);
my @iradius_cup  = ( 29.0,  113.8,  113.8,  138.0);


# Mother Volume
#my $nplanes_mv = 7;
#my @zplane_mv   = ( $zplane_cup[0],  $zplane_cup[1],  $zplane_cup[2],  $zplane_cup[3], $zplane_cone[1], $zplane_cone[2], $zplane_cone[3]);
#my @oradius_mv  = ($oradius_cup[0], $oradius_cup[1], $oradius_cup[2], $oradius_cup[3], $oradius_cup[3],$oradius_cone[2],$oradius_cone[2]);
#my @iradius_mv  = (            0.0,             0.0,             0.0,             0.0,             0.0,             0.0,             0.0);
my $nplanes_mv = 4;
my @zplane_mv   = ( $zplane_cone[0],  $zplane_cone[1],  $zplane_cone[2],  $zplane_cone[3]);
my @oradius_mv  = ($oradius_cone[0], $oradius_cone[1], $oradius_cone[2], $oradius_cone[3]);
my @iradius_mv  = (            0.0,             0.0,             0.0,             0.0,             0.0,             0.0,             0.0);


sub make_ft_shield
{
#############################
#       Mother Volume
#############################
	my %detector = init_det();
	$detector{"name"}         = "ft_shield";
	$detector{"mother"}       = "root";
	$detector{"description"}  = "ft shield mother volume";
	$detector{"color"}        = "ff8000";
	$detector{"type"}         = "Polycone";
	
	my $dimen = "0.0*deg 360*deg $nplanes_mv*counts";
	for(my $i = 0; $i <$nplanes_mv ; $i++)
	{
		$dimen = $dimen ." $iradius_mv[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_mv ; $i++)
	{
		$dimen = $dimen ." $oradius_mv[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_mv ; $i++)
	{
		$dimen = $dimen ." $zplane_mv[$i]*mm";
	}

	$detector{"dimensions"}   = $dimen;
	$detector{"material"}     = "Air";
	$detector{"visible"}     = 0;

	print_det(\%configuration, \%detector);


#############################
# Carbon Fiber Moller Cup
#############################
	%detector = init_det();
	$detector{"name"}        = "htcc_moeller_cup";
	$detector{"mother"}      = "ft_shield";
	$detector{"description"} = "htcc moeller cup";
	$detector{"color"}       = "575757";
	$detector{"type"}        = "Polycone";

	$dimen = "0.0*deg 360*deg $nplanes_cup*counts";
	for(my $i = 0; $i <$nplanes_cup ; $i++)
	{
		$dimen = $dimen ." $iradius_cup[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_cup ; $i++)
	{
		$dimen = $dimen ." $oradius_cup[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_cup ; $i++)
	{
		$dimen = $dimen ." $zplane_cup[$i]*mm";
	}
	$detector{"dimensions"} = $dimen;
	$detector{"material"}   = "CarbonFiber";
	$detector{"style"}       = 1;
	
#	print_det(\%configuration, \%detector);


#############################
# Tungsten Cone
#############################
	%detector = init_det();
	$detector{"name"}        = "ft_w_cone";
	$detector{"mother"}      = "ft_shield";
	$detector{"description"} = "ft tungsten cone";
	$detector{"color"}       = "F88017";
	$detector{"type"}        = "Polycone";
	$dimen = "0.0*deg 360*deg $nplanes_cone*counts";
	for(my $i = 0; $i <$nplanes_cone ; $i++)
	{
		$dimen = $dimen ." $iradius_cone[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_cone ; $i++)
	{
		$dimen = $dimen ." $oradius_cone[$i]*mm";
	}
	for(my $i = 0; $i <$nplanes_cone ; $i++)
	{
		$dimen = $dimen ." $zplane_cone[$i]*mm";
	}
	$detector{"dimensions"} = $dimen;
	$detector{"material"}   = "Tungsten";
	$detector{"style"}       = 1;
	
	print_det(\%configuration, \%detector);


#############################
#       Aluminum Tube
#############################
	%detector = init_det();
	$detector{"name"}        = "ft_al_tube";
	$detector{"mother"}      = "ft_shield";
	$detector{"description"} = "ft aluminum beam pipe";
	$detector{"pos"}         = "0*mm 0.0*mm $al_tube_Z*mm";
	$detector{"color"}       = "F2F2F2";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$al_tube_IR*mm $al_tube_OR*mm $al_tube_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "Aluminum";	
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);


#############################
# Aluminum Window
#############################
	%detector = init_det();
	$detector{"name"}        = "ft_al_window";
	$detector{"mother"}      = "ft_shield";
	$detector{"description"} = "ft aluminum beam pipe window";
	$detector{"pos"}         = "0*mm 0.0*mm $al_window_Z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = "F2F2F2";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "0.0*mm $al_window_OR*mm $al_window_TN*mm 0.*deg 360.*deg";
	$detector{"material"}    = "Aluminum";
	$detector{"style"}       = 1;
	print_det(\%configuration, \%detector);


#############################
# Vacuum inside the cone
#############################
	%detector = init_det();
	$detector{"name"}        = "ft_albpipe_vacuum";
	$detector{"mother"}      = "ft_shield";
	$detector{"description"} = "ft aluminum beam pipe vacuum";
	$detector{"pos"}         = "0*mm 0.0*mm $al_tube_Z*mm";
	$detector{"rotation"}    = "0*deg 0*deg 0*deg";
	$detector{"color"}       = "ffffff";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "0.0*mm $al_tube_IR*mm $al_tube_LT*mm 0.*deg 360.*deg";
	$detector{"material"}    = "Vacuum";
	$detector{"visible"}     = 0;
	print_det(\%configuration, \%detector);

}
