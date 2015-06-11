package com.moni;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Server implements Runnable {

    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final int DEFAULT_PORT = 9000;
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    public Server() {
        this.serverPort = DEFAULT_PORT;
    }

    public void run() {
        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(!isStopped()) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            new Thread(new ClientHandler(clientSocket)).start();
        }
        System.out.println("Server Stopped.") ;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            System.out.println("Server started...");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + serverPort, e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start();
    }
}
