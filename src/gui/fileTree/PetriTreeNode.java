/*
 * BSD-Lizenz Copyright © Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
 * 2013; various authors of Bachelor and/or Masterthesises --> see file
 * 'authors' for detailed information Weiterverbreitung und Verwendung in
 * nichtkompilierter oder kompilierter Form, mit oder ohne Veränderung, sind
 * unter den folgenden Bedingungen zulässig: 1. Weiterverbreitete
 * nichtkompilierte Exemplare müssen das obige Copyright, diese Liste der
 * Bedingungen und den folgenden Haftungsausschluss im Quelltext enthalten. 2.
 * Weiterverbreitete kompilierte Exemplare müssen das obige Copyright, diese
 * Liste der Bedingungen und den folgenden Haftungsausschluss in der
 * Dokumentation und/oder anderen Materialien, die mit dem Exemplar verbreitet
 * werden, enthalten. 3. Weder der Name der Hochschule noch die Namen der
 * Beitragsleistenden dürfen zum Kennzeichnen oder Bewerben von Produkten, die
 * von dieser Software abgeleitet wurden, ohne spezielle vorherige
 * schriftliche Genehmigung verwendet werden. DIESE SOFTWARE WIRD VON DER
 * HOCHSCHULE* UND DEN BEITRAGSLEISTENDEN OHNE JEGLICHE SPEZIELLE ODER
 * IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT, DIE UNTER ANDEREM
 * EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR
 * EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE HOCHSCHULE* ODER DIE
 * BEITRAGSLEISTENDEN FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,
 * SPEZIELLEN, BEISPIELHAFTEN ODER FOLGESCHÄDEN (UNTER ANDEREM VERSCHAFFEN VON
 * ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT;
 * VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),
 * WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER, OB IN
 * VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTER HANDLUNG (INKLUSIVE
 * FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE
 * BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE
 * MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND. Redistribution
 * and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. Neither the name of the
 * University nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written
 * permission. THIS SOFTWARE IS PROVIDED BY THE UNIVERSITY* AND CONTRIBUTORS
 * “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE UNIVERSITY* OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. * bedeutet / means: HOCHSCHULE FÜR ANGEWANDTE
 * WISSENSCHAFTEN HAMBURG / HAMBURG UNIVERSITY OF APPLIED SCIENCES
 */

package gui.fileTree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A special type of {@link DefaultMutableTreeNode} holding some additional
 * information for ReconNet.
 */
public class PetriTreeNode
extends DefaultMutableTreeNode {

  /**
   * The {@link NodeType} of this {@link PetriTreeNode}.
   */
  private NodeType nodeType;

  /**
   * This node is displayed with a checkbox (rule node).
   */
  private boolean hasCheckBox;

  /**
   * State of checkbox.
   */
  private boolean checked;

  /**
   * This node is selected and highlighted in tree.
   */
  private boolean selected;

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 602894489830122046L;

  /**
   * The text to display in the GUI
   */
  private String displayText;

  /**
   * Constructor.
   *
   * @param nodeType
   *        Type of this node.
   * @param userObject
   *        user object.
   */
  PetriTreeNode(NodeType nodeType, Object userObject) {

    super(userObject);
    init(nodeType);
  }

  public PetriTreeNode(NodeType nodeType, String displayText,
    Object userObject) {

    this(nodeType, userObject);
    this.displayText = displayText;
  }

  /**
   * Initializes this node.
   *
   * @param nodeType
   *        Type of this node.
   */
  private void init(NodeType nodeType) {

    this.checked = false;
    this.nodeType = nodeType;
    if (this.nodeType.equals(NodeType.RULE)) {
      this.setHasCheckBox(true);
    }
  }

  /**
   * Calls super.toString().
   */
  public String toString() {

    if (this.displayText != null) {
      return this.displayText;
    } else {
      return super.toString();
    }
  }

  /**
   * Compares type of this node with given node type.
   *
   * @param nodeType
   *        {@link NodeType} to compare this node with.
   * @return Returns whether this node is of type of given type.
   */
  public boolean isNodeType(NodeType nodeType) {

    return this.nodeType.equals(nodeType);
  }

  /**
   * Returns true if this node should be displayed with checkbox (rule node).
   *
   * @return true if this node should be displayed with checkbox (rule node).
   */
  public boolean isHasCheckBox() {

    return hasCheckBox;
  }

  /**
   * Sets this node to be displayed with checkbox or not.
   *
   * @param hasCheckBox
   *        True = display with checkbox, false = display without checkbox.
   */
  public void setHasCheckBox(boolean hasCheckBox) {

    this.hasCheckBox = hasCheckBox;
  }

  /**
   * Returns checked state of checkbox.
   *
   * @return checked state of checkbox.
   */
  public boolean isChecked() {

    return checked;
  }

  /**
   * Changes the checked state of checkbox.
   *
   * @param checked
   *        new checked state.
   */
  public void setChecked(boolean checked) {

    this.checked = checked;
  }

  /**
   * returns selected state of this node (marked to be selected in tree).
   *
   * @return selected state.
   */
  public boolean isSelected() {

    return selected;
  }

  /**
   * sets whether this node should be displayed selected or not.
   *
   * @param selected
   *        new seleted state.
   */
  public void setSelected(boolean selected) {

    this.selected = selected;
  }
}
