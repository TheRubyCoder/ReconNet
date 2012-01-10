package persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Graphics {

	Color color;
	
	List<Position> position;
	
	List<Dimension> dimension;
	
	
	
	@XmlElements(value = { @XmlElement })
	public List<Position> getPosition() {
		return position;
	}
	public void setPosition(List<Position> position) {
		this.position = position;
	}
	
	@XmlElements(value = { @XmlElement })
	public List<Dimension> getDimension() {
		return dimension;
	}
	public void setDimension(List<Dimension> dimension) {
		this.dimension = dimension;
	}
	
	@XmlElement
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
