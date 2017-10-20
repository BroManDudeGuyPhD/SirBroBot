/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot.commands.commands;

import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.convertDigitsToWords;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author aaf8553
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