package ru.geekbrains.chat.server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Server is start, waiting for clients");
            Socket socket = serverSocket.accept();
            System.out.println("Client connect");
            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true){
                String msg = is.readUTF();
                if (msg.equals("/end")){
                    out.writeUTF("/end_confirm");
                    is.close();
                    out.close();
                    socket.close();
                    serverSocket.close();
                    System.out.println("Client disconnected server is stopped");
                    break;
                }
                System.out.println("client: " + msg + '\n');
                out.writeUTF("echo: " + msg);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
