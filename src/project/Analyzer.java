package project;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {

    //Constructor este al ser llamado toma como parametro una cadena que debera provenir de la ventana
    public Analyzer(String sourceCode) {
        // Remuevo los comentarios
        String removeComm = removeComments(sourceCode);
        //Genero los tokens y clasifico
        // tokens = Tokens.tokenize(removeComm);
        //Creo una instancia para el Lexico
        Lexer lexer = new Lexer(new StringReader(removeComm));
        //Para los tokens que trajo el Lexico
        tokens = new ArrayList<>();
        // Token token;
        tokensIds = new ArrayList<>();
        try {
            Token currentToken;
            while ((currentToken = lexer.yylex()) != null) {
                boolean tokenExists = false;
                if(
                    currentToken.getType() != TokenType.NUMERO &&
                    currentToken.getType() != TokenType.CADENA &&
                    currentToken.getType() != TokenType.COLOR_HEX &&
                    currentToken.getType() != TokenType.TOKEN_UNKNOWN
                ){
                    if(currentToken.getType() == TokenType.IDENTIFICADOR){
                        for (Token tok : tokens) {
                            if(tok.getValue() != null){
                                //System.out.println(tok.getValue() + " " + currentToken.getValue());
                                if (currentToken.getValue().equals(tok.getValue())) {
                                    tokenExists = true;
                                    currentToken.setId(tok.getId());
                                    break;
                                }else{
                                    String tipo = "";
                                    Token tipoToken = tokens.get(tokens.size()-1);
                                    if(tipoToken.getType() == TokenType.CHARACTER){
                                        tipo = "CHARACTER";
                                    }else if(tipoToken.getType() == TokenType.IMAGE){
                                        tipo = "IMAGE";
                                    }else if(tipoToken.getType() == TokenType.SOUND){
                                        tipo = "SOUND";
                                    }
                                    currentToken.setTipoToken(tipo);
                                }
                            }
                        }
                    }else{
                        for (Token tok : tokens) {
                            if(
                                tok.getType() != TokenType.NUMERO &&
                                tok.getType() != TokenType.CADENA &&
                                tok.getType() != TokenType.COLOR_HEX &&
                                tok.getType() != TokenType.TOKEN_UNKNOWN
                            ){
                                if (tok.getType() == currentToken.getType()) {
                                    tokenExists = true;
                                    currentToken.setId(tok.getId());
                                    break;
                                }
                            }
                        }
                    }
                }
                arbolSintac arbol = new arbolSintac(currentToken.getLine(), currentToken.getColumn(), currentToken.getId());
                if (!tokenExists) {
                    currentToken.setLine(0);
                    currentToken.setColumn(0);
                    tokens.add(currentToken);
                }
                tokensIds.add(arbol);
            }
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
        //Genero el archivo tabla de simbolos y escribo los tokens que anteriormente analice
        generateTable();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<arbolSintac> getTokensIds() {
        return tokensIds;
    }

    private void generateTable() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_simbolos.txt"))) {
            for (Token token : tokens) {
                String value = token.getId().toString();
                if(token.getValue() != null){
                    value = value + " | " + token.getValue();
                }else{
                    value = value + " | " + token.getValorToken().toString();
                }
                writer.write(value);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo fuente: " + e.getMessage());
        }
    }

    private static String removeComments(String input){
        //Eliminar comentarios de varias lineas
        input = input.replaceAll("##.*?##", "");
        //Eliminar comentarios de una linea
        input = input.replaceAll("/#.*(?<!\\n)", "");
        return input;
    }

    List<Token> tokens;
    List<arbolSintac> tokensIds;

}