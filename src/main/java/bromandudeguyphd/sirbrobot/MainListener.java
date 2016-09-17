package bromandudeguyphd.sirbrobot;

import bromandudeguyphd.imagewriting.MirrorImage;
import bromandudeguyphd.imagewriting.NegativeImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
//import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.impl.events.MentionEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.UserJoinEvent;
import sx.blah.discord.handle.impl.events.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.UserVoiceChannelMoveEvent;
//import sx.blah.discord.util.audio.*;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import bromandudeguyphd.htmlparsing.*;
import static bromandudeguyphd.sirbrobot.sirbrobot.client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.GuildLeaveEvent;
import sx.blah.discord.handle.impl.events.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.audio.AudioPlayer;

/**
 * @author AndrewFossier
 */
public class MainListener {

    static final Logger LOG = LoggerFactory.getLogger(MainListener.class);

    ArrayList<String> curseWords = new ArrayList<>();
    List<IGuild> serversJoined = new ArrayList<>();
    ArrayList<String> serverIconNames = new ArrayList<>();
    ArrayList<String> rolesOnServer = new ArrayList<>();
    ArrayList<String> userInfo = new ArrayList<>();


    //Map stores Voice Anonounce Determiner for all servers, key is Server ID and 
    Map VAD = new HashMap();
    Map VADdata = new HashMap();
    Map WAD = new HashMap();
    Map WADdata = new HashMap();
    Map LAD = new HashMap();
    Map LADdata = new HashMap();
    Map SAD = new HashMap();
    Map PMD = new HashMap();
    Map<String, ArrayList<String>> Playlist = new HashMap();
    Map<String, String> TAG = new HashMap();

    commandsClass commands = new commandsClass();
    ArrayList<String> textChannel = new ArrayList<>();
    ArrayList<String> autoJoinChannels = new ArrayList<>();


    Twitter twitter = TwitterFactory.getSingleton();
    tokens tokens;
    

    boolean updateDispatcher = false;
    boolean messageStatus = false;
    //private final HashMap<IUser, Long> commandCooldown = new HashMap<>();
    int usageCounter = 0;
    long tStart = System.currentTimeMillis();

    MessageBuilder messageBuilderGifs = new MessageBuilder(sirbrobot.client);
    File dance = new File("src/images/dancingKnight.gif");
    File joust = new File("src/images/joust.gif");
    File wakeup = new File("src/images/wakeup.gif");
    File taunt = new File("src/images/taunt.gif");
    File insult = new File("src/images/insult.gif");
    File joust2 = new File("src/images/joust2.gif");
    File fairest = new File("src/images/fairest.jpg");
    File enemy = new File("src/images/enemy.png");

    long twitterID = 0;
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @EventSubscriber
    public void onGuildCreate(GuildCreateEvent event){
        if(updateDispatcher == true){
            

            try {
                try {
                    sirbrobot.client.getOrCreatePMChannel(event.getGuild().getOwner()).sendMessage("Thanks for adding me to your guild! It definitely means a lot to the guy that made me.\n"
                            + "My main command is `?commands`. It lets you see what I am capable of. Most of my features are opt-in, meaing you must enable them for them to work.\n"
                            + "Definitely be sure to join the project at https://discord.gg/0wCCISzMcKMkfX88 \n"
                            + "And check out my \n"
                            + "**Twitter account:** <https://twitter.com/SirBroBotThe1st>\n"
                            + "**Website: **http://bootswithdefer.tumblr.com/sirbrobot\n");
                } catch (MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                
                IChannel updateChannel = sirbrobot.client.getChannelByID("197567480439373824");
                System.out.println("Joined guild "+event.getGuild().getName()+" | Members: "+event.getGuild().getUsers().size()+"  :::  Server Count: "+sirbrobot.client.getGuilds().size()+" User Count: "+getUsers());
                RequestBuffer.request(() -> { 
                    try {
                        updateChannel.sendMessage("Joined guild "+event.getGuild().getName()+" | Members: "+event.getGuild().getUsers().size()+"  :::  Server Count: "+sirbrobot.client.getGuilds().size()+" User Count: "+getUsers());
                    } catch (MissingPermissionsException | DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                });
            } catch (DiscordException ex) {
                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RateLimitException ex) {
                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             
        }
 
        
    }
    
    @EventSubscriber
    public void onGuildLeave(GuildLeaveEvent event){
        System.out.println("Left guild " + event.getGuild().getName() + " | Members: " + event.getGuild().getUsers().size());
        IChannel updateChannel = sirbrobot.client.getChannelByID("197567480439373824");
        if (updateDispatcher == true) {
            try {
                updateChannel.sendMessage("Left guild " + event.getGuild().getName() + " | Members: " + event.getGuild().getUsers().size() + "  :::  Server Count: " + sirbrobot.client.getGuilds().size() + " User Count: " + getUsers());
            } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event){
        
       //sx.blah.discord.handle.obj.Status status = sx.blah.discord.handle.obj.Status.stream("say ?commands", "https://www.twitch.tv/sirbrobot/profile");
        //sirbrobot.client.changeStatus(status);
        serversJoined = sirbrobot.client.getGuilds();

//        try {
//            fileIO.readFile(curseWords, "bannedWords.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            fileIO.readFile(serverIconNames, "src/dataDocuments/serverIconUrls.txt");
        } catch (IOException ex) {
            LOG.warn("Unable to load serverIconNames");
        }

        
        try {
            fileIO.readFile(autoJoinChannels, "src/dataDocuments/autoJoinChannels.txt");
        } catch (IOException ex) {
            LOG.warn("Unable to load serverIconNames");
        }
        
        try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        
        fileIO.readHash(VAD, "VAD");
        fileIO.readHash(VADdata, "VADdata");
        fileIO.readHash(WAD, "WAD");
        fileIO.readHash(WADdata, "WADdata");
        fileIO.readHash(TAG, "TAG");
        fileIO.readHash(LAD, "LAD");
        fileIO.readHash(LADdata, "LADdata");
        fileIO.readHash(PMD, "PMD");

        PostingHTMLData post = new PostingHTMLData();
        try {
            post.sendReq(sirbrobot.client.getGuilds().size());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        for (int i = 0; i < autoJoinChannels.size(); i++) {
//            try {
//                sirbrobot.client.getVoiceChannelByID(autoJoinChannels.get(i)).join();
//
//            } catch (MissingPermissionsException e) {
//                System.out.println("Cant connect to AutoJoinChannel "+sirbrobot.client.getVoiceChannelByID(autoJoinChannels.get(i)).getName());
//            }
//            AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(sirbrobot.client.getVoiceChannelByID(autoJoinChannels.get(i)).getGuild());
//            audio.setVolume((float) 0.13);
//
//        }
        updateDispatcher = true;
        client.changeStatus(sx.blah.discord.handle.obj.Status.stream("?commands", "https://www.twitch.tv/sirbrobot"));
    }

    @EventSubscriber
    public void handleJoin(UserJoinEvent event) {
        if (WAD.containsKey(event.getGuild().getID())) {
            LocalDateTime joined = event.getJoinTime();
            IUser user = event.getUser();
            IUser Owner = event.getGuild().getOwner();
            MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
            messageBuilder.withChannel(event.getGuild().getChannels().get(0));

            event.getGuild().getID();

            RequestBuffer.request(() -> {
                try {

                    messageBuilder.withContent("" + WAD.get(event.getGuild().getID()).toString().replace("USERMENTION", event.getUser().mention()).replace("USERNAME", "**" + event.getUser().getName() + "**")).send();

                    //IMessage sendWelcome = sirbrobot.client.getChannelByID("main_chat").sendMessage("Welcome to ACORN " + user.mention() +"! Glad you joined us. Check out the #server-rules and PM an Admin to get Chat rights, along with registering your Discord account");
                } catch (DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

    }

    @EventSubscriber
    public void handleVoiceJoin(UserVoiceChannelJoinEvent event){
        if (VAD.containsKey(event.getChannel().getGuild().getID())) {
            IVoiceChannel channelJoined = event.getChannel();
            String userJoined = event.getUser().getName();

            RequestBuffer.request(() -> {
                try {
                    MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
                    messageBuilder.withChannel(event.getChannel().getGuild().getChannelByID(VADdata.get(event.getChannel().getGuild().getID()).toString()));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " joined " + channelJoined.getName()).withTTS().send();
                    
                    if (VAD.get(event.getChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " joined " + channelJoined.getName());
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    if (VAD.get(event.getChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                } catch ( DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @EventSubscriber
    public void handleVoiceMove(UserVoiceChannelMoveEvent event) {
        if (VAD.containsKey(event.getNewChannel().getGuild().getID())) {
            IVoiceChannel channelJoined = event.getNewChannel();
            String userJoined = event.getUser().getName();

            RequestBuffer.request(() -> {
                try {
                    MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
                    messageBuilder.withChannel(event.getNewChannel().getGuild().getChannelByID(VADdata.get(event.getNewChannel().getGuild().getID()).toString()));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " moved to " + channelJoined.getName()).withTTS().send();
                    
                    
                    if (VAD.get(event.getNewChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " moved to " + channelJoined.getName());
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    if (VAD.get(event.getNewChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                } catch ( DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

    }

    @EventSubscriber
    public void handleVoiceLeave(UserVoiceChannelLeaveEvent event){
        IVoiceChannel channelJoined = event.getChannel();
        if (LAD.containsKey(event.getChannel().getGuild().getID())) {
            String userJoined = event.getUser().getName();

            RequestBuffer.request(() -> {
                try {
                    MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
                    messageBuilder.withChannel(event.getChannel().getGuild().getChannelByID(LADdata.get(event.getChannel().getGuild().getID()).toString()));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " left " + channelJoined.getName()).withTTS().send();

                    if (LAD.get(event.getChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " left " + channelJoined.getName());
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    if (LAD.get(event.getChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DiscordException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                } catch ( DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

//    @EventSubscriber
//    public void onDiscordDisconnectedEvent(DiscordDisconnectedEvent event) throws HTTP429Exception, ChatNotFoundException {
//        try {
//                sirbrobot.skype.getOrLoadChat("8:andrew.addison.").sendMessage("I've gone offline.");
//            } catch (ConnectionException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        System.out.println(event.getReason());
//        if (event.getReason().equals(event.getReason().TIMEOUT)) {
//            try {
//                sirbrobot.skype.getOrLoadChat("19:33f1c36b235544eab1b957983ea10ec1@thread.skype").sendMessage("I've gone offline because: TIMED OUT : Reboot Iminent");
//            } catch (ConnectionException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ChatNotFoundException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (event.getReason().equals(event.getReason().LOGGED_OUT)) {
//            try {
//                sirbrobot.skype.getOrLoadChat("19:33f1c36b235544eab1b957983ea10ec1@thread.skype").sendMessage("I've gone offline because: LOGGED OUT");
//            } catch (ConnectionException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ChatNotFoundException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else if (event.getReason().equals(event.getReason().UNKNOWN)) {
//            try {
//                sirbrobot.skype.getOrLoadChat("8:andrew.addison.").sendMessage("I've gone offline because: UNKNOWN_ERROR");
//            } catch (ConnectionException ex) {
//                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        fileIO.save(serverIconNames, "ServerIconUrls.txt", "src/dataDocuments");
//        fileIO.save(playedSongs, "playedSongs.txt", "src/dataDocuments");
//        fileIO.saveHash(VAD, "VAD");
//        fileIO.saveHash(VADdata, "VADdata");
//        fileIO.saveHash(WAD, "WAD");
//        fileIO.saveHash(WADdata, "WADdata");
//        fileIO.saveHash(LAD, "LAD");
//        fileIO.saveHash(LADdata, "LADdata");
//        fileIO.saveHash(PMD, "PMD");
//        System.out.println("Disconnected!");
//        System.out.println(event.getReason().name());
//
//    }

    @EventSubscriber
    public void mentioned(MentionEvent event){
        IUser broBotUser;
        IMessage message = event.getMessage();
        String mcontent = message.getContent().toLowerCase();
        MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
        messageBuilder.withChannel(event.getMessage().getChannel());
        broBotUser = message.getMentions().get(0);

        Date date = new Date();

//        
//        
//        // && !adminUsers.contains(message.getAuthor().getId())
//        if (commandCooldown.containsKey(message.getAuthor())) {
//            if ((date.getTime() - commandCooldown.get(message.getAuthor())) <= 3000) {
//                message.reply(" We've added a 3-second delay to all commands. You've waited " + (date.getTime() - commandCooldown.get(message.getAuthor())) / 1000 + " seconds.");
//                return;
//            } else {
//                commandCooldown.put(message.getAuthor(), date.getTime());
//            }
//        }
        String mention = message.getAuthor().mention();
        IUser user;
        user = message.getMentions().get(0);
        String sender = message.getAuthor().getName();

        if (mcontent.contains("hello")) {

            //Custom Message for ME
            if (sender.equals("BroManDudeGuyPhD")) {
                try {
                    messageBuilder.withContent("Hello maker " + mention).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Custom Message for Jolee
            else if (sender.equals("Jaurielb")) {
                try {
                    messageBuilder.withContent(String.format("Good Day Madame " + mention)).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Custom Message for Dane
            else if (sender.equals("Dane")) {
                try {
                    messageBuilder.withContent(String.format("I Dub thee, Dane, PHP and Javascript master of the Universe " + mention)).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Custome Message for Nick
            else if (sender.equals("NickPlusPlus")) {
                try {
                    messageBuilder.withContent(String.format("#TeamLG@MLG2016 " + mention)).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Custom message for Andre
            else if (sender.equals("WhiteDespair")) {
                try {
                    messageBuilder.withContent(String.format("Hello " + mention + ", where is Black  Hope?" + mention)).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //General User Response
            else {
                try {
                    messageBuilder.withContent(String.format("Hello " + mention)).send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            usageCounter++;
        }

        if (mcontent.contains("dance")) {
            
            try {
                event.getMessage().getChannel().sendFile(dance);
                } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("joust")) {
try{event.getMessage().getChannel().sendFile(joust2);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
           
usageCounter++;
        } else if (mcontent.contains("wake up")) {
            try{event.getMessage().getChannel().sendFile(wakeup);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("insult")) {
            try{event.getMessage().getChannel().sendFile(insult);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("taunt")) {
            try{event.getMessage().getChannel().sendFile(taunt);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("who is the fairest of them all")) {
            try{event.getMessage().getChannel().sendFile(fairest);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("who is your foe")) {
            try{event.getMessage().getChannel().sendFile(enemy);}catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            usageCounter++;
        } else if (mcontent.contains("tag")) {

            //Syntax @Mention tag <name> <action> <url (if applicable)
            String[] saveArray = message.getContent().split(" ");
            String tagName = null;
            String action = null;
            String url = null;
            for (int i = 0; i < saveArray.length; i++) {
                if (i == 2) {
                    tagName = saveArray[2];
                }
                if (i == 3) {
                    action = saveArray[3];
                }
                if (i == 4) {
                    url = saveArray[4];
                }
            }

            if (saveArray.length == 2 && saveArray[1].toLowerCase().equals("taghelp")) {
                messageBuilder.withContent("Syntax: @SirBroBot(mention) tag <name> <action> <url>\n```"
                        + "1. @SirBroBot\n"
                        + "2a. tag (always put when adding tag)\n"
                        + "2b. tags     :returns all currently assigned tags\n"
                        + "3. <name>    :give name to tag(when adding) OR SirBroBot returns image associated with name (if tag exists)\n"
                        + "4. <action>  :(add, remove. info)\n"
                        + "5. url       :when adding a tag, URL is the content of an image tag\n"
                        + "```");
                try {
                    messageBuilder.send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (saveArray.length == 2 && saveArray[1].toLowerCase().equals("tags")) {
                messageBuilder.appendContent("TAGS: " + TAG.size() + "\n```");
                TAG.forEach((String a, String b) -> {
                    //System.out.printf("%s, %s\n", a, b);
                    messageBuilder.appendContent(String.format("%s ", a));
                });
                messageBuilder.appendContent("```");
                try {
                    messageBuilder.send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Printing out tags
            if (saveArray.length > 2) {
                //Printing out image tags
                if (TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("imageJPG")) {
                    File file = new File("src/tagData/images/" + tagName.toLowerCase() + ".jpg");
                    try{event.getMessage().getChannel().sendFile(file);}catch(MissingPermissionsException e){} catch (IOException | RateLimitException | DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("imagePNG")) {
                    File file = new File("src/tagData/images/" + tagName.toLowerCase() + ".png");
                    try{event.getMessage().getChannel().sendFile(file);}catch(MissingPermissionsException e){} catch (IOException | RateLimitException | DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //Printing out gif tags
                else if (TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("gif")) {
                    File file = new File("src/tagData/gifs/" + saveArray[2].toLowerCase() + ".gif");
                    try{event.getMessage().getChannel().sendFile(file);}catch(MissingPermissionsException e){} catch (IOException | RateLimitException | DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //ADDING a tag
            //@SirBroBot(mention) tag <name> <action> <url>
            if (saveArray.length == 5 && action.toLowerCase().equals("add")) {
                if (TAG.containsKey(tagName)) {
                    try {
                        message.reply("A tag exists with this name already. Your unoriginallity amazes me.");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    String tagAuthor = message.getAuthor().getName();

                    if (url.toLowerCase().endsWith(".gif")) {
                        try {
                            fileIO.saveImage(url, tagName + ".gif", "src/tagData/gifs/");
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        TAG.put(tagName, "gif");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: GIF) added!");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (url.toLowerCase().endsWith(".png")) {
                        try {
                            fileIO.saveImage(url, tagName + ".png", "src/tagData/images/");
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        TAG.put(tagName, "imagePNG");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: IMAGE) added");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (url.toLowerCase().endsWith(".jpg")) {

                        try {
                            //                        String extension = url.substring(url.lastIndexOf("."));
//                        fileIO.saveImage(url, tagName + "."+extension, "src/tagData/images/");
fileIO.saveImage(url, tagName + ".jpg", "src/tagData/images/");
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        TAG.put(tagName, "imageJPG");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: IMAGE) added");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                //REMOVING a tag
            } else if (saveArray.length == 4 && action.toLowerCase().equals("remove") && TAG.containsKey(tagName)) {
                Path path = null;

                fileIO.saveHash(TAG, "TAG");

                if (TAG.get(tagName).equals("imagePNG")) {
                    path = Paths.get("E:/Andrew/Documents/NetBeansProjects/SirBroBot1.0.1/src/tagData/images/" + tagName + ".png");
                } else if (TAG.get(tagName).equals("imageJPG")) {
                    path = Paths.get("E:/Andrew/Documents/NetBeansProjects/SirBroBot1.0.1/src/tagData/images/" + tagName + ".jpg");
                }
                if (TAG.get(tagName).equals("gif")) {
                    path = Paths.get("E:/Andrew/Documents/NetBeansProjects/SirBroBot1.0.1/src/tagData/gifs/" + tagName + ".gif");
                }

                try {

                    Files.delete(path);
                } catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n", path);
                } catch (DirectoryNotEmptyException x) {
                    System.err.format("%s not empty%n", path);
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                }
                TAG.remove(tagName.toLowerCase());
                try {
                    event.getMessage().getChannel().sendMessage("Tag removed!");
                } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                    java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    /**
     * @param event
     * @throws IOException
     * @throws DiscordException
     * @throws MissingPermissionsException
     */
    int messagesSeen = 0;

    @EventSubscriber
    @SuppressWarnings("SleepWhileInLoop")
    public void onMessageReceived(MessageReceivedEvent event) throws IOException, DiscordException, TwitterException, MissingPermissionsException, Exception {

        
        IUser root = sirbrobot.client.getUserByID(tokens.rootID());
        IChannel updateChannel = sirbrobot.client.getChannelByID("197567480439373824");

        messagesSeen++;

        IMessage message = event.getMessage();
        MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
        messageBuilder.withChannel(event.getMessage().getChannel());
        String Mcontent = message.getContent().toLowerCase().trim();
        boolean serverOwner = false;

        //Determine Server owner status
        if (Mcontent.contains("who is root")) {
            sendMessage(root.mention() + " is root");
            
            //message.reply(root.getID()+" is root");
        }

        if (message.getChannel().isPrivate()) {
            if (message.getAuthor() != root && messageStatus == true) {
                sirbrobot.client.getOrCreatePMChannel(root).sendMessage(message.getAuthor().getName() + ": " + message.getContent());
            }

            if (Mcontent.contains("invite")) {
                sirbrobot.client.getOrCreatePMChannel(message.getAuthor()).sendMessage("Invite me to a server with https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=473168957");
            }
            
            else if(Mcontent.equals("?commands")){
                 sirbrobot.client.getOrCreatePMChannel(message.getAuthor()).sendMessage(commands.commandListNormal);
            }
            else if(Mcontent.equals("?ocommands")){
                 sirbrobot.client.getOrCreatePMChannel(message.getAuthor()).sendMessage("These must be used in a server by the SERVER OWNER. **They wont work in this PM**\n"+commands.commandListOwner);
            }
            else if(Mcontent.equals("?mcommands")){
                 sirbrobot.client.getOrCreatePMChannel(message.getAuthor()).sendMessage("These must be used in a server. **They wont work in this PM**\n"+commands.mcommands);
            }
            
            else if (Mcontent.contains("commands") | Mcontent.contains("help")) {

                IPrivateChannel person = sirbrobot.client.getOrCreatePMChannel(message.getAuthor());
                person.toggleTypingStatus();

                IMessage tempmessage = person.sendMessage("Determining Owner status.");

                try {Thread.sleep(2100);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

                int placeholder = 0;
                for (int i = 0; i < sirbrobot.client.getGuilds().size(); i++) {
                    if (sirbrobot.client.getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
                        serverOwner = true;
                        placeholder = i;
                    }
                }

                if (serverOwner == true) {
                    tempmessage.edit("Hello **" + sirbrobot.client.getGuilds().get(placeholder).getName() + "** server owner! \n"
                            + "Fetching commands");
                    try {
                        Thread.sleep(2100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.commandListOwner);

                }

                if (serverOwner == false) {
                    tempmessage.edit("I am not on a server you own. I will fetch Normal Use commands. To add me go here: https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=8 ");
                    person.sendMessage(commands.commandListNormal);
                }
            } 
            
            else if (Mcontent.startsWith("?broadcaston")) {
                for (int i = 0; i < sirbrobot.client.getGuilds().size(); i++) {
                    if (sirbrobot.client.getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
                        serverOwner = true;
                    }
                }
                if (PMD.containsKey(message.getAuthor().getID())) {
                    PMD.replace(message.getAuthor().getID(), "ON");
                    fileIO.saveHash(PMD, "PMD");
                    updateChannel.sendMessage("Broadcast turned ON for Owner **" + message.getAuthor().getName() + "**");
                    message.reply("Broadcast recieve status set to **TRUE**");
                } else {
                    PMD.put(message.getAuthor().getID(), "ON");
                    fileIO.saveHash(PMD, "PMD");
                    updateChannel.sendMessage("Broadcast turned ON for Owner **" + message.getAuthor().getName() + "**");
                    message.reply("Broadcast recieve status set to **TRUE**");
                }
            } else if (Mcontent.startsWith("?broadcastoff")) {
                if (PMD.containsKey(message.getAuthor().getID())) {
                    PMD.replace(message.getAuthor().getID(), "OFF");
                    fileIO.saveHash(PMD, "PMD");
                    updateChannel.sendMessage("Broadcast turned OFF for Owner **" + message.getAuthor().getName() + "**");
                    message.reply("Broadcast recieve status set to **FALSE**");
                } else {
                    PMD.put(message.getAuthor().getID(), "OFF");
                    fileIO.saveHash(PMD, "PMD");
                    updateChannel.sendMessage("Broadcast turned OFF for Owner **" + message.getAuthor().getName() + "**");
                    message.reply("Broadcast recieve status set to **FALSE**");
                }
            } else if (message.getContent().startsWith("?") || message.getContent().startsWith(">")) {
                IPrivateChannel person = sirbrobot.client.getOrCreatePMChannel(message.getAuthor());
                RequestBuffer.request(() -> {
                    try {
                        person.sendMessage("I dont support commands VIA pm, but to see my commands say ?commands");
                    } catch (MissingPermissionsException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DiscordException ex) {
                        java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } //Must be public channel
        //COMMANDS begin with ?
        
        else if (message.getContent().startsWith("?")) {

//ONLY BROMANDUDEGUYPHD (Bot Creator) Can cast these for obvious reasons, it shuts the bot down
            if (message.getAuthor().equals(root)) {

                if (message.getContent().contains("?quit")) {
                    messageBuilder.withContent(String.format("GoodBye!"));
                    messageBuilder.build();
                    sirbrobot.client.logout();
                    sirbrobot.skype.logout();

                    fileIO.save(serverIconNames, "ServerIconUrls.txt", "src/dataDocuments");
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");
                    fileIO.saveHash(TAG, "TAG");
                    fileIO.saveHash(PMD, "PMD");

                    Path pathSource = null;
                    Path pathDestionation = null;

//                    for (int i = 0; i < songsThisSession.size(); i++) {
//                        pathSource = Paths.get("E:/Andrew/Documents/NetBeansProjects/SirBroBot1.0.1/" + songsThisSession.get(i) + ".webm");
//                        pathDestionation = Paths.get("E:/Andrew/Documents/NetBeansProjects/SirBroBot1.0.1/src/songs/downloaded/" + songsThisSession.get(i) + ".webm");
//                        try {
//                            Files.move(pathSource, pathDestionation);
//                        } catch (NoSuchFileException x) {
//                            System.err.format("%s: no such" + " file or directory%n", pathSource);
//                        } catch (DirectoryNotEmptyException x) {
//                            System.err.format("%s not empty%n", pathSource);
//                        } catch (IOException x) {
//                            // File permission problems are caught here.
//                            System.err.println(x);
//                        }
//                    }

                    System.exit(0);

                } else if (Mcontent.equals("?reboot")) {
                    fileIO.save(serverIconNames, "ServerIconUrls.txt", "src/dataDocuments");
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");
                    fileIO.saveHash(TAG, "TAG");
                    fileIO.saveHash(PMD, "PMD");
                    IMessage rebootMessage = event.getMessage().getChannel().sendMessage("I am rebooting");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    sirbrobot.client.logout();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    sirbrobot.boot();
                    usageCounter++;

//        try {
//            fileIO.readFile(curseWords, "bannedWords.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
                    try {
                        fileIO.readFile(serverIconNames, "src/dataDocuments/serverIconUrls.txt");
                    } catch (IOException ex) {
                        LOG.warn("Unable to load serverIconNames");
                    }


                    fileIO.readHash(VAD, "VAD");
                    fileIO.readHash(VADdata, "VADdata");
                    fileIO.readHash(WAD, "WAD");
                    fileIO.readHash(WADdata, "WADdata");
                    fileIO.readHash(TAG, "TAG");
                    fileIO.readHash(LAD, "LAD");
                    fileIO.readHash(LADdata, "LADdata");
                    fileIO.readHash(PMD, "PMD");

                    rebootMessage.edit("I hath returned!");
                } else if (Mcontent.startsWith("?say ")) {

                    try {
                        message.delete();
                    } catch (MissingPermissionsException e) {
                    };
                    messageBuilder.withContent(String.format(message.getContent().replace("?say ", "")));
                    messageBuilder.build();
                    usageCounter++;

                } else if (Mcontent.startsWith("?guildid")) {
                    updateChannel.sendMessage("**Guild** " + message.getGuild().getName() + " **ID:** " + message.getGuild().getID());
                    usageCounter++;
                } else if (Mcontent.startsWith("?channelid")) {
                    updateChannel.sendMessage("**Guild** " + message.getGuild().getName() + " **Channel** " + message.getChannel().getName() + " **ID:** " + message.getChannel().getID());
                    usageCounter++;
                } else if (message.getContent().startsWith("?getID ")) {
                    message.delete();
                    IUser user;
                    user = message.getMentions().get(0);
                    messageBuilder.withContent(user.getName() + "'s IS is: " + user.getID());
                    messageBuilder.build();
                    usageCounter++;
                } //Broadcasts message to server owners
                else if (Mcontent.startsWith("?broadcast ")) {
                    System.out.print("Broadcast Request Recieved");
                    String broadcastContent = message.getContent().replace("?broadcast ", "");
                    message.delete();
                    int servers = sirbrobot.client.getGuilds().size();

                    for (int i = 0; i < servers; i++) {
                        IUser user = sirbrobot.client.getGuilds().get(i).getOwner();
                        boolean broadcastStatus = true;
                        if (sirbrobot.client.getGuilds().get(i).getID().equals("153315968091684865") | sirbrobot.client.getGuilds().get(i).getID().equals("81384788765712384") | sirbrobot.client.getGuilds().get(i).getID().equals("110373943822540800")) {
                            broadcastStatus = false;
                        }

                        if (PMD.containsKey(user.getID())) {
                            if (PMD.get(user.getID()).equals("ON")) {
                                broadcastStatus = true;
                            }
                            if (PMD.get(user.getID()).equals("OFF")) {
                                broadcastStatus = false;
                            }
                        }

                        if (sirbrobot.client.getGuilds().get(i).getID() == null) {
                            broadcastStatus = false;
                            System.out.println("Error found on: " + i);
                        }

                        if (broadcastStatus == true) {
                            RequestBuffer.request(() -> {
                                try {
                                    sirbrobot.client.getOrCreatePMChannel(user).sendMessage("Hello " + user.getName() + "! " + "My creator has issued a broadcast: \n\n **BROADCASTED MESSAGE:**\n" + broadcastContent);

                                } catch (DiscordException e) {
                                    e.printStackTrace();

                                } catch (MissingPermissionsException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            });

                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }

                            event.getMessage().getChannel().sendMessage(i + " ::Broadcast sent to: " + sirbrobot.client.getGuilds().get(i).getName());
                        }

                        if (broadcastStatus == false) {
                            if (sirbrobot.client.getGuilds().get(i).getID().equals("153315968091684865") | sirbrobot.client.getGuilds().get(i).getID().equals("81384788765712384") | sirbrobot.client.getGuilds().get(i).getID().equals("110373943822540800")) {
                                event.getMessage().getChannel().sendMessage(i + " ::Broadcast **EXPECTED FAILURE** to: " + sirbrobot.client.getGuilds().get(i).getName());
                            } else {
                                event.getMessage().getChannel().sendMessage(i + " ::Broadcast **FAILED** to: " + sirbrobot.client.getGuilds().get(i).getName());
                            }

                            if (PMD.containsKey(user.getID())) {
                                if (PMD.get(user.getID()).equals("OFF")) {
                                    event.getMessage().getChannel().sendMessage("BROADCAST DENIED:: Server: " + sirbrobot.client.getGuilds().get(i).getName() + "Owner: " + user.getName());
                                }
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }

                        }
                    }

                } else if (Mcontent.startsWith("?specificbroadcast")) {

                    int servers = sirbrobot.client.getGuilds().size();
                    int owner2Broadcast;
                    String[] ownerToBroadcast = message.getContent().split(":");

                    char[] toCharArray = ownerToBroadcast[1].toCharArray();

                    owner2Broadcast = Character.getNumericValue(toCharArray[0]);

                    if (toCharArray.length > 1) {

                        int roleToAddSecond = Character.getNumericValue(toCharArray[1]);
                        owner2Broadcast = 10 + roleToAddSecond;
                        System.out.println(Character.getNumericValue(toCharArray[1]));
                        System.out.println(ownerToBroadcast[1]);
                    }

                    for (int i = 0; i < servers; i++) {
                        if (i == owner2Broadcast) {
                            IUser user = sirbrobot.client.getGuilds().get(i).getOwner();
                            sirbrobot.client.getOrCreatePMChannel(user).sendMessage("Hello " + user.getName() + "! "
                                    + "My creator has issued a broadcast: \n\n"
                                    + "**BROADCASTED MESSAGE:**\n"
                                    + "    Hello! Thank you for having SirBroBot on your server!.\n"
                                    + "    As the server owner, there are special commands you have access to.\n"
                                    + "    If you wish to not recieve these please send me a direct message BroManDudeGuyPhD#5846 and I will remove you.\n\n"
                                    + "    Thank you for adding SirBroBot, and thank you for your patience!"
                                    + "    Report bugs and stay connected to the project at https://discord.gg/0wCCISzMcKOD7udN"
                                    + "    I also made this page to keep users updated a little more formally than PMs: http://bootswithdefer.tumblr.com/sirbrobot");
                        }

                    }

                    IUser user = sirbrobot.client.getGuilds().get(1).getOwner();
                    message.delete();

                } else if (Mcontent.equals("?tweetabout")) {

                    twitter.updateStatus(
                            "Discord Servers: " + sirbrobot.client.getGuilds().size() + "\n"
                            + "Voice Channels: " + client.getVoiceChannels().size() + "\n"
                            + "Text Channels: " + client.getChannels(false).size() + "\n"
                            + "Total Users: " + getUsers() + "\n"
                            + "Messages Seen: " + messagesSeen + "\n"
                            + "Uptime: " + getUptime() + "\n");
                    event.getMessage().getChannel().sendMessage("Server stats sent!");
                    usageCounter++;

                } else if (Mcontent.startsWith("?tweet ")) {

                    String[] tweetToSend = message.getContent().split(":");
                    Status status = twitter.updateStatus(tweetToSend[1]);
                    event.getMessage().getChannel().sendMessage("Tweet sent!");
                    twitterID = status.getId();

                    usageCounter++;
                } else if (Mcontent.startsWith("?deletetweet")) {
                    if (twitterID == 0) {
                        message.reply("I dont have an ID to delete");
                    } else {
                        twitter.destroyStatus(twitterID);
                        event.getMessage().getChannel().sendMessage("Tweet deleted!");
                    }

                    usageCounter++;
                } else if (Mcontent.equals("?users")) {
                    getUsers();
                    ArrayList<String> temp = new ArrayList<>();
                    int counter = 0;

                    for (int i = 0; i < sirbrobot.client.getGuilds().size(); i++) {
                        temp.add("\n" + i + ") " + userInfo.get(counter) + ": " + userInfo.get(counter + 1));
                        counter++;
                        counter++;
                    }
                    message.reply("Users and servers I see: \n" + temp.toString());

                    usageCounter++;
                } else if (Mcontent.equals("?postdata")) {
                    PostingHTMLData post = new PostingHTMLData();
                    post.sendReq(sirbrobot.client.getGuilds().size());
                    //post.sendReq("38");
                }
                
                else if (Mcontent.equals("?togglemessages")){
                    if(messageStatus == true){
                        messageStatus = false;
                        message.reply("Messages OFF");
                    }
                    else if(messageStatus == false){
                        messageStatus = true;
                        message.reply("Messages ON");
                    }
                }
                
                else if(Mcontent.equals("?toggleupdates")){
                    if(updateDispatcher == true){
                        updateDispatcher = false;
                        message.reply("Updates OFF");
                    }
                    else if (updateDispatcher == false){
                        updateDispatcher = true;
                        message.reply("Updates ON");
                    }
                }
                
                else if(Mcontent.contains("?guildwithchannel")){
                    String channelName = message.getContent().replace("?guildwithchannnel", "");
                    for(int i = 0; i < sirbrobot.client.getGuilds().size(); i++){
                        for(int j = 0; j < sirbrobot.client.getGuilds().get(i).getChannels().size(); j++){
                            if(sirbrobot.client.getGuilds().get(i).getChannels().get(j).getName().equals(channelName)){
                                message.reply(sirbrobot.client.getGuilds().get(i).getName());
                            }
                        }
                    }
                }
                
                else if(Mcontent.contains("?leaveguild")){
                    String guildName = message.getContent().replace("?leaveguild", "");
                    for(int i = 0; i < sirbrobot.client.getGuilds().size(); i++){
                        if(sirbrobot.client.getGuilds().get(i).getName().equals(guildName)){
                            sirbrobot.client.getGuildByID(sirbrobot.client.getGuilds().get(i).getID()).leaveGuild();
                        }
                            
                    }
                }
                
                else if(Mcontent.equals("?purge")){
                    FileChecker.purge();
                }
            }

//OWNER ONLY COMMANDS
            if (message.getAuthor().getID().equals(message.getGuild().getOwnerID())) {

                //Returns text file with roles
                if (message.getContent().equals("?rolestxt")) {
                    int guildRoleAmount = message.getGuild().getRoles().size();
                    List<IRole> serverRoles = message.getGuild().getRoles();
                    String fileName = "rolesOnServer-" + message.getGuild().getName() + ".txt";

                    for (int i = 0; i < guildRoleAmount; i++) {
                        rolesOnServer.add(i + ". " + serverRoles.get(i).getName());
                    }

                    File rolesOnServerFile = new File("src/dataDocuments" + fileName);
                    fileIO.save(rolesOnServer, fileName, "src/dataDocuments");

                    try {
                        fileIO.readFile(curseWords, fileName);
                    } catch (IOException ex) {
                        LOG.warn("Unable to load cureWords file");
                    }
                    event.getMessage().getChannel().sendFile(rolesOnServerFile);

                    rolesOnServer.clear();

                    usageCounter++;
                } //ADDS a role. Syntax is ?setrole[space]role#[space] Username
                else if (Mcontent.startsWith("?roles")) {
                    int guildRoleAmount = message.getGuild().getRoles().size();
                    List<IRole> serverRoles = message.getGuild().getRoles();
                    for (int i = 0; i < guildRoleAmount; i++) {
                        if (i == 0) {
                            rolesOnServer.add(i + ". everyone");
                        } else {
                            rolesOnServer.add(i + ". " + serverRoles.get(i).getName() + " ");
                        }
                    }
                    message.reply(rolesOnServer.toString());
                    rolesOnServer.clear();

                    usageCounter++;
                } else if (message.getContent().startsWith("?setrole")) {
                    try {
                        message.delete();
                    } catch (MissingPermissionsException e) {
                    }
                    
                    
                    char role = 0;
                   
                    String[] rolesArray = message.getContent().split(" ");
                    for(int i = 0; i < rolesArray[1].length(); i++){
                        role += rolesArray[1].charAt(i);
                    }

                    //1. Admin , 2. Moderator , 3. Owner , 4. League people , 5. Bot Commander , 6. In Timeout Corner , 7. Member, 8. Bot Overlord
                    int roleToAdd = Character.getNumericValue(role);

                    IUser user;
                    user = message.getMentions().get(0);

                    IRole[] roles = new IRole[2];
                    IRole role1 = message.getGuild().getEveryoneRole();
                    IRole role2 = message.getGuild().getRoles().get(roleToAdd);

                    roles[0] = role1;
                    roles[1] = role2;

                    message.getGuild().editUserRoles(user, roles);

                    messageBuilder.withContent("Operation successful: " + message.getGuild().getRoles().get(roleToAdd) + " role assigned to " + message.getMentions().get(0));
                    messageBuilder.build();

                    usageCounter++;

                } else if (message.getContent().startsWith("?addrole")) {
                    try {
                        message.delete();
                    } catch (MissingPermissionsException e) {
                    }
                    char role = 0;
                   
                    String[] rolesArray = message.getContent().split(" ");
                    for(int i = 0; i < rolesArray[1].length(); i++){
                        role += rolesArray[1].charAt(i);
                    }
                    
                    int roleToAdd = Character.getNumericValue(role);

                    IUser user;
                    user = message.getMentions().get(0);

                    IRole[] roles = new IRole[user.getRolesForGuild(message.getGuild()).size() + 1];

                    for (int i = 0; i < user.getRolesForGuild(message.getGuild()).size(); i++) {
                        roles[i] = user.getRolesForGuild(message.getGuild()).get(i);

                    }

                    IRole role2 = message.getGuild().getRoles().get(roleToAdd);

                    roles[user.getRolesForGuild(message.getGuild()).size()] = role2;

                    message.getGuild().editUserRoles(user, roles);

                    messageBuilder.withContent("Operation successful: " + message.getGuild().getRoles().get(roleToAdd) + " role added to " + message.getMentions().get(0) + "\n");

                    messageBuilder.build();

                    usageCounter++;

                } //TTS Message Announcements
                else if (Mcontent.startsWith("?vjaon")) {

//                    if(VADdata.get(message.getGuild().getID())!=message.getChannel().getID()){
//                        message.reply("I currently only support announcing in one channel, and I already am set to announce in **"
//                                +message.getGuild().getChannelByID(VADdata.get(message.getGuild().getID()).toString()).getName()+"** ");
//                    }
                    if (Mcontent.equals("?vjaon announce")) {

                        VAD.remove(message.getGuild().getID());
                        VAD.put(message.getGuild().getID(), "true save");
                        message.reply("**Voice Join Announce** has been turned **ON** and will announce in THIS text channel (" + message.getChannel().getName() + "). Announcements will not be deleted");
                    }
                    if (Mcontent.equals("?vjaon")) {
                        VAD.remove(message.getGuild().getID());
                        VAD.put(message.getGuild().getID(), "true");
                        message.reply("**Voice Join Announce** has been turned **ON** and will announce in THIS text channel (" + message.getChannel().getName() + "). Announcements will be deleted");
                    }
                    VADdata.put(message.getGuild().getID(), message.getChannel().getID());
                    System.out.println("VAD set to true in " + message.getGuild().getName());
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    updateChannel.sendMessage("VAD set to **TRUE** in " + message.getGuild().getName());
                    usageCounter++;

                } else if (Mcontent.startsWith("?vjaoff")) {
                    message.reply("**Voice Join Announce** has been turned **OFF** and will no longer announce in this channel");
                    VAD.remove(message.getGuild().getID());
                    VADdata.remove(message.getGuild().getID());
                    System.out.println("VAD set to false in " + message.getGuild().getName());
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    updateChannel.sendMessage("VAD set to **FALSE** in " + message.getGuild().getName());
                    usageCounter++;
                } else if (Mcontent.startsWith("?vlaon")) {

                    if (Mcontent.equals("?vlaon announce")) {
                        LAD.remove(message.getGuild().getID());
                        LAD.put(message.getGuild().getID(), "true save");
                        message.reply("**Voice LEAVE Announce** has been turned **ON** and will announce in this channel (" + message.getChannel().getName() + "). Announcements will not be deleted");
                    }
                    if (Mcontent.equals("?vlaon")) {
                        LAD.remove(message.getGuild().getID());
                        LAD.put(message.getGuild().getID(), "true");
                        message.reply("**Voice LEAVE Announce** has been turned **ON** and will announce in this channel (" + message.getChannel().getName() + "). Anouncements will be deleted");
                    }
                    LADdata.put(message.getGuild().getID(), message.getChannel().getID());
                    System.out.println("LAD set to true in " + message.getGuild().getName());
                    fileIO.saveHash(LAD, "LAD");
                    fileIO.saveHash(LADdata, "LADdata");

                    usageCounter++;
                } else if (Mcontent.equals("?vlaoff")) {
                    message.reply("**Voice LEAVE Announce** has been turned **OFF** and will announce in this channel (" + message.getChannel().getName() + ")");
                    LAD.remove(message.getGuild().getID());
                    LADdata.remove(message.getGuild().getID());
                    System.out.println("LAD set to false in " + message.getGuild().getName());
                    fileIO.saveHash(LAD, "LAD");
                    fileIO.saveHash(LADdata, "LADdata");

                    usageCounter++;
                } else if (Mcontent.startsWith("?welcomeon")) {
                    message.reply("New User welcome message initiated to: \n"
                            + message.getContent().replace("?welcomeon ", ""));
                    WAD.put(message.getGuild().getID(), message.getContent().replace("?welcomeon ", ""));
                    WADdata.put(message.getGuild().getID(), message.getChannel().getID());
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");

                    usageCounter++;
                } else if (Mcontent.startsWith("?welcomeoff")) {
                    message.reply("New User welcome message removed");
                    WAD.remove(message.getGuild().getID());
                    WADdata.remove(message.getGuild().getID());
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");

                    usageCounter++;
                } else if (Mcontent.startsWith("?welcomeedit")) {
                    message.reply("New User welcome message edited");
                    WAD.replace(message.getGuild().getID(), message.getContent().replace("?welcomeedit ", ""));
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");

                    usageCounter++;
                } else if (Mcontent.equals("?welcomeview")) {
                    if (WAD.containsKey(message.getGuild().getID())) {
                        try {
                            event.getMessage().getChannel().sendMessage(WAD.get(message.getGuild().getID()).toString());
                        } catch (NullPointerException e) {
                            message.reply("I dont have a Welcome message stored");
                        }
                    } else {
                        message.reply("Welcome message is currently turned off. Use `?welcomeon Message Content` to activate");
                    }
                } else if (Mcontent.startsWith("?purgechannel")) {
                    message.reply("This command is undergoing maintenance. Trust me, youll thank me -" + root.mention());
//                    try{message.delete();} catch(MissingPermissionsException e){};
//                    int timer = 10;
//                    message.getChannel().toggleTypingStatus();
//                    IChannel purgeChannel = message.getChannel();
//
//                    IMessage tempmessage = messageBuilder.withContent("**CHANNEL PURGE IMMINENT** in **" + timer + "** seconds (" + message.getChannel().getName() + ")").send();
//
//                    boolean purge = true;
//
//                    IUser someDude = message.getAuthor();
//
//                    for (int i = 10; i > -1; i--) {
//                        tempmessage.edit("**CHANNEL PURGE IMMINENT** in **" + timer + "** seconds (" + message.getChannel().getName() + ")");
//                        timer--;
//                        try {
//                            Thread.sleep(1100);                 //1000 milliseconds is one second.
//                        } catch (InterruptedException ex) {
//                            Thread.currentThread().interrupt();
//                        }
//
//                        MessageList history = purgeChannel.getMessages();
//                        List<IMessage> list = history.stream().filter(messageHistory -> messageHistory.getAuthor() == someDude).limit(5).collect(Collectors.toList());
//
//                        if (list.toString().contains("abort")) {
//                            purge = false;
//                        }
//                    }
//
//                    String name = purgeChannel.getName();
//                    String topic = purgeChannel.getTopic();
//                   
//                        if (topic.contains("-")) {
//                            String[] topicText = topic.split("-");
//                            topic = "";
//                            for (int i = 0; i < topicText.length; i++) {
//                                if (topicText[i].contains("Last Purge") == false) {
//                                    topic += topicText[i];
//                                }
//                            }
//                        }
//                    IChannel newChannel = null;
//                    try{
//                    newChannel = message.getGuild().createChannel(name);
//                    newChannel.changeTopic(topic+" - Last Purge: "+String.format(GetCurrentDateTime()));
//                    newChannel.changePosition(purgeChannel.getPosition());
//                    }catch(DiscordException e){
//                        
//                    }catch(MissingPermissionsException e){
//                        message.reply("Check my permissions! I cant do that.");
//                    }
//                    
//                    for(int i = 0; i < message.getGuild().getRoles().size(); i++){
//                            try{
//                                    newChannel.overrideRolePermissions(newChannel.getGuild().getRoles().get(i),
//                                    purgeChannel.getRoleOverrides().get(purgeChannel.getGuild().getRoles().get(i).getID()).allow(),
//                                    purgeChannel.getRoleOverrides().get(purgeChannel.getGuild().getRoles().get(i).getID()).deny());
//                            }catch(NullPointerException e){
////                                System.out.println("No Roles for "+newChannel.getGuild().getRoles().get(i).getName());
////                                System.out.println("Failed for "+purgeChannel.getGuild().getRoles().get(i).getName());
////                                System.out.println("======================================================================\n");
//                            }
//                            catch (MissingPermissionsException p) {
//                            message.reply("Are you kidding? I dont have permission. I need Manage Roles permissions.");
//                        }
//                    }
//                    
//                    
//                    for(int i = 0; i < message.getGuild().getUsers().size(); i++){
//                            try{
//                                    newChannel.overrideUserPermissions(newChannel.getGuild().getUsers().get(i),
//                                    purgeChannel.getUserOverrides().get(purgeChannel.getGuild().getUsers().get(i).getID()).allow(),
//                                    purgeChannel.getUserOverrides().get(purgeChannel.getGuild().getUsers().get(i).getID()).deny());
//
//                            }catch(NullPointerException e){
//                                
//                            }
//                            catch (MissingPermissionsException p) {
//                            message.reply("Are you kidding? I dont have permission. I need Manage Roles permissions.");
//                        }
//                    }
//                    System.out.println(purgeChannel.getName()+" channel purged on "+purgeChannel.getGuild().getName());
//                    sirbrobot.client.getOrCreatePMChannel(purgeChannel.getGuild().getOwner()).sendMessage(purgeChannel.getName()+" channel purged");
//                    
//                    try {
//                            purgeChannel.delete();
//                        } catch (MissingPermissionsException p) {
//                            message.reply("I dont have permission. I need Manage Channels Permission.");
//                        }
//                    
//                    usageCounter++;
                } //                else if (Mcontent.startsWith("?purgeuser")) {
                //                    String[] temp = message.getContent().split(" ");
                //
                //                    IUser purgeUser = message.getMentions().get(0);
                //                    IChannel targetChannel = message.getChannel();
                //                    int timer = 10;
                //                    boolean purge = true;
                //                    IMessage tempmessage = messageBuilder.withContent("**CHANNEL PURGE IMMINENT** in **" + timer + "** seconds (" + message.getChannel().getName() + ")").send();
                //
                //                    for (int i = 10; i > -1; i--) {
                //                        tempmessage.edit("**USER PURGE IMMINENT** in **" + timer + "** seconds (**" + purgeUser.getName() + "**)");
                //                        timer--;
                //                        try {
                //                            Thread.sleep(1100);                 //1000 milliseconds is one second.
                //                        } catch (InterruptedException ex) {
                //                            Thread.currentThread().interrupt();
                //                        }
                //
                //                        MessageList callerHistory = targetChannel.getMessages();
                //                        List<IMessage> list = callerHistory.stream().filter(messageHistory -> messageHistory.getAuthor() == message.getAuthor()).limit(5).collect(Collectors.toList());
                //
                //                        if (list.toString().contains("abort")) {
                //                            purge = false;
                //                        }
                //                    }
                //
                //                    if (purge == true) {
                //                        List<IMessage> messages100 = new ArrayList<>();
                //                        if (temp[2].toLowerCase().equals("all")) {
                //                            MessageList userHistory = targetChannel.getMessages();
                //                            List<IMessage> purgeList = userHistory.stream().filter(messageHistory -> messageHistory.getAuthor() == purgeUser).collect(Collectors.toList());
                //                                
                //                            try {
                //                                while (purgeList.size() > 0) {
                //                                     
                //                                        for (int j = 0; j < 99; j++) {
                //                                            messages100.add(targetChannel.getMessages().get(j));
                //                                        }
                //                                        targetChannel.getMessages().bulkDelete(messages100);
                //                                        messages100.clear();
                //
                //                                        try {
                //                                            Thread.sleep(1100);                 //1000 milliseconds is one second.
                //                                        } catch (InterruptedException ex) {
                //                                            Thread.currentThread().interrupt();
                //                                        }
                //                                        
                //                                    if (purgeList.size() <= 98) {
                //                                        for (int j = 0; j < targetChannel.getMessages().size(); j++) {
                //                                            messages100.add(targetChannel.getMessages().get(j));
                //                                        }
                //
                //                                        targetChannel.getMessages().bulkDelete(messages100);
                //                                        messages100.clear();
                //                                    }
                //                                }
                //                            } catch (MissingPermissionsException p) {
                //                                message.reply("Are you kidding? I dont have permission. I need Manage Messages Permission.");
                //                            }
                //                        } else if(!"all".equals(temp[2].toLowerCase())) {
                //                            char messagesToDelete = 0;
                //                            for (int i = 0; i < temp[2].length(); i++) {
                //                                messagesToDelete = temp[2].charAt(i);
                //                            }
                //                            int messageAmount = Character.getNumericValue(messagesToDelete);
                //
                //                            MessageList userHistory = targetChannel.getMessages();
                //                            List<IMessage> purgeList = userHistory.stream().filter(messageHistory -> messageHistory.getAuthor() == purgeUser).limit(messageAmount).collect(Collectors.toList());
                //
                //                            for (int i = 0; i < purgeList.size(); i++) {
                //                                purgeList.get(i).delete();
                //                            }
                //                        }
                //                    } else {
                //                        tempmessage.edit("User was spared");
                //                    }
                //                }
                else if (Mcontent.startsWith("?broadcaston")) {
                    if (PMD.containsKey(message.getAuthor().getID())) {
                        PMD.replace(message.getAuthor().getID(), "ON");
                        fileIO.saveHash(PMD, "PMD");
                        updateChannel.sendMessage("Broadcast turned ON for Owner **" + message.getAuthor().getName() + "**");
                    } else {
                        PMD.put(message.getAuthor().getID(), "ON");
                        fileIO.saveHash(PMD, "PMD");
                        updateChannel.sendMessage("Broadcast turned ON for Owner **" + message.getAuthor().getName() + "**");
                    }
                } else if (Mcontent.startsWith("?broadcastoff")) {
                    if (PMD.containsKey(message.getAuthor().getID())) {
                        PMD.replace(message.getAuthor().getID(), "OFF");
                        fileIO.saveHash(PMD, "PMD");
                        updateChannel.sendMessage("Broadcast turned OFF for Owner **" + message.getAuthor().getName() + "**");
                    } else {
                        PMD.put(message.getAuthor().getID(), "OFF");
                        fileIO.saveHash(PMD, "PMD");
                        updateChannel.sendMessage("Broadcast turned OFF for Owner **" + message.getAuthor().getName() + "**");
                    }
                }

            } //Admin Commands    
            else if (message.getAuthor().getRolesForGuild(message.getGuild()).toString().contains("Admin")) {
                switch (message.getContent()) {
                    case "?bannedListSize":
                        messageBuilder.withContent(String.format("There are " + curseWords.size() + " banned Words"));
                        message.delete();
                        messageBuilder.build();
                        usageCounter++;
                        break;

                    //Returns list of roles to chat
                    case "?channels":
                        messageBuilder.withContent(String.format("Channels I can see: " + sirbrobot.client.getChannels(false)));
                        messageBuilder.build();
                        usageCounter++;
                        break;

                    default:
                        break;
                }

            }

//General Commands
            //Lists Commands
            if (Mcontent.startsWith("?commands")) {
                //message.getAuthor().getRolesForGuild(message.getGuild()).toString().contains("Admin") || 
                messageBuilder.withContent(String.format(commands.commandListNormal));
                System.out.println("Commands Requested by " + message.getAuthor().getName() + " on " + message.getChannel().getGuild().getName());
                messageBuilder.build();

                usageCounter++;
            } else if (Mcontent.startsWith("?mcommands")) {
                messageBuilder.withContent(String.format(commands.mcommands));
                messageBuilder.build();
            } else if (Mcontent.startsWith("?invite")) {
                sirbrobot.client.getOrCreatePMChannel(message.getAuthor()).sendMessage("Invite me to a server with https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=473168957");
                usageCounter++;
            } else if (Mcontent.equals("?pmcommands")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException e) {
                };
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                boolean serverOwnerPM = false;
                IPrivateChannel person = sirbrobot.client.getOrCreatePMChannel(message.getAuthor());
                person.toggleTypingStatus();

                IMessage tempmessage = person.sendMessage("Determining Owner status...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                

                int placeholder2 = 0;
                for (int i = 0; i < sirbrobot.client.getGuilds().size(); i++) {
                    if (sirbrobot.client.getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
                        serverOwnerPM = true;
                        placeholder2 = i;
                    }
                }

                if (serverOwnerPM == true) {
                    tempmessage.edit("Hello **" + sirbrobot.client.getGuilds().get(placeholder2).getName() + "** server owner! \n"
                            + "Fetching commands");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.commandListOwner);
                    person.sendMessage(commands.mcommands);
                }

                if (serverOwnerPM == false) {
                    tempmessage.edit("I am not on a server you own. I will fetch Normal Use commands. To add me go here: https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=8 ");
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.mcommands);
                }

                System.out.println("Commands Requested VIA PM by " + message.getAuthor().getName() + " on " + message.getChannel().getGuild().getName());

                usageCounter++;
            } else if (Mcontent.startsWith("?ocommands")) {
                if (message.getAuthor().getID().equals(message.getGuild().getOwner().getID())) {
                    message.getChannel().sendMessage(commands.commandListOwner);
                }

                if (message.getAuthor().getID() != message.getGuild().getOwner().getID()) {
                    message.reply("You arent the server Owner of **" + message.getGuild().getName() + "**, " + message.getGuild().getOwner().getName() + " is. \n"
                            + "But here are the commands anyway. **THEY WONT WORK HERE**");
                    message.getChannel().sendMessage(commands.commandListOwner);
                }

                usageCounter++;
            } else if (Mcontent.toLowerCase().equals("?usecounter")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException e) {
                };
                messageBuilder.withContent(String.format("I've issued " + usageCounter + " commands since my last upgrade."));
                messageBuilder.build();
                usageCounter++;
            } else if (Mcontent.equals("?uptime")) {
                messageBuilder.withContent(getUptime());
                messageBuilder.build();
                usageCounter++;
            } else if (Mcontent.equals("?servers")) {

                messageBuilder.withContent(String.format("I am currently the Knight of " + sirbrobot.client.getGuilds().size() + " servers\n"));
                messageBuilder.build();
                usageCounter++;
            } else if (Mcontent.equals("?serverinfo")) {

                IGuild guildID = message.getGuild();
                String guildName = guildID.getName();
                String iconUrl = guildID.getIconURL();
                boolean iconSaveStatus = false;

                if (iconUrl.contains("/null.jpg")) {
                    File serverIcon = new File("src/images/serverIcons/null.jpg");
                    event.getMessage().getChannel().sendFile(serverIcon);
                    event.getMessage().getChannel().sendMessage(String.format("\n```" + "\nServer Name: " + guildName + "\n"
                            + "Owner: " + message.getGuild().getOwner().getName() + "\n"
                            + "Creation Date: " + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                            + " "
                            + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n"
                            + "Members: " + guildID.getUsers().size() + "\n"
                            + "Region: " + guildID.getRegion().getName() + "\n"
                            + "-------------------------------------------------------------\n"
                            + "```"));
                    System.out.println("Server " + message.getGuild().getName() + " has no Icon, provided one");

                    usageCounter++;
                } else {
                    System.out.println("Server Icon Saved for " + message.getGuild().getName());
                    File serverIcon = new File("src/images/serverIcons/" + guildName + ".jpg");
                    fileIO.saveImage(iconUrl, guildName + ".jpg", "src/images/serverIcons/");
                    event.getMessage().getChannel().sendFile(serverIcon);

                    event.getMessage().getChannel().sendMessage(String.format("\n```" + "\nServer Name: " + guildName + "\n"
                            + "Owner: " + message.getGuild().getOwner().getName() + "\n"
                            + "Creation Date: " + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                            + " "
                            + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n"
                            + "Members: " + guildID.getUsers().size() + "\n"
                            + "Region: " + guildID.getRegion().getName() + "\n"
                            + "-------------------------------------------------------------\n"
                            + "```"));
                    fileIO.saveImage(iconUrl, guildName, "src/images/serverIcons/");
                    usageCounter++;
                }
            } else if (Mcontent.equals("?about")) {
                messageBuilder.withContent("```"
                        + "About SirBroBot\n"
                        + "----------------\n"
                        + "Servers: " + sirbrobot.client.getGuilds().size() + "\n"
                        + "Voice Channels: " + client.getVoiceChannels().size() + "\n"
                        + "Text Channels: " + client.getChannels(false).size() + "\n"
                        + "Total Users: " + getUsers() + "\n"
                        + "Messages Seen: " + messagesSeen + "\n"
                        + "Uptime: " + getUptime() + "\n"
                        + "My server (Join me!): https://discord.gg/0wCCISzMcKMkfX88 \n"
                        + "----------------\n"
                        + "Programmer: BroManDudeGuyPhD#5846 \n"
                        + "```"
                        + "Twitter account: <https://twitter.com/SirBroBotThe1st>\n"
                        + "Website: http://bootswithdefer.tumblr.com/sirbrobot\n");

                messageBuilder.build();
                usageCounter++;
            } else if (Mcontent.startsWith("?tsearch ")) {
                try {
                    String[] userToSearch = message.getContent().trim().split(":");
                    boolean iconSaveStatus = false;

                    String iconUrl = twitter.users().showUser(userToSearch[1].trim()).getBiggerProfileImageURL();

                    //twitterProfiles.add(twitter.users().showUser(userToSearch[1]).getName());
                    File serverIcon = new File("src/images/twitterIcons/" + twitter.users().showUser(userToSearch[1]).getName().trim().replace(" ", "").replace(":", "") + ".jpg");
                    fileIO.saveImage(iconUrl, twitter.users().showUser(userToSearch[1]).getName().trim().replace(" ", "").replace(":", "") + ".jpg", "src/images/twitterIcons/");

                    event.getMessage().getChannel().sendFile(serverIcon);

                    usageCounter++;

                    messageBuilder.withContent(userToSearch[1] + "'s info: \n"
                            + "```Name: " + twitter.users().showUser(userToSearch[1]).getName() + "\n"
                            + "Screen Name: " + twitter.users().showUser(userToSearch[1]).getScreenName() + "\n"
                            + "Twitter ID: " + twitter.users().showUser(userToSearch[1]).getId() + "\n"
                            + "Location: " + twitter.users().showUser(userToSearch[1]).getLocation() + "\n"
                            + "Followers: " + twitter.users().showUser(userToSearch[1]).getFollowersCount() + "\n"
                            + "Following: " + twitter.users().showUser(userToSearch[1]).getFriendsCount() + "\n"
                            + "Tweets: " + twitter.users().showUser(userToSearch[1]).getStatusesCount() + "\n"
                            + "```"
                    ).send();
                } catch (TwitterException ex) {

                    message.reply("User not found! Try again");
                }
            } //Returns Authors token in chat USE CAREFULLY
            else if (Mcontent.equals("?myinfo")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException e) {
                };
                sirbrobot.client.getOrCreatePMChannel(message.getAuthor())
                        .sendMessage("Info for user " + message.getAuthor().getName() + " #" + message.getAuthor().getDiscriminator() + "\n"
                                + "ID: " + message.getAuthor().getID() + "\n"
                                + "Account created: " + message.getAuthor().getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Connected Voice Channel: " + message.getAuthor().getClient().getLaunchTime() + "/n```"
                                + "IconURL: " + message.getAuthor().getAvatarURL());

                usageCounter++;

            } else if (Mcontent.startsWith("?whois")) {
                IUser who = message.getMentions().get(0);
                String rolesForWho = "";
                String onlineStatus = "";
                String game = null;

                for (int i = 0; i < +who.getRolesForGuild(message.getGuild()).size(); i++) {
                    rolesForWho += who.getRolesForGuild(message.getGuild()).get(i).getName() + ", ";
                }

                if (who.getStatus().isEmpty()!= true) {
                    game = who.getStatus().toString();
                }

                if (who.getStatus().isEmpty() == true) {
                    game = "None";
                }

                if (who.getAvatarURL().contains("/null.jpg")) {
                    try {
                        File discordUserIconSingle = new File("src/images/discordUserIcons/null.jpg");

                        event.getMessage().getChannel().sendFile(discordUserIconSingle);
                        messageBuilder.withContent(
                                "```"
                                + "Name: " + who.getName()
                                + " #" + who.getDiscriminator() + "\n"
                                + "Roles: " + rolesForWho + "\n"
                                + "Account created: " + who.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Currently playing: " + game + "\n"
                                + "Presence: " + who.getPresence() + "\n"
                                + "```"
                        ).send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        fileIO.saveImage(who.getAvatarURL(), who.getName() + ".jpg", "src/images/discordUserIcons/");
                        File discordUserIconSingle = new File("src/images/discordUserIcons/" + who.getName() + ".jpg");

                        event.getMessage().getChannel().sendFile(discordUserIconSingle);
                        messageBuilder.withContent(
                                "```"
                                + "Name: " + who.getName()
                                + " #" + who.getDiscriminator() + "\n"
                                + "Roles: " + rolesForWho + "\n"
                                + "Account created: " + who.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Currently playing: " + game + "\n"
                                + "Presence: " + who.getPresence() + "\n"
                                + "```"
                        ).send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
              usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?img-mirror")) {

                if (message.getAttachments().size() > 0) {
                    IMessage update = messageBuilder.withContent("Processing image...").send();
                    try {
                        fileIO.saveImage(message.getAttachments().get(0).getUrl(), message.getAttachments().get(0).getFilename(), "src/images/imageWriting/imagesToManipulate/");
                        MirrorImage.setImage(message.getAttachments().get(0).getFilename());
                        MirrorImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                        };
                        File reversedImage = new File("src/images/imageWriting/reversedImages/reversed" + message.getAttachments().get(0).getFilename());
                        event.getMessage().getChannel().sendFile(reversedImage);
                    } catch (IOException  | DiscordException e) {
                        e.printStackTrace();
                        message.reply("You must attach an image to your message! I cant reverse 'nothing'");
                    }
                    update.edit("DONE!");
                }

                if (message.getContent().contains("http")) {
                    IMessage update = messageBuilder.withContent("Processing link...").send();
                    String url = message.getContent().replace("?img-mirror", "");
                    try {
                        fileIO.saveImage(url, "imagetomanipulate.jpg", "src/images/imageWriting/imagesToManipulate/");
                        MirrorImage.setImage("imagetomanipulate.jpg");
                        MirrorImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                        };
                        File reversedImage = new File("src/images/imageWriting/reversedImages/reversed" + "imagetomanipulate.jpg");
                        event.getMessage().getChannel().sendFile(reversedImage);
                    } catch (IOException  | DiscordException e) {
                        e.printStackTrace();

                    }
                    update.edit("DONE!");
                }
                usageCounter++;
            } else if (Mcontent.startsWith("?img-negative")) {
                if (message.getAttachments().size() > 0) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing image...");

                    try {
                        fileIO.saveImage(message.getAttachments().get(0).getUrl(), message.getAttachments().get(0).getFilename(), "src/images/imageWriting/imagesToManipulate/");
                        NegativeImage.setImage(message.getAttachments().get(0).getFilename());
                        NegativeImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                        }
                        File negativeImage = new File("src/images/imageWriting/negativeImages/negative" + message.getAttachments().get(0).getFilename());
                        event.getMessage().getChannel().sendFile(negativeImage);
                    } catch (IOException | DiscordException e) {
                        e.printStackTrace();
                        message.reply("You must attach an image to your message! I cant reverse 'nothing'");
                    }
                    update.edit("DONE!");
                }

                if (message.getContent().contains("http")) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing link...");

                    String url = message.getContent().replace("?img-negative", "");
                    try {
                        fileIO.saveImage(url, "imagetomanipulate.jpg", "src/images/imageWriting/imagesToManipulate/");
                        NegativeImage.setImage("imagetomanipulate.jpg");
                        NegativeImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                        };
                        File reversedImage = new File("src/images/imageWriting/negativeImages/negative" + "imagetomanipulate.jpg");
                        event.getMessage().getChannel().sendFile(reversedImage);

                    } catch (IOException  | DiscordException e) {
                        e.printStackTrace();

                    }
                    update.edit("DONE!");
                }
                usageCounter++;
            } else if (Mcontent.startsWith("?img-id ")) {

                if (Mcontent.contains("http")) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing image...");

                    String results = HTMLUnit.submittingForm(message.getContent().replace("?img-id", ""));

                    if (results.contains("I think this may be inappropriate content so I won't show it")) {
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                            event.getMessage().getChannel().sendMessage("Well I tried and failed to delete your message...");
                        }
                    }

                    update.edit(results);
                }
                usageCounter++;
            } else if (Mcontent.startsWith("?sitetohtml")) {
                String url = message.getContent().replace("?sitetohtml", "");
                TextParser.html(url);
                File file = new File("src/dataDocuments/HTMLfiles/" + url.replace(":", "").replace("/", ".") + ".html");
                event.getMessage().getChannel().sendFile(file);
                usageCounter++;
            } else if (Mcontent.startsWith("?google")) {
                GoogleSearch.run(message.getContent().replace("?google", ""));
                event.getMessage().getChannel().sendMessage(GoogleSearch.returnResults());
                GoogleSearch.clearSearch();
            } else if (Mcontent.startsWith("?d20")) {
                int random = (int) (Math.random() * 20 + 1);
                String response = null;
                if (random == 0) {
                    response = ":zero:";
                }
                if (random == 1) {
                    response = ":one:";
                }
                if (random == 2) {
                    response = ":two:";
                }
                if (random == 3) {
                    response = ":three:";
                }
                if (random == 4) {
                    response = ":four:";
                }
                if (random == 5) {
                    response = ":five:";
                }
                if (random == 6) {
                    response = ":six:";
                }
                if (random == 7) {
                    response = ":seven:";
                }
                if (random == 8) {
                    response = ":eight:";
                }
                if (random == 9) {
                    response = ":nine:";
                }
                if (random == 10) {
                    response = ":ten:";
                }
                if (random == 11) {
                    response = ":one::one:";
                }
                if (random == 12) {
                    response = ":one::two:";
                }
                if (random == 13) {
                    response = ":one::three:";
                }
                if (random == 14) {
                    response = ":one::four:";
                }
                if (random == 15) {
                    response = ":one::five:";
                }
                if (random == 16) {
                    response = ":one::six:";
                }
                if (random == 17) {
                    response = ":one::seven:";
                }
                if (random == 18) {
                    response = ":one::eight:";
                }
                if (random == 19) {
                    response = ":one::nine:";
                }
                if (random == 20) {
                    response = ":two::zero:";
                }

                event.getMessage().getChannel().sendMessage(response);
                usageCounter++;
            } else if (Mcontent.startsWith("?randnum")) {
                String[] nums = Mcontent.split(" ");
                int Low = 0;
                int High = 0;
                int Result = 0;
                Random r = new Random();

                boolean resultsValid = false;

                try {
                    Low = Integer.parseInt(nums[1]);
                    resultsValid = true;
                } catch (NumberFormatException e) {
                    event.getMessage().getChannel().sendMessage(":warning:` I dont recognize` **" + nums[1] + "** `as a valid number `:warning:");
                    resultsValid = false;
                }

                try {
                    High = Integer.parseInt(nums[2]);
                    resultsValid = true;
                } catch (NumberFormatException e) {
                    event.getMessage().getChannel().sendMessage(":warning:` I dont recognize` **" + nums[2] + "** `as a valid number `:warning:");
                    resultsValid = false;
                }

                if (resultsValid == true) {
                    try {
                        if (Low > High) {
                            int temp = Low;
                            Low = High;
                            High = temp;
                        }

                        Result = r.nextInt(High - Low) + Low;
                        event.getMessage().getChannel().sendMessage("Random number between " + nums[1] + " and " + nums[2] + "\n**" + Result + "**");
                    } catch (IllegalArgumentException | UnsupportedOperationException e) {
                        event.getMessage().getChannel().sendMessage(":warning:`Something went wrong`:warning:");
                    }

                }
                usageCounter++;
            }
            System.gc();
        } //Music and streaming commands ahead

        else if (Mcontent.startsWith(">")) {

            if (Mcontent.startsWith(">join")) {

                try {
                    message.delete();
                } catch (MissingPermissionsException e) {
                    
                }
                IVoiceChannel voiceChannel = message.getAuthor().getConnectedVoiceChannels().get(0);
                textChannel.add(message.getChannel().getID());
                if (voiceChannel != null) {
                    try{
                    voiceChannel.join();
                    }catch(MissingPermissionsException e){
                        message.reply("I dont have permission to join this voice channel. ");
                    }
                    AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                    audio.setVolume((float) 0.13);
                }
//                        channel.getAudioChannel().queueFile(new File("src/songs/I Am SOO Happy.mp3"));
//                        channel.getAudioChannel().queueFile(new File("src/songs/Zircon - Just Hold On (Abstruse Remix).mp3"));

            }
            
            if(Mcontent.startsWith(">autojoin")){
                IVoiceChannel voiceChannel = message.getAuthor().getConnectedVoiceChannels().get(0);
                
                if (voiceChannel != null) {
                    try{
                    voiceChannel.join();
                    AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                    audio.setVolume((float) 0.13);
                    message.reply("I will now join this channel in case of a reboot");
                    }catch(MissingPermissionsException e){
                        message.reply("I dont have permission to join this voice channel. ");
                    }
                    
                    autoJoinChannels.add(message.getChannel().getID().toString());
                    try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
                    File autoJoinChannelsFile = new File("src/dataDocuments" + "autoJoinChannels.txt");
                    fileIO.save(autoJoinChannels, "autoJoinChannels.txt", "src/dataDocuments");
                }
            }

            if (Mcontent.equals(">count")) {
                event.getMessage().getChannel().sendMessage("" + sirbrobot.client.getConnectedVoiceChannels().size());
            } else if (Mcontent.equals(">leaveall")) {
                if (message.getAuthor().equals(root)) {
                    
                    for (int i = 0; i < textChannel.size(); i++) {

                            try {
                                IMessage sendMessage = sirbrobot.client.getChannelByID(textChannel.get(i)).sendMessage("I needed to Disconnect from voice channels, give me a minute! ");
                            } catch (DiscordException | MissingPermissionsException ex) {
                                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            sirbrobot.client.getConnectedVoiceChannels().get(i).leave();

                        
                    
                    }

                }
            } else if (Mcontent.startsWith(">volume")) {
                if (message.getAuthor().equals(root)) {
                    AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                    audio.setVolume(Float.parseFloat(message.getContent().split(" ")[1]));
                }
            }

            if (Mcontent.startsWith(">pause")) {
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                audio.setPaused(true);
            }
            else if (Mcontent.startsWith(">resume")) {
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                audio.setPaused(false);
            } 
            
            else if (Mcontent.equals(">play")) {
                event.getMessage().getChannel().sendMessage("NOW PLAYING!");
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                audio.setVolume((float) 0.13);

                audio.queue(new File("src/songs/krewella - come and get it (razihel remix).mp3"));
                audio.queue(new File("src/songs/Fuck Gravity (Original Mix).mp3"));
                audio.queue(new File("src/songs/Turn Up (Original Mix).mp3"));
                audio.queue(new File("src/songs/Teminiate.mp3"));//Teminite & Panda Eyes - Highscore
                audio.queue(new File("src/songs/The Magician - Sunlight (Elephante Remix).mp3"));
                audio.queue(new File("src/songs/Phoebe Ryan - Mine (Win & Woo Remix) Radio Edit.mp3"));
                audio.queue(new File("src/songs/Bassist.mp3"));
                audio.queue(new File("src/songs/I Am SOO Happy.mp3"));
                audio.queue(new File("src/songs/Zircon - Just Hold On (Abstruse Remix).mp3"));
                audio.queue(new File("src/songs/Maximum Overdrive.mp3"));

//                audio.queue(new File("src/songs/leaguesongs/Aftershock - Schoolboy.m4a"));
//                audio.queue(new File("src/songs/leaguesongs/Radioactive - Imagine Dragons.m4a"));
//                audio.queue(new File("src/songs/leaguesongs/Witchcraft - Pendulum.m4a"));
//                audio.queue(new File("src/songs/leaguesongs/Everything's Magic - Angels & Airwaves.m4a"));
//                audio.queue(new File("src/songs/leaguesongs/Levels (Skrillex Remix) - Avicii.m4a"));
//                audio.queue(new File("src/songs/leaguesongs/PROJECT Yi (Vicetone Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Silver Scrapes (ProtoShredanoid Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/The Boy Who Shattered Time (MitiS Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/The Glory (James Egbert Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Welcome to Planet Urf (Jauz Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Worlds Collide (Arty Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Edge of Infinity (Minnesota Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Flash Funk (Marshmello Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Let The Games Begin (Hyper Potions Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Lucidity (Dan Negovan Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Piercing Light (Mako Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/PROJECT Yi (Vicetone Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Silver Scrapes (ProtoShredanoid Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/The Boy Who Shattered Time (MitiS Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/The Glory (James Egbert Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Welcome to Planet Urf (Jauz Remix).mp3"));
//                audio.queue(new File("src/songs/leaguesongs/Worlds Collide (Arty Remix).mp3"));
            } 
           
            else if (Mcontent.startsWith(">leave")) {
                client.getConnectedVoiceChannels().stream().filter((IVoiceChannel channel) -> channel.getGuild().equals(message.getGuild())).findFirst().ifPresent(IVoiceChannel::leave);
            } 
            
                
            else if(Mcontent.equals(">skip")){
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                audio.skip();
            }
                
              

             else if (Mcontent.startsWith(">stop")) {
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());

                audio.skipTo(audio.getPlaylistSize());

            } else if (Mcontent.startsWith(">playlist")) {
                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                System.out.println("Playlist reached");

                System.out.println(audio.getPlaylistSize());

                //{file=src\songs\
                if(audio.getPlaylistSize()> 0){
                if (audio.getCurrentTrack().getMetadata().toString().contains("file=src")){

                    String songName = audio.getCurrentTrack().getMetadata().toString().replace("{file=src\\songs\\", "").replace("}", "");

                    messageBuilder.appendContent("PLAYLIST: \n");
                    messageBuilder.appendContent("Currently playing: " + songName + "\n");//+ " || " + audio.getCurrentTrack().getCurrentTrackTime() + " / " + audio.getCurrentTrack().getTotalTrackTime() + " ||\n"

                    if (audio.getPlaylist().size() > 1) {
                        for (int i = 1; i < audio.getPlaylistSize(); i++) {
                            messageBuilder.appendContent(i + ": " + audio.getPlaylist().get(i).getMetadata().toString().replace("{file=src\\songs\\", "").replace("}", "").replace(".mp3", "") + "\n");
                        }
                    }

                    messageBuilder.send();
                } else {
                    messageBuilder.appendContent("PLAYLIST: \n");
                    messageBuilder.appendContent("Currently playing: " + audio.getCurrentTrack().getMetadata().toString() + "\n");//+ " || " + audio.getCurrentTrack().getCurrentTrackTime() + " / " + audio.getCurrentTrack().getTotalTrackTime() + " ||\n"

                    if (audio.getPlaylist().size() > 1) {
                        for (int i = 1; i < audio.getPlaylistSize(); i++) {
                            messageBuilder.appendContent(i + ": " + audio.getPlaylist().get(i).getMetadata() + "\n");
                        }
                    }
                    messageBuilder.send();
                }
                }

            } 
            else if(Mcontent.startsWith(">search:")){
                    AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                    String[] videoSearch = Mcontent.split("search:");
                    String temp = null;
                    IMessage tempmessage = null;
                    
                    if(videoSearch.length == 2){
                        try {
                            message.delete();
                        } catch (MissingPermissionsException e) {
                        }
                    tempmessage = messageBuilder.withContent("Searching **YouTube** for terms: "+videoSearch[1].trim()).send();
                    temp = queueFromYouTubeSearch(audio, videoSearch[1].trim(), message.getGuild().getID());
                    }
                    
                    else{
                        temp = null;
                        message.reply("Command error. Syntax is `>stream search: keywords`");
                    }
                    if(temp != null){
                            IUser user = message.getAuthor();
                            tempmessage.edit(user.mention() + " added: **" + temp.trim() +"**  ("+ execCmd("youtube-dl.exe ytsearch:\""+temp+"\" --get-duration")+")".trim());
                            //System.out.println(audio.getCurrentTrack().getMetadata().toString());
                            
                        }
                        else
                            tempmessage = messageBuilder.withContent("There was an error, please forgive me.").send();

                     
                    
                }
            
            else if (Mcontent.startsWith(">stream")) {

                if (Mcontent.contains("search:")) {
                    AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                    String[] videoSearch = Mcontent.split("search:");
                    String temp = null;
                    IMessage tempmessage = null;

                    if (videoSearch.length == 2) {
                        message.delete();
                        tempmessage = messageBuilder.withContent("Searching **YouTube** for terms: " + videoSearch[1].trim()).send();
                        temp = queueFromYouTubeSearch(audio, videoSearch[1].trim(), message.getGuild().getID());
                    } else {
                        temp = null;
                        message.reply("Command error. Syntax is `>stream search: keywords`");
                    }
                    if (temp != null) {
                        IUser user = message.getAuthor();
                        tempmessage.edit(user.mention() + " added: **" + temp.trim() + "**  (" + execCmd("youtube-dl.exe ytsearch:\"" + temp + "\" --get-duration") + ")".trim());
                        System.out.println(audio.getCurrentTrack().getMetadata().toString());

                    } else {
                        tempmessage = messageBuilder.withContent("There was an error, please forgive me.").send();
                    }

                } else {

                    String urlContent = message.getContent().replace(">stream ", "");
                    if (message.getContent().contains("&list")) {
                        String[] listSpliter = urlContent.split("&list");
                        urlContent = listSpliter[0];
                    }
                    String[] songID = urlContent.split("=");
                    Boolean streamStatus = true;

                    IMessage tempmessage = null;
                    String content = message.getContent();
                    IChannel channel = message.getChannel();
                    String url = content.trim().split(" ", 2)[1].trim();

                    if (url.isEmpty()) {
                        message.reply("You have to enter a *YOUTUBE URL*");
                        streamStatus = false;
                    }

                    if (url.contains("youtu") == false) {
                        message.reply("You should enter a *YOUTUBE URL*.");

                    }

                    if (url.contains("http") == false) {
                        message.reply("You should enter a *URL* when using `>stream` \n"
                                + "Use `>search: keywords` to search for keywords like song titles and artists.");
                        streamStatus = false;
                    }

                    if (url.contains("&list")) {
                        String[] listSpliter = urlContent.split("&list");
                        url = listSpliter[0];
                    }

                    try {

                        tempmessage = messageBuilder.withContent("Preparing to process URL into queue: ").send();
                        AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
                        String temp = queueFromYouTube(audio, url, message.getGuild().getID());
                        if (temp != null) {
                            IUser user = message.getAuthor();
                            
                            try{message.delete();} catch(MissingPermissionsException e){};
                            
                            try{
                                
                                tempmessage.edit(user.mention() + " added: **" + temp + "**");
                            }catch(MissingPermissionsException e){
                                message.reply(" added: **" + temp + "**");
                            }
                            //System.out.println(audio.getCurrentTrack().getMetadata().toString());

                        } else {
                            tempmessage.edit("There was an error, please forgive me.");
                        }

                    } catch (DiscordException e) {
                        LOG.warn("Could not get audio channel", e);
                        tempmessage.edit("Could not get the audio channel for this server");
                        }
                    }

                }
            System.gc();
        }
        
                
    }


    private String queueFromYouTube(final AudioPlayer audio, String id, String guild) {
        String name = System.getProperty("os.name").contains("Windows") ? "youtube-dl.exe" : "youtube-dl";
//        ProcessBuilder builder = new ProcessBuilder(name, "-q", "-f", "worstaudio",
//            "--exec", "ffmpeg -hide_banner -nostats -loglevel panic -y -i {} -vn -q:a 5 -f mp3 pipe:1", "-o",id)s", "--", id);

        ProcessBuilder builder = new ProcessBuilder(name, "-o",
                "%(title)s.%(ext)s", "\"" + id + "\"", "--restrict-filenames", "-f", "bestaudio",
                "--exec", "ffmpeg -hide_banner -nostats -loglevel panic -y -i {} -vn -q:a 5 -f mp3 pipe:1", "--");

        if (id.contains("&list")) {
            String[] listSpliter = id.split("&list");
            id = listSpliter[0];
        }
        String URLSongname = null;
        try {
            URLSongname = execCmd("youtube-dl.exe ytsearch:\"" + id + "\" --get-title").trim();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Process process = builder.start();
            try {
                CompletableFuture.runAsync(() -> logStream(process.getErrorStream()));

                AudioPlayer.Track track = new AudioPlayer.Track(AudioSystem.getAudioInputStream(process.getInputStream()));
                track.getMetadata().put(URLSongname, execCmd("youtube-dl " + id + " --get-duration"));

                audio.queue(track);

            } catch (UnsupportedAudioFileException e) {
                LOG.warn("Could not queue audio", e);
                process.destroyForcibly();
            }

        } catch (IOException e) {
            LOG.warn("Could not start process", e);
        }
        return URLSongname;
    }

    private String queueFromYouTubeSearch(final AudioPlayer audio, final String search, String guild) {
        String name = System.getProperty("os.name").contains("Windows") ? "youtube-dl.exe" : "youtube-dl";
//        ProcessBuilder builder = new ProcessBuilder(name, "-q", "-f", "worstaudio",
//            "--exec", "ffmpeg -hide_banner -nostats -loglevel panic -y -i {} -vn -q:a 5 -f mp3 pipe:1", "-o",id)s", "--", id);

        ProcessBuilder builder = new ProcessBuilder(name,
                "ytsearch:" + search.trim(), "--restrict-filenames", "-f", "bestaudio",
                "--exec", "ffmpeg -hide_banner -nostats -loglevel panic -y -i {} -vn -q:a 5 -f mp3 pipe:1", "--");

        String results = null;

        try {
            Process process = builder.start();

            try {
                results = execCmd("youtube-dl.exe ytsearch:\"" + search.trim() + "\" --get-title").trim();
                CompletableFuture.runAsync(() -> logStream(process.getErrorStream()));
                AudioPlayer.Track track = new AudioPlayer.Track(AudioSystem.getAudioInputStream(process.getInputStream()));
                track.getMetadata().put(results, execCmd("youtube-dl " + search.trim() + " --get-duration"));

                audio.queue(track);
                String vidName = null;
                String command = null;

            } catch (UnsupportedAudioFileException e) {
                LOG.warn("Could not queue audio", e);
                process.destroyForcibly();
            }
        } catch (IOException e) {
            LOG.warn("Could not start process", e);
        }

        return results;

    }

    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next().trim() : "";
    }

    public static void sendMessage(String message) {
        MessageBuilder messageBuilder = new MessageBuilder(sirbrobot.client);
        RequestBuffer.request(() -> {
            try {

                messageBuilder.withContent(message).send();

            } catch (DiscordException ex) {
                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MissingPermissionsException ex) {
                java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

  

    private BufferedReader newProcessReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
    }

    private void logStream(InputStream stream) {
        try (BufferedReader input = newProcessReader(stream)) {
            String line;
            while ((line = input.readLine()) != null) {
                LOG.info("[yt-dl] " + line);
            }
        } catch (IOException e) {
            LOG.warn("Could not read from stream", e);
        }
    }

    public String getUptime() {
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;

        int days = (int) TimeUnit.MILLISECONDS.toDays(tDelta);
        long hrs = TimeUnit.MILLISECONDS.toHours(tDelta) - (days * 24);
        long min = TimeUnit.MILLISECONDS.toMinutes(tDelta) - (TimeUnit.MILLISECONDS.toHours(tDelta) * 60);
        long sec = TimeUnit.MILLISECONDS.toSeconds(tDelta) - (TimeUnit.MILLISECONDS.toMinutes(tDelta) * 60);

        return String.format("%02dd:%02dh:%02dm:%02ds", days, hrs, min, sec);
    }

    public int getUsers() {
        int guildsJoined = sirbrobot.client.getGuilds().size();
        int totalUsers = 0;
        int currentUsers = 0;

        if (userInfo.size() < sirbrobot.client.getGuilds().size() * 2) {
            for (int i = 0; i < guildsJoined; i++) {
                userInfo.add(sirbrobot.client.getGuilds().get(i).getName());
                //System.out.print(userInfo.get(i));
                //System.out.println("\nServer: " + sirbrobot.client.getGuilds().get(i).getName());
                for (int b = 0; b < sirbrobot.client.getGuilds().get(i).getUsers().size(); b++) {
                    totalUsers++;
                    currentUsers++;
                }
                userInfo.add("" + currentUsers);
                currentUsers = 0;
                //System.out.println("Contains: " + users + " members");     
            }
        } else {
            for (int i = 0; i < guildsJoined; i++) {
                for (int b = 0; b < sirbrobot.client.getGuilds().get(i).getUsers().size(); b++) {
                    totalUsers++;
                }
            }
        }
        return totalUsers;
    }

    public String GetCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();

        //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

//    public void deleteLastMessage(String ChannelID) throws InterruptedException{
//        try {
//            //client.getChannelByID(VADdata.get(event.getChannel().getGuild().getID()).toString()).getMessages().getLatestMessage().delete()
//            client.getChannelByID(ChannelID).getMessages().getLatestMessage().;
//        } catch (MissingPermissionsException | HTTP429Exception | DiscordException ex) {
//            java.util.logging.Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//        else if (message.getContent().equals("Hello SirBroBot")) {
//            messageBuilder.withContent(String.format("Hello %s.", message.getAuthor().getName()));
//            try {
//                messageBuilder.build();
//            } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
//                e.printStackTrace();
//            }
//        } 
//        else {
//            messageBuilder.withContent(String.format("%s said %s in the %s channel.", message.getAuthor().getName(), message.getContent(), message.getChannel().getName()));
//            try {
//                messageBuilder.build();
//            } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
//                e.printStackTrace();
//            }
//        }
}
