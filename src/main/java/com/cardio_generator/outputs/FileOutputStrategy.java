package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Implements a file-based output strategy for storing patient health data.
 * This class ensures that simulated health data is saved to a structured file system.
 * <p>
 * The output data is written into files organized by labels, allowing for easy retrieval 
 * and analysis of patient records. It supports concurrent operations for efficient storage.
 * </p>
 *
 * @author [Your Name]
 */
public class FileOutputStrategy implements OutputStrategy {

    /** Base directory where output files are stored. */
    private String baseDirectory;// Changed variable name to camelCase
    
    /** Maps labels to file paths for efficient data retrieval and storage. */
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();// Changed name to camelCase

    /** Logger for handling error messages and logging operations. */
    private static final Logger logger = Logger.getLogger(FileOutputStrategy.class.getName());

    /**
     * Initializes the file output strategy with a specified base directory.
     *
     * @param baseDirectory The directory where output files will be stored.
     */
    // Removed extra blank line before constructor
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Writes patient health data to a file corresponding to the provided label.
     * If the directory does not exist, it is created automatically.
     *
     * @param patientId The unique identifier of the patient whose data is being recorded.
     * @param timestamp The time at which the data was generated, in milliseconds.
     * @param label The category of health data being stored (e.g., "ECG", "BloodPressure").
     * @param data The actual health data value to be stored.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable // Changed variable name to camelCase for filePath and fileMap
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) { // Catching specific IOException instead of generic Exception
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}