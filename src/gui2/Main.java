package gui2;

import javax.swing.SwingUtilities;

/**
 * Main Class of GUI. It simply openes and configures the main window
 *
 */
public class Main {
	
	public static void main(String[] args) {
		//invokeLater makes all UI-Updates thread safe
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow.getInstance();
            }
        });
	}
}
