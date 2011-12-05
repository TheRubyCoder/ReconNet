package engine;

import javax.swing.JPanel;

/** Interface for layoting aspects of the engine */
public interface ILayoutData {
	
	/**
	 * Draws the petrinet with the given ID onto the JPanel
	 * @param panel 
	 * @param id
	 * @return <tt>true</tt> when successful<br/><tt>false</tt> else
	 */
	boolean drawPetrinet(JPanel panel, int id);

}
