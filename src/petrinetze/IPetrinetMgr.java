package haw.wp.rcpn;
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
	 * Leere Session, um Petrinetze zu generieren.
	 * Dieses Session-Objekt kann mehrere PN beinhalten.
	 * 
	 * @return  {@link ISession}
	 */
	public ISession createSession();
 
}
