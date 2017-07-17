package bromandudeguyphd.sirbrobot;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import bromandudeguyphd.htmlparsing.PostingHTMLData;
import bromandudeguyphd.htmlparsing.*;
import bromandudeguyphd.imagewriting.MirrorImage;
import bromandudeguyphd.imagewriting.NegativeImage;
import bromandudeguyphd.sirbrobot.music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.handle.audio.IAudioManager;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * @author BroManDudeGuyPhD
 */
@SuppressWarnings("JavaDoc")
public class MainListener {
    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();;
    Map<Long, GuildMusicManager> musicManagers = new HashMap<>();
    
    ArrayList<String> curseWords = new ArrayList<>();
    List<IGuild> serversJoined = new ArrayList<>();
    ArrayList<String> serverIconNames = new ArrayList<>();
    ArrayList<String> rolesOnServer = new ArrayList<>();
    ArrayList<String> userInfo = new ArrayList<>();


    //Map stores Voice Anonounce Determiner for all servers, key is Server ID and
    Map<String, String> VAD = new HashMap<>();
    Map<String, String> VADdata = new HashMap<>();
    Map<String, String> WAD = new HashMap<>();
    Map<String, String> WADdata = new HashMap<>();
    Map<String, String> LAD = new HashMap<>();
    Map<String, String> LADdata = new HashMap<>();
    Map<String, String> PMD = new HashMap<>();
    @SuppressWarnings("unused")
    Map<String, ArrayList<String>> Playlist = new HashMap<>();
    Map<String, String> TAG = new HashMap<>();

    @SuppressWarnings("deprecation")
    commandsClass commands = new commandsClass();
    ArrayList<String> textChannel = new ArrayList<>();
    static ArrayList<String> autoJoinChannels = new ArrayList<>();

    Twitter twitter = TwitterFactory.getSingleton();
    public static IUser root;
    
    boolean updateDispatcher = false;
    boolean messageStatus = false;
    int usageCounter = 0;

    File dance = new File("src/images/dancingKnight.gif");
    //File joust = new File("src/images/joust.gif");
    File wakeup = new File("src/images/wakeup.gif");
    File taunt = new File("src/images/taunt.gif");
    File insult = new File("src/images/insult.gif");
    File joust2 = new File("src/images/joust2.gif");
    File fairest = new File("src/images/fairest.jpg");
    File enemy = new File("src/images/enemy.png");

    long twitterID = 0;
    static int messagesSeen = 0;
    
    
    nlpLibrary nlp = new nlpLibrary();

    @EventSubscriber
    public void onGuildCreate(GuildCreateEvent event) {
        if (SirBroBot.client.isReady()) {
//            RequestBuffer.request(() -> {
//                try {
//                    Messages.send("Thanks for adding me to your guild! It definitely means a lot to the guy that made me.\n"
//                            + "My main command is `?commands`. It lets you see what I am capable of. Most of my features are opt-in, meaing you must enable them for them to work.\n"
//                            + "Definitely be sure to join the project at https://discord.gg/0wCCISzMcKMkfX88 \n"
//                            + "And check out my \n"
//                            + "**Twitter account:** <https://twitter.com/SirBroBotThe1st>\n"
//                            + "**Website: **http://bootswithdefer.tumblr.com/SirBroBot\n", event.getClient().getOrCreatePMChannel(event.getGuild().getOwner()));
//                } catch (DiscordException e) {
//                    SirBroBot.LOGGER.error(null, e);
//                }
//            });
//            IChannel updateChannel = event.getClient().getChannelByID("197567480439373824");
//            String alert = "Joined guild " + event.getGuild().getName()
//                    + " | Members: " + event.getGuild().getUsers().size()
//                    + "  :::  Server Count: " + event.getClient().getGuilds().size()
//                    + " User Count: " + getUsers();
//            System.out.println(alert);
//            Messages.send(alert, updateChannel);
        }
    }

    @EventSubscriber
    public void onGuildLeave(GuildLeaveEvent event) {
        System.out.println("Left guild " + event.getGuild().getName() + " | Members: " + event.getGuild().getUsers().size()+ "  :::  Server Count: " + event.getClient().getGuilds().size());
        IChannel updateChannel = event.getClient().getChannelByID("197567480439373824");
        if (updateDispatcher) {
            try {
                updateChannel.sendMessage("Left guild " + event.getGuild().getName() + " | Members: " + event.getGuild().getUsers().size() + "  :::  Server Count: " + event.getClient().getGuilds().size() + " User Count: " + getUsers());
            } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }
        }
    }

    @EventSubscriber
    public void onReadyEvent(@SuppressWarnings("UnusedParameters") ReadyEvent event) {
        sx.blah.discord.handle.obj.Status status = sx.blah.discord.handle.obj.Status.stream("?commands", "https://www.twitch.tv/SirBroBot/profile");
        //sx.blah.discord.handle.obj.Status status = sx.blah.discord.handle.obj.Status.game("Boot in progress");
        event.getClient().changeStatus(status);
        
//        try {
//            fileIO.readFile(curseWords, "bannedWords.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            fileIO.readFile(serverIconNames, "src/dataDocuments/serverIconUrls.txt");
        } catch (IOException ignored) {
            SirBroBot.LOGGER.warn("Unable to load serverIconNames");
        }


        try {
            fileIO.readFile(autoJoinChannels, "src/dataDocuments/autoJoinChannels.txt");
        } catch (IOException ignored) {
            SirBroBot.LOGGER.warn("Unable to load serverIconNames");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }

        fileIO.readHash(VAD, "VAD");
        fileIO.readHash(VADdata, "VADdata");
        fileIO.readHash(WAD, "WAD");
        fileIO.readHash(WADdata, "WADdata");
        fileIO.readHash(TAG, "TAG");
        fileIO.readHash(LAD, "LAD");
        fileIO.readHash(LADdata, "LADdata");
        fileIO.readHash(PMD, "PMD");



        updateDispatcher = true;
        
        SirBroBot.LOGGER.info("Booted!!");
        System.out.println("Booted!");
        
        

//        autoJoinVoiceClass autoJoinThread = new autoJoinVoiceClass(event);
//        Thread thread = new Thread(autoJoinThread);
//        thread.start();

    }

    @EventSubscriber
    public void handleJoin(UserJoinEvent event) {
        if (WAD.containsKey(event.getGuild().getID())) {
            Messages.send(WAD.get(event.getGuild().getID())
                    .replace("USERMENTION", event.getUser().mention())
                    .replace("USERNAME", "**" + event.getUser().getName() + "**"), event.getGuild().getChannelByID(WADdata.get(event.getGuild().getID())));
        }

    }

    @EventSubscriber
    public void handleVoiceJoin(UserVoiceChannelJoinEvent event) {
        if (VAD.containsKey(event.getVoiceChannel().getGuild().getID())) {
            IVoiceChannel channelJoined = event.getVoiceChannel();
            String userJoined = event.getUser().getName();

            RequestBuffer.request(() -> {
                try {
                    MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
                    messageBuilder.withChannel(event.getVoiceChannel().getGuild().getChannelByID(VADdata.get(event.getVoiceChannel().getGuild().getID())));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " joined " + channelJoined.getName()).withTTS().send();

                    if (VAD.get(event.getVoiceChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " joined " + channelJoined.getName());
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                    if (VAD.get(event.getVoiceChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                } catch (DiscordException | MissingPermissionsException ex) {
                    SirBroBot.LOGGER.error(null, ex);
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
                    MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
                    messageBuilder.withChannel(event.getNewChannel().getGuild().getChannelByID(VADdata.get(event.getNewChannel().getGuild().getID())));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " moved to " + channelJoined.getName()).withTTS().send();


                    if (VAD.get(event.getNewChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " moved to " + channelJoined.getName());
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                    if (VAD.get(event.getNewChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                } catch (DiscordException | MissingPermissionsException ex) {
                    SirBroBot.LOGGER.error(null, ex);
                }
            });
        }

    }

    @EventSubscriber
    public void handleVoiceLeave(UserVoiceChannelLeaveEvent event) {
        IVoiceChannel channelJoined = event.getVoiceChannel();
        if (LAD.containsKey(event.getVoiceChannel().getGuild().getID())) {
            String userJoined = event.getUser().getName();

            RequestBuffer.request(() -> {
                try {
                    MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
                    messageBuilder.withChannel(event.getVoiceChannel().getGuild().getChannelByID(LADdata.get(event.getVoiceChannel().getGuild().getID())));
                    IMessage tempmessage = messageBuilder.withContent(userJoined + " left " + channelJoined.getName()).withTTS().send();

                    if (LAD.get(event.getVoiceChannel().getGuild().getID()).equals("true save")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.edit("**" + userJoined + "**" + " left " + channelJoined.getName());
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                    if (LAD.get(event.getVoiceChannel().getGuild().getID()).equals("true")) {
                        RequestBuffer.request(() -> {
                            try {
                                tempmessage.delete();
                            } catch (MissingPermissionsException | DiscordException ex) {
                                SirBroBot.LOGGER.error(null, ex);
                            }
                        });
                    }
                } catch (DiscordException | MissingPermissionsException ex) {
                    SirBroBot.LOGGER.error(null, ex);
                }
            });
        }
    }

//    @EventSubscriber
//    public void onDiscordDisconnectedEvent(DiscordDisconnectedEvent event) throws HTTP429Exception, ChatNotFoundException {
//        try {
//                SirBroBot.skype.getOrLoadChat("8:andrew.addison.").sendMessage("I've gone offline.");
//            } catch (ConnectionException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            }
//        System.out.println(event.getReason());
//        if (event.getReason().equals(event.getReason().TIMEOUT)) {
//            try {
//                SirBroBot.skype.getOrLoadChat("19:33f1c36b235544eab1b957983ea10ec1@thread.skype").sendMessage("I've gone offline because: TIMED OUT : Reboot Iminent");
//            } catch (ConnectionException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            } catch (ChatNotFoundException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            }
//        } else if (event.getReason().equals(event.getReason().LOGGED_OUT)) {
//            try {
//                SirBroBot.skype.getOrLoadChat("19:33f1c36b235544eab1b957983ea10ec1@thread.skype").sendMessage("I've gone offline because: LOGGED OUT");
//            } catch (ConnectionException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            } catch (ChatNotFoundException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            }
//        } else if (event.getReason().equals(event.getReason().UNKNOWN)) {
//            try {
//                SirBroBot.skype.getOrLoadChat("8:andrew.addison.").sendMessage("I've gone offline because: UNKNOWN_ERROR");
//            } catch (ConnectionException ex) {
//                SirBroBot.LOGGER.error(null, ex);
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
    public void mentioned(MentionEvent event) {

        IMessage message = event.getMessage();
        String mcontent = message.getContent().replace("<@166913295457058817>", "").toLowerCase();
        MessageBuilder messageBuilder = new MessageBuilder(event.getClient()).withChannel(event.getMessage().getChannel());

        root = event.getClient().getUserByID(tokens.rootID());
        String mention = message.getAuthor().mention();
        IChannel channel = message.getChannel();
        boolean tag = false;
        
//Temporary Christmas reply
//        if (mcontent.contains("christmas")) {
//            try {
//                message.reply("Merry Christmas! :christmas_tree: :gift: :ribbon: :snowman2: :santa: :slight_smile: :smiley:");
//            } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
//                SirBroBot.LOGGER.error(null, ex);
//            }
//        }

        if (mcontent.contains("dance")) {

            try {
                event.getMessage().getChannel().sendFile(dance);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("joust")) {
            try {
                event.getMessage().getChannel().sendFile(joust2);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("wake up")) {
            try {
                event.getMessage().getChannel().sendFile(wakeup);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("insult")) {
            try {
                event.getMessage().getChannel().sendFile(insult);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("taunt")) {
            try {
                event.getMessage().getChannel().sendFile(taunt);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("who is the fairest of them all")) {
            try {
                event.getMessage().getChannel().sendFile(fairest);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } else if (mcontent.contains("who is your foe")) {
            try {
                event.getMessage().getChannel().sendFile(enemy);
            } catch (IOException | MissingPermissionsException | RateLimitException | DiscordException ex) {
                SirBroBot.LOGGER.error(null, ex);
            }

            usageCounter++;
        } 
        
        
        
        else if (mcontent.contains("tag")) {

            //Syntax @Mention tag <name> <action> <url (if applicable)
            String[] saveArray = message.getContent().split("\\s");
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
                tag = true;
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
                    SirBroBot.LOGGER.error(null, ex);
                }
            } 
            
            else if (saveArray.length == 2 && saveArray[1].toLowerCase().equals("tags")) {
                tag = true;
                messageBuilder.appendContent("TAGS: " + TAG.size() + "\n```");
                TAG.forEach((String a, String b) -> {
                    //System.out.printf("%s, %s\n", a, b);
                    messageBuilder.appendContent(String.format("%s ", a));
                });
                messageBuilder.appendContent("```");
                try {
                    messageBuilder.send();
                } catch (RateLimitException | DiscordException | MissingPermissionsException ex) {
                    SirBroBot.LOGGER.error(null, ex);
                }
            }

            //Printing out tags
            if (saveArray.length > 2) {
                //Printing out image tags
                if (tagName != null && TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("imageJPG")) {
                    File file = new File("src/tagData/images/" + tagName.toLowerCase() + ".jpg");
                    try {
                        event.getMessage().getChannel().sendFile(file);
                    } catch (MissingPermissionsException ignored) {
                    } catch (IOException | RateLimitException | DiscordException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }

                } else if (tagName != null && TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("imagePNG")) {
                    File file = new File("src/tagData/images/" + tagName.toLowerCase() + ".png");
                    try {
                        event.getMessage().getChannel().sendFile(file);
                    } catch (MissingPermissionsException ignored) {
                    } catch (IOException | RateLimitException | DiscordException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }
                } //Printing out gif tags
                else if (tagName != null && TAG.containsKey(tagName.toLowerCase()) && TAG.get(tagName).equals("gif")) {
                    File file = new File("src/tagData/gifs/" + saveArray[2].toLowerCase() + ".gif");
                    try {
                        event.getMessage().getChannel().sendFile(file);
                    } catch (MissingPermissionsException ignored) {
                    } catch (IOException | RateLimitException | DiscordException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }
                }
            }

            //ADDING a tag
            //@SirBroBot(mention) tag <name> <action> <url>
            if (saveArray.length == 5 && action != null && action.toLowerCase().equals("add")) {
                if (TAG.containsKey(tagName)) {
                    try {
                        message.reply("A tag exists with this name already. Your unoriginallity amazes me.");
                    } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }
                } else {
                    if (url != null && url.toLowerCase().endsWith(".gif")) {
                        try {
                            fileIO.saveImage(url, tagName + ".gif", "src/tagData/gifs/");
                        } catch (IOException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                        TAG.put(tagName, "gif");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: GIF) added!");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                    } else if (url != null && url.toLowerCase().endsWith(".png")) {
                        try {
                            fileIO.saveImage(url, tagName + ".png", "src/tagData/images/");
                        } catch (IOException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                        TAG.put(tagName, "imagePNG");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: IMAGE) added");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                    } else if (url != null && url.toLowerCase().endsWith(".jpg")) {

                        try {
                            //                        String extension = url.substring(url.lastIndexOf("."));
//                        fileIO.saveImage(url, tagName + "."+extension, "src/tagData/images/");
                            fileIO.saveImage(url, tagName + ".jpg", "src/tagData/images/");
                        } catch (IOException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                        TAG.put(tagName, "imageJPG");
                        fileIO.saveHash(TAG, "TAG");
                        try {
                            event.getMessage().getChannel().sendMessage("Tag (type: IMAGE) added");
                        } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                            SirBroBot.LOGGER.error(null, ex);
                        }
                    }
                }
                //REMOVING a tag
            } else if (saveArray.length == 4 && action != null && action.toLowerCase().equals("remove") && TAG.containsKey(tagName)) {
                Path path = null;

                fileIO.saveHash(TAG, "TAG");

                if (TAG.get(tagName).equals("imagePNG")) {
                    path = Paths.get("src/tagData/images/" + tagName + ".png");
                } else if (TAG.get(tagName).equals("imageJPG")) {
                    path = Paths.get("src/tagData/images/" + tagName + ".jpg");
                }
                if (TAG.get(tagName).equals("gif")) {
                    path = Paths.get("src/tagData/gifs/" + tagName + ".gif");
                }

                if (path != null)
                    try {
                        Files.delete(path);
                    } catch (NoSuchFileException ignored) {
                        System.err.format("%s: no such" + " file or directory%n", path);
                    } catch (DirectoryNotEmptyException ignored) {
                        System.err.format("%s not empty%n", path);
                    } catch (IOException x) {
                        SirBroBot.LOGGER.error(null, x);
                    }
                if (tagName != null)
                    TAG.remove(tagName.toLowerCase());
                try {
                    event.getMessage().getChannel().sendMessage("Tag removed!");
                } catch (MissingPermissionsException | RateLimitException | DiscordException ex) {
                    SirBroBot.LOGGER.error(null, ex);
                }
            }

        }
        

        //NLP Starts here ONLY IN SBB TEST SERVER FOR NOW
        else if (tag == false && !event.getMessage().mentionsEveryone() && !event.getMessage().mentionsHere()){
                Messages.send(nlp.processMessageWithMentions(message), message.getChannel());
            }
            
            
            
            
        
        messagesSeen++;
    }
    


    


    @EventSubscriber
    @SuppressWarnings("SleepWhileInLoop")
    public void onMessageReceived(MessageReceivedEvent event) throws Exception {


        root = event.getClient().getUserByID(tokens.rootID());
        IChannel updateChannel = event.getClient().getChannelByID("197567480439373824");
        IChannel chan = event.getMessage().getChannel();

        messagesSeen++;

        IMessage message = event.getMessage();
//        MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
//        messageBuilder.withChannel(event.getMessage().getChannel());
        String Mcontent = message.getContent().toLowerCase().trim();
        boolean serverOwner = false;

        //Determine Server owner status
        if (Mcontent.contains("who is root")) {
            Messages.send(root.mention() + " is root", event.getMessage().getChannel());

            //message.reply(root.getID()+" is root");
        }

        if (message.getChannel().isPrivate()) {
            IPrivateChannel person = event.getClient().getOrCreatePMChannel(message.getAuthor());
            if (message.getAuthor() != root && messageStatus) {
                event.getClient().getOrCreatePMChannel(root).sendMessage(message.getAuthor().getName() + ": " + message.getContent());
            }

            if (Mcontent.contains("invite")) {
                event.getClient().getOrCreatePMChannel(message.getAuthor()).sendMessage("Invite me to a server with https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=473168957");
            } else if (Mcontent.equals("?ocommands")) {
                event.getClient().getOrCreatePMChannel(message.getAuthor()).sendMessage("These must be used in a server by the SERVER OWNER. **They wont work in this PM**\n" + commands.commandListOwner);
            } else if (Mcontent.equals("?mcommands")) {
                event.getClient().getOrCreatePMChannel(message.getAuthor()).sendMessage("These must be used in a server. **They wont work in this PM**\n" + commands.mcommands);
            } else if (Mcontent.contains("commands") | Mcontent.contains("help")) {

                
                person.toggleTypingStatus();

                IMessage tempmessage = person.sendMessage("Determining Owner status.");

                try {
                    Thread.sleep(2100);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }

                int placeholder = 0;
                for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
                    if (event.getClient().getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
                        serverOwner = true;
                        placeholder = i;
                    }
                }

                if (serverOwner) {
                    tempmessage.edit("Hello **" + event.getClient().getGuilds().get(placeholder).getName() + "** server owner! \n"
                            + "Fetching commands");
                    try {
                        Thread.sleep(2100);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.commandListOwner);

                }

                if (!serverOwner) {
                    tempmessage.edit("I am not on a server you own. I will fetch Normal Use commands. To add me go here: https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=8 ");
                    person.sendMessage(commands.commandListNormal);
                }
            } else if (Mcontent.startsWith("?broadcaston")) {
                for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
                    if (event.getClient().getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
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
            } 
            
            else if (Mcontent.startsWith("?broadcastoff")) {
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
            } 
            else if (message.getContent().startsWith("?") || message.getContent().startsWith(">")) {
                
                RequestBuffer.request(() -> {
                    try {
                        person.sendMessage("I dont support commands VIA pm, but to see my commands say ?commands. Also, I have a basic understanding of natural language, so we can talk :)");
                    } catch (MissingPermissionsException | DiscordException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }
                });
            }
            
            //NLP for private messages
            else{
                person.sendMessage(nlp.processMessage(message));
            }
                    
        } 


//Must be public channel
        //COMMANDS begin with ?

        else if (message.getContent().startsWith("?")) {

//ONLY BROMANDUDEGUYPHD (Bot Creator) Can cast these for obvious reasons, it shuts the bot down
            if (message.getAuthor().equals(root)) {

                if (message.getContent().contains("?quit")) {
                    fileIO.save(autoJoinChannels, "src/dataDocuments/autoJoinChannels.txt");
                    Messages.send("Goodbye!", chan);
                    event.getClient().logout();
                    //SirBroBot.skype.logout();

//                    fileIO.save(serverIconNames, "ServerIconUrls.txt");
                    try{
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");
                    fileIO.saveHash(TAG, "TAG");
                    fileIO.saveHash(PMD, "PMD");
                    fileIO.save(autoJoinChannels, "src/dataDocuments/autoJoinChannels.txt");
                    } catch(Exception e){
                        
                    }
                    System.exit(0);

                } 
                
                else if (Mcontent.equals("?reboot")) {
//                    fileIO.save(serverIconNames, "ServerIconUrls.txt");
                    fileIO.saveHash(VAD, "VAD");
                    fileIO.saveHash(VADdata, "VADdata");
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");
                    fileIO.saveHash(TAG, "TAG");
                    fileIO.saveHash(PMD, "PMD");
                    IMessage rebootMessage = event.getMessage().getChannel().sendMessage("I am rebooting");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    SirBroBot.client.logout();
                    
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }

                    SirBroBot.boot();
                    usageCounter++;

                    try {
                        fileIO.readFile(serverIconNames, "src/dataDocuments/serverIconUrls.txt");
                    } catch (IOException ignored) {
                        SirBroBot.LOGGER.error("Unable to load serverIconNames");
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
                } 
                
                else if (Mcontent.startsWith("?say ")) {

                    try {
                        message.delete();
                    } catch (MissingPermissionsException ignored) {
                    }
                    Messages.send(Mcontent.substring(5), chan);
                    usageCounter++;

                } 
                
                else if (Mcontent.startsWith("?guildid")) {
                    updateChannel.sendMessage("**Guild** " + message.getGuild().getName() + " **ID:** " + message.getGuild().getID());
                    usageCounter++;
                } 
                
                else if (Mcontent.startsWith("?channelid")) {
                    updateChannel.sendMessage("**Guild** " + message.getGuild().getName() + " **Channel** " + message.getChannel().getName() + " **ID:** " + message.getChannel().getID());
                    usageCounter++;
                } 
                
                else if (message.getContent().startsWith("?getID ")) {
                    message.delete();
                    IUser user;
                    user = message.getMentions().get(0);
                    Messages.send(user.getName() + "'s ID is: " + user.getID(), chan);
                    usageCounter++;
                } //Broadcasts message to server owners
                
                else if (Mcontent.startsWith("?broadcast ")) {
                    System.out.print("Broadcast Request Recieved");
                    String broadcastContent = message.getContent().replace("?broadcast ", "");
                    message.delete();
                    int servers = event.getClient().getGuilds().size();

                    for (int i = 0; i < servers; i++) {
                        IUser user = event.getClient().getGuilds().get(i).getOwner();
                        boolean broadcastStatus = true;
                        if (event.getClient().getGuilds().get(i).getID().equals("153315968091684865") | event.getClient().getGuilds().get(i).getID().equals("81384788765712384") | event.getClient().getGuilds().get(i).getID().equals("110373943822540800")) {
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

                        if (event.getClient().getGuilds().get(i).getID() == null) {
                            broadcastStatus = false;
                            System.out.println("Error found on: " + i);
                        }

                        if (broadcastStatus) {
                            RequestBuffer.request(() -> {
                                try {
                                    event.getClient().getOrCreatePMChannel(user).sendMessage("Hello " + user.getName() + "! " + "My creator has issued a broadcast: \n\n **BROADCASTED MESSAGE:**\n" + broadcastContent);

                                } catch (DiscordException | NullPointerException | MissingPermissionsException e) {
                                    e.printStackTrace();

                                }
                            });

                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ignored) {
                                Thread.currentThread().interrupt();
                            }

                            event.getMessage().getChannel().sendMessage(i + " ::Broadcast sent to: " + event.getClient().getGuilds().get(i).getName());
                        }

                        if (!broadcastStatus) {
                            if (event.getClient().getGuilds().get(i).getID().equals("153315968091684865") | event.getClient().getGuilds().get(i).getID().equals("81384788765712384") | event.getClient().getGuilds().get(i).getID().equals("110373943822540800")) {
                                event.getMessage().getChannel().sendMessage(i + " ::Broadcast **EXPECTED FAILURE** to: " + event.getClient().getGuilds().get(i).getName());
                            } else {
                                event.getMessage().getChannel().sendMessage(i + " ::Broadcast **FAILED** to: " + event.getClient().getGuilds().get(i).getName());
                            }

                            if (PMD.containsKey(user.getID())) {
                                if (PMD.get(user.getID()).equals("OFF")) {
                                    event.getMessage().getChannel().sendMessage("BROADCAST DENIED:: Server: " + event.getClient().getGuilds().get(i).getName() + "Owner: " + user.getName());
                                }
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ignored) {
                                Thread.currentThread().interrupt();
                            }

                        }
                    }

                } 
                
                else if (Mcontent.startsWith("?specificbroadcast")) {

                    int servers = event.getClient().getGuilds().size();
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
                            IUser user = event.getClient().getGuilds().get(i).getOwner();
                            event.getClient().getOrCreatePMChannel(user).sendMessage("Hello " + user.getName() + "! "
                                    + "My creator has issued a broadcast: \n\n"
                                    + "**BROADCASTED MESSAGE:**\n"
                                    + "    Hello! Thank you for having SirBroBot on your server!.\n"
                                    + "    As the server owner, there are special commands you have access to.\n"
                                    + "    If you wish to not recieve these please send me a direct message BroManDudeGuyPhD#5846 and I will remove you.\n\n"
                                    + "    Thank you for adding SirBroBot, and thank you for your patience!"
                                    + "    Report bugs and stay connected to the project at https://discord.gg/0wCCISzMcKOD7udN"
                                    + "    I also made this page to keep users updated a little more formally than PMs: http://bootswithdefer.tumblr.com/SirBroBot");
                        }

                    }

                    message.delete();

                } 
                
                else if (Mcontent.equals("?tweetabout")) {

                    twitter.updateStatus(
                            "Discord Servers: " + event.getClient().getGuilds().size() + "\n"
                                    + "Voice Channels: " + event.getClient().getVoiceChannels().size() + "\n"
                                    + "Text Channels: " + event.getClient().getChannels(false).size() + "\n"
                                    + "Total Users: " + getUsers() + "\n"
                                    + "Messages Seen: " + messagesSeen + "\n"
                                    + "Uptime: " + getUptime() + "\n");
                    event.getMessage().getChannel().sendMessage("Server stats sent!");
                    usageCounter++;

                } 
                
                else if (Mcontent.startsWith("?tweet:")) {

                    String[] tweetToSend = message.getContent().trim().split(":");
                    Status status = twitter.updateStatus(tweetToSend[1]);
                    event.getMessage().getChannel().sendMessage("Tweet sent!");
                    twitterID = status.getId();

                    usageCounter++;
                } 
                
                else if (Mcontent.startsWith("?deletetweet")) {
                    if (twitterID == 0) {
                        message.reply("I dont have an ID to delete");
                    } else {
                        twitter.destroyStatus(twitterID);
                        event.getMessage().getChannel().sendMessage("Tweet deleted!");
                    }

                    usageCounter++;
                } 
                
                
                else if (Mcontent.equals("?togglemessages")) {
                    messageStatus = !messageStatus;
                    message.reply("Messages " + (messageStatus ? "ON" : "OFF"));
                } 
                
                else if (Mcontent.equals("?toggleupdates")) {
                    updateDispatcher = !updateDispatcher;
                    message.reply("Updates " + (updateDispatcher ? "ON" : "OFF"));
                } 
                
                else if (Mcontent.contains("?guildwithchannel")) {
                    String channelName = message.getContent().replace("?guildwithchannnel", "");
                    for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
                        for (int j = 0; j < event.getClient().getGuilds().get(i).getChannels().size(); j++) {
                            if (event.getClient().getGuilds().get(i).getChannels().get(j).getName().equals(channelName)) {
                                message.reply(event.getClient().getGuilds().get(i).getName());
                            }
                        }
                    }
                } 
                
                else if (Mcontent.contains("?leaveguild")) {
                    String guildName = message.getContent().replace("?leaveguild", "");
                    for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
                        if (event.getClient().getGuilds().get(i).getName().equals(guildName)) {
                            event.getClient().getGuildByID(event.getClient().getGuilds().get(i).getID()).leaveGuild();
                        }

                    }
                } else if (Mcontent.equals("?purge")) {
                    FileChecker.purge();
                }
                
                else if (Mcontent.equals("?updatecarbon")) {
                    PostingHTMLData post = new PostingHTMLData();
                    try {
                        post.sendReq(SirBroBot.client.getGuilds().size());
                    } catch (IOException ex) {
                        SirBroBot.LOGGER.error(null, ex);
                    }
                    message.reply("Carbonitex updated!");
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
//                    fileIO.save(rolesOnServer, fileName);

                    try {
                        fileIO.readFile(curseWords, fileName);
                    } catch (IOException ignored) {
                        SirBroBot.LOGGER.warn("Unable to load cureWords file");
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
                    } catch (MissingPermissionsException ignored) {
                    }


                    char role = 0;

                    String[] rolesArray = message.getContent().split(" ");
                    for (int i = 0; i < rolesArray[1].length(); i++) {
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

                    Messages.send("Operation successful: " + message.getGuild().getRoles().get(roleToAdd) + " role assigned to " + message.getMentions().get(0), chan);

                    usageCounter++;

                } else if (message.getContent().startsWith("?addrole")) {
                    try {
                        message.delete();
                    } catch (MissingPermissionsException ignored) {
                    }
                    char role = 0;

                    String[] rolesArray = message.getContent().split(" ");
                    for (int i = 0; i < rolesArray[1].length(); i++) {
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

                    Messages.send("Operation successful: " + message.getGuild().getRoles().get(roleToAdd) + " role added to " + message.getMentions().get(0) + "\n", chan);


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
                   
                    WAD.put(message.getGuild().getID(), message.getContent().replace("?welcomeedit ", ""));
                    WADdata.put(message.getGuild().getID(), message.getChannel().getID());
                    message.reply("New User welcome message edited");
                    
                    fileIO.saveHash(WAD, "WAD");
                    fileIO.saveHash(WADdata, "WADdata");

                    usageCounter++;
                } else if (Mcontent.equals("?welcomeview")) {
                    if (WAD.containsKey(message.getGuild().getID())) {
                        try {
                            event.getMessage().getChannel().sendMessage(WAD.get(message.getGuild().getID()));
                        } catch (NullPointerException ignored) {
                            message.reply("I dont have a Welcome message stored");
                        }
                    } else {
                        message.reply("Welcome message is currently turned off. Use `?welcomeon Message Content` to activate");
                    }
                } 
                
                else if (Mcontent.startsWith("?purgechannel")) {
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
//                    event.getClient().getOrCreatePMChannel(purgeChannel.getGuild().getOwner()).sendMessage(purgeChannel.getName()+" channel purged");
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
                //                    if (purge) {
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
                        Messages.send("There are " + curseWords.size() + " banned Words", chan);
                        usageCounter++;
                        break;

                    //Returns list of roles to chat
                    case "?channels":
                        Messages.send("Channels I can see: " + event.getClient().getChannels(false).toString(), chan);
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
                Messages.send(commands.commandListNormal, chan);

                usageCounter++;
            } else if (Mcontent.startsWith("?mcommands")) {
                Messages.send(commands.mcommands, chan);
                
            } 
            
            else if (Mcontent.startsWith("?invite")) {
                event.getClient().getOrCreatePMChannel(message.getAuthor()).sendMessage("Invite me to a server with https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=473168957");
                usageCounter++;
            } 
            
            else if (Mcontent.equals("?pmcommands")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException ignored) {
                }
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
                boolean serverOwnerPM = false;
                IPrivateChannel person = event.getClient().getOrCreatePMChannel(message.getAuthor());
                person.toggleTypingStatus();

                IMessage tempmessage = person.sendMessage("Determining Owner status...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }


                int placeholder2 = 0;
                for (int i = 0; i < event.getClient().getGuilds().size(); i++) {
                    if (event.getClient().getGuilds().get(i).getOwner().getID().equals(message.getAuthor().getID())) {
                        serverOwnerPM = true;
                        placeholder2 = i;
                    }
                }

                if (serverOwnerPM) {
                    tempmessage.edit("Hello **" + event.getClient().getGuilds().get(placeholder2).getName() + "** server owner! \n"
                            + "Fetching commands");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.commandListOwner);
                    person.sendMessage(commands.mcommands);
                }

                if (!serverOwnerPM) {
                    tempmessage.edit("I am not on a server you own. I will fetch Normal Use commands. To add me go here: https://discordapp.com/oauth2/authorize?&client_id=171691699263766529&scope=bot&permissions=8 ");
                    person.sendMessage(commands.commandListNormal);
                    person.sendMessage(commands.mcommands);
                }

                System.out.println("Commands Requested VIA PM by " + message.getAuthor().getName() + " on " + message.getChannel().getGuild().getName());

                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?ocommands")) {
                if (message.getAuthor().getID().equals(message.getGuild().getOwner().getID())) {
                    Messages.send(commands.commandListOwner, chan);
                }

                if (!Objects.equals(message.getAuthor().getID(), message.getGuild().getOwner().getID())) {
                    message.reply("You arent the server Owner of **" + message.getGuild().getName() + "**, " + message.getGuild().getOwner().getName() + " is. \n"
                            + "But here are the commands anyway. **THEY WONT WORK HERE**");
                    message.getChannel().sendMessage(commands.commandListOwner);
                }

                usageCounter++;
                
            } 
            
            else if (Mcontent.toLowerCase().equals("?usecounter")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException ignored) {
                }
                Messages.send("I've issued " + usageCounter + " commands since my last upgrade.", chan);
                usageCounter++;
            } 
            
            else if (Mcontent.equals("?uptime")) {
                Messages.send(SirBroBot.getUptime(), chan);
                usageCounter++;
            } 
            
            else if (Mcontent.equals("?servers")) {

                Messages.send("I am currently the Knight of " + SirBroBot.client.getGuilds().size() + " servers\n", chan);
                usageCounter++;
            } 
                       
            
            else if (Mcontent.startsWith("?tsearch:")) {
                //Searches twitter for given username
                try {
                    String[] userToSearch;
                    userToSearch = message.getContent().trim().split(":");
                    boolean iconSaveStatus = false;

                    String iconUrl = twitter.users().showUser(userToSearch[1].trim()).getBiggerProfileImageURL();

                    
                    
                    //twitterProfiles.add(twitter.users().showUser(userToSearch[1]).getName());
                    File serverIcon = new File("src/images/twitterIcons/" + twitter.users().showUser(userToSearch[1]).getName().trim().replace(" ", "").replace(":", "") + ".jpg");
                    fileIO.saveImage(iconUrl, twitter.users().showUser(userToSearch[1]).getName().trim().replace(" ", "").replace(":", "") + ".jpg", "src/images/twitterIcons/");

                    event.getMessage().getChannel().sendFile(serverIcon);

                    usageCounter++;

                    Messages.send(userToSearch[1] + "'s info: \n"
                            + "```Name: " + twitter.users().showUser(userToSearch[1]).getName() + "\n"
                            + "Screen Name: " + twitter.users().showUser(userToSearch[1]).getScreenName() + "\n"
                            + "Twitter ID: " + twitter.users().showUser(userToSearch[1]).getId() + "\n"
                            + "Location: " + twitter.users().showUser(userToSearch[1]).getLocation() + "\n"
                            + "Followers: " + twitter.users().showUser(userToSearch[1]).getFollowersCount() + "\n"
                            + "Following: " + twitter.users().showUser(userToSearch[1]).getFriendsCount() + "\n"
                            + "Tweets: " + twitter.users().showUser(userToSearch[1]).getStatusesCount() + "\n"
                            + "```", chan);
                } catch (TwitterException ignored) {

                    message.reply("User not found! Try again");
                }
            } //Returns Authors token in chat USE CAREFULLY
            
            else if (Mcontent.equals("?myinfo")) {
                try {
                    message.delete();
                } catch (MissingPermissionsException ignored) {
                }
                event.getClient().getOrCreatePMChannel(message.getAuthor())
                        .sendMessage("Info for user " + message.getAuthor().getName() + " #" + message.getAuthor().getDiscriminator() + "\n"
                                + "ID: " + message.getAuthor().getID() + "\n"
                                + "Account created: " + message.getAuthor().getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Connected Voice Channel: " + message.getAuthor().getClient().getConnectedVoiceChannels().get(0).getName() + "/n```"
                                + "IconURL: " + message.getAuthor().getAvatarURL());

                usageCounter++;

            } 
            
            else if (Mcontent.startsWith("?whois")) {
                IUser who = message.getMentions().get(0);
                String rolesForWho = "";
                String game = null;

                for (int i = 0; i < +who.getRolesForGuild(message.getGuild()).size(); i++) {
                    rolesForWho += who.getRolesForGuild(message.getGuild()).get(i).getName() + ", ";
                }

                if (!who.getStatus().isEmpty()) {
                    game = who.getStatus().toString();
                }

                if (who.getStatus().isEmpty()) {
                    game = "None";
                }

                if (who.getAvatarURL().contains("/null.jpg")) {
                    try {
                        File discordUserIconSingle = new File("src/images/discordUserIcons/null.jpg");

                        event.getMessage().getChannel().sendFile(discordUserIconSingle);
                        Messages.send("```"
                                + "Name: " + who.getName()
                                + " #" + who.getDiscriminator() + "\n"
                                + "Roles: " + rolesForWho + "\n"
                                + "Account created: " + who.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Currently playing: " + game + "\n"
                                + "Presence: " + who.getPresence() + "\n"
                                + "```", chan);
                    } catch (FileNotFoundException | DiscordException | RateLimitException | MissingPermissionsException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        fileIO.saveImage(who.getAvatarURL(), who.getName() + ".jpg", "src/images/discordUserIcons/");
                        File discordUserIconSingle = new File("src/images/discordUserIcons/" + who.getName() + ".jpg");

                        event.getMessage().getChannel().sendFile(discordUserIconSingle);
                        Messages.send("```"
                                + "Name: " + who.getName()
                                + " #" + who.getDiscriminator() + "\n"
                                + "Roles: " + rolesForWho + "\n"
                                + "Account created: " + who.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n"
                                + "Currently playing: " + game + "\n"
                                + "Presence: " + who.getPresence() + "\n"
                                + "```", chan);
                    } catch (IOException | DiscordException | RateLimitException | MissingPermissionsException e) {
                        e.printStackTrace();
                    }
                }
                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?img-mirror")) {
                //Creates mirror image of given image
                MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
                if (message.getAttachments().size() > 0) {
                    IMessage update = messageBuilder.withContent("Processing image...").send();
                    try {
                        fileIO.saveImage(message.getAttachments().get(0).getUrl(), message.getAttachments().get(0).getFilename(), "src/images/imageWriting/imagesToManipulate/");
                        MirrorImage.setImage(message.getAttachments().get(0).getFilename());
                        MirrorImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException ignored) {
                        }
                        File reversedImage = new File("src/images/imageWriting/reversedImages/reversed" + message.getAttachments().get(0).getFilename());
                        event.getMessage().getChannel().sendFile(reversedImage);
                    } catch (IOException | DiscordException e) {
                        e.printStackTrace();
                        message.reply("You must attach an image to your message! I cant reverse 'nothing'");
                    }
                    update.edit("DONE!");
                }

                if (message.getContent().contains("http")) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing image...");
                    String url = message.getContent().replace("?img-mirror", "");
                    try {
                        fileIO.saveImage(url, "imagetomanipulate.jpg", "src/images/imageWriting/imagesToManipulate/");
                        MirrorImage.setImage("imagetomanipulate.jpg");
                        MirrorImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException ignored) {
                        }
                        File reversedImage = new File("src/images/imageWriting/reversedImages/reversed" + "imagetomanipulate.jpg");
                        event.getMessage().getChannel().sendFile(reversedImage);
                    } catch (IOException | DiscordException e) {
                        e.printStackTrace();

                    }
                    update.edit("DONE!");
                }
                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?img-neg")) {
                //Creates negative image of given image
                if (message.getAttachments().size() > 0) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing image...");

                    try {
                        fileIO.saveImage(message.getAttachments().get(0).getUrl(), message.getAttachments().get(0).getFilename(), "src/images/imageWriting/imagesToManipulate/");
                        NegativeImage.setImage(message.getAttachments().get(0).getFilename());
                        NegativeImage.run();
                        try {
                            message.delete();
                        } catch (MissingPermissionsException ignored) {
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
                        } catch (MissingPermissionsException ignored) {
                        }
                        File reversedImage = new File("src/images/imageWriting/negativeImages/negative" + "imagetomanipulate.jpg");
                        event.getMessage().getChannel().sendFile(reversedImage);

                    } catch (IOException | DiscordException e) {
                        e.printStackTrace();

                    }
                    update.edit("DONE!");
                }
                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?img-id ")) {
                //Posts image link to Microsofts image recognition bot and gets the result.
                if (Mcontent.contains("http")) {
                    IMessage update = event.getMessage().getChannel().sendMessage("Processing image...");

                    String results = HTMLUnit.imgid(message.getContent().replace("?img-id", ""));

                    if (results.contains("I think this may be inappropriate content so I won't show it")) {
                        try {
                            message.delete();
                        } catch (MissingPermissionsException ignored) {
                            event.getMessage().getChannel().sendMessage("Well I tried and failed to delete your message...");
                        }
                    }

                    update.edit(results);
                }
                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?sitetohtml")) {
                //Seemed cool
                String url = message.getContent().replace("?sitetohtml", "");
                TextParser.html(url);
                File file = new File("src/dataDocuments/HTMLfiles/" + url.replace(":", "").replace("/", ".") + ".html");
                event.getMessage().getChannel().sendFile(file);
                usageCounter++;
            } 
            
            else if (Mcontent.startsWith("?google")) {
                //Work in progress, as it were
                GoogleSearch.run(message.getContent().replace("?google ", ""));
                event.getMessage().getChannel().sendMessage(GoogleSearch.returnResults().replace(",", "").replace("[", "").replace("]", ""));
                GoogleSearch.clearSearch();
            } 
            
            
            else if (Mcontent.startsWith("?d20")) {
                //Could be replaced with something more intuitive, Im lazy.
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
                    response = ":one::zero:";
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
            } 
            
            else if (Mcontent.startsWith("?randnum")) {
                String[] nums = Mcontent.split(" ");
                int Low = 0;
                int High = 0;
                int Result;
                Random r = new Random();

                boolean resultsValid;

                try {
                    Low = Integer.parseInt(nums[1]);
                    resultsValid = true;
                } catch (NumberFormatException ignored) {
                    event.getMessage().getChannel().sendMessage(":warning:` I dont recognize` **" + nums[1] + "** `as a valid number `:warning:");
                    resultsValid = false;
                }

                try {
                    High = Integer.parseInt(nums[2]);
                    resultsValid = true;
                } catch (NumberFormatException ignored) {
                    event.getMessage().getChannel().sendMessage(":warning:` I dont recognize` **" + nums[2] + "** `as a valid number `:warning:");
                    resultsValid = false;
                }

                if (resultsValid) {
                    try {
                        if (Low > High) {
                            int temp = Low;
                            Low = High;
                            High = temp;
                        }

                        Result = r.nextInt(High - Low) + Low;
                        event.getMessage().getChannel().sendMessage("Random number between " + nums[1] + " and " + nums[2] + "\n**" + Result + "**");
                    } catch (IllegalArgumentException | UnsupportedOperationException ignored) {
                        event.getMessage().getChannel().sendMessage(":warning:`Something went wrong`:warning:");
                    }

                }
                usageCounter++;
            }

        } 


//Music and streaming commands ahead

        else if (Mcontent.startsWith(">")) {

            GuildMusicManager musicManager = getGuildAudioPlayer(message.getChannel().getGuild());
            
            
            if (Mcontent.startsWith(">join")) {
                
                try {
                    
                    message.reply(":warning: **WARNING** :warning: Control volume on the `User Volume` menu from Discord. Access by right clicking on the User menu on SirBroBots name. `Waiting 10 seconds to play` (Message may be deleivered twice for redundancy)");
                    message.delete();
                    Thread.sleep(10000);
                } catch (MissingPermissionsException ignored) {

                }
                IVoiceChannel voiceChannel = message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel();
                textChannel.add(message.getChannel().getID());
                if (voiceChannel != null) {
                    try {
                        voiceChannel.join();
                    } catch (MissingPermissionsException ignored) {
                        message.reply("I dont have permission to join this voice channel. ");
                    }

                }

            }

            if (Mcontent.startsWith(">autojoin")) {
                IVoiceChannel voiceChannel = message.getAuthor().getVoiceStateForGuild(message.getGuild()).getChannel();

                if (voiceChannel != null) {
                    try {
                        voiceChannel.join();
                        
                        message.reply("I will now join this channel in case of a reboot");
                    } catch (MissingPermissionsException ignored) {
                        message.reply("I dont have permission to join this voice channel. ");
                    }

                    if(!autoJoinChannels.contains(voiceChannel.getID())){
                        autoJoinChannels.add(voiceChannel.getID());
                        fileIO.save(autoJoinChannels, "src/dataDocuments/autoJoinChannels.txt");
                    }
                    
                    else if(autoJoinChannels.contains(voiceChannel.getID())){
                        message.reply("Im already set to autojoin this channel.");
                    }
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                    
                                 
                }
            }

            if (Mcontent.equals(">count")) {
                event.getMessage().getChannel().sendMessage("" + event.getClient().getConnectedVoiceChannels().size());
            } 
            
            else if (Mcontent.equals(">leaveall") && message.getAuthor().equals(root)) {

                sx.blah.discord.handle.obj.Status statusStopUpdate = sx.blah.discord.handle.obj.Status.game("Rebooting Audio");
                SirBroBot.client.changeStatus(statusStopUpdate);

                List<IVoiceChannel> leaveList = SirBroBot.client.getConnectedVoiceChannels();

                for (int i = 0; i < leaveList.size(); i++) {

                    leaveList.get(i).leave();
                    try {
                        musicManager = getGuildAudioPlayer(SirBroBot.client.getGuildByID(leaveList.get(i).getID()));
                        while (musicManager.player.getPlayingTrack().isSeekable()) {
                            musicManager.scheduler.nextTrack();
                        }
                    } catch (NullPointerException E) {

                    }

                }
                System.out.println("Music Stopped on all servers: Now On: " + SirBroBot.client.getConnectedVoiceChannels().size());

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                statusStopUpdate = sx.blah.discord.handle.obj.Status.stream("?commands", "https://www.twitch.tv/sirbrobot/profile");
                SirBroBot.client.changeStatus(statusStopUpdate);

            }
            

            if (Mcontent.startsWith(">pause")) {
                musicManager.player.setPaused(true);
            } 
            
            else if (Mcontent.startsWith(">resume")) {
                musicManager.player.setPaused(false);
            } 
            
            else if (Mcontent.equals(">play")) {
                message.reply("Being reworked under new Music system, >stream with a URL still works");
//                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
//                audio.setVolume((float) 0.13);
//
//                audio.queue(new File("src/songs/krewella - come and get it (razihel remix).mp3"));
//                audio.queue(new File("src/songs/Fuck Gravity (Original Mix).mp3"));
//                audio.queue(new File("src/songs/Turn Up (Original Mix).mp3"));
//                audio.queue(new File("src/songs/Teminiate.mp3"));//Teminite & Panda Eyes - Highscore
//                audio.queue(new File("src/songs/The Magician - Sunlight (Elephante Remix).mp3"));
//                audio.queue(new File("src/songs/Phoebe Ryan - Mine (Win & Woo Remix) Radio Edit.mp3"));
//                audio.queue(new File("src/songs/Bassist.mp3"));
//                audio.queue(new File("src/songs/I Am SOO Happy.mp3"));
//                audio.queue(new File("src/songs/Zircon - Just Hold On (Abstruse Remix).mp3"));
//                audio.queue(new File("src/songs/Maximum Overdrive.mp3"));

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
                event.getClient().getConnectedVoiceChannels().stream().filter((IVoiceChannel channel) -> channel.getGuild().equals(message.getGuild())).findFirst().ifPresent(IVoiceChannel::leave);
            } 
            
            else if (Mcontent.startsWith(">skip")) {
                if(Mcontent.equals(">skip")){
                    skipTrack(message.getAuthor(), message.getChannel());
                }
                
                else{
                    String trackSkip[] = Mcontent.split(">skip ");
                    String[] skipto = Mcontent.split(" ");
                    Integer result = Integer.valueOf(skipto[1]);
                    
                    skipMultipleTrackS(message.getAuthor(), message.getChannel(),result);
                    
                }
                
                
            } 
            
            else if (Mcontent.startsWith(">stop")) {
            try{
                while (!musicManager.player.getPlayingTrack().getIdentifier().equals("")){
                    musicManager.scheduler.nextTrack();
                }
                }
                catch(NullPointerException E){
                    System.out.println("Music Stopped on: "+event.getMessage().getGuild().getName());
                }
            } 
            
            else if (Mcontent.startsWith(">playlist")) {
                MessageBuilder messageBuilder = new MessageBuilder(SirBroBot.client);
                messageBuilder.withChannel(event.getMessage().getChannel());
                
                messageBuilder.appendContent("PLAYLIST: \n");
                messageBuilder.appendContent("Currently playing: **" + musicManager.player.getPlayingTrack().getInfo().title +"** Runtime:`"+musicManager.player.getPlayingTrack().getDuration()+"`");
                
               
                        
//                    if (audio.getPlaylist().size() > 1) {
//                        for (int i = 1; i < audio.getPlaylistSize(); i++) {
//                            messageBuilder.appendContent(i + ": " + audio.getPlaylist().get(i).getMetadata() + "\n");
//                        }
//                    }
                messageBuilder.send();
                    
                  
                    
//                AudioPlayer audio = AudioPlayer.getAudioPlayerForGuild(event.getMessage().getGuild());
//                System.out.println("Playlist reached");
//
//                System.out.println(audio.getPlaylistSize());
//
//                //{file=src\songs\
//                if (audio.getPlaylistSize() > 0) {
//                    
//                    if (audio.getCurrentTrack().getMetadata().toString().contains("file=src")) {
//
//                        String songName = audio.getCurrentTrack().getMetadata().toString().replace("{file=src\\songs\\", "").replace("}", "");
//
//                        messageBuilder.append("PLAYLIST: \n");
//                        messageBuilder.append("Currently playing: ").append(songName).append("\n");//+ " || " + audio.getCurrentTrack().getCurrentTrackTime() + " / " + audio.getCurrentTrack().getTotalTrackTime() + " ||\n"
//
//                        if (audio.getPlaylist().size() > 1) {
//                            for (int i = 1; i < audio.getPlaylistSize(); i++) {
//                                messageBuilder.append(i).append(": ").append(audio.getPlaylist().get(i).getMetadata().toString().replace("{file=src\\songs\\", "").replace("}", "").replace(".mp3", "")).append("\n");
//                            }
//                        }
//
//                        Messages.send(messageBuilder.toString(), chan);
//                    } else {
//                        messageBuilder.append("PLAYLIST: \n");
//                        messageBuilder.append("Currently playing: ").append(audio.getCurrentTrack().getMetadata().toString()).append("\n");//+ " || " + audio.getCurrentTrack().getCurrentTrackTime() + " / " + audio.getCurrentTrack().getTotalTrackTime() + " ||\n"
//
//                        if (audio.getPlaylist().size() > 1) {
//                            for (int i = 1; i < audio.getPlaylistSize(); i++) {
//                                messageBuilder.append(i).append(": ").append(audio.getPlaylist().get(i).getMetadata()).append("\n");
//                            }
//                        }
//                        Messages.send(messageBuilder.toString(), chan);
//                    }
//                }

            } 
            
            else if (Mcontent.startsWith(">search")) {
                
               
                String[] videoSearch = Mcontent.split("search");
                String temp;
                IMessage tempmessage = null;
                MessageBuilder messageBuilder = new MessageBuilder(event.getClient());
                if (videoSearch.length == 2) {
                    try {
                        message.delete();
                    } catch (MissingPermissionsException ignored) {
                    }
                    tempmessage = messageBuilder.withChannel(chan).withContent("Searching `YouTube` for terms: `" + videoSearch[1].trim()+"`").send();
                    Thread.sleep(2500);
                    loadYTSearch(message.getChannel(), videoSearch[1], message.getAuthor());
                    
                } else {
                    temp = null;
                    message.reply("Command error. Syntax is `>search: keywords`");
                }

            } 
            
            else if (Mcontent.startsWith(">stream")) {
                if(musicManager.getAudioProvider().getChannels()==0){
                message.reply(":warning: **WARNING** :warning: Control volume on the `User Volume` menu from Discord. Access by right clicking on the User menu on SirBroBots name. `Waiting 10 seconds to play any audio` (Message may be deleivered twice for redundancy)");
                Thread.sleep(10000);
                }
                AudioSourceManagers.registerRemoteSources(playerManager);
                AudioSourceManagers.registerLocalSource(playerManager);

                
                String[] urlContent = message.getContent().split(">stream");

                if (urlContent.length < 1) {
                    message.reply("Put space between >stream and URL");
                } else {
                    loadAndPlay(message.getChannel(), urlContent[1].trim(), message.getAuthor());
                }
            }
        }
    }

    
    //LAVAPLAYER
              
        private synchronized GuildMusicManager getGuildAudioPlayer(IGuild guild) {
        long guildId = Long.parseLong(guild.getID());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setAudioProvider(musicManager.getAudioProvider());

        return musicManager;
    }
 
       private void loadAndPlay(final IChannel channel, final String trackUrl, IUser author) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                Messages.send(author.mention()+" Added **" + track.getInfo().title +"** ("+ SirBroBot.getTimeFromMilis(track.getInfo().length)+")", channel);
                
                play(channel.getGuild(), musicManager, track);
             
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                if (playlist.getTracks().size() > 200) {
                    AudioTrack firstTrack = playlist.getSelectedTrack();

                    if (firstTrack == null) {
                        firstTrack = playlist.getTracks().get(0);
                    }

                    long playlistMilis = 0;
                    int counter = 0;
                    for (AudioTrack track : playlist.getTracks()) {

                        playlistMilis += playlist.getTracks().get(counter).getDuration();
                        counter += 1;
                    }
                    Messages.send(author.mention()+" Added `" + playlist.getName() + "` with `" + playlist.getTracks().size() + "` tracks `(" + SirBroBot.getTimeFromMilis(playlistMilis) + ")`\n"
                            + "PLAYLIST OVER 200 Songs, choose smaller playlist **(ONLY PLAYING FIRST SONG)**", channel);

                    play(channel.getGuild(), musicManager, firstTrack);
                } 
                else {

                    long playlistMilis = 0;
                    int counter = 0;
                    for (AudioTrack track : playlist.getTracks()) {
                        playlistMilis += playlist.getTracks().get(counter).getDuration();
                        musicManager.scheduler.queue(track);
                        counter += 1;
                    }
                    Messages.send(author.mention()+" Added `" + playlist.getName() + "` with `" + playlist.getTracks().size() + "` tracks and `" + SirBroBot.getTimeFromMilis(playlistMilis) + "` of playtime", channel);
                }
            }

            @Override
            public void noMatches() {
                Messages.send("Nothing found by " + trackUrl, channel);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Messages.send("Could not play: " + exception.getMessage(), channel);
            }
        });
    }
       
       
       private void loadYTSearch(final IChannel channel, final String query, IUser author) {
           String id = "NONE";
           String url = "NONE";
           final String finalURL;
        try {
            id = execCmd("youtube-dl ytsearch:\"" + query + "\" --get-id").trim();
            url = "http://www.youtube.com/watch?v="+id;
            
        } catch (IOException ex) {
            Logger.getLogger(MainListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        finalURL = url; 
        
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        
        playerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                Messages.send(author.mention()+" Added **" + track.getInfo().title +"** ("+ SirBroBot.getTimeFromMilis(track.getInfo().length)+") :: <"+finalURL+">", channel);
                
                play(channel.getGuild(), musicManager, track);   
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                String results = "NULL";
                if (playlist.getTracks().size() > 200) {
                    AudioTrack firstTrack = playlist.getSelectedTrack();

                    if (firstTrack == null) {
                        firstTrack = playlist.getTracks().get(0);
                    }

                    long playlistMilis = 0;
                    int counter = 0;
                    for (AudioTrack track : playlist.getTracks()) {

                        playlistMilis += playlist.getTracks().get(counter).getDuration();
                        counter += 1;
                    }
                    Messages.send(author.mention()+" Added `" + playlist.getName() + "` with `" + playlist.getTracks().size() + "` tracks `(" + SirBroBot.getTimeFromMilis(playlistMilis) + ")`\n"
                            + "PLAYLIST OVER 200 Songs, choose smaller playlist **(ONLY PLAYING FIRST SONG)**", channel);

                    play(channel.getGuild(), musicManager, firstTrack);
                } else {

                    long playlistMilis = 0;
                    int counter = 0;
                    for (AudioTrack track : playlist.getTracks()) {
                        playlistMilis += playlist.getTracks().get(counter).getDuration();
                        musicManager.scheduler.queue(track);
                        counter += 1;
                    }
                    Messages.send(author.mention()+" Added `" + playlist.getName() + "` with `" + playlist.getTracks().size() + "` tracks and `" + SirBroBot.getTimeFromMilis(playlistMilis) + "` of playtime", channel);
                }
            }

            @Override
            public void noMatches() {
                Messages.send("Nothing found by search of`" + query+"`", channel);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                Messages.send("Could not play: " + exception.getMessage(), channel);
            }
        });
}
       
    private void play(IGuild guild, GuildMusicManager musicManager, AudioTrack track) {
    connectToFirstVoiceChannel(guild.getAudioManager());

    musicManager.scheduler.queue(track);
}

  private void skipTrack(IUser author, IChannel channel) {
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    musicManager.scheduler.nextTrack();

    Messages.send(author.mention()+" Skipped to next track.", channel);
  }
  
  private void skipMultipleTrackS(IUser author, IChannel channel, Integer skips) {
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    
    for(int i = 0; i < skips+1; i++){
        musicManager.scheduler.nextTrack();
    }

    Messages.send(author.mention()+" Skipped "+skips.toString()+ " tracks.", channel);
  }


  
  private static void connectToFirstVoiceChannel(IAudioManager audioManager) {
    for (IVoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
      if (voiceChannel.isConnected()) {
        return;
      }
    }

    for (IVoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
      try {
        voiceChannel.join();
      } catch (MissingPermissionsException e) {

      }
    }
  }
  //END LAVAPLAYER

  
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
            SirBroBot.LOGGER.error(null, ex);
        }

        try {
            Process process = builder.start();
            try {
                CompletableFuture.runAsync(() -> logStream(process.getErrorStream()));

                AudioPlayer.Track track = new AudioPlayer.Track(AudioSystem.getAudioInputStream(process.getInputStream()));
                track.getMetadata().put(URLSongname, execCmd("youtube-dl " + id + " --get-duration"));

                audio.queue(track);

            } catch (UnsupportedAudioFileException e) {
                SirBroBot.LOGGER.warn("Could not queue audio", e);
                process.destroyForcibly();
            }

        } catch (IOException e) {
            SirBroBot.LOGGER.warn("Could not start process", e);
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
                SirBroBot.LOGGER.warn("Could not queue audio", e);
                process.destroyForcibly();
            }
        } catch (IOException e) {
            SirBroBot.LOGGER.warn("Could not start process", e);
        }

        return results;

    }

    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next().trim() : "";
    }
    


    private BufferedReader newProcessReader(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
    }

    private void logStream(InputStream stream) {
        try (BufferedReader input = newProcessReader(stream)) {
            String line;
            while ((line = input.readLine()) != null) {
                SirBroBot.LOGGER.info("[yt-dl] " + line);
            }
        } catch (IOException e) {
            SirBroBot.LOGGER.warn("Could not read from stream", e);
        }
    }

    public static String getUptime() {
        return SirBroBot.getUptime();
    }
    
    public static int getMessagesSeen() {
        return messagesSeen;
    }
    
    

    public static long getUsers() {
        long totalUsers = 0;
        for (IGuild guild : SirBroBot.client.getGuilds()) {
            totalUsers += guild.getUsers().size();
        }
        return totalUsers;
    }

    public String GetCurrentDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

//    public void deleteLastMessage(String ChannelID) throws InterruptedException{
//        try {
//            //client.getChannelByID(VADdata.get(event.getChannel().getGuild().getID()).toString()).getMessages().getLatestMessage().delete()
//            client.getChannelByID(ChannelID).getMessages().getLatestMessage().;
//        } catch (MissingPermissionsException | HTTP429Exception | DiscordException ex) {
//            SirBroBot.LOGGER.error(null, ex);
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
