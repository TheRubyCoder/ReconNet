package petrinetze;
/**
 * IPetrinet definiert eine Schnittstelle um Petrinetzstrukturen
 * (Stellen, Transitionen, Kanten und Markierungen) zu editieren.
 * Au�erdem liefert sie Methoden um die semantischen Aspekte
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
	 * 			Die Anzahl der maximal m�glichen Token.
	 */
	public void createPlace(String name);
	
	/**
	 * Entfernt die angegebene Stelle.
	 * @param id
	 * 			Die ID der Stelle.
	 */
	public void deletePlaceById(int id);
	
	/**
	 * �ndert den Namen der Stelle.
	 * @param id
	 * 			Die ID der Stelle.
	 * @param newName
	 * 			Der neue Name der Stelle.
	 */
	public void changePlaceName(int id, String newName);
	
	/**
	 * �ndert die Anzahl der maximal m�glichen Token.
	 * @param id
	 * 			Die ID der Stelle.
	 * @param marks
	 * 			Die neue Anzahl der maximal m�glichen Token. 
	 */
	public void changePlaceMarks(int id, int marks);
	
	
	
	/**
	 * Fuegt eine Transition hinzu.
	 * @param name
	 * 			Der Name der Transition
	 */
	public void createTransition(String name);
	
	/**
	 * Entfernt die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition. 
	 */
	public void deleteTransitionByID(String id);
	
	/**
	 * �ndert den Namen der Transition.
	 * @param id
	 * 			Die ID der Transition.
	 * @param newName
	 * 			Der neue Name der Transition.
	 */
	public void changeTransitionName(String id, String newName);
	
	/**
	 * rnw  ---> TODO
	 */
	public void rnw();
	
	/**
	 * tlb  ---> TODO
	 */
	public void tlb();
	
	
	
	/**
	 * Fuegt eine Kante hinzu.
	 * @param marks
	 * 			Die Kantengewichtung.
	 */
	public void createArc(int marks);
	
	/**
	 * Entfernt die angegebene Kante.
	 * @param id
	 * 			Die ID der Kante.
	 */
	public void deleteArcByID(int id);
	
	/**
	 * �ndert die Kantengewichtung der Kante.
	 * @param id
	 * 			Die ID der Kante.
	 * @param marks
	 * 			Die neue Kantengewichtung. 
	 */
	public void changeArcMarks(int id, int marks);
	
	/**
	 * �ndert den Startknoten der Kante.
	 * @param id
	 * 			Die ID der Kante.
	 */
	public void changeArcStart(int id);
	
	/**
	 * �ndert den Endknoten der Kante.
	 * @param id
	 * 			Die ID der Kante.
	 */
	public void changeArcEnd(int id);
	
	
	
	/**
	 * Gibt alle aktivierten Transitionen zur�ck. 
	 */
	public void getActivatedTransitions();
	
	/**
	 * Schaltet die angegebene Transition.
	 * @param id
	 * 			Die ID der Transition.
	 */
	public void fire(int id);
	
	
	
}
