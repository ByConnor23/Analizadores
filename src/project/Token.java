package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {

    public Token() {
    }

    public static List<String> tokenize(String sourceCode) {
        List<String> tokens = new ArrayList<>();

        Pattern stringPattern = Pattern.compile("(['\"])(.*?)\\1"); // busca cadenas
        Pattern identifierPattern = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");// busca palabras
        Pattern numberPattern = Pattern.compile("\\d+");// busca numeros
        Pattern punctuationPattern = Pattern.compile("[(){};,\\.=:<>+\\-*/]");// busca signos de puntuacion
        Pattern separadoPattern = Pattern.compile("[\n\\s]");// busca separadores

        Matcher matcher;

        while (!sourceCode.isEmpty()) {
            // Busco coincidencias con los tokens
            // Si encuentran alguna, la guarda dependiendo de cuál coincidencia sea
            matcher = stringPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add(match);
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = identifierPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                if (isKeyword(match)) {
                    tokens.add(match);
                } else {
                    tokens.add(match);
                }
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = numberPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add(match);
                sourceCode = sourceCode.substring(match.length());
                continue;
            }

            matcher = punctuationPattern.matcher(sourceCode);
            if (matcher.lookingAt()) {
                String match = matcher.group();
                tokens.add(match);
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
            tokens.add(sourceCode);
            break;
        }

        return tokens;
    }

    public static boolean isKeyword(String token) {
        // Lista de palabras clave de ejemplo
        String[] keywords = { "public", "static", "void", "main", "int", "double", "string", "bool", "false", "true", "if", "else",
                "breaker", "Screen", "Menu", "go", "define", "Color", "Image", "Sound", "setBackground", "playSound", "stopSound", "Character" }; // Mario y sus palabras claves
        return Arrays.asList(keywords).contains(token);
    }
}
