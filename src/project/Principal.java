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
        int count = 1;
        String sourceCode = "public static void main(){show();[]\n}";
        Lexer lexer = new Lexer(new StringReader(sourceCode));
        List<Token> tokens = new ArrayList<>();
        while(true){
            TokenType token = lexer.yylex();
            if(token == null){
                if(!tokens.isEmpty()){
                    System.out.println("Tokens encontrados:\n");
                    tokens.forEach(toke -> {
                        System.out.println("Token: " + toke.getValue() + " " + toke.getLine());
                    });
                }
                
                return;
            }
            if(token == TokenType.ERROR_TOKEN_DESCONOCIDO){
                System.out.println("Token desconocido: " + lexer.lexeme + " " + count);
                // return;
            }else{
                Token token1 = new Token(token, lexer.lexeme, count, lexer.yylength());
                tokens.add(token1);
            }
            
            // switch(token){
            //     case PUBLIC:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case STATIC:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case VOID:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case MAIN:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case PARENTESIS_DE_APERTURA:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case PARENTESIS_DE_CERRADURA:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case LLAVE_DE_APERTURA:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case LLAVE_DE_CERRADURA:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            //     case ERROR_TOKEN_DESCONOCIDO:
            //         System.out.println("Token desconocido: " + lexer.lexeme + " " + count);
            //         break;
            //     default:
            //         System.out.println("Token: " + lexer.lexeme + " " + count);
            //         break;
            // }
        }
    }
}
