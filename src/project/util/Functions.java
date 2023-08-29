package project.util;
// package compilerTools;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class Functions {
    public static String ANSI_BLACK = "\033[30m";

    public static String ANSI_RED = "\033[31m";

    public static String ANSI_RED_BLACK = "\033[31;2m";

    public static String ANSI_GREEN = "\033[32m";

    public static String ANSI_GREEN_BLACK = "\033[32;2m";

    public static String ANSI_YELLOW = "\033[33m";

    public static String ANSI_YELLOW_BLACK = "\033[33;2m";

    public static String ANSI_BLUE = "\033[34m";

    public static String ANSI_BLUE_BLACK = "\033[34;2m";

    public static String ANSI_PURPLE = "\033[35m";

    public static String ANSI_PURPLE_BLACK = "\033[35;2m";

    public static String ANSI_RESET = "\033[0m";

    public static void clearDataInTable(JTable paramJTable) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) paramJTable.getModel();
        defaultTableModel.setRowCount(0);
    }

    public static void addRowDataInTable(JTable paramJTable, Object[] paramArrayOfObject) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) paramJTable.getModel();
        defaultTableModel.addRow(paramArrayOfObject);
    }

    public static void insertAsteriskInName(final JFrame jFrame, JTextComponent paramJTextComponent,
            final Runnable function) {
        paramJTextComponent.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent param1KeyEvent) {
            }

            public void keyPressed(KeyEvent param1KeyEvent) {
            }

            public void keyReleased(KeyEvent param1KeyEvent) {
                int i = param1KeyEvent.getKeyCode();
                if (i == 10 || (i >= 65 && i <= 90) || (i >= 48 && i <= 57) || (i >= 97 && i <= 122) ||
                        (i != 27 && (i < 37 || i > 40) && (i < 16 || i > 18) && i != 524 && i != 20)) {
                    if (!jFrame.getTitle().contains("*")) {
                        jFrame.setTitle(jFrame.getTitle() + "*");
                    }
                    function.run();
                }
            }
        });
    }

    public static void insertAsteriskInName(JFrame paramJFrame, JTextComponent paramJTextComponent) {
        insertAsteriskInName(paramJFrame, paramJTextComponent, () -> {

        });
    }

    public static String centerWord(String paramString1, String paramString2, String paramString3, int paramInt) {
        int i = paramString1.length();
        if (paramInt <= i)
            return paramString1;
        paramInt -= i;
        if (paramInt % 2 == 0) {
            paramInt /= 2;
            String str3 = String.join("", Collections.nCopies(paramInt, paramString2));
            String str4 = String.join("", Collections.nCopies(paramInt, paramString3));
            return str3 + str3 + paramString1;
        }
        paramInt = (paramInt - 1) / 2;
        String str1 = String.join("", Collections.nCopies(paramInt + 1, paramString2));
        String str2 = String.join("", Collections.nCopies(paramInt, paramString3));
        return str1 + str1 + paramString1;
    }

    public static String formatString(String paramString1, String paramString2, String paramString3) {
        String str = "";
        while (true) {
            int i = paramString1.indexOf("\\" + paramString2);
            if (i != -1) {
                str = str + str + paramString1.substring(0, i).replace(paramString2, paramString3);
                paramString1 = paramString1.substring(i + paramString2.length() + 1, paramString1.length());
                continue;
            }
            break;
        }
        str = str + str;
        return str;
    }

    public static void colorTextPane(ArrayList<TextColor> paramArrayList, JTextPane paramJTextPane, Color paramColor) {
        StyledDocument styledDocument = paramJTextPane.getStyledDocument();
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground,
                paramColor);
        styledDocument.setCharacterAttributes(0, styledDocument.getLength(), attributeSet, true);
        for (TextColor textColor : paramArrayList) {
            attributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground,
                    textColor.getColor());
            styledDocument.setCharacterAttributes(textColor.getStart(), textColor.getSize(), attributeSet, true);
        }
    }

    public static void setLineNumberOnJTextComponent(JTextComponent paramJTextComponent) {
        setLineNumberOnJTextComponent(paramJTextComponent, 3, new Color(196, 39, 39));
    }

    public static void setLineNumberOnJTextComponent(JTextComponent paramJTextComponent, int paramInt,
            Color paramColor) {
        LineNumber lineNumber = new LineNumber(paramJTextComponent, paramInt, paramColor);
        JTextComponent jTextComponent = paramJTextComponent;
        Container container = jTextComponent.getParent();
        while (container != null) {
            if (container instanceof JScrollPane) {
                JScrollPane jScrollPane = (JScrollPane) container;
                jScrollPane.setRowHeaderView(lineNumber);
                return;
            }

            jTextComponent = (JTextComponent) container;
            container = jTextComponent.getParent();
        }
    }

    public static boolean isLetter(String paramString) {
        return paramString.matches("[A-Za-z");
    }

    public static boolean isWord(String paramString) {
        return paramString.matches("[A-Za-z");
    }

    public static boolean isDigit(String paramString) {
        return paramString.matches("[0-9]");
    }

    public static boolean isNumber(String paramString) {
        return paramString.matches("0|[1-9][0-9]*");
    }

    public static boolean isSpace(String paramString) {
        return paramString.matches("[ \t\f]+");
    }

    public static boolean isSpaceOrSaltLine(String paramString) {
        return paramString.matches("[ \t\f\r\n]+");
    }

    public static void sortErrorsByLineAndColumn(ArrayList<ErrorLSSL> paramArrayList) {
        quicksortErrorsByLine(paramArrayList, 0, paramArrayList.size() - 1);
        sortsErrorByColumnGroupedOnLine(paramArrayList);
    }

    private static void quicksortErrorsByLine(ArrayList<ErrorLSSL> paramArrayList, int paramInt1, int paramInt2) {
        ErrorLSSL errorLSSL = paramArrayList.get(paramInt1);
        int i = paramInt1;
        int j = paramInt2;
        while (i < j) {
            while (((ErrorLSSL) paramArrayList.get(i)).getLine() <= errorLSSL.getLine() && i < j) {
                i++;
            }
            while (((ErrorLSSL) paramArrayList.get(j)).getLine() > errorLSSL.getLine()) {
                j--;
            }
            if (i < j) {
                ErrorLSSL errorLSSL1 = paramArrayList.get(i);
                paramArrayList.set(i, paramArrayList.get(j));
                paramArrayList.set(j, errorLSSL1);
            }
        }
        paramArrayList.set(paramInt1, paramArrayList.get(j));
        paramArrayList.set(j, errorLSSL);
        if (paramInt1 < j - 1) {
            quicksortErrorsByLine(paramArrayList, paramInt1, j - 1);
        }
        if (j + 1 < paramInt2) {
            quicksortErrorsByLine(paramArrayList, j + 1, paramInt2);
        }
    }

    private static void quicksortErrorsByColumn(ArrayList<ErrorLSSL> paramArrayList, int paramInt1, int paramInt2) {
        ErrorLSSL errorLSSL = paramArrayList.get(paramInt1);
        int i = paramInt1;
        int j = paramInt2;
        while (i < j) {
            while (((ErrorLSSL) paramArrayList.get(i)).getColumn() <= errorLSSL.getColumn() && i < j) {

                i++;
            }
            while (((ErrorLSSL) paramArrayList.get(j)).getColumn() > errorLSSL.getColumn()) {
                j--;
            }
            if (i < j) {
                ErrorLSSL errorLSSL1 = paramArrayList.get(i);
                paramArrayList.set(i, paramArrayList.get(j));
                paramArrayList.set(j, errorLSSL1);
            }
        }
        paramArrayList.set(paramInt1, paramArrayList.get(j));
        paramArrayList.set(j, errorLSSL);
        if (paramInt1 < j - 1) {
            quicksortErrorsByColumn(paramArrayList, paramInt1, j - 1);
        }
        if (j + 1 < paramInt2) {
            quicksortErrorsByColumn(paramArrayList, j + 1, paramInt2);
        }
    }

    private static void sortsErrorByColumnGroupedOnLine(ArrayList<ErrorLSSL> paramArrayList) {
        int i = paramArrayList.size();

        for (int j = 0; j < i; j++) {
            ErrorLSSL errorLSSL = paramArrayList.get(j);
            int k = j, m = -1;
            for (int n = j + 1; n < i; n++) {
                ErrorLSSL errorLSSL1 = paramArrayList.get(n);
                if (errorLSSL.getLine() == errorLSSL1.getLine()) {
                    m = n;
                } else {
                    if (m != -1) {
                        quicksortErrorsByColumn(paramArrayList, k, m);
                        j = m;
                        m = -1;
                    }
                    break;
                }
            }
            if (m != -1)
                quicksortErrorsByColumn(paramArrayList, k, m);
        }
    }
}
