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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        String sourceCode = "public static void main(){\nshow();\nint x = 0;\n[]}";
        Lexer lexer = new Lexer(new StringReader(sourceCode));
        List<Token> tokens = new ArrayList<>();
        while(true){
            Token token = lexer.yylex();
            if(token == null){
                if(!tokens.isEmpty()){
                    System.out.println("\nTokens encontrados:");
                    tokens.forEach(toke -> {
                        System.out.println("Token: " + toke.getValue() + " " + toke.getType() + " " + toke.getLine() + " " + toke.getColumn());
                    });
                }
                
                return;
            }
            if(token.getType() == TokenType.ERROR_TOKEN_DESCONOCIDO){
                System.out.println("Token desconocido: " + token.getValue() + " " + token.getLine() + " " + token.getColumn());
                // return;
            }else{
                Token token1 = new Token(token.getType(), token.getValue(), token.getLine(), token.getColumn());
                tokens.add(token1);
            }
        }
    }
}
