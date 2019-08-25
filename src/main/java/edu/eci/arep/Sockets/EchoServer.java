package edu.eci.arep.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a server that response requests of a client. This server
 * response the square of a number sent by the client.
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {

        // Socket Port
        int port = 35000;

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
        int numberReceived = 0;
        while (inputLine != null) {
            System.out.println("Número recibido: " + inputLine);
            try {
                numberReceived = Integer.parseInt(inputLine);
            } catch (NumberFormatException ex) {
                break;
            }
            outputLine = String.valueOf(numberReceived * numberReceived);
            out.println(outputLine);
            inputLine = in.readLine();
        }

        // Closing all connections.
        out.close();
        in.close();
        serverSocket.close();
        clientSocket.close();
    }

}
