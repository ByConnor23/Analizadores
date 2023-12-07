package project;

public class ReturnDomain {
    private String codigo;
    private Object valor;
    private Token token;
    
    public ReturnDomain(String codigo, Object valor, Token token) {
        this.codigo = codigo;
        this.valor = valor;
        this.token = token;
    }
    
    public Token getToken() {
        return token;
    }
    public void setToken(Token token) {
        this.token = token;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Object getValor() {
        return valor;
    }
    public void setValor(Object valor) {
        this.valor = valor;
    }
}
