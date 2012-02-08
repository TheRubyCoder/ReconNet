package persistence;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import petrinet.INode;
import petrinet.Petrinet;
import transformation.Rule;
import engine.attribute.NodeLayoutAttribute;
import engine.ihandler.IPetrinetPersistence;
import engine.ihandler.IRulePersistence;

public class Persistence /* implements IPersistance */{
	static {
		try {
			context = JAXBContext.newInstance(persistence.Pnml.class,
					Arc.class, Converter.class, Dimension.class,
					Graphics.class, InitialMarking.class, Name.class,
					Net.class, Page.class, Place.class, PlaceName.class,
					Position.class, Transition.class, TransitionLabel.class,
					TransitionName.class, TransitionRenew.class, Color.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static JAXBContext context;

	public static boolean savePetrinet(String pathAndFilename,
			Petrinet petrinet, Map<INode, NodeLayoutAttribute> nodeMap) {

		Map<String, String[]> coordinates = new HashMap<String, String[]>();

		for (Entry<INode, NodeLayoutAttribute> e : nodeMap.entrySet()) {
			String[] coords = {
					String.valueOf(e.getValue().getCoordinate().getX()),
					String.valueOf(e.getValue().getCoordinate().getY()),
					String.valueOf(e.getValue().getColor().getRed()),
					String.valueOf(e.getValue().getColor().getGreen()),
					String.valueOf(e.getValue().getColor().getBlue()) };
			coordinates.put(String.valueOf(e.getKey().getId()), coords);
		}

		Pnml pnml = Converter.convertToPnml(petrinet, coordinates);
		File file = new File(pathAndFilename);

		try {

			Marshaller m = context.createMarshaller();

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			FileWriter fw = new FileWriter(file);
			m.marshal(pnml, fw);
			fw.flush();
			fw.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int loadPetrinet(String pathAndFilename,
			IPetrinetPersistence handler) {
		Pnml pnml = new Pnml();
		try {
			Unmarshaller m = context.createUnmarshaller();

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			pnml = (Pnml) m.unmarshal(new File(pathAndFilename));

			return Converter.convertToPetrinet(pnml, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	public static boolean saveRule(String pathAndFilename, Rule rule,
			Map<INode, NodeLayoutAttribute> nodeMapL,
			Map<INode, NodeLayoutAttribute> nodeMapK,
			Map<INode, NodeLayoutAttribute> nodeMapR) {
		boolean success = false;

		Map<String, String[]> coordinates = new HashMap<String, String[]>();
		/*
		 * for(Entry<INode, NodeLayoutAttribute> e:nodeMap.entrySet()){ String[]
		 * coords
		 * ={String.valueOf(e.getValue().getCoordinate().getX()),String.valueOf
		 * (e.getValue().getCoordinate().getY())};
		 * coordinates.put(String.valueOf(e.getKey().getId()), coords); }
		 */

		try {

			Map<INode, NodeLayoutAttribute> nodeMapMerged = new HashMap<INode, NodeLayoutAttribute>();
			nodeMapMerged.putAll(nodeMapL);
			nodeMapMerged.putAll(nodeMapK);
			nodeMapMerged.putAll(nodeMapR);

			Marshaller m = context.createMarshaller();
			Pnml pnml = Converter.convertRuleToPnml(rule, nodeMapMerged);

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			FileWriter fw = new FileWriter(pathAndFilename);
			m.marshal(pnml, fw);
			fw.flush();
			fw.close();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return success;
	}

	public static int loadRule(String pathAndFilename, IRulePersistence handler) {
		Pnml pnml;
		try {
			Unmarshaller m = context.createUnmarshaller();

			m.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());

			pnml = (Pnml) m.unmarshal(new File(pathAndFilename));

			return Converter.convertToRule(pnml, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}
