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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author aaf8553
 */
public class StatisticsUpdate extends TimerTask {
    public void run() {
       JSONObject obj = new JSONObject();
       
       obj.put("Discord Servers", SirBroBot.client.getGuilds().size());
       obj.put("Voice Channels",SirBroBot.client.getVoiceChannels().size() );
       obj.put("Text Channels", SirBroBot.client.getChannels(true).size());
       obj.put("Total Users", getUsers());
       obj.put("Messages Seen", DiscordListener.getMessagesSeen());
       obj.put("Uptime", getUptime());
       
  


        try (FileWriter writer = new FileWriter(tokens.webhookLink())) {

            writer.write(obj.toJSONString());
            writer.flush();
            
        }catch (IOException ex) {
            Logger.getLogger(StatisticsUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } 
    
    }
}
