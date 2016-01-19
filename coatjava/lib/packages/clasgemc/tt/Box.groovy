package g4volume;

/**
 * Stores size information of a Geant4 {@code Box} with three parameters.
 * @param dx	half-length along x
 * @param dy	half-length along y
 * @param dz	half-length along z
 */
public class Box
{
	protected double dx, dy, dz;

	public Box( double x, double y, double z)
	{
		dx = x;
		dy = y;
		dz = z;
	}

	public double dx()
	{ return dx; }

	public double dy()
	{ return dy; }

	public double dz()
	{ return dz; }

	public String toString()
	{
		String desc = "Geant4 Box";
		desc += "\n\t dx: " + dx;
		desc += "\n\t dy: " + dy;
		desc += "\n\t dz: " + dz;

		return desc;
	}
}
