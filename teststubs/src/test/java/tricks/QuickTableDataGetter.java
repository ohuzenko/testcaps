package tricks;


import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Date;


public class QuickTableDataGetter {

    public static void main(String[] args) throws Exception {

        String url = "https://www.spotthelost.com/country-capital-currency.php";
        String locator = "table#container table";
       // System.out.println(Arrays.toString(getTableDataFromRequest(url,locator)));
        getTableDataFromRequest(url,locator);

    }
    public static String[] getTableDataFromRequest(String urlToRead, String locator) throws Exception{

        Date start = new Date();
        Document page = Jsoup.connect(urlToRead).get();
        Element table = page.select(locator).get(0);
        Elements rows = table.select("tr");
        String [] res = new String[rows.size()];
        int i=0;

        for(Element row : rows){
             res[i] = row.select("td").get(1).text();
             i++;
            //System.out.println(row.select("td").get(1).text());
        }

         Date stop = new Date();

        System.out.println("Jsoup.connect -> to get all the page data takes " + (stop.getTime() - start.getTime()) + "ms");


        start = new Date();
        URL  url = new URL(urlToRead);
       // URLConnection conn = url.openConnection();

        // open the stream and put it into BufferedReader
        BufferedReader br = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        StringBuilder html= new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            html.append(inputLine);
        }
        br.close();

        Document page1 = Jsoup.parse(html.toString());
        Element table1 = page1.select(locator).get(0);
        Elements rows1 = table1.select("tr");
        String [] res1 = new String[rows.size()];

        i=0;

        for(Element row1 : rows1){
            res[i] = row1.select("td").get(1).text();
            i++;
            //System.out.println(row.select("td").get(1).text());
        }


        stop = new Date();
        System.out.println("Standard Oracle ->  to get all the page data takes  " + (stop.getTime() - start.getTime()) + "ms");
        return res;
    }

}



