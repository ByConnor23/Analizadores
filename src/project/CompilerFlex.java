package project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CompilerFlex {
    public static void main(String[] args) throws IOException, Exception {
        String ruta = "D:/ITESCAM/Octavo Semestre/Programs/lexer_project/src/project/Lexer.flex";
        String ruta2 = "C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/Syntax.cup"};
        generateLexer(ruta, ruta2, rutaS);
    }

    public static void generateLexer(String ruta, String ruta2, String[] rutaS)throws IOException, Exception{
        File archivo;
        archivo = new File(ruta);
        JFlex.Main.generate(archivo);
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/sym.java"), 
                Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/sym.java")
        );
        Path rutaSin = Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/Sintax.java"), 
                Paths.get("C:/Users/willi/OneDrive/Documentos/OctavoSemestre/Automatas ii/Analizadores/src/project/Sintax.java")
        );
    }
}
