package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import transformation.dependency.PetrinetAdapter;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.handler.NodeTypeEnum;
import engine.handler.petrinet.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;
import engine.session.SessionManager;
import exceptions.EngineException;

public class EngineMockupWithEngine {
	
	private IPetrinetManipulation iPetrinetManipulation;
	private int id;
	
	public EngineMockupWithEngine() {
		iPetrinetManipulation = PetrinetManipulation.getInstance();
		
		id = iPetrinetManipulation.createPetrinet();
	}
	
	public void build() throws EngineException {		

		iPetrinetManipulation.createPlace(id, new Point2D.Double(10, 10));
		iPetrinetManipulation.createPlace(id, new Point2D.Double(10, 100));
		iPetrinetManipulation.createPlace(id, new Point2D.Double(100, 10));
		iPetrinetManipulation.createPlace(id, new Point2D.Double(100, 100));

		iPetrinetManipulation.createTransition(id, new Point2D.Double(55, 10));
		iPetrinetManipulation.createTransition(id, new Point2D.Double(10, 55));
		iPetrinetManipulation.createTransition(id, new Point2D.Double(100, 55));
		iPetrinetManipulation.createTransition(id, new Point2D.Double(55, 100));

		Petrinet petrinet = SessionManager.getInstance().getPetrinetData(1).getPetrinet();
		
		List<Place> 	 places 	 = new ArrayList<Place>(petrinet.getAllPlaces());
		List<Transition> transitions = new ArrayList<Transition>(petrinet.getAllTransitions());
		
		iPetrinetManipulation.createArc(id, places.get(0),      transitions.get(0));
		iPetrinetManipulation.createArc(id, transitions.get(0), places.get(1));
		
		iPetrinetManipulation.createArc(id, places.get(1),      transitions.get(1));
		iPetrinetManipulation.createArc(id, transitions.get(1), places.get(2));
		
		iPetrinetManipulation.createArc(id, places.get(2),      transitions.get(2));
		iPetrinetManipulation.createArc(id, transitions.get(2), places.get(3));
		
		iPetrinetManipulation.createArc(id, places.get(3),      transitions.get(3));
		iPetrinetManipulation.createArc(id, transitions.get(3), places.get(0));
	}
	
}

