package edu.eci.arep.WebServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;

/**
 * Big Http Server implements a web server that can return html, images and other files.
 */
public class BigHttpServer {

    private static final File ROOT = new File(System.getProperty("user.dir") + "/src/main/resources/public");
    private static final String DEFAULT_FILE = "/index.html";
    private static final String NOT_FOUND = "/404.html";
    private static final String METHOD_NOT_ALLOWED = "/405.html";
    private static final String UNSUPPORTED_MEDIA_TYPE = "/415.html";

    private static final int PORT = 35000;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Listening for connections on port --> " + PORT);
        } catch (IOException ex) {
            System.out.println("Could not listen on port: " + PORT + ". IOException: " + ex);
        }

        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedOutputStream dataOut = null;
        // Keep connection until client disconnect to the server.
        try {
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted.");
                } catch (IOException e) {
                    System.out.println("Could not accept the connection to client.");
                }
                // Prepare to receive and send requests and responses.
                // For the header.
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // For the binary data requested.
                dataOut = new BufferedOutputStream(clientSocket.getOutputStream());
                // Header from client.
                String inputLine = in.readLine();
                String[] header = inputLine.split(" ");
                // Read HTTP request from the client socket.
                while (inputLine != null) {
                    System.out.println("Received: " + inputLine);
                    if (!in.ready()) {
                        break;
                    }
                    inputLine = in.readLine();
                }
                String[] responseMethod = checkMethod(out, dataOut, header[0]);
                String contentType = "text/html";
                if (Boolean.parseBoolean(responseMethod[0])) {
                    File file = null;
                    if (header[1].equals("/")) {
                        file = new File(ROOT, DEFAULT_FILE);
                        sendRequest(out, dataOut, file, contentType, "200 OK");
                    } else {
                        file = new File(ROOT, header[1]);
                        if (!file.exists()) {
                            File newFile = new File(ROOT, NOT_FOUND);
                            sendRequest(out, dataOut, newFile, "text/html", "404 NOT_FOUND");
                        } else {
                            String[] responseContentType = checkContentType(out, dataOut, header[1]);
                            contentType = responseContentType[0];
                            if (responseContentType[1].equals("UNSUPPORTED_MEDIA_TYPE")) {
                                file = new File(ROOT, UNSUPPORTED_MEDIA_TYPE);
                                sendRequest(out, dataOut, file, contentType, "415 UNSUPPORTED_MEDIA_TYPE");
                            } else if (responseContentType[1].equals("OK")) {
                                sendRequest(out, dataOut, file, contentType, "200 OK");
                            }
                        }
                    }
                } else {
                    if (responseMethod[1].equals("METHOD_NOT_ALLOWED")) {
                        File file = new File(ROOT, METHOD_NOT_ALLOWED);
                        sendRequest(out, dataOut, file, "text/html", "405 METHOD_NOT_ALLOWED");
                    }
                }
            }
        } finally {
            // Closing all connections.
            out.close();
            in.close();
            dataOut.close();
            serverSocket.close();
            clientSocket.close();
        }
    }

    private static String[] checkMethod(PrintWriter out, BufferedOutputStream dataOut, String method) throws IOException {
        String[] response = new String[2];
        response[0] = "true";
        response[1] = "OK";
        if (!method.equals("GET")) {
            response[0] = "false";
            response[1] = "METHOD_NOT_ALLOWED";
        }
        return response;
    }

    private static String[] checkContentType(PrintWriter out, BufferedOutputStream dataOut, String requestedFile) throws IOException {
        String[] response = new String[2];
        response[0] = "text/html";
        response[1] = "OK";
        if (requestedFile.endsWith(".htm") || requestedFile.endsWith(".html")) {
            response[0] = "text/html";
        } else if (requestedFile.endsWith(".png")) {
            response[0] = "image/png";
        } else if (requestedFile.endsWith(".jpg") || requestedFile.endsWith(".jpeg")) {
            response[0] = "image/jpeg";
        } else{
            response[1] = "UNSUPPORTED_MEDIA_TYPE";
        }
        return response;
    }

    private static void sendRequest(PrintWriter out, BufferedOutputStream dataOut, File file, String contentType, String response) throws IOException {
        // Header
        out.println("HTTP/1.1 " + response);
        out.println("Server: Java HTTP Server from CarlosCL : 1.0");
        out.println("Date: " + new Date());
        out.println("Content-type: " + contentType);
        out.println("Content-length: " + file.length());
        out.println();
        out.flush();
        // Content
        String[] contentTypeDivided = contentType.split("/");
        if (contentTypeDivided[0].equals("image")) {
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, contentTypeDivided[1], baos);
            dataOut.write(baos.toByteArray());
            dataOut.flush();
        } else {
            byte[] fileByte = fileToByte(file);
            dataOut.write(fileByte, 0, (int) file.length());
            dataOut.flush();
        }
    }

    private static byte[] fileToByte(File file) throws IOException {
        byte[] dataByte = new byte[(int) file.length()];
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(dataByte);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return dataByte;
    }
}
