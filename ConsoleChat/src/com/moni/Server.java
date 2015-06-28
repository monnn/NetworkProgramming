package com.moni;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

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

    public static void main(String args[]) {
        System.out.println("Server started at port: " + DEFAULT_PORT);
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                while (true) {
                    clientSocket = serverSocket.accept();
                    ClientThread thread1 = new ClientThread(clientSocket, threads, names);
                    //threadMap.put(thread1, null);
                    long start = System.currentTimeMillis();
                    thread1.start();
                    long end = System.currentTimeMillis();
                    long time = end - start;
                    System.out.println("time:" + time);
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}




