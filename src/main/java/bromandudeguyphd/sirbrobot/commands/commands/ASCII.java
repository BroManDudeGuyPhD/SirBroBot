package bromandudeguyphd.sirbrobot.commands.commands;

import static bromandudeguyphd.sirbrobot.DiscordListener.execYTcmd;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
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
        
        Messages.send("```"+execYTcmd("figlet "+message.replace("?ascii", "")+message.replace("?ASCII", "")).toString()+"```", channel);
        
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