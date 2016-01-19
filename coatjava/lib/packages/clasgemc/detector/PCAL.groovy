//-----------------------------------------------------------
// Definitions for Pre-shower Calorimeter (PCAL)
//
// Author: Lee Allison, 3/09/2015
//-----------------------------------------------------------

package groovy_clas.detector;

import groovy_clas.g4volume.Trap;
import groovy_clas.g4volume.Rotation;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

/**
 * CLAS12 Pre-shower Calorimeter Detector (PCAL)
 *	
 * All lengths are measured in cm
 * All angles in final file will be in degrees
 *	
 * All scintillators and mother volumes are constructed as {@code Trap}s
 */
 public class PCAL extends Calorimeter
 {
 	public PCAL()
	{
		println "New PCAL Created\nBuilding...";

		lead_thick = 0.2;
		lead_shift = 0.6;
		dist2tgt   = 697.78;

		for( Sector sector : det_local.getAllSectors() )
		{
			int sector_id = sector.getSectorId();
			
			int superlayer_id = 0; // PCAL
			Superlayer superlayer = sector.getSuperlayer(superlayer_id);

			List<Layer> layers = superlayer.getAllLayers();
			Layer layer_clas   = det_clas.getSector(sector_id).getSuperlayer(superlayer_id).getLayer(0);

			// pcal properties
			Trap pcal_size = getSectorSize(layers);
			Point3D pcal_center = getSectorCenter(layers);
			Rotation pcal_rotation = getLayerRotation(layer_clas);

			// strings for volumes_map
			String pcal_nam = String.format("pcal_s%d", sector_id+1);
			String pcal_des = String.format("PCAL Sector %d", sector_id+1);
			String pcal_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", pcal_center.x(), pcal_center.y(), pcal_center.z());
			String pcal_rot = String.format("ordered: zxy %.1f*deg %.1f*deg %.1f*deg",
											 90-pcal_rotation.z(), pcal_rotation.y(), pcal_rotation.x());
			String pcal_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg %.4f*cm %.4f*cm %.4f*cm %.2f*deg",
											 pcal_size.dz(), pcal_size.theta(), pcal_size.phi(),
											 pcal_size.dy1(), pcal_size.dx1(), pcal_size.dx2(), pcal_size.alpha1(),
											 pcal_size.dy2(), pcal_size.dx3(), pcal_size.dx4(), pcal_size.alpha2());
			
			Map<String, String> pcal_properties = new HashMap<String, String>();
			pcal_properties.put("mother", "root");
			pcal_properties.put("description", pcal_des);
			pcal_properties.put("pos", pcal_pos);
			pcal_properties.put("rotation", pcal_rot);
			pcal_properties.put("color", "6611ff");
			pcal_properties.put("type", "G4Trap");
			pcal_properties.put("dimensions", pcal_dim);
			pcal_properties.put("material", "G4_AIR");
			pcal_properties.put("mfield", "no");
			pcal_properties.put("ncopy", "1");
			pcal_properties.put("pMany", "1");
			pcal_properties.put("exist", "1");
			pcal_properties.put("visible", "0");
			pcal_properties.put("style", "1");
			pcal_properties.put("sensitivity", "no");
			pcal_properties.put("hit_type", "no");
			pcal_properties.put("identifiers", "no");

			println String.format("PCAL sector %d: %s", sector_id, pcal_rotation.toString());

			// add names and properties to
			// the sector list and volumes map
			sector_names.add(pcal_nam);
			volumes_map.put(pcal_nam, pcal_properties);

			// all strip mother volumes and lead layers
			// have the same size, so define them outside
			// layer loop to save a little bit of computation
			Trap lead_size = getLeadSize(layers[0]);
			Trap layer_size = getLayerSize(layers[0]);
			for( Layer layer : layers )
			{
				int layer_id = layer.getLayerId();
				
				if( layer_id%3 == 0 ) // U-view mother layers
				{
					String ulayer_nam = buildLayer("U", layer, layer_size, layer_id, sector_id, pcal_nam, pcal_des);
					ulayer_names.add(ulayer_nam);
					
					List<Component> strips = layer.getAllComponents();
					getComponentStackShift(strips, getUStripShift(strips).toVector3D());
					for( Component strip : strips )
					{
						Trap ustrip_size = getUStripSize(strip, superlayer_id);
						String ustrip_nam = buildUStrip("U", strip, ustrip_size, layer_id, superlayer_id, sector_id, ulayer_nam, pcal_des);

						ustrip_names.add(ustrip_nam);
					}
				}

				if( layer_id%3==1 ) // V-view mother layers
				{
					String vlayer_nam = buildLayer("V", layer, layer_size, layer_id, sector_id, pcal_nam, pcal_des);
					vlayer_names.add(vlayer_nam);

					List<Component> strips = layer.getAllComponents();
					getComponentStackShift(strips, getVStripShift(strips).toVector3D());
					for( Component strip : strips )
					{
						Trap vstrip_size = getVWStripSize(strip, superlayer_id);
						String vstrip_nam = buildVWStrip("V", strip, vstrip_size, layer_id, superlayer_id, sector_id, vlayer_nam, pcal_des);

						vstrip_names.add(vstrip_nam);
					}
				}

				if( layer_id%3==2 ) // W-view mother layers
				{
					String wlayer_nam = buildLayer("W", layer, layer_size, layer_id, sector_id, pcal_nam, pcal_des);
					wlayer_names.add(wlayer_nam);

					List<Component> strips = layer.getAllComponents();
					getComponentStackShift(strips, getWStripShift(strips).toVector3D());
					for( Component strip : strips )
					{
						Trap wstrip_size = getVWStripSize(strip, superlayer_id);
						String wstrip_nam = buildVWStrip("W", strip, wstrip_size, layer_id, superlayer_id, sector_id, wlayer_nam, pcal_des);

						wstrip_names.add(wstrip_nam);
					}
				}

				if( layer_id < layers.size()-1 ) // lead slab layers
				{
					Point3D lead_center = getLeadCenter(layer);

					// strings for volumes_map
					String lead_nam = String.format("PCAL_Lead_layer_%d_s%d", layer_id+1, sector_id+1);
					String lead_mom = pcal_nam;
					String lead_des = pcal_des + String.format(" Lead Layer %d", layer_id+1);
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

	//==== Layer and Strip bulding Functions ====//
	
	public String buildLayer(String view, Layer layer, Trap layer_size, int layer_id, int sector_id, String pcal_nam, String pcal_des )
	{
		Point3D layer_center = getLayerCenter(layer).toPoint3D();

		// strings for volumes_map
		String layer_nam = String.format("%s_view_layer_%d_s%d", view, layer_id+1, sector_id+1);
		String layer_mom = pcal_nam;
		String layer_des = pcal_des + String.format(" %s-view Layer %d", view, layer_id+1);
		String layer_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", layer_center.x(), layer_center.y(), layer_center.z());
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

	public String buildUStrip(String view, Component strip, Trap strip_size, int layer_id, int superlayer_id, int sector_id, String layer_nam, String pcal_des)
	{
		int strip_id = strip.getComponentId();
		Point3D strip_center = strip.getMidpoint();

		// strings for volumes_map
		String strip_nam = String.format("%s_view_strip_%d_layer_%d_pcal_s%d", view, strip_id+1, layer_id+1, sector_id+1);
		String strip_mom = layer_nam;
		String strip_des = pcal_des + String.format(" %s-view Strip %d, Layer %d", view, strip_id+1, layer_id+1);
		String strip_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", strip_center.y(), strip_center.x(), 0.0);
		String strip_col = getStripColor(layer_id)[strip_id%2];
		String strip_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg",
										 strip_size.dz(), strip_size.theta(), strip_size.phi(),
										 strip_size.dy1(), strip_size.dx1(), strip_size.dx2(), strip_size.alpha1(),
										 strip_size.dy2(), strip_size.dx3(), strip_size.dx4(), strip_size.alpha2());
		String strip_man = String.format("sector manaul %d module manual %d view manual %d strip manual %d",
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
		strip_properties.put("sensitivity", "pcal");
		strip_properties.put("hit_type", "pcal");
		strip_properties.put("identifiers", strip_man);

		// add properties to volumes_map
		volumes_map.put(strip_nam, strip_properties);

		return strip_nam;
	}

	public String buildVWStrip(String view, Component strip, Trap strip_size, int layer_id, int superlayer_id, int sector_id, String layer_nam, String pcal_des)
	{
		int strip_id = strip.getComponentId();
		Point3D strip_center = strip.getMidpoint();

		// strings for volumes_map
		String strip_nam = String.format("%s_view_strip_%d_layer_%d_pcal_s%d", view, strip_id+1, layer_id+1, sector_id+1);
		String strip_mom = layer_nam;
		String strip_des = pcal_des + String.format(" %s-view Strip %d, Layer %d", view, strip_id+1, layer_id+1);
		String strip_pos = String.format("%.4f*cm %.4f*cm %.4f*cm", -strip_center.y(), strip_center.x(), 0.0);
		String strip_rot = String.format("ordered: zxy %.4f*deg 0.0*deg 0.0*deg", 90-Math.toDegrees(strip.getDirection().phi()));
		String strip_col = getStripColor(layer_id)[strip_id%2];
		String strip_dim = String.format("%.4f*cm %.2f*deg %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg %.4f*cm %.5f*cm %.4f*cm %.2f*deg",
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
		strip_properties.put("sensitivity", "pcal");
		strip_properties.put("hit_type", "pcal");
		strip_properties.put("identifiers", strip_man);

		// add properties to volumes_map
		volumes_map.put(strip_nam, strip_properties);

		return strip_nam;
	}

	//==== Misc Functions ====//

	public void printPCAL()
	{
		printCalorimeter("pcal/pcal__geometry_lee.txt");
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
				colors.add("ff66cc");
				colors.add("66ff99");
				break;

			case 1:
				colors.add("66ffcc");
				colors.add("ff9966");
				break;

			case 2:
				colors.add("ccff66");
				colors.add("9966ff");
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
		Point3D p0_N = top.getVolumePoint(0);
		Point3D p4_N = top.getVolumePoint(4);
		Point3D p1_1 = bot.getVolumePoint(1);
		Point3D p5_1 = bot.getVolumePoint(5);

		// "missing" tip and center of stack
		Point3D tip = getTriangleTip(p0_N,p1_1,p4_N,p5_1);
		Point3D mid = Point3D.average(p0_N,Point3D.average(tip,p4_N));

		return mid;
	}
 }