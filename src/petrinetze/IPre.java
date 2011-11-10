package petrinetze;
/**
 * Das Objekt bildet das Pre-State eines Petrinetzes.
 * Und Liefert entsprechende Methoden.
 * 
 * @author Reiter, Safai
 *
 */
public interface IPre {
	/**
	 * Liefert das Pre von dem entsprechenden Petrinetz
	 * Kantengewichte der jeweiligen Transitionen und 
	 * Stellen.
	 * 
	 * 	0 1 3 6 
	 *  1 1 3 2
	 *  2 2 5 3	
	 *  3 1 3 1
	 *  
	 * @return
	 * Senkrecht: Stellen
	 * Waagerecht: Transitionen 
	 */
	public int[][] getPreAsArray ();
	
	/**
	 * Die Identifier der Transitionen, die in dem Pre Matrix vorhanden sind. 
	 * @return Array mit den Ids.
	 */
	public int[] getTransitionIds();
	
	/**
	 * Die Identifier der Places, die in dem Pre Matrix vorhanden sind. 
	 * @return Array mit den Ids.
	 */
	public int[] getPlaceIds();
	
	/**
	 * Returns only the part of toString() that represents the matrix
	 * @return Matrix like looking String
	 */
	public String matrixStringOnly();
}
