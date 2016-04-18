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

package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import transformation.units.utils.ExpressionGrammarBaseVisitor;
import transformation.units.utils.ExpressionGrammarLexer;
import transformation.units.utils.ExpressionGrammarParser;
import transformation.units.utils.ExpressionGrammarParser.ChoiceExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.CombinedExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.LoopExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.RuleNameExpressionContext;

public final class TransformationUnitWindow {

  private JFrame transformationUnitWindow;

  private JPanel rootPanel;
  private JPanel controlExpressionPanel;
  private JPanel parseTreePanel;
  private JPanel actionPanel;

  private JTextField controlExpression;
  private JButton executeButton;

  private static TransformationUnitWindow instance;

  private TreeViewer treeViewer;
  private ParseTree parseTree;

  List<String> ruleExecutionSequence;

  private TransformationUnitWindow() {

    this.transformationUnitWindow = new JFrame("Transformationseinheit");
    this.transformationUnitWindow.setSize(new Dimension(640, 480));

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
      BorderFactory.createEtchedBorder(), "Aktionen"));
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

    this.controlExpression = new JTextField();
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

    this.executeButton = new JButton("Transformationseinheit ausführen");
    executeButton.setEnabled(false);
    this.executeButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        executeTransformationUnit();
      }
    });
    c = new GridBagConstraints();
    c.weightx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(executeButton, c);

    JButton calculateSequenceButton =
      new JButton("Transformationsfolge berechnen");
    c = new GridBagConstraints();
    c.weightx = 1;
    c.fill = GridBagConstraints.HORIZONTAL;
    actionPanel.add(calculateSequenceButton, c);

    this.transformationUnitWindow.add(rootPanel);
  }

  /**
   * Returns the transformation unit window instance.
   *
   * @return The transformation unit window instance.
   */
  public static TransformationUnitWindow getInstance() {

    if (instance == null) {
      instance = new TransformationUnitWindow();
    }

    return instance;
  }

  private void checkControlExpression(String expression) {

    ruleExecutionSequence = null;

    ANTLRInputStream inputStream = new ANTLRInputStream(expression);
    ExpressionGrammarLexer lexer = new ExpressionGrammarLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    ExpressionGrammarParser parser = new ExpressionGrammarParser(tokenStream);
    parser.setErrorHandler(new BailErrorStrategy());

    parseTreePanel.removeAll();

    try {
      parseTree = parser.start();
      treeViewer =
        new TreeViewer(java.util.Arrays.asList(parser.getRuleNames()),
          parseTree);
      parseTreePanel.add(treeViewer);
      executeButton.setEnabled(true);

      // check if all rules of Sequence are valid rule names
      // ...

    } catch (RuntimeException e) {
      parseTree = null;
      executeButton.setEnabled(false);
      JOptionPane.showMessageDialog(
        this.transformationUnitWindow,
        "Der Kontrollausdruck konnte nicht geparsed werden. Bitte überprüfen Sie die Syntax");
    }

    transformationUnitWindow.pack();
  }

  private void executeTransformationUnit() {

    RuleExecutionVisitor visitor = new RuleExecutionVisitor();

    try {
      visitor.visit(parseTree);
      visitor.printRulesExecuted();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this.transformationUnitWindow,
        "Die Transformationseinheit konnte nicht angewendet werden.");
    }

  }

  public void showWindow() {

    if (!transformationUnitWindow.isVisible()) {
      transformationUnitWindow.setVisible(true);
    }

  }

  private class RuleExecutionVisitor
    extends ExpressionGrammarBaseVisitor<Void> {

    private Random dice = new Random();

    private ArrayList<String> rulesExecuted = new ArrayList<String>();

    public void printRulesExecuted() {

      System.out.println("rulesExecuted: " + rulesExecuted);
    }

    @Override
    public Void visitRuleNameExpression(RuleNameExpressionContext ctx) {

      String ruleName = ctx.ID().getText();

      // rule mismatch percentage ~ 10%
      boolean ruleDoesMatch = dice.nextInt(100) > 10;

      if (ruleDoesMatch) {
        System.out.println("match found for rule '" + ruleName
          + "' .. transform");
        rulesExecuted.add(ruleName);
      } else {
        System.out.println("no match found for rule '" + ruleName
          + "' .. throw exception");
        throw new RuntimeException("no match for rule '" + ruleName + "'");
      }

      return null;
    }

    @Override
    public Void visitLoopExpression(LoopExpressionContext ctx) {

      // '*' character is available -> visit children n times
      if (ctx.getChildCount() == 2) {

        // execute subtree asLongAsPossible
        for (int i = 0; i < 100; i++) {

          ArrayList<String> snapshot = new ArrayList<String>(rulesExecuted);

          try {
            // try to execute sub tree
            visit(ctx.getChild(0));
          } catch (RuntimeException e) {
            // some rule in the subtree failed
            // recover snapshot
            rulesExecuted = snapshot;
            break;
          }
        }

      } else {
        visit(ctx.getChild(0));
      }

      return null;
    }

    @Override
    public Void visitCombinedExpression(CombinedExpressionContext ctx) {

      for (ParseTree child : ctx.children) {

        if (child instanceof ChoiceExpressionContext) {
          visit(child);
        }

      }

      return null;
    }

    @Override
    public Void visitChoiceExpression(ChoiceExpressionContext ctx) {

      Iterator<ParseTree> iterator = ctx.children.iterator();

      // if childcount is > 1 then there must be a choice -> a ('|' b)
      if (ctx.getChildCount() > 1) {

        while (iterator.hasNext()) {
          ParseTree child = iterator.next();
          if (!(child instanceof LoopExpressionContext)) {
            iterator.remove();
          }
        }

        int index = dice.nextInt(ctx.children.size());
        visit(ctx.getChild(index));

      } else {
        visit(ctx.getChild(0));
      }

      return null;
    }

  }

  private class MyVisitor
    extends ExpressionGrammarBaseVisitor<Void> {

    private List<String> ruleExecutionSequence = new ArrayList<String>();
    private Random dice = new Random();

    public List<String> getRuleExecutionSequence() {

      return ruleExecutionSequence;
    }

    @Override
    public Void visitRuleNameExpression(RuleNameExpressionContext ctx) {

      ruleExecutionSequence.add(ctx.ID().getText());
      return null;
    }

    @Override
    public Void visitLoopExpression(LoopExpressionContext ctx) {

      // '*' character is available -> visit children n times
      if (ctx.getChildCount() == 2) {

        int n = dice.nextInt(5);

        for (int i = 0; i < n; i++) {
          visit(ctx.getChild(0));
        }
      } else {
        visit(ctx.getChild(0));
      }

      return null;
    }

    @Override
    public Void visitCombinedExpression(CombinedExpressionContext ctx) {

      for (ParseTree child : ctx.children) {

        if (child instanceof ChoiceExpressionContext) {
          visit(child);
        }

      }

      return null;
    }

    @Override
    public Void visitChoiceExpression(ChoiceExpressionContext ctx) {

      Iterator<ParseTree> iterator = ctx.children.iterator();

      // if childcount is > 1 then there must be a choice -> a ('|' b)
      if (ctx.getChildCount() > 1) {

        while (iterator.hasNext()) {
          ParseTree child = iterator.next();
          if (!(child instanceof LoopExpressionContext)) {
            iterator.remove();
          }
        }

        int index = dice.nextInt(ctx.children.size());
        visit(ctx.getChild(index));

      } else {
        visit(ctx.getChild(0));
      }

      return null;
    }

  }

}
