package project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Syntax {
    private final List<Token> tokens;
    private int index;
    private List<String> errors;
    private List<arbolSintac> arbolSintac;
    private String codigo;
    private int count;

    public Syntax(List<Token> tokens, List<arbolSintac> arbolSintac) {
        this.tokens = tokens;
        this.index = 0;
        this.errors = new ArrayList<>();
        this.arbolSintac = arbolSintac;
        this.codigo = "";
        this.count = 0;
    }

    public String parse() throws Exception {

        A();
        if (index != arbolSintac.size()) {
            errors.add("Error: código fuente no válido `(╯°□°)╯ ︵ ┻━┻");
        }

        if (!errors.isEmpty()) {
            System.out.println(codigo);
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Se encontraron los siguientes errores:\n");
            for (String error : errors) {
                errorMessage.append(error).append("\n");
            }
            return errorMessage.toString();
        } else {
            System.out.println(codigo);
            return "Análisis sintáctico exitoso.";
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------------

    // Para el metodo principal
    private void A() throws Exception {
        if (!match(TokenType.PUBLIC)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'public' en la línea " + line + ", columna " + column);
                consume();
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba 'public' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
        System.out.println(index);
        if (!match(TokenType.STATIC)) {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'static' en la línea " + line + ", columna " + column);
                consume();
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba 'static' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
        System.out.println(index);
        if (!match(TokenType.VOID)) {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'void' en la línea " + line + ", columna " + column);
                consume();
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba 'void' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
        System.out.println(index);
        if (!match(TokenType.MAIN)) {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'main' en la línea " + line + ", columna " + column);
                consume();
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba 'main' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
        System.out.println(index);
        // Continúa con el análisis
        F();
    }

    private void F() throws Exception {
        PA();
        PC();
        LLA();
        if(index != arbolSintac.size()){
            CA(); // Para determinar si se declara algo dentro del metodo principal
        }
        LLC();
    }

    // Para lo que se puede escribir dentro del metodo principal y tanto dentro como
    // fuera de las funciones Screen () y define

    private void CA() throws Exception {
        // System.out.println("Entro a CA");
        if(index < arbolSintac.size()){
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            
            if (index + 1 < arbolSintac.size()) {
                nextToken = arbolSintac.get(index + 1);
            }

            if(actualCurrentToken.getValue().equals("}") && nextToken == null){
                System.out.println("Entro al return CA");
                return;
            }else{
                if(!match(TokenType.DEFINE) && !match(TokenType.SCREEN)){
                    if (index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'define' o 'Screen' en la línea " + line + ", columna " + column);
                        consume();
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'define' o 'Screen' en la línea " + line + ", columna " + column);
                    }
                }
                else{
                    if (match(TokenType.DEFINE)) {
                        X(); // Para el camino de 'define'
                        CA(); // Para la recursividad
                    } else if (match(TokenType.SCREEN)) {
                        if(!match(TokenType.SCREEN)){
                            if(index < arbolSintac.size()){
                                arbolSintac currentToken = arbolSintac.get(index);
                                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                                    int line = currentToken.getLine();
                                    int column = currentToken.getColumn();
                                    errors.add("Se esperaba 'Screen' en la línea " + line + ", columna " + column);
                                }else{
                                    int line = currentToken.getLine();
                                    int column = currentToken.getColumn();
                                    errors.add("Se esperaba 'Screen' en la línea " + line + ", columna " + column);
                                    consume();
                                }
                            }else{
                                arbolSintac currentToken = arbolSintac.get(index-1);
                                int line = currentToken.getLine();
                                int column = currentToken.getColumn() + getLength(currentToken.getId());
                                errors.add("Se esperaba 'Screen' en la línea " + line + ", columna " + column);
                            }
                        }else{
                            consume();
                        }
                        AI(); // Para el camino de 'Screen'
                        CA(); // Para la recursividad
                    }
                }
            }
        }else{
            return;
        }
    }

    private void N() throws Exception {
        
        // Si despues del define recibo un tipo de dato hago esto
        if (match(TokenType.INT) || match(TokenType.DOUBLE) || match(TokenType.STRING) || match(TokenType.BOOLEANO)) {
            if(index < arbolSintac.size()){
                String tip = OPE(); // Para el tipo de dato
                if(tip != null)
                    Q(tip);// Para el resto
            }
        } else if (match(TokenType.CHARACTER)) {
            // Consume y sigo con el resto
            if (match(TokenType.CHARACTER)) {
                consume();
            } else {
                if(index < arbolSintac.size()){
                    arbolSintac currentToken = arbolSintac.get(index);
                    Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                    if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                    }else{
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                        consume();
                    }
                }else {
                    arbolSintac currentToken = arbolSintac.get(index-1);
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                }
            }
            O();// Para el resto
        } else if (match(TokenType.IMAGE) || match(TokenType.SOUND)) {
            // Consumo y sigo con el resto
            TokenType type = tokens.get(getIndex(arbolSintac.get(index).getId())).getType();
            if (match(TokenType.IMAGE) || match(TokenType.SOUND)) {
                consume();
            } else {
                if(index < arbolSintac.size()){
                    arbolSintac currentToken = arbolSintac.get(index);
                    Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                    if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Image' o 'Sound' en la línea " + line + ", columna " + column);

                    }else{
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Image' o 'Sound' en la línea " + line + ", columna " + column);
                        consume();
                    }
                }else {
                    arbolSintac currentToken = arbolSintac.get(index-1);
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un tipo dato, un 'Character' o un 'Image/Sound' en " + line + ", columna " + column);
                }                
            }
            AC(type); // Para el resto

        } else if (!match(TokenType.INT) || !match(TokenType.DOUBLE) || !match(TokenType.STRING)
                || !match(TokenType.BOOLEANO) || !match(TokenType.CHARACTER) || !match(TokenType.IMAGE)
                || !match(TokenType.SOUND)) {
            if(index < arbolSintac.size() ){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un 'tipo de dato' o 'Character' o 'Image' o 'Sound' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un 'tipo de dato' o 'Character' o 'Image' o 'Sound' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un 'tipo de dato' o 'Character' o 'Image' o 'Sound' en la línea " + line + ", columna " + column);
            }
        }
    }

    private void Q(String tip) throws Exception {
        String TQ = "";
        UUID idT = T(); // Para determinar que sea un Identificador y no una palabra clave
        if(idT != null && tip != null)
            asignarTipo(idT, tip);
            
            //tokens.get(getIndex(idT)).setTipoToken(tip);
            IGU(); // Para el signo de '='
            UUID idR = R(); // Para el Numero o Identificador
            if(idR != null){
                boolean isEquals = false;
                if(tokens.get(getIndex(idR)).getType() == TokenType.IDENTIFICADOR){
                    if(tokens.get(getIndex(idR)).getTipoToken() != null){
                        isEquals = tokens.get(getIndex(idR)).getTipoToken().equals(tip);
                    }else{
                        errors.add("Error: la variable '" + tokens.get(getIndex(idR)).getValue() + "' no ha sido declarada (línea " + arbolSintac.get(index-1).getLine() + ", columna " + arbolSintac.get(index-1).getColumn() + ")");
                    }
                }else{
                    isEquals = tip.equals(tokens.get(getIndex(idR)).getTipoToken());
                }
                if(isEquals){
                    // tokens.get(getIndex(idT)).setTipoToken(tip);
                    // tokens.get(getIndex(idT)).setValorToken(tokens.get(getIndex(idR)).getValorToken());
                    asignarValor(idT, idR);
                    codigo += "def " + tokens.get(getIndex(idT)).getValue() + ", " + tokens.get(getIndex(idR)).getTipoToken() + "||";
                    if(tokens.get(getIndex(idR)).getType() == TokenType.CADENA){
                        codigo += tokens.get(getIndex(idT)).getValue() + " = \"" + tokens.get(getIndex(idR)).getValorToken() + "\"||";    
                    }else{
                        codigo += tokens.get(getIndex(idT)).getValue() + " = " + tokens.get(getIndex(idR)).getValorToken() + "||";
                    }
                }else{
                    errors.add("No se puede asignar un valor de tipo '" + tokens.get(getIndex(idR)).getTipoToken() + "' a una variable de tipo '" + tip + "' (línea " + arbolSintac.get(getIndexArbol(idR)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idR)).getColumn() + ")");
                }
            }
            System.out.println(TQ);
            codigo += TQ;

    }

    private void O() throws Exception {// T = Character("Cadena", Color(#"HEX"));
        UUID idT = T(); // Para determinar que sea un Identificador y no una palabra clave
        // TO = tokens.get(getIndex(idT)).getValue() + ": {nombre: string, color: string} = "; //color no se como hacerle
        // if(idT != null){
            IGU(); // Para el igual
            // Para la palabra reservada Character
            if (match(TokenType.CHARACTER)) {
                consume();
            } else {
                if(index < arbolSintac.size()){
                    arbolSintac currentToken = arbolSintac.get(index);
                    Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                    if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                    }else{
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                        consume();
                    }
                }else{
                    arbolSintac currentToken = arbolSintac.get(index-1);
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Character' en la línea " + line + ", columna " + column);
                }
            }
            UUID idCad = S(); // Para lo que va despues
            if(idCad != null){
                codigo += "def " + tokens.get(getIndex(idT)).getValue() + ", Character||";
                codigo += "param " + tokens.get(getIndex(idCad)).getColor() + "||";
                codigo += "call color, 1||";
                codigo += "ret t0||";
                codigo += "param \"" + tokens.get(getIndex(idCad)).getValorToken() + "\"||";
                codigo += "param t0||";
                codigo += "call Character, 2||";
                // tokens.get(getIndex(idT)).setValorToken(tokens.get(getIndex(idCad)).getValorToken());
                asignarValor(idT, idCad);
                if(tokens.get(getIndex(idCad)).getColor() != null){
                    tokens.get(getIndex(idT)).setColor(tokens.get(getIndex(idCad)).getColor());
                }
            }
        // }else{
        //     errors.add("Error: variable no declarada");
        //     // errors.add("Error: la variable '" + tokens.get(getIndex(idT)).getValue() + "' no ha sido declarada (línea " + arbolSintac.get(index-1).getLine() + ", columna " + arbolSintac.get(index-1).getColumn() + ")");
        // }

    }

    private UUID S() throws Exception {
        PA(); // Parantesis de apertura
        UUID idCad = DD(); // Para lo que va dentro del parentensis
        PC(); // Parentesis de cerradura
        PYC(); // Punto y coma
        // codigo += TO + "{nombre: " + tokens.get(getIndex(idCad)).getValorToken() + ", color: " + tokens.get(getIndex(idCad)).getColor() + "||";
        return idCad;
    }

    private UUID DD() throws Exception {
        UUID idCad = CAD(); // Para el string
        // Para la coma
        if (match(TokenType.COMA)) {
            consume();
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
                } else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
            }
        }
        String color = U(); // Para la palabra reservada de Color
        if(idCad != null && color != null){
            tokens.get(getIndex(idCad)).setColor(color);
        }else{
            if(idCad == null)errors.add("Error: variable no declarada");
            if(color == null)errors.add("Error: color no encontrado");
        }
        return idCad;
    }

    private String DE() throws Exception {
        PA(); // Parantesis de apertura
        String color = BQ(); // Para el color
        PC(); // Parentesis de cerradura
        return color;
    }

    private UUID R() throws Exception {
        System.out.println("Entro a R");
        UUID id = null;
        if (match(TokenType.NUMERO)) {
            id = NUM(); // validar si es un número
        } else if (match(TokenType.CADENA)) {
            id = CAD();// validar si es una cadena
        } else if (match(TokenType.TRUE) || match(TokenType.FALSE)) {
            id = TF(); // Para falso y verdadero
        } else if (match(TokenType.IDENTIFICADOR)) {
            id = T(); // Para validar que es un identificador
        } else {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número o una cadena o True o False o identificador en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número o una cadena o True o False o Identificador en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un número o una cadena o True o False o Identificador en la línea " + line + ", columna " + column);
            }
        }
        PYC(); // Para el ;
        return id;
    }

    private void AC(TokenType type) throws Exception { // Image/sound T = "";
        UUID idT = T(); // Para el identificador
        IGU(); // Para el igual
        // UUID idCad = CAD();
        UUID idCad = null;
        if(match(TokenType.MEMORY_MANAGEMENT)){
            idCad = arbolSintac.get(index).getId();
            consume();
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'una dirección de almacenamiento' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'una dirección de almacenamiento' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'una direccion de almacenamiento' en la línea " + line + ", columna " + column);
            }
        }
        if(idT != null && idCad != null){
            codigo += "def " + tokens.get(getIndex(idT)).getValue() + ", " + type + "||";
            codigo += tokens.get(getIndex(idT)).getValue() + " = \"" + tokens.get(getIndex(idCad)).getValorToken() + "\"||";
            tokens.get(getIndex(idT)).setValorToken(tokens.get(getIndex(idCad)).getValorToken());
        }
        PYC(); // Para el ;
    }

    private void AI() throws Exception {
        UUID idT = T(); // Verifico que sea un identificador
        PA(); // Para el parentesis de apertura
        PC(); // Para el parentesis de cerradura
        codigo += ":" + (tokens.get(getIndex(idT)).getValue()).toUpperCase() + "||";
        codigo += "def " + tokens.get(getIndex(idT)).getValue() + "_void_void||"; //nombre_retorna_recibe1_recibe2_reciben
        AJ(); // Para lo que va despues de eso
    }

    private void AJ() throws Exception {
        LLA(); // Para la llave de apertura
        if(index != arbolSintac.size()){
            CB(); // Para lo que se puede escribir dentro de la función Screen
        }
        LLC(); // Para la llave de cerradura
    }

    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Para lo que se puede escribir dentro de los metodos de Screen

    private void CB() throws Exception {
        System.out.println("Entro a CB");
        if(index < arbolSintac.size()){
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            Token nextTokenValue = null;
            if(index + 1 < arbolSintac.size()){
                nextToken = arbolSintac.get(index + 1);
                nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
            }
            if (actualCurrentToken != null && nextTokenValue != null) {
                System.out.println("Actual: " + actualCurrentToken.getType() + " Siguiente: " + nextTokenValue.getType());
            } else {
                if (actualCurrentToken == null) {
                    System.out.println("El token actual es null" + " Siguiente: " + nextTokenValue.getType());
                }
                if (nextTokenValue == null) {
                    System.out.println("Actual:"+ actualCurrentToken.getType() +" El siguiente token es null");
                }
            }
            if((actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextToken == null) 
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA)) 
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.SCREEN))){
                System.out.println("Entro al return CB");
                return;
            }else{
                if (match(TokenType.DEFINE)) {
                    X(); // Verifico que me llegue un define
                    CB(); // Vuelvo a llamar al metodo para que sea recursivo
                }else if(match(TokenType.BACKGROUND) || match(TokenType.SHOW) || match(TokenType.HIDE) ||
                    match(TokenType.PLAY_SOUND) || match(TokenType.STOP_SOUND) || match(TokenType.IF) || match(TokenType.IDENTIFICADOR) ||
                    match(TokenType.PARENTESIS_DE_APERTURA) || match(TokenType.NUMERO)){
                    B(); // Para todas estas opciones
                    CB(); // Para la recursividad
                } else if (match(TokenType.INT) || match(TokenType.DOUBLE) || match(TokenType.STRING) || match(TokenType.BOOLEANO)) {
                    H(); // Para en caso de un tipo de dato
                    CB(); // Para la recursividad
                } else  if (match(TokenType.MENU)) {
                    MEN(); // Para el Menu
                    CB(); // Para la recursividad
                }else{
                    if(index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                        if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'define' o 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                        }else{
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'define' o 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                            consume();    
                        }
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn() + getLength(currentToken.getId());
                        errors.add("Se esperaba 'define' o 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                    }
                }
            }
        }else{
            return;
        }
    }

    private void AN(TokenType type) throws Exception { // !Validar si los tokens son del tipo image o sound
        PA(); // Para el parentesis de apertura
        UUID idT = T(); // Para el identificador
        PC(); // Para el parentesis de cerradura
        PYC(); // Para el punto y coma
        if(type != null && idT != null){
            codigo += "param " + tokens.get(getIndex(idT)).getValue() + "||";
            codigo += "call " + type + ", 1||";
        }
    }

    private void DF() throws Exception { // ("") que hace esto?
        UUID idCad = CAD(); // Para el String
        if(idCad != null){
            Token token = tokens.get(getIndex(idCad));
            if(token != null){
                codigo += "param " + token.getValorToken() + "||";
                codigo += "call showText " + "1||";
            }
            if(valorPrimitive(token.getTipoToken()) != TokenType.CADENA){
                errors.add("Error: variable '" + token.getValue() + "' no es de tipo 'String' (línea " + arbolSintac.get(getIndexArbol(idCad)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idCad)).getColumn() + ")");
            }
            if(tokens.get(getIndex(idCad)).getValorToken() == null){
                errors.add("Error: variable no declarada (línea " + arbolSintac.get(getIndexArbol(idCad)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idCad)).getColumn() + ")");
            }
        }else{
            errors.add("Error: variable no declarada (línea " + arbolSintac.get(index-1).getLine() + ", columna " + arbolSintac.get(index-1).getColumn() + ")");
        }
        PC(); // Para el parentesis de Cerradura
    }


    // private Token K() throws Exception {
    //     Token idL = L(); // Para los simbolos
    //     Token tokenK = KP(idL);
    //     return tokenK;
    // }

    // private Token L() throws Exception {
    //     if (match(TokenType.PARENTESIS_DE_APERTURA)) {// EN ESPERA
    //         PA(); // Parentesis de apertura
    //         if (match(TokenType.PARENTESIS_DE_APERTURA) || match(TokenType.NUMERO) || match(TokenType.IDENTIFICADOR)) {
    //             Token tokenK = K(); // para llamar a la funcion de K
    //             PC(); // Para el )
    //             return tokenK;
    //         }
    //     } else if (match(TokenType.IDENTIFICADOR)) {
    //         UUID id = T(); // Para el identificador
    //         return tokens.get(getIndex(id));
    //     } else if (match(TokenType.NUMERO)) {
    //         UUID id = NUM(); // Para el numero
    //         return tokens.get(getIndex(id));
    //     } else {
    //         arbolSintac currentToken = arbolSintac.get(index);
    //         int line = currentToken.getLine();
    //         int column = currentToken.getColumn();
    //         errors.add("Se esperaba '(', identificador o numero en la línea" + line + ", columna " + column);
    //         consume();
    //     }
    //     return null;
    // }

    // private Token KP(Token tokL) throws Exception { //operandor operando
    //     Token token = tokL;
    //     if (match(TokenType.SUMA) || match(TokenType.RESTA) || match(TokenType.MULTIPLICACION) || 
    //         match(TokenType.DIVISION)) {
    //         TokenType opeAri = tokens.get(getIndex(arbolSintac.get(index).getId())).getType();
    //         UUID idZ;
    //         consume();
    //         if (match(TokenType.ASIGNACION)) {
    //             idZ = Z(); // en caso de que tenga una operacion iterativa
    //             Token tokenZ = tokens.get(getIndex(idZ));
    //             Token tokenL = tokL;
    //             double valLDouble, valZDouble;
    //             if(parseNumber(tokenL.getValorToken()) && parseNumber(tokenZ.getValorToken())){
    //                 valLDouble = ((Number) tokenL.getValorToken()).doubleValue();
    //                 valZDouble = ((Number) tokenZ.getValorToken()).doubleValue();
    //                 Object valL, valZ;
    //                 if(tokenL.getType() == TokenType.IDENTIFICADOR){
    //                     valL = tokenL.getValue();
    //                 }else{
    //                     valL = tokenL.getValorToken();
    //                 }
    //                 if(tokenL.getType() == TokenType.IDENTIFICADOR){
    //                     valZ = tokenZ.getValue();
    //                 }else{
    //                     valZ = tokenZ.getValorToken();
    //                 }
    //                 switch (opeAri) {
    //                     case SUMA:
    //                         codigo += "t = " + valL + " + " + valZ + "||";
    //                         valLDouble += valZDouble;
    //                         break;
    //                     case RESTA:
    //                         codigo += "t = " + valL + " - " + valZ + "||";
    //                         valLDouble -= valZDouble;
    //                         break;
    //                     case MULTIPLICACION:
    //                         codigo += "t = " + valL + " * " + valZ + "||";
    //                         valLDouble *= valZDouble;
    //                         break;
    //                     case DIVISION:
    //                         codigo += "t = " + valL + " / " + valZ + "||";
    //                         valLDouble /= valZDouble;
    //                         break;
    //                     default:
    //                         valLDouble = Double.MIN_VALUE;
    //                         break;
    //                 }
    
    //                 if(valLDouble != Double.MIN_VALUE){
    //                     Object idObject;
    //                     String tipotoken;
    //                     int val = (int) valLDouble;
    //                     double isEntero = valLDouble - val;
    //                     if(isEntero == 0.0){
    //                         idObject =  (Object)val;
    //                         tipotoken = "INT";
    //                     }else{
    //                         idObject = (Object)valLDouble;
    //                         tipotoken = "DOUBLE";
    //                     }
    //                     codigo += tokenL.getValue() + " = t||";
    //                     token = new Token(TokenType.NUMERO, 0, 0, null, tipotoken, idObject);
    //                 }else{
    //                     errors.add("Error: no se puede realizar la operación aritmética (línea " + arbolSintac.get(index-1).getLine() + ", columna " + arbolSintac.get(index-1).getColumn() + ")");
    //                 }
    //             }
                
    //         } else {
    //             Token tokenL2 = L();
    //             token = KP(tokenL2);
    //         }

    //     }else{
    //         arbolSintac currentToken = arbolSintac.get(index);
    //         int line = currentToken.getLine();
    //         int column = currentToken.getColumn();  
    //         errors.add("Se esperaba un operador en la línea" + line + ", columna " + column);
    //         consume();
        
    //     }
    //     PYC();
    //     return token;
    // }

    // private void XR() throws Exception{
    //     if(index < arbolSintac.size()){

    //     }else{
    //         if(index < arbolSintac.size()){
    //             arbolSintac currentToken = arbolSintac.get(index);
    //             Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //             if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
    //                 int line = currentToken.getLine();
    //                 int column = currentToken.getColumn();
    //                 errors.add("Se esperaba un número o una cadena o True o False o Identificador en la línea " + line + ", columna " + column);
    //             }else{
    //                 int line = currentToken.getLine();
    //                 int column = currentToken.getColumn();
    //                 errors.add("Se esperaba un número o una cadena o True o False o Identificador en la línea " + line + ", columna " + column);
    //                 consume();
    //             }
    //         }else{
    //             arbolSintac currentToken = arbolSintac.get(index-1);
    //             int line = currentToken.getLine();
    //             int column = currentToken.getColumn();
    //             errors.add("Se esperaba un número o una cadena o True o False o Identificador en la línea " + line + ", columna " + column);
    //         }
    //     }
    // }

    // private void K() throws Exception {
    //     if(index < arbolSintac.size()){
    //         Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //         arbolSintac nextToken = null;
    //         Token nextTokenValue = null;
    //         if(index + 1 < arbolSintac.size()){
    //             nextToken = arbolSintac.get(index + 1);
    //             nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
    //         }

    //         if((actualCurrentToken.getType().equals(TokenType.PARENTESIS_DE_CERRADURA) && nextToken == null) || (actualCurrentToken.getType().equals(TokenType.PARENTESIS_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.PARENTESIS_DE_CERRADURA))){
    //             System.out.println("Entro al return K");
    //             return;
    //         }else{
    //             L(); // Para los simbolos
    //             KP();
    //         }
    //     }else{
    //         return;
    //     }
        
    // }

    // private void L() throws Exception {
    //     if(index < arbolSintac.size()){
    //         Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //         arbolSintac nextToken = null;
    //         Token nextTokenValue = null;
    //         if(index + 1 < arbolSintac.size()){
    //             nextToken = arbolSintac.get(index + 1);
    //             nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
    //         }

    //         if((actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextToken == null) || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA))){
    //             System.out.println("Entro al return CB");
    //             return;
    //         }else{
    //             if (match(TokenType.PARENTESIS_DE_APERTURA)) {
    //                 PA(); // Parentesis de apertura
    //                 if (match(TokenType.PARENTESIS_DE_APERTURA) || match(TokenType.NUMERO) || match(TokenType.IDENTIFICADOR)) {
    //                     K(); // para llamar a la funcion de K
    //                     PC(); // Para el )
    //                 }else{
    //                     if(index < arbolSintac.size()){
    //                         arbolSintac currentToken = arbolSintac.get(index);
    //                         Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //                         if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
    //                             int line = currentToken.getLine();
    //                             int column = currentToken.getColumn();
    //                             errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                         }else{
    //                             int line = currentToken.getLine();
    //                             int column = currentToken.getColumn();
    //                             errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                             consume();
    //                         }
    //                     }else{
    //                         arbolSintac currentToken = arbolSintac.get(index-1);
    //                         int line = currentToken.getLine();
    //                         int column = currentToken.getColumn() + getLength(currentToken.getId());
    //                         errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                     }
    //                 }
    //             } else if (match(TokenType.IDENTIFICADOR)) {
    //                 T(); // Para el identificador
    //             } else if (match(TokenType.NUMERO)) {
    //                 NUM(); // Para el numero
    //             } else {
    //                 if(index < arbolSintac.size()){
    //                     arbolSintac currentToken = arbolSintac.get(index);
    //                     Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //                     if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
    //                         int line = currentToken.getLine();
    //                         int column = currentToken.getColumn();
    //                         errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                     }else{
    //                         int line = currentToken.getLine();
    //                         int column = currentToken.getColumn();
    //                         errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                         consume();
    //                     }
    //                 }else{
    //                     arbolSintac currentToken = arbolSintac.get(index-1);
    //                     int line = currentToken.getLine();
    //                     int column = currentToken.getColumn() + getLength(currentToken.getId());
    //                     errors.add("Se esperaba un '(' o un Número o Identificador en la línea " + line + ", columna " + column);
    //                 }
    //             }
    //         }
    //     }else{
    //         return;
    //     }

        
    // }

    // private void KP() throws Exception {
    //     if(index < arbolSintac.size()){
    //         Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //         arbolSintac nextToken = null;
    //         Token nextTokenValue = null;    
    //         if(index + 1 < arbolSintac.size()){
    //             nextToken = arbolSintac.get(index + 1);
    //             nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
    //         }

    //         if(actualCurrentToken.getType().equals(TokenType.PARENTESIS_DE_CERRADURA) && nextToken == null || actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
    //             System.out.println("Entro al return KP");
    //             return;
    //         }else{
    //             if (match(TokenType.SUMA) || match(TokenType.RESTA) || match(TokenType.MULTIPLICACION) || 
    //             match(TokenType.DIVISION)) {
    //                 consume();
    //                 if (match(TokenType.ASIGNACION)) {
    //                     Z(); // en caso de que tenga una operacion iterativa
    //                 } else {
    //                     L();
    //                     KP();
    //                 }
    //             }else{ 
    //                 if(index < arbolSintac.size()){
    //                     arbolSintac currentToken = arbolSintac.get(index);
    //                     Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
    //                     if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
    //                         int line = currentToken.getLine();
    //                         int column = currentToken.getColumn();
    //                         errors.add("Se esperaba un '+' o un '-' o un '*' o un '/' en la línea " + line + ", columna " + column);
    //                     }else{
    //                         int line = currentToken.getLine();
    //                         int column = currentToken.getColumn();
    //                         errors.add("Se esperaba un '+' o un '-' o un '*' o un '/' en la línea " + line + ", columna " + column);
    //                         consume();
    //                     }
    //                 }else{
    //                     arbolSintac currentToken = arbolSintac.get(index-1);
    //                     int line = currentToken.getLine();
    //                     int column = currentToken.getColumn();
    //                     errors.add("Se esperaba un '+' o un '-' o un '*' o un '/' en la línea " + line + ", columna " + column);
    //                 }   
    //             }
    //         PYC();
    //         }
    //     }else{
    //         return;
    //     }
    // }

    // -------------------------------------------------------------------------------------------------------------------------------

    
    // -------------------------------------------------------------------------------------------------------------------------------
    // Para las producciones que se comparten en su mayoria

    private void H() throws Exception{
        String ope = OPE(); // Verfico que sea un tipo de dato
        Y(ope); // Verifico que es lo siguiente que llega
    }

    private void Y(String ope) throws Exception {
        UUID idT =  T(); // Verifico que me llegue un id
        if(idT != null && ope != null)
            tokens.get(getIndex(idT)).setTipoToken(ope);
            codigo += "def " + tokens.get(getIndex(idT)).getValue() + ", " + ope + "||";
        if (match(TokenType.PUNTO_Y_COMA)) {
            PYC(); // Si llega el punto y coma termina
        } else if (match(TokenType.ASIGNACION)) {
            IGU(); // Si llega el igual es necesario recibir el valor
            UUID idR = R(); // Para validar el valor y, punto y coma
            if(idR != null){
                boolean isEquals = false;
                if(tokens.get(getIndex(idR)).getType() == TokenType.IDENTIFICADOR){
                    if(tokens.get(getIndex(idR)).getTipoToken() != null){
                        isEquals = tokens.get(getIndex(idR)).getTipoToken().equals(ope);
                    }else{
                        errors.add("Error: la variable '" + tokens.get(getIndex(idR)).getValue() + "' no ha sido declarada (línea " + arbolSintac.get(index-1).getLine() + ", columna " + arbolSintac.get(index-1).getColumn() + ")");
                    }
                }else{
                    isEquals = ope.equals(tokens.get(getIndex(idR)).getTipoToken());
                }
                if(isEquals){
                    tokens.get(getIndex(idT)).setTipoToken(ope);
                    tokens.get(getIndex(idT)).setValorToken(tokens.get(getIndex(idR)).getValorToken());
                    codigo += tokens.get(getIndex(idT)).getValue() + " = " + tokens.get(getIndex(idR)).getValorToken() + "||";
                }else{
                    errors.add("No se puede asignar un valor de tipo '" + tokens.get(getIndex(idR)).getTipoToken() + "' a una variable de tipo '" + ope + "' (línea " + arbolSintac.get(getIndexArbol(idR)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idR)).getColumn() + ")");
                }
            }
        } else if (match(TokenType.COMA)) {
            // Si me llega una coma
            if (match(TokenType.COMA)) {
                consume();
            } else {
                if(index < arbolSintac.size()){
                    arbolSintac currentToken = arbolSintac.get(index);
                    Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                    if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
                    }else{
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
                        consume();
                    }
                }else{
                    arbolSintac currentToken = arbolSintac.get(index);
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una ',' en la línea " + line + ", columna " + column);
                }
            }
            Y(ope);// recursivo
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un ';' o un '=' o una ',' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un ';' o un '=' o una ',' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un ';' o un '=' o una ',' en la línea " + line + ", columna " + column);
            }
        }
    }

    private void G() throws Exception{
        if(match(TokenType.CADENA) || match(TokenType.TRUE) || match(TokenType.FALSE)){
            consume(); // Para consumir el token
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una cadena o True o False en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una cadena o True o False en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba una cadena o True o False en la línea " + line + ", columna " + column);
            }
        }
    }

    private void E() throws Exception{
        J();
        EP();
    }

    private void EP() throws Exception{
        if(match(TokenType.SUMA) || match(TokenType.RESTA)){
            consume();
            J();
            EP();
        }
    }

    private void J() throws Exception{
        M();
        JP();
    }

    private void JP() throws Exception{
        if(match(TokenType.MULTIPLICACION) || match(TokenType.DIVISION)){
            consume();
            M();
            JP();
        }
    }

    private void M() throws Exception{
        if(match(TokenType.IDENTIFICADOR)){
            T();
        }else if(match(TokenType.NUMERO)){
            NUM();
        }else if(match(TokenType.PARENTESIS_DE_APERTURA)){
            PA();
            E();
            PC();
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un identificador o un número o un '(' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un identificador o un número o un '(' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un identificador o un número o un '(' en la línea " + line + ", columna " + column);
            }
        }
    }

    private void B() throws Exception{
        if (match(TokenType.BACKGROUND) || match(TokenType.SHOW) || match(TokenType.HIDE) ||
                match(TokenType.PLAY_SOUND) || match(TokenType.STOP_SOUND)) {
            AL(); // Para determinar si me llega una de estas palabras reservadas
        } else if (match(TokenType.IF)) {
            IF(); // Para el if
        } else if (match(TokenType.IDENTIFICADOR)) {
            UUID idT = T(); // Si llega un identificador
            // Y luego un = quiere decir que es una inicializacion de variable
            if (match(TokenType.ASIGNACION) && idT != null) {
                IGU(); // Para el igual

                // if (match(TokenType.PARENTESIS_DE_APERTURA)) {
                //     Token tokenK = K();
                //     if(tokenK != null){
                //         if(isNumber(tokens.get(getIndex(idT)).getTipoToken()) && 
                //         isNumber(tokenK.getTipoToken())){
                //             codigo += tokens.get(getIndex(idT)).getValue() + " = " + tokenK.getValorToken() + "||";
                //             tokens.get(getIndex(idT)).setValorToken(tokenK.getValorToken());
                //         }
                //     }
                // } else if (match(TokenType.NUMERO) || match(TokenType.CADENA) || match(TokenType.TRUE)
                //         || match(TokenType.FALSE) || match(TokenType.IDENTIFICADOR)) {
                //     UUID idR = R(); // Para determinar el tipo de dato
                //     if(idR != null){
                //         if(isNumber(tokens.get(getIndex(idT)).getTipoToken()) &&
                //         isNumber(tokens.get(getIndex(idR)).getTipoToken())){
                //             codigo += tokens.get(getIndex(idT)).getValue() + " = " + tokens.get(getIndex(idR)).getValorToken() + "||";
                //             tokens.get(getIndex(idT)).setValorToken(tokens.get(getIndex(idR)).getValorToken());
                //         }
                //     }


                if (match(TokenType.CADENA) || match(TokenType.TRUE) || match(TokenType.FALSE)) {
                    G(); // En caso de recibir esos datos 
                    PYC(); // Para el punto y coma
                } else if (match(TokenType.NUMERO) || match(TokenType.IDENTIFICADOR)) {
                    E(); // Para la declaracion de una variable o identificador
                    PYC(); // Para el punto y coma
                }else if (match(TokenType.PARENTESIS_DE_APERTURA)) { 
                    PA(); // Para el parentesis de apertura
                    E(); // Para la declaracion de una variable o identificador
                    PC(); // Para el parentesis de cerradura
                    PYC(); // Para el " ; ""
                }else{
                    if(index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                        if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba un '(' o un Número o Cadena o Identificador o True o False en la línea " + line + ", columna " + column);
                        }else{
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba un '(' o un Número o Cadena o Identificador o True o False en la línea " + line + ", columna " + column);
                            consume();
                        }
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba un '(' o un Número o Cadena o Identificador o True o False en la línea " + line + ", columna " + column);
                    }
                }
            }
        } else if (match(TokenType.PARENTESIS_DE_APERTURA)) {
                PA(); // Para el parentesis de apertura
                DF(); // Para lo que va despues
                PYC(); // Para el punto y coma
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Background' o 'show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o un '(' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Background' o 'show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o un '(' en la línea " + line + ", columna " + column);                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba 'Background' o 'show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o un '(' en la línea " + line + ", columna " + column);
            }
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Para la estructura de menu

    // Opciones del Menu
    private void AO() throws Exception {
        PA(); // Para el parentesis de apertura
        DF(); // Para la cadena y parentesis de cerradura
        DP(); // Para el " : "
        if(index != arbolSintac.size()){
            AT(); // Para lo que puede ir dentro de cada opción
            if (match(TokenType.BREAKER)) { // Para marcar el final de una opción
                BRE(); // Para determinar que me llegue un breaker
            } else if (match(TokenType.GO)) { // Para marcar el final de una opción
                GO(); // Para el Go
            }else{
                if(index < arbolSintac.size()){
                    arbolSintac currentToken = arbolSintac.get(index);
                    Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                    if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Breaker' o 'Go' en la línea " + line + ", columna " + column);
                    }else{
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba 'Breaker' o 'Go' en la línea " + line + ", columna " + column);
                        consume();
                    }
                }else{
                    arbolSintac currentToken = arbolSintac.get(index-1);
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn() + getLength(currentToken.getId());
                    errors.add("Se esperaba 'Breaker' o 'Go' en la línea " + line + ", columna " + column);
                }
            }
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            Token nextTokenValue = null;
            if(index + 1 < arbolSintac.size()){
                nextToken = arbolSintac.get(index + 1);
                nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
            }
            if (actualCurrentToken != null && nextToken != null) {
                System.out.println("PostMenu - Actual: " + actualCurrentToken.getType() + " Siguiente: " + nextTokenValue.getType());
            } else {
                if (actualCurrentToken == null) {
                    System.out.println("PostMenu - El token actual es null" + " Siguiente: " + nextTokenValue.getType());
                }
                if (nextTokenValue == null) {
                    System.out.println("PostMenu - Anterior: "+ actualCurrentToken.getType() +" El siguiente token es null");
                }
            }
            if((actualCurrentToken.getType().equals(TokenType.PARENTESIS_DE_APERTURA) && nextTokenValue.getType().equals(TokenType.CADENA))){
            // || (actualCurrentToken.getType().equals(TokenType.GO) && nextTokenValue.getType().equals(TokenType.PUNTO_Y_COMA))){
                System.out.println("Entro a recursividad de AO");
                AO();
            }
        }
     AO(); // Para llamar esto de manera recursiva y crear tantas opciones como se desee
    }

    private void AT() throws Exception {
        if(index < arbolSintac.size()){
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            Token nextTokenValue = null;
            if(index + 1 < arbolSintac.size()){
                nextToken = arbolSintac.get(index + 1);
                nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
            }
            if (actualCurrentToken != null && nextTokenValue != null) {
                System.out.println("Menu - Actual: " + actualCurrentToken.getType() + " Siguiente: " + nextTokenValue.getType());
            } else {
                if (actualCurrentToken == null) {
                    System.out.println("Menu - El token actual es null" + " Siguiente: " + nextTokenValue.getType());
                }
                if (nextTokenValue == null) {
                    System.out.println("Menu - Anterior: "+ actualCurrentToken.getType() +" El siguiente token es null");
                }
            }
            if((actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextToken == null) 
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA))
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.SCREEN))
            || (actualCurrentToken.getType().equals(TokenType.BREAKER) && nextTokenValue.getType().equals(TokenType.PUNTO_Y_COMA))
            || (actualCurrentToken.getType().equals(TokenType.GO) && nextTokenValue.getType().equals(TokenType.PUNTO_Y_COMA))){
                System.out.println("Entro al return AT");
                return;
            }else{
                if (match(TokenType.INT) || match(TokenType.DOUBLE) || match(TokenType.STRING) || match(TokenType.BOOLEANO)) {
                    H(); // Para la declaracion de una variable
                    AT();
                } else if(match(TokenType.BACKGROUND) || match(TokenType.SHOW) || match(TokenType.HIDE) ||
                        match(TokenType.PLAY_SOUND) || match(TokenType.STOP_SOUND) || match(TokenType.IF) || match(TokenType.IDENTIFICADOR) ||
                        match(TokenType.PARENTESIS_DE_APERTURA) || match(TokenType.NUMERO)
                        ){
                    B(); // Para todas estas opciones) {
                    AT();
                } else{
                    if(index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                        if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                        }else{
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                            consume();
                        }
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn() + getLength(currentToken.getId());
                        errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                    }
                    System.out.println("Error en el Menu");
                }
            }
        }else{
            return;
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------


    private void BM() throws Exception {
        T(); // Para el identificador
        BU(); // Para lo comparadores
        RR(); // Para la igual
    }

    private UUID RR() throws Exception {
        UUID id = null;
        if (match(TokenType.NUMERO)) {
            id = NUM(); // validar si es un número
            // PYC(); // Para el ;
        } else if (match(TokenType.CADENA)) {
            id = T(); // Para validar que es un identificador
            // PYC(); // Para el ;
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número o una cadena en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número o una cadena en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba un número o una cadena en la línea " + line + ", columna " + column);
            }
        }
        return id;
    }

    // private void Z() throws Exception {
    //     IGU(); // Para los operadores
    //     RR(); // Para determinar que sea un número o identificador
    //     // PYC(); // Para el " ; "
    //     // BO(); // Para llamar al metodo de nuevo
    // }

    // -------------------------------------------------------------------------------------------------------------------------------
    // Para la estructura del if

    private void BF() throws Exception {
        LLA(); // Para la llaves de apertura
        if(index != arbolSintac.size()){
            BG(); // Para lo que va dentro del if
        }
        LLC(); // Para la llave de cerradura
    }

    private void BG() throws Exception {
        if(index < arbolSintac.size()){
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            Token nextTokenValue = null;

            if(index + 1 < arbolSintac.size()){
                nextToken = arbolSintac.get(index + 1);
                nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
            }
            System.out.println("En el IF - Actual: " + actualCurrentToken.getType() + " Siguiente: " + nextTokenValue.getType());
            if((actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextToken == null) 
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA)) 
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.ELSE))
            || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.MENU))){
                System.out.println("Entro al return BG");
                return;
            }else{
                if(match(TokenType.BACKGROUND) || match(TokenType.SHOW) || match(TokenType.HIDE) ||
                    match(TokenType.PLAY_SOUND) || match(TokenType.STOP_SOUND) || match(TokenType.IF) || match(TokenType.IDENTIFICADOR) ||
                    match(TokenType.PARENTESIS_DE_APERTURA) || match(TokenType.NUMERO)
                    ){
                    B(); // Para todas estas opciones
                    BG(); // Para la recursividad
                }else if(match(TokenType.MENU)){
                    MEN(); //Para el menu
                    BG(); // Para la recursividad
                }else{
                    if(index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                        if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                        }else{
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                            consume();

                        }
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn() + getLength(currentToken.getId());
                        errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' o 'if' o 'identificador' o 'tipo de dato' o 'Menu' en la línea " + line + ", columna " + column);
                    }
                    System.out.println("error del if");
                }
            }
        }else{
            return;
        }
        //¿Aquí podría haber un error? no se s ti 
    }

    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Para expresiones regulares

    // Para determinar si es un identificador
    private UUID T() throws Exception {
        System.out.println("Entro a T");
        if (!match(TokenType.IDENTIFICADOR)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un identificador en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un identificador en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un identificador en la línea " + line + ", columna " + column);
            }
            // System.out.println("Llegue hasta aquí");
        }else{
            consume();
            return arbolSintac.get(index-1).getId();
        }
        return null;
    }

    // Para los números
    private UUID NUM() throws Exception {
        if (!match(TokenType.NUMERO)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un número en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + currentToken.getValue().length();
                errors.add("Se esperaba un número en la línea " + line + ", columna " + column);
            }
        }else{
            consume();
            return arbolSintac.get(index-1).getId();
        }
        return null;
    }

    // Para las cadenas
    private UUID CAD() throws Exception {
        if (!match(TokenType.CADENA)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una cadena en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba una cadena en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + currentToken.getValue().length();
                errors.add("Se esperaba una cadena en la línea " + line + ", columna " + column);
            }
        }else{
            consume();
            return arbolSintac.get(index-1).getId();
        }
        return null;
    }

    private UUID TF() throws Exception {
        if (!match(TokenType.TRUE) || !match(TokenType.FALSE)) {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'True' o 'False' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'True' o 'False' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'True' o 'False' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
            return arbolSintac.get(index-1).getId();
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Para palabras reservadas
    private void X() throws Exception {
        //Si recibo define
        if (!match(TokenType.DEFINE)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'define' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'define' en la línea " + line + ", columna " + column);
                    consume();
                }    
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + currentToken.getValue().length();
                errors.add("Se esperaba 'define' en la línea " + line + ", columna " + column);
            }
        }else{
            consume();
        }
        System.out.println(index);
        //Para el resto
        N();
    }

    private String OPE() throws Exception {
        if (match(TokenType.INT)) {
            consume();
            return "INT";
        }else if(match(TokenType.DOUBLE)){
            consume();
            return "DOUBLE";
        }else if(match(TokenType.STRING)){
            consume();
            return "CADENA";
        }else if(match(TokenType.BOOLEANO)){
            consume();
            return "BOOLEANO";
        }else{
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'un tipo de dato' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'un tipo de dato' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'un tipo de dato' en la línea " + line + ", columna " + column);
            }
        }
        return null;
    }

    private String U() throws Exception {
        if (match(TokenType.COLOR)) {
            consume();
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Color' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Color' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Color' en la línea " + line + ", columna " + column);
            }
        }
        String color = DE(); // Para el color
        return color;
    }

    // private void AB() throws Exception {
    //     if (match("Image") || match("Sound")) {
    //         consume();
    //     }
    //     AC(); // Para lo que va despues, ("dirección")
    // }

    // private void SCR() throws Exception {
    //     if (match("Screen")) {
    //         consume();
    //     } else {
    //         throw new Exception("Se esperaba 'Screen' ");
    //     }
    //     AI();// Para lo que va despues de la palabra reservada Screen
    // }

    private void AL() throws Exception {
        TokenType type = null;
        if (match(TokenType.BACKGROUND) || match(TokenType.SHOW) || match(TokenType.HIDE) ||
                match(TokenType.PLAY_SOUND) || match(TokenType.STOP_SOUND)) {
            type = tokens.get(getIndex(arbolSintac.get(index).getId())).getType();
            consume();
        }else{
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Background' o 'Show' o 'Hide' o 'PlaySound' o 'StopSound' en la línea " + line + ", columna " + column);
            }
        }
        AN(type); // Para lo que va despues, (id)
    }

    private void MEN() throws Exception {/// Menu voy a deber
        if (match(TokenType.MENU)) {
            consume();
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Menu' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Menu' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Menu' en la línea " + line + ", columna " + column);
            }
        }
        DP(); // Para el " : "
        AO(); // Para las opciones del menu
    }

    private void BRE() throws Exception {
        if (match(TokenType.BREAKER)) {
            consume();
            codigo += "goto||";
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Breaker' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Breaker' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Breaker' en la línea " + line + ", columna " + column);
            }
        }
        PYC(); // Para el " ; "
    }

    private void GO() throws Exception {
        if (match(TokenType.GO)) {
            consume();
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Go' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba 'Go' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba 'Go' en la línea " + line + ", columna " + column);
            }
        }
        UUID idT = T(); // Para el identificador
        if(idT != null){
            codigo += "goto " + tokens.get(getIndex(idT)).getValue() + "||";
        }
        PYC(); // Para el " ; "
    }

    private void IF() throws Exception {
        if (match(TokenType.IF)) {
            consume();
        } else {
            if(index < arbolSintac.size()){
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un 'if' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un 'if' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + currentToken.getValue().length();
                errors.add("Se esperaba un 'if' en la línea " + line + ", columna " + column);
            }
        }
        PA(); // Para el parentesis de apertura
        BM(); // Para el condicional
        PC(); // Para el parentesis de cerradura
        codigo += "if tbm||";
        BF(); // Para lo que va dentro del if
        ELS(); // Para poner o no, un else if o else
    }

    private void ELS() throws Exception {
        if(index < arbolSintac.size()){
            Token actualCurrentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
            arbolSintac nextToken = null;
            Token nextTokenValue = null;
            if(index + 1 < arbolSintac.size()){
                nextToken = arbolSintac.get(index + 1);
                nextTokenValue = tokens.get(getIndex(arbolSintac.get(index + 1).getId()));
            }

            if((actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextToken == null) || (actualCurrentToken.getType().equals(TokenType.LLAVE_DE_CERRADURA) && nextTokenValue.getType().equals(TokenType.LLAVE_DE_CERRADURA))){
                return;
            }else{
                if (match(TokenType.ELSE)) {
                    consume();
                    System.out.println("Concuso else");
                    if (match(TokenType.IF)) {
                        IF(); // Si recibo un else if
                    } else {
                        BF(); // Si recibo else
                    }
                }else{
                    if(index < arbolSintac.size()){
                        arbolSintac currentToken = arbolSintac.get(index);
                        Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                        if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba un 'else' o 'else if' en la línea " + line + ", columna " + column);
                        }else{
                            int line = currentToken.getLine();
                            int column = currentToken.getColumn();
                            errors.add("Se esperaba un 'else' o 'else if' en la línea " + line + ", columna " + column);
                            consume();
                        }
                    }else{
                        arbolSintac currentToken = arbolSintac.get(index-1);
                        int line = currentToken.getLine();
                        int column = currentToken.getColumn();
                        errors.add("Se esperaba un 'else' o 'else if' en la línea " + line + ", columna " + column);
                    }
                }
            }
        }else{
            return;
        }
    }
    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Metodos para signos de operadores

    private void IGU() throws Exception {
        // System.out.println("Entro a IGU");
        if (!match(TokenType.ASIGNACION)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '=' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '=' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                Token currentToken = tokens.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + currentToken.getValue().length();
                errors.add("Se esperaba un '=' en la línea " + line + ", columna " + column);
            }
        } else{
            consume();
        }
    }

    private int BU() throws Exception {
        int comp = 0;
        if (match(TokenType.MENOR_QUE)) {
            consume();
            comp = 1;
        } else if(match(TokenType.MAYOR_QUE)){
            consume();
            comp = 2;
        } else if(match(TokenType.MENOR_O_IGUAL_QUE)){
            consume();
            comp = 3;
        } else if(match(TokenType.MAYOR_O_IGUAL_QUE)){
            consume();
            comp = 4;
        } else if(match(TokenType.IGUALACION)){
            consume();
            comp = 5;
        } else {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.PARENTESIS_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba '<' o '>' o '<=' o '>=' o '==' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba '<' o '>' o '<=' o '>=' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba '<' o '>' o '<=' o '>=' en la línea " + line + ", columna " + column);
            }
        }
        return comp;
    }

    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------

    private String BQ() throws Exception {
        if (!match(TokenType.ASIGNACION_DE_COLOR)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '#' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '#' en la línea " + line + ", columna " + column);
                    consume();
                }
            } else {
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un '#' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
        
        if(match(TokenType.COLOR_HEX)){
            try {
                Token currentToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                String color = currentToken.getValorToken() + "";
                boolean isColor = isColor(color);
                System.out.println("isColor: " + isColor);
                isColor = true;
                if(isColor){
                    consume();
                    return "#"+color;
                }
            } catch (Exception e) {
                // System.out.println("Error en el color");
            }
        } else {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un color hexadecimal en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un color hexadecimal en la línea " + line + ", columna " + column);
                    consume();
                }
            } else {
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba un color hexadecimal en la línea " + line + ", columna " + column);
            }
        }
        return null;
    }

    // -------------------------------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------------------------------
    // Metodos para signos de delimitadores

    // Para llaves de Apertura " { "
    private void LLA() throws Exception {
        if (!match(TokenType.LLAVE_DE_APERTURA)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '{' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '{' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba ' { ' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
    }

    // /Para llaves de Cerradura " } "
    private void LLC() throws Exception {
        System.out.println("Entro a LLC");

        if (!match(TokenType.LLAVE_DE_CERRADURA)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '}' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba un '}' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba un '}' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
    }

    // /Para el punto y coma " ; "
    private void PYC() throws Exception {
        if(!match(TokenType.PUNTO_Y_COMA)){
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ; ' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ; ' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba ' ; ' en la línea " + line + ", columna " + column);
            }
        }else{
            consume();
        }
    }

    /// Para parentesis de Aparetura " ( "
    private void PA() throws Exception {
        if (!match(TokenType.PARENTESIS_DE_APERTURA)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ( ' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ( ' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba ' ( ' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
    }

    /// Para parentensis de cerradura " ) "
    private void PC() throws Exception {
        if (!match(TokenType.PARENTESIS_DE_CERRADURA)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ) ' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' ) ' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn() + getLength(currentToken.getId());
                errors.add("Se esperaba ' ) ' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
    }

    // Para los dos puntos " : "
    private void DP() throws Exception {
        if (!match(TokenType.DOS_PUNTOS)) {
            if (index < arbolSintac.size()) {
                arbolSintac currentToken = arbolSintac.get(index);
                Token otherToken = tokens.get(getIndex(arbolSintac.get(index).getId()));
                if(otherToken.getType().equals(TokenType.LLAVE_DE_CERRADURA)){
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ' : ' en la línea " + line + ", columna " + column);
                }else{
                    int line = currentToken.getLine();
                    int column = currentToken.getColumn();
                    errors.add("Se esperaba ':' en la línea " + line + ", columna " + column);
                    consume();
                }
            }else{
                arbolSintac currentToken = arbolSintac.get(index-1);
                int line = currentToken.getLine();
                int column = currentToken.getColumn();
                errors.add("Se esperaba ':' en la línea " + line + ", columna " + column);
            }
        } else {
            consume();
        }
    }

    // -------------------------------------------------------------------------------------------------------------------------------
    // Métodos auxiliares

    //Metodo para hacer concidencias con el token en turno con lo esperado de la gramatica
    private boolean match(TokenType expectedType) {
        if (index >= 0 && index < arbolSintac.size()) { // Verifica los límites antes de acceder
            int ind = getIndex(arbolSintac.get(index).getId());
            // System.out.println(ind+"\n");
            if(ind >= 0){
                Token token = tokens.get(ind);
                // System.out.println(ind + " " + token.getType() + " " + expectedType);
                return token.getType() == expectedType;
            }
        }
        return false;
    }

    //Metodo para pedir un nuevo token de la lista de tokens
    private void consume() {
        if (index < arbolSintac.size()) {
            // System.out.println(tokens.get(index).getValue());
            index++;
            } else {
            // Si no quedan más tokens, puedes lanzar una excepción o simplemente mostrar
            //un mensaje de advertencia
            System.out.println("¡Se ha llegado al final del archivo fuente!");
            // System.exit(0);
        }
    }

    public int getIndex(UUID id) {
        for(int i = 0; i < arbolSintac.size(); i++){
            if(tokens.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    private int getIndexArbol(UUID id){
        for(int i = 0; i < arbolSintac.size(); i++){
            if(arbolSintac.get(i).getId().equals(id)){
                return i;
            }
        }
        return -1;
    }

    private int getLength(UUID id){
        for(int i = 0; i < arbolSintac.size(); i++){
            if(tokens.get(i).getId().equals(id)){
                return tokens.get(i).getValue().length();
            }
        }
        return 0;
    }

    private boolean isColor(String cad){
        // String cad2 = cad.replaceAll("\"", "");
        String patterHex = "^[A-Fa-f0-9]{6}$";
        Pattern patternH = Pattern.compile(patterHex);
        Matcher matcherH = patternH.matcher(cad);
        boolean match = matcherH.matches();
        return match;
    }

    private TokenType valorPrimitive(Object variable){
        if(variable instanceof Integer){
            return TokenType.INT;
        }
        if(variable instanceof Double){
            return TokenType.DOUBLE;
        }
        if(variable instanceof String){
            return TokenType.CADENA;
        }
        if(variable instanceof Boolean){
            return TokenType.BOOLEANO;
        }
        return null;
    }

    private int parseInt(Object valor){
        try {
            return (Integer) valor;
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    private double parseDouble(Object valor){
        try {
            return (Double) valor;
        } catch (Exception e) {
            return Double.MIN_VALUE;
        }
    }

    private String parseString(Object valor){
        try {
            return (String) valor;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean parseBoolean(Object valor){
        try {
            return (Boolean) valor;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private boolean parseNumber(Object valor){
        if(valor instanceof Integer || valor instanceof Double){
            return true;
        }
        return false;
    }

    private boolean isNumber(String valorToken){
        if(valorToken.equals("DOUBLE") || valorToken.equals("INT") || valorToken.equals("NUMERO")){
            return true;
        }
        return false;
    }

    private boolean areEquals(int comp, Object valor1, Object valor2){
        int val1Int = parseInt(valor1);
        int val2Int = parseInt(valor2);
        double val1Double = parseDouble(valor1);
        double val2Double = parseDouble(valor2);
        if(val1Int != Integer.MIN_VALUE){
            if(val2Int != Integer.MIN_VALUE){
                switch (comp) {
                    case 1:
                        codigo += "tbm = " + val1Int + "<" + val2Int + "||";
                        return val1Int < val2Int;
                    case 2:
                        codigo += "tbm = " + val1Int + ">" + val2Int + "||";
                        return val1Int > val2Int;
                    case 3:
                        codigo += "tbm = " + val1Int + "<=" + val2Int + "||";
                        return val1Int <= val2Int;
                    case 4:
                        codigo += "tbm = " + val1Int + ">=" + val2Int + "||";
                        return val1Int >= val2Int;
                    case 5:
                        codigo += "tbm = " + val1Int + "==" + val2Int + "||";
                        return val1Int == val2Int;
                    default:
                        return false;
                }
            }else if(val2Double != Double.MIN_VALUE){
                switch (comp) {
                    case 1:
                        codigo += "tbm = " + val1Int + "<" + val2Double + "||";
                        return val1Int < val2Double;
                    case 2:
                        codigo += "tbm = " + val1Int + ">" + val2Double + "||";
                        return val1Int > val2Double;
                    case 3:
                        codigo += "tbm = " + val1Int + "<=" + val2Double + "||";
                        return val1Int <= val2Double;
                    case 4:
                        codigo += "tbm = " + val1Int + ">=" + val2Double + "||";
                        return val1Int >= val2Double;
                    case 5:
                        codigo += "tbm = " + val1Int + "==" + val2Double + "||";
                        return val1Int == val2Double;
                    default:
                        return false;
                }
            }
        }else if(val1Double != Double.MIN_VALUE){
            if(val2Int != Integer.MIN_VALUE){
                switch (comp) {
                    case 1:
                        codigo += "tbm = " + val1Double + "<" + val2Int + "||";
                        return val1Double < val2Int;
                    case 2:
                        codigo += "tbm = " + val1Double + ">" + val2Int + "||";
                        return val1Double > val2Int;
                    case 3:
                        codigo += "tbm = " + val1Double + "<=" + val2Int + "||";
                        return val1Double <= val2Int;
                    case 4:
                        codigo += "tbm = " + val1Double + ">=" + val2Int + "||";
                        return val1Double >= val2Int;
                    case 5:
                        codigo += "tbm = " + val1Double + "==" + val2Int + "||";
                        return val1Double == val2Int;
                    default:
                        return false;
                }
            }else if(val2Double != Double.MIN_VALUE){
                switch (comp) {
                    case 1:
                        codigo += "tbm = " + val1Double + "<" + val2Double + "||";
                        return val1Double < val2Double;
                    case 2:
                        codigo += "tbm = " + val1Double + ">" + val2Double + "||";
                        return val1Double > val2Double;
                    case 3:
                        codigo += "tbm = " + val1Double + "<=" + val2Double + "||";
                        return val1Double <= val2Double;
                    case 4:
                        codigo += "tbm = " + val1Double + ">=" + val2Double + "||";
                        return val1Double >= val2Double;
                    case 5:
                        codigo += "tbm = " + val1Double + "==" + val2Double + "||";
                        return val1Double == val2Double;
                    default:
                        return false;
                }
            }
        }
        return false;
    }

    private void asignarValor(UUID idT, Object valor){
        try {
            tokens.get(getIndex(idT)).setValorToken(valor);
        } catch (Exception e) {
            errors.add("Error: no se pudo asignar valor a " + tokens.get(getIndex(idT)).getValorToken() + " (línea " + arbolSintac.get(getIndexArbol(idT)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idT)).getColumn() + ")");
        }
    }

    private void asignarValor(UUID idAsignado, UUID idAsignador){
        try {
            tokens.get(getIndex(idAsignado)).setValorToken(tokens.get(getIndex(idAsignador)).getValorToken());
        } catch (Exception e) {
            errors.add("Error: no se pudo asignar valor a " + tokens.get(getIndex(idAsignado)).getValorToken() + " (línea " + arbolSintac.get(getIndexArbol(idAsignado)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idAsignado)).getColumn() + ")");
        }
    }

    private void asignarTipo(UUID idT, String tipo){
        try {
            tokens.get(getIndex(idT)).setTipoToken(tipo);
        } catch (Exception e) {
            errors.add("Error: no se pudo asignar tipo a " + tokens.get(getIndex(idT)).getValorToken() + " (línea " + arbolSintac.get(getIndexArbol(idT)).getLine() + ", columna " + arbolSintac.get(getIndexArbol(idT)).getColumn() + ")");
        }
    }

    private String selectType (String tip){
        String tipo = null;
        if(tip.equals("INT") || tip.equals("DOUBLE")){
            tipo = "number";
        }else if(tip.equals("CADENA")){
            tipo = "string";
        }else if(tip.equals("BOOLEANO")){
            tipo = "boolean";
        }

        return tipo;
    }

    
    public List<String> getErrors() {
        return errors;
    }
}