package project.generate;

import java.io.FileWriter;
import java.io.IOException;

class translator {
    String html;
    String index = "let ventanaActual = 1\n" +
                    "function mostrarVentana(numeroVentana){\n" +
                    "document.querySelectorAll('.ventana').forEach(function(ventana) {\n" +
                    "ventana.style.display = 'none';})\n"+
                    "document.getElementById('ventana_' + numeroVentana).style.display = 'block';\n" +
                    "}\n" +
                    "function siguienteVentana(){\n" +
                    "ventanaActual++;\n" +
                    "if(ventanaActual > 2){\n" +
                    "ventanaActual = 1;\n}" +
                    "mostrarVentana(ventanaActual);\n" +
                    "}\n" +
                    "mostrarVentana(ventanaActual);\n";
    // public static void main(String[] args) {
    //     generarHtml html = new generarHtml();
    //     html.start();
    //     html.generateHtml();
    //     html.generateJs();
    // }

    public void generateTitle(String title){
        html = html.replace("<title>Index</title>", "<title>"+title+"</title>");
    }

    public void start(){ //el programa se basa en contenedores div llamados ventanas
        html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "<title>Index</title>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\" />" +
                "</head>\n" +
                "<body>\n";
                // "<h1>Prueba</h1>\n" +
                // "<p>Prueba de generacion de un HTML 3</p>\n" +
                // "<script src=\"Hello_world.js\"></script>" +
                // "</body>\n" +
                // "</html>";

        //un ciclo que genera cada ventana
        //dentro de la generacion de cada ventana se genere otro ciclo todo el contenido de la ventana

        html += "<div class=\"ventana\" id=\"ventana_1\">";
        background("img.png");
        generateText("Hola mundo");
        html += "<button class=\"btn_pass\" onclick=\"siguienteVentana()\">Siguiente</button>";
        html += "</div>\n";
        html += "<div class=\"ventana\" id=\"ventana_2\">";
        background("img2.png");
        String options[] = {"Opcion 1", "Opcion 2", "Opcion 3"};
        generateMenu(options);
        html += "<button class=\"btn_pass\" onclick=\"siguienteVentana()\">Siguiente</button>";
        html += "</div>\n";



        html += "<script src=\"index.js\"></script>" +
                "</body>\n" +
                "</html>";

        generateHtml();
        generateJs();
    }

    public void generateMenu(String[] options){
        html += "<div class=\"menu-container\" >\n<ul>\n";
        for (int i = 0; i < options.length; i++) {
            html += "<li><a href=\"#\">"+options[i]+"</a></li>\n";
        }
        html += "</ul>\n</div>\n";
    }

    private void generateHtml(){
        try {
            FileWriter fichero = new FileWriter("prueba.html");
            fichero.write(html);
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateJs(){
        try {
            FileWriter fichero = new FileWriter("index.js");
            fichero.write(index);
            fichero.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateText(String text){
        html += "<div class=\"text-container\">\n" +
        "<p class=\"text-container__text\">"+text+"</p>\n" +
        "</div>\n";
    }

    public void background(String dir){
        html += "<div>\n" +
        "<img src=\""+dir+"\" alt=\"#\">\n" +
        "</div>";
    }

    public void generateSound(String dir){
        html += "<div class=\"sound-container\">\n" +
        "<audio controls>\n" +
        "<source src=\"" + dir + "\" type=\"audio/ogg\">\n" +
        "</audio>\n" +
        "</div>\n";
    }

    public String getHtml(){
        return html;
    }
}