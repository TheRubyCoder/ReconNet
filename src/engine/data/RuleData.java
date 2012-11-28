package engine.data;

import java.awt.geom.Point2D;
import java.util.List;

import petrinet.model.INode;
import transformation.Rule;
import transformation.TransformationComponent;
import engine.Positioning;
import engine.attribute.ColorGenerator;
import exceptions.ShowAsInfoException;

/**
 * This Class is a data container for a Rule and all Petrinet in this Rule.
 * 
 * @author alex (aas772)
 */

final public class RuleData extends SessionDataAbstract {
	private Rule rule;
	private JungData jungDataL;
	private JungData jungDataK;
	private JungData jungDataR;
	/** Used for adding places so all places in the rule have the same color */
	private ColorGenerator colorGenerator;

	@SuppressWarnings("unused")
	// no default constructor
	private RuleData() {
	}

	public RuleData(int id, Rule rule, JungData lJungData, JungData kJungData,
			JungData rJungData) {
		check(id > 0, "id have to be greater than 0");
		check(rule instanceof Rule, "petrinet not of type Petrinet");
		check(lJungData instanceof JungData, "lJungData not of type JungData");
		check(kJungData instanceof JungData, "kJungData not of type JungData");
		check(rJungData instanceof JungData, "rJungData not of type JungData");

		checkContaining(rule.getK(), kJungData);
		checkContaining(rule.getL(), lJungData);
		checkContaining(rule.getR(), rJungData);

		check(!(kJungData == lJungData || lJungData == rJungData || kJungData == rJungData),
				"jungData same instance");

		this.id = id;
		this.rule = rule;
		this.jungDataL = lJungData;
		this.jungDataK = kJungData;
		this.jungDataR = rJungData;
		this.colorGenerator = new ColorGenerator();
	}

	/**
	 * Gets the JungData of L from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getLJungData() {
		return jungDataL;
	}

	/**
	 * Gets the JungData of K from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getKJungData() {
		return jungDataK;
	}

	/**
	 * Gets the JungData of R from a Rule
	 * 
	 * @return JungData
	 */
	public JungData getRJungData() {
		return jungDataR;
	}

	/**
	 * Gets a Rule.
	 * 
	 * @return Rule
	 */
	public Rule getRule() {
		return rule;
	}

	/** Returns the color generator for this rule */
	public ColorGenerator getColorGenerator() {
		return colorGenerator;
	}

	/**
	 * Removes data of elements that are no longer in the rule. This may be used
	 * if the rule is altered from outside the engine.
	 * 
	 * @param petrinet
	 */
	public void deleteDataOfMissingElements(Rule rule) {
		getLJungData().deleteDataOfMissingElements(rule.getL());
		getKJungData().deleteDataOfMissingElements(rule.getK());
		getRJungData().deleteDataOfMissingElements(rule.getR());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuleData other = (RuleData) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * Moves the <tt>node</tt> to the <tt>coordinate</tt> relativ to its current
	 * position. Also moves all mappings in L,K,R
	 * 
	 * @param node
	 * @param coordinate
	 * @throws ShowAsInfoException
	 *             if nodes are in the way
	 */
	public void moveNodeRelative(INode node, Point2D coordinate) {
		if (!getKJungData().isCreatePossibleAt(coordinate)) {
			throw new ShowAsInfoException("Ein Knoten ist im Weg");
		}

		List<INode> nodeMappings = TransformationComponent.getTransformation()
				.getMappings(getRule(), node);
		INode nodeInL = nodeMappings.get(0);
		INode nodeInK = nodeMappings.get(1);
		INode nodeInR = nodeMappings.get(2);
		if (nodeInL != null) {
			getLJungData().moveNodeWithoutPositionCheck(
					nodeInL,
					Positioning.addPoints(getLJungData()
							.getNodeLayoutAttributes().get(nodeInL)
							.getCoordinate(), coordinate));
		}
		if (nodeInK != null) {
			getKJungData().moveNodeWithoutPositionCheck(
					nodeInK,
					Positioning.addPoints(getKJungData()
							.getNodeLayoutAttributes().get(nodeInK)
							.getCoordinate(), coordinate));
		}
		if (nodeInR != null) {
			getRJungData().moveNodeWithoutPositionCheck(
					nodeInR,
					Positioning.addPoints(getRJungData()
							.getNodeLayoutAttributes().get(nodeInR)
							.getCoordinate(), coordinate));
		}
	}
}
