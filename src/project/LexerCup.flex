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
    identifer = {leter}({leter}|{digit})*

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
    
    comma = ","

    /* comparison operators */
    comparisonOperator = "<=" | ">=" | "==" | "<" | ">"

    /* arithmetic operators */
    arithmeticOperator = "+" | "-" | "*" | "/"

    /* asignment operators */
    plus = "++"

    /* color */
    colorSymbol = "#"

    /* keywords */
    
    //Main method
    public = "public"

    static = "static"

    void = "void"

    main = "main"

    //Keywords new
    define = "define"

    character = "Character"
    
    screen = "Screen"

    background = "setBackground"

    sSound = "stopSound"

    pSound = "playSound"

    hide = "hide"
    
    show = "show"

    menu = "Menu"

    go = "go"

    breaker = "breaker"

    //Atmosphere
    image = "Image"

    sound = "Sound"

    //Data type
    dataType = "int" | "double" | "string" | "bool"

    booleanType = "true" |  "false"

    //control structures
    for = "for"

    if = "if"

    else = "else"

%%
    {comment} | {whiteSpace} 
    
    /* Identifer */

    {identifer} {return new Symbol(sym.Identifer, yychar, yyline, yytext());}

    /* delimiters */

    {leftBrace} {return new Symbol(sym.leftBrace, yychar, yyline, yytext());}

    {rightBrace} {return new Symbol(sym.rightBrace, yychar, yyline, yytext());}

    {leftParethesis} {return new Symbol(sym.leftParethesis, yychar, yyline, yytext());}

    {rightParethesis} {return new Symbol(sym.rightParethesis, yychar, yyline, yytext());}

    {colon} {return new Symbol(sym.colon, yychar, yyline, yytext());}

    {semiColon} {return new Symbol(sym.semiColon, yychar, yyline, yytext());}

    {comma} {return new Symbol(sym.comma, yychar, yyline, yytext());}

    /* dataType */

    {number} {return new Symbol(sym.number, yychar, yyline, yytext());}

    {string} {return new Symbol(sym.string, yychar, yyline, yytext());}

    /* operators */

    {comparisonOperator} {return new Symbol(sym.comparisonOperator, yychar, yyline, yytext());}

    {arithmeticOperator} {return new Symbol(sym.arithmeticOperator, yychar, yyline, yytext());}

    {plus} {return new Symbol(sym.plus, yychar, yyline, yytext());}

    {equalSignal} {return new Symbol(sym.equalSignal, yychar, yyline, yytext());}

    /* color */

    {colorSymbol} {return new Symbol(sym.colorSymbol, yychar, yyline, yytext());}

    /* keywords */
    
    {public} {return new Symbol(sym.public, yychar, yyline, yytext());}

    {static} {return new Symbol(sym.static, yychar, yyline, yytext());}

    {void} {return new Symbol(sym.void, yychar, yyline, yytext());}

    {main} {return new Symbol(sym.main, yychar, yyline, yytext());}

    {define} {return new Symbol(sym.define, yychar, yyline, yytext());}

    {character} {return new Symbol(sym.character, yychar, yyline, yytext());}

    {screen} {return new Symbol(sym.screen, yychar, yyline, yytext());}

    {background} {return new Symbol(sym.background, yychar, yyline, yytext());}

    {sSound} {return new Symbol(sym.sSound, yychar, yyline, yytext());}

    {pSound} {return new Symbol(sym.pSound, yychar, yyline, yytext());}

    {hide} {return new Symbol(sym.hide, yychar, yyline, yytext());}

    {show} {return new Symbol(sym.show , yychar, yyline, yytext());}

    {menu} {return new Symbol(sym.menu , yychar, yyline, yytext());}

    {go} {return new Symbol(sym.go, yychar, yyline, yytext());}

    {breaker} {return new Symbol(sym.breaker, yychar, yyline, yytext());}

    {image} {return new Symbol(sym.image, yychar, yyline, yytext());}

    {sound} {return new Symbol(sym.sound, yychar, yyline, yytext());}

    {dataType} {return new Symbol(sym.dataType, yychar, yyline, yytext());}

    {booleanType} {return new Symbol(sym.booleanType, yychar, yyline, yytext());}

    {for} {return new Symbol(sym.for, yychar, yyline, yytext());}
    
    {if} {return new Symbol(sym.if, yychar, yyline, yytext());}

    {else} {return new Symbol(sym.else, yychar, yyline, yytext());}

    /* errors */
    .    { return new Symbol(sym.error, yychar, yyline, yytext());}

