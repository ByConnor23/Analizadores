package project;
import static project.TokenType.*;
%%
%class Lexer
%type Token
%{
    public TokenType lexeme;

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
    {comment} | {whiteSpace} {/* ignore */}
    
    /* Identifer */

    {identifer} {lexeme=yytext(); return IDENTIFICADOR;}

    /* delimiters */

    {leftBrace} {lexeme=yytext(); return LLAVE_DE_APERTURA;}

    {rightBrace} {lexeme=yytext(); return LLAVE_DE_CERRADURA;}

    {leftParethesis} {lexeme=yytext(); return PARENTESIS_DE_APERTURA;}

    {rightParethesis} {lexeme=yytext(); return PARENTESIS_DE_CERRADURA;}

    {colon} {lexeme=yytext(); return DOS_PUNTOS;}

    {semiColon} {lexeme=yytext(); return PUNTO_Y_COMA;}

    {comma} {lexeme=yytext(); return COMA;}

    /* dataType */

    {number} {lexeme=yytext(); return NUMERO;}

    {chaising} {lexeme=yytext(); return CADENA;}

    /* operators */

    
    {lessThan} {lexeme=yytext();return MENOR_QUE;}

    {moreThan} {lexeme=yytext();return MAYOR_QUE;}

    {ltoet} {lexeme=yytext();return MENOR_O_IGUAL_QUE;}

    {gtoet} {lexeme=yytext();return MAYOR_O_IGUAL_QUE;}

    {awa} {lexeme=yytext();return IGUALACION;}

    {more} {lexeme=yytext();return MAS;}

    {menus} {lexeme=yytext();return MENOS;}

    {by} {lexeme=yytext();return POR;}

    {on} {lexeme=yytext();return SOBRE;}

    {plus} {lexeme=yytext(); return OPERADOR_DE_AUMENTO;}

    {equalSignal} {lexeme=yytext(); return ASIGNACION;}

    /* color */

    {colorSymbol} {lexeme=yytext();return ASIGNACION_DE_COLOR;}

    /* keywords */
    
    {public} {lexeme=yytext();return PUBLIC;}

    {static} {lexeme=yytext();return STATIC;}

    {void} {lexeme=yytext();return VOID;}

    {main} {lexeme=yytext();return MAIN;}

    {define} {lexeme=yytext();return DEFINE;}

    {character} {lexeme=yytext();return CHARACTER;}

    {screen} {lexeme=yytext();return SCREEN;}

    {background} {lexeme=yytext();return BACKGROUND;}

    {sSound} {lexeme=yytext();return STOP_SOUND;}

    {pSound} {lexeme=yytext();return PLAY_SOUND;}

    {hide} {lexeme=yytext();return HIDE;}
    
    {show} {lexeme=yytext();return SHOW;}

    {menu} {lexeme=yytext();return MENU;}

    {go} {lexeme=yytext();return GO;}

    {breaker} {lexeme=yytext();return BREAKER;}

    {color} {lexeme=yytext();return COLOR;}

    {image} {lexeme=yytext();return IMAGE;}

    {sound} {lexeme=yytext();return SOUND;}

    /* dataTypes */

    {intK} {lexeme=yytext(); return INT;}

    {doubleK} {lexeme=yytext(); return DOUBLE;}

    {stringK} {lexeme=yytext(); return STRING;}

    {boolK} {lexeme=yytext(); return BOOLEANO;}

    {trueK} {lexeme=yytext();return TRUE;}

    {falseK} {lexeme=yytext(); return FALSE;}

    /* assignamentTypes */

    {for} {lexeme=yytext();return FOR;}

    {if} {lexeme=yytext();return IF;}

    {else} {lexeme=yytext();return ELSE;}

    /* errors */
    .    { lexeme=yytext(); return ERROR_TOKEN_DESCONOCIDO;}


