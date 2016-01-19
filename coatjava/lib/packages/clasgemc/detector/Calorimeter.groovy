//-----------------------------------------------------------
// Definitions for generalized Detector
//
// Author: Lee Allison, 2/27/2015
//-----------------------------------------------------------

package clasgemc.detector;
import  clasgemc.g4volume.*;

import java.lang.Math;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

import org.jlab.geom.detector.ec.*;
import org.jlab.clasrec.utils.DataBaseLoader;

/**
 * Generic class for Electromagnetic
 * and Pre-shower Calorimeters
 */
public class Calorimeter extends Detector
{
	
	// variables for contructing the detector from Factory
	// in both local and CLAS coordinates
	protected ConstantProvider data = DataBaseLoader.getConstantsEC();
	protected ECFactory     factory = new ECFactory();
	protected ECDetector   det_clas = factory.createDetectorCLAS(data);
	protected ECDetector  det_local = factory.createDetectorLocal(data);
	
	// maps holding volume's property name and value
	// volume -> {property: value}
	protected List<String> sector_names;
	protected List<String> ulayer_names;
	protected List<String> ustrip_names;
	protected List<String> vlayer_names;
	protected List<String> vstrip_names;
	protected List<String> wlayer_names;
	protected List<String> wstrip_names;
	protected List<String> lead_names;
	
	// used for component dimension and position calculations
	protected double lead_thick;
	protected double lead_shift;
	protected double dist2tgt;
	protected Vector3D shift;
	
	public Calorimeter() 
	{ 
		// initialize new volume name lists when new Calorimeter is created
		sector_names = new ArrayList<String>();
		ulayer_names = new ArrayList<String>();
		ustrip_names = new ArrayList<String>();
		vlayer_names = new ArrayList<String>();
		vstrip_names = new ArrayList<String>();
		wlayer_names = new ArrayList<String>();
		wstrip_names = new ArrayList<String>();
		lead_names   = new ArrayList<String>();

		shift = new Vector3D();
	}
	
	
	//===== Misc. Functions =====//
	
	public void printCalorimeter( String filename )
	{
		
		PrintWriter calo_out = new PrintWriter(filename, "UTF-8");
		List<List<String>> maps = Arrays.asList(sector_names,
											    ulayer_names, ustrip_names,
											    vlayer_names, vstrip_names,
											    wlayer_names, wstrip_names,
											    lead_names)
		
		for( List<String> name_list : maps )
		{
			for( String name : name_list )
			{
				calo_out.write(String.format("%40s  |", name));
				calo_out.write(String.format("%30s  |", volumes_map[name]["mother"]));
				calo_out.write(String.format("%50s  |", volumes_map[name]["description"]));
				calo_out.write(String.format("%40s  |", volumes_map[name]["pos"]));
				calo_out.write(String.format("%45s  |", volumes_map[name]["rotation"]));
				calo_out.write(String.format("%10s  |", volumes_map[name]["color"]));
				calo_out.write(String.format("%10s  |", volumes_map[name]["type"]));
				calo_out.write(String.format("%120s |", volumes_map[name]["dimensions"]));
				calo_out.write(String.format("%15s  |", volumes_map[name]["material"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["mfield"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["ncopy"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["pMany"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["exist"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["visible"]));
				calo_out.write(String.format("%5s   |", volumes_map[name]["style"]));
				calo_out.write(String.format("%10s  |", volumes_map[name]["sensitivity"]));
				calo_out.write(String.format("%10s  |", volumes_map[name]["hit_type"]));
				calo_out.write(String.format("%60s \n", volumes_map[name]["identifiers"]));
			}
		}
		
		calo_out.close();
	}
	
	/**
	 * Find the "missing" point at the top of U layers and base of
	 * VW layers for more accurate calculation of the midpoint of the scintillators
	 */
	public Point3D getTriangleTip( Point3D p0, Point3D p1, Point3D p4, Point3D p5)
	{
		// Line 1, side of triangle
		double x1 = p0.x();
		double y1 = p0.y();
		
		double x2 = p1.x();
		double y2 = p1.y();
		
		// Line 2, base of triangle
		double x3 = p4.x();
		double y3 = p4.y();
	
		double x4 = p5.x();
		double y4 = p5.y();
		
		double D = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		double x = ((x1*y2-y1*x2)*(x3-x4) - (x1-x2)*(x3*y4-y3*x4))/D;
		double y = ((x1*y2-y1*x2)*(y3-y4) - (y1-y2)*(x3*y4-y3*x4))/D;
		
		return new Point3D(x, y, 0);
	}
	
	/**
	 * Uses angle between vectors along base and
	 * through center of scintillator, defined
	 * by 4 points, to determine angle alpha for G4Trap
	*/
	public double getTrapAlpha( Point3D p0, Point3D p1, Point3D p4, Point3D p5)
	{
		Point3D p04 = p0.midpoint(p4)
		Point3D p15 = p1.midpoint(p5)
	
		// vectors from midpoint of dx1 to 
		// midpoint of dx2 and along base of dx2
		Vector3D vecperp = p1.vectorTo(p5);
		Vector3D vecmid  = p15.vectorTo(p04);
		
		// find angle (in degrees) made between normal to dx2 and
		// line connecting midpoints of dx1 and dx2
		double dotprod = vecmid.dot(vecperp)/(vecmid.mag()*vecperp.mag());
		double angle   = Math.acos(dotprod);
		double alpha   = 90 - Math.toDegrees(angle);
	
		return alpha;
	}
	
	//===== Generic Sector Functions =====//
	
	public Point3D getSectorCenter( List<Layer> layers )
	{
		Layer layer1 = layers.get(0); // first U layer
		Layer layerN = layers.get(layers.size()-1); // last W layer
		
		// bounding face of each layer
		Face3D face1 = layer1.getBoundary().face(0);
		Face3D faceN = layerN.getBoundary().face(0);

		// points of each face
		Point3D p0_1 = face1.point(0);
		Point3D p0_N = faceN.point(0);
		Point3D p1_1 = face1.point(1);
		Point3D p1_N = faceN.point(1);
		Point3D p2_1 = face1.point(2);
		Point3D p2_N = faceN.point(2);

		// geant-defined centers of each face
		Point3D center1 = Point3D.average(p0_1,Point3D.average(p1_1,p2_1));
		Point3D centerN = Point3D.average(p0_N,Point3D.average(p1_N,p2_N));
		
		// define the center of the stack (shift) in local coordinates
		// shift z by 0.5 cm to compensate for the Nth layer's boundary being at the front face instead of the back
		Point3D pshift = Point3D.average(center1,centerN);
		pshift.translateXYZ(0,0,0.5);
		shift = pshift.toVector3D();
		
		double z0 = 0;
		if( layer1.getSuperlayerId()==2 )
			z0 += 18.3334 + lead_thick + 0.0011; // shift outer EC to back of inner EC + 1 lead layer
		
		// push and rotate center to clas coordinates
		Point3D center = pshift;
		double L1  = dist2tgt + z0; // cm, dist2tgt
		int secId  = layer1.getSectorId();
		
		center.setZ(center.z()+L1);
		center.rotateY(Math.toRadians(25));
		center.rotateZ(Math.toRadians(60*secId));
		
		return center;
	}
	
	public Trap getSectorSize( List<Layer> layers )
	{
		// grab first and last layer of sector
		Layer layer1 = layers.get(0);
		Layer layerN = layers.get(layers.size()-1);
	
		// make a bounding volume from the faces of first and last layer
		Face3D face1 = layer1.getBoundary().face(0);
		Face3D faceN = layerN.getBoundary().face(0);
	
		// averages for finding dy1/dy2
		Point3D p112 = Point3D.average(face1.point(1),face1.point(2));
		Point3D pN12 = Point3D.average(faceN.point(1),faceN.point(2));
		
		// calculate diminsions
		double mother_gap = 2.7; // cm
		
		double lead_buffer = 0;
		if( layer1.getSuperlayerId()==1 )
			lead_buffer += lead_thick + 0.001; // so inner ec can encapsulate lead between inner/outer
		
		double dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2;
		
		dz     = 0.5*(faceN.point(0).z() - face1.point(0).z() + 1) + lead_buffer; // add 1 cm because faceN is at front of layerN
		theta  = 0;
		phi    = 0;
		dy1    = 0.5*face1.point(0).distance(p112) + mother_gap;
		dx1    = 0.0001;
		dx2    = 0.5*face1.point(1).distance(face1.point(2)) + mother_gap;
		alpha1 = 0;
		dy2    = 0.5*faceN.point(0).distance(pN12) + mother_gap;
		dx3    = dx1;
		dx4    = 0.5*faceN.point(1).distance(faceN.point(2)) + mother_gap;
		alpha2 = 0;

		return new Trap(dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2);
	}
	
	//===== Generic Layer Functions =====//
	
	public Vector3D getLayerCenter( Layer layer )
	{
		// make a bounding volume from the faces of first and last layer
		Face3D face = layer.getBoundary().face(0);

		// points of face
		Point3D p0 = face.point(0);
		Point3D p1 = face.point(1);
		Point3D p2 = face.point(2);
		
		// calculate center of layer
		Point3D pcenter = Point3D.average(p0,Point3D.average(p1,p2));
		pcenter.translateXYZ(0,0,0.5); // shift Z to compensate for boundary being on face instead of in center
		
		Vector3D center = pcenter.toVector3D();
		center.sub(shift);
		
		return center;
	}
	
	public Trap getLayerSize( Layer layer )
	{
		Component comp  = layer.getComponent(0);
		Trap comp_dim = getUStripSize(comp,layer.getSuperlayerId());
	
		// bounding points of face
		Face3D face = layer.getBoundary().face(0);
		Point3D p0  = face.point(0);
		Point3D p1  = face.point(1);
		Point3D p2  = face.point(2);
	
		// midpoint of base for calculating dy1/dy2
		Point3D p12 = Point3D.average(p1,p2);
		
		double mother_gap = 0.5; // cm, to prevent overlaps
		
		double dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2;
		
		dz     = comp_dim.dz();
		theta  = 0;
		phi    = 0;
		dy1    = 0.5*p0.distance(p12) + mother_gap;
		dx1    = 0.0001;
		dx2    = 0.5*p1.distance(p2) + mother_gap;
		alpha1 = 0;
		dy2    = dy1;
		dx3    = dx1;
		dx4    = dx2;
		alpha2 = 0;

		return new Trap(dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2);
	}
	
	//===== Lead Layer Functions =====//
	
	public Point3D getLeadCenter( Layer layer )
	{
		Point3D center = getLayerCenter(layer).toPoint3D();
		center.translateXYZ(0, 0, lead_shift);
		
		return center;
	}
	
	public Trap getLeadSize( Layer layer )
	{
		// bounding face of layer
		Face3D face = layer.getBoundary().face(0);
		Point3D p0  = face.point(0);
		Point3D p1  = face.point(1);
		Point3D p2  = face.point(2);
		
		// midpoint of base for calculating dy1/dy2
		Point3D p12 = Point3D.average(p1,p2);
		
		double dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2;
		
		dz     = 0.5*lead_thick;
		theta  = 0;
		phi    = 0;
		dy1    = 0.5*p0.distance(p12);
		dx1    = 0.0001;
		dx2    = 0.5*p1.distance(p2);
		alpha1 = 0;
		dy2    = dy1;
		dx3    = dx1;
		dx4    = dx2;
		alpha2 = 0;

		return new Trap(dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2);
		
	}
	
	//===== U Strip Functions =====//
	
	public Point3D getUStripShift( List<Component> strips )
	{
		// first and last strip of layer
		Component bot = strips.get(0);
		Component top = strips.get(strips.size()-1);
		
		// bounding points on front face
		Point3D p0_1 = bot.getVolumePoint(0);
		Point3D p4_1 = bot.getVolumePoint(4);
		Point3D p1_N = top.getVolumePoint(1);
		Point3D p5_N = top.getVolumePoint(5);
		
		// "missing" tip and center of stack
		Point3D tip = getTriangleTip(p0_1,p1_N,p4_1,p5_N);
		Point3D mid = Point3D.average(tip,Point3D.average(p1_N,p5_N));
		 
		return mid;
	}
	
	public Trap getUStripSize( Component strip, int superlayer_id )
	{
		// bounding points on front face
		Point3D p0 = strip.getVolumePoint(0);
		Point3D p1 = strip.getVolumePoint(1);
		Point3D p2 = strip.getVolumePoint(2);
		Point3D p4 = strip.getVolumePoint(4);
		Point3D p5 = strip.getVolumePoint(5);
		
		// midpoints for calculating dy1/dy2
		Point3D p04 = p0.midpoint(p4);
		Point3D p15 = p1.midpoint(p5);
		
		double trim = 0;
		if( superlayer_id > 0 )
			trim += 1e-4; // trim ec to prevent overlap, pcal is okay
	
		
		double dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2;
		
		dz     = 0.5*p1.distance(p2);
		theta  = 0;
		phi    = 0;
		dy1    = 0.5*p04.distance(p15) - trim;
		dx1    = 0.5*p0.distance(p4);
		dx2    = 0.5*p1.distance(p5);
		alpha1 = 0;
		dy2    = dy1;
		dx3    = dx1;
		dx4    = dx2;
		alpha2 = 0;
	
		return new Trap(dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2);
	}
	
	//===== VW Strip Functions =====//
	
	public Point3D getVStripShift( List<Component> strips )
	{
		// first and last strip of layer
		Component bot = strips.get(0);
		Component top = strips.get(strips.size()-1);
		
		// bounding points on front face
		Point3D p0_1 = bot.getVolumePoint(0);
		Point3D p4_1 = bot.getVolumePoint(4);
		Point3D p1_N = top.getVolumePoint(1);
		Point3D p5_N = top.getVolumePoint(5);
		
		// "missing" tip and center of stack
		Point3D tip = getTriangleTip(p0_1,p1_N,p4_1,p5_N);
		Point3D mid = Point3D.average(p1_N,Point3D.average(tip,p5_N));

		return mid;
	}
	
	/*public Point3D getWStripShift( List<Component> strips )
	{
		
			Because of slight variation with w-view bounding volume points, this must be explicitly
			defined seperatly for both PCAL and EC
		
	}
	*/
	
	public Trap getVWStripSize( Component strip, int superlayer_id )
	{
		// bounding points on front face
		Point3D p0 = strip.getVolumePoint(0);
		Point3D p1 = strip.getVolumePoint(1);
		Point3D p2 = strip.getVolumePoint(2);
		Point3D p4 = strip.getVolumePoint(4);
		Point3D p5 = strip.getVolumePoint(5);
	
		// midpoints for calculating dy1/dy2
		Point3D p04 = p0.midpoint(p4);
		Point3D p15 = p1.midpoint(p5);
		
		double trim = 0.025;
		if( superlayer_id > 0 )
			trim *= 2; // trim to prevent overlap
	
		double dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2;

		dz     = 0.5*p1.distance(p2);
		theta  = 0;
		phi    = 0;
		dy1    = 0.5*p04.distance(p15) - trim;
		dx1    = 0.5*p0.distance(p4);
		dx2    = 0.5*p1.distance(p5);
		alpha1 = getTrapAlpha(p0,p1,p4,p5);
		dy2    = dy1;
		dx3    = dx1;
		dx4    = dx2;
		alpha2 = alpha1;

		return new Trap(dz, theta, phi, dy1, dx1, dx2, alpha1, dy2, dx3, dx4, alpha2);
	}
}
