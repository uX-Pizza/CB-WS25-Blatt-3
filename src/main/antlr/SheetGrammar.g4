grammar SheetGrammar;


// Parser
start   : stmt* EOF ;

stmt    : vardecl NL
        | while NL
        | cond NL ;

vardecl : IDENT ':=' expr ;

while   : 'while' NL* expr NL* 'do' NL* stmt* NL* 'end' ;
cond    : 'if' NL* expr NL* 'do' NL*  stmt+ NL* ('else' NL* 'do' NL* stmt+)? NL* 'end' ;

expr    : expr '*' expr
        | expr '/' expr
        |  expr '+' expr
        |  expr '-' expr
        |  expr '==' expr
        |  expr '!=' expr
        |  expr '>' expr
        |  expr '<' expr
        |  IDENT
        |  INT
        |  STRING
        |  '(' expr ')' ;


// Lexer
IDENT   : [a-zA-Z_]+ [a-zA-Z0-9_]* ;
INT     : [0-9]+ ;
STRING  :  '"' (~[\n\r"])* '"' ;

NL      : [\n] ;
COMMENT :  '#' ~[\n\r]* -> skip ;
WS      : [ \t]+ -> skip ;
