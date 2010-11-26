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
	public String getTlb();

	/**
	 * Fuehrt ein renew von Lable bei Id durch.
	 */
	public void rnw();
	
	/**
	 * Fuehrt ein renew von Lable mit der Zaehlvariable durch
	 */
	public void setRnw(IRenew rnw);
	
	/**
	 * Fuehrt ein renew von Lable mit der Zaehlvariable durch
	 */
	public IRenew getRnw();
	
	/**
	 * Userdefined renew Funktion. 
	 * Beim Schalten werden die Bedingungen uerberprueft 
	 * und entsprechend ein renew durchgefuehrt. 
	 * @pre Voraussetzung fuer die Ausfuehrung dieser Methode ist
	 * eine ausgefuehlte Lable-Tabelle
	 */
	public void rnwAsUserDefined();
	

}