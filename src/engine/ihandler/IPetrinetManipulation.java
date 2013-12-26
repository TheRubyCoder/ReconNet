/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package engine.ihandler;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;

import com.sun.istack.NotNull;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.data.JungData;
import engine.handler.NodeTypeEnum;
import exceptions.EngineException;
import gui.PetrinetViewer;

/**
 * 
 * This is GUI-Interface for all actions with Petrinet-functions.
 * 
 * Implementation: engine.handler.petrinet.PetrinetManipulation
 * 
 * Some functions are: - create[Petrinet|Arc|Place|Transition](..) -
 * delete[Arc|Place|Transition](..) - get[Arc|Place|Transition]Attribute(..) -
 * getJungLayout(..) - move[Graph|Node](..) - save(..) -
 * set[Marking|Pname|Tlb|Tname|Weight|NodeType](..)
 * 
 * @author alex (aas772)
 * 
 */

public interface IPetrinetManipulation {

	/**
	 * Creates an Arc
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param from
	 *            Source of the Arc
	 * @param to
	 *            Target of the Arc
	 * @throws EngineException
	 */
	public void createArc(@NotNull int id, @NotNull INode from,
			@NotNull INode to) throws EngineException;

	/**
	 * 
	 * Creates a Place
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param coordinate
	 *            Point where the Place will be created
	 * @return
	 * @throws EngineException
	 * 
	 */
	public void createPlace(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException;

	/**
	 * 
	 * Creates a Petrinet
	 * 
	 * @return ID of the created Petrinet
	 * 
	 */
	public int createPetrinet();

	/**
	 * Creates a Transition
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param coordinate
	 *            Point where the Transition will be created
	 * @return
	 * @throws EngineException
	 */
	public void createTransition(@NotNull int id, @NotNull Point2D coordinate)
			throws EngineException;

	/**
	 * Deletes an Arc
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param arc
	 *            which will be deleted
	 * @throws EngineException
	 */
	public void deleteArc(@NotNull int id, @NotNull IArc arc)
			throws EngineException; 

	/**
	 * Deletes a Place
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            which will be deleted
	 */
	public void deletePlace(@NotNull int id, @NotNull INode place)
			throws EngineException;

	/**
	 * Deletes a Transition
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param transition
	 *            which will be deleted
	 */
	public void deleteTransition(@NotNull int id, @NotNull INode transition)
			throws EngineException;

	/**
	 * Gets the Attributes from an Arc
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param arc
	 *            which attributes are wanted
	 * @return ArcAttribute
	 */
	public ArcAttribute getArcAttribute(@NotNull int id, @NotNull IArc arc)
			throws EngineException; 

	/**
	 * Gets the JungLayout from the Petrinet
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @return AbstractLayout
	 * @throws EngineException
	 */

	public AbstractLayout<INode, IArc> getJungLayout(@NotNull int id)
			throws EngineException; 

	/**
	 * Gets the Attributes from a Place
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            which attributes are wanted
	 * @return PlaceAtrribute
	 * @throws EngineException
	 */
	public PlaceAttribute getPlaceAttribute(@NotNull int id,
			@NotNull INode place) throws EngineException;

	/**
	 * Gets the Attributes from a Transition
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param transition
	 *            which attributes are wanted
	 * @return TransitionAttribute
	 * @throws EngineException
	 */
	public TransitionAttribute getTransitionAttribute(@NotNull int id,
			@NotNull INode transition) throws EngineException;

	/**
	 * Moves a Graph.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param relativePosition
	 *            relative movement of the Graph
	 * @throws EngineException
	 */
	public void moveGraph(@NotNull int id, @NotNull Point2D relativePosition)
			throws EngineException;

	/**
	 * Move a graph into the vision of the GUI. (Top left node will be at [0,0]
	 * position). This may be useful if the user moved the graph outside the
	 * window and can't find it
	 * 
	 * @param id
	 *            id of the graph
	 * @throws EngineException
	 */
	public void moveGraphIntoVision(@NotNull int id) throws EngineException;

	/**
	 * Moves a node.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param node
	 *            to move
	 * @param relativePosition
	 *            relative movement of the node
	 * @throws EngineException
	 */
	public void moveNode(@NotNull int id, @NotNull INode node,
			@NotNull Point2D relativePosition) throws EngineException;

	/**
	 * Saves a Petrinet.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param path
	 *            where to save the Petrinet
	 * @param filename
	 *            name for the Petrinet
	 * @param format
	 *            which the Petrinet should be saved. (PNML the only option till
	 *            now)
	 */
	public void save(@NotNull int id, @NotNull String path,
			@NotNull String filename, @NotNull String format, double nodeSize)
			throws EngineException;

	/**
	 * 
	 * Load a Petrinet.
	 * 
	 * @param path
	 *            where is this Petrinet
	 * @param filename
	 *            name for the Petrinet
	 * @return the id of the Petrinet
	 * 
	 */
	public int load(@NotNull String path, @NotNull String filename);

	/**
	 * Sets the Marking of a Place.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            where to set the Mark
	 * @param marking
	 *            amount of mark
	 * @throws EngineException
	 */
	public void setMarking(@NotNull int id, @NotNull INode place,
			@NotNull int marking) throws EngineException;
	
	/**
	 * Sets the Capacity of a Place.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            where to set the Capacity
	 * @param marking
	 *            amount of marCapacityk
	 * @throws EngineException
	 */
	public void setCapacity(@NotNull int id, @NotNull INode place,
			@NotNull int capacity) throws EngineException;

	/**
	 * Sets the PName of a Place.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            where to set the PName
	 * @param pname
	 *            PName
	 * @throws EngineException
	 */
	public void setPname(@NotNull int id, @NotNull INode place,
			@NotNull String pname) throws EngineException;

	/**
	 * Sets the Tlb of a Transition.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param transition
	 *            where to set the tlb
	 * @param tlb
	 *            TransitionLabel
	 * @throws EngineException
	 */
	public void setTlb(@NotNull int id, @NotNull INode transition,
			@NotNull String tlb) throws EngineException;

	/**
	 * Sets the TName of a Transition.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param transition
	 *            where to set the TName
	 * @param tname
	 *            TName
	 * @throws EngineException
	 */
	public void setTname(@NotNull int id, @NotNull INode transition,
			@NotNull String tname) throws EngineException;

	/**
	 * Sets the Weight of an Arc.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param arc
	 *            where to set the weight
	 * @param weight
	 *            weight of the arc
	 * @throws EngineException
	 */
	public void setWeight(@NotNull int id, @NotNull IArc arc, @NotNull int weight)
			throws EngineException;

	/**
	 * 
	 * Sets a Strings as RNW.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param rnw
	 *            String as RNW
	 * @throws EngineException
	 * 
	 */
	public void setRnw(@NotNull int id, @NotNull INode transition,
			@NotNull IRenew renews) throws EngineException;

	/**
	 * 
	 * Set Color of a Place.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @param place
	 *            which should modify
	 * @param color
	 *            new Color
	 * @throws EngineException
	 * 
	 */
	public void setPlaceColor(@NotNull int id, @NotNull INode place,
			@NotNull Color color) throws EngineException;

	/**
	 * 
	 * This methods clean the Tool from this Petrinet.
	 * 
	 * @param id
	 *            ID of the Petrinet
	 * @throws EngineException
	 * 
	 */
	public void closePetrinet(@NotNull int id) throws EngineException;

	/**
	 * Returns the type of the Object.
	 * 
	 * @param node
	 *            to check
	 * @return Enum composed of Place, Transition
	 * @throws EngineException
	 */
	public NodeTypeEnum getNodeType(@NotNull INode node) throws EngineException;

	/**
	 * Prints the petrinet with <tt>id</tt> in the console
	 * */
	public void printPetrinet(@NotNull int id);

	/**
	 * Similar to {@link PetrinetViewer#moveAllNodesTo(float, Point)} but looks
	 * up petrinet with <code>id</code> first
	 * 
	 * @param id
	 * @param factor
	 * @param point
	 */
	public void moveAllNodesTo(int id, float factor, Point point);

	/**
	 * Sets the nodeSize for the JungData of petrinet with <code>id</code>
	 * 
	 * @see {@link JungData#setNodeSize(double)}
	 * @param id
	 * @param nodeSize
	 */
	public void setNodeSize(int id, double nodeSize);
	
	
	/**
	 * Returns the nodeSize of Petrinet with <code>id</code>
	 * @param id
	 * @throws if id is wrong
	 * @return
	 */
	public double getNodeSize(int id) throws NullPointerException;

	/**
     * 
     * Creates a NAC
     * 
     * @return ID of the created NAC
     * 
     */
    public int createNac();

}
