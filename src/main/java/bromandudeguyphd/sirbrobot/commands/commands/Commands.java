package bromandudeguyphd.sirbrobot.commands.commands;

import bromandudeguyphd.sirbrobot.Aligner;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Lists all commands to the user
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class Commands implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Map<String, String> commands = new HashMap<>();
        SirBroBot.dispatcher.getCommands().stream().filter(command -> command.getType() == CommandTypes.NORMAL)
                .forEach(command -> commands.put(command.getName(), command.getDescription()));
        String response = "**General Use commands: **\n```xl\n<required paramater> | {optional paramater}\n```\n```" +
                Aligner.align(commands, ":") +
                "\n```";
        Messages.send(response, channel);
    }

    @Override
    public String getName() {
        return "commands";
    }

    @Override
    public String getDescription() {
        return "See this list ";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
