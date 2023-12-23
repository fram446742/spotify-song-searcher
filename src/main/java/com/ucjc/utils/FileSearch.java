package com.ucjc.utils;

import java.io.File;

public class FileSearch {

    public static String findFilePathInProject(String fileName) {
        // Get the project directory
        String projectDir = System.getProperty("user.dir");

        // Create a File object for the project directory
        File projectFolder = new File(projectDir);

        // Call the recursive search method
        File foundFile = searchFile(projectFolder, fileName);

        // Return the absolute path of the found file (or null if not found)
        return foundFile != null ? foundFile.getAbsolutePath() : null;
    }

    private static File searchFile(File directory, String fileName) {
        // Get the list of files and directories in the current directory
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().equals("target")) {
                    // If it's a directory (excluding "target"), recursively search inside
                    File foundFile = searchFile(file, fileName);
                    if (foundFile != null) {
                        return foundFile; // File found in a subdirectory
                    }
                } else if (file.isFile() && file.getName().equals(fileName)) {
                    return file; // File found in the current directory
                }
            }
        }

        return null; // File not found in the current directory or its subdirectories
    }
}
