package ru.nsu.buzyurkin;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SocketTest {
    static Server server = new Server(33333);
    @BeforeAll
    public static void startServer() throws InterruptedException {
        server.start();
        Thread.sleep(5000);
    }

    @AfterAll
    public static void stopServer() throws IOException {
        server.stopServer();
    }

    @Test
    public void testBasic() throws IOException, InterruptedException {
        Client cl1 = new Client("localhost", 33333);
        cl1.start();
        Client cl2 = new Client("localhost", 33333);
        cl2.start();

        Thread.sleep(5000);

        assertTrue(server.anyCompound(List.of(1, 2, 3, 4, 5, 6, 7, 8)));


        cl1.stopConnection();
        cl2.stopConnection();
        Thread.sleep(5000);
    }

    @Test
    public void test5Clients() throws IOException, InterruptedException {
        Client cl1 = new Client("localhost", 33333);
        cl1.start();
        Client cl2 = new Client("localhost", 33333);
        cl2.start();
        Client cl3 = new Client("localhost", 33333);
        cl3.start();
        Thread.sleep(5000);
        Client cl4 = new Client("localhost", 33333);
        cl4.start();
        Client cl5 = new Client("localhost", 33333);
        cl5.start();
        Thread.sleep(5000);

        assertFalse(server.anyCompound(List.of(20319251, 6997901, 6997927, 6997937, 17858849,
                6997967, 6998009, 6998029, 6998039, 20165149, 6998051, 6998053)));
    }

    @Test
    public void testClientsClosed() throws IOException, InterruptedException {
        Client cl3 = new Client("localhost", 33333);
        cl3.start();
        Thread.sleep(3000);
        cl3.stopConnection();
        Thread.sleep(5000);

        Client cl4 = new Client("localhost", 33333);
        cl4.start();
        Client cl5 = new Client("localhost", 33333);
        cl5.start();

        Thread.sleep(3000);

        assertFalse(server.anyCompound(List.of(5, 7, 11, 13, 17, 19, 23, 29, 31, 37)));
    }

}
