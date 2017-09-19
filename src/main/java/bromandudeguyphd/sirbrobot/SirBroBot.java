// Started Jun 22, 2016, 10:05:48 PM 

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

import bromandudeguyphd.sirbrobot.commands.CommandDispatcher;
import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
//import sx.blah.discord.util.RateLimitException;


/**
 * @author BroManDudeGuyPhD
 */
public class SirBroBot {

    public static final Logger LOGGER = LoggerFactory.getLogger(SirBroBot.class);
    private static final int SHARD_COUNT = 8;
    public static IDiscordClient client;
    public static Skype skype;
    public static IUser root;
    public static CommandDispatcher dispatcher;
    private static final long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws Exception {
    FileChecker.purge();
    boot();
    
//        try {
//            bootSkype();
//        }catch (InvalidCredentialsException | ConnectionException | NotParticipatingException ignored){}
    }

    public static void boot() throws DiscordException, InterruptedException {
        //Discord4J.disableChannelWarnings();
        client = new ClientBuilder().setMaxReconnectAttempts(1000).withToken(tokens.discordToken()).withShards(SHARD_COUNT).login();
        client.getDispatcher().registerListener(dispatcher = new CommandDispatcher());
        client.getDispatcher().registerListener(new DiscordListener());
        
        
    }

    public static void bootSkype() throws InvalidCredentialsException, ConnectionException, NotParticipatingException {
        String password = tokens.skypePassword();
        skype = new SkypeBuilder("sirbrobot", password).withAllResources().build();
        skype.login();

//        skype.getEventDispatcher().registerListener(new Listener() {
//            @EventHandler
//            public void onMessage(MessageReceivedEvent e) {
//                System.out.println("Got message: " + e.getMessage().getContent());
//                //IMessage sendMessage = client.getOrCreatePMChannel(root).sendMessage("Recieved Skype Message: "+e.getMessage().getContent().toString()+"\nFrom: "+e.getMessage().getSender().getDisplayName());
//
//                if (e.getMessage().getContent().toString().contains("?REBOOT")) {
//
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException ex) {
//                        Thread.currentThread().interrupt();
//                    }
//                    ProcessBuilder pb = new ProcessBuilder("java", "-jar", SirBroBot.class.getProtectionDomain().getCodeSource().getLocation().toString());
//                    pb.inheritIO();
//                    try {
//                        pb.start();
//                    } catch (IOException e1) {
//                        LOGGER.error("Could not reboot!", e1);
//                        try {
//                            e.getMessage().getSender().getChat().sendMessage("Could not reboot! " + e);
//                        } catch (ConnectionException e2) {
//                            LOGGER.error("Could not send skype message!", e2);
//                        }
//                        return;
//                    }
//                    System.exit(0);
//                }
//            }
//        });
//        skype.subscribe();
    }

        
        public static String getUptime() {
            
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - startTime;

        int days = (int) TimeUnit.MILLISECONDS.toDays(tDelta);
        long hrs = TimeUnit.MILLISECONDS.toHours(tDelta) - (days * 24);
        long min = TimeUnit.MILLISECONDS.toMinutes(tDelta) - (TimeUnit.MILLISECONDS.toHours(tDelta) * 60);
        long sec = TimeUnit.MILLISECONDS.toSeconds(tDelta) - (TimeUnit.MILLISECONDS.toMinutes(tDelta) * 60);

        return String.format("%02dd:%02dh:%02dm:%02ds", days, hrs, min, sec);
    }
        
        public static String getTimeFromMilis(long milis) {

        long hrs = TimeUnit.MILLISECONDS.toHours(milis);
        long min = TimeUnit.MILLISECONDS.toMinutes(milis) - (TimeUnit.MILLISECONDS.toHours(milis) * 60);
        long sec = TimeUnit.MILLISECONDS.toSeconds(milis) - (TimeUnit.MILLISECONDS.toMinutes(milis) * 60);

        return String.format("%02dh:%02dm:%02ds", hrs, min, sec);
    }
        
    }



