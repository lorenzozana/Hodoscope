#!/usr/bin/perl -w

use strict;
use lib ("$ENV{GEMC}/io");
use utils;
use parameters;
use geometry;
use math;

use Math::Trig;

# Help Message
sub help()
{
	print "\n Usage: \n";
	print "   geometry.pl <configuration filename>\n";
 	print "   Will create the CLAS12 Central Neutron Detector (CND) using the variation specified in the configuration file\n";
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

# Loading configuration file from argument
our %configuration = load_configuration($ARGV[0]);

# One can change the "variation" here if one is desired different from the config.dat
# $configuration{"variation"} = "myvar";

# Load the parameters
our %parameters    = get_parameters(%configuration);


# Assign parameters to local variables:

my $layers   = $parameters{"layers"};
my $paddles = $parameters{"paddles"};  # per layer!

my $length1 = $parameters{"paddles_length1"};  # length of paddles in each layer, numbered outwards from center
my $length2 = $parameters{"paddles_length2"};
my $length3 = $parameters{"paddles_length3"};

my $r0 = $parameters{"inner_radius"};   # doesn't include the wrapping
my $r1 = $parameters{"outer_radius"};

my $z_offset1 = $parameters{"z0_layer1"};  # offset of center of paddles in layer 1 from center of mother volume
my $z_offset2 = $parameters{"z0_layer2"};
my $z_offset3 = $parameters{"z0_layer3"};

my $mother_offset = $parameters{"z0_mothervol"}; # offset of center of mother volume from magnet center

my $mother_clearance = $parameters{"mothervol_z_gap"};    # cm, clearance at either end of mother volume
my $mother_gap1      = $parameters{"mothervol_gap_in"};   # cm, clearance on the inside of mother volume (just to fit in wrapping)
my $mother_gap2      = $parameters{"mothervol_gap_out"};  # cm, clearance on outside of mother volume (to allow for the corners of the trapezoid paddles)

my $layer_gap  = $parameters{"layer_gap"};
my $paddle_gap = $parameters{"paddle_gap"};
my $block_gap = $parameters{"segment_gap"};  # gap either side of block

my $wrap_thickness = $parameters{"wrap_thickness"};  # total thickness of wrapping material

my $uturn_r_1  = $parameters{"uturn_i_radius"};  # larger radius of uturn for inner layer
my $uturn_r_2  = $parameters{"uturn_m_radius"};  # larger radius of uturn for middle layer
my $uturn_r_3  = $parameters{"uturn_o_radius"};  # larger radius of uturn for outer layer



my @length = ($length1, $length2, $length3);  # full length of the paddles

my @uturn_r = ($uturn_r_1, $uturn_r_2, $uturn_r_3);  # uturn radius values

my @z_offset = ($z_offset1, $z_offset2, $z_offset3);  # offset of center of each paddle wrt center of magnet

my $angle_slice = 360.0/($paddles);  # degrees, angle corresponding to one segment in phi

my $dR = ($r1 - $r0 - (($layers-1) * $layer_gap)) / $layers;  # thickness of one layer (assuming all layers are equally thick)

my @mother_gap = ($mother_gap1, $mother_gap2);  #cm, clearance on the inside of mother volume (just to fit in wrapping), followed by
# clearance on outside of mother volume (to allow for the corners of the trapezoid paddles)

my @pcolor = ('33dd66', '239a47', '145828');  # paddle colors by layer
my $wcolor = 'af3cff';  # wrapping color
my $ucolor =  '3c78ff';  # u-turn color

my $half_diff;

# Mother Volume
sub make_cnd
{
	my $longest_half1 = 0.;
	my $longest_half2 = 0.;
    
	for(my $i=0; $i<$layers; $i++)
	{
		my $temp_dz1 = 0.5 * $length[$i] - $z_offset[$i];  #upstream half
		my $temp_dz2 = 0.5 * $length[$i] + $z_offset[$i] + $uturn_r[$i];  #downstream half
		
		if ($longest_half1 < $temp_dz1){
			$longest_half1 = $temp_dz1;
		}
		if ($longest_half2 < $temp_dz2){
			$longest_half2 = $temp_dz2;
		}
	}

	my $mother_dz = ($longest_half1 + $longest_half2) * 0.5 + $mother_clearance;

	$half_diff = 0.5 * ($longest_half2 - $longest_half1);

	my $IR = $r0 - $mother_gap[0];
	my $OR = $r1 + $mother_gap[1];
	
	my %detector = init_det();
	$detector{"name"}        = "cnd";
	$detector{"mother"}      = "root";
	$detector{"description"} = "Central Neutron Detector";
	$detector{"pos"}         = "0*cm 0*cm ($mother_offset+$half_diff)*cm";
	$detector{"color"}       = "33bb99";
	$detector{"type"}        = "Tube";
	$detector{"dimensions"}  = "$IR*cm $OR*cm $mother_dz*cm 0*deg 360*deg";
	$detector{"material"}    = "G4_AIR";
	$detector{"visible"}     = 0;
	$detector{"style"}       = 0;
	print_det(\%configuration, \%detector);
}

# Paddles
sub make_paddles
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap;
		my $outerRadius = $innerRadius + $dR;
		
		my $dz = $length[$j-1] / 2.0;
		my $z = sprintf("%.3f", $z_offset[$j-1]);

        my $bx = $innerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
        my $tx = $outerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
		
		for(my $i=1; $i<=($paddles); $i++)
		{
			my $theta = ((2*$i - 1 + ((-1)**($i)))/2)*$angle_slice;  # increment angle for every other paddle
            if ($i%2)
			{
         		#required vertices
		        my $ver1x = -$bx;
		        my $ver1y = $innerRadius;
		        my $ver2x = -$tx;
		        my $ver2y = $outerRadius;
		        my $ver3x = -(0.5)*$paddle_gap;
		        my $ver3y = $outerRadius;
		        my $ver4x = -(0.5)*$paddle_gap;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_Paddle_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $pcolor[$j-1];
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "ScintillatorB";
				$detector{"style"}       = 1;
				$detector{"ncopy"}       = $i;
				$detector{"sensitivity"} = "cnd";
				$detector{"hit_type"}    = "cnd";
				$detector{"identifiers"} = "layer manual $j paddle manual $i";
				print_det(\%configuration, \%detector);
			}
			else
            {
				#required vertices
		        my $ver1x = (0.5)*$paddle_gap;
		        my $ver1y = $innerRadius;
		        my $ver2x = (0.5)*$paddle_gap;
		        my $ver2y = $outerRadius;
		        my $ver3x = $tx;
		        my $ver3y = $outerRadius;
		        my $ver4x = $bx;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_Paddle_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $pcolor[$j-1];
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "ScintillatorB";
				$detector{"style"}       = 1;
				$detector{"ncopy"}       = $i;
				$detector{"sensitivity"} = "cnd";
				$detector{"hit_type"}    = "cnd";
				$detector{"identifiers"} = "layer manual $j paddle manual $i";
				print_det(\%configuration, \%detector);
			}
        }
	}
}

# Paddles_Wrapping_Under
sub make_paddles_wrapping_under
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap - $wrap_thickness;
		my $outerRadius = $innerRadius + $wrap_thickness;
		
		my $dz = $length[$j-1] / 2.0;
		my $z = sprintf("%.3f", $z_offset[$j-1]);

        my $bx = $innerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
        my $tx = $outerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
		
		for(my $i=1; $i<=($paddles); $i++)
		{
         	my $theta = ((2*$i - 1 + ((-1)**($i)))/2)*$angle_slice;  # increment angle for every other paddle
            if ($i%2)
			{
         		#required vertices
		        my $ver1x = -$bx;
		        my $ver1y = $innerRadius;
		        my $ver2x = -$tx;
		        my $ver2y = $outerRadius;
		        my $ver3x = -(0.5)*$paddle_gap;
		        my $ver3y = $outerRadius;
		        my $ver4x = -(0.5)*$paddle_gap;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleUnderWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Under Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
			else
            {
         		#required vertices
		        my $ver1x = (0.5)*$paddle_gap;
		        my $ver1y = $innerRadius;
		        my $ver2x = (0.5)*$paddle_gap;
		        my $ver2y = $outerRadius;
		        my $ver3x = $tx;
		        my $ver3y = $outerRadius;
		        my $ver4x = $bx;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleUnderWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Under Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
        }
	}
}

# Paddles_Wrapping_Upper
sub make_paddles_wrapping_upper
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + $j*$dR + ($j-1)*$layer_gap;
		my $outerRadius = $innerRadius + $wrap_thickness;
	
		my $dz = $length[$j-1] / 2.0;
		my $z = sprintf("%.3f", $z_offset[$j-1]);

        my $bx = $innerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
        my $tx = $outerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));

		for(my $i=1; $i<=($paddles); $i++)
		{
         	my $theta = ((2*$i - 1 + ((-1)**($i)))/2)*$angle_slice;  # increment angle for every other paddle
            if ($i%2)
			{
         		#required vertices
		        my $ver1x = -$bx;
		        my $ver1y = $innerRadius;
		        my $ver2x = -$tx;
		        my $ver2y = $outerRadius;
		        my $ver3x = -(0.5)*$paddle_gap;
		        my $ver3y = $outerRadius;
		        my $ver4x = -(0.5)*$paddle_gap;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleUpperWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Upper Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
			else
            {
         		#required vertices
		        my $ver1x = (0.5)*$paddle_gap;
		        my $ver1y = $innerRadius;
		        my $ver2x = (0.5)*$paddle_gap;
		        my $ver2y = $outerRadius;
		        my $ver3x = $tx;
		        my $ver3y = $outerRadius;
		        my $ver4x = $bx;
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleUpperWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Upper Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
        }
	}
}

# Paddles_Wrapping_Straight_Edge
sub make_paddles_wrapping_straight_edge
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap - $wrap_thickness;
		my $outerRadius = $innerRadius + $dR + 2*$wrap_thickness;

		my $dz = $length[$j-1] / 2.0;
		my $z = sprintf("%.3f", $z_offset[$j-1]);

		for(my $i=1; $i<=($paddles); $i++)
		{
			my $theta = ((2*$i - 1 + ((-1)**($i)))/2)*$angle_slice;  # increment angle for every other paddle
            if ($i%2)
			{
         		#required vertices
		        my $ver1x = -(0.5*$paddle_gap);
		        my $ver1y = $innerRadius;
		        my $ver2x = -(0.5*$paddle_gap);
		        my $ver2y = $outerRadius;
		        my $ver3x = (-(0.5*$paddle_gap)+$wrap_thickness);
		        my $ver3y = $outerRadius;
		        my $ver4x = (-(0.5*$paddle_gap)+$wrap_thickness);
		        my $ver4y = $innerRadius;

		        my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleStraightEdgeWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Straight Edge Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
			else
            {
         		#required vertices
		        my $ver1x = ((0.5*$paddle_gap)-$wrap_thickness);
		        my $ver1y = $innerRadius;
		        my $ver2x = ((0.5*$paddle_gap)-$wrap_thickness);
		        my $ver2y = $outerRadius;
		        my $ver3x = (0.5*$paddle_gap);
		        my $ver3y = $outerRadius;
		        my $ver4x = (0.5*$paddle_gap);
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleStraightEdgeWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Straight Edge Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
        }
	}
}

# Paddles_Wrapping_Angled_Edge
sub make_paddles_wrapping_angled_edge
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap - $wrap_thickness;
		my $outerRadius = $innerRadius + $dR + 2*$wrap_thickness;

		my $dz = $length[$j-1] / 2.0;
		my $z = sprintf("%.3f", $z_offset[$j-1]);

        my $bx = $innerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
        my $tx = $outerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
		
		for(my $i=1; $i<=($paddles); $i++)
		{
			my $theta = ((2*$i - 1 + ((-1)**($i)))/2)*$angle_slice;  # increment angle for every other paddle
            if ($i%2)
            {
         		#required vertices
		        my $ver1x = -$bx;
		        my $ver1y = $innerRadius;
		        my $ver2x = -$tx;
		        my $ver2y = $outerRadius;
		        my $ver3x = -$tx+(($wrap_thickness)/(cos(rad($angle_slice))));
		        my $ver3y = $outerRadius;
		        my $ver4x = -$bx+(($wrap_thickness)/(cos(rad($angle_slice))));
		        my $ver4y = $innerRadius;

				my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."_PaddleAngledEdgeWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Angled Edge Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
			else
            {
         		#required vertices
		        my $ver1x = $bx-(($wrap_thickness)/(cos(rad($angle_slice))));
		        my $ver1y = $innerRadius;
		       	my $ver2x = $tx-(($wrap_thickness)/(cos(rad($angle_slice))));
		        my $ver2y = $outerRadius;
		        my $ver3x = $tx;
		        my $ver3y = $outerRadius;
		        my $ver4x = $bx;
		        my $ver4y = $innerRadius;

        		my $z_final = $z-$half_diff;

				my %detector = init_det();
				$detector{"name"}        = "CND_Layer$j"."PaddleAngledEdgeWrap_$i";
				$detector{"mother"}      = "cnd";
				$detector{"description"} = "Central Neutron Detector, Layer $j, Scintillator Angled Edge Wrap $i";
				$detector{"pos"}         = "0*cm 0*cm $z_final*cm";
				$detector{"color"}       = $wcolor;
				$detector{"rotation"}    = "0*deg 0*deg $theta*deg";
				$detector{"type"}        = "G4GenericTrap";
				$detector{"dimensions"}  = "$dz*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm $ver1x*cm $ver1y*cm $ver2x*cm $ver2y*cm $ver3x*cm $ver3y*cm $ver4x*cm $ver4y*cm";
				$detector{"material"}    = "G4_Al";
				$detector{"mfield"}      = "no";
				$detector{"ncopy"}       = $i;
				$detector{"style"}       = 1;
				print_det(\%configuration, \%detector);
			}
        }
	}
}

# U-Turn
sub make_uturn
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap;
		my $outerRadius = $innerRadius + $dR;
		
		my $dz = $length[$j-1] / 2.0;
		my $dy = $dR / 2.0;
		my $z = sprintf("%.3f", ($dz + $z_offset[$j-1]));

        my $bx = $innerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
        my $tx = $outerRadius*tan(rad($angle_slice)) - 0.5*$block_gap/(cos(rad($angle_slice)));
		
		for(my $i=1; $i<=(0.5*$paddles); $i++)
		{
			my $theta = ($i-1)*2*$angle_slice;

 	        #rotations
			my $rotZ = 0.;
        	my $rotX = 270.;
          	my $rotY = 270.-$theta;

          	#position
			my $x = sprintf("%.11f", ($innerRadius+$dy)*(cos(rad($theta))));
			my $y = sprintf("%.11f", ($innerRadius+$dy)*(sin(rad($theta))));

			my $z_final = $z-$half_diff;

			my %detector = init_det();
			$detector{"name"}        = "CND_Layer$j"."_PaddleU-Turn_$i";
			$detector{"mother"}      = "cnd";
			$detector{"description"} = "Central Neutron Detector, Layer $j, U-Turn $i";
			$detector{"pos"}         = "$x*cm $y*cm $z_final*cm";
			$detector{"color"}       = $ucolor;
			$detector{"rotation"}    = "$rotX*deg $rotY*deg $rotZ*deg";
			$detector{"type"}        = "Cons";
			$detector{"dimensions"}  = "0*cm $bx*cm 0*cm $tx*cm $dy*cm 0*deg 180.*deg";
			$detector{"material"}    = "G4_PLEXIGLASS";
			$detector{"style"}       = 1;
			$detector{"ncopy"}       = $i;
			$detector{"identifiers"} = "layer manual $j paddle manual $i";
			print_det(\%configuration, \%detector);
        }
	}
}

# U-Turn Wrapping Side
sub make_uturn_wrapping_side
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap;
		my $outerRadius = $innerRadius + $dR;
		
		my $dz = $length[$j-1] / 2.0;
		my $dy = $dR / 2.0;
		my $z = sprintf("%.3f", ($dz + $z_offset[$j-1]));

        my $bxI = $innerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice))));
        my $txI = $outerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice))));

        my $bx = $bxI + (($wrap_thickness)/(cos(rad($angle_slice))));
		my $tx = $txI + (($wrap_thickness)/(cos(rad($angle_slice))));

		for(my $i=1; $i<=(0.5*$paddles); $i++)
		{
			my $theta = ($i-1)*2*$angle_slice;

 	        #rotations
			my $rotZ = 0.;
        	my $rotX = 270.;
          	my $rotY = 270.-$theta;
          	#position
			my $x = sprintf("%.11f", ($innerRadius+$dy)*(cos(rad($theta))));
			my $y = sprintf("%.11f", ($innerRadius+$dy)*(sin(rad($theta))));

			my $z_final = $z-$half_diff;

			my %detector = init_det();
			$detector{"name"}        = "CND_Layer$j"."_PaddleU-TurnSideWrap_$i";
			$detector{"mother"}      = "cnd";
			$detector{"description"} = "Central Neutron Detector, Layer $j, U-Turn Side Wrap $i";
			$detector{"pos"}         = "$x*cm $y*cm $z_final*cm";
			$detector{"color"}       = $wcolor;
			$detector{"rotation"}    = "$rotX*deg $rotY*deg $rotZ*deg";
			$detector{"type"}        = "Cons";
			$detector{"dimensions"}  = "$bxI*cm $bx*cm $txI*cm $tx*cm $dy*cm 0*deg 180.*deg";
			$detector{"material"}    = "G4_Al";
			$detector{"mfield"}      = "no";
			$detector{"ncopy"}       = $i;
			$detector{"style"}       = 1;
			print_det(\%configuration, \%detector);
        }
	}
}

# U-Turn Wrapping Under
sub make_uturn_wrapping_under
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + ($j-1)*$dR + ($j-1)*$layer_gap - $wrap_thickness;
		my $outerRadius = $innerRadius + $wrap_thickness;
		
		my $dz = $length[$j-1] / 2.0;
		my $dy = $wrap_thickness/ 2.0;
		my $z = sprintf("%.3f", ($dz + $z_offset[$j-1]));

        my $bx = $innerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
        my $tx = $outerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
		
		for(my $i=1; $i<=(0.5*$paddles); $i++)
		{
			my $theta = ($i-1)*2*$angle_slice;

 	        #rotations
			my $rotZ = 0.;
	      	my $rotX = 270.;
          	my $rotY = 270-$theta;
          	#position
			my $x = sprintf("%.11f", ($innerRadius+$dy)*(cos(rad($theta))));
			my $y = sprintf("%.11f", ($innerRadius+$dy)*(sin(rad($theta))));

			my $z_final = $z-$half_diff;

			my %detector = init_det();
			$detector{"name"}        = "CND_Layer$j"."_PaddleU-TurnUnderWrap_$i";
			$detector{"mother"}      = "cnd";
			$detector{"description"} = "Central Neutron Detector, Layer $j, U-Turn Under Wrap $i";
			$detector{"pos"}         = "$x*cm $y*cm $z_final*cm";
			$detector{"color"}       = $wcolor;
			$detector{"rotation"}    = "$rotX*deg $rotY*deg $rotZ*deg";
			$detector{"type"}        = "Cons";
			$detector{"dimensions"}  = "0*cm $bx*cm 0*cm $tx*cm $dy*cm 0*deg 180.*deg";
			$detector{"material"}    = "G4_Al";
			$detector{"mfield"}      = "no";
			$detector{"ncopy"}       = $i;
			$detector{"style"}       = 1;
			print_det(\%configuration, \%detector);
        }
	}
}

# U-Turn Wrapping Upper
sub make_uturn_wrapping_upper
{
	for(my $j=1; $j<=$layers; $j++)
	{
		my $innerRadius = $r0 + $j*$dR + ($j-1)*$layer_gap;
		my $outerRadius = $innerRadius + $wrap_thickness;
		
		my $dz = $length[$j-1] / 2.0;
		my $dy = $wrap_thickness / 2.0;
		my $z = sprintf("%.3f", ($dz + $z_offset[$j-1]));

        my $bx = $innerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
        my $tx = $outerRadius*tan(rad($angle_slice)) - ((0.5*$block_gap)/(cos(rad($angle_slice)))) + (($wrap_thickness)/(cos(rad($angle_slice))));
		
		for(my $i=1; $i<=(0.5*$paddles); $i++)
		{
			my $theta = ($i-1)*2*$angle_slice;

 	        #rotations
			my $rotZ = 0.;
        	my $rotX = 270.;
          	my $rotY = 270.-$theta;
          	#position
			my $x = sprintf("%.11f", ($innerRadius+$dy)*(cos(rad($theta))));
			my $y = sprintf("%.11f", ($innerRadius+$dy)*(sin(rad($theta))));

			my $z_final = $z-$half_diff;

			my %detector = init_det();
			$detector{"name"}        = "CND_Layer$j"."_PaddleU-TurnUpperWrap_$i";
			$detector{"mother"}      = "cnd";
			$detector{"description"} = "Central Neutron Detector, Layer $j, U-Turn Upper Wrap $i";
			$detector{"pos"}         = "$x*cm $y*cm $z_final*cm";
			$detector{"color"}       = $wcolor;
			$detector{"rotation"}    = "$rotX*deg $rotY*deg $rotZ*deg";
			$detector{"type"}        = "Cons";
			$detector{"dimensions"}  = "0*cm $bx*cm 0*cm $tx*cm $dy*cm 0*deg 180.*deg";
			$detector{"material"}    = "G4_Al";
			$detector{"mfield"}      = "no";
			$detector{"ncopy"}       = $i;
			$detector{"style"}       = 1;
			print_det(\%configuration, \%detector);
        }
	}
}


make_cnd();
make_paddles();
make_paddles_wrapping_under();
make_paddles_wrapping_upper();
make_paddles_wrapping_straight_edge();
make_paddles_wrapping_angled_edge();
make_uturn();
make_uturn_wrapping_side();
make_uturn_wrapping_under();
make_uturn_wrapping_upper();