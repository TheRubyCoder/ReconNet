package gui2;

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
	
	private static AttributePane instance;
	
	static {
		instance = new AttributePane();
	}
	
	public static AttributePane getInstance(){
		return instance;
	}
	
	/** Table containing data for current node */
	private JTable table;
	
	private JPanel attributePane;
	
	/** Id of the node that is currently shown */
	private int currentNodeId;
	
	private AttributePane(){
		table = initiateTable();
		showNode(1);
		attributePane = initiateAttributePane(table);
	}

	private JPanel initiateAttributePane(JTable table) {
		JPanel pane = new JPanel();
		pane.setBorder(ATTRIBUTE_PANE_BORDER);
		pane.setBounds(ATTRIBUTE_PANE_X, 
				ATTRIBUTE_PANE_Y, 
				ATTRIBUTE_PANE_WIDTH, 
				ATTRIBUTE_PANE_HEIGHT);
		pane.setLayout(new GridLayout(1,1));
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(false);
		
		pane.add(scrollPane);
		return pane;
	}

	private JTable initiateTable() {
		
		JTable table = new JTable();//content,columnNames);
		return table;
	}
	
	public void showNode(int nodeId){
		currentNodeId = nodeId;
		//TODO: find out type of node
		//TODO: get attributes of node
		TableModel model = new PlaceTableModel("mock1","mock2","mock3");
		model.addTableModelListener(new TableListener());
		table.setModel(model);
	}
	
	public void addTo(JPanel frame){
		frame.add(attributePane);
	}
	
	/** Table Model for displaying and editing places. All cells need to be String<br/>
	 * A TableModel is needed to tell Swing what cells are editable */
	private static class PlaceTableModel extends AbstractTableModel{
		
		private String[] columnNames = {"Atrribut","Wert"};
		
		private String[][] data = {
				{"Id",""},
				{"Name",""},
				{"Markierung",""}
		};
		
		public PlaceTableModel(String id, String name, String mark) {
			data[0][1] = id;
			data[1][1] = name;
			data[2][1] = mark;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return data[0].length;
		}
		
		public String getColumnName(int col) {
	        return columnNames[col];
	    }

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
	        data[row][col] = (String)value;
	        fireTableCellUpdated(row, col);
	    }
		
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
	
	private static class TransitionTableModel {
		
	}
	
	private static class ArcTableModel {
		
	}
	
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
