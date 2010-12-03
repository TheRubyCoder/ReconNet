package engine.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.ITransition;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import engine.Engine;
import engine.impl.mock.Arc;
import engine.impl.mock.Node;
import engine.impl.mock.Petrinet;

public class Test {





	public static void main(String[] args) throws Exception{

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


    	final EngineContext ec = new EngineContext(new Petrinet());
    	final Engine engine = new EngineImpl(ec);

    	final VisualizationViewer<INode, IArc> vv = new VisualizationViewer<INode, IArc>(ec.getLayout(), new Dimension(600,600));
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

				Graphics2D g = null;


				g = pi.createGraphics();
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
				if(node instanceof ITransition) {
					return transIcon;
				}

				return placeIcon;
			}
		});

        Factory<INode> vertexFactory = new VertexFactory();
        Factory<IArc> edgeFactory = new EdgeFactory();

		final EditingModalGraphMouse<INode,IArc> graphMouse =
        	new EditingModalGraphMouse<INode,IArc>(vv.getRenderContext(), vertexFactory, edgeFactory);

    	graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);
        vv.addGraphMouseListener(new GraphMouseListener<INode>() {

			@Override
			public void graphReleased(INode v, MouseEvent me) {
				System.out.println("graphReleased: "+v+" "+me);

			}

			@Override
			public void graphPressed(INode v, MouseEvent me) {
				System.out.println("graphPressed: "+v+" "+me);

			}

			@Override
			public void graphClicked(INode v, MouseEvent me) {
				System.out.println("graphClicked: "+v+" "+me);

			}
		});
        vv.getPickedVertexState().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				System.out.println("itemStateChanged: "+e);

			}
		});

        vv.addKeyListener(graphMouse.getModeKeyListener());

    	//-------------Buttons-------------------

    	List<JButton> buttons = new ArrayList<JButton>();

    	buttons.add(cb("FRLayout", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getLayoutEditor().setLayout(Layout.FRLayout);
				engine.getLayoutEditor().apply(vv);
				System.out.println("FRLayout");
			}
		}));

    	buttons.add(cb("KKLayout", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				engine.getLayoutEditor().setLayout(Layout.KKLayout);
				System.out.println("KKLayout");
				engine.getLayoutEditor().apply(vv);
			}
		}));

    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setSize(100, buttons.size() * 26);
    	buttonPanel.setLayout(new GridLayout(0, 1));
    	for(JButton b : buttons) {
    		buttonPanel.add(b);
    	}


    	//----------------------------------------

    	JFrame frame = new JFrame("test");
    	frame.getContentPane().add(vv);

    	JPanel eastPanel = new JPanel();
    	eastPanel.setPreferredSize(new Dimension(buttonPanel.getWidth(),600));
    	eastPanel.setLayout(null);
    	eastPanel.add(buttonPanel);
    	eastPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
    	frame.getContentPane().add(eastPanel, BorderLayout.EAST);

    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	frame.pack();
    	frame.setVisible(true);

	}

	private static JButton cb(String name, ActionListener l) {
		JButton b = new JButton(name);
		b.addActionListener(l);
		return b;
	}


    static class VertexFactory implements Factory<INode> {

    	int i=0;

		public INode create() {
			return new Node();
		}
    }

    static class EdgeFactory implements Factory<IArc> {

    	int i=0;

		public IArc create() {
			return new Arc();
		}
    }


}
