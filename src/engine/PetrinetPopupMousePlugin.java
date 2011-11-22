package engine;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

/**
 * 
 * @author edit by alex
 *
 */
public class PetrinetPopupMousePlugin extends AbstractPopupGraphMousePlugin {

	/**
	 * Popupmenu for an right click
	 * include:
	 * - Perform Layout
	 * - Change Layout
	 * -- KKLayout
	 * -- FRLayout
	 * - Delete
	 */
    private JPopupMenu popupMenu;

    private VisualizationViewer<INode,Arc> viewer;

    private final Engine engine;

    private final Action deleteAction = new AbstractAction("Delete") {

		private static final long serialVersionUID = 4353136895080536189L;

		@Override
        public void actionPerformed(ActionEvent e) {
            engine.getGraphEditor().remove(viewer.getPickedVertexState().getPicked());
            engine.getGraphEditor().remove(viewer.getPickedEdgeState().getPicked());
        }
    };

    private final Action layoutAction = new AbstractAction("Perform Layout") {

		private static final long serialVersionUID = 8284060951365630301L;

		@Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().apply(viewer);
        }
    };

    private final Action kkLayoutAction = new AbstractAction("KKLayout") {

		private static final long serialVersionUID = -3397103612335429766L;

		@Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().setLayout(Layout.KKLayout);
        }
    };

    private final Action frLayoutAction = new AbstractAction("FRLayout") {

		private static final long serialVersionUID = -289433404847293940L;

		@Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().setLayout(Layout.FRLayout);
        }
    };

    /**
     * Constructor for this Class.
     * It build an Popupmenu for an right click.
     * @param engine
     */
    public PetrinetPopupMousePlugin(Engine engine) {
        this.engine = engine;
        this.popupMenu = new JPopupMenu();
        
        // add first entry: Perform Layout
        this.popupMenu.add(layoutAction);

        final JMenu layoutMenu = new JMenu("Change Layout");
        final JRadioButtonMenuItem kkItem = new JRadioButtonMenuItem(kkLayoutAction);
        layoutMenu.add(kkItem);
        final JRadioButtonMenuItem frItem = new JRadioButtonMenuItem(frLayoutAction);
        layoutMenu.add(frItem);
        final ButtonGroup layoutGroup = new ButtonGroup();
        layoutGroup.add(kkItem);
        layoutGroup.add(frItem);
        layoutGroup.setSelected(kkItem.getModel(), true);
        
        // add submenu with: KKLayout; LRLayout
        this.popupMenu.add(layoutMenu);

        this.popupMenu.add(new JSeparator());
        
        // all last entry: Delete
        this.popupMenu.add(deleteAction);
    }

    /**
     * Methode zum behandeln von MausEvents
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void handlePopup(MouseEvent e) {

        viewer = (VisualizationViewer<INode, Arc>) e.getSource();

        /*
        final INode selected = viewer.getPickSupport().getVertex(viewer.getGraphLayout(), e.getX(), e.getY());

        if (selected == null) {
            popupMenu.show((Component) e.getSource(), e.getX(), e.getY());
        }
        else if (selected instanceof ITransition) {
            System.out.println("On transition " + selected);
        }
        else if (selected instanceof IPlace) {
            System.out.println("On place " + selected);
        }
        */

        deleteAction.setEnabled(!(viewer.getPickedEdgeState().getPicked().isEmpty() && viewer.getPickedVertexState().getPicked().isEmpty()));
        popupMenu.show(viewer, e.getX(), e.getY());
    }
}
