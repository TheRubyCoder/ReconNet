package petrinetze;

import java.util.List;
import java.util.Set;

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
	public ITransition createTransition(String name, IRenew rnw);
	
	/**
	 * Entfernt die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition. 
	 */
	public void deleteTransitionByID(int id);
	
	/**
	 * Fuegt eine Kante hinzu.
	 * 			Die Kantengewichtung.
	 */
	public IArc createArc(String name, INode start, INode end);
	
	/**
	 * Entfernt die angegebene Kante.
	 * @param id
	 * 			Die ID der Kante.
	 */
	public void deleteArcByID(int id);
	
	/**
	 * Gibt alle aktivierten Transitionen zurück. 
	 */
	public Set<ITransition> getActivatedTransitions();
	
	/**
	 * Schaltet die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition.
	 */
	public Set<INode> fire(int id);
	
	/**
	 * Schaltet nicht-deterministisch.
	 * @return alle Nodes (sowohl Stellen als auch Transitionen), 
	 * die sich geaendert haben.
	 */
	public Set<INode> fire();
	
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
	 * Liefert die ID zurueck
	 * @return 
	 */
	public int getId();
	
	/**
	 * Liefert alle Stellen des Petrinetzes zurueck
	 * @return {@link IPlace}
	 */
	public Set<IPlace> getAllPlaces();
	
	/**
	 * Liefert alle Transitionen des Petrinetzes zurueck
	 * @return {@link ITransition}
	 */
	public Set<ITransition> getAllTransitions();
	
	/**
	 * Liefert alle Kanten des Petrinetzes zuueck
	 * @return
	 */
	public Set<IArc> getAllArcs();
	
	/**
	 * Liefert alle graphische Elemente des Petrinetzes
	 * @return
	 */
	public IGraphElement getAllGraphElement();
	/**
	 * 
	 * @param l
	 */
	void addPetrinetListener(IPetrinetListener l);
	/**
	 * 
	 * @param l
	 */
	void removePetrinetListener(IPetrinetListener l);
	
}
