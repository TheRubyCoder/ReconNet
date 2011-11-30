package persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import petrinet.IPetrinet;
import petrinet.Petrinet;
import petrinet.PetrinetComponent;


public class Converter {
	
	/** not working yet*/
	public Pnml convertToPnml(Petrinet petrinet, Map<String, String[]>layout){
		Pnml pnml=new Pnml();
		pnml.net=new Net();
		Net net=new Net();
		net.setId(String.valueOf(petrinet.getId()));
		pnml.net=net;
		
		Page page=new Page();
		Set<petrinet.Place> set=petrinet.getAllPlaces();
		List<Place> places=new ArrayList<Place>();
		for(petrinet.Place place : set){
			Place newPlace=new Place();
			newPlace.setId(String.valueOf(place.getId()));
			PlaceName placeName=new PlaceName();
			placeName.setText(place.getName());
			newPlace.setPlaceName(placeName);
			
			
		}
		
		
		return pnml;
	}
	/** works*/
	public Petrinet convertToPetrinet(Pnml pnml){
		IPetrinet ipetrinet=PetrinetComponent.getPetrinet();
		Petrinet petrinet=ipetrinet.createPetrinet();
		//Strings sind die IDsfür die objekte AUS der Pnml
		Map<String, petrinet.INode> placesAndTransis=new HashMap<String, petrinet.INode>();
		Map<String, petrinet.Arc> 	   arcs=new HashMap<String, petrinet.Arc>();
		
		//create places
		for(Place place:pnml.getNet().page.place){

			petrinet.Place place2=petrinet.createPlace(place.getPlaceName().text);
			place2.setMark(Integer.valueOf(place.getInitialMarking().getText()));
			place2.setName(place.getPlaceName().getText());
			placesAndTransis.put(place.getId(), place2);
			

		}
		
	
		
		
		for(Transition trans:pnml.getNet().getPage().getTransition()){
			petrinet.Transition transition=petrinet.createTransition(trans.getId(), null);
			transition.setName(trans.getTransitionName().text);
		//	transition.setTlb(trans.getTransitionLabel().getText());
			
			placesAndTransis.put(trans.getId(), transition);
			System.out.println("addet Transition: id:"+ trans.getId() +",name: "+ trans.getTransitionName().text );
		}
		
		for(Arc arc:pnml.getNet().getPage().getArc()){
			
			Set<petrinet.Place> arcsFromPetrinet=petrinet.getAllPlaces();
			
			System.out.println("Source: "+arc.getSource());
			System.out.println("Target: "+arc.getTarget());
			petrinet.Arc arc2=petrinet.createArc(arc.getId(),placesAndTransis.get(arc.getSource()), placesAndTransis.get(arc.getTarget()));
			arcs.put(arc.id, arc2);
			
		}
		
	
		
		
		return petrinet;
	}
	


}
