package harness;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    petrinetze.impl.PetrinetTest.class,
    petrinetze.impl.PetrinetzTest.class,
    petrinetze.impl.DeleteTest.class,
    transformation.TransformationSuite.class,
    persistence.PersistanceTest.class,
    engine.data.JungDataTest.class
})
public class AllTests {}
