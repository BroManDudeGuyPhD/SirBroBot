package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.imagewriting.ImageCaption;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.tokens;
import java.io.File;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * Flashback command class
 * @author BroManDudeGuyPhD on 10.3.2017
 */
public class Flashback implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        //Join message together (This is stupid I know temp solution)
        
        String message = String.join(" ", args);
        File flashbackCaption = ImageCaption.create(tokens.flashbackDirectory(), message, sender);
        
        Messages.sendWithImage("Flashback Memem with message text from "+sender.mention(), channel, flashbackCaption);


    }

    @Override
    public String getName() {
        return "flashback";
    }

    @Override
    public String getDescription() {
        return "adds text to a WWII flashback meme";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
