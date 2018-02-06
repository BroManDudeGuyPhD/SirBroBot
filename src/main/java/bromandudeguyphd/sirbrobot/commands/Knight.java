package bromandudeguyphd.sirbrobot.commands;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import bromandudeguyphd.sirbrobot.tokens;
import java.io.File;
import java.util.Random;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * <br>
 * Created by BroManDudeGuyPhD on Nov.7.2017
 */
public class Knight implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        final File dir = new File(tokens.knightDirectory());
        File[] files = dir.listFiles();
        Random rand = new Random();
        File file = files[rand.nextInt(files.length)];
        Messages.sendWithImage("", channel, file);


    }

    @Override
    public String getName() {
        return "knight";
    }

    @Override
    public String getDescription() {
        return "Gets a Random Knight image";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
