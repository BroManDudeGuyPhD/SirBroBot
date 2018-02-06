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
public class factor implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        
        Messages.send("Factors of "+args[1]+"\n ```"+ execYTcmd("factor "+args[1]).toString()+"```", channel);
        
    }

    @Override
    public String getName() {
        return "factor";
    }

    @Override
    public String getDescription() {
        return "get factors of a number";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
    
}