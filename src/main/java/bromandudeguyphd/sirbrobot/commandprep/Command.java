package bromandudeguyphd.sirbrobot.commandprep;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/**
 * Represents a command
 */
public interface Command {

    /**
     * Dispatches discord command
     *
     * @param args    Arguments
     * @param sender  The User who sent the command
     * @param channel The channel the command was sent it
     */
    void execute(String[] args, IUser sender, IChannel channel);

    /**
     * Returns the command trigger
     * @return The trigger
     */
    String getName();

    /**
     * Represents a simple description
     * @return A short summary of the command
     */
    String getDescription();

    /**
     * Gets the command type
     * @return The command type
     */
    CommandTypes getType();
}
