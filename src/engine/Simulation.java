package engine;

import petrinetze.ITransition;

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
     * Durchf�hren eines Simulationsschrittes mit gegebener Transition.
     *
     * @todo Exception im Fehlerfall?
     *
     * @see petrinetze.IPetrinet#fire(int) 
     */
    void step(ITransition transition);

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

    /**
     * Entfernen eines Listeners.
     *
     * @param listener der zu entfernende {@link StepListener}.
     */
    void removeStepListener(StepListener listener);

}
