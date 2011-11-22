package engine;

/**
 * Listener-Interface zur Benachrichtigung bei Schalten des Petrinetzes.
 *
 * @todo Diese Schnittstelle sollte evtl. in Teilen von der Petrinetzkomponente bedient werden,
 *       da diese das Tokenspiel implementiert.
 */
public interface StepListener {
    
    /**
     * Callback-Methode für das Schaltereignis.
     *
     * @param s die SimulationImpl, die das Schalten veranlasst hat
     * @todo Soll das Petrinetz hier mitgegeben werden?
     */
    void stepped(Simulation s);

    /**
     * Callback-Methode bei Start der SimulationImpl.
     *
     * @param s die gestartete SimulationImpl
     */
    void started(Simulation s);

    /**
     * Callback-Methode bei Stoppen der SimulationImpl.
     *
     * @param s die gestoppte SimulationImpl
     */
    void stopped(Simulation s);
}
