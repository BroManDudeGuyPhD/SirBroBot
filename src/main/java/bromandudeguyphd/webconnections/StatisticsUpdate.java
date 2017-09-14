/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.webconnections;

import bromandudeguyphd.sirbrobot.DiscordListener;
import static bromandudeguyphd.sirbrobot.DiscordListener.getUptime;
import static bromandudeguyphd.sirbrobot.DiscordListener.getUsers;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.fileIO;
import bromandudeguyphd.sirbrobot.tokens;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aaf8553
 */
public class StatisticsUpdate extends TimerTask {
    public void run() {
        
       String stats = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "  <title>Stats data</title>"
                + "  <meta name=\"google-site-verification\" content=\"DO2kB7uoA7gkpKZHDIQRuGhrTd6etVY5ZDf7QhQh0fk\" />"
                + "  <link rel=\"shortcut icon\" type=\"image/png\" href=\"/favicon.png\"/>"
                + "  <link rel=\"stylesheet\" type=\"text/css\" href=\"/styling/main_style.css\">"
                + "</head>"
                + "<body>"
                + "Discord Servers: " + SirBroBot.client.getGuilds().size() + "<br>"
                + "Voice Channels: " + SirBroBot.client.getVoiceChannels().size() + "<br>"
                + "Text Channels: " + SirBroBot.client.getChannels(false).size() + "<br>"
                + "Total Users: " + getUsers() + "<br>"
                + "Messages Seen: " + DiscordListener.getMessagesSeen()+ "\n" + "Uptime: " + getUptime() + "<br>"
                + "</body>"
                + "</html> ";

        File fileOne = new File(tokens.webhookLink());

        try (PrintWriter writer = new PrintWriter(fileOne)) {

            writer.println(stats);
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
            Logger.getLogger(fileIO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    }
}
