grammar ExpressionGrammar;		

@header {
    package transformation.units.utils;
}

prog
	: expression EOF
	;

expression
	: '(' expression ')'						# parenthesesExpression
	| left=expression '*'						# loopExpression
	| left=expression '|' right=expression		# choiceExpression
	| left=expression ';' right=expression		# combinedExpression
	| IDENTIFIER								# atomExpression
	;
	
IDENTIFIER 
	: [a-zA-Z_] [a-zA-Z_0-9]*
	;