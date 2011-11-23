package engine;

import java.awt.Point;
import java.util.Map;

import petrinet.INode;
import petrinet.Petrinet;

/**
 * Interface for saving and loading petrinets and rules via file system
 */
public interface IPersistence {
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB STUB
	 * @param petrinet
	 * @param layout
	 * @param file
	 * @return
	 */
	boolean save(Petrinet petrinet, Map<INode, Point> layout, String file);
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param file
	 * @return
	 */
	Petrinet loadPetrinet(String file);
	
	/**
	 * STUB STUB STUB STUB STUB STUB STUB STUB STUB STUB 
	 * @param file
	 * @return
	 */
	Map<INode, Point> loadLayout(String file);

}
