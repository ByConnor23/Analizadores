package project.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.Utilities;

public final class LineNumber extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {
    private static final Border OUTER = new MatteBorder(0, 0, 0, 2, Color.BLACK);
    public static final float LEFT = 0.0F;
    public static final float CENTER = 0.5F;
    public static final float RIGHT = 1.0F;
    private static final int HEIGHT = 2146483647;
    private JTextComponent component;
    private boolean updateFont;
    private int borderGap;
    private Color currentLineForeground;
    private float digitAlignment;
    private int minimumDisplayDigits;
    private int lastDigits;
    private int lastHeight;
    private int lastLine;
    private HashMap<String, FontMetrics> fonts;

    public LineNumber(JTextComponent paramJTextComponent) {
        this(paramJTextComponent, 3, Color.GRAY);
    }

    public LineNumber(JTextComponent paramJTextComponent, int paramInt) {
        this(paramJTextComponent, paramInt, Color.GRAY);
    }

    public LineNumber(JTextComponent paramJTextComponent, int paramInt, Color paramColor) {
        this.component = paramJTextComponent;

        setFont(paramJTextComponent.getFont());
        setBorderGap(5);
        setCurrentLineForeground(paramColor);
        setDigitAlignment(1.0F);
        setMinimumDisplayDigits(paramInt);

        paramJTextComponent.getDocument().addDocumentListener(this);
        paramJTextComponent.addCaretListener(this);
        paramJTextComponent.addPropertyChangeListener("font", this);
    }

    public boolean getUpdateFont() {
        return this.updateFont;
    }

    public void setUpdateFont(boolean paramBoolean) {
        this.updateFont = paramBoolean;
    }

    public int getBorderGap() {
        return this.borderGap;
    }

    public void setBorderGap(int paramInt) {
        this.borderGap = paramInt;
        EmptyBorder emptyBorder = new EmptyBorder(0, paramInt, 0, paramInt);
        setBorder(new CompoundBorder(OUTER, emptyBorder));
        this.lastDigits = 0;
        setPreferredWidth();
    }

    public Color getCurrentLineForeground() {
        return (this.currentLineForeground == null) ? getForeground() : this.currentLineForeground;
    }

    public void setCurrentLineForeground(Color paramColor) {
        this.currentLineForeground = paramColor;
    }

    public float getDigitAlignment() {
        return this.digitAlignment;
    }

    public void setDigitAlignment(float paramFloat) {
        this.digitAlignment = (paramFloat > 1.0F) ? 1.0F : ((paramFloat < 0.0F) ? -1.0F : paramFloat);
    }

    public int getMinimumDisplayDigits() {
        return this.minimumDisplayDigits;
    }

    public void setMinimumDisplayDigits(int paramInt) {
        this.minimumDisplayDigits = paramInt;
        setPreferredWidth();
    }

    private void setPreferredWidth() {
        Element element = this.component.getDocument().getDefaultRootElement();
        int i = element.getElementCount();
        int j = Math.max(String.valueOf(i).length(), this.minimumDisplayDigits);

        if (this.lastDigits != j) {
            this.lastDigits = j;
            FontMetrics fontMetrics = getFontMetrics(getFont());
            int k = fontMetrics.charWidth('0') * j;
            Insets insets = getInsets();
            int m = insets.left + insets.right + k;

            Dimension dimension = getPreferredSize();
            dimension.setSize(m, 2146483647);
            setPreferredSize(dimension);
            setSize(dimension);
        }
    }

    public void paintComponent(Graphics paramGraphics) {
        super.paintComponent(paramGraphics);

        FontMetrics fontMetrics = this.component.getFontMetrics(this.component.getFont());
        Insets insets = getInsets();
        int i = (getSize()).width - insets.left - insets.right;

        Rectangle rectangle = paramGraphics.getClipBounds();
        int j = this.component.viewToModel(new java.awt.Point(0, rectangle.y));
        int k = this.component.viewToModel(new java.awt.Point(0, rectangle.y + rectangle.height));

        while (j <= k) {
            try {
                if (isCurrentLine(j)) {
                    paramGraphics.setColor(getCurrentLineForeground());
                } else {
                    paramGraphics.setColor(getForeground());
                }

                String str = getTextLineNumber(j);
                int m = fontMetrics.stringWidth(str);
                int n = getOffsetX(i, m) + insets.left;
                int i1 = getOffsetY(j, fontMetrics);
                paramGraphics.drawString(str, n, i1);
                j = Utilities.getRowEnd(this.component, j) + 1;
            } catch (Exception exception) {
                break;
            }
        }
    }

    private boolean isCurrentLine(int paramInt) {
        int i = this.component.getCaretPosition();
        Element element = this.component.getDocument().getDefaultRootElement();

        return (element.getElementIndex(paramInt) == element.getElementIndex(i));
    }

    protected String getTextLineNumber(int paramInt) {
        Element element1 = this.component.getDocument().getDefaultRootElement();
        int i = element1.getElementIndex(paramInt);
        Element element2 = element1.getElement(i);

        if (element2.getStartOffset() == paramInt) {
            return String.valueOf(i + 1);
        }
        return "";
    }

    private int getOffsetX(int paramInt1, int paramInt2) {
        return (int) ((paramInt1 - paramInt2) * this.digitAlignment);
    }

    private int getOffsetY(int paramInt, FontMetrics paramFontMetrics) throws BadLocationException {
        Rectangle rectangle = this.component.modelToView(paramInt);
        int i = paramFontMetrics.getHeight();
        int j = rectangle.y + rectangle.height;
        int k = 0;

        if (rectangle.height == i) {
            k = paramFontMetrics.getDescent();
        } else {
            if (this.fonts == null) {
                this.fonts = new HashMap<>();
            }

            Element element1 = this.component.getDocument().getDefaultRootElement();
            int m = element1.getElementIndex(paramInt);
            Element element2 = element1.getElement(m);

            for (byte b = 0; b < element2.getElementCount(); b++) {
                Element element = element2.getElement(b);
                AttributeSet attributeSet = element.getAttributes();
                String str1 = (String) attributeSet.getAttribute(StyleConstants.FontFamily);
                Integer integer = (Integer) attributeSet.getAttribute(StyleConstants.FontSize);
                String str2 = str1 + str1;

                FontMetrics fontMetrics = this.fonts.get(str2);

                if (fontMetrics == null) {
                    Font font = new Font(str1, 0, integer.intValue());
                    fontMetrics = this.component.getFontMetrics(font);
                    this.fonts.put(str2, fontMetrics);
                }

                k = Math.max(k, fontMetrics.getDescent());
            }
        }

        return j - k;
    }

    public void caretUpdate(CaretEvent paramCaretEvent) {
        int i = this.component.getCaretPosition();
        Element element = this.component.getDocument().getDefaultRootElement();
        int j = element.getElementIndex(i);

        if (this.lastLine != j) {
            repaint();
            this.lastLine = j;
        }
    }

    public void changedUpdate(DocumentEvent paramDocumentEvent) {
        documentChanged();
    }

    public void insertUpdate(DocumentEvent paramDocumentEvent) {
        documentChanged();
    }

    public void removeUpdate(DocumentEvent paramDocumentEvent) {
        documentChanged();
    }

    private void documentChanged() {
        SwingUtilities.invokeLater(() -> {
            try {
                int i = this.component.getDocument().getLength();

                Rectangle rectangle = this.component.modelToView(i);
                if (rectangle != null && rectangle.y != this.lastHeight) {
                    setPreferredWidth();
                    repaint();
                    this.lastHeight = rectangle.y;
                }
            } catch (BadLocationException badLocationException) {
            }
        });
    }

    public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
        if (paramPropertyChangeEvent.getNewValue() instanceof Font)
            if (this.updateFont) {
                Font font = (Font) paramPropertyChangeEvent.getNewValue();
                setFont(font);
                this.lastDigits = 0;
                setPreferredWidth();
            } else {
                repaint();
            }
    }
}
