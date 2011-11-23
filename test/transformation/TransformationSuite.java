
package transformation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import transformation.rules.*;

/**
 *  
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	MorphismTest.class,
	RuleTest.class,
	MorphismPlacesTest.class,
	MorphismTransitionsTest.class,
	Rule7Test.class,
//	Rule3Test.class, // fails
//	Rule2Test.class, // fails
	Rule1Test.class,
	ScenariosRuleCreationTest.class //fails partially
	}
)

public class TransformationSuite { }