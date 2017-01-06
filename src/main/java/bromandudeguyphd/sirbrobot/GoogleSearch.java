package bromandudeguyphd.sirbrobot;
/**
 * @author BroManDudeGuyPhD
 */

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleSearch {

    public static ArrayList<String> searchResults = new ArrayList<>();

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    public static void run(String term) throws IOException {
        String google = "http://www.google.com/search?q=";
        String search = term;
        String charset = "UTF-8";
        String userAgent = "ExampleBot 1.0 (+http://example.com/bot)";

        Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select(".g>.r>a");
        int counter = 1;
        
        for (Element link : links) {
            String title = link.text();
            String url = link.absUrl("href"); 
            // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
            url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

            if (!url.startsWith("http")) {
                continue; // Ads/news/etc.
            }

            searchResults.add(counter+title+" :: <"+url +">\n");
            counter++;
        }
    }

    public static String returnResults() {
        return searchResults.toString();
    }

    public static void clearSearch() {
        searchResults.clear();
    }

}
