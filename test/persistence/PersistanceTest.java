package persistence;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class PersistanceTest {

	@Test
	public void testExamplePNMLParsing() {
		Pnml pnml = new Pnml();
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(persistence.Pnml.class,
					Arc.class, Converter.class, Dimension.class,
					Graphics.class, InitialMarking.class, Name.class,
					Net.class, Page.class, Place.class, PlaceName.class,
					Position.class, Transition.class, TransitionLabel.class,
					TransitionName.class, TransitionRenew.class);
			Unmarshaller m = context.createUnmarshaller();

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			pnml = (Pnml) m
					.unmarshal(new File("test/persistence/example.pnml"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		petrinet.Petrinet petrinet = Converter.convertToPetrinet(pnml);
		
		Set<petrinet.Place> places = petrinet.getAllPlaces();
		assertEquals(1, places.size());
		
		petrinet.Place place = places.iterator().next();
		assertEquals(2, place.getMark());
		assertEquals("myplace", place.getName());
		
		petrinet.Transition transition = petrinet.getAllTransitions().iterator().next();
		assertEquals("mytrans", transition.getName());
	}

}
