package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates simulated blood saturation level data for patients.
 * <p>
 * This class models realistic fluctuations in blood saturation percentages using a 
 * pseudo-random number generator. Each patient's saturation level varies slightly over time, 
 * staying within a predefined safe range.
 * </p>
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    /** Random generator used to simulate slight variations in blood saturation levels. */
    private static final Random random = new Random();

    /** Stores the last recorded saturation values for each patient to ensure continuity. */
    private int[] lastSaturationValues;

    /**
     * Initializes the blood saturation data generator for a given number of patients.
     * <p>
     * Each patient starts with a randomly assigned baseline saturation level 
     * between 95% and 100%.
     * </p>
     *
     * @param patientCount The total number of patients being monitored.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates a simulated blood saturation level for a patient.
     * <p>
     * The method applies small random fluctuations to the previous recorded value, 
     * ensuring realistic variability while keeping saturation levels within safe boundaries.
     * </p>
     *
     * @param patientId The unique identifier of the patient whose saturation level is being generated.
     * @param outputStrategy The output strategy that handles storing or transmitting the generated data.
     * @throws RuntimeException If an unexpected issue occurs during data generation.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;

            // Output the generated saturation value
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
