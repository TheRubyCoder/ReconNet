package engine;

import java.awt.geom.Point2D;

/**
 * Utility class for calculating positions
 */
public class Positioning {
	
	private Positioning() {	}
	
	/**
	 * Returns the addition of two points (vectors like addition)
	 * 
	 * @param base
	 * @param move
	 * @return
	 */
	public static Point2D.Double addPoints(Point2D base, Point2D move) {
		return new Point2D.Double(base.getX() + move.getX(), base.getY()
				+ move.getY());
	}

}
