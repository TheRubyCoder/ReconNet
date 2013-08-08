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

import static gui.Style.PETRINET_BORDER;
import static gui.Style.PETRINET_PANE_LAYOUT;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import petrinet.model.IArc;
import petrinet.model.INode;
import edu.uci.ics.jung.algorithms.layout.Layout;
import exceptions.EngineException;

/** Pane for displaying petrinets */
class PetrinetPane {

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
		setBorderTitle(title);
		try {
			Layout<INode, IArc> layout = EngineAdapter.getPetrinetManipulation()
					.getJungLayout(petrinetId);
			if (petrinetViewer != null) {
				petrinetViewer.removeFrom(petrinetPanel);
			}
			petrinetViewer = new PetrinetViewer(layout, petrinetId, null);
			double nodeSize = EngineAdapter.getPetrinetManipulation()
					.getNodeSize(petrinetId);
			petrinetViewer.setNodeSize(nodeSize);
			petrinetViewer.addTo(petrinetPanel);
			MainWindow.getInstance().repaint();
			SimulationPane.getInstance().setSimulationPaneEnable();
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
