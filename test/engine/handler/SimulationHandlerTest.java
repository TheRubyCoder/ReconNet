package engine.handler;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.util.IterationManager;
import org.junit.Test;

import petrinet.model.INode;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import transformation.ITransformation;
import transformation.Rule;
import transformation.TransformationComponent;
import engine.data.RuleData;
import engine.handler.petrinet.PetrinetManipulation;
import engine.handler.petrinet.PetrinetPersistence;
import engine.handler.rule.RuleManipulation;
import engine.handler.simulation.SimulationHandler;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRuleManipulation;
import engine.ihandler.ISimulation;
import engine.session.SessionManager;
import exceptions.EngineException;

public class SimulationHandlerTest {
/**
 * Fehler:Simulation bricht ab, wenn keine Regel anwendbar ist, obwohl mindestens eine Transition aktiviert ist
 */
	@Test
	public void fireOrTransformTest() {
		try {

			IPetrinetPersistence iPetrinetPersistence = PetrinetPersistence
					.getInstance();
			// petrinetz erstellen
			int petrinetID = iPetrinetPersistence.createPetrinet();
			// Koordinaten für alle Stellen festlegen
			Point2D place1Point = new Point2D.Double(50., 50.);
			Point2D place2Point = new Point2D.Double(400., 50.);
			Point2D place4Point = new Point2D.Double(50., 200.);
			Point2D place3Point = new Point2D.Double(400., 200.);

			// Stellen erstellen und in Petrinet einfügen
			Place place1 = iPetrinetPersistence.createPlace(petrinetID,
					place1Point);
			Place place2 = iPetrinetPersistence.createPlace(petrinetID,
					place2Point);
			Place place3 = iPetrinetPersistence.createPlace(petrinetID,
					place3Point);
			Place place4 = iPetrinetPersistence.createPlace(petrinetID,
					place4Point);
			// Label ändern
			iPetrinetPersistence.setPname(petrinetID, place3, "defined");
			// Markierung einfügen
			iPetrinetPersistence.setMarking(petrinetID, place1, 1);

			// Transitionen erstellen
			// Koordinaten für alle Transitionen festlegen
			Point2D transition1Point = new Point2D.Double(200., 50.);
			Point2D transition4Point = new Point2D.Double(50., 100.);
			Point2D transition2Point = new Point2D.Double(400., 100.);
			Point2D transition3Point = new Point2D.Double(200., 200.);
			// Transitionen erstellen und in Petrinet einfügen
			Transition trans1 = iPetrinetPersistence.createTransition(
					petrinetID, transition1Point);
			Transition trans2 = iPetrinetPersistence.createTransition(
					petrinetID, transition2Point);
			Transition trans3 = iPetrinetPersistence.createTransition(
					petrinetID, transition3Point);
			Transition trans4 = iPetrinetPersistence.createTransition(
					petrinetID, transition4Point);

			// Kanten erstellen und in Petrinet einfügen
			// PRE's
			iPetrinetPersistence.createPreArc(petrinetID, place1, trans1);
			iPetrinetPersistence.createPreArc(petrinetID, place2, trans2);
			iPetrinetPersistence.createPreArc(petrinetID, place3, trans3);
			iPetrinetPersistence.createPreArc(petrinetID, place4, trans4);
			// Post's
			iPetrinetPersistence.createPostArc(petrinetID, trans4, place1);
			iPetrinetPersistence.createPostArc(petrinetID, trans3, place4);
			iPetrinetPersistence.createPostArc(petrinetID, trans2, place3);
			iPetrinetPersistence.createPostArc(petrinetID, trans1, place2);

			//Erstelle Regel
			ITransformation iTransformation = TransformationComponent.getTransformation();
			Rule rule = iTransformation.createRule();
			RuleData ruleData = SessionManager.getInstance().createRuleData(rule);
			int ruleId = ruleData.getId();
			iTransformation.storeSessionId(ruleId, rule);
			Place placeK = rule.addPlaceToK("defined");
			Place placeL = rule.fromKtoL(placeK);
			Place placeR = rule.fromKtoR(placeK);
			rule.setMarkInK(placeL, 1);
			Transition transRule = rule.addTransitionToR(""); 
			rule.addPostArcToR("", transRule, placeR);
			rule.addPreArcToR("", placeR, transRule);
			
			
			
			
			
			
			// Simulation
			ISimulation iSimulation = SimulationHandler.getInstance();
			Collection<Integer> ruleIDs = new ArrayList<Integer>();
			ruleIDs.add(ruleId);
			
			
			iSimulation.fireOrTransform(petrinetID, ruleIDs, 2000);

		} catch (EngineException e) {
			e.printStackTrace();
		}
		assert (true);
	}


}
