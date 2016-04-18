// Generated from ExpressionGrammar.g4 by ANTLR 4.5.3

    package transformation.units.utils;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionGrammarParser}.
 */
public interface ExpressionGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionGrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ExpressionGrammarParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionGrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ExpressionGrammarParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionGrammarParser#combinedExpression}.
	 * @param ctx the parse tree
	 */
	void enterCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionGrammarParser#combinedExpression}.
	 * @param ctx the parse tree
	 */
	void exitCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionGrammarParser#choiceExpression}.
	 * @param ctx the parse tree
	 */
	void enterChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionGrammarParser#choiceExpression}.
	 * @param ctx the parse tree
	 */
	void exitChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExpressionGrammarParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void enterLoopExpression(ExpressionGrammarParser.LoopExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionGrammarParser#loopExpression}.
	 * @param ctx the parse tree
	 */
	void exitLoopExpression(ExpressionGrammarParser.LoopExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ruleNameExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void enterRuleNameExpression(ExpressionGrammarParser.RuleNameExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ruleNameExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void exitRuleNameExpression(ExpressionGrammarParser.RuleNameExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void enterBracketExpression(ExpressionGrammarParser.BracketExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 */
	void exitBracketExpression(ExpressionGrammarParser.BracketExpressionContext ctx);
}