package engine.handler;

import java.awt.geom.Point2D;
import java.util.Map;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Place;
import petrinet.Transition;

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
	}

	@Override
	public void createPlace(int id, RuleNet net, Point2D coordinate) {
	}

	@Override
	public int createRule() {
		return 0;
	}

	@Override
	public void createTransition(int id, RuleNet net, Point2D coordinate) {
	}

	@Override
	public void deleteArc(int id, RuleNet net, Arc arc) {
	}

	@Override
	public void deletePlace(int id, RuleNet net, INode place) {
	}

	@Override
	public void deleteTransition(int id, RuleNet net, INode transition) {
	}

	@Override
	public ArcAttribute getArcAttribute(int id, Arc arc) {
		return null;
	}

	@Override
	public AbstractLayout<INode, Arc> getJungLayout(int id, RuleNet net) {
		return null;
	}

	@Override
	public PlaceAttribute getPlaceAttribute(int id, INode place) {
		return null;
	}

	@Override
	public TransitionAttribute getTransitionAttribute(int id, INode transition) {
		return null;
	}

	@Override
	public RuleAttribute getRuleAttribute(int id) throws EngineException {
		
//	    RuleData ruleData = sessionManager.getRuleData(id);
//		
//		// Test: is id valid
//		if(ruleData == null){
//			exception("RuleManipulation - id of the rule is wrong");
//		} else {
//			ruleData.getKJungData();
//			
//			// TODO : weiter machen..
//			
//			int lId = 0;
//			int kId = 0;
//			int rId = 0;
//			
//			RuleAttribute ruleAttribute = new RuleAttribute(lId, kId, rId);
//			
//			return ruleAttribute;
//		}
//		
		return null;
	}

	@Override
	public void moveNode(int id, INode node, Point2D relativePosition) throws EngineException {
		
		// get the Petrinet from the id and SessionManager
		PetrinetData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData jungData = ruleData.getJungData();
	
			// step 1 alles durchgehen kleinstes x,y ; groeßstes x,y
			Map<INode, NodeLayoutAttribute> layoutMap = utility
					.getNodeLayoutAttributes(jungData);
			
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
					// TODO : write text
					exception("");
				}
				
			} else {
				
				Point2D point = new Point2D.Double(newPointX, newPointY);
				
				try {
					jungModification.moveNode(jungData, node, point);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
				
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
		PetrinetData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData jungData = ruleData.getJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new marking
				p.setMark(marking);
	
				// call JungModification
				try {
					jungModification.updatePlace(jungData, p);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
				
			}

		}
		
	}

	@Override
	public void setPname(int id, INode place, String pname) throws EngineException {
		
		// get the Petrinet from the id and SessionManager
		PetrinetData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData jungData = ruleData.getJungData();
	
			if (this.getNodeType(place).equals(NodeType.Place)) {
				// cast object
				Place p = (Place) place;
	
				// set new Pname
				p.setName(pname);
	
				// call JungModification
				try {
					jungModification.updatePlace(jungData, p);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
	
			}
			
		}
		
	}

	@Override
	public void setTlb(int id, INode transition, String tlb) throws EngineException {
		
		// get the Petrinet from the id and SessionManager
		PetrinetData ruleData = sessionManager.getRuleData(id);
		
		// Test: is id valid
		if(ruleData == null){
			exception("RuleManipulation - id of the rule is wrong");
		} else {
		
			JungData jungData = ruleData.getJungData();
	
			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;
	
				// set new Tlb
				t.setTlb(tlb);
	
				// call JungModification
				try {
					jungModification.updateTransition(jungData, t);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}
	
			}

		}
		
	}

	@Override
	public void setTname(int id, INode transition, String tname) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the rule is wrong");
		} else {

			JungData jungData = ruleData.getJungData();

			if (this.getNodeType(transition).equals(NodeType.Transition)) {
				// cast object
				Transition t = (Transition) transition;

				// set new Tname
				t.setName(tname);

				// call JungModification
				try {
					jungModification.updateTransition(jungData, t);
				} catch (IllegalArgumentException e) {
					// TODO : write text
					exception("");
				}

			}

		}

	}

	@Override
	public void setWeight(int id, Arc arc, int weight) throws EngineException {

		// get the Petrinet from the id and SessionManager
		PetrinetData ruleData = sessionManager.getRuleData(id);

		// Test: is id valid
		if (ruleData == null) {
			exception("RuleManipulation - id of the Rule is wrong");
		} else {

			JungData jungData = ruleData.getJungData();

			// set new weight
			arc.setMark(weight);

			// call JungModification
			try {
				jungModification.updateArc(jungData, arc);
			} catch (IllegalArgumentException e) {
				// TODO : write text
				exception("");
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
