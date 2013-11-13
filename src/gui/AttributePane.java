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

import static gui.Style.ATTRIBUTE_PANE_BORDER;
import static gui.Style.ATTRIBUTE_PANE_DIMENSION;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import petrinet.model.IArc;
import petrinet.model.INode;
import petrinet.model.IRenew;
import petrinet.model.Place;
import petrinet.model.Renews;
import petrinet.model.Transition;
import engine.attribute.PlaceAttribute;
import engine.attribute.TransitionAttribute;
import engine.handler.NodeTypeEnum;
import exceptions.EngineException;

/**
 * Singleton class that represents the attribute chart at the middle top. It
 * implements the task to edit attributes of transitions, nodes and arcs
 */
public class AttributePane {

	/** Singleton instancce */
	private static AttributePane instance;

	// static constructor that initiates the singleton instance and constants
	static {
		instance = new AttributePane();
	}

	/** Returns the only instance of the AttributePane */
	public static AttributePane getInstance() {
		return instance;
	}

	/** Table containing data for current node */
	private JTable table;

	/** Panel for the Attributes */
	private JPanel attributePane;

	/** Private Constructor that configures the pane */
	private AttributePane() {
		table = initiateTable();
		attributePane = initiateAttributePane(table);
	}

	/**
	 * Initialize the Attributepane and set the Dimension and Layout Add the
	 * Table to the Pane
	 */
	private JPanel initiateAttributePane(JTable table) {
		JPanel pane = new JPanel();
		pane.setBorder(ATTRIBUTE_PANE_BORDER);
		pane.setMinimumSize(ATTRIBUTE_PANE_DIMENSION);
		pane.setLayout(new GridLayout(1, 1));

		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(false);

		pane.add(scrollPane);
		return pane;
	}

	/** Initiate the Table for Attributepane */
	private JTable initiateTable() {
		JTable table = new JTable();// content,columnNames);
		return table;
	}

	/**
	 * Makes the attribute pane display the attributes of a <code>node</code>,
	 * using its <code>petrinetViewer</code>
	 * 
	 * @param node
	 * @param petrinetViewer
	 */
	void displayNode(INode node, PetrinetViewer petrinetViewer) {
		try {
			// get type and id
			NodeTypeEnum type = (NodeTypeEnum) EngineAdapter
					.getPetrinetManipulation().getNodeType(node);
			String id = String.valueOf(node.getId());
			AbstractPetriTableModel tableModel = null;
			// display place
			if (type == NodeTypeEnum.Place) {
				PlaceAttribute placeAttribute = petrinetViewer
						.getPlaceAttribute((Place) node);
				String name = placeAttribute.getPname();
				String mark = String.valueOf(placeAttribute.getMarking());
				String capacity = placeAttribute.getCapacity() == 
						Integer.MAX_VALUE?"unbegrenzt"
						:String.valueOf(placeAttribute.getCapacity());

				tableModel = new PlaceTableModel(id, name, mark, capacity);
			} else {
				// display edge
				TransitionAttribute transitionAttribute = petrinetViewer
						.getTransitionAttribute((Transition) node);
				String name = transitionAttribute.getTname();
				String tlb = transitionAttribute.getTLB();
				IRenew renew = transitionAttribute.getRNW();
				String renewString = renew.toGUIString();
				tableModel = new TransitionTableModel(id, name, tlb,
						renewString, table);
			}
			// add listener
			tableModel.addTableModelListener(new TableListener(petrinetViewer,
					node));
			// make changes account
			table.setModel(tableModel);
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes the attribute pane display the attributes of an <code>edge</code>,
	 * using its <code>petrinetViewer</code>
	 * 
	 * @param edge
	 * @param petrinetViewer
	 */
	void displayEdge(IArc edge, PetrinetViewer petrinetViewer) {
		String weight = String.valueOf(petrinetViewer.getArcAttribute(edge)
				.getWeight());
		String id = String.valueOf(edge.getId());

		ArcTableModel arcTableModel = new ArcTableModel(id, weight);
		TableListener tableListener = new TableListener(petrinetViewer, edge);
		arcTableModel.addTableModelListener(tableListener);

		table.setModel(arcTableModel);
	}

	/**
	 * Makes the attribute pane display an empty space. (In case no node is selected)
	 */
	public void displayEmpty() {
		DefaultTableModel defaultTableModel = new DefaultTableModel();
		table.setModel(defaultTableModel);
	}

	/**
	 * Add this Panel to given frame
	 * 
	 * @param frame
	 *            wich the pane add to
	 */
	public void addTo(JPanel frame) {
		frame.add(attributePane, BorderLayout.CENTER);
	}

	/** Disable the tabe */
	void setTableDisable() {
		table.setEnabled(false);
	}

	/** Enable the tabe */
	void setTableEnable() {
		table.setEnabled(true);
	}

	/**
	 * Abstract super class for refactoring the three Table Models for Place,
	 * Transition and Arc<br/>
	 * Table Model for displaying and editing places. All cells need to be
	 * String<br/>
	 * A TableModel is needed to tell Swing what cells are editable
	 */
	private static abstract class AbstractPetriTableModel extends
			AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6737819176767454927L;

		protected abstract String[][] getData();

		/** Head of table */
		protected String[] columnNames = { "Atrribut", "Wert" };

		@Override
		public int getRowCount() {
			return getData().length;
		}

		@Override
		public int getColumnCount() {
			return getData()[0].length;
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return getData()[rowIndex][columnIndex];
		}

		/**
		 * Set wich rows and columns are editable. the first row and the first
		 * column are not editable
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
			// only bottom right is editable
			if (row > 0 && col > 0) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			getData()[rowIndex][columnIndex] = (String) aValue;
			fireTableCellUpdated(rowIndex, columnIndex);
		}

	}

	/** Class for the Table with Place Attributes */
	private static class PlaceTableModel extends AbstractPetriTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6357182574839407504L;
		/**
		 * The General Data for the Placetable
		 */
		private String[][] data = { { "Id", "" }, { "Name", "" },
				{ "Markierung", "" }, { "Kapazität", "" } };

		/** Initiates the table with actual data for id, name, and mark */
		public PlaceTableModel(String id, String name, String mark, String capacity) {
			data[0][1] = id;
			data[1][1] = name;
			data[2][1] = mark;
			data[3][1] = capacity;
		}

		/** Returns all data (whole table except the head) */
		@Override
		protected String[][] getData() {
			return data;
		}
	}

	/** Class for the Table with Transition Attributes */
	private static class TransitionTableModel extends AbstractPetriTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3955002911201794539L;
		/**
		 * The General Data for the Transitiontable
		 */
		private String[][] data = { { "Id", "" }, { "Name", "" },
				{ "Label", "" }, { "Renew", "" } };

		/**
		 * Initiates the table with actual data for id, name, laben and renew
		 */
		public TransitionTableModel(String id, String name, String label,
				String renew, JTable table) {
			data[0][1] = id;
			data[1][1] = name;
			data[2][1] = label;
			data[3][1] = renew;
		}

		/** Returns all data (whole table except the head) */
		@Override
		protected String[][] getData() {
			return data;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// renew changed
			if (rowIndex == 3 && columnIndex == 1) {
				String newTlb = renewValid(data[2][1], (String) aValue);
				if (newTlb != null) {
					super.setValueAt(aValue, rowIndex, columnIndex);
					super.setValueAt(newTlb, 2, 1);
				}
				// tlb changed
			} else if (rowIndex == 2 && columnIndex == 1) {
				String newRenew = tlbValid((String) aValue, data[3][1]);
				System.out.println("tlbValid = " + newRenew);
				if (newRenew != null) {
					System.out.println(newRenew);
					super.setValueAt(newRenew, 3, 1);
					super.setValueAt(aValue, rowIndex, columnIndex);
				}
			} else {
				// anything but renew or tlb changed
				super.setValueAt(aValue, rowIndex, columnIndex);
			}
		}

		/**
		 * Checks whether the newly set tlb is valid to the assigned renew.
		 * Gives the user the chance to select a new renew
		 * 
		 * @return Returns new Renew as GUI String
		 */
		private String tlbValid(String tlb, String renew) {
			IRenew actualRenew = Renews.fromString(renew);
			boolean valid = actualRenew.isTlbValid(tlb);
			if (valid) {
				return renew;
			} else {
				IRenew newRenew = actualRenew;
				while (!newRenew.isTlbValid(tlb)) {
					String[] options = new String[] { "id", "toggle", "count",
							"abbrechen" };
					int pickedIndex = JOptionPane
							.showOptionDialog(
									null,
									"Das gewählte label \""
											+ tlb
											+ "\" passt nicht zum aktuellen Renew \""
											+ newRenew.toGUIString()
											+ "\". Wenn sie das Label behalten möchten, wählen sie ein passendes Renew:",
									"Neues Renew wählen",
									JOptionPane.INFORMATION_MESSAGE,
									JOptionPane.INFORMATION_MESSAGE, null,
									options, "id");
					System.out.println("options[pickedIndex] = "
							+ options[pickedIndex]);
					if (0 <= pickedIndex && pickedIndex <= 2) {
						newRenew = Renews.fromString(options[pickedIndex]);
					} else {
						return null;
					}
				}
				return newRenew.toGUIString();
			}
		}

		/**
		 * Checks whether the newly set renew is valid to the assigned tlb.
		 * Gives the user the chance to insert a new tlb
		 */
		private String renewValid(String tlb, String renew) {
			IRenew actualRenew = Renews.fromString(renew);
			boolean valid = actualRenew.isTlbValid(tlb);
			if (valid) {
				return tlb;
			} else {
				String newTlb = tlb;
				while (!actualRenew.isTlbValid(newTlb)) {
					String inputDialog = JOptionPane
							.showInputDialog("\""
									+ newTlb
									+ "\" passt nicht zum Renew \""
									+ renew
									+ "\". Bitte geben sie ein neues Label ein, das zum neuen Renew passt:");
					if (inputDialog != null) {
						newTlb = inputDialog;
					} else {
						return null;
					}
				}
				return newTlb;
			}
		}
	}

	/** Class for the Table with Arc Attributes */
	private static class ArcTableModel extends AbstractPetriTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6770322595921463169L;
		/**
		 * The General Data for the Arctable
		 */
		private String[][] data = { { "Id", "" }, { "Gewicht", "" }, };

		/** Initiates the table with actual data for id an name */
		public ArcTableModel(String id, String weight) {
			data[0][1] = id;
			data[1][1] = weight;
		}

		/** Returns all data (whole table except the head) */
		@Override
		protected String[][] getData() {
			return data;
		}

	}


	/** Class for Tablelistener to make changes by the user possible */
	private static class TableListener implements TableModelListener {

		/** {@link PetrinetViewer} of currently displayed element */
		private PetrinetViewer petrinetViewer;

		/** currently displayed node (in case its not a node this variable is <code>null</code>) */
		private INode node;

		/** currently displayed arc (in case its not an arc this variable is <code>null</code>) */
		private IArc arc;

		TableListener(PetrinetViewer petrinetViewer, INode node) {
			this.petrinetViewer = petrinetViewer;
			this.node = node;
			this.arc = null;
		}

		/**
		 * Creates a table listener for a table that displays an arc
		 * @param petrinetViewer
		 * @param node
		 */
		TableListener(PetrinetViewer petrinetViewer, IArc arc) {
			this.petrinetViewer = petrinetViewer;
			this.node = null;
			this.arc = arc;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
			int column = e.getColumn();
			TableModel model = (TableModel) e.getSource();
			String data = (String) model.getValueAt(row, column);
			String attribute = (String) model.getValueAt(row, column - 1);
			/* ARC */
			if (arc != null) {
				if (attribute.equals("Gewicht")) {
					try {
						petrinetViewer.setWeight(arc, Integer.parseInt(data));
						// MainWindow.getPetrinetManipulation().setWeight(
						// petrinetId, arc, Integer.parseInt(data));
					} catch (NumberFormatException e1) {
						PopUp.popError("Das Gewicht muss eine natürliche Zahl sein.");
						e1.printStackTrace();
					}
				}
			} else if (node != null) {
				/* PLACE */
				if (petrinetViewer.isNodePlace(node)) {
					Place place = (Place) node;
					if (attribute.equals("Name")) {
						petrinetViewer.setPname(place, data);
					} else if (attribute.equals("Markierung")) {
						try {
							int marking = Integer.parseInt(data);
							petrinetViewer.setMarking(place, marking);
						} catch (NumberFormatException nfe) {
							PopUp.popError("Die Markierung muss eine natürliche Zahl sein.");
						}
					} else if (attribute.equals("Kapazität")) {
						try {
							if(data.length()==0){ //wenn Eingabefeld leer
								//Kapazität auf Max Value setzen
								petrinetViewer.setCapacity(place, Integer.MAX_VALUE);
								//Anzeige sofort aktualisieren, damit "unbegrenzt" reingeschrieben wird
								AttributePane.getInstance().displayNode(place, petrinetViewer);
							}else{
								int capacity = Integer.parseInt(data);
								petrinetViewer.setCapacity(place, capacity);
							}
							
						} catch (NumberFormatException nfe) {
							PopUp.popError("Die Kapazität muss eine natürliche Zahl sein.");
						}
					}

					/* TRANSITION */
				} else {
					Transition transition = (Transition) node;
					if (attribute.equals("Name")) {
						petrinetViewer.setTname(transition, data);
					} else if (attribute.equals("Label")) {
						petrinetViewer.setTlb(transition, data);
					} else if (attribute.equals("Renew")) {
						petrinetViewer.setRenew(transition, data);
					}
				}
			}
			petrinetViewer.smartRepaint();
		}

	}
}
