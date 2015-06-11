package com.moni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class ClientHandler implements Runnable {

    public static Vector clients = new Vector();
    private Socket client;
    private String clientId;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        this.clientId = Thread.currentThread().getName();
        System.out.println(
                "I am thread with name:" + clientId);
        readClientResponse();
    }

    private void readClientResponse() {
        String msg = null;
        try {
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            msg = input.readLine();
        } catch (IOException e) {
            System.out.println("Error reading client response!!!");
        }
        if (msg != null) {
            System.out.print("message received" + msg);
            sendMessageToClient(msg);
        }
    }

    private void sendMessageToClient(String message) {
        int cId = message.charAt(0);
        String msg = message.substring(2);
        Client client = (Client) clients.get(cId);
        client.receiveMessage(msg);

    }

}
