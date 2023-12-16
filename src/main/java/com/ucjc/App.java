package com.ucjc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.ucjc.compiled.generated.Lexer;
import com.ucjc.compiled.generated.Parser;

import java_cup.runtime.ComplexSymbolFactory;

/**
 * Spotify song search engine
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            // Replace "input.txt" with your actual input file
            Path projectBaseDirectory = Paths.get("").toAbsolutePath();
            String inputFilePath = projectBaseDirectory + "/spotify-song-searcher/src/test/java/com/ucjc/input.txt";
            System.out.println("Project Base Directory: " + projectBaseDirectory);
            System.out.println("Input File Path: " + inputFilePath);

            // Create a ComplexSymbolFactory for CUP
            ComplexSymbolFactory complexSymbolFactory = new ComplexSymbolFactory();

            // Create a JFlex lexer
            Lexer lexer = new Lexer(new FileReader(inputFilePath));

            // Create a CUP parser
            Parser parser = new Parser(lexer, complexSymbolFactory);

            // Start parsing
            parser.parse();

            // Check for parsing errors
            if (parser.error_count() == 0) {
                System.out.println("Parsing completed without errors.");

                // Assuming you have the logic to get the SQL file path
                String sqlFilePath = "..\\database\\Spotify.sql";

                // Call the method to execute the SQL file
                executeSqlFile(sqlFilePath);
            } else {
                System.out.println("Parsing completed with errors. Error count: " + parser.error_count());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlFile(String sqlFilePath) {
        try {
            // Read the SQL file
            StringBuilder queryBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    queryBuilder.append(line).append("\n");
                }
            }

            // Execute the SQL query without establishing a direct connection
            try (Connection connection = DriverManager.getConnection("jdbc:dummy")) {
                try (Statement statement = connection.createStatement()) {
                    String sqlQuery = queryBuilder.toString();
                    boolean hasResults = statement.execute(sqlQuery);

                    if (hasResults) {
                        // Process the result set (customize this part based on your needs)
                        // Note: This assumes the SQL query returns a result set
                        // If the query is an update or insert, use statement.executeUpdate() instead
                    } else {
                        System.out.println("No results returned.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
        }
    }
}
