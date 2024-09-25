package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Server {
    private int port = 80;
    private boolean isRunnable = true;
    private final IServerSocket serverSocket;
    private final Router router;

    public Server(Router router, int port, IServerSocket serverSocket) {
        this.router = router;
        this.port = port;
        this.serverSocket = serverSocket;
    }

    public void run() {
        new Thread(this::handleIO).start();
    }

    public void handleIO() {
        while (isRunnable) {
            try{
                listen(this.serverSocket);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

     void respond(InputStream in, OutputStream out) throws IOException {
        HttpRequest request = new HttpRequest(in);
        byte[] response = router.route(request);
        out.write(response);
        out.flush();
    }

    public int getPort() {
        return this.port;
    }

    public IServerSocket getSocket() {
        return this.serverSocket;
    }

    public void handleClient(ISocket socket) throws IOException {
        respond(socket.getInputStream(), socket.getOutputStream());
        socket.close();
    }

    public void listen(IServerSocket serverSocket) throws IOException {
        ISocket clientSocket = serverSocket.accept();
        new Thread(() -> {
            try {
                handleClient(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}