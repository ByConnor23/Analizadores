package project;
%%
%class Lexer
%type Token
%{
    public String lexeme;

    public Token token(String text, String type) {
        return new Token(type, text);
    }
%}
    /* spaces */
    lineTerminator = \r|\n|\r\n
    inputCharacter = [^\r\n]
    whiteSpace     = {lineTerminator} | [ \t\f]

    /* comments */
    comment = {endOfLineComment} | {documentationComment} | {newStyleMultilineComment}

    // Comment can be the last line of the file, without line terminator.
    endOfLineComment       = "##" {inputCharacter}* {lineTerminator}?
    documentationComment   = "#*" {commentContent} "*#"
    newStyleMultilineComment = "#*"
    commentContent         = ( [^*] | \*+ [^#] | \*+ \# [^*] )*

    /* indentifer */

    leter = [a-zA-Z_]+
    digit = [0-9]+
    

    /* numbers + && - */
    number = -?[0-9]+(\.[0-9]+)?

    /* string */
    string = "\"" ([^\n\r\"] | "\\" .)* "\""

    /* delimiters */
    leftBrace = \{

    rightBrace = \}

    leftParethesis = \(

    rightParethesis = \)

    colon = :

    semiColon = ";"

    /* comparison operators */
    comparisonOperator = "<=" | ">=" | "==" | "<" | ">"

    /* arithmetic operators */
    arithmeticOperator = "+" | "-" | "*" | "/"

    /* asignment operators */
    assignmentOperator = "++" | "+=" | "-=" | "*=" | "/="

    /* color */
    colorSymbol = "#"

    /* keywords */
    keywords = "public" |
            "static" |
            "void" |
            "main" |
            "define" |
            "int" |
            "double" |
            "string" |
            "bool" |
            "true" |
            "false" |
            "Character" |
            "Color" |
            "Image" |
            "Sound" |
            "Screen" |
            "setBackground" |
            "show" |
            "hide" |
            "playSound" |
            "stopSound" |
            "Menu" |
            "breaker" |
            "go" |
            "for" |
            "if" |
            "else"

%%
    {comment} | {whiteSpace} 
    
    /* Identifer */

    {leter}({leter}|{digit})* {return token(yytext(), "IDENTIFICADOR");}

    /* delimiters */

    {leftBrace} {return token(yytext(), "LLAVE DE APERTURA");}

    {rightBrace} {return token(yytext(), "LLAVE DE CERRADURA");}

    {leftParethesis} {return token(yytext(), "PARENTESIS DE APERTURA");}

    {rightParethesis} {return token(yytext(), "PARENTESIS DE CERRADURA");}

    {colon} {return token(yytext(), "DOS PUNTOS");}

    {semiColon} {return token(yytext(), "PUNTO Y COMA");}

    /* dataType */

    {number} {return token(yytext(), "NÚMERO");}

    {string} {return token(yytext(), "CADENA");}

    /* operators */

    {comparisonOperator} {return token(yytext(), "OPERADOR DE COMPARACIÓN");}

    {arithmeticOperator} {return token(yytext(), "OPERADOR ARITMETICO");}

    {assignmentOperator} {return token(yytext(), "OPERADOR DE ASIGNACIÓN");}

    /* color */

    {colorSymbol} {return token(yytext(),"ASIGNACIÓN DE COLOR");}

    /* keywords */
    
    {keywords} {return token(yytext(),"PALABRA RESERVADA");}

    /* errors */
    .    { return token(yytext(), "ERROR TOKEN DESCONOCIDO");}


