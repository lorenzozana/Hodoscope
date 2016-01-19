package clasgemc.viewer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

public class DetectorPanel extends JPanel implements MouseMotionListener
{

	private Layer layer;
	private List<Component> comps;
	private List<Poly> polyList;
	private Point2D offset;
	
	private Ellipse2D circle;
	
	public DetectorPanel(Layer l)
	{
		setPreferredSize(new Dimension(800,800));
		
		layer = l;
		comps = layer.getAllComponents();
		
		addMouseMotionListener(this);
		
	}
	
	public void setLayer( Layer l )
	{
		layer = l;
		comps = layer.getAllComponents();
		
		this.repaint();
	}
	
	@Override	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// center origin at center of frame
		Graphics2D graphics = (Graphics2D) g.create();
		//graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Insets insets = getInsets();
		int width  = (int) (getWidth() / 2 );
		int height = (int) (getHeight() / 4 );
		offset = new Point2D.Double(width, height);
		println offset;
		//graphics.translate(width,height);
		graphics.scale(1.2,1.2);
		
		// add axes
		
		polyList = new ArrayList<Path2D.Double>();
		for ( Component component : comps )
		{
			// bounding points of one side of component
			Point3D p0 = component.getVolumePoint(0);
			Point3D p1 = component.getVolumePoint(1);
			Point3D p4 = component.getVolumePoint(4);
			Point3D p5 = component.getVolumePoint(5);
			
			// add component to panel
			Poly poly = new Poly();
			poly.addPoint(p0);
			poly.addPoint(p1);
			poly.addPoint(p5);
			poly.addPoint(p4);
			
			
			// add component to panel
			//Path2D.Double poly = buildPolygon2D(p0,p1,p4,p5);

			
			if(component.getComponentId()%2==0)
				graphics.setColor(new Color(255,128,0));
			else
				graphics.setColor(new Color(255,0,128));
			
			//graphics.fillPolygon(poly);
			graphics.fill(poly);
			
			graphics.setColor(Color.BLACK);
			//graphics.drawPolygon(poly);
			graphics.draw(poly);
			
			polyList.add(poly);
			
		}
		
		circle = new Ellipse2D.Double(0, 0, 50, 50);
		graphics.fill(circle);
		
		
		// clean up
		graphics.dispose();
		
	}
	
	class Poly extends Polygon
	{
		public Poly() { super(); }
		
		public void addPoint( Point3D p )
		{
			super.addPoint( (int)(p.x() + offset.getX()), (int)(p.y() + offset.getY()) );
		}
	}
	
	private Path2D.Double buildPolygon2D( Point3D p0, Point3D p1, Point3D p4, Point3D p5 )
	{
		
		Path2D.Double poly = new Path2D.Double();
		
		poly.moveTo( p0.y() + offset.getX(), -p0.x() + offset.getY() );
		poly.lineTo( p1.y() + offset.getX(), -p1.x() + offset.getY() );
		poly.lineTo( p5.y() + offset.getX(), -p5.x() + offset.getY() );
		poly.lineTo( p4.y() + offset.getX(), -p4.x() + offset.getY() );
		
		
		
		poly.closePath();
		
		return poly;
	}
	
	public void mouseMoved(MouseEvent event)
	{
		DetectorFrame parent = (DetectorFrame)SwingUtilities.getAncestorOfClass(DetectorFrame.class, this);
		parent.component_label.setText(String.format("Position: (%d,%d)", event.getX(), event.getY()));
		
		boolean inPoly = false;
		for( Poly poly : polyList )
		{
			if( poly.contains((int)event.getX(), (int)event.getY() ) )
			{
				parent.histPanel.setBackground(Color.WHITE);
				inPoly = true;
			}
		}
		
		/*if( circle.contains(event.getX(), event.getY() ) )
		{
			parent.histPanel.setBackground(Color.WHITE);
			inPoly = true;
		}*/
		
		if(!inPoly)
			parent.histPanel.setBackground(Color.BLUE);
	}
	
	public void mouseDragged(MouseEvent event)
	{}
	
	
}