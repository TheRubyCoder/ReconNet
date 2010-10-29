package petrinetze;

/**
* Diese Klasse stellt eine Transition in Petrinetze dar und
* bietet die dazu gehörige Methoden dafuer an.
* 
* @author Reiter, Safai
* @version 1.0
*/
import java.util.Vector;


public class Transition extends Node {

	private Vector<String> labels;
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Die rnw Methode.
	 */
	public void rnw() {
		
	}

	/**
	 * @return Den Namen der Transition.
	 */
	public Vector<String> getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 * 			Den Namen der Transition.
	 */
	public void setLabels(Vector<String> labels) {
		this.labels = labels;
	}

}
