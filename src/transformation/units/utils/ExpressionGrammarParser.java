// Generated from ExpressionGrammar.g4 by ANTLR 4.5.3

    package transformation.units.utils;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, ID=6;
	public static final int
		RULE_start = 0, RULE_combinedExpression = 1, RULE_choiceExpression = 2, 
		RULE_loopExpression = 3, RULE_atomExpression = 4;
	public static final String[] ruleNames = {
		"start", "combinedExpression", "choiceExpression", "loopExpression", "atomExpression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'|'", "'*'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ExpressionGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExpressionGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public CombinedExpressionContext combinedExpression() {
			return getRuleContext(CombinedExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(ExpressionGrammarParser.EOF, 0); }
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			combinedExpression();
			setState(11);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CombinedExpressionContext extends ParserRuleContext {
		public List<ChoiceExpressionContext> choiceExpression() {
			return getRuleContexts(ChoiceExpressionContext.class);
		}
		public ChoiceExpressionContext choiceExpression(int i) {
			return getRuleContext(ChoiceExpressionContext.class,i);
		}
		public CombinedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_combinedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterCombinedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitCombinedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitCombinedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CombinedExpressionContext combinedExpression() throws RecognitionException {
		CombinedExpressionContext _localctx = new CombinedExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_combinedExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			choiceExpression();
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(14);
				match(T__0);
				setState(15);
				choiceExpression();
				}
				}
				setState(20);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ChoiceExpressionContext extends ParserRuleContext {
		public List<LoopExpressionContext> loopExpression() {
			return getRuleContexts(LoopExpressionContext.class);
		}
		public LoopExpressionContext loopExpression(int i) {
			return getRuleContext(LoopExpressionContext.class,i);
		}
		public ChoiceExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choiceExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterChoiceExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitChoiceExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitChoiceExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChoiceExpressionContext choiceExpression() throws RecognitionException {
		ChoiceExpressionContext _localctx = new ChoiceExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_choiceExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			loopExpression();
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(22);
				match(T__1);
				setState(23);
				loopExpression();
				}
				}
				setState(28);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopExpressionContext extends ParserRuleContext {
		public AtomExpressionContext atomExpression() {
			return getRuleContext(AtomExpressionContext.class,0);
		}
		public LoopExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterLoopExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitLoopExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitLoopExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopExpressionContext loopExpression() throws RecognitionException {
		LoopExpressionContext _localctx = new LoopExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_loopExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			atomExpression();
			setState(31);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(30);
				match(T__2);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomExpressionContext extends ParserRuleContext {
		public AtomExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomExpression; }
	 
		public AtomExpressionContext() { }
		public void copyFrom(AtomExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RuleNameExpressionContext extends AtomExpressionContext {
		public TerminalNode ID() { return getToken(ExpressionGrammarParser.ID, 0); }
		public RuleNameExpressionContext(AtomExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterRuleNameExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitRuleNameExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitRuleNameExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BracketExpressionContext extends AtomExpressionContext {
		public CombinedExpressionContext combinedExpression() {
			return getRuleContext(CombinedExpressionContext.class,0);
		}
		public BracketExpressionContext(AtomExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).enterBracketExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExpressionGrammarListener ) ((ExpressionGrammarListener)listener).exitBracketExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ExpressionGrammarVisitor ) return ((ExpressionGrammarVisitor<? extends T>)visitor).visitBracketExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomExpressionContext atomExpression() throws RecognitionException {
		AtomExpressionContext _localctx = new AtomExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atomExpression);
		try {
			setState(38);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new RuleNameExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				match(ID);
				}
				break;
			case T__3:
				_localctx = new BracketExpressionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				match(T__3);
				setState(35);
				combinedExpression();
				setState(36);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\b+\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\3\3\3\3\3\7\3\23\n\3\f\3\16\3"+
		"\26\13\3\3\4\3\4\3\4\7\4\33\n\4\f\4\16\4\36\13\4\3\5\3\5\5\5\"\n\5\3\6"+
		"\3\6\3\6\3\6\3\6\5\6)\n\6\3\6\2\2\7\2\4\6\b\n\2\2)\2\f\3\2\2\2\4\17\3"+
		"\2\2\2\6\27\3\2\2\2\b\37\3\2\2\2\n(\3\2\2\2\f\r\5\4\3\2\r\16\7\2\2\3\16"+
		"\3\3\2\2\2\17\24\5\6\4\2\20\21\7\3\2\2\21\23\5\6\4\2\22\20\3\2\2\2\23"+
		"\26\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\5\3\2\2\2\26\24\3\2\2\2\27"+
		"\34\5\b\5\2\30\31\7\4\2\2\31\33\5\b\5\2\32\30\3\2\2\2\33\36\3\2\2\2\34"+
		"\32\3\2\2\2\34\35\3\2\2\2\35\7\3\2\2\2\36\34\3\2\2\2\37!\5\n\6\2 \"\7"+
		"\5\2\2! \3\2\2\2!\"\3\2\2\2\"\t\3\2\2\2#)\7\b\2\2$%\7\6\2\2%&\5\4\3\2"+
		"&\'\7\7\2\2\')\3\2\2\2(#\3\2\2\2($\3\2\2\2)\13\3\2\2\2\6\24\34!(";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}