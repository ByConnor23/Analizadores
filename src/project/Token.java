package project;

import java.util.UUID;

public class Token {
    private UUID id;
    private TokenType type;
    private String value;
    private int line;
    private int column;
    private Object valorToken;
    private String color;
    /**
   * {@code 'INT', 'CADENA', 'BOOLEANO', 'DOUBLE', 'NUMERO'}
   */
    private String tipoToken;
    private String codigo;
    private String lugar;

    //Constructor
    public Token(TokenType type, String value, int line, int column, UUID id) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
        this.id = id;
        this.valorToken = null;
        this.tipoToken = null;
    }

    public Token(TokenType type, int line, int column, UUID id, String tipoToken, Object valorToken){
        this.type = type;
        this.value = null;
        this.line = line;
        this.column = column;
        this.id = id;
        this.tipoToken = tipoToken;
        this.valorToken = valorToken;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public Object getValorToken() {
        return valorToken;
    }

    public void setValorToken(Object valorToken){
        this.valorToken = valorToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public String getLugar(){
        return lugar;
    }

    public void setLugar(String lugar){
        this.lugar = lugar;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
