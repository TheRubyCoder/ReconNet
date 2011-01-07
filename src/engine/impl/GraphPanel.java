package engine.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import engine.Engine;
import engine.impl.ctrl.EditingModalGraphMouseEx;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final EngineContext ec;
	private final VisualizationViewer<INode, IArc> vv;
	private final EditingModalGraphMouseEx<INode, IArc> graphMouse;

    
	

	public GraphPanel(Engine engine, EngineContext context) {
		ec = context;
		vv = new VisualizationViewer<INode, IArc>(ec.getLayout());
		
		setLayout(new BorderLayout());
		add(vv, BorderLayout.CENTER);
		
		graphMouse = new EditingModalGraphMouseEx<INode, IArc>(engine, vv.getRenderContext());
		vv.setGraphMouse(graphMouse);
		vv.addKeyListener(graphMouse.getModeKeyListener());
		
		vertexInit();
		edgeInit();
	}


	private void vertexInit() {
    	vv.getRenderContext().setVertexLabelTransformer(new Transformer<INode, String>() {
			@Override
			public String transform(INode arg0) {
				return arg0.getName();
			}
		});
    	vv.getRenderContext().setVertexIconTransformer(new Transformer<INode, Icon>() {
			private Icon transIcon, placeIcon;

			//Constructor
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

            // FIXME sch�ner machen
            private Icon createPlaceIcon(IPlace place) {
                int s = 20;
                BufferedImage pi = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g = pi.createGraphics();

                g = pi.createGraphics();
				g.setColor(Color.BLUE);
				g.fillOval(0, 0, s, s);
                g.setColor(Color.BLACK);
                g.fillOval(s / 2 - 4, s / 2 - 4, 8, 8);
				g.dispose();

                return new ImageIcon(pi);
            }

			@Override
			public Icon transform(INode node) {
				if(node instanceof ITransition) {
					return transIcon;
				}

				return createPlaceIcon((IPlace)node);
			}
		});
		
	}
	
	private void edgeInit() {
		vv.getRenderContext().setEdgeLabelTransformer(new Transformer<IArc, String>() {
			
			@Override
			public String transform(IArc arg0) {				
				return ""+arg0.getMark();
			}
		});		
	}
	
}
