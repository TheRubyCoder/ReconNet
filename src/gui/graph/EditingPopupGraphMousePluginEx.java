package gui.graph;

import java.awt.event.MouseEvent;

import org.apache.commons.collections15.Factory;

import petrinetze.IArc;
import petrinetze.INode;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class EditingPopupGraphMousePluginEx extends EditingPopupGraphMousePlugin<INode, IArc> {

	public EditingPopupGraphMousePluginEx(Factory<INode> vertexFactory, Factory<IArc> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	@Override
	protected void handlePopup(MouseEvent e) {
        @SuppressWarnings("unchecked")
		final VisualizationViewer<INode, IArc> vv = (VisualizationViewer<INode, IArc>)e.getSource();
        PickedState<INode> pvs = vv.getPickedVertexState();
        
        
        
		if(!pvs.getPicked().isEmpty()) {
			INode node = vv.getPickSupport().getVertex(vv.getGraphLayout(), e.getX(), e.getY());
			
		}
		super.handlePopup(e);
	}

}
