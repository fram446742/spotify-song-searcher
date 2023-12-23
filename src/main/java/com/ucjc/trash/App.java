package com.ucjc.trash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;
import com.ucjc.compiled.generated.Sym;
import com.ucjc.utils.TError;

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

            // Printing the table to see the errors
            LinkedList<TError> table = p.getTable();
            System.out.println("\nErrors encountered\n");
            for (TError error : table) {
                System.out.println("Lexeme: " + error.getLexeme());
                System.out.println("Type: " + error.getType());
                System.out.println("Description: " + error.getDescription());
                System.out.println("Line: " + error.getLine());
                System.out.println("Column: " + error.getColumn());
                System.out.println("------------------------");
            }

            System.out.println("Query analyzed successfully.");

            //Buffered values stored are printed for debugging purposes
            List<Symbol> bufferedValues = buffer.getBuffered();

            System.out.println("Stored values in the buffer:");

            for (Symbol value : bufferedValues) {
                System.out.println(value);
            }

            System.out.println("Result: " + p.result);

        } catch (Exception e) {
            System.err.println("Error analyzing the query: " + e.getMessage());
        }

        // try {
        //     // Second attempt to obtain the parser output with a Symbol class
        //     Lexer lexer = new Lexer(new BufferedReader(new FileReader(inputFilePath)));
        //     Parser parser = new Parser(lexer);
        //     Symbol symbol = parser.parse();
        //     System.out.println("Query analyzed successfully.");
        //     System.out.println(String.valueOf(symbol));
        // } catch (Exception e) {
        //     System.err.println("Error analyzing the query: " + e.getMessage());
        // }

        // Debugging for symbols
        getFields(Sym.class);
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

    private static void getFields(Class<?> targetClass) {
        // Get all fields of the Sym class
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            try {
                // Print the name and value of each constant
                String name = field.getName();
                Object value = field.get(null); // Use get method for arrays
                if (value instanceof String[]) {
                    String[] arrayValue = (String[]) value;
                    System.out.print(name + " = [");
                    for (String element : arrayValue) {
                        System.out.print("\"" + element + "\", ");
                    }
                    System.out.println("]");
                } else {
                    System.out.println(name + " = " + value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
