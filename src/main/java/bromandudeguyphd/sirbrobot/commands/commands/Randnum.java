package bromandudeguyphd.sirbrobot.commands.commands;

import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.convertDigitsToWords;
import java.util.Random;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author BroManDudeGuyPhD
 * created on Oct.20.2017
 */
public class Randnum implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        
       
                int Low = 0;
                int High = 0;
                int Result;
                Random r = new Random();

                boolean resultsValid;

                try {
                    Low = Integer.parseInt(args[0]);
                    resultsValid = true;
                } catch (NumberFormatException ignored) {
                    Messages.send(":warning:` I dont recognize` **" + args[0] + "** `as a valid number `:warning:", channel);
                    resultsValid = false;
                }

                try {
                    High = Integer.parseInt(args[1]);
                    resultsValid = true;
                } catch (NumberFormatException ignored) {
                    Messages.send(":warning:` I dont recognize` **" + args[1] + "** `as a valid number `:warning:", channel);
                    resultsValid = false;
                }

                if (resultsValid) {
                    try {
                        if (Low > High) {
                            int temp = Low;
                            Low = High;
                            High = temp;
                        }

                        Result = r.nextInt(High - Low) + Low;
                        Messages.send("Random number between " + args[0] + " and " + args[1] + "\n**" + Result + "**", channel);
                    } catch (IllegalArgumentException | UnsupportedOperationException ignored) {
                        Messages.send(":warning:`Something went wrong`:warning:", channel);
                    }

                }
        
    }

    @Override
    public String getName() {
        return "randnum";
    }

    @Override
    public String getDescription() {
        return "Generate random number between 2 inputs";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
    
}
