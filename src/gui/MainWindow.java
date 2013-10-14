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

package gui;

import static gui.Style.HEADER_DIMENSION;
import static gui.Style.LEFT_PANEL_DIMENSION;
import static gui.Style.TOTAL_HEIGHT;
import static gui.Style.TOTAL_WIDTH;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.handler.petrinet.PetrinetManipulation;
import engine.ihandler.IPetrinetManipulation;

/**
 * Main Window that contains all sub areas
 */
class MainWindow {

	/** The main frame of the gui */
	JFrame mainFrame;

	/** The Head Panel */
	JPanel headPanel;

	/** The Panel of the left side */
	JPanel leftPanel;

	/** The Panel of the center */
	JPanel centerPanel;

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

	/** Returns petrinet manipulation aspect of engine*/
	public static IPetrinetManipulation getPetrinetManipulation() {
		return manipulation;
	}

	/** Private Constructor that configures the main window */
	private MainWindow() {
		initiateDependencies();
		initializeMainFrame();
		addEditorPane();
		addAttributePane();
		addSimulationPane();
		addPetrinetPane();
		addRulePane();
		addFilePanes();
		show();
	}

	/** Initiates references to engine */
	private void initiateDependencies() {
		manipulation = PetrinetManipulation.getInstance();
	}

	/**
	 * Initializes the main frame with defaults values for title, size and
	 * position
	 */
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		mainFrame.setTitle("ReconNet");
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/gui/icons/ReconNetLogo.png"));
		mainFrame.setSize(TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());

		JPanel head = initializeHeadPanel();
		mainFrame.getContentPane().add(head, BorderLayout.PAGE_START);

		JPanel left = initializeLeftPanel();
		mainFrame.getContentPane().add(left, BorderLayout.WEST);

		JPanel center = initializeCenterPanel();
		mainFrame.getContentPane().add(center, BorderLayout.CENTER);
	}

	/**
	 * Initialize the Mainpanel in the Center with 2 Rows for the Petrinetpane
	 * and Rulepane
	 */
	private JPanel initializeCenterPanel() {
		GridLayout layout = new GridLayout(2, 1);
		centerPanel = new JPanel(layout);

		return centerPanel;
	}

	/**
	 * Initialize the Headpanel with Borderlayout and the Dimension for the
	 * Panel
	 */
	private JPanel initializeHeadPanel() {
		BorderLayout layout = new BorderLayout();
		headPanel = new JPanel(layout);
		headPanel.setPreferredSize(HEADER_DIMENSION);
		headPanel.setMinimumSize(HEADER_DIMENSION);

		return headPanel;
	}

	/**
	 * Initialize the Panel of the left side with 2 Rows for the
	 * FilePanes(Petrinet an Rule) And set the Dimension of the Pane
	 */
	private JPanel initializeLeftPanel() {
		GridLayout layout = new GridLayout(2, 1);
		leftPanel = new JPanel(layout);
		leftPanel.setPreferredSize(LEFT_PANEL_DIMENSION);
		leftPanel.setMinimumSize(LEFT_PANEL_DIMENSION);

		return leftPanel;
	}

	/** Add the Editorpane to the Headpane */
	private void addEditorPane() {
		EditorPane editorPane = EditorPane.getInstance();
		editorPane.addTo(headPanel);
	}

	/** Add the Simulationpane to the Headpane */
	private void addSimulationPane() {
		SimulationPane simulationPane = SimulationPane.getInstance();
		simulationPane.addTo(headPanel);
	}

	/** Add the Petrinetpane to the Centerpane */
	private void addPetrinetPane() {
		PetrinetPane.getInstance().addTo(centerPanel);
	}
	
	/** Adds the Rulepane to the center pane*/
	private void addRulePane() {
		RulePane.getInstance().addTo(centerPanel);
	}

	/** Add the Filepanes to the Leftpane */
	private void addFilePanes() {
		FilePane.getPetrinetFilePane().addTo(leftPanel);
		FilePane.getRuleFilePane().addTo(leftPanel);
	}

	/** Add the Attributepane to the Headpane */
	private void addAttributePane() {
		AttributePane.getInstance().addTo(headPanel);
	}

	/** Set Size of Mainframe and make it visible */
	private void show() {
		mainFrame.pack();
		mainFrame.setBounds(0, 0, TOTAL_WIDTH, TOTAL_HEIGHT);
		mainFrame.setVisible(true);
	}

	/** Repaints whole gui */
	public void repaint() {
		Rectangle oldBounds = mainFrame.getBounds();
		mainFrame.pack(); //resets bounds
		mainFrame.setBounds(oldBounds);
	}

}
