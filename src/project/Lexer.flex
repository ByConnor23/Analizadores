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
    identifer = {leter}({leter}|{digit})*
    

    /* numbers + && - */
    number = -?[0-9]+(\.[0-9]+)?

    /* string */
    chaising = "\"" ([^\n\r\"] | "\\" .)* "\""

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

    color = "Color"

    //Atmosphere
    image = "Image"

    sound = "Sound"

    //Data type
    intK = "int"

    doubleK = "double"

    stringK = "string"

    boolK = "bool"

    trueK = "true"

    falseK = "false"

    //control structures
    for = "for"

    if = "if"

    else = "else"

%%
    {comment} | {whiteSpace} 
    
    /* Identifer */

    {identifer} {return token(yytext(), "IDENTIFICADOR");}

    /* delimiters */

    {leftBrace} {return token(yytext(), "LLAVE DE APERTURA");}

    {rightBrace} {return token(yytext(), "LLAVE DE CERRADURA");}

    {leftParethesis} {return token(yytext(), "PARENTESIS DE APERTURA");}

    {rightParethesis} {return token(yytext(), "PARENTESIS DE CERRADURA");}

    {colon} {return token(yytext(), "DOS PUNTOS");}

    {semiColon} {return token(yytext(), "PUNTO Y COMA");}

    {comma} {return token(yytext(), "COMA");}

    /* dataType */

    {number} {return token(yytext(), "NÚMERO");}

    {chaising} {return token(yytext(), "CADENA");}

    /* operators */

    
    {lessThan} {return token(yytext(),"MENOR QUE");}

    {moreThan} {return token(yytext(),"MAYOR QUE");}

    {ltoet} {return token(yytext(),"MENOR O IGUAL QUE");}

    {gtoet} {return token(yytext(),"MAYOR O IGUAL QUE");}

    {awa} {return token(yytext(),"IGUALACIÓN");}

    {more} {return token(yytext(),"MAS");}

    {menus} {return token(yytext(),"MENOS");}

    {by} {return token(yytext(),"POR");}

    {on} {return token(yytext(),"SOBRE");}

    {plus} {return token(yytext(), "OPERADOR DE AUMENTO");}

    {equalSignal} {return token(yytext(), "ASIGNACIÓN");}

    /* color */

    {colorSymbol} {return token(yytext(),"ASIGNACIÓN DE COLOR");}

    /* keywords */
    
    {public} {return token(yytext(),"PUBLIC");}

    {static} {return token(yytext(),"STATIC");}

    {void} {return token(yytext(),"VOID");}

    {main} {return token(yytext(),"MAIN");}

    {define} {return token(yytext(),"DEFINE");}

    {character} {return token(yytext(),"CHARACTER");}

    {screen} {return token(yytext(),"SCREEN");}

    {background} {return token(yytext(),"BACKGROUND");}

    {sSound} {return token(yytext(),"STOP SOUND");}

    {pSound} {return token(yytext(),"PLAY SOUND");}

    {hide} {return token(yytext(),"HIDE");}
    
    {show} {return token(yytext(),"SHOW");}

    {menu} {return token(yytext(),"MENU");}

    {go} {return token(yytext(),"GO");}

    {breaker} {return token(yytext(),"BREAKER");}

    {color} {return token(yytext(),"COLOR");}

    {image} {return token(yytext(),"IMAGE");}

    {sound} {return token(yytext(),"SOUND");}

    /* dataTypes */

    {intK} {return token(yytext(), "INT");}

    {doubleK} {return token(yytext(), "DOUBLE");}

    {stringK} {return token(yytext(), "STRING");}

    {boolK} {return token(yytext(), "BOOLEANO");}

    {trueK} {return token(yytext(),"TRUE");}

    {falseK} {return token(yytext(), "FALSE");}

    /* assignamentTypes */

    {for} {return token(yytext(),"FOR");}

    {if} {return token(yytext(),"IF");}

    {else} {return token(yytext(),"ELSE");}

    /* errors */
    .    { return token(yytext(), "ERROR TOKEN DESCONOCIDO");}


