package transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;
import transformation.Rule.Net;
import engine.session.SessionManager;
import exceptions.EngineException;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation component instead of directly refering to the classes within the component
 */
public class TransformationComponent implements ITransformation {
	
	/** To Store mappings of ids of {@link SessionManager} */
	Map<Integer,Rule> rules = new HashMap<Integer, Rule>();
	
	//#################### singleton ##################
	private static TransformationComponent instance;
	
	private TransformationComponent() { }
	
	static {
		instance = new TransformationComponent();
	}
	
	public static ITransformation getTransformation() {
		return instance;
	}
	//#################################################
	
	//### instance methods  (UML-Interface) ###########
	@Override
	public Rule createRule() {
		return new Rule();
	}
	

	@Override
	public List<INode> getMappings(Rule rule, INode node) {
		List<INode> mappings = new ArrayList<INode>(3);
		
		mappings.add(null);
		mappings.add(null);
		mappings.add(null);
		
		
		if (node instanceof Place) {
			Net net = getNet(rule, (Place) node);
			
			switch (net) {
			case L :
				mappings.set(0, (Place) node);
				mappings.set(1, rule.fromLtoK((Place) node));
				mappings.set(2, rule.fromLtoR((Place) node));
				break;

			case K :
				mappings.set(0, rule.fromKtoL((Place) node));
				mappings.set(1, (Place) node);
				mappings.set(2, rule.fromKtoR((Place) node));
				break;

			case R :
				mappings.set(0, rule.fromRtoL((Place) node));
				mappings.set(1, rule.fromRtoK((Place) node));
				mappings.set(2, (Place) node);
				break;					
			}
		} else if (node instanceof Transition) {
			Net net = getNet(rule, (Transition) node);
			
			switch (net) {
			case L :
				mappings.set(0, (Transition) node);
				mappings.set(1, rule.fromLtoK((Transition) node));
				mappings.set(2, rule.fromLtoR((Transition) node));
				break;

			case K :
				mappings.set(0, rule.fromKtoL((Transition) node));
				mappings.set(1, (Transition) node);
				mappings.set(2, rule.fromKtoR((Transition) node));
				break;

			case R :
				mappings.set(0, rule.fromRtoL((Transition) node));
				mappings.set(1, rule.fromRtoK((Transition) node));
				mappings.set(2, (Transition) node);
				break;					
			}
		}
		
		return mappings;
	}

	@Override
	public List<IArc> getMappings(Rule rule, IArc arc) {
		List<IArc> mappings = new ArrayList<IArc>(3);
		
		mappings.add(null);
		mappings.add(null);
		mappings.add(null);
		
		
		if (arc instanceof PreArc) {
			Net net = getNet(rule, (PreArc) arc);
			
			switch (net) {
			case L :
				mappings.set(0, (PreArc) arc);
				mappings.set(1, rule.fromLtoK((PreArc) arc));
				mappings.set(2, rule.fromLtoR((PreArc) arc));
				break;

			case K :
				mappings.set(0, rule.fromKtoL((PreArc) arc));
				mappings.set(1, (PreArc) arc);
				mappings.set(2, rule.fromKtoR((PreArc) arc));
				break;

			case R :
				mappings.set(0, rule.fromRtoL((PreArc) arc));
				mappings.set(1, rule.fromRtoK((PreArc) arc));
				mappings.set(2, (PreArc) arc);
				break;					
			}
		} else if (arc instanceof PostArc) {
			Net net = getNet(rule, (PostArc) arc);
			
			switch (net) {
			case L :
				mappings.set(0, (PostArc) arc);
				mappings.set(1, rule.fromLtoK((PostArc) arc));
				mappings.set(2, rule.fromLtoR((PostArc) arc));
				break;

			case K :
				mappings.set(0, rule.fromKtoL((PostArc) arc));
				mappings.set(1, (PostArc) arc);
				mappings.set(2, rule.fromKtoR((PostArc) arc));
				break;

			case R :
				mappings.set(0, rule.fromRtoL((PostArc) arc));
				mappings.set(1, rule.fromRtoK((PostArc) arc));
				mappings.set(2, (PostArc) arc);
				break;					
			}
		}
		
		return mappings;
	}

	@Override
	public List<INode> getMappings(int ruleId, INode node) {
		return getMappings(rules.get(ruleId), node);
	}
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	@Override
	public Transformation transform(Petrinet net, Rule rule) 	{
		Transformation transformation = Transformation.createTransformationWithAnyMorphism(net, rule);
		if(transformation == null) {
			return null;
		}
		
		try {
			return transformation.transform();
		} catch (EngineException contact){
			contact.printStackTrace();
			System.out.println("Contact condition has been broken");
			return null;
		}
	}



	@Override
	public void storeSessionId(int id, Rule rule) {
		rules.put(id, rule);
	}
	
	public Net getNet(Rule rule, Place place) {
		if (rule.getL().containsPlace(place)) {
			return Net.L;
			
		} else if (rule.getK().containsPlace(place)) {
			return Net.K;
			
		} else if (rule.getR().containsPlace(place)) {
			return Net.R;
		} 

		System.out.println("unknown place");	
		return null;
	}
	
	public Net getNet(Rule rule, Transition transition) {
		if (rule.getL().containsTransition(transition)) {
			return Net.L;
			
		} else if (rule.getK().containsTransition(transition)) {
			return Net.K;
			
		} else if (rule.getR().containsTransition(transition)) {
			return Net.R;
		} 

		System.out.println("unknown place");	
		return null;
	}
	
	public Net getNet(Rule rule, PreArc preArc) {
		if (rule.getL().containsPreArc(preArc)) {
			return Net.L;
			
		} else if (rule.getK().containsPreArc(preArc)) {
			return Net.K;
			
		} else if (rule.getR().containsPreArc(preArc)) {
			return Net.R;
		} 

		System.out.println("unknown place");	
		return null;
	}
	
	public Net getNet(Rule rule, PostArc postArc) {
		if (rule.getL().containsPostArc(postArc)) {
			return Net.L;
			
		} else if (rule.getK().containsPostArc(postArc)) {
			return Net.K;
			
		} else if (rule.getR().containsPostArc(postArc)) {
			return Net.R;
		} 

		System.out.println("unknown place");	
		return null;
	}
}
