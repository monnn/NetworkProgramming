package com.moni;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Monica Shopova
 * monika.shopova@gmail.com
 */
class ClientHandler extends Thread {

    private DataInputStream is;
    String name;
    private PrintStream os;
    private Socket clientSocket;
    public static ArrayList<ClientHandler> threads = new ArrayList<ClientHandler>();
    static ArrayList<String> names = new ArrayList<String>();
    public static InetAddress DEFAULT_IP;


        public ClientHandler(Socket clientSocket, ArrayList threads, ArrayList names) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.names = names;
    }

    public void run() {
        try {
            DEFAULT_IP = InetAddress.getByName("127.0.0.1");
            connect();
            notifyForEntering();
            processMessages();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyForLeaving();
        try {
            is.close();
            os.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    boolean nameExists(String name) {
        if (names.contains(name)) {
            return true;
        }
        return false;
    }

    public void connect() throws IOException {
        is = new DataInputStream(clientSocket.getInputStream());
        os = new PrintStream(clientSocket.getOutputStream());
        os.println("Enter your name:");
        name = is.readLine().trim();
        while (nameExists(name) == true)  {
            os.println("User with name " + name + " already exists! Enter another name:");
            name = is.readLine().trim();
        }
        os.println("Hello " + name + "! To leave enter \"bye\" in a new line");
        threads.add(ClientHandler.this);
        names.add(name);
    }

    public void notifyForEntering() {
        for (ClientHandler thread: threads) {
            if (thread != null && thread != this) {
                thread.os.println(name + " is online :)");
            }
        }
    }

    public void notifyForLeaving() {
        for (ClientHandler thread: threads) {
            if (thread != null && thread != this) {
                thread.os.println(name + " is leaving :(");
            }
        }

        for (ClientHandler thread: threads) {
            if (thread == this) {
                thread = null;
            }
        }
    }

    public void processMessages() throws Exception {
        while (true) {
            String line = is.readLine();
            if (line.equals("bye")) {
                break;
            }
            if (line.equals("list")) {
                this.os.println(names.toString());
            } else if (line.startsWith("file")) {
                String[] sendFileTo = line.split(":");
                File file = new File(sendFileTo[1]);
                ClientHandler thread1 = threads.get(names.indexOf(sendFileTo[2]));
                this.sendFile(os, file);
                thread1.receiveFile(is, file);
            } else if (threads != null && names != null) {
                String[] nm = line.split(":");
                if (nm[0].equals("send_all")) {
                    for (ClientHandler thread : threads) {
                        if (thread != null && thread != this) {
                            thread.os.println("<" + name + "> " + nm[1]);
                        }
                    }
                    this.os.println("Message successfully sent");
                }
                else if (!names.contains(nm[0])) {
                    this.os.println("User " + nm[0] + " doesn't exists!");
                } else {
                    ClientHandler thread2 = threads.get(names.indexOf(nm[0]));
                    thread2.os.println("<" + name + "> " + nm[1]);
                    this.os.println("Message successfully sent");
                }
            }
        }
    }

    public void sendFile(OutputStream os, File file) throws Exception{
        byte[] mybytearray = new byte [(int)file.length() + 1];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        this.os.println("Sending file " + file);
        os.flush();
    }

    public void receiveFile(InputStream is, File file) throws Exception {
        is = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(new File("received_" + file));
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = is.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        this.os.println("You have just received a file: " + file);
   }
}
