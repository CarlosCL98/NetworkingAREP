package edu.eci.arep.Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *  Represents a client that connects to a server
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {

        // Socket where the client communicates with the server
        Socket echoSocket = null;
        // Input and output streams.
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException ex) {
            System.out.println("The host given is unknown. Exception: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException to echo socket: " + ex);
        }

        // The client is going to send to the server inputs.
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String userInput = stdin.readLine();
        String inLine = "";
        while (userInput != null) {
            out.println(userInput);
            inLine = in.readLine();
            if (inLine == null) break;
            System.out.println(userInput + " ^ 2 = " + inLine);
            userInput = stdin.readLine();
        }

        // Closing all connections
        echoSocket.close();
        out.close();
        in.close();
        stdin.close();
    }

}
