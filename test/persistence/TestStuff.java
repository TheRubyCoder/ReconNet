package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
	    Converter con=new Converter();
	    petrinet.Petrinet petrinet=con.convertToPetrinet(pnml);
		
	    System.out.println(petrinet.getAllArcs());

	}
	


}