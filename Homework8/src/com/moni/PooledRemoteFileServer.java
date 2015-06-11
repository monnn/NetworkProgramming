package com.moni;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class PooledRemoteFileServer {
    protected int maxConnections;
    protected int listenPort;
    protected ServerSocket serverSocket;
    public PooledRemoteFileServer(int aListenPort, int maxConnections) {
        listenPort = aListenPort;
        this.maxConnections = maxConnections;
    }

    public void setUpHandlers() {
        for (int i = 0; i < maxConnections; i++) {
            PooledConnectionHandler currentHandler = new PooledConnectionHandler();
            new Thread(currentHandler, "Handler " + i).start();
        }
    }

    public void acceptConnections() {
        try {
            ServerSocket server = new ServerSocket(listenPort, 5);
            Socket incomingConnection = null;
            while (true) {
                incomingConnection = server.accept();
                handleConnection(incomingConnection);
            }
        } catch (BindException e) {
            System.out.println("Unable to bind to port " + listenPort);
        } catch (IOException e) {
            System.out.println("Unable to instantiate a ServerSocket on port: " + listenPort);
        }
    }

    protected void handleConnection(Socket connectionToHandle) {
        PooledConnectionHandler.processRequest(connectionToHandle);
    }

    public static void main(String[] args) {
        PooledRemoteFileServer server = new PooledRemoteFileServer(3000, 3);
        server.setUpHandlers();
        server.acceptConnections();
    }
}
