package com.cleanCoders;

import java.io.IOException;

public class ServerSocket implements IServerSocket {
    java.net.ServerSocket socket;

    public ServerSocket(java.net.ServerSocket socket) {
        this.socket = socket;
    }

    @Override
    public ISocket accept() throws IOException {

        java.net.Socket client = socket.accept();
        return new Socket(client);
    }
}
