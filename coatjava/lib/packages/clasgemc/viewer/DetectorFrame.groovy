package clasgemc.viewer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import org.jlab.geom.prim.*;
import org.jlab.geom.base.*;

public class DetectorFrame extends JFrame
{
	protected JPanel sidePanel;
	protected JPanel compPanel;
	protected JPanel histPanel;
	protected DetectorPanel dectPanel;
	
	protected Detector detector;
	protected int layer_id;
	protected int superlayer_id;
	
	protected JLabel superlayer_label;
	protected JLabel layer_label;
	protected JLabel component_label;
	
	protected JComboBox superlayer_dropdown;
	protected JComboBox layer_dropdown;

	public DetectorFrame(Detector d)
	{
		super(d.getType());
		detector = d;
		
		setVisible(true);
		setSize(800,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		// make dropdown for selecting superlayer 
		Sector sector = detector.getSector(0);
		
		int num_superlayers = sector.getNumSuperlayers();
		Vector<Integer> superlayer_ids = new Vector<>();
		for( int i=0; i<num_superlayers; i++ )
			superlayer_ids.add(i);
		
		superlayer_dropdown = new JComboBox(superlayer_ids);
		ChangeSuperlayer change_suplay = new ChangeSuperlayer();
		superlayer_dropdown.addActionListener(change_suplay);
		superlayer_id = 0;
		
		superlayer_label = new JLabel("Superlayer 0");
		
		// make dropdown for selecting layer
		Superlayer superlayer = sector.getSuperlayer(superlayer_id);
		
		int num_layers = superlayer.getNumLayers();
		Vector<Integer> layer_ids = new Vector<>();
		for(int i=0; i<num_layers; i++)
			layer_ids.add(i);
		
		layer_dropdown = new JComboBox(layer_ids);
		ChangeLayer change_lay = new ChangeLayer();
		layer_dropdown.addActionListener(change_lay);
		layer_id = 0;
		
		layer_label = new JLabel("Layer 0");
		
		// build panel and add to frame
		Layer layer = superlayer.getLayer(layer_id);
		dectPanel = new DetectorPanel(layer);
		dectPanel.setBackground(Color.CYAN);
		
		
		// add everything to jframe
		Container c = getContentPane();
		dectPanel.add(layer_dropdown);
		dectPanel.add(layer_label);
		dectPanel.add(superlayer_dropdown);
		dectPanel.add(superlayer_label);
		dectPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		compPanel = new JPanel();
		compPanel.setPreferredSize(new Dimension(390,390));
		compPanel.setBackground(Color.YELLOW);
		compPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		component_label = new JLabel("No Position Defined")
		compPanel.add(component_label);
		
		histPanel = new JPanel();
		histPanel.setPreferredSize(new Dimension(390,390));
		histPanel.setBackground(Color.BLUE);
		histPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(400,800));
		sidePanel.setBackground(Color.CYAN);
		sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		c.add(dectPanel, BorderLayout.CENTER);
		
		sidePanel.add(compPanel,BorderLayout.NORTH);
		sidePanel.add(histPanel,BorderLayout.SOUTH);
		
		c.add(sidePanel,BorderLayout.LINE_END);

		pack();
		dectPanel.repaint();
	}
	
	// define action listener for changing layer dropdown
	public class ChangeLayer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			layer_id = (int)cb.getSelectedItem();
			Layer layer = detector.getSector(0).getSuperlayer(superlayer_id).getLayer(layer_id);
			
			layer_label.setText("Layer " + layer_id);
			
			dectPanel.setLayer(layer);
		}
	}
	
	// define action listener for changing superlayer dropdown
	public class ChangeSuperlayer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
			superlayer_id = (int)cb.getSelectedItem();
			
			Superlayer superlayer = detector.getSector(0).getSuperlayer(superlayer_id);
		
			int num_layers = superlayer.getNumLayers();
			
			layer_dropdown.removeActionListener(layer_dropdown.getActionListeners()[0]);
			
			layer_dropdown.removeAllItems();
			for(int i=0; i<num_layers; i++)
				layer_dropdown.addItem(i);
				
			ChangeLayer change_lay = new ChangeLayer();
			layer_dropdown.addActionListener(change_lay);
			// reset layer dropdown
			// (number of layers can change from superlayer to superlayer)
			layer_id = 0;
			
			Layer layer = detector.getSector(0).getSuperlayer(superlayer_id).getLayer(layer_id);
			
			layer_label.setText("Layer 0");
			superlayer_label.setText("Superlayer " + superlayer_id);
			
			dectPanel.setLayer(layer);
		}
	}

}