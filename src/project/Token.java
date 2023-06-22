package project;

public class Token {
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String tipo;
    private String valor;

    enum Tipos {
        PALABRAS_RESERVADAS("int|double|string|bool|false|true|loopFor|If|else|key|case|breaker|start|add|minus|multi|div|new|back|list|Screen|menu|go|define|color|Image|Sound|setBackground|playSound|stopSound|chBox|Character"),
        IDENTIFICADOR("\"?\'?[a-zA-Z]+[a-zA-Z0-9]*\"?\'?"),
        NUMERO("[0-9]"),
        OPERADOR_BINARIO("\\+|-|\\*|/|%|=|==|!=|<|>|<=|>="),
        SIGNOS_PUNTUCACION(";|\\(|\\)|\\{|\\}|\\[|\\]|,");

        public final String patron;

        Tipos(String s) {
            this.patron = s;
        }
    }
}
