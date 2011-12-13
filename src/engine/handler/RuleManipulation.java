package engine.handler;

import java.awt.geom.Point2D;
import java.util.Map;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
import transformation.ITransformation;
import transformation.Rule;
import transformation.TransformationComponent;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.NodeLayoutAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.RuleAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.data.PetrinetData;
import engine.data.RuleData;
import engine.ihandler.IRuleManipulation;
import engine.jungmodification.JungModification;
import engine.session.SessionManager;
import engine.util.Utility;
import exceptions.EngineException;

/**
 * 
 * This Class implements engine.ihandler.IRuleManipulation.
 * 
 * It is a Singleton.
 * 
 * It can be use for all manipulations for a Rule. -
 * create[Rule|Arc|Place|Transition](..) - delete[Arc|Place|Transition](..) -
 * get[Rule|Arc|Place|Transition]Attribute(..) - getJungLayout(..) -
 * moveNode(..) - save(..) - set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)
 * 
 * @author alex (aas772)
 * 
 */

public class RuleManipulation implements IRuleManipulation {

	private final SessionManager sessionManager;
	private final JungModification jungModification;
	private final Utility utility;
	private static RuleManipulation ruleManipulation;

	private RuleManipulation() {
		sessionManager = SessionManager.getInstance();
		jungModification = JungModification.getInstance();
		utility = Utility.getInstance();
	}

	public static RuleManipulation getInstance() {
		if (ruleManipulation == null) {
			ruleManipulation = new RuleManipulation();
		}

		return ruleManipulation;
	}

	@Override
	public void createArc(int id, INode from, INode to) {
		// TODO
	}

	@Override
	public void createPlace(int id, RuleNet net, Point2D coordinate) {
		// TODO
	}

	@Override
	public int createRule() {

		Rule rule = TransformationComponent.getTransformation().createRule();
		
		// TODO id for each rule..
		
		return 0;
	}

	@Override
	public void createTransition(int id, RuleNet net, Point2D coordinate) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the Rule is wrong");
		} else {
			
			Petrinet lPetrinet = ruleData.getRule().getL();
			JungData lJungData = ruleData.getKJungData();
			
			Petrinet kPetrinet = ruleData.getRule().getK();
			JungData kJungData = ruleData.getKJungData();
			
			Petrinet rPetrinet = ruleData.getRule().getR();
			JungData rJungData = ruleData.getKJungData();
			
			// create a new Place
			Transition newTransition = lPetrinet.createTransition("undefined");
	
			// ***************************
			// TODO: ChangedPetrinetResult
			// ***************************
	
			// call JungModificator
			try {
//				jungModification.createTransition(jungData, newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}
		}		
		
	}

	@Override
	public void deleteArc(int id, RuleNet net, Arc arc) {
		// TODO
	}

	@Override
	public void deletePlace(int id, RuleNet net, INode place) {
		// TODO
	}

	@Override
	public void deleteTransition(int id, RuleNet net, INode transition) {
		// TODO
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {
		
		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;
		
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) {
		
		// TODO: Mathias =)
		
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) throws EngineException {
		
		if (this.getNodeType(place).equals(NodeType.Place)) {
			Place p = (Place) place;

			int marking = p.getMark();
			String pname = p.getName();

			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname);

			return placeAttribute;
		}
		
		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Place");
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) throws EngineException {
		
		if (this.getNodeType(transition).equals(NodeType.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw);

			return transitionAttribute;
		}
		
		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Transition");
		return null;
	}

	@Override
	public RuleAttribute getRuleAttribute(int id) throws EngineException {
		
		// get the RuleData from the id and SessionManager
	    RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
			// get all Id's from L, K, R
			int lId = ruleData.getRule().getL().getId();
			int kId = ruleData.getRule().getK().getId();
			int rId = ruleData.getRule().getR().getId();

			// create a RuleAttribute
			RuleAttribute ruleAttribute = new RuleAttribute(lId, kId, rId);
			
			return ruleAttribute;
		}
		
		return null;
	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition) throws EngineException {
		
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			// get all jungdata => l, k, r
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
	
			Map<INode, NodeLayoutAttribute> lLayoutMap = utility
					.getNodeLayoutAttributes(lJungData);
			Map<INode, NodeLayoutAttribute> kLayoutMap = utility
					.getNodeLayoutAttributes(kJungData);
			Map<INode, NodeLayoutAttribute> rLayoutMap = utility
					.getNodeLayoutAttributes(rJungData);
			
			// move node in l
			moveNodeInternal(lJungData, lLayoutMap, node, relativePosition);
			
			// move node in k
			moveNodeInternal(kJungData, kLayoutMap, node, relativePosition);
			
			// move node in r
			moveNodeInternal(rJungData, rLayoutMap, node, relativePosition);
			
		}	
		
	}
	
	/**
	 * 
	 * This is a helper method for moveNode. It calculate the new position.
	 * 
	 * @param jungData 
	 * @param layoutMap
	 * @param node
	 * @param relativePosition
	 * @throws EngineException
	 */
	private void moveNodeInternal(JungData jungData, Map<INode, NodeLayoutAttribute> layoutMap, INode node, Point2D relativePosition) throws EngineException{
		
		NodeLayoutAttribute nla = layoutMap.get(node);
		
		double newPointX = nla.getCoordinate().getX() + relativePosition.getX();
		double newPointY = nla.getCoordinate().getY() + relativePosition.getY();
		
		if(newPointX < 0 || newPointY < 0){
			
			double x = relativePosition.getX() - newPointX;
			double y = relativePosition.getY() - newPointY;

			Point2D point = new Point2D.Double(x, y);
			
			try {
				jungModification.moveNode(jungData, node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}
			
		} else {
			
			Point2D point = new Point2D.Double(newPointX, newPointY);
			
			try {
				jungModification.moveNode(jungData, node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}
			
		}
	
	}

	@Override
	public void save(int id, String path, String filename, String format) {
		
		// TODO: Alex... warte auf das Persistence-Team
		
	}

	@Override
	public void setMarking(int id, INode place, int marking) throws EngineException {
		
		// get the Petrinet from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new marking
				p.setMark(marking);
	
				// call JungModification for l
				try {
					
					jungModification.updatePlace(lJungData, p);
					
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in l");
				}

				// call JungModification for k
				try {

					jungModification.updatePlace(kJungData, p);

				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in k");
				}
				
				// call JungModification for r
				try {
					
					jungModification.updatePlace(rJungData, p);
					
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in r");
				}
				
			}

		}
		
	}

	@Override
	public void setPname(int id, INode place, String pname) throws EngineException {
		
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new Pname
				p.setName(pname);
	
				// call JungModification for l
				try {
					jungModification.updatePlace(lJungData, p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in l");
				}

				// call JungModification for k
				try {
					jungModification.updatePlace(kJungData, p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in k");
				}

				// call JungModification for r
				try {
					jungModification.updatePlace(rJungData, p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in r");
				}
				
			}
			
		}
		
	}

	@Override
	public void setTlb(int id, INode transition, String tlb) throws EngineException {
		
		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();
	
			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;
	
				// set new Tlb
				t.setTlb(tlb);
	
				// call JungModification for l
				try {
					jungModification.updateTransition(lJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in l");
				}
				
				// call JungModification for k
				try {
					jungModification.updateTransition(kJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in k");
				}
				
				// call JungModification for r
				try {
					jungModification.updateTransition(rJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in r");
				}
	
			}

		}
		
	}

	@Override
	public void setTname(int id, INode transition, String tname) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tname
				t.setName(tname);

				// call JungModification for l
				try {
					jungModification.updateTransition(lJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in l");
				}

				// call JungModification for k
				try {
					jungModification.updateTransition(kJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in k");
				}
				
				// call JungModification for r
				try {
					jungModification.updateTransition(rJungData, t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in r");
				}
				
			}

		}

	}

	@Override
	public void setWeight(int id, Arc arc, int weight) throws EngineException {

		// get the RuleData from the id and SessionManager
		RuleData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {

			JungData lJungData = ruleData.getLJungData();
			JungData kJungData = ruleData.getKJungData();
			JungData rJungData = ruleData.getRJungData();

			// set new weight
			arc.setMark(weight);

			// call JungModification for l
			try {
				jungModification.updateArc(lJungData, arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in l");
			}

			// call JungModification for k
			try {
				jungModification.updateArc(kJungData, arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in k");
			}

			// call JungModification for r
			try {
				jungModification.updateArc(rJungData, arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in r");
			}

		}

	}

	@Override
	public Enum<?> getNodeType(INode node) {

		if (node instanceof Place) {
			return NodeType.Place;
		} else if (node instanceof Transition) {
			return NodeType.Transition;
		} else {
			return null;
		}

	}

	private void exception(@NotNull String value) throws EngineException {
		throw new EngineException(value);
	}

}
