package project;

import java.util.UUID;

public class arbolSintac {
    private UUID id;
    private int line;
    private int column;

    public arbolSintac(int line, int column, UUID id) {
        this.id = id;
        this.line = line;
        this.column = column;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
}
