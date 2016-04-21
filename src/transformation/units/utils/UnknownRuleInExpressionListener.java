package transformation.units.utils;

import java.util.Set;

import transformation.units.utils.ExpressionGrammarParser.AtomExpressionContext;

public class UnknownRuleInExpressionListener
extends ExpressionGrammarBaseListener {

  private Set<String> ruleNames;

  public UnknownRuleInExpressionListener(Set<String> ruleNames) {

    this.ruleNames = ruleNames;
  }

  @Override
  public void exitAtomExpression(AtomExpressionContext ctx) {

    if (!ruleNames.contains(ctx.getText())) {
      throw new RuntimeException(new UnknownRuleInExpressionException(
        ctx.getText()));
    }
  }

  public class UnknownRuleInExpressionException
    extends Exception {

    private static final long serialVersionUID = 1L;
    private String ruleName;

    public UnknownRuleInExpressionException(String ruleName) {

      this.ruleName = ruleName;
    }

    public String getRuleName() {

      return this.ruleName;
    }

  }

}
