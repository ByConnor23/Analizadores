package project;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        Principal principal = new Principal();
        try {
            principal.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        String sourceCode = "public static void main(){define int = 0;\ndefine int y 9;}";
        Lexer lexer = new Lexer(new StringReader(sourceCode));
        List<Token> tokens = new ArrayList<>();

        while (true) {
            Token token = lexer.yylex();
            if (token == null) {
                if (!tokens.isEmpty()) {
                    System.out.println("\nTokens encontrados:");
                    tokens.forEach(tok -> {
                        System.out.println("Token: " + tok.getValue() + " " + tok.getType() + " " + tok.getLine() + " " + tok.getColumn());
                    });
                    int size = tokens.size();
                    System.out.println("Size of tokens list: " + size);
                }

                // Mover el análisis sintáctico aquí, después de procesar todos los tokens
                try {
                    Syntax syntax = new Syntax(tokens);
                    syntax.parse();
                    // Comprobar si hubo errores y mostrarlos
                    List<String> errores = syntax.getErrors();
                    if (!errores.isEmpty()) {
                        System.out.println("Errores sintácticos:");
                        for (String error : errores) {
                            System.out.println(error);
                        }
                    } else {
                        System.out.println("Análisis sintáctico exitoso.");
                    }
                } catch (Exception e) {
                    System.out.println("Error general: " + e.getMessage());
                }

                return;
            }

            if (token.getType() == TokenType.ERROR_TOKEN_DESCONOCIDO) {
                System.out.println("Token desconocido: " + token.getValue() + " " + token.getLine() + " " + token.getColumn());
            } else {
                Token token1 = new Token(token.getType(), token.getValue(), token.getLine(), token.getColumn());
                tokens.add(token1);
            }
        }
    }
}
