package engine.handler.rule;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Map;

import petrinet.Arc;
import petrinet.INode;
import petrinet.IRenew;
import petrinet.Petrinet;
import petrinet.Place;
import petrinet.Transition;
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
import engine.data.RuleData;
import engine.handler.NodeTypeEnum;
import engine.handler.RuleNet;
import engine.ihandler.IRuleManipulation;
import engine.session.SessionManager;
import exceptions.EngineException;



final public class RuleHandler {

	private final SessionManager sessionManager;
	private static RuleHandler ruleManipulation;

	private RuleHandler() {
		sessionManager = SessionManager.getInstance();
	}

	protected static RuleHandler getInstance() {
		if (ruleManipulation == null) {
			ruleManipulation = new RuleHandler();
		}

		return ruleManipulation;
	}

	public void createArc(int id, INode from, INode to) {
		// TODO
	}

	public void createPlace(int id, RuleNet net, Point2D coordinate) {
		// TODO
	}

	public int createRule() {

		Rule rule = TransformationComponent.getTransformation().createRule();
		
		// TODO id for each rule..
		
		return 0;
	}

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
	
			// call Jung
			try {
//				jungData.createTransition(newTransition, coordinate);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
			}
	
		}		
		
	}

	public void deleteArc(int id, RuleNet net, Arc arc) {
		// TODO
	}

	public void deletePlace(int id, RuleNet net, INode place) {
		// TODO
	}

	public void deleteTransition(int id, RuleNet net, INode transition) {
		// TODO
	}

	public ArcAttribute getArcAttribute(int id, Arc arc) {
		
		int weight = arc.getMark();

		ArcAttribute arcAttribute = new ArcAttribute(weight);

		return arcAttribute;
		
	}

	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) {
		
		// TODO: Mathias =)
		
		return null;
	}

	public PlaceAttribute getPlaceAttribute(int id, INode place) throws EngineException {
		
		if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
			Place p = (Place) place;

			int marking = p.getMark();
			String pname = p.getName();
			
			// TODO : change default color
			Color color = Color.gray;

			PlaceAttribute placeAttribute = new PlaceAttribute(marking, pname, color);

			return placeAttribute;
		}
		
		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Place");
		return null;
	}

	public TransitionAttribute getTransitionAttribute(int id, INode transition) throws EngineException {
		
		if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
			Transition t = (Transition) transition;

			String tlb = t.getTlb();
			String tname = t.getName();
			IRenew rnw = t.getRnw();
			
			// TODO : change default boolean
			boolean isActivated = false;

			TransitionAttribute transitionAttribute = new TransitionAttribute(
					tlb, tname, rnw, isActivated);

			return transitionAttribute;
		}
		
		exception("RuleManipulation - getPlaceAttribute: the INode value is not a Transition");
		return null;
	}

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
	
			Map<INode, NodeLayoutAttribute> lLayoutMap = lJungData.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> kLayoutMap = kJungData.getNodeLayoutAttributes();
			Map<INode, NodeLayoutAttribute> rLayoutMap = rJungData.getNodeLayoutAttributes();
			
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
	 * 
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
				jungData.moveNode(node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}
			
		} else {
			
			Point2D point = new Point2D.Double(newPointX, newPointY);
			
			try {
				jungData.moveNode(node, point);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: moveNode");
			}
			
		}
	
	}

	public void save(int id, String path, String filename, String format) {
		
		// TODO: Alex... warte auf das Persistence-Team
		
	}

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
	
			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new marking
				p.setMark(marking);
	
				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updatePlace(p);
					
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in l");
				}

				// call Jung for k
				try {

					// TODO existiert nimmer
					//kJungData.updatePlace(p);

				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in k");
				}
				
				// call Jung for r
				try {
					
					// TODO existiert nimmer
					//rJungData.updatePlace(p);
					
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setMarking in r");
				}
				
			}

		}
		
	}

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
	
			if (this.getNodeType(place).equals(NodeTypeEnum.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new Pname
				p.setName(pname);
	
				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in l");
				}

				// call JungModification for k
				try {					
					// TODO existiert nimmer
					// kJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in k");
				}

				// call JungModification for r
				try {					
					// TODO existiert nimmer
					// rJungData.updatePlace(p);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setPname in r");
				}
				
			}
			
		}
		
	}

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
	
			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;
	
				// set new Tlb
				t.setTlb(tlb);
	
				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in l");
				}
				
				// call Jung for k
				try {
					// TODO existiert nimmer
					// kJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in k");
				}
				
				// call Jung for r
				try {
					// TODO existiert nimmer
					// rJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTlb in r");
				}
	
			}

		}
		
	}

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

			if (this.getNodeType(transition).equals(NodeTypeEnum.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tname
				t.setName(tname);

				// call Jung for l
				try {
					// TODO existiert nimmer
					// lJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in l");
				}

				// call Jung for k
				try {
					// TODO existiert nimmer
					// kJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in k");
				}
				
				// call Jung for r
				try {
					// TODO existiert nimmer
					// rJungData.updateTransition(t);
				} catch (IllegalArgumentException e) {
					exception("RuleManipulation - error: setTname in r");
				}
				
			}

		}

	}

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
				// TODO existiert nimmer
				// lJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in l");
			}

			// call JungModification for k
			try {
				// TODO existiert nimmer
				// kJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in k");
			}

			// call JungModification for r
			try {
				// TODO existiert nimmer
				// rJungData.updateArc(arc);
			} catch (IllegalArgumentException e) {
				exception("RuleManipulation - error: setWeight in r");
			}

		}

	}

	public Enum<?> getNodeType(INode node) {

		if (node instanceof Place) {
			return NodeTypeEnum.Place;
		} else if (node instanceof Transition) {
			return NodeTypeEnum.Transition;
		} else {
			return null;
		}

	}

	private void exception(@NotNull String value) throws EngineException {
		throw new EngineException(value);
	}

}
