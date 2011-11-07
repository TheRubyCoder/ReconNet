package engine;

import petrinetze.ITransition;

/**
 * Control class for the token game.
 * 
 */
public interface Simulation {

    /**
     * Execution of a Simulation step.
     */
    void step();

    /**
     * Execution of a Simulation step with given transition.
     * 
     * @param transition : transition where the step should be done
     *
     * @todo Exception when transition could not be stepped
     *
     * @see petrinetze.IPetrinet#fire(int) 
     */
    void step(ITransition transition);

    /**
     * start of continuous simulation.
     *
     * @param delay Time delay in milliseconds
     *
     * @throws IllegalStateException if simulation is active
     */
    void start(long delay);

    /**
     * stop of continuous simulation.
     *
     */
    void stop();

    
    /**
     * checks if simulation is active
     *
     * @return <code>true</code> if simulation is active, <code>false</code> otherwise
     */
    boolean isRunning();

    /**
     * adds a Listener, which is notified at each step.
     *
     * @param listener  {@link StepListener} to be notified.
     */
    void addStepListener(StepListener listener);

    /**
     * removes a listener.
     *
     * @param listener  {@link StepListener} to be removed.
     */
    void removeStepListener(StepListener listener);

}
