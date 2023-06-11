package project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.Token.Tipos;

public class analizador_lexico {
    public analizador_lexico(String codigo) {
        start(codigo);
    }

    public static ArrayList<Token> lex(String input) {
        final ArrayList<Token> tokens = new ArrayList<Token>();
        final StringTokenizer st = new StringTokenizer(input);
        juntador(st);
        while (st.hasMoreTokens()) {
            String palabra = st.nextToken();
            boolean matched = false;
            System.out.println(palabra);
            for (Tipos tokenTipo : Tipos.values()) {
                Pattern patron = Pattern.compile(tokenTipo.patron);
                Matcher matcher = patron.matcher(palabra);
                if (matcher.find()) {
                    Token tk = new Token();
                    tk.setTipo(tokenTipo.toString());
                    tk.setValor(palabra);
                    tokens.add(tk);
                    matched = true;

                }
            }

            if (!matched) {
                throw new RuntimeException("Se encontró un token invalido.");
            }
        }
        return tokens;
    }

    // metodo para crear el archivo
    public static void crea(ArrayList lex, ArrayList tipo) {
        try {
            FileWriter writer = new FileWriter("tabla_simbolos.txt", true);
            writer.write("---------------------------------------------\n");
            writer.write("|---------lexema-------|--------Tipo--------|\n");
            for (int i = 0; i < lex.size(); i++) {

                writer.write("| " + lex.get(i) + " | " + tipo.get(i) + " | \n");
            }
            writer.write("---------------------------------------------");
            writer.close();
            System.out.println("Archivo creado y contenido añadido correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // metodo para eliminar comentarios
    public static String eliminarComentario(String input) {
        // elimino comentarios
        input = input.replaceAll("##.*?##", "");
        input = input.replaceAll("/#.*(?<!\\n)", "");
        return input;
    }

    public static void start(String codifoFuente) {
        // elimino comentarios
        codifoFuente = eliminarComentario(codifoFuente);

        // separo por lineas y por ;
        String[] newCadena = codifoFuente.split("[\n;]");
        ArrayList<Token> tokens = new ArrayList<Token>();

        // aquellos elementos que tiene espacios
        String[] lexemas = Arrays.stream(newCadena).filter(s -> s.trim().length() > 0).toArray(String[]::new);
        // ciclo para validar y añadir
        for (int i = 0; i < lexemas.length; i++) {
            lexemas[i] = lexemas[i].trim();
            tokens.addAll(lex(lexemas[i]));////////////////////no esta guardando los tokens//////////////////////////////////////
        }
        int fila = tokens.size();
        String[][] tabla = new String[fila][2];
        fila = 0;
        // System.out.println("---------------------------------------------");
        // System.out.println("|---------lexema-------|--------Tipo--------|");
        for (Token token : tokens) {
            tabla[fila][0] = token.getTipo();
            tabla[fila][1] = token.getValor();
            // System.out.println("|" + tabla[fila][1] + " | " + tabla[fila][0] + "|");
            fila++;
        }
        ArrayList<String> lex = new ArrayList<>();
        ArrayList<String> tipo = new ArrayList<>();
        int i = 0;
        while (i < tabla.length) {
            String str = tabla[i][1];
            if (str.startsWith("\"")) {
                while (!str.endsWith("\"")) {
                    i++;
                    str += " " + tabla[i][1];
                }
            }
            lex.add(str);
            tipo.add(tabla[i][0]);
            i++;
        }
        //creo mi archivo con la tabla de simbolos
        //crea(lex, tipo);
    }

    public static void juntador(StringTokenizer listaTokens) {
        ArrayList<String> tokensEntreComillas = new ArrayList<String>();
        // for (StringTokenizer tokenizer : listaTokens) {
        while (listaTokens.hasMoreTokens()) {
            String token = listaTokens.nextToken();
            if (token.startsWith("\"")) {
                StringBuilder sb = new StringBuilder();
                sb.append(token);
                while (listaTokens.hasMoreTokens()) {
                    token = listaTokens.nextToken();
                    sb.append(" ").append(token);
                    if (token.endsWith("\"")) {
                        break;
                    }
                }
                tokensEntreComillas.add(sb.toString());
            }else{
                tokensEntreComillas.add(token);
            }
        }
        // }
        //System.out.println("texto procesado comillas");
        //for(int i = 0; i < tokensEntreComillas.size(); i++){
        //    System.out.println(tokensEntreComillas.get(i));
        //}

    }
    
}
