package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Defines the contract for patient data generators used in health monitoring simulations.
 * <p>
 * This interface ensures that all implementing classes provide a method to generate 
 * simulated patient data and output it via a specified strategy.
 * </p>
 *
 * @author [Your Name]
 */
public interface PatientDataGenerator {

    /**
     * Generates and outputs simulated health data for a specific patient.
     * <p>
     * The data generation process varies based on the implementation, producing values for 
     * different health metrics such as blood pressure, saturation levels, ECG readings, or alerts.
     * </p>
     *
     * @param patientId The unique identifier of the patient whose data is being generated.
     * @param outputStrategy The output strategy responsible for storing or transmitting the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
