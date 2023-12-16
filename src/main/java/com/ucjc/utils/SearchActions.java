package com.ucjc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchActions {

    // Update this constant with the path to your MDF file
    private static final String MDF_FILE_PATH = "..\\database\\Spotify.mdf";

    public static void searchSong(String field, String value) {
        try {
            // Establish a connection
            String jdbcUrl = "jdbc:sqlserver://localhost:1433;databaseName=YourDatabaseName;attachDBFilename=" + MDF_FILE_PATH;
            try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
                // Create a SQL query based on the search field
                String sql = "SELECT * FROM Songs WHERE " + field + " = ?";

                // Prepare the SQL statement
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Set the parameter for the prepared statement
                    statement.setString(1, value);

                    // Execute the query
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Process the result set (customize this part based on your needs)
                        while (resultSet.next()) {
                            System.out.println("Song found:");
                            System.out.println("NUMBER: " + resultSet.getString("NUMBER"));
                            System.out.println("SONG_NAME: " + resultSet.getString("SONG_NAME"));
                            System.out.println("ARTIST: " + resultSet.getString("ARTIST"));
                            System.out.println("ALBUM: " + resultSet.getString("ALBUM"));
                            System.out.println("MILLION_STREAMS: " + resultSet.getString("MILLION_STREAMS"));
                            System.out.println("RELEASE_DATE: " + resultSet.getString("RELEASE_DATE"));
                            System.out.println();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in a real-world scenario
        }
    }
}
