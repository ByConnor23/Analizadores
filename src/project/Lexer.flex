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

    {leftBrace} {return token(yytext(), "LLAVE_DE_APERTURA");}

    {rightBrace} {return token(yytext(), "LLAVE_DE_CERRADURA");}

    {leftParethesis} {return token(yytext(), "PARENTESIS_DE_APERTURA");}

    {rightParethesis} {return token(yytext(), "PARENTESIS_DE_CERRADURA");}

    {colon} {return token(yytext(), "DOS_PUNTOS");}

    {semiColon} {return token(yytext(), "PUNTO_Y_COMA");}

    {comma} {return token(yytext(), "COMA");}

    /* dataType */

    {number} {return token(yytext(), "NUMERO");}

    {chaising} {return token(yytext(), "CADENA");}

    /* operators */

    
    {lessThan} {return token(yytext(),"MENOR_QUE");}

    {moreThan} {return token(yytext(),"MAYOR_QUE");}

    {ltoet} {return token(yytext(),"MENOR_O_IGUAL_QUE");}

    {gtoet} {return token(yytext(),"MAYOR_O_IGUAL_QUE");}

    {awa} {return token(yytext(),"IGUALACION");}

    {more} {return token(yytext(),"MAS");}

    {menus} {return token(yytext(),"MENOS");}

    {by} {return token(yytext(),"POR");}

    {on} {return token(yytext(),"SOBRE");}

    {plus} {return token(yytext(), "OPERADOR_DE_AUMENTO");}

    {equalSignal} {return token(yytext(), "ASIGNACION");}

    /* color */

    {colorSymbol} {return token(yytext(),"ASIGNACION_DE_COLOR");}

    /* keywords */
    
    {public} {return token(yytext(),"PUBLIC");}

    {static} {return token(yytext(),"STATIC");}

    {void} {return token(yytext(),"VOID");}

    {main} {return token(yytext(),"MAIN");}

    {define} {return token(yytext(),"DEFINE");}

    {character} {return token(yytext(),"CHARACTER");}

    {screen} {return token(yytext(),"SCREEN");}

    {background} {return token(yytext(),"BACKGROUND");}

    {sSound} {return token(yytext(),"STOP_SOUND");}

    {pSound} {return token(yytext(),"PLAY_SOUND");}

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
    .    { return token(yytext(), "ERROR_TOKEN_DESCONOCIDO");}


