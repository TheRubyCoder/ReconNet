package gui;

import static gui.Style.PETRINET_BORDER;
import static gui.Style.PETRINET_PANE_LAYOUT;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;
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
			Layout<INode, Arc> layout = EngineAdapter.getPetrinetManipulation()
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
