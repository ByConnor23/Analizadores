package project;

import java.io.File;


public class CompilerFlex {
    public static void main(String[] args) {
        String ruta = "D:/ITESCAM/Octavo Semestre/Programs/lexer_project/src/project/Lexer.flex";
        generateLexer(ruta);
    }

    public static void generateLexer(String ruta){
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }
}
