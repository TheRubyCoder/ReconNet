package transformation.units.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import petrinet.model.Petrinet;
import transformation.units.utils.ExpressionGrammarParser.AsLongAsPossibleExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.AtomExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.ChoiceExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.CombinedExpressionContext;
import transformation.units.utils.ExpressionGrammarParser.ProgContext;
import transformation.units.utils.ExpressionGrammarParser.RandomNumberOfTimesExpressionContext;
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
  private int asLongAsPossibleExecutionLimit;
  private int randomNumberOfTimesUpperRange;

  public TransformationUnitExecutionVisitor(int petrinetId,
    Map<String, Integer> ruleNameToId, int asLongAsPossibleExecutionLimit,
    int randomNumberOfTimesUpperRange) {

    this.petrinetId = petrinetId;
    this.ruleNameToId = ruleNameToId;
    this.asLongAsPossibleExecutionLimit = asLongAsPossibleExecutionLimit;
    this.randomNumberOfTimesUpperRange = randomNumberOfTimesUpperRange;
  }

  /**
   * Method will be executed once. If something went wrong in the subtree
   * which has not been caught, the transformation unit failed and the
   * previous petrinet is restored. A new exception is thrown to be shown in
   * the GUI
   */
  @Override
  public Void visitProg(ProgContext ctx) {

    Petrinet petrinet =
      SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet();
    JungData jungData =
      SessionManager.getInstance().getPetrinetData(petrinetId).getJungData();

    System.out.println(".. execute transformation unit");

    int snapshotId = Math.abs(dice.nextInt());
    System.out.println("... create snapshot [" + snapshotId + "]");

    Petrinet petrinetSnapshot = new Petrinet(petrinet);
    JungData jungDataSnapshot = new JungData(jungData, petrinetSnapshot);

    List<String> executedRulesSnapshot = new ArrayList<String>(executedRules);

    try {
      visit(ctx.expression());
      visit(ctx.EOF());
      System.out.println(".. execution successful");
      System.out.println(".. executed rules: " + this.toString());
    } catch (RuntimeException e) {
      System.out.println("... recover snapshot [" + snapshotId + "]");
      System.out.println(".. execution failed");
      SessionManager.getInstance().replacePetrinetData(petrinetId,
        petrinetSnapshot, jungDataSnapshot);
      this.executedRules = executedRulesSnapshot;
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
  public Void visitAsLongAsPossibleExpression(
    AsLongAsPossibleExpressionContext ctx) {

    for (int i = 0; i < asLongAsPossibleExecutionLimit; i++) {

      // make snapshot of current petri net
      // .. snapshot = petrinet.clone

      Petrinet petrinet =
        SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet();
      JungData jungData =
        SessionManager.getInstance().getPetrinetData(petrinetId).getJungData();

      int snapshotId = Math.abs(dice.nextInt());
      System.out.println("... create snapshot [" + snapshotId + "]");

      Petrinet petrinetSnapshot = new Petrinet(petrinet);
      JungData jungDataSnapshot = new JungData(jungData, petrinetSnapshot);

      List<String> executedRulesSnapshot =
        new ArrayList<String>(executedRules);

      try {
        // try to execute the subtree
        visit(ctx.left);
      } catch (RuntimeException e) {
        // some rule in the subtree failed
        // recover snapshot
        // .. petrinet = snapshot
        System.out.println("... recover snapshot [" + snapshotId + "]");
        SessionManager.getInstance().replacePetrinetData(petrinetId,
          petrinetSnapshot, jungDataSnapshot);
        this.executedRules = executedRulesSnapshot;
        break;
      }

    }

    return null;
  }

  @Override
  public Void visitRandomNumberOfTimesExpression(
    RandomNumberOfTimesExpressionContext ctx) {

    // random number between 0 and upper range (inklusive)
    int numberOfTimes = dice.nextInt(this.randomNumberOfTimesUpperRange + 1);

    for (int i = 0; i < numberOfTimes; i++) {

      // make snapshot of current petri net
      // .. snapshot = petrinet.clone

      Petrinet petrinet =
        SessionManager.getInstance().getPetrinetData(petrinetId).getPetrinet();
      JungData jungData =
        SessionManager.getInstance().getPetrinetData(petrinetId).getJungData();

      int snapshotId = Math.abs(dice.nextInt());
      System.out.println("... create snapshot [" + snapshotId + "]");

      Petrinet petrinetSnapshot = new Petrinet(petrinet);
      JungData jungDataSnapshot = new JungData(jungData, petrinetSnapshot);

      List<String> executedRulesSnapshot =
        new ArrayList<String>(executedRules);

      try {
        // try to execute the subtree
        visit(ctx.left);
      } catch (RuntimeException e) {
        // some rule in the subtree failed
        // recover snapshot
        // .. petrinet = snapshot
        System.out.println("... recover snapshot [" + snapshotId + "]");
        SessionManager.getInstance().replacePetrinetData(petrinetId,
          petrinetSnapshot, jungDataSnapshot);
        this.executedRules = executedRulesSnapshot;
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
      System.out.print("... execute rule: " + ruleName);

      SimulationHandler.getInstance().transform(this.petrinetId, ruleId);
      executedRules.add(ruleName);
    } catch (EngineException e) {
      // no Match was found. Throw Exception up the parseTree
      // e.printStackTrace();
      System.out.println(" .. failed");
      throw new RuntimeException(e);
    }

    System.out.println(" .. success");

    return null;
  }

  @Override
  public String toString() {

    return executedRules.toString();
  }
}
