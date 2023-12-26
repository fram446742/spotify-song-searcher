package com.ucjc.analizers;

import java_cup.runtime.*;
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import com.ucjc.utils.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class prueva {
    public static void main(String[] args) {
        String NUMBER = genericSearch("NUMBER", "234", "=");
        // String SONG_NAME = genericSearch("SONG_NAME", "Blinding Lights", "=");
        // String ARTIST = genericSearch("ARTIST", "Taylor Swift", "=");
        // String ARTIST_COUNT = genericSearch("ARTIST_COUNT", "2", "=");
        // String RELEASED_YEAR = genericSearch("RELEASED_YEAR", "2020", "=");
        // String RELEASED_MONTH = genericSearch("RELEASED_MONTH", "5", "=");
        // String RELEASED_DAY = genericSearch("RELEASED_DAY", "20", "=");
        // String STREAMS = genericSearch("STREAMS", "1000", ">");
        // String BPM = genericSearch("BPM", "100", ">");
        // String KEY = genericSearch("`KEY`", "C#", "=");
        // String MODE = genericSearch("MODE", "Major", "=");
        // String DANCEABILITY = genericSearch("DANCEABILITY", "30", ">");
        // String VALENCE = genericSearch("VALENCE", "30", ">");
        // String ENERGY = genericSearch("ENERGY", "30", ">");
        // String ACOUSTICNESS = genericSearch("ACOUSTICNESS", "30", ">");
        // String INSTRUMENTALNESS = genericSearch("INSTRUMENTALNESS", "30", ">");
        // String LIVENESS = genericSearch("LIVENESS", "30", ">");
        // String SPEECHINESS = genericSearch("SPEECHINESS", "30", ">");

        System.out.println(NUMBER);
    }

    public static List<String> searchInDatabase(String jdbcUrl, String username, String password,
            String tableName, String columnName, String targetValue, String comparisonOperator) {
        List<String> results = new ArrayList<>();

        if (jdbcUrl == null || username == null || password == null || tableName == null || columnName == null
                || comparisonOperator == null) {
            // Add appropriate error handling or logging for invalid input parameters
            return results;
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Build the SQL query dynamically based on the comparison operator
            String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " " + comparisonOperator + " ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Set the parameter value
                statement.setString(1, targetValue);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Construct a string representation of the entire row and add it to the results
                        StringBuilder row = new StringBuilder();
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for (int i = 1; i <= columnCount; i++) {
                            row.append(resultSet.getString(i));
                            if (i < columnCount) {
                                row.append(", ");
                            }
                        }
                        results.add(row.toString());
                    }
                }
            }
        } catch (SQLException e) {
            // Log or handle the exception appropriately
            e.printStackTrace();
        }

        return results;
    }

    public static String genericSearch(String columnName, String target, String comparisonOperator) {
        String jdbcUrl = "jdbc:mysql://sql8.freesqldatabase.com:3306/sql8672470";
        String username = "sql8672470";
        String password = "tjUFjunKny";
        String tableName = "Spotify_2023";

        System.out.println("Target (value): " + target);
        System.out.println("Column name (filter for the database): " + columnName);
        System.out.println("Comparison operator: " + comparisonOperator);

        // Get column names from the database
        List<String> columnNames = getColumnNames(jdbcUrl, username, password, tableName);

        if (!columnNames.isEmpty()) {
            // Build a table with column names and values
            StringBuilder table = new StringBuilder();

            // Add column names as headers
            for (String colName : columnNames) {
                table.append(String.format("%-20s", colName)); // Adjust the width as needed
            }
            table.append(System.lineSeparator());

            // Retrieve values based on the provided search criteria
            List<String> foundValues = searchInDatabase(jdbcUrl, username, password, tableName, columnName, target,
                    comparisonOperator);

                    System.out.println("VALORES OBTENIDOS: " + foundValues);

            // Add values for each row
            for (String value : foundValues) {
                String[] rowValues = value.split(", ");
                for (String rowValue : rowValues) {
                    table.append(String.format("%-20s", rowValue)); // Adjust the width as needed
                }
                table.append(System.lineSeparator());
            }

            // System.out.println("Table:");
            // System.out.println(table.toString());

            return table.toString();
        } else {
            System.out.println("Unable to retrieve column names from the database.");
            return null;
        }
    }

    // Helper method to retrieve column names from the database
    private static List<String> getColumnNames(String jdbcUrl, String username, String password, String tableName) {
        List<String> columnNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet resultSet = metaData.getColumns(null, null, tableName, null)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    columnNames.add(columnName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        return columnNames;
    }
}