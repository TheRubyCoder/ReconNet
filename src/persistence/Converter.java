package persistence;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.NodeLayoutAttribute;
import engine.handler.RuleNet;
import engine.handler.rule.RuleHandler;
import engine.ihandler.IPetrinetManipulation;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRulePersistence;
import exceptions.EngineException;


import petrinet.INode;
import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;
import transformation.Rule;

public class Converter {

	/** not working yet */
	static public Pnml convertToPnml(Petrinet petrinet,
			Map<String, String[]> layout) {
		Pnml pnml = new Pnml();
		pnml.net = new ArrayList<Net>();
		Net xmlnet = new Net();
		xmlnet.setId(String.valueOf(petrinet.getId()));
		pnml.net.add(xmlnet);

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
	static public int convertToPetrinet(Pnml pnml, IPetrinetPersistence handler) {
		try {
			// create petrinet
			int petrinetID = handler.createPetrinet();

			// IPetrinet ipetrinet=PetrinetComponent.getPetrinet();
			// Petrinet petrinet=ipetrinet.createPetrinet();
			// Strings sind die IDsfür die objekte AUS der Pnml
			Map<String, petrinet.INode> placesAndTransis = new HashMap<String, petrinet.INode>();
			Map<String, petrinet.Arc> arcs = new HashMap<String, petrinet.Arc>();

			// create places
			for (Place place : pnml.getNet().get(0).page.place) {
				INode realPlace = handler.createPlace(petrinetID,
						positionToPoint2D(place.getGraphics().getPosition()));
				handler.setPname(petrinetID, realPlace, place.getPlaceName()
						.getText());
				handler.setMarking(petrinetID, realPlace,
						Integer.parseInt(place.getInitialMarking().getText()));
				placesAndTransis.put(place.getId(), realPlace);
			}

			// create transitions
			for (Transition trans : pnml.getNet().get(0).getPage().getTransition()) {
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
			for (Arc arc : pnml.getNet().get(0).getPage().getArc()) {
				handler.createArc(petrinetID, placesAndTransis.get(arc.source), placesAndTransis.get(arc.target));
				
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
	
	public static boolean convertToRule(Pnml pnml, IRulePersistence handler) {
		int id=handler.createRule();
		
		
		//Todo: make nettype independent from position in rnml-file
		Net lNet= pnml.getNet().get(0);
		Net kNet=pnml.getNet().get(1);
		Net rNet=pnml.getNet().get(2);
		
		List<Place> lPlaces=lNet.getPage().getPlace();
		List<Transition> lTransis=lNet.getPage().getTransition();
		List<Arc> lArcs=lNet.getPage().getArc();
		
		List<Place> kPlaces=kNet.getPage().getPlace();
		List<Transition> kTransis=kNet.getPage().getTransition();
		List<Arc> kArcs=kNet.getPage().getArc();
		
		List<Place> rPlaces=rNet.getPage().getPlace();
		List<Transition> rTransis=rNet.getPage().getTransition();
		List<Arc> rArcs=rNet.getPage().getArc();
		
		//INode node= handler.createPlace(id, RuleNet.L, null);
		
		
		return false;
	}
	
	public static Pnml convertRuleToPnml ( Rule rule, Map<INode, NodeLayoutAttribute> map){
		Pnml pnml=new Pnml();
		
		pnml.net = new ArrayList<Net>();
		Net lNet = createNet(rule.getL(), map, RuleNet.L);
		Net kNet = createNet(rule.getK(), map, RuleNet.K);
		Net rNet = createNet(rule.getR(), map, RuleNet.R);
		
		List<Net>nets=new ArrayList<Net>();
		pnml.setNet(nets);
		
		
		return pnml;
	}
	
	private static Net createNet(Petrinet petrinet, Map<INode, NodeLayoutAttribute> map, RuleNet type) {
		Net net=new Net();
		Page page=new Page();
		net.setId(String.valueOf(petrinet.getId()));
		net.setNettype(type.name());
	
	
		
		Set<petrinet.Arc> arcs=petrinet.getAllArcs();
		Set<petrinet.Place> places=petrinet.getAllPlaces();
		Set<petrinet.Transition> transis=petrinet.getAllTransitions();
		
		List<Arc> listArcs= new ArrayList<Arc>();
		List<Place> listPlace=  new ArrayList<Place>();
		List<Transition> listTrans= new ArrayList<Transition>();
		
		
		try{
		//inserting places
		for(petrinet.Place p:places){
			Place newPlace=new Place();
			
			//name
			PlaceName name=new PlaceName();
			name.setText(p.getName());
			newPlace.setPlaceName(name);
			
			//id
			newPlace.setId(String.valueOf(p.getId()));
			
			//Coordinates
			Graphics graphics=new Graphics();
			Position pos=new Position();
			
			pos.setX(String.valueOf(map.get(p).getCoordinate().getX()));
			pos.setY(String.valueOf(map.get(p).getCoordinate().getY()));
	
			
			List<Position> positions=new ArrayList<Position>();
			positions.add(pos);
			graphics.setPosition(positions);
	
			newPlace.setGraphics(graphics);
			
			InitialMarking initM=new InitialMarking();
			initM.setText(String.valueOf(p.getMark()));
			
			listPlace.add(newPlace);
			
		}
		
		//inserting Transitions
		for(petrinet.Transition t:transis){
			Transition transi=new Transition();
			transi.setId(String.valueOf(t.getId()));
			
			TransitionName name=new TransitionName();
			name.setText(t.getName());
			transi.setTransitionName(name);
			
			//Coordinates
			Graphics graphics=new Graphics();
			Dimension d=new Dimension();
			Position pos=new Position();
		//	AbstractLayout<INode, petrinet.Arc> layout = handler.getJungLayout(t.getId(), type);
			
	
			pos.setX(String.valueOf(map.get(t).getCoordinate().getX()));
			pos.setY(String.valueOf(map.get(t).getCoordinate().getY()));
			
		
			
			List<Position> positions=new ArrayList<Position>();
			positions.add(pos);
			graphics.setPosition(positions);
			
			transi.setGraphics(graphics);
			
			
			//Transitionlabel
			TransitionLabel label=new TransitionLabel();
			label.setText(t.getTlb());
			transi.setTransitionLabel(label);
			
			TransitionRenew rnw=new TransitionRenew();
			rnw.setText(t.getRnw().renew(label.getText()));
			
			transi.setTransitionRenew(rnw);
			
			listTrans.add(transi);
		}
		
		//inserting arcs
		for(petrinet.Arc a:arcs){
			Arc arc=new Arc();
			arc.setId(String.valueOf(a.getId()));
			
			
			//Coordinates
			Graphics graphics=new Graphics();
			Dimension d=new Dimension();
			Position pos=new Position();
			
			List<Dimension> dimensions=new ArrayList<Dimension>();
			dimensions.add(d);
			graphics.setDimension(dimensions);
			
			List<Position> positions=new ArrayList<Position>();
			positions.add(pos);
			graphics.setPosition(positions);
			
			arc.setGraphics(graphics);
			
			//inscripten = name!
			Inscription i=new Inscription();
			i.setText(a.getName());
			arc.setInscription(i);
			
			//source and target
			arc.setSource(String.valueOf(a.getStart().getId()));
			arc.setTarget(String.valueOf(a.getEnd().getId()));
			
			listArcs.add(arc);
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		page.setArc(listArcs);
		page.setPlace(listPlace);
		page.setTransition(listTrans);
		
		
		
		return net;
	}

	

}
