package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import engine.handler.petrinet.PetrinetHandler;

public class TestStuff {

	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, JAXBException {
		Pnml pnml=new Pnml();
		JAXBContext context =  
	        JAXBContext.newInstance( persistence.Pnml.class , Arc.class, Converter.class, Dimension.class, Graphics.class, InitialMarking.class, Name.class, Net.class,
	        		Page.class, Place.class, PlaceName.class, Position.class, Transition.class, TransitionLabel.class, TransitionName.class, TransitionRenew.class);
	    Unmarshaller m = context.createUnmarshaller();
	    
	    m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
	 //   m.setProperty( Unmarshaller., Boolean.TRUE );
	    
	    pnml=(Pnml)m.unmarshal(new File("test/persistence/example.pnml"));
	    
	    // TODO: enable code again if internal API is fully implemented; see engine.session.SessionManager.createPetrinetData(SessionManager.java:80)
	    /*
	    PetrinetManipulation petriMani = PetrinetManipulation.getInstance();
	    boolean success = Converter.convertToPetrinet(pnml, petriMani);
		*/
		
	    // TODO: change code to use different APIs
	    /*
	    System.out.println(petrinet.getAllArcs());
	    System.out.println("got here");
	    Map<String, String[]> layoutMap=new HashMap<String, String[]>();
	    System.out.println("got here");
	    int i=0;
	    for(petrinet.Place p:petrinet.getAllPlaces()){
	    	String[] pos=new String[]{String.valueOf(i),String.valueOf(i)};
	    	layoutMap.put(String.valueOf(p.getId()), pos);
	    	i++;
	    }
	    
	    Pnml pnml2=Converter.convertToPnml(petrinet, layoutMap);
	    Marshaller ma=context.createMarshaller();
	    File f=new File("./testPNMLFile.txt");
	    ma.marshal(pnml2, f);
	    System.out.print("file:"+f);
	    */
	}
	


}
