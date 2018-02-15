package bromandudeguyphd.sirbrobot.commands;

import bromandudeguyphd.sirbrobot.DiscordListener;
import static bromandudeguyphd.sirbrobot.DiscordListener.getUptime;
import static bromandudeguyphd.sirbrobot.DiscordListener.getUsers;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author BroManDudeGuyPhD
 * created on Feb.15.2018
 */
public class tweetabout implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            twitter.updateStatus(
                    "Discord Servers: " + SirBroBot.client.getGuilds().size() + "\n"
                    + "Voice Channels: " + SirBroBot.client.getVoiceChannels().size() + "\n"
                    + "Text Channels: " + SirBroBot.client.getChannels(false).size() + "\n"
                    + "Total Users: " + getUsers() + "\n"
                    + "Messages Seen: " + DiscordListener.messagesSeen + "\n"
                    + "Uptime: " + getUptime() + "\n");

        } catch (TwitterException ex) {
            Messages.send("Error tweeting", channel);
        }
        Messages.send("Server stats sent!", channel);

    }

    @Override
    public String getName() {
        return "tweetabout";
    }

    @Override
    public String getDescription() {
        return "Tweet Discord Stats";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.BROMAN;
    }
    
}