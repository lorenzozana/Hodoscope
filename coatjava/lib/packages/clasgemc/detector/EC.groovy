//-----------------------------------------------------------
// Definitions for Electromagnetic Calorimeter (EC)
//
// Author: Lee Allison, 3/09/2015
//-----------------------------------------------------------

package clasgemc.detector;

import clasgemc.g4volume.Trap;
import clasgemc.g4volume.Rotation;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

public class EC extends Calorimeter
{
	public EC()
	{
		lead_thick = 0.237;
		lead_shift = 0.61905;
		dist2tgt   = 717.723;

		for( Sector sector : det_local.getAllSectors() )
		{
			int sector_id = sector.getSectorId();

			for( int superlayer_id=1; superlayer_id<=2; superlayer_id++ )
			{
				Superlayer superlayer = sector.getSuperlayer(superlayer_id);

				List<Layer> layers = superlayer.getAllLayers();
				Layer layer_clas = det_clas.getSector(sector_id).getSuperlayer(superlayer_id).getLayer(0);

				// get sector information

				Trap ec_size = getSectorSize(layers);
				List<String> ec_string = getSuperlayerString(superlayer_id);
				Point3D ec_center = getSectorCenter(layers);
				Rotation ec_rotation = getLayerRotation(layer_clas);

				// strings for volumes_map
				String ec_nam = String.format("ec_%s_s%d", ec_string[0], sector_id+1);
				String ec_des = String.format("%s EC Sector %d", ec_string[1], sector_id+1);
				String ec_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", ec_center.x(), ec_center.y(), ec_center.z());
				String ec_rot = String.format("ordered: zxy %.1f*deg %.1f*deg %.1f*deg",
												 90-ec_rotation.z(), ec_rotation.y(), ec_rotation.x());
				String ec_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg",
												 ec_size.dz(), ec_size.theta(), ec_size.phi(),
												 ec_size.dy1(), ec_size.dx1(), ec_size.dx2(), ec_size.alpha1(),
												 ec_size.dy2(), ec_size.dx3(), ec_size.dx4(), ec_size.alpha2());
				
				Map<String, String> ec_properties = new HashMap<String, String>();
				ec_properties.put("mother", "root");
				ec_properties.put("description", ec_des);
				ec_properties.put("pos", ec_pos);
				ec_properties.put("rotation", ec_rot);
				ec_properties.put("color", "11ff66");
				ec_properties.put("type", "G4Trap");
				ec_properties.put("dimensions", ec_dim);
				ec_properties.put("material", "G4_AIR");
				ec_properties.put("mfield", "no");
				ec_properties.put("ncopy", "1");
				ec_properties.put("pMany", "1");
				ec_properties.put("exist", "1");
				ec_properties.put("visible", "0");
				ec_properties.put("style", "1");
				ec_properties.put("sensitivity", "no");
				ec_properties.put("hit_type", "no");
				ec_properties.put("identifiers", "no");

				// add names and properties to
				// the sector list and volumes map
				sector_names.add(ec_nam);
				volumes_map.put(ec_nam, ec_properties);

				for( Layer layer : layers )
				{
					int layer_id = layer.getLayerId();
					Trap lead_size = getLeadSize(layer);
					Trap layer_size = getLayerSize(layer);

					if( layer_id%3 == 0 ) // U-view mother layers
					{
						String ulayer_nam = buildLayer("U", ec_string, layer, layer_size, layer_id, sector_id, ec_nam, ec_des);
						ulayer_names.add(ulayer_nam);
						
						List<Component> strips = layer.getAllComponents();
						getComponentStackShift(strips, getUStripShift(strips).toVector3D());
						for( Component strip : strips )
						{
							Trap ustrip_size = getUStripSize(strip, superlayer_id);
							String ustrip_nam = buildUStrip("U", ec_string, strip, ustrip_size, layer_id, superlayer_id, sector_id, ulayer_nam, ec_des);

							ustrip_names.add(ustrip_nam);
						}
					}

					if( layer_id%3==1 ) // V-view mother layers
					{
						String vlayer_nam = buildLayer("V", ec_string, layer, layer_size, layer_id, sector_id, ec_nam, ec_des);
						vlayer_names.add(vlayer_nam);

						List<Component> strips = layer.getAllComponents();
						getComponentStackShift(strips, getVStripShift(strips).toVector3D());
						for( Component strip : strips )
						{
							Trap vstrip_size = getVWStripSize(strip, superlayer_id);
							String vstrip_nam = buildVWStrip("V", ec_string, strip, vstrip_size, layer_id, superlayer_id, sector_id, vlayer_nam, ec_des);

							vstrip_names.add(vstrip_nam);
						}
					}

					if( layer_id%3==2 ) // W-view mother layers
					{
						String wlayer_nam = buildLayer("W", ec_string, layer, layer_size, layer_id, sector_id, ec_nam, ec_des);
						wlayer_names.add(wlayer_nam);

						List<Component> strips = layer.getAllComponents();
						getComponentStackShift(strips, getWStripShift(strips).toVector3D());
						for( Component strip : strips )
						{
							Trap wstrip_size = getVWStripSize(strip, superlayer_id);
							String wstrip_nam = buildVWStrip("W", ec_string, strip, wstrip_size, layer_id, superlayer_id, sector_id, wlayer_nam, ec_des);

							wstrip_names.add(wstrip_nam);
						}
					}

					if( layer_id < layers.size()-1 ) // lead slab layers
					{
						Point3D lead_center = getLeadCenter(layer);

						// strings for volumes_map
						String lead_nam = String.format("%s_EC_Lead_layer_%d_s%d", ec_string[1], layer_id+1, sector_id+1);
						String lead_mom = ec_nam;
						String lead_des = ec_des + String.format(" Lead Layer %d", layer_id+1);
						String lead_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", lead_center.x(), lead_center.y(), lead_center.z());
						String lead_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg",
														 lead_size.dz(), lead_size.theta(), lead_size.phi(),
														 lead_size.dy1(), lead_size.dx1(), lead_size.dx2(), lead_size.alpha1(),
														 lead_size.dy2(), lead_size.dx3(), lead_size.dx4(), lead_size.alpha2());

						Map<String, String> lead_properties = new HashMap<String, String>();
						lead_properties.put("mother", lead_mom);
						lead_properties.put("description", lead_des);
						lead_properties.put("pos", lead_pos);
						lead_properties.put("rotation", "ordered: zxy 0.0*deg 0.0*deg 0.0*deg");
						lead_properties.put("color", "a1a1a1");
						lead_properties.put("type", "G4Trap");
						lead_properties.put("dimensions", lead_dim);
						lead_properties.put("material", "G4_Pb");
						lead_properties.put("mfield", "no");
						lead_properties.put("ncopy", "1");
						lead_properties.put("pMany", "1");
						lead_properties.put("exist", "1");
						lead_properties.put("visible", "1");
						lead_properties.put("style", "1");
						lead_properties.put("sensitivity", "no");
						lead_properties.put("hit_type", "no");
						lead_properties.put("identifiers", "no");

						lead_names.add(lead_nam);
						volumes_map.put(lead_nam, lead_properties);
					}
				}
			}
		}
	}

	public String buildLayer(String view, List<String> ec_string, Layer layer, Trap layer_size, int layer_id, int sector_id, String ec_nam, String ec_des )
	{
		Point3D layer_center = getLayerCenter(layer).toPoint3D();

		// strings for volumes_map
		String layer_nam = String.format("%s_view_%s_ec_layer_%d_s%d", view, ec_string[0], layer_id+1, sector_id+1);
		String layer_mom = ec_nam;
		String layer_des = ec_des + String.format(" %s-view %s Layer %d", view, ec_string[1], layer_id+1);
		String layer_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", layer_center.y(), layer_center.x(), layer_center.z());
		String layer_col = getLayerColor(layer_id);
		String layer_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg",
										 layer_size.dz(), layer_size.theta(), layer_size.phi(),
										 layer_size.dy1(), layer_size.dx1(), layer_size.dx2(), layer_size.alpha1(),
										 layer_size.dy2(), layer_size.dx3(), layer_size.dx4(), layer_size.alpha2());

		Map<String, String> layer_properties = new HashMap<String, String>();
		layer_properties.put("mother", layer_mom);
		layer_properties.put("description", layer_des);
		layer_properties.put("pos", layer_pos);
		layer_properties.put("rotation", "ordered: zxy 0.0*deg 0.0*deg 0.0*deg");
		layer_properties.put("color", layer_col);
		layer_properties.put("type", "G4Trap");
		layer_properties.put("dimensions", layer_dim);
		layer_properties.put("material", "G4_AIR");
		layer_properties.put("mfield", "no");
		layer_properties.put("ncopy", "1");
		layer_properties.put("pMany", "1");
		layer_properties.put("exist", "1");
		layer_properties.put("visible", "0");
		layer_properties.put("style", "0");
		layer_properties.put("sensitivity", "no");
		layer_properties.put("hit_type", "no");
		layer_properties.put("identifiers", "no");

		// add properties to volumes_map
		volumes_map.put(layer_nam, layer_properties);

		return layer_nam;
	}

	public String buildUStrip(String view, List<String> ec_string, Component strip, Trap strip_size, int layer_id, int superlayer_id, int sector_id, String layer_nam, String ec_des)
	{
		int strip_id = strip.getComponentId();
		Point3D strip_center = strip.getMidpoint();

		// strings for volumes_map
		String strip_nam = String.format("%s_view_strip_%d_%s_ec_layer_%d_s%d", view, strip_id+1, ec_string[0], layer_id+1, sector_id+1);
		String strip_mom = layer_nam;
		String strip_des = ec_des + String.format(" %s-view Strip %d, %s Layer %d", view, strip_id+1, ec_string[1], layer_id+1);
		String strip_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", strip_center.y(), strip_center.x(), 0.0);
		String strip_col = getStripColor(layer_id)[strip_id%2];
		String strip_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg",
										 strip_size.dz(), strip_size.theta(), strip_size.phi(),
										 strip_size.dy1(), strip_size.dx1(), strip_size.dx2(), strip_size.alpha1(),
										 strip_size.dy2(), strip_size.dx3(), strip_size.dx4(), strip_size.alpha2());
		String strip_man = String.format("sector manual %d stack manual %d view manual %d strip manual %d",
										  sector_id+1, superlayer_id+1, layer_id%3+1, strip_id+1);

		Map<String, String> strip_properties = new HashMap<String, String>();
		strip_properties.put("mother", strip_mom);
		strip_properties.put("description", strip_des);
		strip_properties.put("pos", strip_pos);
		strip_properties.put("rotation", "ordered: zxy 0.0*deg 0.0*deg 0.0*deg");
		strip_properties.put("color", strip_col);
		strip_properties.put("type", "G4Trap");
		strip_properties.put("dimensions", strip_dim);
		strip_properties.put("material", "scintillator");
		strip_properties.put("mfield", "no");
		strip_properties.put("ncopy", "1");
		strip_properties.put("pMany", "1");
		strip_properties.put("exist", "1");
		strip_properties.put("visible", "1");
		strip_properties.put("style", "1");
		strip_properties.put("sensitivity", "ec");
		strip_properties.put("hit_type", "ec");
		strip_properties.put("identifiers", strip_man);

		// add properties to volumes_map
		volumes_map.put(strip_nam, strip_properties);

		return strip_nam;
	}

	public String buildVWStrip(String view, List<String> ec_string, Component strip, Trap strip_size, int layer_id, int superlayer_id, int sector_id, String layer_nam, String ec_des)
	{
		int strip_id = strip.getComponentId();
		Point3D strip_center = strip.getMidpoint();

		// strings for volumes_map
		String strip_nam = String.format("%s_view_strip_%d_%s_ec_layer_%d_s%d", view, strip_id+1, ec_string[0], layer_id+1, sector_id+11);
		String strip_mom = layer_nam;
		String strip_des = ec_des + String.format(" %s-view Strip %d, %s Layer %d", view, strip_id+1, ec_string[1], layer_id+1);
		String strip_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", -strip_center.y(), strip_center.x(), 0.0);
		String strip_rot = String.format("ordered: zxy %.4f*deg 0.0*deg 0.0*deg", 90-Math.toDegrees(strip.getDirection().phi()));
		String strip_col = getStripColor(layer_id)[strip_id%2];
		String strip_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg",
										 strip_size.dz(), strip_size.theta(), strip_size.phi(),
										 strip_size.dy1(), strip_size.dx1(), strip_size.dx2(), strip_size.alpha1(),
										 strip_size.dy2(), strip_size.dx3(), strip_size.dx4(), strip_size.alpha2());
		String strip_man = String.format("sector manaul %d module manual %d view manual %d strip manual %d",
										  sector_id+1, superlayer_id+1, layer_id%3+1, strip_id+1);

		Map<String, String> strip_properties = new HashMap<String, String>();
		strip_properties.put("mother", strip_mom);
		strip_properties.put("description", strip_des);
		strip_properties.put("pos", strip_pos);
		strip_properties.put("rotation", strip_rot);
		strip_properties.put("color", strip_col);
		strip_properties.put("type", "G4Trap");
		strip_properties.put("dimensions", strip_dim);
		strip_properties.put("material", "scintillator");
		strip_properties.put("mfield", "no");
		strip_properties.put("ncopy", "1");
		strip_properties.put("pMany", "1");
		strip_properties.put("exist", "1");
		strip_properties.put("visible", "1");
		strip_properties.put("style", "1");
		strip_properties.put("sensitivity", "ec");
		strip_properties.put("hit_type", "ec");
		strip_properties.put("identifiers", strip_man);

		// add properties to volumes_map
		volumes_map.put(strip_nam, strip_properties);

		return strip_nam;
	}

	//==== Mist Functions ====//

	public void printEC()
	{
		printCalorimeter("ec/ec__geometry_lee.txt");
	}

	public List<String> getSuperlayerString( int superlayer_id )
	{
		List<String> ec_string = new ArrayList<String>();

		switch(superlayer_id) {
			case 1:
				ec_string.add("inner");
				ec_string.add("Inner");
				break;

			case 2:
				ec_string.add("outer");
				ec_string.add("Outer");
				break;
		}

		return ec_string;
	}

	public String getLayerColor( int layer_id )
	{
		switch(layer_id%3)
		{
			case 0:
				return "ff6633";
				break;

			case 1:
				return "6633ff";
				break;

			case 2:
				return "33ff66";
				break;
		}
	}

	public List<String> getStripColor( int layer_id )
	{
		List<String> colors = new ArrayList<String>();

		switch(layer_id%3)
		{
			case 0: 
				colors.add("11ffff");
				colors.add("ff1111");
				break;

			case 1:
				colors.add("ff11ff");
				colors.add("11ff11");
				break;

			case 2:
				colors.add("ffff11");
				colors.add("1111ff");
				break;
		}

		return colors;
	}

	//==== W Strip Functions ====//

	public Point3D getWStripShift( List<Component> strips )
	{
		// first and last strip of layer
		Component bot = strips[0];
		Component top = strips[strips.size()-1];

		// bounding points on front face
		Point3D p0_1 = bot.getVolumePoint(0);
		Point3D p4_1 = bot.getVolumePoint(4);
		Point3D p1_N = top.getVolumePoint(1);
		Point3D p5_N = top.getVolumePoint(5);
		
		// "missing" tip and center of stack
		Point3D tip = getTriangleTip(p0_1,p1_N,p4_1,p5_N);
		Point3D mid = Point3D.average(p5_N,Point3D.average(tip,p1_N));

		return mid;
	}
}