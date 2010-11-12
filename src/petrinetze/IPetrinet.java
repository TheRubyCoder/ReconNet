package petrinetze;

import java.util.List;

/**
 * IPetrinet definiert eine Schnittstelle um Petrinetzstrukturen
 * (Stellen, Transitionen, Kanten und Markierungen) zu editieren.
 * Außerdem liefert sie Methoden um die semantischen Aspekte
 * "Aktivierung" und "Schalten" bereitzustellen.
 * @version 1.0
 * @author Safai, Reiter
 */

public interface IPetrinet {

	
	/**
	 * Fuegt eine Stelle hinzu.
	 * @param name
	 * 			Der Name der Stelle.
	 * @param marks
	 * 			Die Anzahl der maximal möglichen Token.
	 */
	public IPlace createPlace(String name);
	
	/**
	 * Entfernt die angegebene Stelle.
	 * @param id
	 * 			Die ID der Stelle.
	 */
	public void deletePlaceById(int id);
	
	/**
	 * Fuegt eine Transition hinzu.
	 * @param name
	 * 			Der Name der Transition
	 */
	public ITransition createTransition(String name);
	
	/**
	 * Entfernt die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition. 
	 */
	public void deleteTransitionByID(String id);
	
	/**
	 * Fuegt eine Kante hinzu.
	 * @param marks
	 * 			Die Kantengewichtung.
	 */
	public IArc createArc();
	
	/**
	 * Entfernt die angegebene Kante.
	 * @param id
	 * 			Die ID der Kante.
	 */
	public void deleteArcByID(int id);
	
	/**
	 * Gibt alle aktivierten Transitionen zurück. 
	 */
	public List<ITransition> getActivatedTransitions();
	
	/**
	 * Schaltet die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition.
	 */
	public List<INode> fire(int id);
	
	/**
	 * Schaltet nicht-deterministisch.
	 * @return alle Nodes (sowohl Stellen als auch Transitionen), 
	 * die sich geaendert haben.
	 */
	public List<INode> fire();
	
	/**
	 * Liefert das Pre-Objekt zu dem Netz zurueck
	 * @return {@link IPre}
	 */
	public IPre getPre();
	
	/**
	 * Liefert das Pre-Objekt zu dem Netz zurueck
	 * @return {@link IPost}
	 */
	public IPost getPost();
	
	/**
	 * liefert die ID zurueck
	 * @return
	 */
	public int getId();
	
}
