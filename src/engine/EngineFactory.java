package engine;

import engine.impl.EngineFactoryImpl;
import petrinetze.IPetrinet;

/**
 * Created by IntelliJ IDEA.
 * User: moritz
 * Date: 12.11.2010
 * Time: 16:43:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class EngineFactory {

    protected EngineFactory() {}

    public static EngineFactory newFactory() {
        return new EngineFactoryImpl();
    }

    public abstract Engine createEngine(IPetrinet petrinet);
}
