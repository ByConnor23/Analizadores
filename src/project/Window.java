package project;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    //Panel
    JPanel panel;

    //Constructor
    public Window() {
        this.setSize(1000, 700); //Tamaño de la ventana
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
        panel.setBackground(Color.GRAY);//Agregando color de fondo

        this.getContentPane().add(panel);//Añadiendo el papel a la ventana

        initLabels();
        initButtons();
        initTextArea();
    }

    public void initLabels() {
        //Fuente
        Font mono = new Font("VictorMono", Font.BOLD, 30);

        //Etiquetas de texto
        JLabel titleLabel = new JLabel();
        titleLabel.setText("VisualFire");
        titleLabel.setBounds(250, 60, 200, 40);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(mono);
        panel.add(titleLabel);

        //Imagen tipo logo
        ImageIcon logo = new ImageIcon("fuego_v2.png");
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(425, 55, 50, 50);
        iconLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        panel.add(iconLabel);

        //Etiqueta tipo texto
        JLabel textInput = new JLabel();
        textInput.setText("Codigo Fuente");
        textInput.setHorizontalAlignment(JLabel.CENTER);
        textInput.setBounds(225, 150, 100, 50);
        panel.add(textInput);

        //Etiqueta tipo texto
        JLabel textOutput = new JLabel();
        textOutput.setText("Tabla de simbolos");
        textOutput.setHorizontalAlignment(JLabel.CENTER);
        textOutput.setBounds(640, 150, 120,50);
        panel.add(textOutput);
    }

    public void initButtons() {
        //Fuente
        Font mono = new Font("VictorMono", Font.PLAIN, 15);

        //Botones
        JButton load = new JButton();
        load.setText("Cargar Archivo");
        load.setBackground(new Color(210, 19, 18));
        load.setBounds(250, 120, 140, 30);
        load.setHorizontalAlignment(JButton.CENTER);
        load.setFont(mono);
        load.setForeground(Color.WHITE);
        panel.add(load);

        JButton scan = new JButton();
        scan.setText("Analizar");
        scan.setBackground(new Color(210, 19, 18));
        scan.setBounds(390, 120, 100, 30);
        scan.setHorizontalAlignment(JButton.CENTER);
        scan.setFont(mono);
        scan.setForeground(Color.WHITE);
        panel.add(scan);
    }

    public void initTextArea() {
        //Area de Texto para entrada de datos
        JTextArea inputArea = new JTextArea();
        inputArea.setBounds(70, 190, 400, 300);
        inputArea.setBackground(Color.WHITE);
        panel.add(inputArea);

        //Area de Texto para la salida de tokens
        JTextArea outputArea = new JTextArea();
        outputArea.setBounds(520, 190, 400, 300);
        outputArea.setBackground(Color.WHITE);
        outputArea.setEditable(false);
        panel.add(outputArea);
    }
}
