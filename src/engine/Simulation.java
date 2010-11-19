package engine;

import petrinetze.ITransition;

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
     * Durchführen eines Simulationsschrittes mit gegebener Transition.
     *
     * @todo Exception im Fehlerfall?
     *
     * @see petrinetze.IPetrinet#fire(int) 
     */
    void step(ITransition transition);

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

    /**
     * Entfernen eines Listeners.
     *
     * @param listener der zu entfernende {@link StepListener}.
     */
    void removeStepListener(StepListener listener);

}
