package edu.eci.arep.URL;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL Reader class allows to read the data of a URL given.
 *
 * @author Carlos Medina
 */
public class URLReader {

    public static void main( String[] args ) {
        URL url = null;

        try {
            System.out.println("Reading URL...");
            url = new URL("https://schinotes.herokuapp.com:80/register/index.html?data=54#AREP2019");
            System.out.println("--------Protocol--------");
            String protocol = url.getProtocol();
            System.out.println(protocol);
            System.out.println("--------Authority--------");
            String authority = url.getAuthority();
            System.out.println(authority);
            System.out.println("--------Host--------");
            String host = url.getHost();
            System.out.println(host);
            System.out.println("--------Port--------");
            int port = url.getPort();
            System.out.println(port);
            System.out.println("--------Path--------");
            String path = url.getPath();
            System.out.println(path);
            System.out.println("--------Query--------");
            String query = url.getQuery();
            System.out.println(query);
            System.out.println("--------File--------");
            String file = url.getFile();
            System.out.println(file);
            System.out.println("--------Ref--------");
            String ref = url.getRef();
            System.out.println(ref);
        } catch (MalformedURLException ex) {
            System.out.println("The URL given is incorrect. Exception: " + ex);
        }

    }
}
