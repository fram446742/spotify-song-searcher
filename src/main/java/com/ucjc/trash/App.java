package com.ucjc.trash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;

import java_cup.runtime.ComplexSymbolFactory;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Lexer lexer = null;
        try {
            // Use the class loader to load the file as a resource
            BufferedReader reader = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream("/input.txt")));

            logger.info("Input File Path: /input.txt");

            // Create a ComplexSymbolFactory for CUP
            ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();

            // Create a JFlex lexer
            lexer = new Lexer(reader);

            // Create a CUP parser
            Parser parser = new Parser(lexer, complexSymbolFactory);

            // Start parsing
            parser.parse();

            // Check for parsing errors
            if (parser.error_count() == 0) {
                logger.info("Parsing completed without errors.");

                // Assuming you have the logic to get the SQL file path
                String sqlFilePath = "/database/Spotify.sql";

                // Call the method to execute the SQL file
                executeSqlFile(sqlFilePath);
            } else {
                logger.severe("Parsing completed with errors. Error count: " + parser.error_count());
            }
        } catch (Exception e) {
            logger.severe("An error occurred: " + e.getMessage());
            // Optionally, log the entire stack trace for debugging purposes
            // logger.log(Level.SEVERE, "An error occurred", e);
        } finally {
            // Close the lexer in the finally block to ensure proper cleanup
            if (lexer != null) {
                try {
                    lexer.yyclose();
                } catch (IOException e) {
                    // Handle the IOException if necessary
                    logger.severe("Error closing lexer: " + e.getMessage());
                }
            }
        }
    }

    private static void executeSqlFile(String sqlFilePath) {
        try {
            // Read the SQL file
            StringBuilder queryBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(App.class.getResourceAsStream(sqlFilePath)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    queryBuilder.append(line).append("\n");
                }
            }

            // Process the SQL query based on your needs (e.g., log it)
            String sqlQuery = queryBuilder.toString();
            logger.info("SQL Query to be executed:\n" + sqlQuery);

            // Note: You can customize this part based on your needs.
            // For example, you might log the SQL query, write it to a file, or perform
            // other operations.

        } catch (IOException e) {
            logger.severe("An error occurred while reading the SQL file: " + e.getMessage());
            // Optionally, log the entire stack trace for debugging purposes
            // logger.log(Level.SEVERE, "An error occurred", e);
        }
    }

}
