package gui2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PopUp {
	
	/** Utility class */
	private PopUp() {}

	
	public static void popUnderConstruction(String function){
		UnderConstructionPopUp.pop(function);
	}
	
	public static void popError(String message){
		JOptionPane.showMessageDialog(new JFrame(), message, "Ein Fehler ist aufgetreten",
		        JOptionPane.ERROR_MESSAGE);
	}
	
	private static class UnderConstructionPopUp {

		private JFrame frame;

		private UnderConstructionPopUp(String title) {
			frame = new JFrame(title);
			// ImageIcon icon = new ImageIcon("src/gui2/icons/newPetrinet.png");
			ImagePanel underConstructionPanel = new ImagePanel(
					"src/gui2/icons/underConstructionWithText.png");
			frame.add(underConstructionPanel);
			frame.pack();
			frame.setVisible(true);
		}

		public static void pop(String title) {
			new UnderConstructionPopUp(title);
		}
	}
	
	private static class ImagePanel extends JPanel {

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