package engine;

import java.awt.geom.Point2D;

/**
 * Utility class for calculating positions
 */
public class Positioning {
	
	private Positioning() {	
		// utility class
	}
	
	/**
	 * Returns the addition of two points (vector-like addition)
	 * @example [5,7] + [1,3] = [6,9]
	 * @param base
	 * @param move
	 * @return
	 */
	public static Point2D.Double addPoints(Point2D base, Point2D move) {
		return new Point2D.Double(base.getX() + move.getX(), base.getY()
				+ move.getY());
	}

}
