package project;
import static project.TokenType.*;
%%
%class Lexer
%type Token
%{
    public String lexeme;
    private int lastBreak = 0;
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

    //{lexeme=yytext(); return "IDENTIFICADOR";}

    {identifer} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.IDENTIFICADOR, lexeme=yytext(), yyline + 1, column);
                }

    /* delimiters */

    {leftBrace} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.LLAVE_DE_APERTURA, lexeme=yytext(), yyline + 1, column);
                }

    {rightBrace} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.LLAVE_DE_CERRADURA, lexeme=yytext(), yyline + 1, column);
                }

    {leftParethesis} {
                        int column = yychar - lastBreak + 1;
                        return new Token(TokenType.PARENTESIS_DE_APERTURA, lexeme=yytext(), yyline + 1, column);
                    }

    {rightParethesis} {
                        int column = yychar - lastBreak + 1;
                        return new Token(TokenType.LLAVE_DE_CERRADURA, lexeme=yytext(), yyline + 1, column);
                    }

    {colon} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.DOS_PUNTOS, lexeme=yytext(), yyline + 1, column);
            }

    {semiColon} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.PUNTO_Y_COMA, lexeme=yytext(), yyline + 1, column);
                }

    {comma} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.COMA, lexeme=yytext(), yyline + 1, column);
            }

    /* dataType */

    {number} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.NUMERO, lexeme=yytext(), yyline + 1, column);
            }

    {chaising} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.CADENA, lexeme=yytext(), yyline + 1, column);
                }

    /* operators */

    
    {lessThan} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.MENOR_QUE, lexeme=yytext(), yyline + 1, column);
                }

    {moreThan} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.MAYOR_QUE, lexeme=yytext(), yyline + 1, column);
                }

    {ltoet} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.MENOR_O_IGUAL_QUE, lexeme=yytext(), yyline + 1, column);
            }

    {gtoet} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.MAYOR_O_IGUAL_QUE, lexeme=yytext(), yyline + 1, column);
            }

    {awa} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.IGUALACION, lexeme=yytext(), yyline + 1, column);
            }

    {more} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.SUMA, lexeme=yytext(), yyline + 1, column);
            }

    {menus} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.RESTA, lexeme=yytext(), yyline + 1, column);
            }

    {by} {
            int column = yychar - lastBreak + 1;
            return new Token(TokenType.MULTIPLICACION, lexeme=yytext(), yyline + 1, column);
        }

    {on} {
            int column = yychar - lastBreak + 1;
            return new Token(TokenType.DIVISION, lexeme=yytext(), yyline + 1, column);
        }

    {plus} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.OPERADOR_DE_AUMENTO, lexeme=yytext(), yyline + 1, column);
            }

    {equalSignal} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.ASIGNACION, lexeme=yytext(), yyline + 1, column);
                }

    /* color */

    {colorSymbol} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.ASIGNACION_DE_COLOR, lexeme=yytext(), yyline + 1, column);
                }

    /* keywords */
    
    {public} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.PUBLIC, lexeme=yytext(), yyline + 1, column);
            }

    {static} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.STATIC, lexeme=yytext(), yyline + 1, column);
            }

    {void} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.VOID, lexeme=yytext(), yyline + 1, column);
            }

    {main} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.MAIN, lexeme=yytext(), yyline + 1, column);
            }

    {define} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.DEFINE, lexeme=yytext(), yyline + 1, column);
            }

    {character} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.CHARACTER, lexeme=yytext(), yyline + 1, column);
                }

    {screen} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.SCREEN, lexeme=yytext(), yyline + 1, column);
            }

    {background} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.BACKGROUND, lexeme=yytext(), yyline + 1, column);
                }

    {sSound} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.STOP_SOUND, lexeme=yytext(), yyline + 1, column);
            }

    {pSound} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.PLAY_SOUND, lexeme=yytext(), yyline + 1, column);
            }

    {hide} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.HIDE, lexeme=yytext(), yyline + 1, column);
            }
    
    {show} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.SHOW, lexeme=yytext(), yyline + 1, column);
            }

    {menu} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.MENU, lexeme=yytext(), yyline + 1, column);
            }

    {go} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.GO, lexeme=yytext(), yyline + 1, column);
            }

    {breaker} {
                    int column = yychar - lastBreak + 1;
                    return new Token(TokenType.BREAKER, lexeme=yytext(), yyline + 1, column);
                }

    {color} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.COLOR, lexeme=yytext(), yyline + 1, column);
            }

    {image} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.IMAGE, lexeme=yytext(), yyline + 1, column);
            }

    {sound} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.SOUND, lexeme=yytext(), yyline + 1, column);
            }

    /* dataTypes */

    {intK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.INT, lexeme=yytext(), yyline + 1, column);
            }

    {doubleK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.DOUBLE, lexeme=yytext(), yyline + 1, column);
            }

    {stringK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.STRING, lexeme=yytext(), yyline + 1, column);
            }

    {boolK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.BOOLEANO, lexeme=yytext(), yyline + 1, column);
            }

    {trueK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.TRUE, lexeme=yytext(), yyline + 1, column);
            }

    {falseK} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.FALSE, lexeme=yytext(), yyline + 1, column);
            }

    /* assignamentTypes */

    {for} {
            int column = yychar - lastBreak + 1;
            return new Token(TokenType.FOR, lexeme=yytext(), yyline + 1, column);
        }

    {if} {
            int column = yychar - lastBreak + 1;
            return new Token(TokenType.IF, lexeme=yytext(), yyline + 1, column);
        }

    {else} {
                int column = yychar - lastBreak + 1;
                return new Token(TokenType.ELSE, lexeme=yytext(), yyline + 1, column);
            }

    /* errors */
    .    {
            int column = yychar - lastBreak + 1;
            return new Token(TokenType.ERROR_TOKEN_DESCONOCIDO, lexeme=yytext(), yyline + 1, column);
        }


