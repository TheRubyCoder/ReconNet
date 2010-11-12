package petrinetze;

import java.util.Hashtable;
/**
 * Dieses Interface bildet das Trasition-Objekt und bietet die
 * entsprechenden Methoden an.
 * @author Reiter, Safai
 */
public interface ITransition extends INode {
	/**
	 * Liefert den Namen
	 */
	public String getName();
	/**
	 * Liefert die Id
	 */
	public int getId();
	/**
	 * Setzt den Namen
	 */
	public void setName(String name);

	/**
	 * @return Den Namen der Transition.
	 */
	public Hashtable<String, String> getTlb();

	/**
	 * Die Assoziation fuer die renew Funktion wird 
	 * in Form einer Tabelle mitgegeben.
	 * 
	 * @param labels den Namen der Transition.
	 */
	public void setTlb(Hashtable<String, String> labels);
	
	/**
	 * Fuehrt ein renew von Lable bei Id durch.
	 */
	public void rnwAsId();
	
	/**
	 * Fuehrt ein renew von Lable mit der Zaehlvariable durch
	 */
	public void rnwAsCount();
	
	/**
	 * Userdefined renew Funktion. 
	 * Beim Schalten werden die Bedingungen uerberprueft 
	 * und entsprechend ein renew durchgefuehrt. 
	 * @pre Voraussetzung fuer die Ausfuehrung dieser Methode ist
	 * eine ausgefuehlte Lable-Tabelle
	 */
	public void rnwAsUserDefined();
	

}