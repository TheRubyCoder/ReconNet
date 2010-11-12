package petrinetze;
/**
 * Diese Klasse fungiert als Laufzeitumgebung fuer die Netze.
 * Hier kann ein,oder mehrere Netze erzeugt und verwaltet werden.
 * @author Reiter, Safai
 *
 */
public interface ISession {
	/**
	 * Generiert ein Labeld S-T Petrinetz
	 * @return {@link IPetrinet}
	 */
	public IPetrinet createLabeldPetrinet();
	/**
	 * Fuegt der Session ein neues Netz hinzu.
	 * @param {@link IPetrinet} hinzufuegende Netz net
	 */
	public void sub(IPetrinet net);
	/**
	 * Loescht aus der Session das angegebene Netz.
	 * @param {@link IPetrinet} zu loeschende Netz net
	 */
	public void add(IPetrinet net);
	/**
	 * Indikator fuer es Setzen der Zaehlvariable.
	 * Dieser Indikator muss jedes Mal bei Stoppen mit (false)
	 * und starten mit (true) gesetzt werden. Wichtig, um die Zaehlvariable
	 * zu initialisieren.
	 * @param state (true oder false)
 	 */
	public void setRunning(boolean state);
}
