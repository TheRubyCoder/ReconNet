package gui.graph;

import org.apache.commons.collections15.Factory;

import petrinetze.IArc;
import petrinetze.INode;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;

public class EditingModalGraphMouseEx extends EditingModalGraphMouse<INode, IArc> {

	public EditingModalGraphMouseEx(RenderContext<INode, IArc> rc, Factory<INode> vertexFactory,
			Factory<IArc> edgeFactory, float in, float out) {
		super(rc, vertexFactory, edgeFactory, in, out);
		// TODO Auto-generated constructor stub
	}

	public EditingModalGraphMouseEx(RenderContext<INode, IArc> rc, Factory<INode> vertexFactory,
			Factory<IArc> edgeFactory) {
		super(rc, vertexFactory, edgeFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void loadPlugins() {
		super.loadPlugins();
		popupEditingPlugin = new EditingPopupGraphMousePlugin<INode,IArc>(vertexFactory, edgeFactory);
//		popupEditingPlugin.
	}

}
