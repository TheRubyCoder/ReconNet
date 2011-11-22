package engine;

import petrinet.Petrinet;

public class EngineFactoryImpl extends EngineFactory {

    @Override
    public Engine createEngine(Petrinet petrinet) {
        return new Engine(petrinet);
    }
}
