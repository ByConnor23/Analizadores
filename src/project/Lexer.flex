package project;
import static project.TokenType.*;
%%
%class Lexer
%type Token
%{
    private int fila = 1;
    private int columna = 1;

    private Token newToken(TokenType type, String text) {
        Token token = new Token(type, text, fila, columna);
        columna += text.length();
        return token;
    }

%}
    /* spaces */
whiteSpace     = [ \t\r\n]+

/* comments */
comment = {endOfLineComment} | {documentationComment} | {newStyleMultilineComment}

// Comment can be the last line of the file, without line terminator.
endOfLineComment       = "##" [^\r\n]* (\r|\n|\r\n)?
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

    \n { fila++; columna=1;}
    \r\n?               { fila++; columna = 1; }
    [\u2028\u2029]     { fila++; columna = 1; }

    {comment} | {whiteSpace} { }

    /* delimiters */

    {leftBrace} {
                    return newToken(TokenType.LLAVE_DE_APERTURA, yytext());
                }

    {rightBrace} {
                    return newToken(TokenType.LLAVE_DE_CERRADURA, yytext());
                }

    {leftParethesis} {
                        return  newToken(TokenType.PARENTESIS_DE_APERTURA, yytext());
                    }

    {rightParethesis} {
                        return  newToken(TokenType.PARENTESIS_DE_CERRADURA, yytext());
                    }

    {colon} {
                return  newToken(TokenType.DOS_PUNTOS, yytext());
            }

    {semiColon} {
                    return newToken(TokenType.PUNTO_Y_COMA, yytext());
                }

    {comma} {
                return newToken(TokenType.COMA, yytext());
            }

    /* dataType */

    {number} {
                return newToken(TokenType.NUMERO, yytext());
            }

    {chaising} {
                    return newToken(TokenType.CADENA, yytext());
                }

    /* operators */

    
    {lessThan} {
                    return newToken(TokenType.MENOR_QUE, yytext());
                }

    {moreThan} {

                    return newToken(TokenType.MAYOR_QUE, yytext());
                }

    {ltoet} {
                return newToken(TokenType.MENOR_O_IGUAL_QUE, yytext());
            }

    {gtoet} {
                return newToken(TokenType.MAYOR_O_IGUAL_QUE, yytext());
            }

    {awa} {
                return newToken(TokenType.IGUALACION, yytext());
            }

    {more} {
                return newToken(TokenType.SUMA, yytext());
            }

    {menus} {
                return newToken(TokenType.RESTA, yytext());
            }

    {by} {
            return newToken(TokenType.MULTIPLICACION, yytext());
        }

    {on} {
            return newToken(TokenType.DIVISION, yytext());
        }

    {plus} {
                return newToken(TokenType.OPERADOR_DE_AUMENTO, yytext());
            }

    {equalSignal} {
                    return newToken(TokenType.ASIGNACION, yytext());
                }

    /* color */

    {colorSymbol} {
                    return newToken(TokenType.ASIGNACION_DE_COLOR, yytext());
                }

    /* keywords */
    
    {public} {
                return newToken(TokenType.PUBLIC, yytext());
            }

    {static} {
                return newToken(TokenType.STATIC, yytext());
            }

    {void} {
                return newToken(TokenType.VOID, yytext());
            }

    {main} {
                return newToken(TokenType.MAIN, yytext());
            }

    {define} {
                return newToken(TokenType.DEFINE, yytext());
            }

    {character} {
                    return newToken(TokenType.CHARACTER, yytext());
                }

    {screen} {
                return newToken(TokenType.SCREEN, yytext());
            }

    {background} {

                    return newToken(TokenType.BACKGROUND, yytext());
                }

    {sSound} {
                return newToken(TokenType.STOP_SOUND, yytext());
            }

    {pSound} {
                return newToken(TokenType.PLAY_SOUND, yytext());
            }

    {hide} {
                return newToken(TokenType.HIDE, yytext());
            }
    
    {show} {
                return newToken(TokenType.SHOW, yytext());
            }

    {menu} {
                return newToken(TokenType.MENU, yytext());
            }

    {go} {
            return newToken(TokenType.GO, yytext());
        }

    {breaker} {
                return newToken(TokenType.BREAKER, yytext());
            }

    {color} {
                return newToken(TokenType.COLOR, yytext());
            }

    {image} {
                return newToken(TokenType.IMAGE, yytext());
            }

    {sound} {
                return newToken(TokenType.SOUND, yytext());
            }

    /* dataTypes */

    {intK} {
                return newToken(TokenType.INT, yytext());
            }

    {doubleK} {
                return newToken(TokenType.DOUBLE, yytext());
            }

    {stringK} {
                return newToken(TokenType.STRING, yytext());
            }

    {boolK} {
                return newToken(TokenType.BOOLEANO, yytext());
            }

    {trueK} {
                return newToken(TokenType.TRUE, yytext());
            }

    {falseK} {
                return newToken(TokenType.FALSE, yytext());
            }

    /* assignamentTypes */

    {for} {
            return newToken(TokenType.FOR, yytext());
        }

    {if} { 
            return newToken(TokenType.IF, yytext());
        }

    {else} {
                return newToken(TokenType.ELSE, yytext());
            }

    /* Identifer */
    {identifer} {
                    return  newToken(TokenType.IDENTIFICADOR, yytext());
                }

    /* errors */
    .    {
            return newToken(TokenType.ERROR_TOKEN_DESCONOCIDO, yytext());
        }


