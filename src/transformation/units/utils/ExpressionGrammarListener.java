// Generated from ExpressionGrammar.g4 by ANTLR 4.5.3

    package transformation.units.utils;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionGrammarParser}.
 */
public interface ExpressionGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExpressionGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(ExpressionGrammarParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExpressionGrammarParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(ExpressionGrammarParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesesExpression(ExpressionGrammarParser.ParenthesesExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesesExpression(ExpressionGrammarParser.ParenthesesExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code combinedExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code combinedExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCombinedExpression(ExpressionGrammarParser.CombinedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpression(ExpressionGrammarParser.AtomExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpression(ExpressionGrammarParser.AtomExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code choiceExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code choiceExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitChoiceExpression(ExpressionGrammarParser.ChoiceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code asLongAsPossibleExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAsLongAsPossibleExpression(ExpressionGrammarParser.AsLongAsPossibleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code asLongAsPossibleExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAsLongAsPossibleExpression(ExpressionGrammarParser.AsLongAsPossibleExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code randomNumberOfTimesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterRandomNumberOfTimesExpression(ExpressionGrammarParser.RandomNumberOfTimesExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code randomNumberOfTimesExpression}
	 * labeled alternative in {@link ExpressionGrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitRandomNumberOfTimesExpression(ExpressionGrammarParser.RandomNumberOfTimesExpressionContext ctx);
}