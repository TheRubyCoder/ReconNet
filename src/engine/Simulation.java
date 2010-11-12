package engine;

/**
 * Steuerungsklasse für das Tokenspiel.
 * 
 */
public interface Simulation {

    /**
     * Durchführen eines Simulationsschrittes.
     */
    void step();

    /**
     * Starten der kontinuierlichen Simulation.
     *
     * @param delay Zeitverzögerung in Millisekunden
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
     * Aktueller Ausführungszustand
     *
     * @return <code>true</code> wenn die Simulation aktiv ist, <code>false</code> sonst
     */
    boolean isRunning();

    /**
     * Hinzufügen eines Listeners, der bei jedem Schritt benachrichtigt wird.
     *
     * @param listener der zu benachrichtigende {@link StepListener}.
     */
    void addStepListener(StepListener listener);

}
