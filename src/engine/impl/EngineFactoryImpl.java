package engine.impl;

import engine.Engine;
import engine.EngineFactory;
import petrinetze.Petrinet;

public class EngineFactoryImpl extends EngineFactory {

    @Override
    public Engine createEngine(Petrinet petrinet) {
        return new EngineImpl(petrinet);
    }
}
