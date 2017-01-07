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
import bromandudeguyphd.sirbrobot.MainListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;
import sx.blah.discord.util.audio.AudioPlayer;

public class autoJoinVoiceClass implements Runnable {

    @Override
    public void run() {
        int autoJoinChannelsAtStart = MainListener.autoJoinChannels.size();
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(MainListener.autoJoinChannels);
        boolean autoJoinCompleted = false;
        int servers = SirBroBot.client.getGuilds().size();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(autoJoinVoiceClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        if (servers == SirBroBot.client.getGuilds().size()) {
            for (int i = 0; i < autoJoinChannelsAtStart; i++) {
                System.out.println(i + " " + temp.get(i));
                try {
                    SirBroBot.client.getVoiceChannelByID(temp.get(i)).join();
                } catch (MissingPermissionsException ex) {
                    Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
            sx.blah.discord.handle.obj.Status status = sx.blah.discord.handle.obj.Status.stream("say ?commands", "https://www.twitch.tv/SirBroBot/profile");
            SirBroBot.client.changeStatus(status);
        } 
        
        else {
            run();
        }

    }
}
