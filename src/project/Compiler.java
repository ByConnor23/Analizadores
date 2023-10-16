package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;
import project.util.ErrorLSSL;
import project.util.FileDirectory;
// import project.util.LineNumber;
import project.util.Functions;
import project.util.LineNumber;
import project.util.Production;

public class Compiler extends JFrame {

	public Compiler() {
		initComponents();
		init();
	}

	private void init() {
		// ==================== This ====================
		// this.setTitle("Code Compiler");
		title = "Code Compiler";
		this.setTitle(title);
		// this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				directory.Exit();
				System.exit(0);
			}

		});
		this.setSize(1660, 880);
		this.setLocationRelativeTo(null);
		directory = new FileDirectory(this, codeEditor, title, ".txt");
		timerKeyRelease = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {
			timerKeyRelease.stop();
		});
		Functions.insertAsteriskInName(this, codeEditor, () -> {
			timerKeyRelease.restart();
		});
	}

	private void initComponents() {

		// ==================== Components ====================
		contentPanel = new JPanel(new MigLayout("fill", "[grow][][]", "[grow]"));
		btnNewFile = new JButton();
		btnSave = new JButton();
		btnSaveAs = new JButton();
		btnUndo = new JButton();
		btnRedo = new JButton();
		btnCut = new JButton();
		btnCopy = new JButton();
		btnPaste = new JButton();
		btnRun = new JButton();

		// ==================== Menu ====================
		toolBar = new JToolBar();
		menu = new JMenuBar();
		fileMenu = new JMenu();
		newMenuItem = new JMenuItem();
		openMenuItem = new JMenuItem();
		saveMenuItem = new JMenuItem();
		saveAsMenuItem = new JMenuItem();
		exitMenuItem = new JMenuItem();

		editMenu = new JMenu();
		undoMenuItem = new JMenuItem();
		redoMenuItem = new JMenuItem();
		cutMenuItem = new JMenuItem();
		copyMenuItem = new JMenuItem();
		pasteMenuItem = new JMenuItem();

		helpMenu = new JMenu();
		aboutMenuItem = new JMenuItem();

		// ==================== Content Panel ====================
		codeEditor = new JTextPane();
		symbolTable = new JTable();
		outputConsole = new JTextArea();

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ==================== Menu Bar ====================
		{
			fileMenu.setText("File");
			fileMenu.setMnemonic('F');

			{// ==================== File Menu ====================
				// ==================== New Menu Item ====================
				newMenuItem = new JMenuItem("New File");
				newMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				newMenuItem.setMnemonic('N');
				newMenuItem.addActionListener(e -> newActionPerformed(e));
				fileMenu.add(newMenuItem);

				// ==================== Open Menu Item ====================
				openMenuItem = new JMenuItem("Open");
				openMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				openMenuItem.setMnemonic('O');
				openMenuItem.addActionListener(e -> openActionPerformed(e));
				fileMenu.add(openMenuItem);

				// ==================== Save Menu Item ====================
				saveMenuItem = new JMenuItem("Save");
				saveMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				saveMenuItem.setMnemonic('S');
				saveMenuItem.addActionListener(e -> saveActionPerformed(e));
				fileMenu.add(saveMenuItem);

				// ==================== Save As Menu Item ====================
				saveAsMenuItem = new JMenuItem("Save As");
				saveAsMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_S,
								Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_DOWN_MASK));
				saveAsMenuItem.setMnemonic('A');
				saveAsMenuItem.addActionListener(e -> saveAsActionPerformed(e));
				fileMenu.add(saveAsMenuItem);

				// ==================== Exit Menu Item ====================
				exitMenuItem = new JMenuItem("Exit");
				exitMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				exitMenuItem.setMnemonic('x');
				exitMenuItem.addActionListener(e -> exitActionPerformed());
				fileMenu.add(exitMenuItem);
			}
			menu.add(fileMenu);
			{ // ==================== Edit Menu ====================
				editMenu = new JMenu("Edit");
				editMenu.setMnemonic('E');

				{
					// ==================== Undo Menu Item ====================
					undoMenuItem = new JMenuItem("Undo");
					undoMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_Z,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					undoMenuItem.setMnemonic('U');
					undoMenuItem.setIcon(new FlatSVGIcon("resources/icons/undo.svg"));
					undoMenuItem.addActionListener(e -> newActionPerformed(e));
					editMenu.add(undoMenuItem);

					// ==================== Redo Menu Item ====================
					redoMenuItem = new JMenuItem("Redo");
					redoMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_Y,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					redoMenuItem.setMnemonic('R');
					redoMenuItem.setIcon(new FlatSVGIcon("resources/icons/redo.svg"));
					redoMenuItem.addActionListener(e -> newActionPerformed(e));
					editMenu.add(redoMenuItem);

					// ==================== Cut Menu Item ====================
					cutMenuItem = new JMenuItem("Cut");
					cutMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_X,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					cutMenuItem.setMnemonic('t');
					cutMenuItem.setIcon(new FlatSVGIcon("resources/icons/cut.svg"));
					cutMenuItem.addActionListener(e -> newActionPerformed(e));
					editMenu.add(cutMenuItem);

					// ==================== Copy Menu Item ====================
					copyMenuItem = new JMenuItem("Copy");
					copyMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_C,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					copyMenuItem.setMnemonic('C');
					copyMenuItem.setIcon(new FlatSVGIcon("resources/icons/copy.svg"));
					copyMenuItem.addActionListener(e -> newActionPerformed(e));
					editMenu.add(copyMenuItem);

					// ==================== Paste Menu Item ====================
					pasteMenuItem = new JMenuItem("Paste");
					pasteMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_V,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					pasteMenuItem.setMnemonic('P');
					pasteMenuItem.setIcon(new FlatSVGIcon("resources/icons/paste.svg"));
					pasteMenuItem.addActionListener(e -> newActionPerformed(e));
					editMenu.add(pasteMenuItem);
				}
			}
			menu.add(editMenu);
			{// ==================== About Menu ====================
				helpMenu = new JMenu("Help");
				helpMenu.setMnemonic('H');

				{
					// ==================== About Menu Item ====================
					aboutMenuItem = new JMenuItem("About");
					aboutMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
					aboutMenuItem.setMnemonic('A');
					aboutMenuItem.addActionListener(e -> newActionPerformed(e));
					helpMenu.add(aboutMenuItem);
				}
			}
			menu.add(helpMenu);

		}
		setJMenuBar(menu);

		// ==================== Tool Bar ====================
		{
			toolBar.setMargin(new Insets(3, 3, 3, 3));

			{// ==================== New File Button ====================
				btnNewFile.setToolTipText("New File");
				btnNewFile.setIcon(new FlatSVGIcon("resources/icons/newFile.svg"));
				btnNewFile.addActionListener(e -> newActionPerformed(e));
				toolBar.add(btnNewFile);
				toolBar.addSeparator();
			}
			{// ==================== Save Button ====================
				btnSave.setToolTipText("Save");
				btnSave.setIcon(new FlatSVGIcon("resources/icons/save.svg"));
				toolBar.add(btnSave);
			}
			{// ==================== Save As Button ====================
				btnSaveAs.setToolTipText("Save As");
				btnSaveAs.setIcon(new FlatSVGIcon("resources/icons/saveAs.svg"));
				toolBar.add(btnSaveAs);
				toolBar.addSeparator();
			}
			{// ==================== Undo Button ====================
				btnUndo.setToolTipText("Undo");
				btnUndo.setIcon(new FlatSVGIcon("resources/icons/undo.svg"));
				toolBar.add(btnUndo);
			}
			{// ==================== Redo Button ====================
				btnRedo.setToolTipText("Redo");
				btnRedo.setIcon(new FlatSVGIcon("resources/icons/redo.svg"));
				toolBar.add(btnRedo);
				toolBar.addSeparator();
			}
			{// ==================== Cut Button ====================
				btnCut.setToolTipText("Cut");
				btnCut.setIcon(new FlatSVGIcon("resources/icons/cut.svg"));
				toolBar.add(btnCut);
			}
			{// ==================== Copy Button ====================
				btnCopy.setToolTipText("Copy");
				btnCopy.setIcon(new FlatSVGIcon("resources/icons/copy.svg"));
				toolBar.add(btnCopy);
			}
			{ // ==================== Paste Button ====================
				btnPaste.setToolTipText("Paste");
				btnPaste.setIcon(new FlatSVGIcon("resources/icons/paste.svg"));
				toolBar.add(btnPaste);
				toolBar.addSeparator();
			}
			{// ==================== Run Button ====================
				btnRun.setToolTipText("Run");
				btnRun.setIcon(new FlatSVGIcon("resources/icons/run.svg"));
				toolBar.add(btnRun);
				// toolBar.addSeparator();
			}
		}
		contentPane.add(toolBar, BorderLayout.NORTH);
		// ==================== Content Panel ====================
		{// ==================== Code Editor ====================
			codeEditor.setBackground(Color.WHITE);
			codeEditor.setMargin(new Insets(10, 10, 10, 10));
			//Si recibe un cambio en el documento, se ejecuta el metodo analyse
			codeEditor.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					analyse();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					analyse();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					analyse();
				}		
			});
			LineNumber line = new LineNumber(codeEditor);
			JScrollPane rootCode = new JScrollPane(codeEditor);
			rootCode.setRowHeaderView(line);
			contentPanel.add(rootCode, "grow");
		}
		{// ==================== Symbol Table ====================
			symbolTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Token Value", "Token Type", "Token Row", "Token Column"
			}) {
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, String.class, String.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false
				};
			
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}
			});
			symbolTable.setAutoCreateRowSorter(true);
			symbolTable.setComponentPopupMenu(null);
			contentPanel.add(new JScrollPane(symbolTable), "w 2501p, grow, wrap");
		}
		{// ==================== Output Console ====================
			outputConsole.setBackground(Color.WHITE);
			outputConsole.setEditable(false);
			contentPanel.add(new JScrollPane(outputConsole), "grow, span 2");
		}
		contentPane.add(contentPanel, BorderLayout.CENTER);

		this.pack();
	}

	private void analyse() {
		outputConsole.setText("");
		try {
			// Análisis Léxico
			String content = codeEditor.getText().trim();
	
			Analyzer analizador = new Analyzer(content);
	
			List<Token> tokens = analizador.getTokens();
	
			Object[][] data = new Object[tokens.size()][4];
	
			for (int i = 0; i < tokens.size(); i++) {
				Token token = tokens.get(i);
				data[i][0] = token.getValue();
				data[i][1] = token.getType();
				data[i][2] = token.getLine();
				data[i][3] = token.getColumn();
			}
	
			symbolTable.setModel(new DefaultTableModel(data, new String[] {
				"Token Value", "Token Type", "Token Row", "Token Column"
			}));
	
			// Análisis Sintáctico
			Syntax syntax = new Syntax(tokens);
			syntax.parse();
			// Comprobar si hubo errores y mostrarlos
			List<String> errores = syntax.getErrors();
			if (!errores.isEmpty()) {
				outputConsole.append("Errores:\n");
				for (String error : errores) {
					outputConsole.append(error + "\n");
				}
			} else {
				outputConsole.append("Análisis sintáctico exitoso.");
			}
	
		} catch (Exception e) {
			outputConsole.append("Error general: " + e.getMessage() + "\n");
		}
	}

	private void newActionPerformed(ActionEvent event) {
		directory.New();
	}

	private void openActionPerformed(ActionEvent event) {
		if (directory.Open()) {
			clearFields();
		}
	}

	private void exitActionPerformed() {
		directory.Exit();
	}

	private void saveActionPerformed(ActionEvent event) {
		directory.Save();
	}

	private void saveAsActionPerformed(ActionEvent event) {
		directory.SaveAs();
	}

	private void clearFields() {
		Functions.clearDataInTable(symbolTable);
	}

	public static void main(String args[]) {

		FlatLightLaf.setup();

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Compiler().setVisible(true);
			}
		});
	}

	// ==================== Variables ====================

	private String title;
	private FileDirectory directory;
	private ArrayList<Token> tokens;
	private ArrayList<ErrorLSSL> errors;

	private Timer timerKeyRelease;
	private ArrayList<Production> productions;
	private HashMap<String, String> identifiers;
	private boolean codeHasBeenCopiled = false;

	private JButton btnNewFile;
	private JButton btnSave;
	private JButton btnSaveAs;
	private JButton btnRun;
	private JButton btnCut;
	private JButton btnCopy;
	private JButton btnPaste;
	private JButton btnUndo;
	private JButton btnRedo;
	// Tests
	private JToolBar toolBar;
	private JMenuBar menu;
	// File Menu
	private JMenu fileMenu;
	private JMenuItem newMenuItem;
	private JMenuItem openMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exitMenuItem;
	// Edit Menu
	private JMenu editMenu;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private JMenuItem cutMenuItem;
	private JMenuItem copyMenuItem;
	private JMenuItem pasteMenuItem;
	// About Menu
	private JMenu helpMenu;
	private JMenuItem aboutMenuItem;

	// Content panel
	private JPanel contentPanel;
	private JTextPane codeEditor;
	private JTextArea outputConsole;
	private JTable symbolTable;

}
