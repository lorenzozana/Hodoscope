package clasgemc.g4volume;

/**
 * Stores size information of a basic Geant4 {@code Trd} with five parameters.
 * @param dx1	half-length along x at the surface positioned at -dz
 * @param dx2	half-length along x at the surface positioned at +dz
 * @param dy1	half-length along y at the surface positioned at -dz
 * @param dy2	half-length along y at the surface positioned at +dz
 * @param dz 	half-length along z-axis
 */
public class Trd
{
	public double dx1, dx2;
	public double dy1, dy2, dz;

	public Trd(double x1, double x2,
			   double y1, double y2, double z)
	{
		dx1 = x1;
		dx2 = x2;
		dy1 = y1;
		dy2 = y2;
		dz  =  z;
	}

	public double dx1()
	{ return dx1; }

	public double dx2()
	{ return dx2; }

	public double dy1()
	{ return dy1; }

	public double dy2()
	{ return dy2; }

	public double dz()
	{ return dz; }

	public String toString()
	{
		String desc = "Geant4 Trapazoid";
		desc += "\n\t dx1: " + dx1;
		desc += "\n\t dx2: " + dx2;
		desc += "\n\t dy1: " + dy1;
		desc += "\n\t dy2: " + dy2;
		desc += "\n\t dz : " + dz;

		return desc;
	}
}
