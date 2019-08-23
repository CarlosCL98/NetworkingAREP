package edu.eci.arep;

import java.io.*;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

/**
 * Page Reader reads a URL and turns it into an html file.
 *
 * @author Carlos Medina
 */
public class PageReader {

    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Write your URL:");
        URL urlRead = null;
        try {
            urlRead = new URL(sc.nextLine());
            BufferedReader bf = new BufferedReader(new InputStreamReader(urlRead.openStream()));
            //Create file where is going to save the page data.
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "/src/main/resources/result.html"), "utf-8"));
            String line = bf.readLine();
            while (line != null) {
                writer.write(line);
                line = bf.readLine();
            }
            System.out.println("Done writing the new file result.html");
        } catch (MalformedURLException ex) {
            System.out.println("The URL given is incorrect. Exception: " + ex);
        } catch (IOException ex) {
            System.out.println("IOException: " + ex);
        }
        sc.close();
    }

}
