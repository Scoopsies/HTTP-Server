package com.cleanCoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    Server server;

    @BeforeEach
    void setup() {
        server = new Server();
    }

    @Test
    void serverSocketCreatedAtPort80byDefault() {
        assertEquals(80, server.getPort());
    }

    @Test
    void localSocketAddressCreated() throws InterruptedException, IOException {
        server.run();
        Thread.sleep(5);
        assertEquals("0.0.0.0/0.0.0.0:80", server.getSocket().getLocalSocketAddress().toString());
        server.stop();
    }

    @Test
    void runListensForAConnection() throws IOException, InterruptedException {
        server.run();
        Thread.sleep(5);

        var socketAddress = server.getSocket().getLocalSocketAddress();
        try (var socket = new Socket()) {
            assertDoesNotThrow(() -> socket.connect(socketAddress));
            var outputStream = socket.getOutputStream();
            outputStream.write("GET / HTTP/1.1 \r\n".getBytes());
            outputStream.flush();

        } catch (IOException ioe) {
            fail("IOException occurred: " + ioe.getMessage());
        }
        server.stop();
    }

    @Test
    void stopClosesTheConnectionToPort() throws IOException, InterruptedException {
        server.run();
        Thread.sleep(5);
        assertFalse(server.getSocket().isClosed());
        server.stop();
        assertTrue(server.getSocket().isClosed());
    }
}
