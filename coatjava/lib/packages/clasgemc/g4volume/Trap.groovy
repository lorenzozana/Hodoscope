package clasgemc.g4volume;

/**
 * Stores size information of a generic Geant4 {@code Trap} with eleven parameters.
 * @param dz		half-length along z-axis
 * @param theta		polar angle of the line joining the centers of the +/-dz faces
 * @param phi		azimuthal angle joining the centers of the +/-dz faces
 * @param dy1		half-length along y of the face at -dz
 * @param dy2		half-length along y of the face at +dz
 * @param dx1		half-length along x of the side at -dy1 of the -dz face
 * @param dx2		half-length along x of the side at +dy1 of the -dz face
 * @param dx3		half-length along x of the side at -dy2 of the +dz face
 * @param dx4		half-length along x of the side at +dy2 of the +dz face
 * @param alpha1	angle with respect to the y axis from the center of the side at -dy1 to the center at +dy1 of the -dz face
 * @param alpha2	angle with respect to the y axis from the center of the side at -dy2 to the center at +dy2 of the +dz face
 */
public class Trap
{
	public double dz, theta, phi;
	public double dy1, dx1, dx2, alpha1;
	public double dy2, dx3, dx4, alpha2;

	public Trap(double z,  double t, double p,
			    double y1, double x1, double x2, double a1,
			    double y2, double x3, double x4, double a2)
	{
		dz     = z;
		theta  = t;
		phi    = p;
		dy1    = y1;
		dx1    = x1;
		dx2    = x2;
		alpha1 = a1;
		dy2    = y2;
		dx3    = x3;
		dx4    = x4;
		alpha2 = a2;
	}

	public double dz()
	{ return dz; }

	public double theta()
	{ return theta; }

	public double phi()
	{ return phi; }

	public double dy1()
	{ return dy1; }

	public double dx1()
	{ return dx1; }

	public double dx2()
	{ return dx2; }

	public double alpha1()
	{ return alpha1; }

	public double dy2()
	{ return dy2; }

	public double dx3()
	{ return dx3; }

	public double dx4()
	{ return dx4; }

	public double alpha2()
	{ return alpha2; }

	public String toString()
	{
		String desc = "Geant4 Generalized Trapazoid";
		desc += "\n\t    dz: " + dz;
		desc += "\n\t theta: " + theta;
		desc += "\n\t   phi: " + phi;
		desc += "\n\t   dy1: " + dy1;
		desc += "\n\t   dx1: " + dx1;
		desc += "\n\t   dx2: " + dx2;
		desc += "\n\talpha1: " + alpha1;
		desc += "\n\t   dy2: " + dy2;
		desc += "\n\t   dx3: " + dx3;
		desc += "\n\t   dx4: " + dx4;
		desc += "\n\talpha2: " + alpha2;

		return desc;
	}
}