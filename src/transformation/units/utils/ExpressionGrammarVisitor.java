// Generated from ExpressionGrammar.g4 by ANTLR 4.5.3

    package transformation.units.utils;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExpressionGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExpressionGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExpressionGrammarParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ExpressionGrammarParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionGrammarParser#combinedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionGrammarParser#choiceExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ExpressionGrammarParser#loopExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopExpression(ExpressionGrammarParser.LoopExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ruleNameExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleNameExpression(ExpressionGrammarParser.RuleNameExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bracketExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#atomExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketExpression(ExpressionGrammarParser.BracketExpressionContext ctx);
}