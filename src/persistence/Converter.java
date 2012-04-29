package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import petrinet.INode;
import petrinet.Petrinet;
import transformation.ITransformation;
import transformation.Rule;
import transformation.TransformationComponent;
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
	static public Pnml convertPetrinetToPnml(Petrinet petrinet,
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

		/** Contains the created INode object for each XML-id */
		Map<String, INode> idToINodeInL = new HashMap<String, INode>();
		Map<String, INode> idToINodeInK = new HashMap<String, INode>();
		Map<String, INode> idToINodeInR = new HashMap<String, INode>();
		try {
			addPlacesToRule(id, lPlaces, kPlaces, rPlaces, handler,
					idToINodeInL, idToINodeInK, idToINodeInR);
			addTransitionsToRule(id, lTransis, kTransis, rTransis, handler,
					idToINodeInL, idToINodeInK, idToINodeInR);
			fillMapsWithMappings(id, idToINodeInL, idToINodeInK, idToINodeInR);
			addArcsToTule(id, lArcs, kArcs, rArcs, handler, idToINodeInL,
					idToINodeInK, idToINodeInR);
		} catch (EngineException e) {
			PopUp.popError(e);
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * After Places and Rules have been added, there are only
	 * String-id-to-INode-mappings for the original inserted nodes, but not for
	 * the ones that were inserted automatically For beeing able to properly add
	 * arcs to the rule, we need those mappings
	 */
	private static void fillMapsWithMappings(int ruleId,
			Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
			Map<String, INode> idToINodeInR) {
		ITransformation transformation = TransformationComponent
				.getTransformation();
		// fill with mappings of Ls nodes
		for (String xmlId : idToINodeInL.keySet()) {
			INode node = idToINodeInL.get(xmlId);
			List<INode> mappings = transformation.getMappings(ruleId, node);
			for (int i = 0; i < mappings.size(); i++) {
				INode respectiveNode = mappings.get(i);
				if (respectiveNode != null) {
					if (i == 0) {
						idToINodeInL.put(xmlId, respectiveNode);
					} else if (i == 1) {
						idToINodeInK.put(xmlId, respectiveNode);
					} else if (i == 2) {
						idToINodeInR.put(xmlId, respectiveNode);
					}
				}
			}
		}
		// fill with mappings of Ks nodes
		for (String xmlId : idToINodeInK.keySet()) {
			INode node = idToINodeInK.get(xmlId);
			List<INode> mappings = transformation.getMappings(ruleId, node);
			for (int i = 0; i < mappings.size(); i++) {
				INode respectiveNode = mappings.get(i);
				if (respectiveNode != null) {
					if (i == 0) {
						idToINodeInL.put(xmlId, respectiveNode);
					} else if (i == 1) {
						idToINodeInK.put(xmlId, respectiveNode);
					} else if (i == 2) {
						idToINodeInR.put(xmlId, respectiveNode);
					}
				}
			}
		}
		// fill with mappings of Rs nodes
		for (String xmlId : idToINodeInR.keySet()) {
			INode node = idToINodeInR.get(xmlId);
			List<INode> mappings = transformation.getMappings(ruleId, node);
			for (int i = 0; i < mappings.size(); i++) {
				INode respectiveNode = mappings.get(i);
				if (respectiveNode != null) {
					if (i == 0) {
						idToINodeInL.put(xmlId, respectiveNode);
					} else if (i == 1) {
						idToINodeInK.put(xmlId, respectiveNode);
					} else if (i == 2) {
						idToINodeInR.put(xmlId, respectiveNode);
					}
				}
			}
		}
	}

	/**
	 * Adds all arcs from the XML to the rule object, using the engine handler
	 * 
	 * @param idToINodeInR
	 * @param idToINodeInK
	 */
	private static void addArcsToTule(int id, List<Arc> lArcs, List<Arc> kArcs,
			List<Arc> rArcs, IRulePersistence handler,
			Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
			Map<String, INode> idToINodeInR) throws EngineException {
		/*
		 * All arcs must be in K. To determine where the arcs must be added, we
		 * have to find out: Are they also in L AND in K or just in one of them?
		 * And if they are only in one of them - in which?
		 */
		for (Arc arc : kArcs) {
			RuleNet toAddto;
			INode source;
			INode target;
			if (getIdsOfArcsList(lArcs).contains(arc.getId())) {
				if (getIdsOfArcsList(rArcs).contains(arc.getId())) {
					toAddto = RuleNet.K;
					source = idToINodeInK.get(arc.getSource());
					target = idToINodeInK.get(arc.getTarget());
				} else {
					toAddto = RuleNet.L;
					source = idToINodeInL.get(arc.getSource());
					target = idToINodeInL.get(arc.getTarget());
				}
			} else {
				toAddto = RuleNet.R;
				source = idToINodeInR.get(arc.getSource());
				target = idToINodeInR.get(arc.getTarget());
			}
			handler.createArc(id, toAddto, source, target);
		}

	}

	/**
	 * Adds places to the rule, writing the mappings of created places into the
	 * resprective maps (last 3 parameters)
	 * 
	 * @param id
	 * @param lPlaces
	 * @param kPlaces
	 * @param rPlaces
	 * @param handler
	 * @param idToINodeInL
	 * @param idToINodeInK
	 * @param idToINodeInR
	 * @return
	 * @throws EngineException
	 */
	private static Map<String, INode> addPlacesToRule(int id,
			List<Place> lPlaces, List<Place> kPlaces, List<Place> rPlaces,
			IRulePersistence handler, Map<String, INode> idToINodeInL,
			Map<String, INode> idToINodeInK, Map<String, INode> idToINodeInR)
			throws EngineException {
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
			handler.setPlaceColor(id, createdPlace, place.getGraphics()
					.getColor().toAWTColor());
			handler.setPname(id, createdPlace, place.getPlaceName().getText());
			handler.setMarking(id, createdPlace,
					Integer.valueOf(place.getInitialMarking().getText()));
			if (toAddto == RuleNet.L) {
				idToINodeInL.put(place.id, createdPlace);
			} else if (toAddto == RuleNet.K) {
				idToINodeInK.put(place.id, createdPlace);
			} else {
				idToINodeInR.put(place.id, createdPlace);
			}
		}
		return result;
	}

	private static Map<String, INode> addTransitionsToRule(int id,
			List<Transition> lTransitions, List<Transition> kTransition,
			List<Transition> rTransition, IRulePersistence handler,
			Map<String, INode> idToINodeInL, Map<String, INode> idToINodeInK,
			Map<String, INode> idToINodeInR) throws EngineException {
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
			handler.setTlb(id, createdTransition, transition
					.getTransitionLabel().getText());
			handler.setTname(id, createdTransition, transition
					.getTransitionName().getText());
			if (toAddto == RuleNet.L) {
				idToINodeInL.put(transition.id, createdTransition);
			} else if (toAddto == RuleNet.K) {
				idToINodeInK.put(transition.id, createdTransition);
			} else {
				idToINodeInR.put(transition.id, createdTransition);
			}
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
			private static final long serialVersionUID = 8434245017694015611L;

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
				List<Position> positions = new ArrayList<Position>();
				positions.add(pos);
				graphics.setPosition(positions);

				// Color
				Color c = new Color();
				c.setR(String.valueOf(map.get(p).getColor().getRed()));
				c.setG(String.valueOf(map.get(p).getColor().getGreen()));
				c.setB(String.valueOf(map.get(p).getColor().getBlue()));
				graphics.setColor(c);

				newPlace.setGraphics(graphics);

				// Marking
				InitialMarking initM = new InitialMarking();
				initM.setText(String.valueOf(p.getMark()));

				newPlace.setInitialMarking(initM);

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
				rnw.setText(t.getRnw().toGUIString());

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
