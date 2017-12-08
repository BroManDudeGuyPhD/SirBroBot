package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.tokens;
import java.io.File;
import java.util.Random;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * <br>
 * Created by BroManDudeGuyPhD on 11.7.2017
 */
public class Knight implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        final File dir = new File(tokens.ivanDirectory());
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
