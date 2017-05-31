/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
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

package gui;

import static gui.Style.HEADER_DIMENSION;
import static gui.Style.TOTAL_HEIGHT;
import static gui.Style.TOTAL_WIDTH;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import engine.handler.petrinet.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;
import gui.fileTree.FileTreePane;

/**
 * Main Window that contains all sub areas
 */
final class MainWindow {

  // -------------GUI Panels-------------------------

  /**
   * The main frame of the gui
   */
  private JFrame mainFrame;

  /**
   * The Head panel of the gui
   */
  private JPanel headPanel;

  /**
   * The Body panel of the gui
   */
  private JSplitPane bodyPanel;

  /**
   * The UpperBody panel of the gui
   */
  private JPanel upperBodyPanel;

  // ------------END of GUI Panel--------------------

  /** PetrinetManipulation aspect of engine */
  private static IPetrinetManipulation manipulation;

  /** singleton instance */
  private static MainWindow instance;

  // static constructor that initiates the singleton instance and constants
  static {
    instance = new MainWindow();
  }

  /** Returns the only instance of the main window */
  public static MainWindow getInstance() {

    return instance;
  }

  /** Returns petrinet manipulation aspect of engine */
  public static IPetrinetManipulation getPetrinetManipulation() {

    return manipulation;
  }

  /** Private Constructor that configures the main window */
  private MainWindow() {

    initiateDependencies();
    initializeMainFrame();
    initializeGuiComponents();
    addGuiComponents();
    show();
    this.setDividerLocations();
  }

  /**
   * sets the location of the divider from {@link JSplitPane} to the half of
   * the parent panel.
   */
  private void setDividerLocations() {

    this.bodyPanel.setDividerLocation(this.bodyPanel.getHeight() / 2);
  }

  /**
   * adds gui components to the main window.
   */
  private void addGuiComponents() {

    mainFrame.getContentPane().add(this.headPanel, BorderLayout.PAGE_START);
    mainFrame.getContentPane().add(this.bodyPanel, BorderLayout.CENTER);
  }

  /**
   * Initializes the main frame with defaults values for title, size and
   * position
   */
  private void initializeMainFrame() {

    mainFrame = new JFrame();
    mainFrame.setTitle("ReconNet");
    mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(
      "src/gui/icons/ReconNetLogo.png"));
    mainFrame.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(new BorderLayout());
  }

  /**
   * Adds GUI components to frame.
   */
  private void initializeGuiComponents() {

    initializeHeadPanel();
    initializeBodyPanel();
  }

  /**
   * Adds the body panel (JSPlitPane) to frame.
   */
  private void initializeBodyPanel() {

    initializeUpperBodyPanel();
    JPanel p = new JPanel(new GridLayout(1, 1));
    RulePane.getInstance().addTo(p);
    this.bodyPanel =
      new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.upperBodyPanel, p);
    this.bodyPanel.setOneTouchExpandable(true);
    this.bodyPanel.setResizeWeight(.5);
  }

  /**
   * initializes the upper body panel holding the net and file tree.
   */
  private void initializeUpperBodyPanel() {

    this.upperBodyPanel = new JPanel(new BorderLayout());
    FileTreePane.getInstance().addTo(this.upperBodyPanel);
    PetrinetPane.getInstance().addTo(this.upperBodyPanel);
  }

  /**
   * Adds the head panel to frame.
   */
  private void initializeHeadPanel() {

    headPanel = new JPanel(new BorderLayout());
    headPanel.setPreferredSize(HEADER_DIMENSION);
    headPanel.setMinimumSize(HEADER_DIMENSION);

    EditorPane.getInstance().addTo(headPanel);
    AttributePane.getInstance().addTo(headPanel);
    SimulationPane.getInstance().addTo(headPanel);
  }

  /**
   * Initiates references to engine
   */
  private void initiateDependencies() {

    manipulation = PetrinetManipulation.getInstance();
  }

  /**
   * Set Size of Mainframe and make it visible
   */
  private void show() {

    mainFrame.pack();
    mainFrame.setBounds(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
    mainFrame.setVisible(true);
  }

  /**
   * Repaints whole gui
   */
  public void repaint() {

    Rectangle oldBounds = mainFrame.getBounds();
    mainFrame.pack(); // resets bounds
    mainFrame.setBounds(oldBounds);
  }

}
