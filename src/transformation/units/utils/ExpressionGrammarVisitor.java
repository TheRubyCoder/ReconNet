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
	 * Visit a parse tree produced by {@link ExpressionGrammarParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(ExpressionGrammarParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenthesesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesesExpression(ExpressionGrammarParser.ParenthesesExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code combinedExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpression(ExpressionGrammarParser.AtomExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code choiceExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code asLongAsPossibleExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsLongAsPossibleExpression(ExpressionGrammarParser.AsLongAsPossibleExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code randomNumberOfTimesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRandomNumberOfTimesExpression(ExpressionGrammarParser.RandomNumberOfTimesExpressionContext ctx);
}