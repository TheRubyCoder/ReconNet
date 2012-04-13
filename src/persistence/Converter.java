package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import petrinet.INode;
import petrinet.Petrinet;
import transformation.Rule;
import engine.attribute.NodeLayoutAttribute;
import engine.handler.RuleNet;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;
import gui2.PopUp;

/**
 * This class provides converting methods which are used by persistence class to
 * convert from and to JAXB-classes.
 * 
 * */
public class Converter {
	private static boolean test = true;

	/** not working yet */
	static public Pnml convertToPnml(Petrinet petrinet,
			Map<String, String[]> layout) {
		Pnml pnml = new Pnml();
		pnml.net = new ArrayList<Net>();
		Net xmlnet = new Net();
		xmlnet.setId(String.valueOf(petrinet.getId()));
		pnml.net.add(xmlnet);

		Page page = new Page();
		xmlnet.setPage(page);
		Set<petrinet.Place> set = petrinet.getAllPlaces();
		List<Place> places = new ArrayList<Place>();
		page.setPlace(places);
		for (petrinet.Place place : set) {
			Place newPlace = new Place();
			newPlace.setId(String.valueOf(place.getId()));
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
			Color c = new Color();
			c.setR(layout.get(newPlace.getId())[2]);
			c.setG(layout.get(newPlace.getId())[3]);
			c.setB(layout.get(newPlace.getId())[4]);
			location.setColor(c);

			List<Position> positionList = new ArrayList<Position>();
			positionList.add(position);
			location.setPosition(positionList);
			newPlace.setGraphics(location);
			places.add(newPlace);

		}

		Set<petrinet.Transition> transis = petrinet.getAllTransitions();
		List<Transition> Tlist = new ArrayList<Transition>();
		for (petrinet.Transition t : transis) {
			Transition transi = new Transition();
			transi.setId(String.valueOf(t.getId()));

			TransitionName name = new TransitionName();
			name.setText(t.getName());
			transi.setTransitionName(name);

			// Coordinates
			Graphics graphics = new Graphics();
			Dimension d = new Dimension();
			Position pos = new Position();
			pos.setX(layout.get(transi.getId())[0]);

			pos.setY(layout.get(transi.getId())[1]);
			// AbstractLayout<INode, petrinet.Arc> layout =
			// handler.getJungLayout(t.getId(), type);

			// Todo Positionstuff
			// pos.setX(String.valueOf(layout.get(t).getCoordinate().getX()));
			// pos.setY(String.valueOf(map.get(t).getCoordinate().getY()));

			List<Position> positions = new ArrayList<Position>();
			positions.add(pos);
			graphics.setPosition(positions);

			transi.setGraphics(graphics);

			// Transitionlabel
			TransitionLabel label = new TransitionLabel();
			label.setText(t.getTlb());
			transi.setTransitionLabel(label);

			TransitionRenew rnw = new TransitionRenew();
			rnw.setText(t.getRnw().renew(label.getText()));

			transi.setTransitionRenew(rnw);

			Tlist.add(transi);
		}
		pnml.getNet().get(0).page.setTransition(Tlist);

		Set<petrinet.Arc> arcs = petrinet.getAllArcs();
		List<Arc> newArcs = new ArrayList<Arc>();
		for (petrinet.Arc arc : arcs) {
			Arc newArc = new Arc();
			String arcEnd = null;

			for (Place p : places) {
				if (p.getId().equals(String.valueOf(arc.getEnd().getId()))) {
					arcEnd = p.getId();
					break;
				}
			}

			if (arcEnd == null) {
				for (Transition t : Tlist) {
					if (t.getId().equals(String.valueOf(arc.getEnd().getId()))) {
						arcEnd = t.getId();
						break;
					}
				}
			}

			newArc.setTarget(arcEnd);

			String arcStart = null;

			for (Place p : places) {
				if (p.getId().equals(String.valueOf(arc.getStart().getId()))) {
					arcStart = p.getId();
					break;
				}
			}

			if (arcStart == null) {
				for (Transition t : Tlist) {
					if (t.getId()
							.equals(String.valueOf(arc.getStart().getId()))) {
						arcStart = t.getId();
						break;
					}
				}
			}

			newArc.setSource(arcStart);
			System.out.println("creating arc source:" + arcStart + " target:"
					+ arcEnd);
			Inscription i = new Inscription();
			i.setText(arc.getName());
			newArc.setInscription(i);

			newArc.setId(String.valueOf(arc.getId()));
			newArcs.add(newArc);
		}
		pnml.getNet().get(0).getPage().setArc(newArcs);
		return pnml;
	}

	static private Point2D positionToPoint2D(List<Position> pos) {
		return new Point2D.Double(Double.parseDouble(pos.get(0).x),
				Double.parseDouble(pos.get(0).y));
	}

	/** works */
	static public int convertToPetrinet(Pnml pnml, IPetrinetPersistence handler) {
		int petrinetID = -1;
		try {
			// create petrinet
			petrinetID = handler.createPetrinet();

			// IPetrinet ipetrinet=PetrinetComponent.getPetrinet();
			// Petrinet petrinet=ipetrinet.createPetrinet();
			// Strings sind die IDsfür die objekte AUS der Pnml
			Map<String, petrinet.INode> placesAndTransis = new HashMap<String, petrinet.INode>();
			Map<String, petrinet.Arc> arcs = new HashMap<String, petrinet.Arc>();

			// create places
			for (Place place : pnml.getNet().get(0).page.place) {
				INode realPlace;
				if (place.getGraphics() != null
						&& !place.getGraphics().getPosition().isEmpty()) {
					realPlace = handler
							.createPlace(petrinetID, positionToPoint2D(place
									.getGraphics().getPosition()));
					handler.setPlaceColor(
							petrinetID,
							realPlace,
							new java.awt.Color(Integer.parseInt(place
									.getGraphics().getColor().getR()), Integer
									.parseInt(place.getGraphics().getColor()
											.getG()), Integer.parseInt(place
									.getGraphics().getColor().getB())));
				} else {
					realPlace = handler.createPlace(
							petrinetID,
							new Point2D.Double(Math.random() * 10, Math
									.random() * 10));
				}
				handler.setPname(petrinetID, realPlace, place.getPlaceName()
						.getText());
				handler.setMarking(petrinetID, realPlace,
						Integer.parseInt(place.getInitialMarking().getText()));
				placesAndTransis.put(place.getId(), realPlace);
			}

			// create transitions
			for (Transition trans : pnml.getNet().get(0).getPage()
					.getTransition()) {
				INode realTransition;
				if (trans.getGraphics() != null
						&& !trans.getGraphics().getPosition().isEmpty()) {
					System.out.println(trans.getTransitionName().getText());
					System.out
							.println(trans.getGraphics().getPosition().size());
					realTransition = handler
							.createTransition(petrinetID,
									positionToPoint2D(trans.getGraphics()
											.getPosition()));
				} else {
					realTransition = handler.createTransition(
							petrinetID,
							new Point2D.Double(Math.random() * 100, Math
									.random() * 100));
				}

				handler.setTname(petrinetID, realTransition, trans
						.getTransitionName().getText());
				handler.setTlb(petrinetID, realTransition, trans
						.getTransitionLabel().getText());
				if (placesAndTransis.containsKey(trans.getId()))
					System.out.println("AHHHHHH!");
				placesAndTransis.put(trans.getId(), realTransition);

			}

			// create arcs
			for (Arc arc : pnml.getNet().get(0).getPage().getArc()) {
				handler.createArc(petrinetID, placesAndTransis.get(arc.source),
						placesAndTransis.get(arc.target));

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
			return petrinetID;
		} catch (EngineException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Creates a rule with the <tt>handler</tt> that is defined within
	 * <tt>pnml</tt>
	 * 
	 * @return id of created rule
	 */
	public static int convertToRule(Pnml pnml, IRulePersistence handler) {
		int id = handler.createRule();
		// try {

		// Todo: make nettype independent from position in rnml-file
		Net lNet = pnml.getNet().get(0);
		Net kNet = pnml.getNet().get(1);
		Net rNet = pnml.getNet().get(2);

		List<Place> lPlaces = lNet.getPage().getPlace();
		List<Transition> lTransis = lNet.getPage().getTransition();
		List<Arc> lArcs = lNet.getPage().getArc();

		List<Place> kPlaces = kNet.getPage().getPlace();
		List<Transition> kTransis = kNet.getPage().getTransition();
		List<Arc> kArcs = kNet.getPage().getArc();

		List<Place> rPlaces = rNet.getPage().getPlace();
		List<Transition> rTransis = rNet.getPage().getTransition();
		List<Arc> rArcs = rNet.getPage().getArc();

		Map<String, INode> idToINode = new HashMap<String, INode>();
		try {
			Map<String, INode> mappedPlaces = addPlacesToRule(id, lPlaces,
					kPlaces, rPlaces, handler);
			Map<String, INode> mappedTransitions = addTransitionsToRule(id,
					lTransis, kTransis, rTransis, handler);
			idToINode.putAll(mappedPlaces);
			idToINode.putAll(mappedTransitions);
			addArcsToTule(id, lArcs, kArcs, rArcs, handler, idToINode);
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}

		//
		// List<?>[] arrayWithPlaceLists = { kPlaces, lPlaces, rPlaces };
		// List<?>[] arrayWithTransLists = { kTransis, lTransis, rTransis };
		// List<?>[] arrayWithArcLists = { kArcs, lArcs, rArcs };
		//
		// Map<String, INode> idToINode = new HashMap<String, INode>();
		//
		// for (int i = 0; i < arrayWithPlaceLists.length; i++) {
		// RuleNet rulenet = null;
		// switch (i) {
		// case 0:
		// rulenet = RuleNet.K;
		// if (test)
		// System.out.println("K");
		// break;
		// case 1:
		// rulenet = RuleNet.L;
		// if (test)
		// System.out.println("L");
		// break;
		// case 2:
		// rulenet = RuleNet.R;
		// if (test)
		// System.out.println("R");
		// }
		//
		// for (Place p : (List<Place>) arrayWithPlaceLists[i]) {
		// INode node = handler.createPlace(id, rulenet,
		// positionToPoint2D(p.getGraphics().getPosition()));
		// node.setName(p.getPlaceName().getText());
		// java.awt.Color c = new java.awt.Color(255, 255, 255);
		// if (p.graphics.getColor() != null) {
		// c = new java.awt.Color(Integer.parseInt(p.getGraphics()
		// .getColor().getR()), Integer.parseInt(p
		// .getGraphics().getColor().getG()),
		// Integer.parseInt(p.getGraphics().getColor()
		// .getB()));
		// if (test)
		// System.out.println("found color: " + c);
		// }
		// handler.setPlaceColor(id, node, c);
		// idToINode.put(p.getId(), node);
		//
		// if (rulenet == RuleNet.K) {
		// Iterator<Place> iter = lPlaces.iterator();
		// while (iter.hasNext()) {
		// Place tempPlace = iter.next();
		// if (tempPlace.getId().equals(p.getId()))
		// iter.remove();
		// }
		//
		// iter = rPlaces.iterator();
		// while (iter.hasNext()) {
		// Place tempPlace = iter.next();
		// if (tempPlace.getId().equals(p.getId()))
		// iter.remove();
		// }
		// }
		// }
		//
		// if (test)
		// System.out.println("Rulenet: " + rulenet.name()
		// + " map id -> inode (only Places): \n" + idToINode);
		//
		// for (Transition t : (List<Transition>) arrayWithTransLists[i]) {
		// INode node = handler.createTransition(id, rulenet,
		// positionToPoint2D(t.getGraphics().getPosition()));
		// node.setName(t.getTransitionName().getText());
		// handler.setTlb(id, node, t.getTransitionLabel().getText());
		// idToINode.put(t.getId(), node);
		//
		// if (rulenet == RuleNet.K) {
		// Iterator<Transition> iter = lTransis.iterator();
		// while (iter.hasNext()) {
		// Transition tempT = iter.next();
		// if (tempT.getId().equals(t.getId()))
		// iter.remove();
		// }
		// iter = rTransis.iterator();
		// while (iter.hasNext()) {
		// Transition tempT = iter.next();
		// if (tempT.getId().equals(t.getId()))
		// iter.remove();
		// }
		// }
		// }
		//
		// if (test)
		// System.out.println("Rulenet: " + rulenet.name()
		// + " map id -> inode(with Transistions): \n"
		// + idToINode);
		//
		// for (Arc a : (List<Arc>) arrayWithArcLists[i]) {
		// System.out.println(idToINode.get(a.getSource()) + " -> "
		// + idToINode.get(a.getTarget()));
		// INode node = handler.createArc(id, rulenet,
		// idToINode.get(a.getSource()),
		// idToINode.get(a.getTarget()));
		// node.setName(a.getInscription().getText());
		// if (rulenet == RuleNet.K) {
		// Iterator<Arc> iter = lArcs.iterator();
		// while (iter.hasNext()) {
		// Arc temp = iter.next();
		// if (temp.getId().equals(a.getId()))
		// iter.remove();
		// }
		// iter = rArcs.iterator();
		// while (iter.hasNext()) {
		// Arc temp = iter.next();
		// if (temp.getId().equals(a.getId()))
		// iter.remove();
		// }
		// }
		// }
		//
		// if (test) {
		// System.out.println();
		// }
		//
		// }
		//
		// } catch (EngineException e) {
		//
		// e.printStackTrace();
		// return -1;
		// }

		return id;
	}

	private static void addArcsToTule(int id, List<Arc> lArcs, List<Arc> kArcs,
			List<Arc> rArcs, IRulePersistence handler,
			Map<String, INode> idToINode) throws EngineException {
		/*
		 * All arcs must be in K. To determine where the arcs must be added, we
		 * have to find out: Are they also in L AND in K or just in one of them?
		 * And if they are only in one of them - in which?
		 */
		for (Arc arc : kArcs) {
			RuleNet toAddto;
			if (getIdsOfArcsList(lArcs).contains(arc.getId())) {
				if (getIdsOfArcsList(rArcs).contains(arc.getId())) {
					toAddto = RuleNet.K;
				} else {
					toAddto = RuleNet.L;
				}
			} else {
				toAddto = RuleNet.R;
			}
			handler.createArc(id, toAddto, idToINode.get(arc.getSource()),
					idToINode.get(arc.getTarget()));
		}

	}

	private static Map<String, INode> addPlacesToRule(int id,
			List<Place> lPlaces, List<Place> kPlaces, List<Place> rPlaces,
			IRulePersistence handler) throws EngineException {
		Map<String, INode> result = new HashMap<String, INode>();
		/*
		 * All places must be in K. To determine where the places must be added,
		 * we have to find out: Are they also in L AND in K or just in one of
		 * them? And if they are only in one of them - in which?
		 */
		for (Place place : kPlaces) {
			RuleNet toAddto;
			if (getIdsOfPlaceList(lPlaces).contains(place.getId())) {
				if (getIdsOfPlaceList(rPlaces).contains(place.getId())) {
					toAddto = RuleNet.K;
				} else {
					toAddto = RuleNet.L;
				}
			} else {
				toAddto = RuleNet.R;
			}
			INode createdPlace = handler.createPlace(id, toAddto,
					positionToPoint2D(place.getGraphics().getPosition()));
			result.put(place.getId(), createdPlace);
//			FIXME: Uncommented due to nullpointer: initialMarking and Color are null (maybe not safed?)
//			handler.setMarking(id, (INode) place,
//					Integer.valueOf(place.getInitialMarking().getText()));
//			handler.setPlaceColor(id, (INode) place, place.getGraphics()
//					.getColor().toAWTColor());
//			handler.setPname(id, (INode) place, place.getPlaceName().getText());
		}
		return result;
	}

	private static Map<String, INode> addTransitionsToRule(int id,
			List<Transition> lTransitions, List<Transition> kTransition,
			List<Transition> rTransition, IRulePersistence handler)
			throws EngineException {
		Map<String, INode> result = new HashMap<String, INode>();
		/*
		 * All Transitions must be in K. To determine where the transitions must
		 * be added, we have to find out: Are they also in L AND in K or just in
		 * one of them? And if they are only in one of them - in which?
		 */
		for (Transition transition : kTransition) {
			RuleNet toAddto;
			if (getIdsOfTransitionList(lTransitions).contains(
					transition.getId())) {
				if (getIdsOfTransitionList(rTransition).contains(
						transition.getId())) {
					toAddto = RuleNet.K;
				} else {
					toAddto = RuleNet.L;
				}
			} else {
				toAddto = RuleNet.R;
			}
			INode createdTransition = handler.createTransition(id, toAddto,
					positionToPoint2D(transition.getGraphics().getPosition()));
			result.put(transition.getId(), createdTransition);
		}
		return result;
	}

	private static List<String> getIdsOfPlaceList(List<Place> placeList) {
		List<String> ids = new LinkedList<String>();
		for (Place place : placeList) {
			ids.add(place.getId());
		}
		return ids;
	}

	private static List<String> getIdsOfTransitionList(
			List<Transition> transitionList) {
		List<String> ids = new LinkedList<String>();
		for (Transition place : transitionList) {
			ids.add(place.getId());
		}
		return ids;
	}

	private static List<String> getIdsOfArcsList(List<Arc> arcList) {
		List<String> ids = new LinkedList<String>();
		for (Arc arc : arcList) {
			ids.add(arc.getId());
		}
		return ids;
	}

	public static Pnml convertRuleToPnml(Rule rule,
			Map<INode, NodeLayoutAttribute> map) {
		Pnml pnml = new Pnml();

		pnml.net = new ArrayList<Net>();
		final Net lNet = createNet(rule.getL(), map, RuleNet.L, rule);
		final Net kNet = createNet(rule.getK(), map, RuleNet.K, rule);
		final Net rNet = createNet(rule.getR(), map, RuleNet.R, rule);

		pnml.setNet(new ArrayList<Net>() {
			{
				add(lNet);
				add(kNet);
				add(rNet);
			}
		});

		return pnml;
	}

	private static Net createNet(Petrinet petrinet,
			Map<INode, NodeLayoutAttribute> map, RuleNet type, Rule rule) {
		Net net = new Net();
		Page page = new Page();
		net.setId(String.valueOf(petrinet.getId()));
		net.setNettype(type.name());

		net.setPage(page);

		Set<petrinet.Arc> arcs = petrinet.getAllArcs();
		Set<petrinet.Place> places = petrinet.getAllPlaces();
		Set<petrinet.Transition> transis = petrinet.getAllTransitions();

		List<Arc> listArcs = new ArrayList<Arc>();
		List<Place> listPlace = new ArrayList<Place>();
		List<Transition> listTrans = new ArrayList<Transition>();

		try {
			// inserting places
			for (petrinet.Place p : places) {
				Place newPlace = new Place();

				// id
				if (type != RuleNet.K) {
					INode correspondingNode = type == RuleNet.L ? rule
							.fromLtoK(p) : rule.fromRtoK(p);
					if (correspondingNode != null) {
						p = (petrinet.Place) correspondingNode;
					}
				}

				// name
				PlaceName name = new PlaceName();
				name.setText(p.getName());
				newPlace.setPlaceName(name);

				newPlace.setId(String.valueOf(p.getId()));

				// Coordinates
				Graphics graphics = new Graphics();
				Position pos = new Position();

				pos.setX(String.valueOf(map.get(p).getCoordinate().getX()));
				pos.setY(String.valueOf(map.get(p).getCoordinate().getY()));

				Color c = new Color();
				/** FIXME: make code work */
				// c.setR(String.valueOf(map.get(newPlace.getId()).getColor().getRed()));
				// c.setG(String.valueOf(map.get(newPlace.getId()).getColor().getGreen()));
				// c.setB(String.valueOf(map.get(newPlace.getId()).getColor().getBlue()));

				List<Position> positions = new ArrayList<Position>();
				positions.add(pos);
				graphics.setPosition(positions);

				newPlace.setGraphics(graphics);

				InitialMarking initM = new InitialMarking();
				initM.setText(String.valueOf(p.getMark()));

				listPlace.add(newPlace);

			}

			// inserting Transitions
			for (petrinet.Transition t : transis) {
				Transition transi = new Transition();

				if (type != RuleNet.K) {
					INode correspondingNode = type == RuleNet.L ? rule
							.fromLtoK(t) : rule.fromRtoK(t);
					if (correspondingNode != null) {
						t = (petrinet.Transition) correspondingNode;
					}
				}

				transi.setId(String.valueOf(t.getId()));

				TransitionName name = new TransitionName();
				name.setText(t.getName());
				transi.setTransitionName(name);

				// Coordinates
				Graphics graphics = new Graphics();
				Dimension d = new Dimension();
				Position pos = new Position();
				// AbstractLayout<INode, petrinet.Arc> layout =
				// handler.getJungLayout(t.getId(), type);

				pos.setX(String.valueOf(map.get(t).getCoordinate().getX()));
				pos.setY(String.valueOf(map.get(t).getCoordinate().getY()));

				List<Position> positions = new ArrayList<Position>();
				positions.add(pos);
				graphics.setPosition(positions);

				transi.setGraphics(graphics);

				// Transitionlabel
				TransitionLabel label = new TransitionLabel();
				label.setText(t.getTlb());
				transi.setTransitionLabel(label);

				TransitionRenew rnw = new TransitionRenew();
				rnw.setText(t.getRnw().renew(label.getText()));

				transi.setTransitionRenew(rnw);

				listTrans.add(transi);
			}

			// inserting arcs
			for (petrinet.Arc a : arcs) {
				Arc arc = new Arc();

				if (type != RuleNet.K) {
					INode correspondingNode = type == RuleNet.L ? rule
							.fromLtoK(a) : rule.fromRtoK(a);
					if (correspondingNode != null) {
						a = (petrinet.Arc) correspondingNode;
					}
				}

				arc.setId(String.valueOf(a.getId()));

				// Coordinates
				Graphics graphics = new Graphics();
				Dimension d = new Dimension();
				Position pos = new Position();

				List<Dimension> dimensions = new ArrayList<Dimension>();
				dimensions.add(d);
				graphics.setDimension(dimensions);

				List<Position> positions = new ArrayList<Position>();
				positions.add(pos);
				graphics.setPosition(positions);

				arc.setGraphics(graphics);

				// inscripten = name!
				Inscription i = new Inscription();
				i.setText(a.getName());
				arc.setInscription(i);

				// source and target
				arc.setSource(String.valueOf(a.getStart().getId()));
				arc.setTarget(String.valueOf(a.getEnd().getId()));

				listArcs.add(arc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		page.setArc(listArcs);
		page.setPlace(listPlace);
		page.setTransition(listTrans);

		return net;
	}

}
