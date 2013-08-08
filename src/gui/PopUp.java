/*
 * BSD-Lizenz
 * Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 - 2013; various authors of Bachelor and/or Masterthesises --> see file 'authors' for detailed information
 *
 * Weiterverbreitung und Verwendung in nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind unter den folgenden Bedingungen zulässig:
 * 1.	Weiterverbreitete nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten.
 * 2.	Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese Liste der Bedingungen und den folgenden Haftungsausschluss in der Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet werden, enthalten.
 * 3.	Weder der Name der Hochschule noch die Namen der Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die von dieser Software abgeleitet wurden, ohne spezielle vorherige schriftliche Genehmigung verwendet werden.
 * DIESE SOFTWARE WIRD VON DER HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN, SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG), WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2.	Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 3.	Neither the name of the University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *   bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

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