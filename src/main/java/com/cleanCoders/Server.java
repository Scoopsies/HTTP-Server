package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class Server {
    private int port = 80;
    private boolean isRunnable = true;
    private ServerSocket serverSocket;
    private Router router = new Router();

    public Server(){}

    public Server(Router router, int port) {
        this.router = router;
        this.port = port;
    }

    public void run() {
        new Thread(this::handleIO).start();
    }

    public void handleIO() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        while (isRunnable) {
            try {
                var clientSocket = this.serverSocket.accept();

                // TODO - test me
                new Thread(() -> {
                    try {
                        InputStream in = clientSocket.getInputStream();
                        OutputStream out = clientSocket.getOutputStream();

                        handleInOut(in, out);
                        clientSocket.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    }
                }).start();

            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }

     void handleInOut(InputStream in, OutputStream out) throws IOException {
        HttpRequest request = new HttpRequest(in);
        byte[] response = router.route(request);
        out.write(response);
        out.flush();
    }

    public void stop() throws IOException {
        isRunnable = false;
        this.serverSocket.close();
    }

    public int getPort() {
        return this.port;
    }

    public ServerSocket getSocket() {
        return this.serverSocket;
    }
}