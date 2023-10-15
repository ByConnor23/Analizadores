package project.util;

public class ErrorLSSL {
    private String desc;
    private String lexemas;
    private final int numero;
    private final int linea;
    private final int columna;

    public ErrorLSSL(int paramInt, String paramString, Token paramToken) {
        this.numero = paramInt;
        this.desc = paramString;
        this.lexemas = paramToken.getLexeme();
        this.linea = paramToken.getLine();
        this.columna = paramToken.getColumn();
    }

    public ErrorLSSL(int paramInt, String paramString, Production paramProduction, boolean paramBoolean) {
        this.numero = paramInt;
        this.desc = paramString;
        this.lexemas = paramProduction.lexemeRank(0, -1);
        if (paramBoolean) {
            this.linea = paramProduction.getLine();
            this.columna = paramProduction.getColumn();
        } else {
            this.linea = paramProduction.getFinalLine();
            this.columna = paramProduction.getFinalColumn();
        }
    }

    public int getLine() {
        return this.linea;
    }

    public int getColumn() {
        return this.columna;
    }

    public String toString() {
        if (this.lexemas == null) {
            this.lexemas = "~VALOR NULO~";
        }
        if (this.desc == null) {
            this.desc = "~DESCRIPCION NULA~";
        }
        return "Error " + this.numero + ": " + this.desc + " en " + this.lexemas + " en la linea " + this.linea
                + " y columna " + this.columna;
    }
}
