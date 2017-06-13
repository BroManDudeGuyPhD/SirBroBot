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
public class MusicCommands implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Map<String, String> commands = new HashMap<>();
        SirBroBot.dispatcher.getCommands().stream().filter(command -> command.getType() == CommandTypes.MUSIC)
                .forEach(command -> commands.put(command.getName(), command.getDescription()));
        String response = "**Music Commands\uD83C\uDFB5**\n```xl\n<required paramater> | {optional paramater}\n```\n```" +
                Aligner.align(commands, ":") +
                "\n```";
        Messages.send(response, channel);
    }

    @Override
    public String getName() {
        return "mcommands";
    }

    @Override
    public String getDescription() {
        return "Lists music commands";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
