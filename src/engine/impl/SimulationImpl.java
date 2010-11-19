package engine.impl;

import engine.Simulation;
import engine.StepListener;

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

    SimulationImpl(EngineContext context) {
        this.context = context;
    }

    @Override
    public void step() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void start(long delay) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Starting simulation with delay of {0}ms.", delay);
        }
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void stop() {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Stopping simulation.");
        }
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void addStepListener(StepListener listener) {
        synchronized (listeners) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }
    }

    @Override
    public void removeStepListener(StepListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public boolean isRunning() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Synchronisierter Zugriff auf die {@link StepListener}.
     *
     * @return Kopie der Listener-Liste
     */
    private List<StepListener> allListeners() {
        synchronized (listeners) {
            return new ArrayList<StepListener>(listeners);
        }
    }

    /**
     * Feuern eines Events beim Start der Simulation.
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
     * Feuern eines Events beim Stoppen der Simulation.
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
     * Feuern eines Events bei Simulationsschritt.
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
