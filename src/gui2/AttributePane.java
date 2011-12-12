package gui2;

import javax.swing.JScrollPane;
import javax.swing.JTable;

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
	
	private JScrollPane attributeContainer;
	
	/** Id of the node that is currently shown */
	private int currentNodeId;
	
	private AttributePane(){
		table = initiateTable();
	}

	private JTable initiateTable() {
		String[] columnNames = {"Attribute", "Wert"};
		
		String[][] content = {
				{"Id","1"},
				{"Name","Wecker"},
				{"Markierung","5"}
		};
		JTable table = new JTable(content, columnNames);
		return table;
	}
	
	public void showNode(int nodeId){
		currentNodeId = nodeId;
		//TODO: Adjust table
	}
	
//	public void addTo(Frame frame){
//		frame.add
//	}
}
