//-----------------------------------------------------------
// Definitions for Forward Time of Flight (FTOF)
//
// Author: Lee Allison, 3/06/2015
//-----------------------------------------------------------

package groovy_clas.detector;

import groovy_clas.g4volume.Box;
import groovy_clas.g4volume.Trd;
import groovy_clas.g4volume.Rotation;


import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

import org.jlab.geom.detector.ftof.*;
import org.jlab.clasrec.utils.DataBaseLoader;


/**
 * CLAS12 Forward Time of Flight (FTOF)
 *
 * All lengths are measured in cm
 * All angles in final file will be in degrees
 *
 * All scintillator volumes are {@code Box}es
 * All mother volumes are basic {@code Trd}s
 */
 public class FTOF extends Detector
 {
 	private ConstantProvider   data = DataBaseLoader.getConstantsFTOF();
	private FTOFFactory     factory = new FTOFFactory();
	private FTOFDetector   det_clas = factory.createDetectorCLAS(data);
	private FTOFDetector  det_local = factory.createDetectorLocal(data);
	
	private List<String> layer_names;
	private List<String> comp_names;

	public FTOF()
	{
		layer_names = new ArrayList<String>();
		comp_names  = new ArrayList<String>();

		for( Sector sector : det_local.getAllSectors() )
		{
			int sector_id = sector.getSectorId();
			for( Superlayer superlayer : sector.getAllSuperlayers() )
			{
				int superlayer_id = superlayer.getSuperlayerId();
				for( Layer layer : superlayer.getAllLayers() )
				{
					int layer_id = layer.getLayerId();

					// also need the layer in CLAS coordinates
					Layer layer_clas = det_clas.getSector(sector_id).getSuperlayer(superlayer_id).getLayer(layer_id);
				
					// layer properties
					Trd      layer_size     = getLayerSize(layer);
					Point3D  layer_center   = getLayerCenter(layer_clas);
					String   layer_string   = getLayerString(superlayer_id);
					Rotation layer_rotation = getLayerRotation(layer_clas);

					// strings for volumes_map
					String ftof_nam = String.format("ftof_p%s_s%d", layer_string, sector_id+1);
					String ftof_des = String.format("Forward TOF - Panel %s - Sector %d", layer_string, sector_id+1);
					String ftof_pos = String.format("%.3f*cm %.3f*cm %.3f*cm", layer_center.x(), layer_center.y(), layer_center.z());
					String ftof_rot = String.format("ordered: zxy %.3f*deg %.3f*deg %.3f*deg",
													 -90-layer_rotation.z(), -90-layer_rotation.y(), layer_rotation.x());
					String ftof_dim = String.format("%.3f*cm %.3f*cm %.3f*cm %.3f*cm %.3f*cm",
													 layer_size.dx1(), layer_size.dx2(), layer_size.dy1(), layer_size.dy2(), layer_size.dz());

					Map<String, String> ftof_properties = new HashMap<String, String>();
					ftof_properties.put("mother", "root");
					ftof_properties.put("description", ftof_des);
					ftof_properties.put("pos", ftof_pos);
					ftof_properties.put("rotation", ftof_rot);
					ftof_properties.put("color", "ff0000");
					ftof_properties.put("type", "Trd");
					ftof_properties.put("dimensions", ftof_dim);
					ftof_properties.put("material", "G4_AIR");
					ftof_properties.put("mfield", "no");
					ftof_properties.put("ncopy", "1");
					ftof_properties.put("pMany", "1");
					ftof_properties.put("exist", "1");
					ftof_properties.put("visible", "1");
					ftof_properties.put("style", "0");
					ftof_properties.put("sensitivity", "no");
					ftof_properties.put("hit_type", "no");
					ftof_properties.put("identifiers", "no");

					// add names and properties to
					// the names list and volumes map
					layer_names.add(ftof_nam);
					volumes_map.put(ftof_nam, ftof_properties);
				
					// shift components
					List<Component> components = layer.getAllComponents();
					Point3D component_shift = getLayerCenter(layer);
					getComponentStackShift(components, component_shift.toVector3D());

					for( Component component in components )
					{
						int component_id = component.getComponentId();

						Point3D comp_center = component.getMidpoint();
						Box     comp_size   = getPaddleSize(component);

						// strings for volumes_map
						String comp_nam = String.format("panel%s_paddle_%d_s%d",
														 layer_string, component_id+1, sector_id+1);
						String comp_mom = ftof_nam;
						String comp_des = String.format("Paddle %d - Panel %s - Sector %d", 
														 component_id+1, layer_string, sector_id+1);
						String comp_pos = String.format("%.3f*cm %.3f*cm %.3f*cm", 
														 comp_center.z(), comp_center.y(), comp_center.x());
						String comp_col = getLayerColor(superlayer_id);
						String comp_dim = String.format("%.3f*cm %.3f*cm %.3f*cm",
														 comp_size.dx(), comp_size.dy(), comp_size.dz());
						String comp_hit = String.format("ftof_p%s", layer_string);
						String comp_man = String.format("sector manual %d paddle manual %d", 
														 sector_id+1, component_id+1);

						Map<String, String> comp_properties = new HashMap<String, String>();
						comp_properties.put("mother", comp_mom);
						comp_properties.put("description", comp_des);
						comp_properties.put("pos", comp_pos);
						comp_properties.put("rotation", "ordered: zxy 0.0*deg 0.0*deg 0.0*deg");
						comp_properties.put("color", comp_col);
						comp_properties.put("type", "Box");
						comp_properties.put("dimensions", comp_dim);
						comp_properties.put("material", "scintillator");
						comp_properties.put("mfield", "no");
						comp_properties.put("ncopy", "1");
						comp_properties.put("pMany", "1");
						comp_properties.put("exist", "1");
						comp_properties.put("visible", "1");
						comp_properties.put("style", "1");
						comp_properties.put("sensitivity", comp_hit);
						comp_properties.put("hit_type", comp_hit);
						comp_properties.put("identifiers", comp_man);

						comp_names.add(comp_nam);
						volumes_map.put(comp_nam, comp_properties);				
					}

				}
			}
		}
	}
	
	public void printFTOF()
	{
		
		PrintWriter calo_out = new PrintWriter("ftof/ftof__geometry_lee.txt", "UTF-8");
		List<List<String>> names = Arrays.asList(layer_names, comp_names)
		
		for( List<String> name_list : names )
		{
			for( String name : name_list )
			{
				calo_out.write(String.format("%26s |", name));
				calo_out.write(String.format("%15s |", volumes_map[name]["mother"]));
				calo_out.write(String.format("%35s |", volumes_map[name]["description"]));
				calo_out.write(String.format("%35s |", volumes_map[name]["pos"]));
				calo_out.write(String.format("%55s |", volumes_map[name]["rotation"]));
				calo_out.write(String.format("%10s |", volumes_map[name]["color"]));
				calo_out.write(String.format("%10s |", volumes_map[name]["type"]));
				calo_out.write(String.format("%55s |", volumes_map[name]["dimensions"]));
				calo_out.write(String.format("%15s |", volumes_map[name]["material"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["mfield"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["ncopy"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["pMany"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["exist"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["visible"]));
				calo_out.write(String.format("%5s  |", volumes_map[name]["style"]));
				calo_out.write(String.format("%10s |", volumes_map[name]["sensitivity"]));
				calo_out.write(String.format("%10s |", volumes_map[name]["hit_type"]));
				calo_out.write(String.format("%35s\n", volumes_map[name]["identifiers"]));
			}
		}
		
		calo_out.close();
	}
	
	public String getLayerString( int superlayer_id )
	{
		switch (superlayer_id)
		{
			case 0:
				return "1a";
				break;
				
			case 1:
				return "1b";
				break;
				
			case 2:
				return "2";
				break;

			default:
				throw new IllegalArgumentException("Invalid superlayer_id for getLayerString: " + superlayer_id);
		}
	}
	
	public String getLayerColor( int superlayer_id )
	{
		switch (superlayer_id)
		{
			case 0:
				return "ff11e6";
				break;
				
			case 1:
				return "ff11aa";
				break;
				
			case 2:
				return "ff116f";
				break;

			default:
				throw new IllegalArgumentException("Invalid superlayer_id for getLayerColor: " + superlayer_id);
		}
	}
	
	public Point3D getLayerCenter( Layer layer )
	{
		List<Point3D> centers = new ArrayList<Point3D>();
		for( Component component : layer.getAllComponents() )
			centers.add(component.getMidpoint());
			
		return Point3D.average(centers);
	}
	
	public Trd getLayerSize( Layer layer )
	{
		Component bot = layer.getComponent(0);
		Shape3D boundary  = layer.getBoundary();
		
		/*
		Point3D p00 = boundary.face(0).point(0);
		Point3D p10 = boundary.face(1).point(0);
		Point3D p11 = boundary.face(1).point(1);
		Point3D p12 = boundary.face(1).point(2);
		
		Point3D p10_11 = Point3D.average(p10,p11);
		Point3D p00_12 = Point3D.average(p00,p12);
		
		double dx1, dx2, dy1, dy2, dz;
		
		dx1 = 0.5*p00.distance(p12);
		dx2 = 0.5*p10.distance(p11);
		dy1 = 0.5*p10_11.distance(p00_12);
		dy2 = dy1;
		dz = getPaddleSize(bot).dz();
		*/
		
		Point3D p0 = boundary.face(0).point(0);
		Point3D p1 = boundary.face(0).point(1);
		Point3D p2 = boundary.face(0).point(2);
		
		double mother_gap = 0.4; // cm
		double dx1, dx2, dy1, dy2, dz;
		
		dx1 = 0.5*Math.abs(p1.y() - p0.y()) + mother_gap;
		dx2 = 0.5*Math.abs(2*p2.y()) + mother_gap;
		dy1 = getPaddleSize(bot).dy() + mother_gap;
		dy2 = dy1;
		dz = 0.5*Math.abs(p2.x() - p0.x()) + mother_gap;
		
		return new Trd(dx1, dx2, dy1, dy2, dz);
	}
	
	public Box getPaddleSize( Component paddle )
	{
		Point3D p0 = paddle.getVolumePoint(0);
		Point3D p1 = paddle.getVolumePoint(1);
		Point3D p2 = paddle.getVolumePoint(2);
		Point3D p4 = paddle.getVolumePoint(4);
		
		double dx, dy, dz;
		
		dx = 0.5*p0.distance(p4);
		dy = 0.5*p1.distance(p2);
		dz = 0.5*p0.distance(p1);
		
		return new Box(dx, dy, dz);
	}
 }