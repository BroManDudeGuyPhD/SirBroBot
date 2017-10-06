package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.imagewriting.ImageCaption;
import bromandudeguyphd.sirbrobot.DiscordListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.nlpLibrary;
import bromandudeguyphd.sirbrobot.tokens;
import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

/**
 * <br>
 * Created by BroManDudeGuyPhD on 10.3.2017
 */
public class Flashback implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        //Join message together (This is stupid I know temp solution)
        
        String message = String.join(" ", args);
        File flashbackCaption = ImageCaption.create(tokens.flashbackDirectory(), message, channel.getGuild());
        
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
