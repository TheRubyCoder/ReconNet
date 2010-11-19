package engine.impl;

import engine.Engine;
import engine.EngineFactory;
import petrinetze.IPetrinet;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:44:27
 * To change this template use File | Settings | File Templates.
 */
public class EngineFactoryImpl extends EngineFactory {

    @Override
    public Engine createEngine(IPetrinet petrinet) {
        return new EngineImpl(new EngineContext(petrinet));
    }
}
