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
public class ASCII implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        String message = String.join(" ", args);
        
        Messages.send("```"+execYTcmd("figlet "+message.replace("?ascii", "").replace("?ASCII", "")).toString()+"```", channel);
        
    }

    @Override
    public String getName() {
        return "ascii";
    }

    @Override
    public String getDescription() {
        return "Converst text to ASCII bubble text";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
    
}