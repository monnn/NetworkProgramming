package com.moni;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Server {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    public static final int DEFAULT_PORT = 9001;
    public static ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
    public static ArrayList<String> names = new ArrayList<String>();

    public Server(int port) {
        connect();
        acceptConnections();
    }

    public  void connect() {
        System.out.println("Server started at port: " + DEFAULT_PORT);
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public  void acceptConnections() {
        while (true) {
            try {
                while (true) {
                    clientSocket = serverSocket.accept();
                    ClientThread thread1 = new ClientThread(clientSocket, threads, names);
                    thread1.start();
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String args[]) {
        Server server = new Server(DEFAULT_PORT);
    }
}




