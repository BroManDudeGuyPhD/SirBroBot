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
public class pi implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        
        Messages.send("PI to "+args[0]+" places\n ```"+ execYTcmd("pi "+args[1]).toString()+"```", channel);
        
    }

    @Override
    public String getName() {
        return "pi";
    }

    @Override
    public String getDescription() {
        return "returns pi up to desired place";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
    
}
