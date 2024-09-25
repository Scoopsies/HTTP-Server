package com.cleanCoders;

import java.io.IOException;

public interface IServerSocket {
    ISocket accept() throws IOException;
}
