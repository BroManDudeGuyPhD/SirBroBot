package bromandudeguyphd.sirbrobot.commands.commands;

import bromandudeguyphd.sirbrobot.Aligner;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Lists all commands to the user
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class Commands implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        Map<String, String> commands = new LinkedTreeMap<>();
        final int[] i = {0};
        SirBroBot.dispatcher.getCommands().stream()
                .sorted(((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())))
                .filter(command -> command.getType() == CommandTypes.NORMAL)
                .forEach(command -> {
                    String num = String.valueOf(++i[0]);
                    if(num.length() < 2)
                        num = ' ' + num;
                    commands.put(num +". " + command.getType().getTrigger() + command.getName(), command.getDescription());
                });
        String response = "**General Use commands: **\n```xl\n<required paramater> | {optional paramater}\n```\n``` " +
                Aligner.align(commands, ":") +
                "\n```\n";
        response += "Things I will respond to with an @SirBroBot mention behind it: \n```\n"
                + StringUtils.join(SirBroBot.dispatcher.getCommands().stream().filter(command -> command.getType() == CommandTypes.MENTION).collect(Collectors.toList()), ", ")
                + "\n```";
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
