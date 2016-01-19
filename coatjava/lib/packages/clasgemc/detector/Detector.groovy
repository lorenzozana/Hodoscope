//-----------------------------------------------------------
// Definitions for generalized Detector
//
// Author: Lee Allison, 2/27/2015
//-----------------------------------------------------------

package clasgemc.detector;
import  clasgemc.g4volume.Rotation;

import java.lang.Math;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

import org.jlab.geom.detector.ec.*;
import org.jlab.geom.detector.ftof.*;
import org.jlab.clasrec.utils.DataBaseLoader;

/**
 * Generic {@code Detector} class
 */
public class Detector
{
	// Maps volume to properties
	// {volume name: {prop. name: prop. value}}
	protected LinkedHashMap<String, Object> volumes_map;

	public Detector() 
	{ volumes_map = new LinkedHashMap<String, Object>();}
	
	/**
	 * Uses regex to extract the rotation about each axis from a {@code Transformation3D}
	 */
	public Rotation getLayerRotation( Layer layer )
	{
		double rx = 0;
		double ry = 0;
		double rz = 0;
		
		List<Transformation3D.Transform> transform = layer.getTransformation().transformSequence();
		
		for( Transformation3D.Transform sequence : transform )
		{
			if(sequence.toString().contains("Translate"))
				continue;
			
			double rotation = Double.valueOf(sequence.toString().replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
			
			if(sequence.toString().contains("X"))
				rx += Math.toDegrees(rotation);
			
			else if(sequence.toString().contains("Y"))
				ry += Math.toDegrees(rotation);
			
			else if(sequence.toString().contains("Z"))
				rz += Math.toDegrees(rotation);
				
			else
			{
				println "An error has occured in getLayerRotation(). Exiting.\n"
				System.exit(1);
			}
		}
		
		return new Rotation(rx,ry,rz);
	}
	
	// for testing purposes only
	public Double getLayerRotation( String string )
	{
		double myDouble = 0;
		myDouble += Double.valueOf(string.replaceAll("[^\\d.]+|\\.(?!\\d)", ""));
		
		return myDouble;
	}
	
	/**
	 * Using a predetermeined {@code Vector3D} defining the shift from the origin
	 * to the center of the {@code Component} stack, shifts each {@code Component}
	 * such that the origin and stack center lie on top of each other
	 */
	public void getComponentStackShift( List<Component> components, Vector3D shift )
	{
		for(Component component : components) 
			component.translateXYZ(-shift.x(),-shift.y(),-shift.z());
	}
}
