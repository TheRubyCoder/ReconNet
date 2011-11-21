package engine;

import edu.uci.ics.jung.graph.DirectedGraph;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Set;

import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;
import petrinetze.*;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GraphEditorImpl{

	private final EngineContext context;

	private GraphPanel graphPanel;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public GraphEditorImpl(EngineContext context, Engine engine) {
		this.context = context;
		graphPanel = new GraphPanel(engine, context);

		// FIXME hotfix for reacting direct on additions to IPetrinet
		engine.getNet().addPetrinetListener(
				new HotFixPetrinetListener(this.context));
	}

	private <T extends INode> T initNode(T node, final Point2D location) {
		// FIXME hotfix for reacting direct on additions to IPetrinet
		if (!context.getGraph().containsVertex(node)) {
			context.getGraph().addVertex(node);
		}
		System.out.println("in: " + location);
		context.getLayout().setLocation(node, location.getX(), location.getY());
		System.out.println("out: " + context.getLayout().getX(node) + ","
				+ context.getLayout().getY(node));
		return node;
	}

	/**
	 * Erzeugen einer Stelle an angegebener Position.
	 * 
	 * @param location
	 *            Position der Stelle
	 * 
	 * @return neu erzeugte Stelle
	 */
	public Place createPlace(Point2D location) {
		INode place = initNode(context.getPetrinet().createPlace("untitled"),
				location);
		System.out.println(String.format("createPlace(%d,%s)", place.getId(),
				location));
		return (Place) place;
	}

	/**
	 * Erzeugen einer Transition an angegebener Position.
	 * 
	 * @param location
	 *            Position der Transition
	 * 
	 * @return neu erzeugte Transition
	 */
	public Transition createTransition(Point2D location) {
		INode transition = initNode(
				context.getPetrinet().createTransition("untitled",
						Renews.IDENTITY), location);
		System.out.println(String.format("createTransition(%d,%s)",
				transition.getId(), location));
		// TODO IRenew durchreichen
		return (Transition) transition;
	}

	/**
	 * Erzeugen einer Kante zwischen Transition und Stelle.
	 * 
	 * Falls es sich nicht um Transition und Stelle handelt wird die Kante nicht
	 * erzeugt und es wird null zurück gegeben
	 * 
	 * @param from
	 * @param to
	 * 
	 * @return Kante zwischen Transition und Stelle oder null falls vorbindung
	 *         nicht erfüllt
	 */
	public Arc createArc(INode from, INode to) {
		System.out.println(String.format("createArc(%s, %s)", from, to));
		Arc arc = context.getPetrinet().createArc("", from, to);
		// context.getGraph().addEdge(arc, from, to, EdgeType.DIRECTED);
		return arc;
	}

	/**
	 * Entfernen der �bergegebenen Knoten.
	 * 
	 * @param nodes
	 *            die zu entfernenden Knoten
	 */
	public void remove(Set<? extends INode> nodes) {
		for (INode node : nodes) {
			// TODO besser nur aus Petrinet l?schen und dann bei
			// Listener-Callback aus Graph entfernen?
			if (node instanceof Arc) {
				context.getGraph().removeEdge((Arc) node);
				context.getPetrinet().deleteArcByID(node.getId());
			} else {
				context.getGraph().removeVertex(node);

				if (node instanceof Transition) {
					context.getPetrinet().deleteTransitionByID(node.getId());
				} else if (node instanceof Place) {
					context.getPetrinet().deletePlaceById(node.getId());
				}
			}
		}
	}

	public EditMode getEditMode() {
		return context.getEditMode();
	}

	public void setEditMode(EditMode editMode) {
		if (editMode == null)
			throw new IllegalArgumentException("EditMode must not be null");
		context.setEditMode(editMode);
	}

	/**
	 * Gibt das JPanel zum Anzeigen des Graphen zurück
	 * 
	 * @return das JPanel zum Anzeigen des Graphen
	 */
	public JPanel getGraphPanel() {
		return graphPanel;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	public boolean hasListeners(String propertyName) {
		return pcs.hasListeners(propertyName);
	}

	/**
	 * Gibt die ausgew�hlten Knoten / Kanten des Graphen zur�ck.
	 * 
	 * @return die ausgew�hlten Knoten / Kanten des Graphen zur�ck.
	 */
	public Set<INode> getPickedNodes() {
		return graphPanel == null ? Collections.<INode> emptySet() : graphPanel
				.getSelectedNodes();
	}

	private static class HotFixPetrinetListener implements IPetrinetListener {

		private EngineContext engineContext;

		HotFixPetrinetListener(EngineContext engineContext) {
			this.engineContext = engineContext;
		}

		@Override
		public void changed(Petrinet petrinet, INode element,
				ActionType actionType) {
			final DirectedGraph<INode, Arc> g = engineContext.getGraph();

			switch (actionType) {
			case added:
				if (!g.containsVertex(element)) {
					g.addVertex(element);
				}
				break;

			case deleted:
				if (g.containsVertex(element)) {
					g.removeVertex(element);
				}
				break;
			}
		}

		@Override
		public void changed(Petrinet petrinet, Arc element,
				ActionType actionType) {
			final DirectedGraph<INode, Arc> g = engineContext.getGraph();

			switch (actionType) {
			case added:
				if (!g.containsEdge(element)) {
					g.addEdge(element, element.getStart(), element.getEnd());
				}
				break;

			case deleted:
				if (g.containsEdge(element)) {
					g.removeEdge(element);
				}
				break;
			}
		}

	}
}
