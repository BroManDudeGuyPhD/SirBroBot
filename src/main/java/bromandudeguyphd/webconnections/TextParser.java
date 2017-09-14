/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.webconnections;

import bromandudeguyphd.sirbrobot.fileIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author aaf8553
 */
public class TextParser {

    public static ArrayList<String> websites = new ArrayList<>();
    public static ArrayList<String> html = new ArrayList<>();
    public static ArrayList<String> text = new ArrayList<>();

    public static void html(String url) throws Exception {

            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
                html.add(inputLine);
            }

            in.close();
            fileIO.htmlFile(html, url.replace(":", "").replace("/", "."));
            

        
    }

    public static void text(String url) throws IOException {
        String websiteURL = null;

        Document doc = Jsoup.connect(url).get();
        org.jsoup.select.Elements ps = doc.select("p");
        StringBuilder response = new StringBuilder();

        response.append(ps.text());

        text.add(response.toString());
        fileIO.save(text,"src/dataDocuments/HTMLfiles/"+"HTML__"+url.replace("/", ".").replace(":", ""));
        

        
    }
}



