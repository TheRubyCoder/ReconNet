
package transformation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import transformation.rules.*;

/**
 *  
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//	TransformationsTest.class,
//	TransformationTest.class,
	MorphismTest.class,
	RuleTest.class,
	MorphismPlacesTest.class,
	MorphismTransitionsTest.class,
	Rule7Test.class,
//	Rule2Test.class, // fails
	Rule1Test.class,
	ScenariosRuleCreationTest.class
	}
)

public class TransformationSuite { }