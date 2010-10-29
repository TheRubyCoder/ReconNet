package petrinetze;
/**
 * Dieses Interfcase bietet Metoden, um ein Petrinetz
 * zu generieren. Das verhindert die direkte Instnazbildung 
 * mit dem new Operator.
 * 
 * @author Reiter, Safai
 * @version 1.0
 */
public interface IPetrinetMgr {
	
	/**
	 * Generiert ein S-T Petrinetz
	 * @return {@link IPetrinet}
	 */
	public IPetrinet createPetrinet();
	
	/**
	 * Generiert ein Labeld S-T Petrinetz
	 * @return {@link IPetrinet}
	 */
	public IPetrinet createLabeldPetrinet();
 
}
