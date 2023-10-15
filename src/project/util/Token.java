package project.util;

public class Token {
    private final String lexeme;
    private final String lexicalComp;
    private final int line;
    private final int column;

    public Token(String paramString1, String paramString2, int paramInt1, int paramInt2) {
        this.lexeme = paramString1;
        this.lexicalComp = paramString2;
        this.line = paramInt1;
        this.column = paramInt2;

        if (this.lexeme == null) {

            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene un valor nulo como lexema, esto podría llegar a generar errores ó\n"
                    + Functions.ANSI_RED_BLACK + "resultados incorrectos. El token referido es el siguiente:\n"
                    + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.lexeme.isEmpty()) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene una cadena vacía como lexema, esto podría llegar a generar errores\n"
                    + Functions.ANSI_RED_BLACK + "ó resultados incorrectos. El token referido es el siguiente:\n"
                    + Functions.ANSI_BLUE_BLACK + this);
        }

        if (this.lexicalComp == null) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene un valor nulo como componente léxico, esto generará conflictos ó\n"
                    + Functions.ANSI_RED_BLACK
                    + "resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n"
                    + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.lexicalComp.contains("")) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene uno o más espacios en el componente léxico, esto generará conflictos\n"
                    + Functions.ANSI_RED_BLACK
                    + "ó resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n"
                    + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        } else if (this.lexicalComp.isEmpty()) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene una cadena vacía como componente léxico, esto generará conflictos ó\n"
                    + Functions.ANSI_RED_BLACK
                    + "resultados incorrectos al momento de agrupar las producciones, proceda a corregirlo. El token referido\n"
                    + Functions.ANSI_RED_BLACK + "es el siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        }

        if ((((this.line <= 0) ? 1 : 0) | ((this.column <= 0) ? 1 : 0)) != 0) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "Se ha creado un token que contiene un número de línea y/o columna menor o igual que 0, lo cuál es ilógico, esto\n"
                    + Functions.ANSI_RED_BLACK
                    + "podría llegar a generar errores ó resultados incorrectos, proceda a corregirlo. El token referido es el\n"
                    + Functions.ANSI_RED_BLACK + "siguiente:\n" + Functions.ANSI_BLUE_BLACK + this);
        }
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String getLexicalComp() {
        return this.lexicalComp;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public String toString() {
        return "Token [lexeme=" + this.lexeme + ", lexicalComp=" + this.lexicalComp + ", line=" + this.line
                + ", column=" + this.column + "]";
    }

}
