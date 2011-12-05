package engine.ihandler;

import java.awt.geom.Point2D;

import petrinet.Arc;
import petrinet.INode;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.handler.ArcAttribute;
import engine.handler.PlaceAttribute;
import engine.handler.TransitionAttribute;

public interface IPetrinetHandler {

	/**
	 * 
	 * @param id
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean createArc(int id, INode from, INode to);
	
	/**
	 * 
	 * @param id
	 * @param coordinate
	 * @return
	 */
	public boolean createPlace(int id, Point2D coordinate);
	
	/**
	 * 
	 * @return
	 */
	public int createPetrinet();
	
	/**
	 * 
	 * @param id
	 * @param coordinate
	 * @return
	 */
	public boolean createTransition(int id, Point2D coordinate);
	
	/**
	 * 
	 * @param id
	 * @param arc
	 * @return
	 */
	public boolean deleteArc(int id, Arc arc); // TODO IArc gibt es nicht?
	
	// TODO: da sollte doch ne methode drueber die INode aufloest oder?
	/**
	 * 
	 * @param id
	 * @param place
	 * @return
	 */
	public boolean deletePlace(int id, INode place);
	
	/**
	 * 
	 * @param id
	 * @param transition
	 * @return
	 */
	public boolean deleteTransition(int id, INode transition);
	
	/**
	 * 
	 * @param id
	 * @param arc
	 * @return
	 */
	public ArcAttribute getArcAttribute(int id, Arc arc); // TODO IArc gibt es nicht?
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	
	// TODO
	public AbstractLayout<INode, Arc> getJungLayout(int id); // TODO: AbstractLayout<INode, Arc> richtig?
	
	/**
	 * 
	 * @param id
	 * @param place
	 * @return
	 */
	public PlaceAttribute getPlaceAttribute(int id, INode place);
	
	/**
	 * 
	 * @param id
	 * @param transition
	 * @return
	 */
	public TransitionAttribute getTransitionAttribute(int id, INode transition);
	
	/**
	 * 
	 * @param id
	 * @param relativePosition
	 * @return
	 */
	public boolean moveGraph(int id, Point2D relativePosition);
	
	/**
	 * 
	 * @param id
	 * @param node
	 * @param relativePosition
	 * @return
	 */
	public boolean moveNode(int id, INode node, Point2D relativePosition);
	
	/**
	 * 
	 * @param id
	 * @param path
	 * @param filename
	 * @param format
	 * @return
	 */
	public boolean save(int id, String path, String filename, String format); // TODO: String format zu => Format format
	
	/**
	 * 
	 * @param id
	 * @param place
	 * @param marking
	 * @return
	 */
	public boolean setMarking(int id, INode place, int marking);
	
	/**
	 * 
	 * @param id
	 * @param place
	 * @param pname
	 * @return
	 */
	public boolean setPname(int id, INode place, String pname);
	
	/**
	 * 
	 * @param id
	 * @param transition
	 * @param tlb
	 * @return
	 */
	public boolean setTlb(int id, INode transition, String tlb);
	
	/**
	 * 
	 * @param id
	 * @param transition
	 * @param tname
	 * @return
	 */
	public boolean setTname(int id, INode transition, String tname);
	
	/**
	 * 
	 * @param id
	 * @param arc
	 * @param weight
	 * @return
	 */
	public boolean setWeight(int id, Arc arc, int weight);
}
