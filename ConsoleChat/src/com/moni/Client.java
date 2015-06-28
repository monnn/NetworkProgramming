package com.moni;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Client implements Runnable {

    private static Socket clientSocket;
    private static PrintStream os;
    private static DataInputStream is;
    private static BufferedReader inputLine;
    private static boolean closed;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("Connected to server: " + HOST + " ," + Server.DEFAULT_PORT);
        try {
            clientSocket = new Socket(HOST, Server.DEFAULT_PORT);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());
            is = new DataInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host " + HOST);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }

        if (clientSocket != null && os != null && is != null) {
            try {
                new Thread(new Client()).start();
                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                os.close();
                is.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
