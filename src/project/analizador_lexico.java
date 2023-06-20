package project;

import java.io.*;
import java.util.*;

public class analizador_lexico {

    public static void main(String[] args) {
        String sourceCodeFile = "fuente.txt"; // Ruta del archivo fuente
        String symbolTableFile = "tabla_simbolos.txt"; // Ruta del archivo de la tabla de símbolos
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(sourceCodeFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(symbolTableFile));

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                line = removeComments(line);
                analyzeLine(line, writer, lineNumber);
                lineNumber++;
            }

            reader.close();
            writer.close();

            System.out.println("Análisis léxico completado. Se ha generado el archivo de la tabla de símbolos.");
        } catch (IOException e) {
            System.err.println("Error al leer el archivo fuente: " + e.getMessage());
        }
    }

    private static void analyzeLine(String line, BufferedWriter writer, int lineNumber) throws IOException {
        // Expresiones regulares para identificar los diferentes tipos de tokens
        String keywordRegex = "\\b(int|double|string|bool|false|true|loopFor|If|else|key|case|breaker|start|add|minus|multi|div|new|back|list|Screen|menu|go|define|color|Image|Sound|setBackground|playSound|stopSound|chBox|Character)\\b";
        String identifierRegex = "[a-zA-Z][a-zA-Z0-9_]*";
        String stringRegex = "\"[^\"]*\"";
        String numberRegex = "\\d+(\\.\\d+)?";
        String punctuationRegex = "[.,;(){}\\[\\]]";

        // Dividir la línea en tokens
        String[] tokens = line.split("\\s+");

        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i];

            if (token.matches(keywordRegex)) {
                writer.write(token + "\t\t\tKeyword\n");
            } else if (token.matches(identifierRegex)) {
                writer.write(token + "\t\t\tIdentifier\n");
            } else if (token.matches(stringRegex)) {
                writer.write(token + "\t\t\tString\n");
            } else if (token.matches(numberRegex)) {
                writer.write(token + "\t\t\tNumber\n");
            } else if (token.matches(punctuationRegex)) {
                writer.write(token + "\t\t\tPunctuation\n");
            } else {
                // Combinar tokens hasta encontrar el cierre de comillas
                if (token.startsWith("\"") && !token.endsWith("\"")) {
                    StringBuilder combinedToken = new StringBuilder(token);
                    i++;
                    while (i < tokens.length && !tokens[i].endsWith("\"")) {
                        combinedToken.append(" ").append(tokens[i]);
                        i++;
                    }
                    if (i < tokens.length) {
                        combinedToken.append(" ").append(tokens[i]);
                        writer.write(combinedToken.toString() + "\t\t\tString\n");
                    } else {
                        writer.write(combinedToken.toString() + "\t\t\tOther\n");
                    }
                } else {
                    writer.write(token + "\t\t\tOther\n");
                }
            }

            i++;
        }
    }

    private static String removeComments(String input){
        //Eliminar comentarios de varias lineas
        input = input.replaceAll("##.*?##", "");
        //Eliminar comentarios de una linea
        input = input.replaceAll("/#.*(?<!\\n)", "");
        return input;
    }
}
