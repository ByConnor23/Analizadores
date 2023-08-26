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
        tokens = Token.tokenize(removeComm);
        //Genero el archivo tabla de simbolos y escribo los tokens que anteriormente analice
        generateTable(tokens);
    }

    public List<String> getTokens() {
        return tokens;
    }

    private static void generateTable(List<String> tokens) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_simbolos.txt"))) {
            for (String token : tokens) {
                writer.write(token);
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

    List<String> tokens;

}
