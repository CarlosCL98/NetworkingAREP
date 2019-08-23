package edu.eci.arep.ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 */
public class Client {

    public static void main(String[] args) {

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1",35000);
            //out = new PrintWriter();
        } catch (UnknownHostException ex) {
            System.out.println("The host given is unknown. Exception: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
    }

}
