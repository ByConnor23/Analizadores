package project;
%%
%class Lexer
%type Token
%{
    public String lexeme;
%}
    /* spaces */
    LineTerminator = \r|\n|\r\n
    InputCharacter = [^\r\n]
    WhiteSpace     = {LineTerminator} | [ \t\f]

    /* comments */
    Comment = {EndOfLineComment} | {DocumentationComment} | {NewStyleMultilineComment}

    // Comment can be the last line of the file, without line terminator.
    EndOfLineComment       = "##" {InputCharacter}* {LineTerminator}?
    DocumentationComment   = "#*" {CommentContent} "*#"
    NewStyleMultilineComment = "#*"
    CommentContent         = ( [^*] | \*+ [^#] | \*+ \# [^*] )*

    /* indentifer */

    Leter = [a-zA-Z_]+
    Digit = [0-9]+
    Identifier = {Leter}({Leter}|{Digit})*

    /* numbers + && - */
    Number = -?[0-9]+(\.[0-9]+)?

    /* string */
    String = "\"" ([^\n\r\"] | "\\" .)* "\""

    /* delimiters */
    leftBrace = \{

    rightBrace = \}

    leftParethesis = \(

    rightParethesis = \)

    colon = :

    semiColon = ";"

    /* comparison operators */
    ComparisonOperator = "<=" | ">=" | "==" | "<" | ">"

    /* arithmetic operators */
    ArithmeticOperator = "+" | "-" | "*" | "/"

    /* asignment operators */
    AssignmentOperator = "++" | "+=" | "-=" | "*=" | "/="

    /* color */
    ColorSymbol = "#"

    /* keywords */
    Keywords = public |
            static |
            void |
            main |
            define |
            int |
            double |
            string |
            bool |
            true |
            false |
            Character |
            Color |
            Image |
            Sound |
            Screen |
            setBackground |
            show |
            hide |
            playSound |
            stopSound |
            Menu |
            breaker |
            go |
            for |
            if |
            else

    {Comment} | {WhiteSpace} /* Ignore */
    
    /* Identifer */

    {Identifiers} {return token(yytext(), "IDENTIFICADOR")}

    /* delimiters */

    {leftBrace} {return token(yytext(), "LLAVE DE APERTURA")}

    {rightBrace} {return token(yytext(), "LLAVE DE CERRADURA")}

    {leftParethesis} {return token(yytext(), "PARENTESIS DE APERTURA")}

    {rightParethesis} {return token(yytext(), "PARENTESIS DE CERRADURA")}

    {colon} {return token(yytext(), "DOS PUNTOS")}

    {semiColon} {return token(yytext(), "PUNTO Y COMA")}

    /* dataType */

    {Number} {return token(yytext(), "NÚMERO")}

    {String} {return token(yytext(), "CADENA")}

    /* operators */

    {ComparisonOperator} {return token(yytext(), "OPERADOR DE COMPARACIÓN")}

    {ArithmeticOperator} {return token(yytext(), "OPERADOR ARITMETICO")}

    {AssignmentOperator} {return token(yytext(), "OPERADOR DE ASIGNACIÓN")}

    /* color */

    {ColorSymbol} {return token(yytext(),"ASIGNACIÓN DE COLOR")}

    /* keywords */
    
    {KeywordsSy} {return token(yytext(),"PALABRA RESERVADA")}

    /* errors */
    .{return token(yytext(), "ERROR TOKEN DESCONOCIDO")}

%%
