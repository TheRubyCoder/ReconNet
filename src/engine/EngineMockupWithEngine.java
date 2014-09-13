/*
 * BSD-Lizenz Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind
 * unter den folgenden Bedingungen zulässig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT;
 * VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
 * and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. Neither the name of the
 * University nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written
 * permission. THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package engine;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.sun.istack.NotNull;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Petrinet;
import petrinet.model.Place;
import petrinet.model.Transition;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import engine.attribute.ArcAttribute;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import engine.handler.petrinet.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;
import engine.session.SessionManager;
import exceptions.EngineException;

/**
 * This class is leftover from an older stage of development. It is only used
 * in tests and should not be used any more
 */
public class EngineMockupWithEngine
  implements IPetrinetManipulation {

  private IPetrinetManipulation iPetrinetManipulation;
  private int id;

  public EngineMockupWithEngine() {

    iPetrinetManipulation = PetrinetManipulation.getInstance();

    id = iPetrinetManipulation.createPetrinet();

    // System.out.println("mockup! id: " + id);

  }

  public void build()
    throws EngineException {

    // CHECKSTYLE:OFF - No need to check for magic numbers
    createPlace(id, new Point2D.Double(10, 10));
    createPlace(id, new Point2D.Double(10, 100));
    createPlace(id, new Point2D.Double(100, 10));
    createPlace(id, new Point2D.Double(100, 100));

    createTransition(id, new Point2D.Double(55, 10));
    createTransition(id, new Point2D.Double(10, 55));
    createTransition(id, new Point2D.Double(100, 55));
    createTransition(id, new Point2D.Double(55, 100));
    // CHECKSTYLE:ON

    Petrinet petrinet =
      SessionManager.getInstance().getPetrinetData(1).getPetrinet();

    List<Place> places = new ArrayList<Place>(petrinet.getPlaces());
    List<Transition> transitions =
      new ArrayList<Transition>(petrinet.getTransitions());

    // CHECKSTYLE:OFF - No need to check for magic numbers
    createArc(id, places.get(0), transitions.get(0));
    createArc(id, transitions.get(0), places.get(1));

    createArc(id, places.get(1), transitions.get(1));
    createArc(id, transitions.get(1), places.get(2));

    createArc(id, places.get(2), transitions.get(2));
    createArc(id, transitions.get(2), places.get(3));

    createArc(id, places.get(3), transitions.get(3));
    createArc(id, transitions.get(3), places.get(0));
    // CHECKSTYLE:ON

    // Petrinet petrinet2 =
    // SessionManager.getInstance().getPetrinetData(id).getPetrinet();

  }

  @Override
  public void createArc(int id, INode from, INode to)
    throws EngineException {

    iPetrinetManipulation.createArc(id, from, to);

  }

  @Override
  public void createPlace(int id, Point2D coordinate)
    throws EngineException {

    iPetrinetManipulation.createPlace(id, coordinate);

  }

  @Override
  public int createPetrinet() {

    // transform to comment to see the graph above
    // id = iPetrinetManipulation.createPetrinet();

    return id;
  }

  @Override
  public void createTransition(int id, Point2D coordinate)
    throws EngineException {

    iPetrinetManipulation.createTransition(id, coordinate);

  }

  @Override
  public void deleteArc(int id, IArc arc)
    throws EngineException {

    iPetrinetManipulation.deleteArc(id, arc);
  }

  @Override
  public void deletePlace(int id, INode place)
    throws EngineException {

    iPetrinetManipulation.deletePlace(id, place);

  }

  @Override
  public void deleteTransition(int id, INode transition)
    throws EngineException {

    iPetrinetManipulation.deleteTransition(id, transition);

  }

  @Override
  public ArcAttribute getArcAttribute(int id, IArc arc)
    throws EngineException {

    return iPetrinetManipulation.getArcAttribute(id, arc);

  }

  @Override
  public AbstractLayout<INode, IArc> getJungLayout(int id)
    throws EngineException {

    return iPetrinetManipulation.getJungLayout(id);

  }

  @Override
  public PlaceAttribute getPlaceAttribute(int id, INode place)
    throws EngineException {

    return iPetrinetManipulation.getPlaceAttribute(id, place);

  }

  @Override
  public TransitionAttribute getTransitionAttribute(int id, INode transition)
    throws EngineException {

    return iPetrinetManipulation.getTransitionAttribute(id, transition);

  }

  @Override
  public void moveGraph(int id, Point2D relativePosition)
    throws EngineException {

    iPetrinetManipulation.moveGraph(id, relativePosition);

  }

  @Override
  public void moveNode(int id, INode node, Point2D relativePosition)
    throws EngineException {

    iPetrinetManipulation.moveNode(id, node, relativePosition);

  }

  @Override
  public void save(int id, String path, String filename, String format,
    double nodeSize)
    throws EngineException {

    iPetrinetManipulation.save(id, path, filename, format, nodeSize);

  }

  @Override
  public int load(String path, String filename) {

    return 1;
  }

  @Override
  public void setMarking(int id, INode place, int marking)
    throws EngineException {

    iPetrinetManipulation.setMarking(id, place, marking);

  }

  @Override
  public void setPname(int id, INode place, String pname)
    throws EngineException {

    iPetrinetManipulation.setPname(id, place, pname);

  }

  @Override
  public void setTlb(int id, INode transition, String tlb)
    throws EngineException {

    iPetrinetManipulation.setTlb(id, transition, tlb);

  }

  @Override
  public void setTname(int id, INode transition, String tname)
    throws EngineException {

    iPetrinetManipulation.setTname(id, transition, tname);

  }

  @Override
  public void setWeight(int id, IArc arc, int weight)
    throws EngineException {

    iPetrinetManipulation.setWeight(id, arc, weight);

  }

  @Override
  public void setRnw(int id, INode transition, IRenew renews)
    throws EngineException {

    iPetrinetManipulation.setRnw(id, transition, renews);

  }

  @Override
  public NodeTypeEnum getNodeType(INode node)
    throws EngineException {

    return iPetrinetManipulation.getNodeType(node);

  }

  @Override
  public void setPlaceColor(int id, INode place, Color color)
    throws EngineException {

    iPetrinetManipulation.setPlaceColor(id, place, color);

  }

  @Override
  public void closePetrinet(int id)
    throws EngineException {

    iPetrinetManipulation.closePetrinet(id);

  }

  @Override
  public void printPetrinet(int id) {

    throw new UnsupportedOperationException();
  }

  @Override
  public void moveGraphIntoVision(int id)
    throws EngineException {

    throw new UnsupportedOperationException();

  }

  @Override
  public void moveAllNodesTo(int id, float factor, Point point) {

    throw new UnsupportedOperationException();

  }

  @Override
  public void setNodeSize(int id, double nodeDistance) {

    throw new UnsupportedOperationException();

  }

  @Override
  public double getNodeSize(int id) {

    throw new UnsupportedOperationException();
  }

  @Override
  public void setCapacity(@NotNull int id, @NotNull INode place,
    @NotNull int capacity)
    throws EngineException {

    // TODO Auto-generated method stub

  }

  @Override
  public int createNac() {

    // TODO: NAC hier erweitern
    return 0;
  }

}
