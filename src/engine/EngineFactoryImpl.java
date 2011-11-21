package engine;

import petrinetze.Petrinet;

public class EngineFactoryImpl extends EngineFactory {

    @Override
    public Engine createEngine(Petrinet petrinet) {
        return new Engine(petrinet);
    }
}
