package transformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import petrinet.Arc;
import petrinet.INode;
import petrinet.Petrinet;
import engine.session.SessionManager;
import exceptions.EngineException;
import exceptions.GeneralPetrinetException;

/**
 * Singleton that represents the transformation component<br/>
 * Other components refer to this object to delegate to the transformation component instead of directly refering to the classes within the component
 */
public class TransformationComponent implements ITransformation{
	
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
	
	/**
	 * Transformations the petrinet like defined in rule with random morphism
	 * @param petrinet Petrinet to transform
	 * @param rule Rule to apply to petrinet
	 * @throws GeneralPetrinetException When no default morphism found
	 * @return the transformation that was used for transforming (containing rule, nNet and morphism)
	 */
	@Override
	public Transformation transform(Petrinet net, Rule rule)
	{
		Transformation transformation = Transformation.createTransformationWithAnyMorphism(net, rule);
		if(transformation != null){
			try {
				return transformation.transform();
			} catch (EngineException contact){
				System.out.println("Contact condition has been broken");
				return null;
			}
		}else{
			return null;
		}
	}

	@Override
	public void setMark(Rule rule, int placeId, int mark) {
		rule.setMark(placeId, mark);
	}

	@Override
	public List<INode> getMappings(Rule rule, INode node) {
		return rule.getMappings(node);
	}

	@Override
	public List<Arc> getMappings(Rule rule, Arc arc) {
		return rule.getMappings(arc);
	}

	@Override
	public List<INode> getMappings(int ruleId, INode node) {
		return rules.get(ruleId).getMappings(node);
	}

	@Override
	public void storeSessionId(int id, Rule rule) {
		rules.put(id, rule);
	}

}
