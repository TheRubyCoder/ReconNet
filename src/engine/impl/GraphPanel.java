package engine.impl;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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


    static class PlaceIcon implements Icon {

        private final IPlace place;

        private final int size;


        PlaceIcon(IPlace place, int size) {
            this.place = place;
            this.size = size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            final Font font = c.getFont();
            final FontMetrics fm = g.getFontMetrics(font);
            final String label = String.valueOf(place.getMark());
            final Rectangle2D sb = fm.getStringBounds(label, g);
            final int sx = (int)(x + (size - sb.getWidth()) / 2);
            final int sy = (int)(y + fm.getAscent()+ (size - sb.getHeight()) / 2);

            g.setColor(Color.BLUE);
            g.fillOval(x, y, size, size);
            g.setColor(Color.WHITE);
            g.drawString(label, sx, sy);
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

	private void vertexInit() {
    	vv.getRenderContext().setVertexLabelTransformer(new Transformer<INode, String>() {
			@Override
			public String transform(INode arg0) {
				return arg0.getName();
			}
		});
    	vv.getRenderContext().setVertexIconTransformer(new Transformer<INode, Icon>() {
			private Icon transIcon;

			//Constructor
			{
				int s = 20;

				BufferedImage ti = new BufferedImage(s, s, BufferedImage.TYPE_3BYTE_BGR);
				Graphics g = ti.createGraphics();
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, s, s);

				transIcon = new ImageIcon(ti);

			}

            // FIXME schöner machen
            private Icon createPlaceIcon(IPlace place) {
                /*
                int s = 20;
                BufferedImage pi = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);
				Graphics2D g = pi.createGraphics();

                g = pi.createGraphics();
				g.setColor(Color.BLUE);
				g.fillOval(0, 0, s, s);

                if (place.getMark() > 0) {
                    String label = String.valueOf(place.getMark());

                    Rectangle2D b = g.getFontMetrics().getStringBounds(label, g);

                    g.drawString(label, (float)((b.getWidth() + s) / 2d), (float)((b.getHeight() + s) / 2d));

                    g.setColor(Color.BLACK);
                    g.fillOval(s / 2 - 4, s / 2 - 4, 8, 8);
                }
				g.dispose();

                return new ImageIcon(pi);
                */
                return new PlaceIcon(place, 20);
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
