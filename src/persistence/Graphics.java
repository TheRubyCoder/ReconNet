package persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 * Class that represents the graphical information of a node (xml node)
 *
 */
public class Graphics {

	/**
	 * Color information (sub xml node)
	 */
	Color color;
	
	/**
	 * Information about position (sub xml node)
	 */
	List<Position> position;
	
	@XmlElements(value = { @XmlElement })
	public List<Position> getPosition() {
		return position;
	}
	public void setPosition(List<Position> position) {
		this.position = position;
	}
	
	@XmlElement
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
