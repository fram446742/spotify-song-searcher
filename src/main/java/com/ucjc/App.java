package com.ucjc;

import java.io.FileReader;
import java.util.logging.Logger;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        // Obtener la ruta relativa de la carpeta "database"
        String inputFilePath = "C:\\Users\\franc\\Desktop\\Programacion\\Proyecto Logica\\spotify-song-searcher\\src\\main\\resources\\input.txt";

        // Construir la ruta completa al archivo "input.txt"

        Lexer lexer = new Lexer(new FileReader(inputFilePath));
        Parser parser = new Parser(lexer);

        try {
            parser.parse();
            System.out.println("Consulta analizada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al analizar la consulta: " + e.getMessage());
        }
    }
}