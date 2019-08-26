package edu.eci.arep.Datagrams;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This server sends its data to a client.
 */
public class DatagramTimeServer {

    DatagramSocket socket;

    public DatagramTimeServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet); // Wait for a packet.
                System.out.println("Packet received....");
                String dString = new Date().toString();
                buf = dString.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                System.out.println("Packet sent....");
            }
        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        socket.close();
    }

    public static void main(String[] args) {
        DatagramTimeServer ds = new DatagramTimeServer(45000);
        ds.startServer();
    }
}
