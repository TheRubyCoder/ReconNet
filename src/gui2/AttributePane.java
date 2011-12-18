package gui2;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import static gui2.Style.*;

/** Singleton class that represents the attribute chart at the middle top */
public class AttributePane {
	
	/** Singleton instancce*/
	private static AttributePane instance;
	
	//static constructor that initiates the singleton instance and constants
	static {
		instance = new AttributePane();
	}
	
	/** Returns the only instance of the AttributePane */
	public static AttributePane getInstance(){
		return instance;
	}
	
	/** Table containing data for current node */
	private JTable table;
	
	/** Panel for the Attributes*/
	private JPanel attributePane;
	
	/** Id of the node that is currently shown */
	private int currentNodeId;
	
	/** Private Constructor that configures the pane */
	private AttributePane(){
		table = initiateTable();
		showNode(1);
		attributePane = initiateAttributePane(table);
	}

	/** Initialize the Attributepane and set the Dimension and Layout
	 *  Add the Table to the Pane*/
	private JPanel initiateAttributePane(JTable table) {
		JPanel pane = new JPanel();
		pane.setBorder(ATTRIBUTE_PANE_BORDER);
		pane.setMinimumSize(ATTRIBUTE_PANE_DIMENSION);
		pane.setLayout(new GridLayout(1,1));
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(false);
		
		pane.add(scrollPane);
		return pane;
	}

	/** Initiate the Table for Attributepane*/
	private JTable initiateTable() {
		JTable table = new JTable();//content,columnNames);
		return table;
	}
	
	/** Fill the Table with Data of the selected Node */
	public void showNode(int nodeId){
		currentNodeId = nodeId;
		//TODO: find out type of node
		//TODO: get attributes of node
//		TableModel model = new PlaceTableModel("mock1","mock2","mock3");
		TableModel model = new TransitionTableModel("mock1", "mock2", "mock3", "mock4");
//		TableModel model = new ArcTableModel("mock1", "mock2");
		model.addTableModelListener(new TableListener());
		table.setModel(model);
	}
	
	/**
	 * Add this Panel to given frame
	 * @param frame wich the pane add to
	 */
	public void addTo(JPanel frame){
		frame.add(attributePane, BorderLayout.CENTER);
	}
	
	/** Abstract super class for refactoring the three Table Models for Place, Transition and Arc<br/>
	 * Table Model for displaying and editing places. All cells need to be String<br/>
	 * A TableModel is needed to tell Swing what cells are editable */
	private static abstract class AbstractPetriTableModel extends AbstractTableModel {
		
		protected abstract String[][] getData();
		
		/** Head of table */
		protected String[] columnNames = {"Atrribut","Wert"};
		
		
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
		 * Set wich rows and columns are editable. the first row and the first column 
		 * are not editable
		 */
		@Override
		public boolean isCellEditable(int row, int col) {
//			only bottom right is editable
			if(row > 0 && col > 0){
				return true;
			} else {
				return false;
			}
	    }
		
	}
	
	/**  Class for the Table with Place Attributes */
	private static class PlaceTableModel extends AbstractPetriTableModel{
		
		/**
		 * The General Data for the Placetable
		 */
		private String[][] data = {
				{"Id",""},
				{"Name",""},
				{"Markierung",""}
		};
		
		/** Initiates the table with actual data for id, name, and mark */
		public PlaceTableModel(String id, String name, String mark) {
			data[0][1] = id;
			data[1][1] = name;
			data[2][1] = mark;
		}
		
		/** Returns all data (whole table except the head) */
		@Override
		protected String[][] getData() {
			return data;
		}
	}
	
	/**  Class for the Table with Transition Attributes */
	private static class TransitionTableModel extends AbstractPetriTableModel{
		
		/**
		 * The General Data for the Transitiontable
		 */
		private String[][] data = {
				{"Id",""},
				{"Name",""},
				{"Label",""},
				{"Renew",""}
		};
		
		/** Initiates the table with actual data for id, name, laben and renew */
		public TransitionTableModel(String id, String name, String label, String renew) {
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
	}
	
	/**  Class for the Table with Arc Attributes */
	private static class ArcTableModel extends AbstractPetriTableModel{
		
		/**
		 * The General Data for the Arctable
		 */
		private String[][] data = {
				{"Id",""},
				{"Name",""},
		};
		
		/** Initiates the table with actual data for id an name */
		public ArcTableModel(String id, String name) {
			data[0][1] = id;
			data[1][1] = name;
		}
		
		/** Returns all data (whole table except the head) */
		@Override
		protected String[][] getData() {
			return data;
		}
	}
	
	/**  Class for Tablelistener to make Userchanges possible */
	private static class TableListener implements TableModelListener {

		@Override
		public void tableChanged(TableModelEvent e) {
			int row = e.getFirstRow();
	        int column = e.getColumn();
	        TableModel model = (TableModel)e.getSource();
	        String data = (String)model.getValueAt(row, column);
	        String attribute = (String)model.getValueAt(row, column-1);
	        System.out.println("Der Wert " + attribute + " wurde auf " + data + " gesetzt.");
		}
		
	}
}
