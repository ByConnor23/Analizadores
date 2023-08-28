package project;

import java_cup.runtime.Symbol;

%%
%class LexerCup
%type java_cup.runtime.Symbol;
%cup
%full
%line
%char
%{  
    //Cadena analizada
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolum ,value);
    }

    //Cadena no analizada
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolum);
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

    {leter}({leter}|{digit})* {return new Symbol(sym.Identifer, yychar, yyline, yytext());}

    /* delimiters */

    {leftBrace} {return new Symbol(sym.leftBrace, yychar, yyline, yytext());}

    {rightBrace} {return new Symbol(sym.rightBrace, yychar, yyline, yytext());}

    {leftParethesis} {return new Symbol(sym.leftParethesis, yychar, yyline, yytext());}

    {rightParethesis} {return new Symbol(sym.rightParethesis, yychar, yyline, yytext());}

    {colon} {return new Symbol(sym.colon, yychar, yyline, yytext());}

    {semiColon} {return new Symbol(sym.semiColon, yychar, yyline, yytext());}

    /* dataType */

    {number} {return new Symbol(sym.number, yychar, yyline, yytext());}

    {string} {return new Symbol(sym.string, yychar, yyline, yytext());}

    /* operators */

    {comparisonOperator} {return new Symbol(sym.comparisonOperator, yychar, yyline, yytext());}

    {arithmeticOperator} {return new Symbol(sym.arithmeticOperator, yychar, yyline, yytext());}

    {assignmentOperator} {return new Symbol(sym.assignmentOperator, yychar, yyline, yytext());}

    /* color */

    {colorSymbol} {return new Symbol(sym.colorSymbol, yychar, yyline, yytext());}

    /* keywords */
    
    {keywords} {return new Symbol(sym.keywords, yychar, yyline, yytext());}

    /* errors */
    .    { return new Symbol(sym.error, yychar, yyline, yytext());}

