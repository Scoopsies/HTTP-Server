package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class Server {
    private int port = 80;
    private boolean isRunnable = true;
    private String root = ".";
    private ServerSocket serverSocket;
    private Router router = new Router();

    public Server(){}

    public Server(Router router, int port, String root) {
        this.router = router;
        this.port = port;
        this.root = root;
    }

    public void run() {
        Printables.printStartupConfig(root, port);
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

                new Thread(() -> {
                    try {
                        InputStream in = clientSocket.getInputStream();
                        OutputStream out = clientSocket.getOutputStream();
                        HttpRequest request = new HttpRequest(in);
                        byte[] response = router.route(request);
                        out.write(response);
                        out.flush();
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

    public void stop() throws IOException {
        isRunnable = false;
        this.serverSocket.close();
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRoot() {
        return this.root;
    }

    public void setRoot(String root) {
        this.root = root;
        this.router.setDefaultRoot(root);
    }

    public ServerSocket getSocket() {
        return this.serverSocket;
    }

    public boolean getIsRunnable() {
        return this.isRunnable;
    }

    public void setIsRunnable(boolean bool) {
        this.isRunnable = bool;
    }
}