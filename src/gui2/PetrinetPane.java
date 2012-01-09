package gui2;

import static gui2.Style.PETRINET_BORDER;
import static gui2.Style.PETRINET_PANE_LAYOUT;

import javax.swing.JPanel;

import petrinet.Arc;
import petrinet.INode;

import edu.uci.ics.jung.algorithms.layout.Layout;
import exceptions.EngineException;

/** Pane for displaying petrinets */
class PetrinetPane {

	/** Internal JPanel for gui-layouting the petrinet */
	private JPanel petrinetPanel;


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
		
		petrinetViewer = PetrinetViewer.getDefaultViewer(null);
		petrinetViewer.addTo(petrinetPanel);
		
	}

	private JPanel getPetrinetPanel() {
		return petrinetPanel;
	}

	/** Adds the petrinet pane to the given JPanel (frame) */
	public void addTo(JPanel frame) {
		frame.add(getPetrinetPanel());
	}

	public void repaint() {
		petrinetViewer.repaint();
	}
	
	public int getCurrentPetrinetId(){
		return petrinetViewer.getCurrentPetrinetId();
	}
	
	public void displayPetrinet(int petrinetId){
		try{
			Layout<INode,Arc> layout = EngineAdapter.getPetrinetManipulation().getJungLayout(petrinetId);
			petrinetPanel.remove(petrinetViewer);
			petrinetViewer = new PetrinetViewer(layout, petrinetId, null);
			petrinetViewer.addTo(petrinetPanel);
		}catch (EngineException e) {
		}
	}
}
