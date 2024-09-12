package com.cleanCoders;

import com.cleanCoders.routes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    Server server;

    @BeforeEach
    void setup() {
        Router router = new Router();
        router.addRoute("/hello", new HelloRouteHandler());
        router.addRoute("/ping", new PingRouteHandler());
        router.addRoute("/listing", new ListingRouteHandler("."));
        router.addRoute("/form", new FormRouteHandler());
        router.addRoute("/guess", new GuessRouteHandler());
        server = new Server(router, 80, ".");
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

    @Test
    void handleInOutReturnsHelloRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /hello HTTP/1.1\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.handleInOut(in, out);
        assertTrue(out.toString().contains("<h1>Hello!</h1>"));
    }

    @Test
    void handleInOutReturnsGuessRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /guess HTTP/1.1\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.handleInOut(in, out);
        assertTrue(out.toString().contains("<h1>Guessing Game</h1>"));
    }

    @Test
    void handleInOutReturnsPingRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /ping HTTP/1.1\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.handleInOut(in, out);
        assertTrue(out.toString().contains("<h2>Ping</h2>"));
    }
}