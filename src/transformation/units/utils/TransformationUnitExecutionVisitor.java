package transformation.units.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import petrinet.model.Petrinet;
import transformation.units.utils.ExpressionGrammarParser.AtomExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.ChoiceExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.CombinedExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.LoopExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.ProgContext;
import engine.data.JungData;
import engine.handler.simulation.SimulationHandler;
import engine.session.SessionManager;
import exceptions.EngineException;

public class TransformationUnitExecutionVisitor
  extends ExpressionGrammarBaseVisitor<Void> {

  private Random dice = new Random();
  private List<String> executedRules = new ArrayList<String>();

  private int petrinetId;
  private Map<String, Integer> ruleNameToId;

  public TransformationUnitExecutionVisitor(int petrinetId,
    Map<String, Integer> ruleNameToId) {

    this.petrinetId = petrinetId;
    this.ruleNameToId = ruleNameToId;
  }

  /**
   * Method will be executed once. If something went wrong in the subtree
   * which has not been caught, the transformation unit failed and the
   * previous petrinet is restored. A new exception is thrown to be shown in
   * the GUI
   */
  @Override
  public Void visitProg(ProgContext ctx) {

    System.out.println("visitProg(ProgContext ctx)");

    Petrinet petrinet =
      SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet();
    JungData jungData =
      SessionManager.getInstance().getPetrinetData(petrinetId).getJungData();

    Petrinet petrinetSnapshot = new Petrinet(petrinet);
    JungData jungDataSnapshot = new JungData(jungData, petrinetSnapshot);

    try {
      visit(ctx.expression());
      visit(ctx.EOF());
    } catch (RuntimeException e) {
      SessionManager.getInstance().replacePetrinetData(petrinetId,
        petrinetSnapshot, jungDataSnapshot);
      throw e;
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
  public Void visitLoopExpression(LoopExpressionContext ctx) {

    for (int i = 0; i < 100; i++) {

      // make snapshot of current petri net
      // .. snapshot = petrinet.clone

      Petrinet petrinet =
        SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet();
      JungData jungData =
        SessionManager.getInstance().getPetrinetData(petrinetId).getJungData();

      Petrinet petrinetSnapshot = new Petrinet(petrinet);
      JungData jungDataSnapshot = new JungData(jungData, petrinetSnapshot);

      try {
        // try to execute the subtree
        visit(ctx.left);
      } catch (RuntimeException e) {
        // some rule in the subtree failed
        // recover snapshot
        // .. petrinet = snapshot
        SessionManager.getInstance().replacePetrinetData(petrinetId,
          petrinetSnapshot, jungDataSnapshot);
        break;
      }

    }

    return null;
  }

  @Override
  public Void visitAtomExpression(AtomExpressionContext ctx) {

    String ruleName = ctx.IDENTIFIER().getText();
    int ruleId = this.ruleNameToId.get(ruleName);

    try {
      SimulationHandler.getInstance().transform(this.petrinetId, ruleId);
      executedRules.add(ruleName);
    } catch (EngineException e) {
      // no Match was found. Throw Exception up the parseTree
      throw new RuntimeException(e);
    }

    return null;
  }

  @Override
  public String toString() {

    return executedRules.toString();
  }
}
