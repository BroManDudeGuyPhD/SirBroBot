package com.bromandudeguyphd.albot;

/**
 * @author BroManDudeGuyPhD
 */
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ALBot {

    static Twitter twitter = TwitterFactory.getSingleton();
    static int favorites = 0;
    static int retweets = 0;
    static int mostFavoritesIndex = 0;
    static int mostRetweetsIndex = 0;
    static commandsClass commands = new commandsClass();;
    
    public static void main(String[] args){
        String command = null;
        System.out.print("ALBot## ");
        Scanner in = new Scanner( System.in );
        
        
        while (1==1) {
            command = in.nextLine().toLowerCase();
                if(command.equals("quit")){
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                
                System.out.println(commands(command));
            
            
            System.out.print("ALBot## ");
            in = new Scanner( System.in );
        }
    }
    
  
    

    public static String commands(String command){
        String commandSent = command;
        long twitterID = 0;
        String result = null;
        
        if (commandSent.startsWith("?tweet")) {

            try {
                twitter.updateStatus(commandSent.replace("?tweet ", ""));

                result = "Tweet sent! (" +commandSent.replace("?tweet ", "") +") ";
            } catch (TwitterException ex) {
                result = "Failed to send Tweet";
            }

        } 
        
        else if (commandSent.startsWith("?deletetweet")) {
            if (twitterID == 0) {
                return ("I dont have an ID to delete");
            } else {
                try {
                    twitter.destroyStatus(twitterID);
                    result = "Tweet deleted!";
                } catch (TwitterException ex) {
                    result = "Failed to delete Tweet";
                }
                
            }

        }
        
        else if (commandSent.startsWith("?commands")) {
            return commands.commandListNormal;
        }
        
        
        else if (commandSent.startsWith("?topic")) {
            ArrayList<ArrayList> results = getTweets(commandSent.replace("?topic ", ""));
            
            System.out.println("Total tweets   : "+results.get(0).size()+"\n"
                    + "Total Favorites: "+getFavorites()+" \n"
                    + "Total Retweets : "+getRetweets()+"\n\n"
                    + "Most Favorites : "+ results.get(1).get(0) +"\n"
                    + "Tweet: "+results.get(1).get(1)+"\n"
                    + "Most Retweets  : "+ results.get(1).get(2) + "\n"
                    + "Tweet: "+results.get(1).get(3)+"\n");
        }
        
        else{
            result = "No associated command";
        }
        
        return result;
    }
    
    
    //Searches based on keyword, returns analytics including 
    //TOTAL retweets, TOTAL favorites, as well as the tweet for highest retweet count and highest favorite count 
    public static ArrayList<ArrayList> getTweets(String topic) {
        favorites = 0;
        retweets = 0;
        
        Twitter twitter = new TwitterFactory().getInstance();
        ArrayList<ArrayList> analytics = new ArrayList<>();
        ArrayList<String> tweetList = new ArrayList<String>();
        ArrayList<String> popularity = new ArrayList<String>();
        
        //Create Placeholders
        popularity.add("");popularity.add("");popularity.add("");popularity.add("");
        
        try {
            Query query = new Query(topic);
            QueryResult result;
            
            Integer favoritesMax = 0;
            Integer retweetsMax = 0;
                    
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    tweetList.add(tweet.getText());

                    setFavorites(getFavorites()+tweet.getFavoriteCount());
                    setRetweets(getRetweets()+tweet.getRetweetCount());
                    
                    if(tweet.getFavoriteCount() > favoritesMax){
                        favoritesMax = tweet.getFavoriteCount();
                        setMostFavoritesIndex(tweetList.size());
                        
                        //Most favorited Tweet
                        popularity.add(0, favoritesMax.toString());
                        popularity.add(1, tweet.getText());
                        
                    }
                    
                    if(tweet.getRetweetCount() > retweetsMax){
                        retweetsMax = tweet.getRetweetCount();
                        setMostRetweetsIndex(tweetList.size());
                        
                        //Most retweeted Tweet
                        popularity.add(2, retweetsMax.toString());
                        popularity.add(3, tweet.getText());
                        
                    }
                }
                
                
                
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
        
        
        analytics.add(tweetList);
        analytics.add(popularity);
        
        
        
        return analytics;
    }

    public static int getFavorites() {
        return favorites;
    }

    public static void setFavorites(int favorites) {
        ALBot.favorites = favorites;
    }

    public static int getRetweets() {
        return retweets;
    }

    public static void setRetweets(int retweets) {
        ALBot.retweets = retweets;
    }

    public static int getMostFavoritesIndex() {
        return mostFavoritesIndex;
    }

    public static void setMostFavoritesIndex(int mostFavoritesIndex) {
        ALBot.mostFavoritesIndex = mostFavoritesIndex;
    }

    public static int getMostRetweetsIndex() {
        return mostRetweetsIndex;
    }

    public static void setMostRetweetsIndex(int mostRetweetsIndex) {
        ALBot.mostRetweetsIndex = mostRetweetsIndex;
    }
    
    
    
}
