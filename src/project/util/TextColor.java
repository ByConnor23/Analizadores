package project.util;

import java.awt.Color;

public class TextColor {
    private final int start;

    private final int size;

    private final Color color;

    public TextColor(int paramInt1, int paramInt2, Color paramColor) {
        this.start = paramInt1;
        this.size = paramInt2;
        this.color = paramColor;
    }

    public int getStart() {
        return this.start;
    }

    public int getSize() {
        return this.size;
    }

    public Color getColor() {
        return this.color;
    }

    public String toString() {
        return "TextColor{start=" + this.start + ", size=" + this.size + ", color=" + this.color + "}";
    }
}
