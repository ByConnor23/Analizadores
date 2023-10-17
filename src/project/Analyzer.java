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
        Token token;
        try {
            while ((token = lexer.yylex()) != null) {
                tokens.add(token);
            }
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        }
        //Genero el archivo tabla de simbolos y escribo los tokens que anteriormente analice
        generateTable(tokens);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private static void generateTable(List<Token> tokens) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_simbolos.txt"))) {
            for (Token token : tokens) {
                writer.write(token.getValue());
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

}