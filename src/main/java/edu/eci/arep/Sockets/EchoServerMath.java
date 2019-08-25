package edu.eci.arep.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a server that response requests of a client. This server
 * response the result of an trigonometric function of a number sent by the client.
 */
public class EchoServerMath {

    /*public static void main(String[] args) throws IOException {
        String test = "fun";
        String[] testDivided = test.split(":");
        System.out.println(testDivided.length);
    }*/

    public static void main(String[] args) throws IOException {
        int port = 36000;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Could not listen on port: " + port + ". IOException: " + ex);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Could not accept the connection to client.");
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine = in.readLine();
        String outputLine = "";
        String[] data = null;
        String function = "cos";
        while (inputLine != null) {
            System.out.println("Recibido: " + inputLine);
            data = inputLine.split(":");
            if (data.length == 1) {
                try {
                    if (function.equals("cos")) {
                        out.println(Math.cos(Double.parseDouble(data[0])));
                    } else if (function.equals("sen")) {
                        out.println(Math.sin(Double.parseDouble(data[0])));
                    } else if (function.equals("tan")) {
                        out.println(Math.tan(Double.parseDouble(data[0])));
                    }
                } catch (NumberFormatException ex) {
                    break;
                }
            } else if (data.length > 1) {
               if (data[0].equals("fun")) {
                   out.println("Function changed: " + function + " to " + data[1]);
                   function = data[1];
               } else {
                   break;
               }
            }
            inputLine = in.readLine();
        }

        // Closing all connections.
        out.close();
        in.close();
        serverSocket.close();
        clientSocket.close();
    }

}
