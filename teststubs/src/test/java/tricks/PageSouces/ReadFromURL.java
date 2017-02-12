package tricks.PageSouces;

import java.net.*;
import java.io.*;

public class ReadFromURL  {
    public static void main(String[] args) throws Exception {

        String url = "http://www.oracle.com/";
        readPageSourceFromURL(url);
        readPageFromURLConnection(url);
    }

    private static String readPageSourceFromURL(String urlToRead) throws IOException {

        URL url = new URL(urlToRead);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
          sb.append(inputLine);

        in.close();

        return sb.toString();
    }

    private static void readPageFromURLConnection(String urlToRead) throws IOException {

        URL url = new URL(urlToRead);
        URLConnection urlConnection = url.openConnection();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }


}

