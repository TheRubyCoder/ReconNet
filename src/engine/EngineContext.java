package engine;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

class EngineContext {


    public static final String PROPERTY_EDIT_MODE = "editMode";

    public static final String PROPERTY_LAYOUT= "layout";

    private Petrinet petrinet;

    private DirectedGraph<INode,Arc> graph = new DirectedSparseGraph<INode,Arc>();

    private AbstractLayout<INode, Arc> layout = new KKLayout<INode,Arc>(graph);

    private transient final PropertyChangeSupport pcs;

    private EditMode editMode;

    private final UIEditorImpl uiEditor;


    public EngineContext(Petrinet petrinet) {
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

    	for(Arc edge : petrinet.getAllArcs())
    		graph.addEdge(edge, edge.getStart(), edge.getEnd());
	}

	public Petrinet getPetrinet() {
        return petrinet;
    }

    public DirectedGraph<INode, Arc> getGraph() {
        return graph;
    }

    public AbstractLayout<INode, Arc> getLayout() {
        return layout;
    }

	public void setLayout(AbstractLayout<INode, Arc> layout) {
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
