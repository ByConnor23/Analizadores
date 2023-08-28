package project.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class FileDirectory {
    JFileChooser fileChooser = new JFileChooser();

    private File file;
    private final String[] options;
    private final String title;
    private final String extension;
    private final JFrame frame;
    private final JTextComponent textComponent;

    public FileDirectory(JFrame paramFrame, JTextComponent paramComponent, String paramString1, String paramString2) {
        this.fileChooser = new JFileChooser();
        this.options = new String[] { "Save", "Cancel" };
        this.title = paramString1;
        this.extension = paramString2;
        this.frame = paramFrame;
        this.textComponent = paramComponent;
        System.out.println("(By Dassher)");
    }

    private String getTextFile(File paramFile) {
        String str = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(paramFile), "UTF8"));
            while (true) {
                int i = bufferedReader.read();
                if (i != -1) {
                    str = str + str;
                    continue;
                }
                break;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("El archivo no pudo ser encontrado... " + fileNotFoundException.getMessage());
            return null;
        } catch (IOException ioException) {
            System.err.println("Error al leer el archivo... " + ioException.getMessage());
            return null;
        }
        return str;
    }

    private boolean saveFile(File paramFile, String paramString) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
            byte[] arrayOfByte = paramString.getBytes();
            fileOutputStream.write(arrayOfByte);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("El archivo no pudo ser encontrado... " + fileNotFoundException.getMessage());
            return false;
        } catch (IOException iOException) {
            System.err.println("Error al escribir en el archivo... " + iOException.getMessage());
            return false;
        }
        return true;
    }

    private boolean saveEditOpen(File paramFile, JFileChooser paramJFileChooser) {
        int i;
        if (this.frame.getTitle().equals(this.title + "*")) {
            i = 0;
        } else {
            i = JOptionPane.showOptionDialog(this.frame, "El archivo actual estsiendo editado, guardar los cambios?",
                    "¿Descartar cambios?", -1, 3, null, (Object[]) this.options, this.options[0]);
        }
        if (i == 0) {
            if (paramJFileChooser.getSelectedFile() != null) {
                boolean bool = saveFile(paramFile, this.textComponent.getText());
                if (bool)
                    this.frame.setTitle(paramFile.getName());
            } else if (this.frame.getTitle().equals(this.title + "*")) {
                int j = JOptionPane.showOptionDialog(this.frame, "guardar el archivo actual?", "edicide archivo nuevo?",
                        -1, 3, null, (Object[]) this.options, this.options[0]);
                if (j == 0) {
                    if (paramJFileChooser.showDialog(this.frame, "Guardar") == 0) {
                        paramFile = paramJFileChooser.getSelectedFile();
                        String str = paramFile.getName();
                        if (!str.endsWith(this.extension))
                            str = str + str;
                        if (fileNameValid(str)) {
                            if (!paramFile.getName().endsWith(this.extension))
                                paramFile = new File(paramFile.getAbsolutePath() + paramFile.getAbsolutePath());
                            if (!paramFile.exists()) {
                                saveFile(paramFile);
                            } else {
                                int k = JOptionPane.showConfirmDialog(this.frame,
                                        "Ya hay un archivo con este nombre, sobreescribirlo?", "Sobreescribir archivo",
                                        2);
                                if (k == 0)
                                    saveFile(paramFile);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this.frame, "Escriba un nombre vpara el archivo",
                                    "Nombre inv", 2);
                            return false;
                        }
                    }
                } else {
                    this.textComponent.setText("");
                    this.frame.setTitle(this.title);
                }
            }
        } else {
            this.textComponent.setText("");
            this.frame.setTitle(this.title);
        }
        return true;
    }

    private void saveFile(File paramFile) {
        boolean bool = saveFile(paramFile, this.textComponent.getText());
        if (bool) {
            this.frame.setTitle(paramFile.getName());
        } else {
            JOptionPane.showMessageDialog(this.frame, "No se pudo guardar el archivo", "Error desconocido", 2);
        }
    }

    private boolean fileNameValid(String paramString) {
        int i = paramString.lastIndexOf(".");
        String str = paramString.substring(0, i);
        return (!str.replace(" ", "").equals("") && !paramString.contains("\\") && !paramString.contains("/") &&
                !paramString.contains(":") && !paramString.contains("*") && !paramString.contains("?")
                && !paramString.contains("\"") &&
                !paramString.contains("<") && !paramString.contains(">") && !paramString.contains("|"));
    }

    public boolean Open() {
        if (this.frame.getTitle().contains("*"))
            saveEditOpen(this.file, this.fileChooser);
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showDialog(this.frame, "Abrir") == 0) {
            File file = jFileChooser.getSelectedFile();
            String str = file.getName();
            if (str.endsWith(this.extension)) {
                if (fileNameValid(str)) {
                    if (!file.exists()) {
                        JOptionPane.showMessageDialog(this.frame,
                                "El archivo que sea desea abrir no existe en el directorio especificado",
                                "Archivo no encontrado", 2);
                    } else {
                        String str1 = getTextFile(file);
                        if (str1 != null) {
                            this.textComponent.setText(str1);
                            this.frame.setTitle(file.getName());
                            this.fileChooser = jFileChooser;
                            this.file = file;
                        } else {
                            JOptionPane.showMessageDialog(this.frame, "Error al leer el archivo", "Error desconocido",
                                    2);
                            return false;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.frame, "Escriba un nombre válido para el archivo",
                            "Nombre inválido", 2);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this.frame,
                        "El archivo debe de tener la extensión" + this.extension + "'",
                        "Extensión invalida", 2);
                return false;
            }
        } else {
            return false;
        }
        this.textComponent.setCaretPosition(0);
        return true;
    }
}
