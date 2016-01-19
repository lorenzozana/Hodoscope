package clasgemc.g4volume;

/**
 * Description of {@code Rotation} about the x, y, and z axes of a G4 solid
 */
public class Rotation
{
	public double x, y, z;

	public Rotation(double rx, double ry, double rz)
	{
		x = rx;
		y = ry;
		z = rz;
	}

	public double x()
	{ return x; }

	public double y()
	{ return y; }

	public double z()
	{ return z; }

	public String toString()
	{
		String desc = "Rotation of Solid";
		desc += "\n\tAround x: " + x;
		desc += "\n\tAround y: " + y;
		desc += "\n\tAround z: " + z;

		return desc;
	}
}