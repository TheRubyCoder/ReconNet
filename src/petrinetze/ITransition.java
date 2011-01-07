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
     * @throws {@link IllegalArgumentException} falls das Label 
     * nicht für die aktuelle Renewfunktion gültig ist.
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
	/**
	 * Das Pre von der entsprechenden Transition
	 * D.h., alle Stellen aus dem Vorbereich
	 * Das Ergebnis wird in Form eines Hashtables geliefert
	 * und zwar <Id, Mark> der Stelle
	 * @return alle Stellen <Id, Mark>
	 */
	public Hashtable<Integer, Integer> getPre();
	/**
	 * Das Post von der entsprechenden Transition
	 * D.h., alle Stellen aus dem Nachbereich
	 * Das Ergebnis wird in Form eines Hashtables geliefert
	 * und zwar <Id, Mark> der Stelle
	 * @return alle Stellen <Id, Mark>
	 */
	public Hashtable<Integer, Integer> getPost();
	/**
	 * Die uebegebene Kante wird in die Liste der ausgehenden Kanten 
	 * mitaufgenommen.
	 * @param aufzunehmende Kante
	 */
	public void setStartArcs (IArc arc);
	/**
	 * Die uebegebene Kante wird in die Liste der eingehenden Kanten 
	 * mitaufgenommen.
	 * @param aufzunehmende Kante
	 */
	public void setEndArcs (IArc arc);
	/**
	 * Liefert alle ausgehende Kanten
	 * @return
	 */
	public List<IArc> getStartArcs();
	
	/**
	 * Liefert alle eingehende Kanten
	 * @return
	 */
	public List<IArc> getEndArcs();
	

}