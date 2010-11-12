package engine;

/**
 * Listener-Interface zur Benachrichtigung bei Schalten des Petrinetzes.
 *
 * @todo Diese Schnittstelle sollte evtl. von der Petrinetzkomponente bedient werden.
 */
public interface StepListener {
    
    /**
     * Callback-Methode für das Schaltereignis.
     *
     * @param s die Simulation, die das Schalten veranlasst hat
     * @todo Soll das Petrinetz hier mitgegeben werden?
     */
    void stepped(Simulation s);
}
