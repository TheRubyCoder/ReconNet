
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
	Rule3Test.class, 
//	Rule2Test.class, // Nullpointer in morphism finding
	Rule1Test.class,
	ScenariosRuleCreationTest.class ,
	VF2Test.class 
	}
)

public class TransformationSuite { }