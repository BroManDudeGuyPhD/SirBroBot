package bromandudeguyphd.sirbrobot.commands;

import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import bromandudeguyphd.sirbrobot.fileIO;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * @author BroManDudeGuyPhD (using Arsen's structuring) 
 * created on Aug.4.17.
 */
public class ServerInfo implements Command {

    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        EmbedBuilder embed = new EmbedBuilder().setLenient(true);
        embed.withTitle(channel.getGuild().getName());
        embed.withDescription("Owner: " + channel.getGuild().getOwner().mention() + "\n");
        embed.appendField("Created on: ", channel.getGuild().getCreationDate().toString(),false);
        embed.appendField("Members: ", "" + channel.getGuild().getUsers().size(), false);
        embed.appendField("Region: ", channel.getGuild().getRegion().getName(), false);
        embed.appendField("Custom Emojis: ", "" + channel.getGuild().getEmojis().size(), false);

        embed.withFooterText(" ?serverinfo ");
        embed.withFooterIcon(sender.getAvatarURL());

        embed.withColor(Color.red);

        embed.withThumbnail(channel.getGuild().getIconURL());

        Messages.sendWithEmbed("", embed.build(), false, channel);
    }

    @Override
    public String getName() {
        return "serverinfo";
    }

    @Override
    public String getDescription() {
        return "Info about the server";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
