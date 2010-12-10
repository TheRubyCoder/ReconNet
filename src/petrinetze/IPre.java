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
	 * Die Identifier der Stellen und Transition werden 
	 * jeweils als nulltes Element des Arrays abgebildet
	 * Bsp.
	 * 
	 * 	  1 3 6 
	 *  1 1 3 2
	 *  2 2 5 3	
	 *  3 1 3 1
	 *  
	 * @return
	 * Nullte Stelle senkrecht: Id von Stellen
	 * Nullte Stelle waagerecht: Id von Transitionen 
	 */
	public int [][] getPreAsArray ();
}
