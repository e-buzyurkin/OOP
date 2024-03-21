package ru.nsu.buzyurkin;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import ru.nsu.buzyurkin.utility.PrimeUtil;

public class Client extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private String host;
    private int port;
    private volatile boolean isRunning = true;

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
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        while(isRunning) {
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

    public void stopConnection() throws IOException {
        isRunning = false;
        clientSocket.close();
    }
    
}
