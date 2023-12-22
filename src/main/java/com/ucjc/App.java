package com.ucjc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.logging.Logger;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;
import com.ucjc.compiled.generated.Sym;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Scanner;
import java_cup.runtime.ScannerBuffer;
import java_cup.runtime.Symbol;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        // Obtener la ruta relativa de la carpeta "database"
        String inputFilePath = "C:\\Users\\franc\\Desktop\\Programacion\\Proyecto Logica\\spotify-song-searcher\\src\\main\\resources\\input.txt";

        try {
            Scanner scanner = new Lexer(new FileReader(inputFilePath));
            ScannerBuffer buffer = new ScannerBuffer(scanner);
            Parser p = new Parser(buffer, new ComplexSymbolFactory());
            p.parse();
            System.out.println(p.getTabla().toString());
            System.out.println("Consulta analizada correctamente.");
            System.out.println(buffer.getBuffered().get(0));
        } catch (Exception e) {
            System.err.println("Error al analizar la consulta: " + e.getMessage());
        }

        try {
            // Construir la ruta completa al archivo "input.txt"
            Lexer lexer = new Lexer(new BufferedReader(new FileReader(inputFilePath)));
            Parser parser = new Parser(lexer);
            Symbol symbol = parser.parse();
            System.out.println("Consulta analizada correctamente.");
            System.out.println(symbol.toString());
        } catch (Exception e) {
            System.err.println("Error al analizar la consulta: " + e.getMessage());
        }

    }
}
