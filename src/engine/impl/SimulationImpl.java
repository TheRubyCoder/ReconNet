package engine.impl;

import engine.Simulation;
import engine.StepListener;
import petrinetze.ITransition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 14:40:32
 * To change this template use File | Settings | File Templates.
 */
class SimulationImpl implements Simulation {

    private transient final List<StepListener> listeners = new ArrayList<StepListener>();

    private final EngineContext context;

    private final Logger logger = Logger.getLogger(SimulationImpl.class.getName());

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

    SimulationImpl(EngineContext context) {
        this.context = context;
    }
    
    /**
     * lets a transition do a step and fires an SteppedEvent to all Listeners
     */
    
    @Override
    public void step() {
        context.getPetrinet().fire();
        fireStepped();
    }

    /**
     * lets a choosen transition do a step and fires an SteppedEvent to all Listeners
     * 
     * @param transition transition where the step should be done
     */   
    public void step(ITransition transition) {
        context.getPetrinet().fire(transition.getId());
        fireStepped();
    }

    /**
     * starts the continuous simulation and fires a StartedEvent 
     * 
     * @param delay time to wait after each step
     */
    @Override
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
    @Override
    public void stop() {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Stopping simulation.");
        }
        timer.stop();
        fireStopped();
    }
    
    /**
     * adds a stepListener to the list of listeners
     * 
     * @param listener you want to add
     */
    @Override
    public void addStepListener(StepListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
    }
    
    /**
     * removes a stepListener from the list of listeners
     * 
     * @param listener you want to remove
     */
    @Override
    public void removeStepListener(StepListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
    
    /**
     * checks if simulation is running
     * 
     * @return <code> true </code> if simulation is running, <code> false </code> otherwise
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
