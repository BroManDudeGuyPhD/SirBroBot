package bromandudeguyphd.sirbrobot.commands;

import static bromandudeguyphd.sirbrobot.DiscordListener.execYTcmd;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author BroManDudeGuyPhD
 * created on Oct.20.2017
 */
public class Ping implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        
        Messages.send("PONG \n```"+execYTcmd("ping -c 4 www.google.com").toString()+"```", channel);
        
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "pings Google and returns response time";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
    
}
