package com.moni;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
public class Client {

    private String hostname;
    private int port;
    static Socket socketClient;

    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() {
        System.out.println("Attempting to connect to " + hostname + ":" + port);
        try {
            socketClient = new Socket(hostname, port);
            System.out.println("Connection Established");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up." + e.getMessage());
        }
    }

    public void sendMessage() {
        System.out.println("Send message to the server: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.nextLine();
        }

    }

    public synchronized void addClient(Client aClient) {

        ClientHandler.clients.add(aClient);

    }
    
    public void receiveMessage(String message) {
        System.out.println(message);
    }
    public static void main(String arg[]) throws IOException {
        Client client = new Client(Server.DEFAULT_HOSTNAME, Server.DEFAULT_PORT);
        client.addClient(client);

        client.connect();
        client.sendMessage();
        //new Thread(new ClientHandler(socketClient)).start();
    }
}
