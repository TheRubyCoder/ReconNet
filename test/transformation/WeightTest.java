package transformation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import petrinet.PetrinetComponent;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.PostArc;
import petrinet.model.PreArc;
import petrinet.model.Transition;

public class WeightTest {
    
    public Petrinet net1;
    public Rule rule1;
    public Transformation trans1;
    
    final int WEIGHT = 5;
    
    public WeightTest() {
        
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        net1 = PetrinetComponent.getPetrinet().createPetrinet();
        net1.addPlace("undefined");
        
        rule1 = new Rule();
        
        // TODO: Regel, wie in Bsp Netz darstellen
        Place p1 = rule1.addPlaceToK("undefined");       
        Transition t1 = rule1.addTransitionToR("undefined");
        
        PreArc pre1 = rule1.addPreArcToR("undefined",  rule1.fromKtoR(p1), t1);
        pre1.setWeight(WEIGHT);
        
        PostArc post1 = rule1.addPostArcToR("undefined", t1, rule1.fromKtoR(p1));
        post1.setWeight(WEIGHT);
        
        trans1 = TransformationComponent.getTransformation().transform(net1, rule1);
        trans1.transform();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void PreArcWeight() {
        Petrinet net2 = trans1.getPetrinet();
        System.out.println("Pre: " + net2.getPreArcs().iterator().next().getWeight());
        assertTrue("Weight", WEIGHT == net2.getPreArcs().iterator().next().getWeight());
    }
    
    @Test
    public void PostArcWeight() {
        Petrinet net2 = trans1.getPetrinet();
        System.out.println("Post: " + net2.getPostArcs().iterator().next().getWeight());
        assertTrue("Weight", WEIGHT == net2.getPostArcs().iterator().next().getWeight());
    }
    

}
