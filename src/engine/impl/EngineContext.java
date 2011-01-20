package engine.impl;

import engine.EditMode;
import petrinetze.IArc;
import petrinetze.INode;
import petrinetze.IPetrinet;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:09:26
 * To change this template use File | Settings | File Templates.
 */
class EngineContext {


    public static final String PROPERTY_EDIT_MODE = "editMode";

    public static final String PROPERTY_LAYOUT= "layout";

    private IPetrinet petrinet;

    private DirectedGraph<INode,IArc> graph = new DirectedSparseGraph<INode,IArc>();

    private AbstractLayout<INode, IArc> layout = new KKLayout<INode,IArc>(graph);

    private transient final PropertyChangeSupport pcs;

    private EditMode editMode;

    private final UIEditorImpl uiEditor;


    public EngineContext(IPetrinet petrinet) {
        this.petrinet = petrinet;

        this.uiEditor = new UIEditorImpl();

        createGraph();

        pcs = new PropertyChangeSupport(this);
    }

	private void createGraph() {
    	for(INode node : petrinet.getAllPlaces()) {
    		graph.addVertex(node);
    		layout.lock(node, true);
    	}

    	for(INode node : petrinet.getAllTransitions())
    		graph.addVertex(node);

    	for(IArc edge : petrinet.getAllArcs())
    		graph.addEdge(edge, edge.getStart(), edge.getEnd());
	}

	public IPetrinet getPetrinet() {
        return petrinet;
    }

    public DirectedGraph<INode, IArc> getGraph() {
        return graph;
    }

    public AbstractLayout<INode, IArc> getLayout() {
        return layout;
    }

	public void setLayout(AbstractLayout<INode, IArc> layout) {
        final AbstractLayout old = this.layout;
		this.layout = layout;
        pcs.firePropertyChange(PROPERTY_LAYOUT, old, layout);
	}

    public EditMode getEditMode() {
        return editMode;
    }

    public void setEditMode(EditMode editMode) {
        final EditMode old = this.editMode;
        this.editMode = editMode;
        pcs.firePropertyChange(PROPERTY_EDIT_MODE, old, editMode);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public boolean hasListeners(String propertyName) {
        return pcs.hasListeners(propertyName);
    }

    public UIEditorImpl getUIEditor() {
        return uiEditor;
    }
}
