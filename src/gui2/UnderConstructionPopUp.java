package gui2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UnderConstructionPopUp {

	private JFrame frame;

	private UnderConstructionPopUp() {
		frame = new JFrame("In Arbeit");
//		ImageIcon icon = new ImageIcon("src/gui2/icons/newPetrinet.png");
		ImagePanel underConstructionPanel = new ImagePanel("src/gui2/icons/underConstructionWithText.png");
		frame.add(underConstructionPanel);
		frame.pack();
		frame.setVisible(true);
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
	
	public static void main(String[] args) {
		new UnderConstructionPopUp();
	}
	
	public static void pop(){
		new UnderConstructionPopUp();
	}
}
