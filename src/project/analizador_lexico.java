package project;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class analizador_lexico {

    //Constructor este al ser llamado toma como parametro una cadena que debera provenir de la ventana 
    public analizador_lexico(String sourceCode) {
        //Genero los tokens y clasifico
        List<String> tokens = tokenize(sourceCode);
        //Genero el archivo tabla de simbolos y escribo los tokens que anteriormente analice
        generateTable(tokens);
    }

    private static void generateTable(List<String> tokens) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_simbolos.txt"))) {
            for (String token : tokens) {
                writer.write(token);
                writer.newLine();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Error al leer el archivo fuente: " + e.getMessage());
        }
    }

    private static List<String> tokenize(String sourceCode) {
        List<String> tokens = new ArrayList<>();

        Pattern stringPattern = Pattern.compile("(['\"])(.*?)\\1"); // busca cadenas
        Pattern identifierPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");// busca palabras
        Pattern numberPattern = Pattern.compile("\\d+");// busca numeros
        Pattern punctuationPattern = Pattern.compile("[(){};,\\.=]");// busca signos de puntuacion
        Pattern separadoPattern = Pattern.compile("[\n  ]");// busca separadores

        Matcher matcher;

        while (!sourceCode.isEmpty()) {
            // Busco coicidencias con los tokens
            //Si encuentan alguna guarda dependiendo de cual coicidencia sea
            matcher = stringPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add("String: " + match);
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = identifierPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                if (isKeyword(match)) {
                    tokens.add("Keyword: " + match);
                } else {
                    tokens.add("Identifier: " + match);
                }
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = numberPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add("Number: " + match);
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = punctuationPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add("Punctuation: " + match);
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = separadoPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            // Si no se encuentra ningún patrón, se muestra "Token no reconocido" y se
            // detiene la ejecución
            tokens.add("Token no reconocido" + sourceCode);
            break;
            // sourceCode = sourceCode.substring(1);
        }

        return tokens;
    }

    private static boolean isKeyword(String token) {
        // Lista de palabras clave de ejemplo
        String[] keywords = { "int", "double", "string", "bool", "false", "true", "loopFor", "If", "else", "key",
                "case", "breaker", "start", "add", "minus", "multi", "div", "new", "back", "list", "Screen", "menu",
                "go", "define", "color", "Image", "Sound", "setBackground", "playSound", "stopSound", "chBox",
                "Character" }; //Mario y sus palabras claves
        return Arrays.asList(keywords).contains(token);
    }

    // Prueba de uso
    public static void main(String[] args) {
        String sourceCode = "define p1 = Character (\'Marta\' , color = \"#92nd9s\");\n" +
                "define p2 = Character ( \'Yo\' , color = \"#c8c8ff\" );\n" +
                "Screen start ( ) {\n" +
                "        ( \"Se lo eguntaré...\" );\n" +
                "        p1 ( \"Um... te gustaría...\" );\n" +
                "        p1 ( \"¿Te gustaría ir al cine después de la escuela?\" );\n" +
                "       ( \"Silencio.\" );\n" +
                "       ( \"El se sorprendió por tan repentina pregunta, y entonces...\" );" +
                "       p2 ( \"Claro, me encantaría ir contigo\" );" +
                "        ( \"El no sabía que esa noche su vida cambiaria para siempre\" );" +
                "   end;\n" +
                "}";

        analizador_lexico lex = new analizador_lexico(sourceCode);
        /*
         * for (String token : tokens) {
         * System.out.println(token);
         * }
         */
    }
}
