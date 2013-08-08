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

import static gui.Style.EDITOR_PANE_BORDER;
import static gui.Style.EDITOR_PANE_DIMENSION;
import static gui.Style.EDITOR_PANE_LAYOUT;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/** The Panel that contains buttons for Editor-Modes like Pick, Translate, AddTransition, AddPlace etc */
class EditorPane {
	
	/** Internal JPanel */
	private JPanel editorPane;
	
	/** The group containing all 4 buttons. Only one can be selected */
	private ButtonGroup buttonGroup;
	
	/** Button for choosing pick mode */
	private JRadioButton pickButton;
	
	/** Button for choosing create place mode */
	private JRadioButton createPlaceButton;
	
	/** Button for choosing create transition mode */
	private JRadioButton createTransitionButton;
	
	/** Button for choosing create arc mode */
	private JRadioButton createArcButton;
	
	
	
	/** Singleton instance  */
	private static EditorPane instance;
	
	/** static constructor that initializes the instance */
	static {
		instance = new EditorPane();
	}
	
	/** Private default constructor */
	private EditorPane() {
		editorPane = new JPanel();
		getEditorPane().setBorder(EDITOR_PANE_BORDER);
		getEditorPane().setLayout(EDITOR_PANE_LAYOUT);
		
		getEditorPane().setPreferredSize(EDITOR_PANE_DIMENSION);
		getEditorPane().setMinimumSize(EDITOR_PANE_DIMENSION);
		
		initiateRadioButton();
	}
	

	/**
	 * Returns the singleton instance
	 * @return
	 */
	public static EditorPane getInstance(){
		return instance;
	}
	
	/** Returns the internal JPanel */
	private JPanel getEditorPane(){
		return editorPane;
	}
	
	/**
	 * Adds the EditorPane to a frame
	 * @param frame
	 */
	public void addTo(JPanel frame){
		frame.add(getEditorPane(),BorderLayout.LINE_START);
	}
	
	/** Initiates a ButtonGroup with the 4 radio buttons in it */
	private void initiateRadioButton() {
		pickButton = initiateRadioPickButton();
		createArcButton = initiateRadioArcButton();
		createPlaceButton = initiateRadioPlaceButton();
		createTransitionButton = initiateRadioTransitionButton();
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(pickButton);
		buttonGroup.add(createArcButton);
		buttonGroup.add(createPlaceButton);
		buttonGroup.add(createTransitionButton);
	}

	/** Initiates the transition radio button with text and tool tip */
	private JRadioButton initiateRadioTransitionButton() {
		createTransitionButton = new JRadioButton("Transition einfügen");
		createTransitionButton.setMnemonic(KeyEvent.VK_4);
		createTransitionButton.setToolTipText("(Alt+4) In diesem Modus fügen Sie mit einem Klick eine neue Transition hinzu");
		
		// the action command is used later on to identify wich button is selected
		createTransitionButton.setActionCommand("transition");
		getEditorPane().add(createTransitionButton);
		
		return createTransitionButton;
	}
	
	/** Initiates the place radio button with text and tool tip */
	private JRadioButton initiateRadioPlaceButton() {
		createPlaceButton = new JRadioButton("Stelle einfügen");
		createPlaceButton.setMnemonic(KeyEvent.VK_3);
		createPlaceButton.setToolTipText("(Alt+3) In diesem Modus fügen Sie mit einem Klick eine neue Stelle hinzu");
		
		// the action command is used later on to identify wich button is selected
		createPlaceButton.setActionCommand("place");
		getEditorPane().add(createPlaceButton);
		
		return createPlaceButton;
	}
	
	/** Initiates the arc radio button with text and tool tip */
	private JRadioButton initiateRadioArcButton() {
		createArcButton = new JRadioButton("Kante einfügen");
		createArcButton.setMnemonic(KeyEvent.VK_2);
		createArcButton.setToolTipText("(Alt+2) In diesem Modus fügen Sie mit einem Klick eine neue Kante hinzu");
		
		// the action command is used later on to identify wich button is selected
		createArcButton.setActionCommand("arc");
		getEditorPane().add(createArcButton);
		
		return createArcButton;
	}


	/** Initiates the pick radio button with text and tool tip */
	private JRadioButton initiateRadioPickButton(){
		pickButton = new JRadioButton("Auswählen");
		pickButton.setMnemonic(KeyEvent.VK_1);
		pickButton.setSelected(true);
		pickButton.setToolTipText("(Alt+1) Editieren und verschieben Sie Stellen und Transitionen " +
				"indem sie auf sei klicken. Mit drag & drop auf eine weiße Stelle verschieben " +
				"sie das ganze Petrinetze");
		
		// the action command is used later on to identify wich button is selected
		pickButton.setActionCommand("pick");
		getEditorPane().add(pickButton);
		
		return pickButton;
	}
	
	/** Returns the mode that is currently selected */
	public EditorMode getCurrentMode(){
		return EditorMode.valueOf(buttonGroup.getSelection().getActionCommand().toUpperCase());
	}
	
	/** Enumerable for editor modes*/
	static enum EditorMode{
		PLACE, TRANSITION, ARC, PICK
	}
	
	/** Disable all Buuton in this Panel*/
	void setTheHoleEditorPanelDisable(){
		createArcButton.setEnabled(false);
		createPlaceButton.setEnabled(false);
		createTransitionButton.setEnabled(false);
		pickButton.setEnabled(false);
	}
	
	
	/** Enable all Buuton in this Panel*/
	void setTheHoleEditorPanelEnable(){
		createArcButton.setEnabled(true);
		createPlaceButton.setEnabled(true);
		createTransitionButton.setEnabled(true);
		pickButton.setEnabled(true);
	}

}

