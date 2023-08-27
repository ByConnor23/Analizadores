package project;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import net.miginfocom.swing.MigLayout;

public class Compiler extends JFrame {

	public Compiler() {
		initComponents();
	}

	private void newActionPerformed() {
		System.out.println("New");
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

		editMenu = new JMenu();
		undoMenuItem = new JMenuItem();

		// ==================== Content Panel ====================
		codeEditor = new JTextPane();
		symbolTable = new JTable();
		outputConsole = new JPanel();

		// ==================== This ====================
		this.setTitle("Code Compiler");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(1660, 880);
		this.setLocationRelativeTo(null);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ==================== Menu Bar ====================
		{// ==================== File Menu ====================
			fileMenu.setText("File");
			fileMenu.setMnemonic('F');

			{// ==================== New Menu Item ====================
				newMenuItem = new JMenuItem("New");
				newMenuItem.setAccelerator(
						KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
				newMenuItem.setMnemonic('N');
				newMenuItem.addActionListener(e -> newActionPerformed());
				fileMenu.add(newMenuItem);
			}
			menu.add(fileMenu);
			{ // ==================== Edit Menu ====================
				editMenu = new JMenu("Edit");
				editMenu.setMnemonic('E');

				{ // ==================== Undo Menu Item ====================

					undoMenuItem = new JMenuItem("Undo");
					undoMenuItem.setAccelerator(
							KeyStroke.getKeyStroke(KeyEvent.VK_Z,
									Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
					undoMenuItem.setMnemonic('U');
					undoMenuItem.setIcon(new FlatSVGIcon("resources/icons/undo.svg"));
					undoMenuItem.addActionListener(e -> newActionPerformed());
					editMenu.add(undoMenuItem);
				}
				menu.add(editMenu);
			}
		}
		setJMenuBar(menu);

		// ==================== Tool Bar ====================
		{
			toolBar.setMargin(new Insets(3, 3, 3, 3));

			{// ==================== New File Button ====================
				btnNewFile.setToolTipText("New File");
				btnNewFile.setIcon(new FlatSVGIcon("resources/icons/newFile.svg"));
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
				toolBar.addSeparator();
			}
		}
		contentPane.add(toolBar, BorderLayout.NORTH);
		// ==================== Content Panel ====================
		{// ==================== Code Editor ====================
			// codeEditor.setBounds(10, 190, 480, 450);
			codeEditor.setBackground(Color.WHITE);
			codeEditor.setMargin(new Insets(10, 10, 10, 10));
			// line = new Line(codeEditor);
			contentPanel.add(new JScrollPane(codeEditor), "grow");
			// jScrollPane1 = new JScrollPane(codeEditor);
			// jScrollPane1.setBounds(10, 190, 480, 450);
			// jScrollPane1.setRowHeaderView(line);
			// contentPane.add(jScrollPane1);
		}
		{// ==================== Symbol Table ====================
			symbolTable.setModel(new DefaultTableModel(
					new Object[][] {
							{ "item 1", "item 1b", "January", "July", 123, null },
							{ "item 2", "item 2b", "February", "August", 456, true },
							{ "item 3", null, "March", null, null, null },
							{ "item 4", null, "April", null, null, null },
							{ "item 5", null, "May", null, null, null },
							{ "item 6", null, "June", null, null, null },
							{ "item 7", null, "July", null, null, null },
							{ "item 8", null, "August", null, null, null },
							{ "item 9", null, "September", null, null, null },
							{ "item 10", null, "October", null, null, null },
							{ "item 11", null, "November", null, null, null },
							{ "item 12", null, "December", null, null, null },
					},
					new String[] {
							"Not editable", "Text", "Combo", "Combo Editable", "Integer", "Boolean"
					}) {
				Class<?>[] columnTypes = new Class<?>[] {
						Object.class, Object.class, String.class, String.class, Integer.class, Boolean.class
				};
				boolean[] columnEditable = new boolean[] {
						false, true, true, true, true, true
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
			// jScrollPane2.setViewportView(symbolTable);
			contentPanel.add(new JScrollPane(symbolTable), "w 2501p, grow, wrap");
		}
		{// ==================== Output Console ====================
			outputConsole.setBackground(Color.WHITE);
			contentPanel.add(new JScrollPane(outputConsole), "grow, span 2");
		}
		contentPane.add(contentPanel, BorderLayout.CENTER);
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
	private JMenu optionsMenu;
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
	private JMenuItem deleteMenuItem;
	// About Menu
	private JMenu helpMenu;
	private JMenuItem aboutMenuItem;

	// Content panel
	private JPanel contentPanel;
	private JTextPane codeEditor;
	private JPanel outputConsole;
	private JTable symbolTable;

}
