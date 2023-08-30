package project.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public class FileDirectory {
    JFileChooser fileChooser;
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
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(paramFile), "UTF8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            bufferedReader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println("El archivo no pudo ser encontrado... " + fileNotFoundException.getMessage());
        } catch (IOException iOException) {
            System.err.println("Error al leer el archivo... " + iOException.getMessage());
        }
        return stringBuilder.toString();
    }

    private boolean saveFile(File paramFile, String paramString) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
            byte[] arrayOfByte = paramString.getBytes();
            fileOutputStream.write(arrayOfByte);
            fileOutputStream.close(); // Close the stream
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
            i = JOptionPane.showOptionDialog(this.frame, "El archivo actual está siendo editado, ¿guardar los cambios?",
                    "¿Descartar cambios?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, this.options,
                    this.options[0]);
        }
        if (i == JOptionPane.YES_OPTION) {
            if (paramJFileChooser.getSelectedFile() != null) {
                boolean bool = saveFile(paramFile, this.textComponent.getText());
                if (bool) {
                    this.frame.setTitle(paramFile.getName());
                }
            } else if (this.frame.getTitle().equals(this.title + "*")) {
                int j = JOptionPane.showOptionDialog(this.frame, "Guardar el archivo actual?", "Editar archivo nuevo?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, this.options, this.options[0]);
                if (j == JOptionPane.YES_OPTION) {
                    if (paramJFileChooser.showDialog(this.frame, "Guardar") == JFileChooser.APPROVE_OPTION) {
                        paramFile = paramJFileChooser.getSelectedFile();
                        String str = paramFile.getName();
                        if (!str.endsWith(this.extension)) {
                            str = str + this.extension;
                        }
                        if (fileNameValid(str)) {
                            if (!paramFile.exists()) {
                                saveFile(paramFile);
                            } else {
                                int k = JOptionPane.showConfirmDialog(this.frame,
                                        "Ya hay un archivo con este nombre, ¿sobrescribirlo?", "Sobreescribir archivo",
                                        JOptionPane.YES_NO_OPTION);
                                if (k == JOptionPane.YES_OPTION) {
                                    saveFile(paramFile);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this.frame, "Escriba un nombre válido para el archivo",
                                    "Nombre inválido", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    // this.textComponent.setText("");
                    // this.frame.setTitle(this.title);
                    return false;
                }
            }
        } else {
            // this.textComponent.setText("");
            // this.frame.setTitle(this.title);
            return false;
        }
        return true;
    }

    private boolean saveEditNew(File paramFile, JFileChooser paramJFileChooser) {
        int i;
        if (this.frame.getTitle().equals(this.title + "*")) {
            i = 0;
        } else {
            i = JOptionPane.showOptionDialog(this.frame, "El archivo actual está siendo editado, ¿guardar los cambios?",
                    "¿Descartar edición?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, this.options,
                    this.options[0]);
        }
        if (i == JOptionPane.YES_OPTION) {
            if (paramJFileChooser.getSelectedFile() != null) {
                boolean bool = saveFile(paramFile, this.textComponent.getText());
                if (bool) {
                    this.frame.setTitle(paramFile.getName());
                }
                return true;
            } else if (this.frame.getTitle().equals(this.title + "*")) {
                int j = JOptionPane.showOptionDialog(this.frame, "Guardar el archivo actual?",
                        "¿Descartar edición de archivo nuevo?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, this.options, this.options[0]);
                if (j == JOptionPane.YES_OPTION) {
                    if (paramJFileChooser.showDialog(this.frame, "Guardar") == JFileChooser.APPROVE_OPTION) {
                        paramFile = paramJFileChooser.getSelectedFile();
                        String str = paramFile.getName();
                        if (!str.endsWith(this.extension)) {
                            str = str + this.extension;
                        }
                        if (!paramFile.exists()) {
                            saveFile(paramFile);
                            return true;
                        } else {
                            int k = JOptionPane.showConfirmDialog(this.frame,
                                    "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?",
                                    "Sobreescribir archivo", JOptionPane.YES_NO_OPTION);
                            if (k == JOptionPane.YES_OPTION) {
                                saveFile(paramFile);
                                return true;
                            }
                        }
                    }
                }
            } else {
                int j = JOptionPane.showConfirmDialog(this.frame,
                        "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo",
                        JOptionPane.YES_NO_OPTION);
                if (j == JOptionPane.YES_OPTION) {
                    saveFile(paramFile);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Open() {
        this.fileChooser = new JFileChooser();
        this.file = null;
        if (this.fileChooser.showDialog(this.frame, "Abrir") == JFileChooser.APPROVE_OPTION) {
            File file = this.fileChooser.getSelectedFile();
            String str = file.getName();
            if (str.endsWith(this.extension)) {
                if (fileNameValid(str)) {
                    if (!file.exists()) {
                        JOptionPane.showMessageDialog(this.frame,
                                "El archivo que sea desea abrir no existe en el directorio especificado",
                                "Archivo no encontrado", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String str1 = getTextFile(file);
                        if (str1 != null) {
                            this.textComponent.setText(str1);
                            this.frame.setTitle(file.getName());
                            this.fileChooser = fileChooser;
                            this.file = file;
                        } else {
                            JOptionPane.showMessageDialog(this.frame, "Error al leer el archivo",
                                    "Error desconocido", JOptionPane.ERROR_MESSAGE);
                            return false;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.frame, "Escriba un nombre válido para el archivo",
                            "Nombre inválido", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this.frame,
                        "El archivo debe de tener la extensión" + this.extension + "'",
                        "Extensión invalida", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    private void saveFile(File paramFile) {
        boolean bool = saveFile(paramFile, this.textComponent.getText());
        if (bool) {
            this.frame.setTitle(paramFile.getName());
        } else {
            JOptionPane.showMessageDialog(this.frame, "No se pudo guardar el archivo", "Error desconocido",
                    JOptionPane.ERROR_MESSAGE);
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

    public void New() {
        this.file = this.fileChooser.getSelectedFile();
        if (this.frame.getTitle().contains("*")) {
            if (saveEditNew(this.file, this.fileChooser)) {
                this.frame.setTitle(this.title);
                this.textComponent.setText("");
                this.fileChooser = new JFileChooser();
                this.file = null;
            }
        } else {
            this.frame.setTitle(this.title);
            this.textComponent.setText("");
            this.fileChooser = new JFileChooser();
            this.file = null;
        }
    }

    public void Exit() {
        if (this.frame.getTitle().contains("*")) {
            if (saveEditOpen(this.file, this.fileChooser)) {
                this.frame.setTitle(this.title);
                this.fileChooser = new JFileChooser();
                this.file = null;
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    /*
     * public boolean Open() {
     * if (this.frame.getTitle().contains("*")) {
     * if (saveEditOpen(this.file, this.fileChooser)) {
     * OpFile();
     * return true;
     * }
     * } else {
     * OpFile();
     * return false;
     * }
     * return false;
     * }
     */

    public boolean Save() {
        if (this.file != null) {
            saveFile(this.file);
        } else {
            JFileChooser jFileChooser = new JFileChooser();
            if (jFileChooser.showDialog(this.frame, "Guardar") == 0) {
                File file = jFileChooser.getSelectedFile();
                String str = file.getName();
                if (!str.endsWith(this.extension))
                    str = str + str;
                if (fileNameValid(str)) {
                    if (!file.getName().endsWith(this.extension))
                        file = new File(file.getAbsolutePath() + file.getAbsolutePath());
                    if (!file.exists()) {
                        saveFile(file);
                        this.file = file;
                        this.fileChooser = jFileChooser;
                    } else {
                        int i = JOptionPane.showConfirmDialog(this.frame,
                                "Ya hay un archivo con este nombre, ¿desea sobreescribirlo?", "Sobreescribir archivo",
                                2);
                        if (i == 0) {
                            saveFile(file);
                            this.file = file;
                            this.fileChooser = jFileChooser;
                        } else {
                            this.fileChooser = new JFileChooser();
                            this.file = null;
                            return false;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.frame, "scriba un nombre v\u00E1lido para el archivo",
                            "Nombre inválido", 2);
                    this.fileChooser = new JFileChooser();
                    this.file = null;
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean SaveAs() {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showDialog(this.frame, "Guardar como") == 0) {
            File file = jFileChooser.getSelectedFile();
            String str = file.getName();
            if (!str.endsWith(this.extension))
                str = str + str;
            if (fileNameValid(str)) {
                if (!file.getName().endsWith(this.extension))
                    file = new File(file.getAbsolutePath() + file.getAbsolutePath());
                saveFile(file);
                this.file = file;
                this.fileChooser = jFileChooser;
                return true;
            }
            JOptionPane.showMessageDialog(this.frame, "Escriba un nombre válido para el archivo", "Nombre inválido", 2);
            return false;
        }
        return false;
    }
}
