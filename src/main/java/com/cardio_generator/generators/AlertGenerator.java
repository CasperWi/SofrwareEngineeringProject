package com.cardio_generator.generators;

import java.util.Random;
import java.util.logging.Logger;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Simulates patient alert generation for health monitoring systems.
 * <p>
 * This class manages alert states for each patient, determining when an alert should 
 * be triggered or resolved based on predefined probability rates. It ensures realistic 
 * alert fluctuations over time using randomization.
 * </p>
 *
 */
public class AlertGenerator implements PatientDataGenerator {

    /** Random generator used to simulate alert triggering probabilities. */
    // Changed variable name to follow naming conventions for constants
    public static final Random RANDOM_GENERATOR  = new Random();

    /** Array storing the current alert states of each patient (true = active, false = resolved). */
    // Changed variable name to camelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    /** Logger for structured logging of alert-related events. */
    private static final Logger LOGGER = Logger.getLogger(AlertGenerator.class.getName());

    /**
     * Initializes the alert generator for a specified number of patients.
     * <p>
     * Each patient starts without an active alert, and alert states are tracked dynamically.
     * </p>
     *
     * @param patientCount The total number of patients being monitored for alerts.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates a simulated alert status for a patient.
     * <p>
     * Alerts have a probabilistic chance of being triggered or resolved over time. 
     * Resolved alerts have a 90% chance of clearing, while new alerts are determined using 
     * an exponential probability model.
     * </p>
     *
     * @param patientId The unique identifier of the patient whose alert status is being determined.
     * @param outputStrategy The output strategy responsible for storing or transmitting the generated alert data.
     * @throws RuntimeException If an unexpected issue occurs during alert generation.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Added descriptive variable for readability
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double alertProbability  = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < alertProbability ;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        // Changed general Exception to specific RuntimeException
        } catch (RuntimeException e) {
            // Changed System.err.println to Logger for structured logging
            LOGGER.severe("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
