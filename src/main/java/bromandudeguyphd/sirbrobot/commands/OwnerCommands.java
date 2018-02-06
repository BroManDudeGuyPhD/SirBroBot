package bromandudeguyphd.sirbrobot.commands;

import bromandudeguyphd.sirbrobot.Aligner;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Lists all commands to the user
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class OwnerCommands implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Map<String, String> commands = new HashMap<>();
        SirBroBot.dispatcher.getCommands().stream().filter(command -> command.getType() == CommandTypes.OWNER)
                .forEach(command -> commands.put(command.getName(), command.getDescription()));
        String response = "**Owner commands: **\n```xl\n<required paramater> | {optional paramater}\n```\n```" +
                Aligner.align(commands, ":") +
                "\n```";
        Messages.send(response, channel);
    }

    @Override
    public String getName() {
        return "ocommands";
    }

    @Override
    public String getDescription() {
        return "Lists owner commands";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
