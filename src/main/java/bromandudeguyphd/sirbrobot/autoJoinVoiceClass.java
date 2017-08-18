/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

/**
 * @author BroManDudeGuyPhD
 */
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.DiscordListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.Event;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;
import sx.blah.discord.util.audio.AudioPlayer;

public class autoJoinVoiceClass implements Runnable {

    Event controlledEvent;
    
    public autoJoinVoiceClass(Event passedEvent) {
        this.controlledEvent = passedEvent;
        
        System.out.println("Shardcount: "+passedEvent.getClient().getShardCount());
    }

    @Override
    public void run() {
        ReadyEvent event;
        
        int autoJoinChannelsAtStart = DiscordListener.autoJoinChannels.size();
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(DiscordListener.autoJoinChannels);
        boolean autoJoinCompleted = false;
        int servers = SirBroBot.client.getGuilds().size();
        
        
        while(autoJoinCompleted == false){
            
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(autoJoinVoiceClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if (servers == SirBroBot.client.getGuilds().size()) {
            
            for (int i = 0; i < autoJoinChannelsAtStart; i++) {
                System.out.println(i + " " + temp.get(i));
                try {
                    controlledEvent.getClient().getVoiceChannelByID(temp.get(i)).join();
                } catch (MissingPermissionsException ex) {
                    Logger.getLogger(DiscordListener.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
            sx.blah.discord.handle.obj.Status status = sx.blah.discord.handle.obj.Status.stream("Say ?commands", "https://www.twitch.tv/SirBroBot/profile");
            controlledEvent.getClient().changeStatus(status);
            autoJoinCompleted = true;
            
        } 
        }
        
    }
}
