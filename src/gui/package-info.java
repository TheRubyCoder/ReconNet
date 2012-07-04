/**
 * The gui obviosly manages the graphical user interface. It defines the       
 * windows and buttons that are used for human input and output. Therefore it
 * is divided into 6 big areas (panes), each of which standing for a task of
 * the ReConNet. These are
 * <ul>
 * <li> 
 * {@link gui.EditorPane} - Task: pick an {@link gui.EditorPane.EditorMode editor mode}
 * </li><li> 
 * {@link gui.AttributePane} - Task: read and set attributes of graph elements 
 * </li><li>
 * {@link gui.SimulationPane} - Task: run simulations on the petrinet
 * </li><li>
 * {@link gui.FilePane} - Task: save and load petrinets and rules. Display loaded files
 * </li><li>
 * {@link gui.PetrinetPane} - Task: Display the petrinet
 * </li><li> 
 * and {@link gui.RulePane} - Task: Display the rule
 * </li>
 * </ul>																	   #
 * Also an important part of the gui is the {@link gui.PetrinetViewer}, which is
 * a very special {@link javax.swing.JPanel} that uses JUNG to actually display
 * the underlying graph of a petrinet or rule and also to handle mouse events
 */
package gui;