package project;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.ExpandVetoException;

public class Syntax {
    private final List<Token> tokens;
    private int index;
    private List<String> errors;

    public Syntax(List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.errors = new ArrayList<>();
    }

    public String parse() throws Exception{

        A();
        if (index != tokens.size()) {
            errors.add("Error: código fuente no válido");
        }

        if (!errors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Se encontraron los siguientes errores:\n");
            for (String error : errors) {
                errorMessage.append(error).append("\n");
            }
            return errorMessage.toString();
        } else {
            return "Análisis sintáctico exitoso.";
        }
    }
// -------------------------------------------------------------------------------------------------------------------------------

    // Para el metodo principal
    private void A() throws Exception {
        if (!match(TokenType.PUBLIC)) {
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'public' en la línea " + line + ", columna " + column);
            consume();
        } else {
            consume();
        }
    
        if (!match(TokenType.STATIC)) {
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'static' en la línea " + line + ", columna " + column);
            consume();
        } else {
            consume();
        }
    
        if (!match(TokenType.VOID)) {
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'void' en la línea " + line + ", columna " + column);
            consume();
        } else {
            consume();
        }
    
        if (!match(TokenType.MAIN)) {
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'main' en la línea " + line + ", columna " + column);
            consume();
        } else {
            consume();
        }
        // Continúa con el análisis
        F();
    }

    
    private void F() throws Exception{
        PA(); // Para el parentesis de apertura " ( "
        PC(); // Para el parentesis de cerradura " ) "
        LLA(); // Para la llave de " { "
        CA(); // Para determinar si se declara algo dentro del metodo principal
        LLC();// Para el parentesis de " } "
    }

    
// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------

    // Para lo que se puede escribir dentro del metodo principal y tanto dentro como fuera de la 
    // funciones Screen () y define

    private void CA() throws Exception {
            if (match(TokenType.DEFINE)) {
                // Si es "define", consume y va a N()
                consume();
                N();
            } else if (match(TokenType.SCREEN)) {
                // Si es "Screen", consume y va a AI()
                consume();
                //AI();
            } else if (match(TokenType.LLAVE_DE_CERRADURA)) {
                // Si es "}", no se genera error y simplemente se regresa
                return;
            } else {
                // Si no es ninguno de los tokens anteriores, se genera un error
                Token currentToken = tokens.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'define', 'Screen' o '}' en la línea " + line + ", columna " + column);
                consume();
            }
        
        // Continúa con la recursión
        CA();
    }
    

    private void N() throws Exception{
    
        //Si despues del define recibo un tipo de dato hago esto
        if(match(TokenType.INT) || match(TokenType.DOUBLE) || match(TokenType.STRING) || match(TokenType.BOOLEANO)){
            // consumo y reviso el resto
            OPE();
            Q(); // Para el resto
        } else if(match(TokenType.CHARACTER)){
            //Consume y sigo con el resto
            if(match(TokenType.CHARACTER)){
                consume();
            }else{
                Token currentToken = tokens.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
            }
            O();// Para el resto
        }else if(match(TokenType.IMAGE) || match(TokenType.SOUND)){
            // Consumo y sigo con el resto
            if(match(TokenType.IMAGE) || match(TokenType.SOUND)){
                consume();
            }else{
                Token currentToken = tokens.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Image' o 'Sound' en la línea " + line + ", columna " + column);
            }
            AC(); // Para el resto
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un tipo e valor en " + line + ", columna " + column);
            consume();
        }
    }

    private void Q() throws Exception{
        T(); // Para determinar que sea un Identificador y no una palabra clave
        IGU(); // Para el signo de '='
        R(); // Para el Numero o Identificador
        
    }

    private void O() throws Exception{
        T(); // Para determinar que sea un Identificador y no una palabra clave
        IGU(); // Para el igual
        // Para la palabra reservada Character
        if(match(TokenType.CHARACTER)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
        }
        S(); // Para lo que va despues

    }

    private void S() throws Exception{
        PA(); // Parantesis de apertura
        DD(); // Para lo que va dentro del parentensis
        PC(); // Parentesis de cerradura
        PYC(); // Punto y coma
    }

    private void DD() throws Exception{
        CAD(); // Para el string
        // Para la coma
        if(match(TokenType.COMA)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
        }
        U(); // Para la palabra reservada de Color
    }

    private void DE() throws Exception{
        PA(); // Parantesis de apertura
        BQ(); // Para el color
        PC(); // Parentesis de cerradura
    }

    private void R() throws Exception{
        if(match(TokenType.NUMERO)){
            NUM(); // validar si es un número
            // PYC(); // Para el ;
        }else if(match(TokenType.CADENA)){
            CAD(); // validar si es una cadena
            // PYC(); // Para el ;
        }else if(match(TokenType.TRUE) || match(TokenType.FALSE)){
            TF(); // Para falso y verdadero
            // PYC(); // Para el ;
        }else if(match(TokenType.IDENTIFICADOR)){
            T(); // Para validar que es un identificador
            
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un número o cadena o 'True' o 'False' o identificador en la línea " + line + ", columna " + column);
        }

        PYC(); // Para el ;
    }

    private void AC() throws Exception{
        T(); // Para el identificador
        IGU(); // Para el igual
        CAD(); // Para el String
        PYC(); // Para el ;
    }
    
    private void AI() throws Exception{
        T(); // Verifico que sea un identificador
        PA(); // Para el parentesis de apertura
        PC(); // Para el parentesis de cerradura
        AJ(); // Para lo que va despues de eso
    }

    private void AJ() throws Exception{
        LLA(); // Para la llave de apertura
        CB(); // Para lo que se puede escribir dentro de la función Screen
        LLC(); // Para la llave de cerradura
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    // Para lo que se puede escribir dentro de los metodos de Screen

    private void CB() throws Exception{
        if(match("define")){
            DEF(); // Verifico que me llegue un define
            CB(); // Vuelvo a llamar al metodo para que sea recursivo
        }else if(match("int") || match("double") || match("string") || match("bool")){
            OPE(); // Verfico que sea un tipo de dato
            Y(); // Verifico que es lo siguiente que llega
            CB();
        }else if(match("setBackground") || match("show") || match("hide") ||
        match("playSound") || match("stopSound")){
            AL(); // Para determinar si me llega una de estas palabras reservadas
            CB(); // Llamo de nuevo al metodo, por la recursividad
        }else if(match("Menu")){
            MEN(); // Para el Menu
            CB(); // Para la recursividad
        }else if(match("for")){
            FOR(); // Para el For
            CB(); // Para llamar de manera recursiva al metodo
        }else if(match("if")){
            IF(); // Para el if
            CB(); // Para la recursividad
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            T(); // Si llega un identificador
            //Y luego un = quiere decir que es una inicializacion de variable
            if(match("=")){
                IGU(); //Para el igual
                if(match("\\(")){
                    K();
                    CB();
                }else if(match("\\d+") || match("(['\"])(.*?)\\1") || match("True") 
                || match("False") || match("[a-zA-Z_][a-zA-Z0-9_]*") ){
                    R(); // Para determinar el tipo de dato
                    CB(); // Para la recursividad
                }
            }else if(match("\\(")){
                PA(); // Para el parentesis de apertura
                DF(); // Para lo que va despues
                PYC(); // Para el punto y coma
                CB(); // Para llamar al metodo de nuevo
            }else if(match("[+*-/]")){
                KP(); // Llamo a K prima, porque lo siquiente que debe esperar es un signo
                PYC(); // Para el " ; "
                CB(); // Para llamar de nuevo al metodo
            } 
        }else if(match("\\(")){
            //K(); // Si llegue primero un parentesis llamo a K
            PA();
            DF();
            PYC(); // Para el " ; "
            CB(); // Para la recursividad
            
        }else if(match("\\d+")){
            NUM(); // Para verificar que sea un número
            KP(); // Para verificar que lo siguiente del número sea un operador
            PYC(); // Para el " ; "
            CB(); // Para la recursividad
        }
    }

    private void Y() throws Exception{
        T(); // Verifico que me llegue un id
        if(match(";")){
            PYC(); // Si llega el punto y coma termina
        }else if(match("=")){
            IGU(); // Si llega el igual es necesario recibir el valor
            R(); // Para validar el valor y, punto y coma
        }else if(match(",")){
            COM(); // Coma
            Y();// recursivo
        }
    }

    private void AN() throws Exception{
        PA(); // Para el parentesis de apertura
        T(); // Para el identificador
        PC(); // Para el parentesis de cerradura
        PYC(); // Para el punto y coma
    }

    private void DF() throws Exception{
        CAD(); // ¨Para el String
        PC(); // Para el parentesis de Cerradura 
    }
    
private void K() throws Exception{
        L(); // Para los simbolos
        KP();
    }

    private void L()throws Exception{
        if(match("\\(")){
            PA(); // Parentesis de apertura
            if(match("\\(") || match("\\d+") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                K(); // para llamar a la funcion de K
                PC(); // Para el )
            }
        } else if (match("[a-zA-Z_][a-zA-Z0-9_]*")) {
            T(); // Para el identificador
        } else if (match("\\d+")) {
            NUM(); // Para el numero
        }else {
            throw new Exception("Se esperaba '(', identificador o numero");
        }
    }

    private void KP()throws Exception{
        if(match("[+*-/]")){
            consume();
            if(match("=")){
                Z(); // en caso de que tenga una operacion iterativa
            }else{
                L();
                KP();
            }
            
            PYC();
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    // Para la estructura de menu

    //Opciones del Menu
    private void AO() throws Exception{
        if(match("\\(")){
            PA(); // Para el parentesis de apertura
            DF(); // Para la cadena y parentesis de cerradura
            DP(); // Para el " : "
            AT(); //Para lo que puede ir dentro de cada opción
            AO(); // Para llamar esto de manera recursiva y crear tantas opciones como se desee
        }
        
        
    }

    private void AT() throws Exception{
        if(match("setBackground") || match("show") || match("hide") ||
        match("playSound") || match("stopSound")){
            AL();
            AT();
        }else if(match("int") || match("double") || match("string") || match("bool")){
            OPE(); // Verfico que sea un tipo de dato
            Y(); // Verifico que es lo siguiente que llega
            AT();
        }else if(match("breaker")){ // Para marcar el final de una opción
            BRE(); // Para determinar que me llegue un breaker
            AO(); // Para llamar de manera recursiva a las opciones del menu
        }else if(match("go")){ // Para marcar el final de una opción
            GO(); // Para el Go
            AO(); // Para la recursividad
        }else if(match("if")){
            IF();
            AT();
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            T(); // Si llega un identificador
            
            //Y luego un = quiere decir que es una inicializacion de variable
            if(match("=")){
                IGU(); //Para el igual
                if(match("\\(")){
                    K();
                    AT();
                }else if(match("\\d+") || match("(['\"])(.*?)\\1") || match("True") 
                || match("False") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                    R(); // Para determinar el tipo de dato
                    AT();    
                }
            }else if(match("\\(")){
                PA(); // Para el parentesis de apertura
                DF(); // Para lo que va despues
                PYC(); // Para el punto y coma
                AT(); // Para llamar al metodo de nuevo
            }else if(match("[+*-/]")){
                KP(); // Llamo a K prima, porque lo siquiente que debe esperar es un signo
                PYC(); // Para el " ; "
                AT(); // Para llamar de nuevo al metodo
            } 
        }else if(match("\\(")){
            //K(); // Si llegue primero un parentesis llamo a K
            PA();
            DF();
            PYC(); // Para el " ; "
            AT(); // Para la recursividad
        }else if(match("\\d+")){
            NUM(); // Para verificar que sea un número
            KP(); // Para verificar que lo siguiente del número sea un operador
            PYC(); // Para el " ; "
            AT(); // Para la recursividad
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    // Para la estructura del for
    private void BK() throws Exception{
        BL(); // Para el iterador
        PYC(); // Para el " ; "
        BM(); // Para la la condición
        PYC(); // Para el " ; "
        BP(); // Para el incremento
    }

    private void BL() throws Exception{
        T(); // Para el identificador
        IGU(); // Para el " = "
        NUM(); // Para el número
    }
    
    private void BM() throws Exception{
        T(); // Para el identificador
        BU(); // Para lo comparadores
        RR(); // Para la igual
    }

    private void RR() throws Exception{
        if(match("\\d+")){
            NUM(); // validar si es un número
            //PYC(); // Para el ;
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            T(); // Para validar que es un identificador
            //PYC(); // Para el ;
        }
    }

    private void BP() throws Exception{
        T(); // Para el identificador
        if(match("\\+\\+")){
            PLU(); // En caso de que me llegue un aumento de 1
        }else if(match("\\+")){
            consume(); // Consumo y llamo al siguiente
            RR(); // En caso de que me llegue un incremento que tenga un valor númerico o con un identificador
        }
    }

    private void BN() throws Exception{
        LLA(); // Para la llave de apertura
        BO(); // Para lo que se puede llamar dentro del for
        LLC(); // Para la llave de cerradura 
    }

    private void BO() throws Exception{
        if(match("for")){
            FOR(); // Para el for
            BO(); // Para la recursividad
        }else if(match("if")){
            IF(); // Para verificar que es un if
            BO(); // Para la recursividad
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            T(); // Si llega un identificador
            //Y luego un = quiere decir que es una inicializacion de variable
            if(match("=")){
                IGU(); //Para el igual
                if(match("\\(")){
                    K();
                    BO();
                }else if(match("\\d+") || match("(['\"])(.*?)\\1") || match("True") 
                || match("False") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                    R(); // Para determinar el tipo de dato
                    BO();
                }
            }else if(match("\\(")){
                PA(); // Para el parentesis de apertura
                DF(); // Para lo que va despues
                PYC(); // Para el punto y coma
                BO(); // Para llamar al metodo de nuevo
            }else if(match("[+*-/]")){
                KP(); // Llamo a K prima, porque lo siquiente que debe esperar es un signo
                PYC(); // Para el " ; "
                BO(); // Para llamar de nuevo al metodo
            } 
        }else if(match("\\(")){
            //K(); // Si llegue primero un parentesis llamo a K
            PA();
            DF();
            PYC(); // Para el " ; "
            BO(); // Para la recursividad
        }else if(match("\\d+")){
            NUM(); // Para verificar que sea un número
            KP(); // Para verificar que lo siguiente del número sea un operador
            PYC(); // Para el " ; "
            BO(); // Para la recursividad
        }
    }

    private void Z() throws Exception{
        IGU(); // Para los operadores
        RR(); // Para determinar que sea un número o identificador
        //PYC(); // Para el " ; "
        //BO(); // Para llamar al metodo de nuevo
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    // Para la estructura del if

    private void BF() throws Exception{
        LLA(); // Para la llaves de apertura
        BG(); // Para lo que va dentro del if
        LLC(); // Para la llave de cerradura 
    }

    private void BG() throws Exception{
        if(match("setBackground") || match("show") || match("hide") ||
        match("playSound") || match("stopSound")){
            AL(); // Para verificar que me llega alguna de esas palabras claves
            BG(); // Para la recursividad del metodo
        }else if(match("Menu")){
            MEN(); // Si recibo un menu
            BG(); // Para la recursividad
        }else if(match("for")){
            FOR(); // Para el for
            BG(); // Para llamar de manera recursiva al metodo
        }else if(match("if")){
            IF(); // Para el if, de tal forma que sea anidado
            BG(); // Para la recursividad del metodo
        }else if(match("[a-zA-Z_][a-zA-Z0-9_]*")){
            T(); // Si llega un identificador
            //Y luego un = quiere decir que es una inicializacion de variable
            if(match("=")){
                IGU(); //Para el igual
                if(match("\\(")){
                    K();
                    BG();
                }else if(match("\\d+") || match("(['\"])(.*?)\\1") || match("True") 
                || match("False") || match("[a-zA-Z_][a-zA-Z0-9_]*")){
                    R(); // Para determinar el tipo de dato
                    BG(); // Para la recursividad
                }
                
            }else if(match("\\(")){
                PA(); // Para el parentesis de apertura
                DF(); // Para lo que va despues
                PYC(); // Para el punto y coma
                BG(); // Para llamar al metodo de nuevo
            }else if(match("[+*-/]")){
                KP(); // Llamo a K prima, porque lo siquiente que debe esperar es un signo
                PYC(); // Para el " ; "
                BG(); // Para llamar de nuevo al metodo
            } 
        }else if(match("\\(")){
            //K(); // Si llegue primero un parentesis llamo a K
            PA();
            DF();
            PYC(); // Para el " ; "
            BG(); // Para la recursividad
        }else if(match("\\d+")){
            NUM(); // Para verificar que sea un número
            KP(); // Para verificar que lo siguiente del número sea un operador
            PYC(); // Para el " ; "
            BG(); // Para la recursividad
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------    

// -------------------------------------------------------------------------------------------------------------------------------
    // Para expresiones regulares

    //Para determinar si es un identificador
    private void T() throws Exception{
        if(match(TokenType.IDENTIFICADOR)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un identificador en la línea " + line + ", columna " + column);
        }
    }

    //Para los números
    private void NUM() throws Exception{
        if(match(TokenType.NUMERO)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un número en la línea " + line + ", columna " + column);
        }
    }

    //Para las cadenas
    private void CAD() throws Exception{
        if(match(TokenType.CADENA)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba una cadena en la línea " + line + ", columna " + column);
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    // Para palabras reservadas
    private void DEF() throws Exception{
        if(match("define")){
            consume();
        }else{
            throw new Exception("Se esperaba un 'define' ");
        }
        N();
    }

    private void OPE() throws Exception{
        if(match(TokenType.INT) || match(TokenType.DOUBLE) || match(TokenType.STRING) || match(TokenType.BOOLEANO)){
            consume();
        }
    }

    private void TF() throws Exception{
        if(match(TokenType.TRUE) || match(TokenType.FALSE)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba 'True' o 'False' en la línea " + line + ", columna " + column);
        }
    }

    private void CHA() throws Exception{
        if(match("Character")){
            consume();
        }else{
            throw new Exception("Se esperaba 'Character' ");
        }
    }

    private void U() throws Exception{
        if(match(TokenType.COLOR)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un 'Color' en la línea " + line + ", columna " + column);
        }
        DE(); // Para el color
    }

    private void AB() throws Exception{
        if(match("Image") || match("Sound")){
            consume();
        }
        AC(); // Para lo que va despues, ("dirección")
    }

    private void SCR() throws Exception{
        if(match("Screen")){
            consume();
        }else{
            throw new Exception("Se esperaba 'Screen' ");
        }
        AI();// Para lo que va despues de la palabra reservada Screen
    }

    private void AL() throws Exception{
        if(match("setBackground") || match("show") || match("hide") ||
        match("playSound") || match("stopSound")){
            consume();
        }
        AN(); // Para lo que va despues, (id)
    }

    private void MEN() throws Exception{
        if(match("Menu")){
            consume();
        }else{
            throw new Exception("Se esperaba 'Menu' ");
        }
        DP(); // Para el " : "
        AO(); // Para las opciones del menu
    }

    private void BRE() throws Exception{
        if(match("breaker")){
            consume();
        }else{
            throw new Exception("Se esperaba 'breaker' ");
        }
        PYC(); // Para el " ; "
    }

    private void GO() throws Exception{
        if(match("go")){
            consume();
        }else{
            throw new Exception("Se esperaba 'go' ");
        }
        T(); // Para el identificador
        PYC(); // Para el " ; "
    }

    private void FOR() throws Exception{
        if(match("for")){
            consume();
        }else{
            throw new Exception("Se esperaba un 'for' ");
        }
        PA(); // Para el parentesis de apertura
        BK(); // Para el tema del condicional del for
        PC(); // Para el parentesis de cerradura
        BN(); // Para lo que va dentro del for
    }

    private void IF() throws Exception{
        if(match("if")){
            consume();
        }else{
            throw new Exception("Se esperaba 'if' ");
        }
        PA(); // Para el parentesis de apertura
        BM(); // Para el condicional
        PC(); // Para el parentesis de cerradura
        BF(); // Para lo que va dentro del if
        ELS(); // Para poner o no, un else if o else
    }

    private void ELS() throws Exception{
        if(match("else")){
            consume();
            if(match("if")){
                IF(); // Si recibo un else if
            }else{
                BF(); // Si recibo else
            }
        }
    }
// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    //Metodos para signos de operadores
    
    private void IGU() throws Exception{
        if(match(TokenType.ASIGNACION)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un '=' en la línea " + line + ", columna " + column);
        }
    }

    private void BU() throws Exception{
        if(match("<") || match(">") || match("<=") || match(">=")){
            consume();
        }else{
            throw new Exception("Se esperaba '<' o '>' o '<=' o '>=' ");
        }
    }

    /*private void AX() throws Exception{
        if(match("\\+=") || match("\\-=") || match("\\*=") || match("/=") || 
        match("==")){
            consume();
        }else{
            throw new Exception("Se esperaba '+=' o '-=' o '*=' o '/=' o '==' ");
        }
    }*/

    private void PLU() throws Exception{
        if(match("\\+\\+")){
            consume();
        }else{
            throw new Exception("Se esperaba un '++' ");
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------

    //Metodos para signos 
    private void COM() throws Exception{
        if(match(TokenType.COMA)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
        }
    }

    private void BQ() throws Exception{
        if(match(TokenType.ASIGNACION_DE_COLOR)){
            consume();
        }else{
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba un '#' en la línea " + line + ", columna " + column);
        }
        CAD();
    }

// -------------------------------------------------------------------------------------------------------------------------------

// -------------------------------------------------------------------------------------------------------------------------------
    //Metodos para signos de delimitadores

    //Para llaves de Apertura " { "
    private void LLA() throws Exception{
        if (index <= tokens.size()) {
            if (match(TokenType.LLAVE_DE_APERTURA)) {
                consume(); // Consumir el token de cierre de llave '}'
            } else {
                Token currentToken = tokens.get(index - 1); // Obtener el último token analizado
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba '{' para cerrar el bloque en la línea " + line + ", columna " + column);
            }
        } else {
            Token currentToken = tokens.get(index); // Obtener el último token analizado
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba '{' para cerrar el bloque en la línea " + line + ", columna " + column);
        }
    }

    // /Para llaves de Cerradura " } "
    private void LLC() throws Exception {
        if (index <= tokens.size()) {
            if (match(TokenType.LLAVE_DE_CERRADURA)) {
                consume(); // Consumir el token de cierre de llave '}'
            } else {
                Token currentToken = tokens.get(index - 1); // Obtener el último token analizado
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba '}' para cerrar el bloque en la línea " + line + ", columna " + column);
            }
        } else {
            Token currentToken = tokens.get(index); // Obtener el último token analizado
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba '}' para cerrar el bloque en la línea " + line + ", columna " + column);
        }
    }
    
    

    // /Para el punto y coma " ; "
    private void PYC() throws Exception{
        if (!match(TokenType.PUNTO_Y_COMA)) {
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba ' ; ' en la línea " + line + ", columna " + column);
        } else {
            consume();
        }
    }

    ///Para parentesis de Aparetura " ( "
    private void PA() throws Exception{
        if(!match(TokenType.PARENTESIS_DE_APERTURA)){
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba ' ( ' en la línea " + line + ", columna " + column);
            consume();
        }else{
            consume();
        }
    }

    ///Para parentensis de cerradura " ) "
    private void PC() throws Exception{
        if(!match(TokenType.PARENTESIS_DE_CERRADURA)){
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba ' ) ' en la línea " + line + ", columna " + column);
            consume();
        }else{
            consume();
        }
    }

    //Para los dos puntos " : "
    private void DP() throws Exception{
        if(!match(TokenType.DOS_PUNTOS)){
            Token currentToken = tokens.get(index);
            int line = currentToken.getLine();
            int column = currentToken.getColumn();
            errors.add("Se esperaba ' : ' en la línea " + line + ", columna " + column);
        }else{
            consume();
        }
    }

// -------------------------------------------------------------------------------------------------------------------------------


    // Métodos auxiliares
    private boolean match(String expectedTokenPattern) {
        //System.out.println(tokens.get(index));
        if (index < tokens.size()) {
            Token token = tokens.get(index);
            String tokenValue = token.getValue();
            return tokenValue.matches(expectedTokenPattern);
        }
        return false;
    }

    private boolean match(TokenType expectedType) {
        if (index >= 0 && index < tokens.size()) { // Verifica los límites antes de acceder
            Token token = tokens.get(index);
            return token.getType() == expectedType;
        }
        return false;
    }
    
    private void consume() {
        if (index < tokens.size()) {
            System.out.println(tokens.get(index).getValue());
            index++;
        } else {
            // Si no quedan más tokens, puedes lanzar una excepción o simplemente mostrar un mensaje de advertencia
            System.out.println("¡Se ha llegado al final del archivo fuente!");
        }
    }
    

    public List<String> getErrors() {
        return errors;
    }
}
