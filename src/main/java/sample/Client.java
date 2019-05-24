package sample;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;

public class Client {

    private static final String K8S_SERVICE = "server";

    public static void main(String ... args) throws IOException {
        new Client().run();
    }

    private void run() throws IOException {
        while (true) {
            try (Socket s = new Socket()) {
                s.setSoTimeout(10000);
                s.connect(new InetSocketAddress(K8S_SERVICE, 8080), 10000);
                sendRequest(s);
                handleResponse(s);
            }
            sleep(1000);
        }
    }

    private void sendRequest(Socket s) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        writer.write("echo");
        writer.newLine();
        writer.flush();
        System.out.println(LocalDateTime.now() + " send request: echo");
    }

    private void handleResponse(Socket s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String msg = reader.readLine();
        System.out.println(LocalDateTime.now() + " receive: " + msg);
    }

    private void sleep(long millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
