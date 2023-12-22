package com.ucjc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
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

        String inputFilePath = getInputFilePath();
        System.out.println("Relative path of input.txt: " + inputFilePath);

        try {
            // First attempt to obtain the parser output with a ScannerBuffer
            Scanner scanner = new Lexer(new FileReader(inputFilePath));
            ScannerBuffer buffer = new ScannerBuffer(scanner);
            Parser p = new Parser(buffer, new ComplexSymbolFactory());
            p.parse();
            System.out.println(p.getTabla().toString());
            System.out.println("Query analyzed successfully.");
            System.out.println(buffer.getBuffered().get(0));
        } catch (Exception e) {
            System.err.println("Error analyzing the query: " + e.getMessage());
        }

        try {
            // Second attempt to obtain the parser output with a Symbol class
            Lexer lexer = new Lexer(new BufferedReader(new FileReader(inputFilePath)));
            Parser parser = new Parser(lexer);
            Symbol symbol = parser.parse();
            System.out.println("Query analyzed successfully.");
            System.out.println(symbol.toString());
        } catch (Exception e) {
            System.err.println("Error analyzing the query: " + e.getMessage());
        }

        // Debugging for symbols
        getSymbols();
    }

    private static String getInputFilePath() {
        // Get the absolute path of the file as a resource
        String absolutePath = new File("src/main/resources/input.txt").getAbsolutePath();

        // Get the current working directory path
        String workingDirectory = System.getProperty("user.dir");

        // Exclude the "target" folder if the absolute path contains it
        if (absolutePath.contains(File.separator + "target" + File.separator)) {
            absolutePath = absolutePath.substring(0, absolutePath.indexOf(File.separator + "target" + File.separator));
        }

        // Calculate the relative path
        String relativePath = absolutePath.substring(workingDirectory.length() + 1);

        return relativePath;
    }

    private static void getSymbols() {
        Class<Sym> symClass = Sym.class;

        // Get all fields of the Sym class
        Field[] fields = symClass.getDeclaredFields();

        for (Field field : fields) {
            try {
                // Print the name and value of each constant
                String name = field.getName();
                int value = field.getInt(null); // The second parameter is null because constants are static
                System.out.println(name + " = " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
