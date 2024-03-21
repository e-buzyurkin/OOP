package ru.nsu.buzyurkin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import ru.nsu.buzyurkin.exceptions.DeadClientException;

/**
 * Represents a server that handles multiple clients concurrently.
 */
public class Server extends Thread {
    private ServerSocket serverSocket;
    private volatile boolean isRunning = true;
    private int port;

    private final List<ClientHandler> clientList = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructs a new Server instance with the specified port.
     *
     * @param port The port on which the server will listen for incoming connections.
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     * Checks if there are any compound numbers in the provided list.
     *
     * @param list The list of integers to check for compound numbers.
     * @return {@code true} if there are compound numbers in the list, {@code false} otherwise.
     */
    public boolean anyCompound(List<Integer> list) {
        AtomicBoolean anyCompounds = new AtomicBoolean(false);

        List<Thread> threads = new ArrayList<>();

        List<List<Integer>> sublists = new ArrayList<>();
        int listSplitSize = list.size() / clientList.size();
        int listRemaining = list.size() % clientList.size();

        for (int i = 0; i < clientList.size(); i++) {
            int start = i * listSplitSize;
            int end = start + listSplitSize;
            if (i == clientList.size() - 1) {
                end += listRemaining;
            }

            sublists.add(list.subList(start, end));
            int finalI = i;
            Thread thread = new Thread(() -> {
                boolean threadResult = false;
                try {
                    threadResult = clientList.get(finalI).checkNumbers(sublists.get(finalI));
                } catch (DeadClientException e) {
                    throw new IllegalStateException();
                }
                anyCompounds.compareAndExchange(false, threadResult);
            });

            threads.add(thread);
        }

        threads.forEach(Thread::start);

        LinkedList<Integer> notCheckedParts = new LinkedList<>();

        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (IllegalStateException | InterruptedException e) {
                try {
                    clientList.get(i).closeConnection();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                notCheckedParts.add(i);
            }
        }

        for (var clIndex : notCheckedParts) {
            clientList.remove(clIndex);
        }

        while (!notCheckedParts.isEmpty()) {
            for (var index : notCheckedParts) {
                boolean check = anyCompound(sublists.get(index));
                anyCompounds.compareAndExchange(false, check);
            }
        }

        return anyCompounds.get();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Iterator<ClientHandler> iterator = clientList.iterator();
                while (iterator.hasNext()) {
                    ClientHandler client = iterator.next();
                    if (!client.ping()) {
                        clientList.remove(client);
                        try {
                            client.in.close();
                            client.out.close();
                            client.clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }).start();

        while (isRunning) {
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                break;
            }
            ClientHandler clientHandler = new ClientHandler(client);
            clientList.add(clientHandler);
            clientHandler.start();
        }
    }

    /**
     * Stops the server and closes the server socket.
     *
     * @throws IOException if an I/O error occurs when closing the server socket.
     */
    public void stopServer() throws IOException {
        isRunning = false;
        serverSocket.close();
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        PrintWriter out = null;
        Scanner in = null;
        ReentrantLock mutex = new ReentrantLock();

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                clientSocket.setSoTimeout(10000);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        /**
         * Sends a ping message to the client to check if it's still alive.
         *
         * @return {@code true} if the client is alive and responds to the ping,
         * {@code false} otherwise.
         */
        public boolean ping() {
            boolean isFree = mutex.tryLock();
            if (isFree) {
                try {
                    try {
                        out.println("ping");
                        out.flush();

                        clientSocket.setSoTimeout(2000);
                        String response = in.nextLine();

                        return response.equals("pong");
                    } catch (NoSuchElementException | SocketException e) {
                        return false;
                    }
                } finally {
                    mutex.unlock();
                }
            }

            return true;
        }

        /**
         * Closes the connection with the client.
         *
         * @throws IOException if an I/O error occurs when closing the connection.
         */
        public void closeConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }

        /**
         * Checks the provided list of numbers for compound numbers.
         *
         * @param integerList The list of integers to check.
         * @return {@code true} if any of the numbers is a compound number, {@code false} otherwise.
         * @throws DeadClientException if the client is no longer responsive.
         */
        public boolean checkNumbers(List<Integer> integerList) throws DeadClientException {
            mutex.lock();
            try {
                for (var x : integerList) {
                    try {
                        out.println(x);
                        out.flush();
                        clientSocket.setSoTimeout(1000);
                        if (in.nextBoolean()) {
                            return true;
                        }
                    } catch (SocketException e) {
                        throw new DeadClientException();
                    }
                }
                return false;
            } finally {
                mutex.unlock();
            }

        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new Scanner(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
