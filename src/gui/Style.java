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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/** Utility class for constants of gui styling */
public class Style {

    private Style() {
    }

    /* *************************************
     * Main Window ************************************
     */

    /** Total width of main window */
    public static final int TOTAL_WIDTH = 1000;

    /** Total height of main window */
    public static final int TOTAL_HEIGHT = 700;

    /** Dimension of Header */
    public static final Dimension HEADER_DIMENSION = new Dimension(1000, 125);

    /** Dimension of left Panel */
    public static final Dimension LEFT_PANEL_DIMENSION = new Dimension(200, 575);

    /* *************************************
     * Commons ************************************
     */

    /** Height of Panels with buttons at top of gui */
    private static final int HEIGHT_TOP_ELEMENTS = 125;

    private static final int SPACING_BUTTONS = 5;

    public static final int BUTTON_HEIGHT = 30;

    public static final int BUTTON_WIDTH = 150;

    private static final int INSET_TOP = 20;

    private static final int INSET_LEFT = 10;

    public static final BorderLayout FILE_PANE_LAYOUT = new BorderLayout();

    /* *************************************
     * Editor Pane ************************************
     */

    /** Size of the Editor Pane */
    public static final Dimension EDITOR_PANE_DIMENSION = new Dimension(INSET_LEFT + 137, HEIGHT_TOP_ELEMENTS);

    /** Border of editor pane */
    public static final Border EDITOR_PANE_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Editieren");

    /** Layout of editor pane */
    public static final GridLayout EDITOR_PANE_LAYOUT = new GridLayout(4, 1, 10, 10);

    /* *************************************
     * Attribute pane ************************************
     */

    public static final Dimension ATTRIBUTE_PANE_DIMENSION = new Dimension(225, HEIGHT_TOP_ELEMENTS);

    public static final Border ATTRIBUTE_PANE_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Knoten-Attribute");

    /* *************************************
     * SimulationHandler pane ************************************
     */

    public static final Dimension SIMULATION_PANE_DIMENSION = new Dimension(INSET_LEFT + BUTTON_WIDTH * 3 + SPACING_BUTTONS * 3, HEIGHT_TOP_ELEMENTS);

    public static final Point SIMULATION_PANE_BUTTON_ONESTEP_LOCATION = new Point(0 + INSET_LEFT, 0 + INSET_TOP);

    public static final Dimension SIMULATION_PANE_BUTTON_ONESTEP_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

    public static final Point SIMULATION_PANE_BUTTON_KSTEPS_LOCATION = new Point(INSET_LEFT + BUTTON_WIDTH + BUTTON_WIDTH / 3 + SPACING_BUTTONS, 0 + INSET_TOP);

    public static final Dimension SIMULATION_PANE_BUTTON_KSTEPS_SIZE = new Dimension((BUTTON_WIDTH / 3) * 2, BUTTON_HEIGHT);

    public static final Point SIMULATION_PANE_BUTTON_TRANSFORM_LOCATION = new Point(0 + INSET_LEFT, BUTTON_HEIGHT + INSET_TOP + SPACING_BUTTONS);

    public static final Dimension SIMULATION_PANE_BUTTON_TRANSFORM_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

    public static final Point SIMULATION_PANE_BUTTON_STARTSIMULATION_LOCATION = new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP
            + SPACING_BUTTONS + BUTTON_HEIGHT);

    public static final Point SIMULATION_PANE_SPINNER_LOCATION = new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP);

    public static final Dimension SIMULATION_PANE_SPINNER_SIZE = new Dimension(BUTTON_WIDTH / 3, BUTTON_HEIGHT);

    public static final Point SIMULATION_PANE_SLIDER_LOCATION = new Point(INSET_LEFT + (BUTTON_WIDTH + SPACING_BUTTONS) * 2, 0 + INSET_TOP + BUTTON_HEIGHT
            + SPACING_BUTTONS);

    public static final Dimension SIMULATION_PANE_SLIDER_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT * 2 + SPACING_BUTTONS);

    public static final Point SIMULATION_PANE_COMBOBOX_LOCATION = new Point(INSET_LEFT + BUTTON_WIDTH + SPACING_BUTTONS, 0 + INSET_TOP
            + (BUTTON_HEIGHT + SPACING_BUTTONS) * 2);

    public static final Dimension SIMULATION_PANE_COMBOBOX_SIZE = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT - 3);

    public static final Border SIMULATION_PANE_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Simulieren");

    public static final Border SIMULATION_PANE_SPEED_SLIDER_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Geschwindigkeit");

    public static final ImageIcon SIMULATION_START_ICON = new ImageIcon("src/gui/icons/simulationPlay.png");
    public static final ImageIcon SIMULATION_START_ICON_DISABLE = new ImageIcon("src/gui/icons/simulationPlayDisable.png");
    public static final ImageIcon SIMULATION_START_ICON_PRESSED = new ImageIcon("src/gui/icons/simulationPlayPressed.png");
    public static final ImageIcon SIMULATION_PAUSE_ICON = new ImageIcon("src/gui/icons/simulationPause.png");
    public static final ImageIcon SIMULATION_PAUSE_ICON_PRESSED = new ImageIcon("src/gui/icons/simulationPausePressed.png");

    /* *************************************
     * Petrinet pane ************************************
     */

    public static final Border PETRINET_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Petrinetz");

    public static final GridLayout PETRINET_PANE_LAYOUT = new GridLayout(1, 1);

    public static final Color FONT_COLOR_DARK = Color.DARK_GRAY;

    public static final Color FONT_COLOR_BRIGHT = Color.LIGHT_GRAY;

    public static final Color NODE_BORDER_COLOR = Color.DARK_GRAY;

    public static int NODE_SIZE_DEFAULT = 50;

    /**
     * Returns the distance that must be among nodes
     * 
     * @return
     */
    public static double getNodeDistanceDefault() {
        return NODE_SIZE_DEFAULT / 1.5d;
    }

    /**
     * The factor by which selected nodes are drawn bigger to highlight them
     */
    public static final double FACTOR_SELECTED_NODE = 1.2;

    /* *************************************
     * petrinet file pane ************************************
     */

    public static final Border PETRINET_FILE_PANE_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Speichern/Laden - Petrinetze");

    public static final int SOUTH_PANEL_HEIHT = 35;

    public static final int FILE_PANE_ICON_BUTTON_SIZE = 30;
    public static final int FILE_PANE_ICON_SPACING_SIZE = 9;

    public static final ImageIcon NEW_PETRINET_ICON = new ImageIcon("src/gui/icons/newPetrinet.png");
    public static final ImageIcon NEW_PETRINET_PRESSED_ICON = new ImageIcon("src/gui/icons/newPetrinetPressed.png");
    public static final ImageIcon NEW_PETRINET_DISABLED_ICON = new ImageIcon("src/gui/icons/newPetrinetDisabled.png");

    public static final ImageIcon LOAD_PETRINET_ICON = new ImageIcon("src/gui/icons/loadPertinet.png");
    public static final ImageIcon LOAD_PETRINET_PRESSED_ICON = new ImageIcon("src/gui/icons/loadPetrinetPressed.png");
    public static final ImageIcon LOAD_PETRINET_DISABLED_ICON = new ImageIcon("src/gui/icons/loadPertinetDisabled.png");

    public static final ImageIcon SAVE_PETRINET_ICON = new ImageIcon("src/gui/icons/savePertinet.png");
    public static final ImageIcon SAVE_PETRINET_PRESSED_ICON = new ImageIcon("src/gui/icons/savePertinetPressed.png");
    public static final ImageIcon SAVE_PETRINET_DISABLED_ICON = new ImageIcon("src/gui/icons/savePertinetDisabled.png");

    public static final ImageIcon SAVE_AS_PETRINET_ICON = new ImageIcon("src/gui/icons/saveAsPertinet.png");
    public static final ImageIcon SAVE_AS_PETRINET_PRESSED_ICON = new ImageIcon("src/gui/icons/saveAsPertinetPressed.png");
    public static final ImageIcon SAVE_AS_PETRINET_DISABLED_ICON = new ImageIcon("src/gui/icons/saveAsPertinetDisabled.png");

    public static final ImageIcon DELETE_PETRINET = new ImageIcon("src/gui/icons/deletePertinet.png");
    public static final ImageIcon DELETE_PETRINET_PRESSED_ICON = new ImageIcon("src/gui/icons/deletePetrinetPressed.png");
    public static final ImageIcon DELETE_PETRINET_DISABLED_ICON = new ImageIcon("src/gui/icons/deletePertinetDisabled.png");

    public static final int NEW_BUTTON_X = 0;
    public static final int NEW_BUTTON_Y = 0;

    public static final int LOAD_BUTTON_X = NEW_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
    public static final int LOAD_BUTTON_Y = 0;

    public static final int SAVE_BUTTON_X = LOAD_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
    public static final int SAVE_BUTTON_Y = 0;

    public static final int SAVE_AS_BUTTON_X = SAVE_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
    public static final int SAVE_AS_BUTTON_Y = 0;

    public static final int DELETE_BUTTON_X = SAVE_AS_BUTTON_X + FILE_PANE_ICON_BUTTON_SIZE + FILE_PANE_ICON_SPACING_SIZE;
    public static final int DELETE_BUTTON_Y = 0;

    /* *************************************
     * petrinet file tree pane ************************************
     */
    public static final Dimension FILE_TREE_PANE_PREFERRED_SIZE = new Dimension(200, 200);
    public static final String TREE_STRING_ROOT = "root";
    public static final String TREE_STRING_NET = "Nets";
    public static final String TREE_STRING_RULE = "Rules";

    // net root entry
    public static final String TREE_MENU_ADD_NET = "Add Net";
    public static final String TREE_MENU_LOAD_NET = "Load Net";

    // rule root entry
    public static final String TREE_MENU_ADD_RULE = "Add Rule";
    public static final String TREE_MENU_LOAD_RULE = "Load Rule";

    // net entry
    public static final String TREE_MENU_REMOVE_NET = "Remove";
    public static final String TREE_MENU_RELOAD_NET = "Reload";

    // rule entry
    public static final String TREE_MENU_REMOVE_RULE = "Remove";
    public static final String TREE_MENU_RELOAD_RULE = "Reload";
    public static final String TREE_MENU_ADD_NAC = "Add NAC";

    // all tree entries
    public static final String TREE_MENU_SAVE_ALL = "Save All";
    public static final String TREE_MENU_SAVE = "Save";

    /* *************************************
     * rule pane ************************************
     */

    public static final GridLayout RULE_PANEL_LAYOUT = new GridLayout(1, 3);

    public static final Border RULE_PANE_BORDER_NAC = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "NAC");

    public static final Border RULE_PANE_BORDER = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Regel");

    public static final Border RULE_PANE_BORDER_L = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "L");

    public static final Border RULE_PANE_BORDER_K = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "K");

    public static final Border RULE_PANE_BORDER_R = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "R");
}
