package engine;

import engine.impl.EngineFactoryImpl;
import petrinetze.Petrinet;

public abstract class EngineFactory {

    protected EngineFactory() {}

    public static EngineFactory newFactory() {
        return new EngineFactoryImpl();
    }

    public abstract Engine createEngine(Petrinet petrinet);
}
