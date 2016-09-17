// Andrew Fossier
// aaf8553
// CMPS 261
// Programming Project : 18
// Due Date : 1/25/16 11:55PM
// Started Jun 22, 2016, 10:05:48 PM 

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.Visibility;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageEvent;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.ClientBuilder;

import sx.blah.discord.util.DiscordException;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Status;

import sx.blah.discord.util.MissingPermissionsException;
//import sx.blah.discord.util.RateLimitException;


/**
 * @author BroManDudeGuyPhD
 */
public class sirbrobot {

    public static IDiscordClient client;
    public static Skype skype;
    public static IUser root;
    public static tokens tokens;
    
    public static void main(String[] args) throws IOException, Exception {
        FileChecker.purge();
        //bootSkype();
        boot();
    }
    
    public static void boot(){
        
        
        
        try {
            client = new ClientBuilder().setMaxReconnectAttempts(1000).withToken(tokens.discordToken()).login();
        } catch (DiscordException ex) {
            Logger.getLogger(sirbrobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(new MainListener());
        
    }
    
    public static void bootSkype() throws InvalidCredentialsException, ConnectionException, NotParticipatingException{
        String password = tokens.skypePassword();
        skype = new SkypeBuilder("sirbrobot",password).withAllResources().build();
        skype.login();

        skype.getEventDispatcher().registerListener(new Listener() {
            @EventHandler
            public void onMessage(MessageReceivedEvent e){
                IUser root = sirbrobot.client.getUserByID("150074847546966017");
                System.out.println("Got message: " + e.getMessage().getContent());
                //IMessage sendMessage = client.getOrCreatePMChannel(root).sendMessage("Recieved Skype Message: "+e.getMessage().getContent().toString()+"\nFrom: "+e.getMessage().getSender().getDisplayName());
                
                if(e.getMessage().getContent().toString().contains("?REBOOT")){
                    
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    try {
                        client = new ClientBuilder().withToken(tokens.discordToken()).login();
                    } catch (DiscordException ex) {
                        Logger.getLogger(sirbrobot.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
        skype.subscribe();
    }

    

}


