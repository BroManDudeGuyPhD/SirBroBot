package bromandudeguyphd.sirbrobot.commandprep;

import bromandudeguyphd.sirbrobot.commands.SteamStats;
import bromandudeguyphd.sirbrobot.commands.About;
import bromandudeguyphd.sirbrobot.commands.Randnum;
import bromandudeguyphd.sirbrobot.commands.Flashback;
import bromandudeguyphd.sirbrobot.commands.Knight;
import bromandudeguyphd.sirbrobot.commands.Ivan;
import bromandudeguyphd.sirbrobot.commands.d;
import bromandudeguyphd.sirbrobot.commands.Ping;
import bromandudeguyphd.sirbrobot.commands.Github;
import bromandudeguyphd.sirbrobot.commands.ASCII;
import bromandudeguyphd.sirbrobot.commands.ServerInfo;
import bromandudeguyphd.sirbrobot.DiscordListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import sx.blah.discord.api.events.IListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 * New and revised SirBroBot command framework
 */
public class CommandDispatcher implements IListener<MessageReceivedEvent> {
    private Set<Command> commands = new HashSet<>();
    

    public CommandDispatcher() {
        //registerCommand(new Commands());
        //registerCommand(new OwnerCommands());
        //registerCommand(new MusicCommands());
        registerCommand(new About());
        registerCommand(new ServerInfo());
        registerCommand(new SteamStats());
        registerCommand(new Ivan());
        registerCommand(new Flashback());
        registerCommand(new Randnum());
        registerCommand(new d());
        registerCommand(new Ping());
        registerCommand(new ASCII());
        registerCommand(new Knight());
        registerCommand(new Github());
    }

    /**
     * Creates a lone of the command set
     *
     * @return The command sets clone
     */
    public Set<Command> getCommands() {
        Set<Command> ret = new HashSet<>();
        ret.addAll(commands);
        return ret;
    }

    /**
     * Adds a new command to the dispatcher
     *
     * @param c The new command
     * @return True if there is no conflict, false otherwise
     */
    public boolean registerCommand(Command c) {
        if (commands.stream().filter(command -> command.getName().equalsIgnoreCase(c.getName())).findFirst().isPresent())
            return false;
        commands.add(c);
        return true;
    }

    @Override
    public void handle(MessageReceivedEvent chatEvent) {
        String msg = chatEvent.getMessage().getContent();
        commands.stream().filter(comm -> msg.toLowerCase().startsWith(comm.getType().getTrigger()))
                .filter(comm -> msg.equalsIgnoreCase(comm.getType().getTrigger() + comm.getName()) ||
                        msg.toLowerCase().startsWith(comm.getType().getTrigger() + comm.getName() + " "))
                .filter(command -> command.getType().getFilter().test(chatEvent.getMessage().getGuild(), chatEvent.getMessage().getAuthor()))
                .forEach(comm -> {
                    String toSplit = msg.substring(comm.getType().getTrigger().length() + comm.getName().length());
                    if (!msg.equalsIgnoreCase(comm.getType().getTrigger() + comm.getName())) {
                        toSplit = toSplit.substring(1);
                    }
                    String[] args = toSplit.split("\\ ");
                    SirBroBot.LOGGER.error("Dispatching command '" + comm.getType().getTrigger() +
                            comm.getName() + "'! Sender: " + chatEvent.getMessage().getAuthor().getName() + "! Split: '" +
                            toSplit + "', " + Arrays.toString(args));
                    DiscordListener.addToUseCounter();
                    try {
                        comm.execute(args, chatEvent.getMessage().getAuthor(), chatEvent.getMessage().getChannel());
                    } catch (Exception e) {
                        Messages.sendException("***Internal exception occured while trying to proccess your command!***", e, chatEvent.getMessage().getChannel());
                    }
                });
    }

    /**
     * Gets the usage counter
     *
     * @return Usage counter, number of commands ran since boot
     */
    
}
