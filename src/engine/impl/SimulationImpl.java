package engine.impl;

import engine.Simulation;
import engine.StepListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 14:40:32
 * To change this template use File | Settings | File Templates.
 */
class SimulationImpl implements Simulation {

    private final List<StepListener> listeners = new ArrayList<StepListener>();

    public void step() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void start(long delay) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void stop() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public boolean isRunning() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void addStepListener(StepListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    private List<StepListener> allListeners() {
        synchronized (listeners) {
            return new ArrayList<StepListener>(listeners);
        }
    }

    protected void fireStarted() {
        for (StepListener l : allListeners()) {
            l.started(this);
        }
    }

    protected void fireStopped() {
        for (StepListener l : allListeners()) {
            l.stopped(this);
        }
    }

    protected void fireStepped() {
        for (StepListener l : allListeners()) {
            l.stepped(this);
        }
    }
}
