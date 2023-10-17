package project;

import java_cup.runtime.Symbol;

%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%{  
    //Cadena analizada
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn ,value);
    }

    //Cadena no analizada
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
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
    String = "\"" ([^\n\r\"] | "\\" .)* "\""

    /* delimiters */
    leftBrace = \{

    rightBrace = \}

    leftParethesis = \(

    rightParethesis = \)

    colon = :

    semiColon = ";"
    
    comma = ","

    /* comparison operators */

    lessThan = "<"

    moreThan = ">"

    ltoet = "<="

    gtoet = ">="

    awa = "=="

    /* arithmetic operators */

    more = "+"

    menus = "-"

    by = "*"

    on = "/"

    /* asignment operators */
    plus = "++"

    equalSignal = "="

    /* color */
    colorSymbol = "#"

    /* keywords */
    
    //Main method
    Public = "public"

    Static = "static"

    Void = "void"

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

    color = "Color"

    //Atmosphere
    image = "Image"

    sound = "Sound"

    //Data type
    intK = "int"

    doubleK = "double"

    stringK = "String"

    boolK = "bool"

    trueK = "true"

    falseK = "false"

    //control structures
    For = "for"

    If = "if"

    Else = "else"

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

    {String} {return new Symbol(sym.String, yychar, yyline, yytext());}

    /* operators */

    {lessThan} {return new Symbol(sym.lessThan, yychar, yyline, yytext());}

    {moreThan} {return new Symbol(sym.moreThan, yychar, yyline, yytext());}

    {ltoet} {return new Symbol(sym.ltoet, yychar, yyline, yytext());}

    {gtoet} {return new Symbol(sym.gtoet, yychar, yyline, yytext());}

    {awa} {return new Symbol(sym.awa, yychar, yyline, yytext());}

    {more} {return new Symbol(sym.more, yychar, yyline, yytext());}

    {menus} {return new Symbol(sym.menus, yychar, yyline, yytext());}

    {by} {return new Symbol(sym.by, yychar, yyline, yytext());}

    {on} {return new Symbol(sym.on, yychar, yyline, yytext());}

    {plus} {return new Symbol(sym.plus, yychar, yyline, yytext());}

    {equalSignal} {return new Symbol(sym.equalSignal, yychar, yyline, yytext());}

    /* color */

    {colorSymbol} {return new Symbol(sym.colorSymbol, yychar, yyline, yytext());}

    /* keywords */
    
    {Public} {return new Symbol(sym.Public, yychar, yyline, yytext());}

    {Static} {return new Symbol(sym.Static, yychar, yyline, yytext());}

    {Void} {return new Symbol(sym.Void, yychar, yyline, yytext());}

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

    {color} {return new Symbol(sym.color, yychar, yyline, yytext());}

    {image} {return new Symbol(sym.image, yychar, yyline, yytext());}

    {sound} {return new Symbol(sym.sound, yychar, yyline, yytext());}

    /* dataTypes */

    {intK} {return new Symbol(sym.intK, yychar, yyline, yytext());}

    {doubleK} {return new Symbol(sym.doubleK, yychar, yyline, yytext());}

    {stringK} {return new Symbol(sym.stringK, yychar, yyline, yytext());}

    {boolK} {return new Symbol(sym.boolK, yychar, yyline, yytext());}

    {trueK} {return new Symbol(sym.trueK, yychar, yyline, yytext());}

    {falseK} {return new Symbol(sym.falseK, yychar, yyline, yytext());}

    /* assignamentTypes */

    {For} {return new Symbol(sym.For, yychar, yyline, yytext());}
    
    {If} {return new Symbol(sym.If, yychar, yyline, yytext());}

    {Else} {return new Symbol(sym.Else, yychar, yyline, yytext());}

    /* errors */
    .    { return new Symbol(sym.error, yychar, yyline, yytext());}

