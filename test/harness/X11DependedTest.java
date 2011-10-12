package harness;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    engine.impl.EngineTest.class
})

public class X11DependedTest {}
