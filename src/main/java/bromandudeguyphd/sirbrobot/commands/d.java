package bromandudeguyphd.sirbrobot.commands;

import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import bromandudeguyphd.sirbrobot.convertDigitsToWords;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author BroManDudeGuyPhD
 * created on Oct.20.2017
 */
public class d implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        
        int random = (int) (Math.random() * Integer.parseInt(args[0]) + 1);
        
        String[] results = convertDigitsToWords.NumberToSingalDigits(random).split(" ");
        String finalNumber = "";
        for(int i = 0; i < results.length; i++){
            finalNumber += ":"+results[i]+":";
        }
        
        Messages.send(finalNumber, channel);
        
    }

    @Override
    public String getName() {
        return "d";
    }

    @Override
    public String getDescription() {
        return "D&D Dice";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}