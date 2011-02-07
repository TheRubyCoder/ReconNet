
package gui;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author steffen
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                }
                MainGUI gui = new MainGUI();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
        
    }

   


}
