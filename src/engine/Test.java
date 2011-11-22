package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.collections15.Transformer;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Transition;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import static engine.dependency.PetrinetAdapter.createPetrinet;

public class Test {


	public static void main(String[] args) throws Exception{

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


    	final EngineContext ec = new EngineContext(createPetrinet());
    	final Engine engine = new Engine(ec);

    	final VisualizationViewer<INode, Arc> vv = new VisualizationViewer<INode, Arc>(ec.getLayout(), new Dimension(600,600));
    	vv.getRenderContext().setVertexLabelTransformer(new Transformer<INode, String>() {
			@Override
			public String transform(INode arg0) {
				return arg0.getName();
			}
		});
    	vv.getRenderContext().setVertexIconTransformer(new Transformer<INode, Icon>() {
			private Icon transIcon, placeIcon;

			{
				int s = 20;

				BufferedImage pi = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);

				Graphics2D g = pi.createGraphics();
				g.setColor(Color.BLUE);
				g.fillOval(0, 0, s, s);
				g.dispose();

				BufferedImage ti = new BufferedImage(s, s, BufferedImage.TYPE_3BYTE_BGR);
				g = ti.createGraphics();
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, s, s);

				placeIcon = new ImageIcon(pi);
				transIcon = new ImageIcon(ti);

			}

			@Override
			public Icon transform(INode node) {
				if(node instanceof Transition) {
					return transIcon;
				}

				return placeIcon;
			}
		});

    	vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Arc, String>() {

			@Override
			public String transform(Arc arg0) {
				return ""+arg0.getMark();
			}
		});

		final EditingModalGraphMouseEx<INode, Arc> graphMouse =
        	new EditingModalGraphMouseEx<INode, Arc>(engine, vv.getRenderContext());

        vv.setGraphMouse(graphMouse);



        vv.addKeyListener(graphMouse.getModeKeyListener());

    	//-------------Buttons-------------------

    	List<JComponent> controlElements = new ArrayList<JComponent>();

    	controlElements.add(cb("FRLayout", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.getLayoutEditor().setLayout(Layout.FRLayout);
                engine.getLayoutEditor().apply(vv);
                System.out.println("FRLayout");
            }
        }));

    	controlElements.add(cb("KKLayout", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                engine.getLayoutEditor().setLayout(Layout.KKLayout);
                System.out.println("KKLayout");
                engine.getLayoutEditor().apply(vv);
            }
        }));

    	controlElements.add(cb("DoLayout", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getLayoutEditor().apply(vv);
			}
		}));

    	controlElements.add(cb("Place", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getGraphEditor().setEditMode(EditMode.ADD_PLACE);
			}
		}));

    	controlElements.add(cb("Transition", new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			engine.getGraphEditor().setEditMode(EditMode.ADD_TRANSITION);
    		}
    	}));

    	controlElements.add(graphMouse.getModeComboBox());



    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setSize(100, controlElements.size() * 26);
    	buttonPanel.setLayout(new GridLayout(0, 1));
    	for(JComponent b : controlElements) {
    		buttonPanel.add(b);
    	}


    	//----------------------------------------

    	final JFrame frame = new JFrame("test");
    	frame.getContentPane().add(vv);

    	JPanel eastPanel = new JPanel();
    	eastPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(),600));
    	eastPanel.setLayout(null);
    	eastPanel.add(buttonPanel);
    	eastPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    	frame.getContentPane().add(eastPanel, BorderLayout.EAST);

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	frame.pack();
    	EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                frame.setVisible(true);
            }
        });


	}

	private static JButton cb(String name, ActionListener l) {
		JButton b = new JButton(name);
		b.addActionListener(l);
		return b;
	}



}
