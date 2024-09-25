package com.cleanCoders;

import java.io.*;

public class FakeSocket implements ISocket {
    byte[] request;
    private final ByteArrayOutputStream outputStream;
    boolean isClosed = false;

    public FakeSocket(byte[] request) {
        this.request = request;
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(request);
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() {
        this.isClosed = true;
    }
}
