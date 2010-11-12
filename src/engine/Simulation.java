package engine;

/**
 * Steuerungsklasse f�r das Tokenspiel.
 * 
 */
public interface Simulation {

    /**
     * Durchf�hren eines Simulationsschrittes.
     */
    void step();

    /**
     * Starten der kontinuierlichen Simulation.
     *
     * @param delay Zeitverz�gerung in Millisekunden
     *
     * @throws IllegalStateException falls die Simulation aktiv ist
     */
    void start(long delay);

    /**
     * Stoppen der kontinuierlichen Simulation.
     *
     */
    void stop();


    /**
     * Aktueller Ausf�hrungszustand
     *
     * @return <code>true</code> wenn die Simulation aktiv ist, <code>false</code> sonst
     */
    boolean isRunning();

    /**
     * Hinzuf�gen eines Listeners, der bei jedem Schritt benachrichtigt wird.
     *
     * @param listener der zu benachrichtigende {@link StepListener}.
     */
    void addStepListener(StepListener listener);

}
