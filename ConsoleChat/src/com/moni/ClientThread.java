package com.moni;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
class ClientThread extends Thread {

    private DataInputStream is;
    String name;
    private PrintStream os;
    private Socket clientSocket;
    public static ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
    static ArrayList<String> names = new ArrayList<String>();

        public ClientThread(Socket clientSocket, ArrayList threads, ArrayList names) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.names = names;
    }

    public void run() {
        ArrayList<ClientThread> threads = this.threads;
        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());
            os.println("Enter your name:");
            name = is.readLine().trim();
            os.println("Hello " + name + "! To leave enter \"bye\" in a new line");
            threads.add(ClientThread.this);
            names.add(name);
            for (ClientThread thread: threads) {
                if (thread != null && thread != this) {
                    thread.os.println(name + " is online :)");
                }
            }
            while (true) {
                String line = is.readLine();
                if (line.equals("bye")) {
                    break;
                }
                if (line.equals("list")) {
                    this.os.println(names.toString());
                } else if (threads != null && names!=null) {
                    String[] nm = line.split(":");
                    if (nm[0].equals("send_all")) {
                        for (ClientThread thread : threads) {
                            if (thread != null && thread != this) {
                                thread.os.println("<" + name + "> " + nm[1]);

                            }
                        }
                        this.os.println("Message successfully sent");
                    }
                    else if (!names.contains(nm[0])) {
                        this.os.println("User" + nm[0] + " doesn't exists!");
                    } else {
                        ClientThread thread2 = threads.get(names.indexOf(nm[0]));
                        thread2.os.println("<" + name + "> " + nm[1]);
                        this.os.println("Message successfully sent");
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (ClientThread thread: threads) {
                if (thread != null && thread != this) {
                    thread.os.println(name + " is leaving :(");
                }
            }
            for (ClientThread thread: threads) {
                if (thread == this) {
                    thread = null;
                }
            }
        try {
            is.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        os.close();
        try {
            clientSocket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
   }
    }
