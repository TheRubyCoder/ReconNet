package gui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SwingUtilities;

import exceptions.ShowAsInfoException;
import exceptions.ShowAsWarningException;


/**
 * Main Class of GUI. It simply openes and configures the main window
 * 
 */
public class Main {
	
	/** With this EventQueue all uncaught exceptions are printed in the console and displayed to the user */
	private static class EventQueueProxy extends EventQueue {
		protected void dispatchEvent(AWTEvent newEvent) {
			try {
				super.dispatchEvent(newEvent);
			} catch (ShowAsWarningException warning){
				PopUp.popWarning(warning);
			} catch (ShowAsInfoException info){
				PopUp.popInfo(info);
			} catch (Throwable t) {
				t.printStackTrace();
				PopUp.popError(t);
			}
		}
	}

	public static void main(String[] args) {
		
		// Make SWING use the petrinet event queue
		EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		queue.push(new EventQueueProxy());
		
		// invokeLater makes all UI-Updates thread safe
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow.getInstance();
			}
		});
	}
}
