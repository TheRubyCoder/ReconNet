package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import exceptions.EngineException;

import petrinet.INode;
import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;

public class Converter {

	/** not working yet */
	static public Pnml convertToPnml(Petrinet petrinet,
			Map<String, String[]> layout) {
		Pnml pnml = new Pnml();
		pnml.net = new Net();
		Net net = new Net();
		net.setId(String.valueOf(petrinet.getId()));
		pnml.net = net;

		Page page = new Page();
		Set<petrinet.Place> set = petrinet.getAllPlaces();
		List<Place> places = new ArrayList<Place>();
		for (petrinet.Place place : set) {
			Place newPlace = new Place();
			newPlace.setId(String.valueOf(place.getId()));
			System.out.println();
			PlaceName placeName = new PlaceName();
			placeName.setText(place.getName());
			newPlace.setPlaceName(placeName);
			InitialMarking initm = new InitialMarking();
			initm.setText(String.valueOf(place.getMark()));
			newPlace.setInitialMarking(initm);
			Graphics location = new Graphics();
			Position position = new Position();
			position.setX(layout.get(newPlace.getId())[0]);
			position.setY(layout.get(newPlace.getId())[1]);

			List<Position> positionList = new ArrayList<Position>();
			positionList.add(position);
			location.setPosition(positionList);
			places.add(newPlace);

		}

		Set<petrinet.Arc> arcs = petrinet.getAllArcs();
		List<Arc> newArcs = new ArrayList<Arc>();
		for (petrinet.Arc arc : arcs) {
			Arc newArc = new Arc();
			Place endPlace = null;
			for (Place p : places) {
				if (p.getId().equals(String.valueOf(arc.getEnd().getId()))) {
					endPlace = p;
					break;
				}
			}
			newArc.setTarget(endPlace.getId());

			Place startPlace = null;
			for (Place p : places) {
				if (p.getId().equals(String.valueOf(arc.getStart().getId()))) {
					startPlace = p;
					break;
				}
			}
			newArc.setSource(startPlace.getId());

			Inscription i = new Inscription();
			i.setText(arc.getName());
			newArc.setInscription(i);

			newArc.setId(String.valueOf(arc.getId()));

		}

		return pnml;
	}

	static private Point2D positionToPoint2D(List<Position> pos) {
		return new Point2D.Double(Double.parseDouble(pos.get(0).x),
				Double.parseDouble(pos.get(0).y));
	}

	/** works */
	static public boolean convertToPetrinet(Pnml pnml,
			IPetrinetPersistence handler) {
		try {
			// create petrinet
			int petrinetID = handler.createPetrinet();

			// IPetrinet ipetrinet=PetrinetComponent.getPetrinet();
			// Petrinet petrinet=ipetrinet.createPetrinet();
			// Strings sind die IDsfür die objekte AUS der Pnml
			Map<String, petrinet.INode> placesAndTransis = new HashMap<String, petrinet.INode>();
			Map<String, petrinet.Arc> arcs = new HashMap<String, petrinet.Arc>();

			// create places
			for (Place place : pnml.getNet().page.place) {
				INode realPlace = handler.createPlace(petrinetID,
						positionToPoint2D(place.getGraphics().getPosition()));
				handler.setPname(petrinetID, realPlace, place.getPlaceName()
						.getText());
				handler.setMarking(petrinetID, realPlace,
						Integer.parseInt(place.getInitialMarking().getText()));
				placesAndTransis.put(place.getId(), realPlace);
			}

			// create transitions
			for (Transition trans : pnml.getNet().getPage().getTransition()) {
				INode realTransition = handler.createTransition(petrinetID,
						positionToPoint2D(trans.getGraphics().getPosition()));

				handler.setTname(petrinetID, realTransition, trans.getTransitionName().getText());
				handler.setTlb(petrinetID, realTransition, trans.getTransitionLabel().getText());
				
				placesAndTransis.put(trans.getId(), realTransition);
				
				
				/*
				 * petrinet.Transition
				 * transition=petrinet.createTransition(trans.getId(), null);
				 * transition.setName(trans.getTransitionName().text); //
				 * transition.setTlb(trans.getTransitionLabel().getText());
				 * 
				 * placesAndTransis.put(trans.getId(), transition);
				 * System.out.println("addet Transition: id:"+ trans.getId()
				 * +",name: "+ trans.getTransitionName().text );
				 */
			}

			// create arcs
			for (Arc arc : pnml.getNet().getPage().getArc()) {
				/*
				 * Set<petrinet.Place> arcsFromPetrinet=petrinet.getAllPlaces();
				 * 
				 * System.out.println("Source: "+arc.getSource());
				 * System.out.println("Target: "+arc.getTarget()); petrinet.Arc
				 * arc2=petrinet.createArc(arc.getId(),placesAndTransis.get(arc.
				 * getSource()), placesAndTransis.get(arc.getTarget()));
				 * arc2.setEnd(placesAndTransis.get(arc.getTarget()));
				 * arc2.setStart(placesAndTransis.get(arc.getSource()));
				 * arcs.put(arc.id, arc2);
				 */
			}
			return true;
		} catch (EngineException e) {
			e.printStackTrace();
			return false;
		}
	}

}
