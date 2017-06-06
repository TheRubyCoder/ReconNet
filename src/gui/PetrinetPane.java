/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veraenderung, sind
 * unter den folgenden Bedingungen zulaessig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare muessen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare muessen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden duerfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFUEGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FUER
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FUER IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFAELLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHAEDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGUETERN ODER -DIENSTLEISTUNGEN; EINSCHRAENKUNG DER NUTZUNGSFAEHIGKEIT;
 * VERLUST VON NUTZUNGSFAEHIGKEIT; DATEN; PROFIT ODER GESCHAEFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLAESSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MOEGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
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
 * AS IS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FUER ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package gui;

import static gui.Style.PETRINET_BORDER;
import static gui.Style.PETRINET_PANE_LAYOUT;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import petrinet.model.IArc;
import petrinet.model.INode;
import edu.uci.ics.jung.algorithms.layout.Layout;
import exceptions.EngineException;
import gui.graphLayout.AdvancedFRLayout;

/** Pane for displaying petrinets */
public final class PetrinetPane {

  /** Internal JPanel for gui-layouting the petrinet */
  private JPanel petrinetPanel;

  /** {@link PetrinetViewer} of currently displayed petrinet */
  private PetrinetViewer petrinetViewer;

  /** singleton instance of this pane */
  private static PetrinetPane instance;

  /** Returns the singleton instance for this pane */
  public static PetrinetPane getInstance() {

    return instance;
  }

  /* Static constructor that initiates the singleton */
  static {
    instance = new PetrinetPane();
  }

  /** Private default constructor */
  private PetrinetPane() {

    petrinetPanel = new JPanel();
    petrinetPanel.setLayout(PETRINET_PANE_LAYOUT);
    petrinetPanel.setBorder(PETRINET_BORDER);

    // petrinetViewer = PetrinetViewer.getDefaultViewer(null);
    // petrinetViewer.addTo(petrinetPanel);

  }

  /** Sets the title of the border to <code>title</code> */
  private void setBorderTitle(String title) {

    petrinetPanel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), title));
  }

  /** Returns the singleton instance */
  private JPanel getPetrinetPanel() {

    return petrinetPanel;
  }

  /** Adds the petrinet pane to the given JPanel (frame) */
  public void addTo(JPanel frame) {

    frame.add(getPetrinetPanel());
  }

  /** repaints the panel */
  public void repaint() {

    petrinetViewer.repaint();
    // displayPetrinet(getCurrentPetrinetId(), null);

  }

  /** Returns the id of the currently displayed petrinet */
  public int getCurrentPetrinetId() {

    return petrinetViewer.getCurrentId();
  }

  /**
   * Replaces the current PetrinetViewer so the new Petrinet is displayed. All
   * Listeners are attacked to the new Petrinet
   */
  public void displayPetrinet(int petrinetId, String title) {

    displayPetrinet(petrinetId, title, false);
  }

  public void displayPetrinet(int petrinetId, String title,
    boolean layoutFrozen) {

    if (title != null) {
      setBorderTitle(title);
    }

    try {
      Layout<INode, IArc> layout =
        EngineAdapter.getPetrinetManipulation().getJungLayout(petrinetId);
      if (petrinetViewer != null) {
        petrinetViewer.removeFrom(petrinetPanel);
      }

      HashMap<INode, Point2D> locationMap = new HashMap<INode, Point2D>();
      Collection<INode> vertices = layout.getGraph().getVertices();
      for (INode v : vertices) {
        Point2D vp = layout.transform(v);
        locationMap.put(v, vp);
      }

      if (layoutFrozen && AdvancedFRLayout.class.isInstance(layout)) {
        ((AdvancedFRLayout<INode, IArc>) layout).freez();
      }

      petrinetViewer = new PetrinetViewer(layout, petrinetId, null,
        locationMap, new Dimension(640, 480));
      double nodeSize = EngineAdapter.getPetrinetManipulation().getNodeSize(
        petrinetId);
      petrinetViewer.setNodeSize(nodeSize);
      petrinetViewer.addTo(petrinetPanel);
      MainWindow.getInstance().repaint();
      SimulationPane.getInstance().setSimulationPaneEnable();

      if (layoutFrozen && AdvancedFRLayout.class.isInstance(layout)) {
        ((AdvancedFRLayout<INode, IArc>) layout).unfreez();
      }

    } catch (EngineException e) {
    }
  }

  /** Makes the pane display empty space (in case no petrinet is selected) */
  public void displayEmpty() {

    if (petrinetViewer != null) {
      petrinetViewer.removeFrom(petrinetPanel);
      SimulationPane.getInstance().setSimulationPaneDisable();
    }
    petrinetPanel.setBorder(PETRINET_BORDER);
  }

  /**
   * Returns {@link PetrinetViewer#getNodeSize() current node size} of current
   * {@link PetrinetViewer}
   *
   * @return
   */
  public double getCurrentNodeSize() {

    return petrinetViewer.getNodeSize();
  }
}
