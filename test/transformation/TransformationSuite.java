/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import transformation.rules.*;

/**
 *
 * @author Niklas
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
//	TransformationsTest.class,
//	TransformationTest.class,
	MorphismTest.class,
	RuleTest.class,
	MorphismPlacesTest.class,
	MorphismTransitionsTest.class,
	Rule1Test.class 
	}
)

public class TransformationSuite { }