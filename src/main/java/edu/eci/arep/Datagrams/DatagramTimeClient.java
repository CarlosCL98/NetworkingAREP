package edu.eci.arep.Datagrams;

import java.io.IOException;

import java.net.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This client receives the recent date of a server.
 */
public class DatagramTimeClient {

    public static void main(String[] args) {
        String dateReceived = "";
        try {
            while(true) {
                // Buffer to send the data.
                byte[] sendBuf = new byte[256];
                DatagramSocket socket = new DatagramSocket();
                InetAddress address = InetAddress.getByName("127.0.0.1");
                DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 45000);
                // Send packet.
                socket.send(packet);
                System.out.println("Packet sent....");

                packet = new DatagramPacket(sendBuf, sendBuf.length);
                try {
                    /* Set a So Timeout to throw an exception when the client does not receive
                       an answer by the server in 5 seconds.
                    */
                    socket.setSoTimeout(1000);
                    // Receive packet with the server date.
                    socket.receive(packet);
                    dateReceived = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Packet received....");
                    System.out.println("Server on --> Date: " + dateReceived);
                } catch (SocketTimeoutException ex) {
                    // Show the last date update by the server if it does not answer.
                    System.out.println("Server off --> Date: " + dateReceived);
                }
                Thread.sleep(5000);
            }
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

