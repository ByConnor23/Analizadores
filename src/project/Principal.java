package project;

import java.io.IOException;
import java.io.StringReader;

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
        String sourceCode = "public static void main(){show();}";
        Lexer lexer = new Lexer(new StringReader(sourceCode));
        while(true){
            Tokens token = lexer.yylex();
            if(token == null){
                System.out.println("Fin");
                return;
            }
            switch(token){
                case PUBLIC:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case STATIC:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case VOID:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case MAIN:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case PARENTESIS_DE_APERTURA:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case PARENTESIS_DE_CERRADURA:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case LLAVE_DE_APERTURA:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case LLAVE_DE_CERRADURA:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                case ERROR_TOKEN_DESCONOCIDO:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
                default:
                    System.out.println("Token: " + lexer.lexeme + " " + count);
                    break;
            }
        }
    }
}
