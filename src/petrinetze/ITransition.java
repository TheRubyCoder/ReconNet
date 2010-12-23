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
     * Neues Label setzen,
     *
     * @throws {@link IllegalArgumentException} falls das Label nicht f�r die aktuelle Renewfunktion g�ltig ist.
     */
    public void setTlb(String tlb);

	/**
	 * Fuehrt ein renew von Lable bei Id durch.
	 */
	public String rnw();
	
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
	
	public Hashtable<Integer, Integer> getPre();
	public Hashtable<Integer, Integer> getPost();
	public void setStartArcs (IArc arc);
	public void setEndArcs (IArc arc);
	

}