package harness;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    petrinetze.impl.PetrinetTest.class,
    petrinetze.impl.PetrinetzTest.class,
    transformation.TransformationSuite.class
})
public class AllTests {}
