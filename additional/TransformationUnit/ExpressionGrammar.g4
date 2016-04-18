grammar ExpressionGrammar;

@header {
    package transformation.units.utils;
}

@lexer::members {

	@Override
	public void recover(LexerNoViableAltException e) {
		throw new RuntimeException(e);
	}

}

start	
	:	combinedExpression EOF
	;

combinedExpression
	:	choiceExpression (';' choiceExpression)*
	;

choiceExpression
	:	loopExpression ('|' loopExpression)*
	;

loopExpression
	:	atomExpression '*'?
	;

atomExpression
	:	ID												# ruleNameExpression
	|	'(' combinedExpression ')'						# bracketExpression
	;


	
ID
	: ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9')*
	;
	

