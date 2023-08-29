package project.util;

import java.util.ArrayList;

public class Production {

    private String name;
    private final ArrayList<Token> tokens;
    private int line;
    private int column;
    private int lineF;
    private int columnF;

    public Production() {
        this.name = null;
        this.tokens = new ArrayList<>();
        this.line = this.column = this.lineF = this.columnF = 0;
    }

    public Production(Token paramToken) {
        this.tokens = new ArrayList<>();
        this.name = paramToken.getLexicalComp();
        this.tokens.add(paramToken);
        this.line = this.lineF = paramToken.getLine();
        this.column = this.columnF = paramToken.getColumn();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Token> getTokens() {
        return this.tokens;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public int getFinalLine() {
        return this.lineF;
    }

    public int getFinalColumn() {
        return this.columnF;
    }

    public int getSizeTokens() {
        return this.tokens.size();
    }

    public void addToken(Production paramProduction) {
        ArrayList<Token> arrayList = paramProduction.getTokens();
        arrayList.forEach(paramToken -> this.tokens.add(paramToken));

        if (arrayList.size() > 0) {
            Token token = arrayList.get(0);
            if (this.name == null) {
                this.name = token.getLexicalComp().toUpperCase();
            }
            token = arrayList.get(arrayList.size() - 1);
            this.lineF = token.getLine();
            this.columnF = token.getColumn();
        }
        if (this.tokens.size() > 0) {
            Token token = this.tokens.get(0);
            this.line = token.getLine();
            this.column = token.getColumn();
        }

    }

    public String lexemeRank(int paramInt) {
        Token token = tokenRank(paramInt);
        return (token != null) ? token.getLexeme() : null;
    }

    public String lexicalCompRak(int paramInt) {
        Token token = tokenRank(paramInt);
        return (token != null) ? token.getLexicalComp() : null;
    }

    public Token tokenRank(int paramInt) {
        int i = paramInt, j = this.tokens.size();
        if (i < 0) {
            if (i > j - 1) {
                System.out.println("\n" + Functions.ANSI_RED_BLACK
                        + "El índice excede el tamaño de tokens de la produción: " + i + ">" + (j - 1));
                return null;
            }
        } else if (i < -j) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "El índice excede el tamaño de tokens de la produción: " + i + "<" + (-j));
            return null;
        }

        if (i < 0) {
            i += j;
        }

        return this.tokens.get(i);
    }

    public String lexemeRank(int paramInt1, int paramInt2) {
        ArrayList<Token> arrayList = tokenRank(paramInt1, paramInt2);
        if (arrayList != null) {
            String str = "";
            for (byte b = 0; b < arrayList.size(); b++) {
                Token token = arrayList.get(b);
                if (b != arrayList.size() - 1) {
                    str = str + str + " ";
                } else {
                    str = str + str;
                }
            }
            return str;
        }
        return null;
    }

    public String lexicalCompRank(int paramInt1, int paramInt2) {
        ArrayList<Token> arrayList = tokenRank(paramInt1, paramInt2);
        if (arrayList != null) {
            String str = "";
            for (byte b = 0; b < arrayList.size(); b++) {
                Token token = arrayList.get(b);
                if (b != arrayList.size() - 1) {
                    str = str + str + " ";
                } else {
                    str = str + str;
                }
            }
            return str;
        }
        return null;
    }

    public ArrayList<Token> tokenRank(int paramInt1, int paramInt2) {
        int i = paramInt1, j = paramInt2, k = this.tokens.size();
        if (i >= 0) {
            if (i > k - 1) {
                System.out.println(
                        "\n" + Functions.ANSI_RED_BLACK + "El inicial excede el tamade tokens de la producción: "
                                + i + ">" + (k - 1));
                return null;
            }
        } else if (i < -k) {
            System.out.println("\n" + Functions.ANSI_RED_BLACK
                    + "El inicial es inferior al tamade tokens de la producción: " + i + "<-" + k);
            return null;
        }
        if (j >= 0) {
            if (j > k - 1) {
                System.out
                        .println("\n" + Functions.ANSI_RED_BLACK + "El final excede el tamade tokens de la producción: "
                                + j + ">" + (k - 1));
                return null;
            }
        } else if (j < -k) {
            System.out.println(
                    "\n" + Functions.ANSI_RED_BLACK + "El final es inferior al tamade tokens de la producción: "
                            + j + "<-" + k);
            return null;
        }
        if (i < 0 && j >= 0) {
            i += k;
        } else if (i >= 0 && j < 0) {
            j += k;
        }
        if (i > j) {
            System.out.println(
                    "\n" + Functions.ANSI_RED_BLACK + "El inicial debe de ser menor que el final: " + i + ">" + j);
            return null;
        }
        if (i < 0)
            i += k;
        if (j < 0)
            j += k;
        ArrayList<Token> arrayList = new ArrayList();
        for (int m = i; m <= j; m++)
            arrayList.add(this.tokens.get(m));
        return arrayList;
    }

    public boolean nameEqualTo(String paramString) {
        return this.name.equals(paramString);
    }

    public boolean nameEqualTo(String[] paramArrayOfString) {
        for (String str : paramArrayOfString) {
            if (str.equals(this.name))
                return true;
        }
        return false;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public String toString() {
        String str = "";
        int i = this.tokens.size();
        for (byte b = 0; b < i; b++) {
            if (b != i - 1) {
                str = str + str + ",\n";
            } else {
                str = str + str;
            }
        }
        return "Produccion(" + this.name + ", " + this.line + ", " + this.column + ", " + this.lineF + ", "
                + this.columnF + ", [\n" + str + "])";
    }

}
