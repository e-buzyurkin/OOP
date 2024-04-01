package ru.nsu.buzyurkin;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import ru.nsu.buzyurkin.utility.PrimeUtil;

/**
 * Represents a client that connects to a server to perform operations.
 */
public class Client extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private String host;
    private int port;
    private volatile boolean isRunning = true;

    /**
     * Constructs a new Client instance with the specified host and port.
     *
     * @param host The host address of the server.
     * @param port The port number of the server.
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(host, port);

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (isRunning) {
            if (in.hasNextInt()) {
                int num = in.nextInt();

                out.println(!PrimeUtil.isPrime(num));
                out.flush();
            }

            if (in.hasNextLine()) {
                if (in.nextLine().equals("ping")) {
                    out.println("pong");
                    out.flush();
                }
            }
        }
    }

    /**
     * Stops the connection with the server.
     *
     * @throws IOException if an I/O error occurs when closing the connection.
     */
    public void stopConnection() throws IOException {
        isRunning = false;
        clientSocket.close();
    }

}
