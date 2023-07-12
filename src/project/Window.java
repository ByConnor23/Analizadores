package project;

import project.Utils.Line;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Window extends JFrame {
    //Constructor
    public Window() {
        this.setSize(1024, 900); //Tamaño de la ventana
        this.setTitle("Analizador Lexico"); //Nombre de la ventana
        this.setLocationRelativeTo(null);

        this.setResizable(false);

        initComponent();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponent() {
        //Creación del panel
        panel = new JPanel();//Creando un panel
        panel.setLayout(null);
        panel.setBackground(new Color(230, 236, 241));//Agregando color de fondo

        this.getContentPane().add(panel);//Añadiendo el papel a la ventana

        initLabels();
        initButtons();
        initTextArea();
        createTable();
        colors();
    }

    public void initLabels() {

        //Etiquetas de texto
        titleLabel.setText("VisualFire");
        titleLabel.setBounds(350, 60, 200, 40);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(mono);
        panel.add(titleLabel);

        //Imagen tipo logo
        iconLabel.setBounds(530, 55, 50, 50);
        iconLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        panel.add(iconLabel);

        //Etiqueta tipo texto
        textInput.setText("Codigo Fuente");
        textInput.setHorizontalAlignment(JLabel.CENTER);
        textInput.setBounds(225, 150, 100, 50);
        panel.add(textInput);

        //Etiqueta tipo texto
        textOutput.setText("Tabla de simbolos");
        textOutput.setHorizontalAlignment(JLabel.CENTER);
        textOutput.setBounds(640, 150, 120, 50);
        panel.add(textOutput);
    }

    public void initButtons() {
        //Botones
        load.setText("Cargar Archivo");
        load.setBackground(new Color(210, 19, 18));
        load.setBounds(200, 120, 140, 30);
        load.setHorizontalAlignment(JButton.CENTER);
        load.setFont(mono);
        load.setForeground(Color.WHITE);
        panel.add(load);
        load.addActionListener(e ->
                openFile()
        );

        scan.setText("Generar Tabla de símbolos");
        scan.setBackground(new Color(210, 19, 18));
        scan.setBounds(340, 120, 250, 30);
        scan.setHorizontalAlignment(JButton.CENTER);
        scan.setFont(mono);
        scan.setForeground(Color.WHITE);
        panel.add(scan);
        scan.addActionListener(e -> {
            analyse();
            generateTable();
        });

        analyzeSyntax.setText("Análisis sintáctico");
        analyzeSyntax.setBackground(new Color(210, 19, 18));
        analyzeSyntax.setBounds(590, 120, 190, 30);
        analyzeSyntax.setHorizontalAlignment(JButton.CENTER);
        analyzeSyntax.setFont(mono);
        analyzeSyntax.setForeground(Color.WHITE);
        panel.add(analyzeSyntax);
        analyzeSyntax.addActionListener(e -> analSyntax());
    }

    private void initTextArea() {

        //Area de Texto para entrada de datos
        inputArea.setBounds(10, 190, 480, 450);
        inputArea.setBackground(Color.WHITE);
        inputArea.setMargin(new Insets(10, 10, 10, 10));
        panel.add(inputArea);

        line = new Line(inputArea);
        scrollPane = new JScrollPane(inputArea);
        scrollPane.setBounds(10, 190, 480, 450);
        scrollPane.setRowHeaderView(line);
        panel.add(scrollPane);

        // Area para el Análisis Sintáctico
        parsing.setBounds(10, 650, 990, 200);
        parsing.setBackground(Color.WHITE);
        parsing.setEditable(false);
        parsing.setMargin(new Insets(10, 10, 10, 10));
        panel.add(parsing);
    }

    private void createTable() {

        //Area de Texto para la salida de tokens
        //table.setBounds(510, 190, 480, 450);
        //table.setBackground(new Color(29, 29, 29));
        //table.setForeground(Color.WHITE);
        table.setBackground(Color.WHITE);
        table.setFont(table.getFont().deriveFont(Font.BOLD));
        //table.setMargin(new Insets(10, 10, 10, 10));
        panel.add(table);

        // Crear una barra de desplazamiento vertical
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(510, 190, 480, 450);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Agregar el JScrollPane al panel principal
        panel.add(scrollPane);
    }

    private void generateTable() {
        String inputText = inputArea.getText();

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Token", "Cantidad", "Tipo"});

        Matcher matcher = Pattern.compile("(\".*?\")|([\\w]+)|([^\\w\\s])").matcher(inputText);

        while (matcher.find()) {
            String token = matcher.group();
            if (!token.isEmpty()) {
                if (token.matches("\\d+(\\.\\d+)?")) {
                    if (token.contains(".")) {
                        model.addRow(new Object[]{token, 1, "Número Decimal"});
                    } else {
                        model.addRow(new Object[]{token, 1, "Número"});
                    }
                } else if (token.matches("\".*\"")) {
                    model.addRow(new Object[]{token, 1, "Texto"});
                } else if (token.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    if (isKeyword(token)) {
                        model.addRow(new Object[]{token, 1, "Palabra Reservada"});
                    } else {
                        model.addRow(new Object[]{token, 1, "Identificador"});
                    }
                } else {
                    if (isOperator(token)) {
                        model.addRow(new Object[]{token, 1, "Operador"});
                    } else if (isDelimiter(token)) {
                        model.addRow(new Object[]{token, 1, "Delimitador"});
                    }
                }
            }
        }

        table.setModel(model);
    }


    private boolean isKeyword(String token) {
        // Validar si el token es una palabra reservada
        String[] keywords = {"public", "static", "void", "main", "int", "double", "string", "bool", "false", "true", "if",
                "else", "breaker", "Screen", "Menu", "go", "define", "Color", "Image", "Sound", "setBackground",
                "playSound", "stopSound", "Character"};
        return Arrays.asList(keywords).contains(token);
    }

    private boolean isOperator(String token) {
        // Validar si el token es un operador
        String[] operators = {"/", "*", "+", "-", "=", "^", "<", ">", "||", "&&", ">=", "<=", "+=", "-=", "*=", "/=", "==", "++", "--"};
        return Arrays.asList(operators).contains(token);
    }

    private boolean isDelimiter(String token) {
        // Validar si el token es un delimitador
        String[] delimiters = {";", "{", "}", "(", ")", ":", ","};
        return Arrays.asList(delimiters).contains(token);
    }

    private void openFile() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos txt", "txt");

        search.setCurrentDirectory(new File("src\\project\\Analizadores"));
        search.getSelectedFile();
        search.setFileFilter(filter);
        search.showOpenDialog(search);
        File file = new File(search.getSelectedFile().getAbsolutePath());


        try {
            String read = new String(Files.readAllBytes(file.toPath()));
            inputArea.setText(read);

        } catch (FileNotFoundException notFoundException) {
            System.out.println("FileNotFoundException");
        } catch (IOException exception) {
            System.out.println("IOException");

        }
    }

    //Métodos para encontrar las últimas cadenas
    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\P{L}")) {
                break;
            }
        }
        return index;
    }

    //Método para encontrar las primeras cadenas
    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\P{L}")) {
                break;
            }
            index++;
        }
        return index;
    }

    //Método para resaltar las palabras reservadas
    private void colors() {

        final StyleContext cont = StyleContext.getDefaultStyleContext();

        //Colores
        final AttributeSet attRed = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(231, 76, 60));
        final AttributeSet attGreen = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(67, 160, 71));
        final AttributeSet attBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(41, 128, 185));
        final AttributeSet attBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0, 0, 0));
        final AttributeSet attOrange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(227, 119, 41));
        final AttributeSet attPurple = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(115, 45, 217));

        //Aplicar estilo
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\P{L}")) {
                        if (text.substring(wordL, wordR).matches("(\\P{L})*(if|else|if else|for)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attRed, false);
                        } else if (text.substring(wordL, wordR).matches("(\\P{L})*(int|double|string|bool|false|true|breaker)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attGreen, false);
                        } else if (text.substring(wordL, wordR).matches("(\\P{L})*(public|static|void|main)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attBlue, false);
                        } else if (text.substring(wordL, wordR).matches("(\\P{L})*(Screen|Menu|Sound|Color|Character)")) {
                            setCharacterAttributes(wordL, wordL, attOrange, false);
                        } else if (text.substring(wordL, wordR).matches("(\\P{L})*(define|go|setBackground|playSound|stopSound|)")) {
                            setCharacterAttributes(wordL, wordL, attPurple, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, attBlack, false);
                        }
                        wordL = wordR;

                    }
                    wordR++;
                }
            }


            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
            }
        };

        JTextPane txt = new JTextPane(doc);
        String temp = inputArea.getText();
        inputArea.setStyledDocument(txt.getStyledDocument());
        inputArea.setText(temp);
    }

    private void analyse() {
        table.setToolTipText("");
        parsing.setText("");
        try {
            // Análisis Léxico
            String content = inputArea.getText().trim();

            Analyzer analizador = new Analyzer(content);

            StringBuilder output = new StringBuilder();
            List<String> tokens = analizador.getTokens();

            for (String token : tokens) {
                output.append(token).append("\n");
            }
            //outputArea.setText(output.toString());
            //createTable();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void analSyntax() {
        try {
            String sourceCode = inputArea.getText();

            Analyzer analyzer = new Analyzer(sourceCode);

            List<String> tokens = analyzer.getTokens();
            // Analizar sintácticamente los tokens
            syntax = new Syntax(tokens);
            String result = syntax.parse();

            // Mostrar el resultado en el área de texto
            parsing.setText(result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Parser
    Syntax syntax;
    //Panel
    JPanel panel;
    JLabel titleLabel = new JLabel();
    ImageIcon logo = new ImageIcon("fuego_v2.png");
    JLabel iconLabel = new JLabel();
    JLabel textInput = new JLabel();
    JLabel textOutput = new JLabel();
    JButton load = new JButton();
    JButton scan = new JButton();
    JButton analyzeSyntax = new JButton();
    JTable table = new JTable();
    JTextPane inputArea = new JTextPane();
    JTextArea parsing = new JTextArea();
    JFileChooser search = new JFileChooser();

    // Personalización
    Line line;
    JScrollPane scrollPane;
    //Fuente
    Font mono = new Font("VictorMono", Font.PLAIN, 15);

}