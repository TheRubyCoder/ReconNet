package transformation.units.utils;

import exceptions.EngineException;
import gui.EngineAdapter;
import gui.PetrinetPane;
import gui.fileTree.FileTreePane;

import java.util.ArrayList;
import java.util.Random;

import transformation.units.utils.ExpressionGrammarParser.AsLongAsPossibleExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.AtomExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.ChoiceExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.CombinedExpressionContext;

public class RuleExecutionVisitor
  extends ExpressionGrammarBaseVisitor<Void> {

  private Random dice = new Random();

  private ArrayList<String> rulesExecuted = new ArrayList<String>();

  @Override
  public Void visitAtomExpression(AtomExpressionContext ctx) {

    String ruleName = ctx.IDENTIFIER().getText();

    // rule mismatch percentage ~ 10%
    boolean ruleDoesMatch = true;// dice.nextInt(100) > 10;

    if (ruleDoesMatch) {

      int ruleId = FileTreePane.getInstance().getRuleIdByRuleName(ruleName);
      ArrayList<Integer> ruleIds = new ArrayList<Integer>();
      ruleIds.add(ruleId);

      try {
        EngineAdapter.getSimulation().transform(
          PetrinetPane.getInstance().getCurrentPetrinetId(), ruleIds, 1);
      } catch (EngineException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

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
  public Void visitChoiceExpression(ChoiceExpressionContext ctx) {

    boolean random = dice.nextBoolean();

    if (random) {
      visit(ctx.left);
    } else {
      visit(ctx.right);
    }

    return null;
  }

  @Override
  public Void visitCombinedExpression(CombinedExpressionContext ctx) {

    visit(ctx.left);
    visit(ctx.right);
    return null;
  }

  @Override
  public Void visitAsLongAsPossibleExpression(
    AsLongAsPossibleExpressionContext ctx) {

    for (int i = 0; i < 100; i++) {

      ArrayList<String> snapshot = new ArrayList<String>(rulesExecuted);

      try {
        // try to execute the subtree
        visit(ctx.left);
      } catch (RuntimeException e) {
        // some rule in the subtree failed
        // recover snapshot
        rulesExecuted = snapshot;
        break;
      }

    }

    return null;
  }

  public void printRulesExecuted() {

    System.out.println("rulesExecuted: " + rulesExecuted);
  }

}
