package petrinetze;

import java.util.Hashtable;
import java.util.List;
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
	public String rnw(String tlb);
	
	/**
	 * Fuehrt ein renew von Lable mit der Zaehlvariable durch
	 */
	public void setRnw(IRenew rnw);
	
	/**
	 * Fuehrt ein renew von Lable mit der Zaehlvariable durch
	 */
	public IRenew getRnw();
	/**
	 * Liefert alle abgehende Stellen
	 * @return
	 */
	public List<IPlace> getOutgoingPlaces ();
	/**
	 * Liefer alle eingehende Stellen 
	 * @return
	 */
	public List<IPlace> getIncomingPlaces ();	
	

}