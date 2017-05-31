/*
 * BSD-Lizenz Copyright (c) Teams of 'WPP Petrinetze' of HAW Hamburg 2010 -
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

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import engine.handler.transformationunit.TransformationUnitManipulation;
import exceptions.EngineException;
import exceptions.ShowAsInfoException;
import gui.fileTree.FileTreePane;
import transformation.units.utils.ExpressionGrammarLexer;
import transformation.units.utils.ExpressionGrammarParser;
import transformation.units.utils.UnknownRuleInExpressionListener;
import transformation.units.utils.UnknownRuleInExpressionListener.UnknownRuleInExpressionException;

public final class TransformationUnitWindow {

  private final int transformationUnitId;

  private JFrame transformationUnitWindow;

  private JPanel rootPanel;
  private JPanel controlExpressionPanel;
  private JPanel parseTreePanel;
  private JPanel actionPanel;

  private JTextField controlExpression;

  private JSpinner asLongAsPossibleLimitSpinner;
  private JSpinner randomNumberOfTimesUpperRangeSpinner;
  private JButton executeButton;

  private TreeViewer treeViewer;
  private ParseTree parseTree;

  public TransformationUnitWindow(final int transformationUnitId) {

    this.transformationUnitId = transformationUnitId;

    String transformationUnitName =
      TransformationUnitManipulation.getInstance().getFileName(
        transformationUnitId);

    String transformationUnitControlExpression =
      TransformationUnitManipulation.getInstance().getControlExpression(
        transformationUnitId);

    int asLongAsPossibleExecutionLimit =
      TransformationUnitManipulation.getInstance().getAsLongAsPossibleExecutionLimit(
        transformationUnitId);

    int randomNumberOfTimesUpperRange =
      TransformationUnitManipulation.getInstance().getRandomNumberOfTimesUpperRange(
        transformationUnitId);

    this.transformationUnitWindow = new JFrame(transformationUnitName);
    this.transformationUnitWindow.setMinimumSize(new Dimension(640, 480));
    this.transformationUnitWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    GridBagConstraints c;

    rootPanel = new JPanel();
    rootPanel.setLayout(new GridBagLayout());
    rootPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

    controlExpressionPanel = new JPanel();
    controlExpressionPanel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), "Kontrollausdruck"));
    controlExpressionPanel.setLayout(new GridBagLayout());

    parseTreePanel = new JPanel();
    parseTreePanel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), "Parsebaum"));
    parseTreePanel.setLayout(new GridBagLayout());

    actionPanel = new JPanel();
    actionPanel.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), "Parameter und Simulation"));
    actionPanel.setLayout(new GridBagLayout());

    c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 0;
    c.gridx = 0;
    rootPanel.add(controlExpressionPanel, c);

    c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1;
    c.weighty = 1;
    c.gridx = 0;
    rootPanel.add(parseTreePanel, c);

    c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1;
    c.weighty = 0;
    c.gridx = 0;
    rootPanel.add(actionPanel, c);

    this.controlExpression =
      new JTextField(transformationUnitControlExpression);
    c = new GridBagConstraints();
    c.weightx = 0.8;
    c.gridx = 0;
    c.fill = GridBagConstraints.BOTH;
    controlExpressionPanel.add(controlExpression, c);

    JButton checkControlExpression = new JButton("Ausdruck überprüfen");
    checkControlExpression.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        String input = controlExpression.getText();
        checkControlExpression(input);
      }
    });
    c = new GridBagConstraints();
    c.weightx = 0.2;
    c.gridx = 1;
    c.fill = GridBagConstraints.BOTH;
    controlExpressionPanel.add(checkControlExpression, c);

    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 0;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(Box.createVerticalStrut(16), c);

    JLabel asLongAsPossibleLimitLabel =
      new JLabel("Maximale Anzahl an Iterationen bei asLongAsPossible ( ! )");
    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(asLongAsPossibleLimitLabel, c);

    this.asLongAsPossibleLimitSpinner =
      new JSpinner(new SpinnerNumberModel(asLongAsPossibleExecutionLimit, 0,
        9999, 1));
    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(this.asLongAsPossibleLimitSpinner, c);

    JLabel randomNumberOfTimesUpperRangeLabel =
      new JLabel("Obere Grenze für randomNumberOfTimes ( * )");
    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(randomNumberOfTimesUpperRangeLabel, c);

    this.randomNumberOfTimesUpperRangeSpinner =
      new JSpinner(new SpinnerNumberModel(randomNumberOfTimesUpperRange, 0,
        9999, 1));
    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(this.randomNumberOfTimesUpperRangeSpinner, c);

    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 3;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(Box.createVerticalStrut(16), c);

    this.executeButton = new JButton("Transformationseinheit ausführen");
    executeButton.setEnabled(false);
    this.executeButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        storeTransformationUnitData();
        executeTransformationUnit();
      }
    });
    c = new GridBagConstraints();
    c.weightx = 1;
    c.gridy = 5;
    c.gridwidth = 2;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(this.executeButton, c);

    this.transformationUnitWindow.add(rootPanel);

    transformationUnitWindow.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent e) {

        super.windowClosing(e);
        storeTransformationUnitData();
      }
    });
  }

  private void checkControlExpression(String expression) {

    ANTLRInputStream inputStream = new ANTLRInputStream(expression);
    ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    ExpressionGrammarParser parser = new ExpressionGrammarParser(tokenStream);

    // BailErrorStrategy immediately stops the parse operation
    // in case of syntax error
    // throws ParseCancellationException
    parser.setErrorHandler(new BailErrorStrategy());

    parseTreePanel.removeAll();

    Set<String> ruleNames =
      FileTreePane.getInstance().getRuleNamesToIdsMapping().keySet();
    parser.addParseListener(new UnknownRuleInExpressionListener(ruleNames));

    try {
      parseTree = parser.prog();
      treeViewer =
        new TreeViewer(java.util.Arrays.asList(parser.getRuleNames()),
          parseTree);
      parseTreePanel.add(treeViewer);

      executeButton.setEnabled(true);

    } catch (ParseCancellationException pex) {

      JOptionPane.showMessageDialog(
        this.transformationUnitWindow,
        "Der Kontrollausdruck konnte nicht geparsed werden. Bitte überprüfen Sie die Syntax.");
    } catch (RuntimeException e) {

      parseTree = null;
      executeButton.setEnabled(false);

      if (e.getCause() instanceof UnknownRuleInExpressionException) {

        String unknownRule =
          ((UnknownRuleInExpressionException) e.getCause()).getRuleName();

        JOptionPane.showMessageDialog(this.transformationUnitWindow,
          "Der Kontrollausdruck enthält eine unbekannte Regel '"
            + unknownRule + "'.");
      } else {
        JOptionPane.showMessageDialog(this.transformationUnitWindow,
          "Unbekannter Fehler. Bitte überprüfen Sie Ihre Eingabe");
      }
    }

    parseTreePanel.validate();
    parseTreePanel.repaint();
    transformationUnitWindow.pack();
  }

  private void executeTransformationUnit() {

    int petrinetId = PetrinetPane.getInstance().getCurrentPetrinetId();
    Map<String, Integer> ruleNameToId =
      FileTreePane.getInstance().getRuleNamesToIdsMapping();

    try {
      TransformationUnitManipulation.getInstance().executeTransformationUnit(
        transformationUnitId, petrinetId, ruleNameToId);
    } catch (EngineException e) {
      throw new ShowAsInfoException(e.getMessage());
    }

    PetrinetPane.getInstance().displayPetrinet(petrinetId, null);
  }

  private void storeTransformationUnitData() {

    String expression = this.controlExpression.getText();
    int asLongAsPossibleLimit =
      (Integer) this.asLongAsPossibleLimitSpinner.getValue();
    int randomNumberOfTimesUpperRange =
      (Integer) this.randomNumberOfTimesUpperRangeSpinner.getValue();

    TransformationUnitManipulation.getInstance().setControlExpression(
      this.transformationUnitId, expression);
    TransformationUnitManipulation.getInstance().setAsLongAsPossibleExecutionLimit(
      this.transformationUnitId, asLongAsPossibleLimit);
    TransformationUnitManipulation.getInstance().setRandomNumberOfTimesUpperRange(
      transformationUnitId, randomNumberOfTimesUpperRange);
  }

  public void show() {

    if (!transformationUnitWindow.isVisible()) {
      transformationUnitWindow.setVisible(true);
    }

  }

}
