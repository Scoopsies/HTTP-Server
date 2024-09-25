package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Socket implements ISocket {
    java.net.Socket socket;

    public Socket(java.net.Socket socket) {
        this.socket = socket;
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
