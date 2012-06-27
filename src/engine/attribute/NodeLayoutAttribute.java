package engine.attribute;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * 
 * This Class holds all Information/Attribute of NodeLayout. (Which is position
 * and color)
 * 
 */

public class NodeLayoutAttribute {

	private Point2D coordinate;
	private Color color;

	public NodeLayoutAttribute(Point2D coordinate, Color color) {
		this.coordinate = coordinate;
		this.color = color;
	}

	public Point2D getCoordinate() {
		return coordinate;
	}

	public Color getColor() {
		return color;
	}

}
