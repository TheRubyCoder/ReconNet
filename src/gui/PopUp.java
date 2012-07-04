package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Utility class for pop up windows
 * 
 */
public class PopUp {

	/** Utility class */
	private PopUp() {
	}

	/**
	 * Pops up a "under construction" window
	 */
	public static void popUnderConstruction(String function) {
		UnderConstructionPopUp.pop(function);
	}

	/**
	 * Pops up an error displaying window with a <code>message</code>
	 * 
	 * @param message
	 */
	public static void popError(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message,
				"Ein Fehler ist aufgetreten", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Pops up an error displaying window with the message of the
	 * <code>thrown</code> Exception
	 */
	public static void popError(Throwable thrown) {
		JOptionPane.showMessageDialog(new JFrame(), thrown.toString() + "\n\t"
				+ thrown.getStackTrace()[0].toString(),
				"Ein Fehler ist aufgetreten", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Pops up an warning displaying window with the message of the
	 * <code>thrown</code> Exception
	 */
	public static void popWarning(Throwable thrown) {
		JOptionPane.showMessageDialog(new JFrame(), thrown.getMessage(),
				"Ein Fehler ist aufgetreten", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Pops up an info displaying window with the message of the
	 * <code>thrown</code> Exception
	 */
	public static void popInfo(Throwable thrown) {
		JOptionPane.showMessageDialog(new JFrame(), thrown.getMessage(),
				"Ein Fehler ist aufgetreten", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * This may be used for features that are planned to be finnished in the
	 * near future
	 * 
	 */
	private static class UnderConstructionPopUp {

		private JFrame frame;

		private UnderConstructionPopUp(String title) {
			frame = new JFrame(title);
			// ImageIcon icon = new ImageIcon("src/gui/icons/newPetrinet.png");
			ImagePanel underConstructionPanel = new ImagePanel(
					"src/gui/icons/underConstructionWithText.png");
			frame.add(underConstructionPanel);
			frame.pack();
			frame.setVisible(true);
		}

		public static void pop(String title) {
			new UnderConstructionPopUp(title);
		}
	}

	/**
	 * Panel for displaying an image
	 * 
	 */
	private static class ImagePanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6232258106891598165L;
		private Image img;

		public ImagePanel(String img) {
			this(new ImageIcon(img).getImage());
		}

		public ImagePanel(Image img) {
			this.img = img;
			Dimension size = new Dimension(img.getWidth(null),
					img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

	}

}