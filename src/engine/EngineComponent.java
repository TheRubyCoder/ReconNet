package engine;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import javax.swing.JPanel;

import petrinet.ElementType;
import petrinet.INode;
import petrinet.Petrinet;
import transformation.ChangedPetrinetElements;
import transformation.Morphism;
import transformation.Rule;
import transformation.Transformation;
import engine.dependency.PetrinetAdapter;
import engine.dependency.TransformationAdapter;
import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.simulation.Simulation;
import exceptions.GeneralPetrinetException;

/**
 * Singleton that represents the engine component<br/>
 * Other components refer to this object to delegate to the engine component
 * instead of directly reffering to the classes within the component
 */
public class EngineComponent implements IPetrinetManipulation, IPersistence, ISimulation, ILayoutData {

	private static EngineComponent instance;

	private EngineComponent() {	}

	static {
		instance = new EngineComponent();
	}
	
	public static IPetrinetManipulation getPetrinetManipulation(){
		return (IPetrinetManipulation) PetrinetManipulation.getInstance();
	}
	
//	public static IPersistence getPersistence(){
//		return instance;
//	}
	
	public static ISimulation getSimulation(){
		return (ISimulation) Simulation.getInstance();		
	}
	
	//*************************************************************************
	// Engine methods!!! => PetrinetManipulation.getInstance().createPetrinet()
	//*************************************************************************	
	
	public Petrinet createPetrinet() {
		return PetrinetAdapter.createPetrinet();
	}

	public Petrinet getPetrinetById(int id) {
		return PetrinetAdapter.getPetrinetById(id);
	}


	@Override
	public Rule createRule() {
		return TransformationAdapter.createRule();
	}


	@Override
	public void join(Petrinet left, Petrinet right,
			Transformation transformation) {
		TransformationAdapter.join(left, right, transformation);
	}


	@Override
	public void join(Petrinet left, Petrinet right, Morphism morphism, Rule rule) {
		TransformationAdapter.join(left, right, morphism, rule);
	}


	@Override
	public void join(Petrinet left, Petrinet right, Rule rule)
			throws GeneralPetrinetException {
		TransformationAdapter.join(left, right, rule);
		
	}


	@Override
	public Transformation transform(Petrinet net, Rule rule){
		return TransformationAdapter.transform(net, rule);
	}


	@Override
	public boolean simulateOneStep(Petrinet petrinet) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean simulateKSteps(Petrinet petrinet, int k) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean startSimulation(Petrinet petrinet) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean simulateStep(Petrinet petrinet, Integer[] switchVektor) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean save(Petrinet petrinet, Map<INode, Point> layout, String file) {
		//TODO delegate to petrinet component. Use Adapter
		return false;
	}


	@Override
	public Petrinet loadPetrinet(String file) {
		//TODO delegate to petrinet component. Use Adapter
		return null;
	}


	@Override
	public Map<INode, Point> loadLayout(String file) {
		//TODO delegate to petrinet component. Use Adapter
		return null;
	}

	@Override
	public ChangedPetrinetElements setMark(Rule rule, int placeId, int mark) {
		return TransformationAdapter.setMark(rule, placeId, mark);
	}

	@Override
	public boolean drawPetrinet(JPanel panel, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Integer> deleteElementInPetrinet(int petrinetId,
			int elementId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementType getNodeType(int petrinetId, int nodeId) {
		// TODO Auto-generated method stub
		return null;
	}
}
