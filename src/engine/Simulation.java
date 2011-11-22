package engine;

import petrinet.Transition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control class for the token game.
 * 
 */
public class Simulation{

    private transient final List<StepListener> listeners = new ArrayList<StepListener>();

    private final EngineContext context;

    private final Logger logger = Logger.getLogger(Simulation.class.getName());

    private Timer timer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (context.getPetrinet().getActivatedTransitions().isEmpty()) {
                stop();
            }
            else {
                step();
            }
        }
    });

    Simulation(EngineContext context) {
        this.context = context;
    }
    
    /**
     * lets a transition do a step and fires an SteppedEvent to all Listeners
     */
    public void step() {
        context.getPetrinet().fire();
        fireStepped();
    }

    /**
     * Execution of a Simulation step with given transition.
     * 
     * @param transition : transition where the step should be done
     *
     * @todo Exception when transition could not be stepped
     *
     * @see petrinetze.IPetrinet#fire(int) 
     */  
    public void step(Transition transition) {
        context.getPetrinet().fire(transition.getId());
        fireStepped();
    }

    /**
     * start of continuous simulation.
     *
     * @param delay Time delay in milliseconds
     *
     * @throws IllegalStateException if simulation is active
     */
    public void start(long delay) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Starting simulation with delay of {0}ms.", delay);
        }
        timer.setDelay((int)delay);
        timer.start();
        fireStarted();
    }

    /**
     * stops the continuous simulation and fires a StoppedEvent
     */
    public void stop() {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Stopping simulation.");
        }
        timer.stop();
        fireStopped();
    }
    
    /**
     * adds a Listener, which is notified at each step.
     *
     * @param listener  {@link StepListener} to be notified.
     */
    public void addStepListener(StepListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
    }
    
    /**
     * removes a listener.
     *
     * @param listener  {@link StepListener} to be removed.
     */
    public void removeStepListener(StepListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
    
    /**
     * checks if simulation is active
     *
     * @return <code>true</code> if simulation is active, <code>false</code> otherwise
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * synchronized acces to the List of {@link StepListener}.
     *
     * @return copy of the list of listeners
     */
    private List<StepListener> allListeners() {
        synchronized (listeners) {
            return new ArrayList<StepListener>(listeners);
        }
    }

    /**
     * fires an event when starting the simulation
     */
    protected void fireStarted() {
        for (StepListener l : allListeners()) {
            try {
                l.started(this);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception in listener notification", ex);
            }
        }
    }

    /**
     * fires an event when stopping the simulation
     */
    protected void fireStopped() {
        for (StepListener l : allListeners()) {
            try {
                l.stopped(this);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception in listener notification", ex);
            }
        }
    }

    /**
     * fires an event at a single step
     */
    protected void fireStepped() {
        for (StepListener l : allListeners()) {
            try {
                l.stepped(this);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, "Exception in listener notification", ex);
            }
        }
    }
}
