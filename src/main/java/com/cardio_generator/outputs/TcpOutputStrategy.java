package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Implements a TCP-based output strategy for transmitting patient health data.
 * <p>
 * This class creates a TCP server that listens for incoming client connections and streams 
 * real-time health data updates to connected clients. Data is transmitted in a formatted string.
 * </p>
 */
public class TcpOutputStrategy implements OutputStrategy {

    /** Server socket for handling incoming client connections. */
    private ServerSocket serverSocket;
    
    /** Socket representing the connected client. */
    private Socket clientSocket;

    /** Output stream used to send patient data to the connected client. */
    private PrintWriter out;

    /**
     * Initializes a TCP server to stream patient health data.
     * <p>
     * The server listens on the specified port and waits for a client to connect.
     * Once a client connects, the data stream is set up for transmission.
     * </p>
     *
     * @param port The TCP port on which the server listens for incoming connections.
     * @throws IOException If an error occurs while initializing the server socket.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends patient health data to the connected TCP client.
     * <p>
     * The data is formatted as a comma-separated string before transmission. 
     * This ensures that the information is structured for easy parsing by the receiving system.
     * </p>
     *
     * @param patientId The unique identifier of the patient whose data is being transmitted.
     * @param timestamp The time at which the data was generated, in milliseconds.
     * @param label The category of health data being transmitted (e.g., "ECG", "BloodPressure").
     * @param data The actual health data value to be sent.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
