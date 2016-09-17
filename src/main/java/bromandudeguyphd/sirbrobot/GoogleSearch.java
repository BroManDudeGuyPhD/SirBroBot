package bromandudeguyphd.sirbrobot;

/**
 *
 * @author aaf8553
 */

import java.io.IOException;
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
        //Taking search term input from console

        String searchTerm = term;

        int num = 1;
         
        String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
        //without proper User-Agent, we will get 403 error
        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
         
        //below will print HTML data, save it to a file and open in browser to compare
        //System.out.println(doc.html());
         
        //If google search results HTML change the <h3 class="r" to <h3 class="r1"
        //we need to change below accordingly
        Elements results = doc.select("h3.r > a");
        searchResults.add("Search results for: **"+term+"**\n");
        for (Element result : results) {
            
            String linkHref = result.attr("href");
            String linkText = result.text();
            
            if(linkText.startsWith("Images for")){
                
            }
            else

            searchResults.add("Result::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&"))+"\n");
            
        }
    }
    
    public static String returnResults(){
        return searchResults.toString();
    }
    
    public static void clearSearch(){
        searchResults.clear();
    }
 
}
