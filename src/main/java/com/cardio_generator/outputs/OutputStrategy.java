package com.cardio_generator.outputs;


/**
 * Defines the contract for output strategies used in patient health data simulation.
 * <p>
 * This interface ensures that all implementing classes provide a method to handle the 
 * output of simulated health data, supporting various formats such as console, file, TCP, and WebSocket.
 * </p>
 *
 * @author [Your Name]
 */
public interface OutputStrategy {

    /**
     * Outputs patient health data through the implemented strategy.
     * <p>
     * The data can be stored, displayed, or transmitted depending on the specific output strategy.
     * Implementing classes define how the data is processed and formatted.
     * </p>
     *
     * @param patientId The unique identifier of the patient whose data is being output.
     * @param timestamp The timestamp at which the data was generated, in milliseconds since epoch.
     * @param label The category of health data being output (e.g., "ECG", "BloodPressure").
     * @param data The actual health data value to be transmitted or stored.
     */
    void output(int patientId, long timestamp, String label, String data);
}
