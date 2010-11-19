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
     * @param s die Simulation, die das Schalten veranlasst hat
     * @todo Soll das Petrinetz hier mitgegeben werden?
     */
    void stepped(Simulation s);

    /**
     * Callback-Methode bei Start der Simulation.
     *
     * @param s die gestartete Simulation
     */
    void started(Simulation s);

    /**
     * Callback-Methode bei Stoppen der Simulation.
     *
     * @param s die gestoppte Simulation
     */
    void stopped(Simulation s);
}
