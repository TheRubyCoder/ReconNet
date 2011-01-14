package engine.impl.ctrl;

import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import engine.Engine;
import engine.GraphEditor;
import engine.impl.Layout;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPlace;
import petrinetze.ITransition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PetrinetPopupMousePlugin extends AbstractPopupGraphMousePlugin {

    private JPopupMenu popupMenu;

    private VisualizationViewer<INode,IArc> viewer;

    private final Engine engine;

    private final Action deleteAction = new AbstractAction("Delete") {
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.getGraphEditor().remove(viewer.getPickedVertexState().getPicked());
            engine.getGraphEditor().remove(viewer.getPickedEdgeState().getPicked());
        }
    };

    private final Action layoutAction = new AbstractAction("Perform Layout") {
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().apply(viewer);
        }
    };

    private final Action kkLayoutAction = new AbstractAction("KKLayout") {
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().setLayout(Layout.KKLayout);
        }
    };

    private final Action frLayoutAction = new AbstractAction("FRLayout") {
        @Override
        public void actionPerformed(ActionEvent e) {
            engine.getLayoutEditor().setLayout(Layout.FRLayout);
        }
    };

    public PetrinetPopupMousePlugin(Engine engine) {
        this.engine = engine;
        this.popupMenu = new JPopupMenu();
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
        popupMenu.add(layoutMenu);

        this.popupMenu.add(new JSeparator());
        this.popupMenu.add(deleteAction);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void handlePopup(MouseEvent e) {

        viewer = (VisualizationViewer<INode, IArc>) e.getSource();

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
