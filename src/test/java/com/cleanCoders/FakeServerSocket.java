package com.cleanCoders;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class FakeServerSocket implements IServerSocket{
    SocketAddress address;
    ISocket[] sockets;
    int requestAmt = 0;

    public FakeServerSocket(ISocket[] clientSockets) {
        this.address = new InetSocketAddress(0);
        this.sockets = clientSockets;
    }

    @Override
    public ISocket accept() {
        if (requestAmt < sockets.length)
            requestAmt++;

        return sockets[requestAmt - 1];
    }
}
