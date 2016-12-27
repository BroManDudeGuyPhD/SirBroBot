package bromandudeguyphd.sirbrobot.commands.commands;

import bromandudeguyphd.sirbrobot.MainListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class About implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Messages.send("```"
                + "About SirBroBot\n"
                + "----------------\n"
                + "Servers: " + channel.getClient().getGuilds().size() + "\n"
                + "Voice Channels: " + channel.getClient().getVoiceChannels().size() + "\n"
                + "Text Channels: " + channel.getClient().getChannels(false).size() + "\n"
                + "Total Users: " + MainListener.getUsers() + "\n"
                + "Messages Seen: " + MainListener.getMessagesSeen() + "\n"
                + "Uptime: " + new SimpleDateFormat("DDD HH:mm:ss").format(new Date(MainListener.getUptime())) + "\n"
                + "My server (Join me!): https://discord.gg/0wCCISzMcKMkfX88 \n"
                + "----------------\n"
                + "Programmer: BroManDudeGuyPhD#5846 \n"
                + "```"
                + "Twitter account: <https://twitter.com/SirBroBotThe1st>\n"
                + "Website: <http://bootswithdefer.tumblr.com/SirBroBot>\n", channel);
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Some info about me";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
