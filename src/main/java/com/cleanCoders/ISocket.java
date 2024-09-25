package com.cleanCoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISocket {
    boolean isClosed();

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    void close() throws IOException;
}
