package edu.eci.arep.WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Http Server implements a web server that shows a single page.
 */
public class HttpServer {

    public static void main(String[] args) throws IOException {

        // Socket Port
        int port = 35000;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening for connection on port " + port);
        } catch (IOException ex) {
            System.out.println("Could not listen on port: " + port + ". IOException: " + ex);
        }

        PrintWriter out = null;
        BufferedReader in = null;
        Socket clientSocket = null;
        boolean finishConnection = false;
        while (!finishConnection) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException ex) {
                System.out.println("Could not accept the connection to client.");
            }
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            String outputLine = "";
            // Read HTTP request from the client socket.
            while (inputLine != null) {
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
                inputLine = in.readLine();
            }
            outputLine = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>Title of the document</title>\n"
                    + "</head>"
                    + "<body>"
                    + "My Web Site"
                    + "</body>"
                    + "</html>";
            // Header
            out.println("HTTP/1.1 200 Ok");
            out.println("Server: Java HTTP Server from CarlosCL : 1.0");
            out.println("Date: " + new Date());
            out.println("Content-type: " + "text/html");
            out.println();
            // Content
            out.println(outputLine);
            out.flush();
        }

        // Closing all connections.
        out.close();
        in.close();
        serverSocket.close();
        clientSocket.close();
    }
}
