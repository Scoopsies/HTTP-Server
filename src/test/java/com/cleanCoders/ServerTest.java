package com.cleanCoders;

import com.cleanCoders.routes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {
    Server server;
    Router router;
    byte[] req;
    ISocket clientSocket;
    OutputStream os;
    InputStream is;
    IServerSocket serverSocket;

    @BeforeEach
    void setup() throws IOException {
        router = new Router();
        router.addRoute("/hello", new HelloRouteHandler());
        router.addRoute("/ping", new PingRouteHandler());
        router.addRoute("/listing", new ListingRouteHandler("."));
        router.addRoute("/form", new FormRouteHandler());
        router.addRoute("/guess", new GuessRouteHandler());
        req = "GET /ping HTTP/1.1\r\n\r\n".getBytes();
        clientSocket = new FakeSocket(req);
        os = clientSocket.getOutputStream();
        is = clientSocket.getInputStream();

        ISocket[] clientSockets = {clientSocket};
        serverSocket = new FakeServerSocket(clientSockets);
        server = new Server(router, 80, serverSocket);
    }

    @Test
    void respondReturnsHelloRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /hello HTTP/1.1\r\n\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.respond(in, out);
        assertTrue(out.toString().contains("<h1>Hello!</h1>"));
    }

    @Test
    void respondReturnsGuessRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /guess HTTP/1.1\r\n\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.respond(in, out);
        assertTrue(out.toString().contains("<h1>Guessing Game</h1>"));
    }

    @Test
    void respondReturnsPingRequest() throws IOException {
        InputStream in = new ByteArrayInputStream("GET /ping HTTP/1.1\r\n\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        server.respond(in, out);
        assertTrue(out.toString().contains("<h2>Ping</h2>"));
    }

    @Test
    void respondsToClientRequest() throws IOException {
        assertFalse(clientSocket.isClosed());
        server.handleClient(clientSocket);
        String response = os.toString();

        assertTrue(clientSocket.isClosed());
        assertTrue(response.contains("<h2>Ping</h2>"));
    }

    @Test
    void acceptsClientRequests() throws InterruptedException {
        server.run();

        while (this.os.toString().isEmpty())
            Thread.sleep(1);

        String response = this.os.toString();

        assertTrue(this.clientSocket.isClosed());
        assertTrue(response.contains("<h2>Ping</h2>"));
    }

    @Test
    void acceptsMultipleClientRequests() throws IOException, InterruptedException {
        byte[] req2 = "GET /hello HTTP/1.1\r\n\r\n".getBytes();
        ISocket clientSocket2 = new FakeSocket(req2);
        OutputStream os2 = clientSocket2.getOutputStream();
        ISocket[] clientSockets = {clientSocket, clientSocket2};
        IServerSocket serverSocket = new FakeServerSocket(clientSockets);
        Server server = new Server(router, 80, serverSocket);

        server.run();

        while (os.toString().isEmpty())
            Thread.sleep(1);

        String response = os.toString();

        assertTrue(clientSocket.isClosed());
        assertTrue(response.contains("<h2>Ping</h2>"));

        while (os2.toString().isEmpty())
            Thread.sleep(1);

        response = os2.toString();

        assertTrue(clientSocket2.isClosed());
        assertTrue(response.contains("<h1>Hello!</h1>"));
    }
}