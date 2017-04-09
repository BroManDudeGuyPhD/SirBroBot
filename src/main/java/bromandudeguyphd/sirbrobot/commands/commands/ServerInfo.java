package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import bromandudeguyphd.sirbrobot.fileIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 * <br>
 * Created by BroManDudeGuyPhD (using Arsen's structuring) on 4.8.17.
 */
public class ServerInfo implements Command{
 @Override
    public void execute(String[] args, IUser sender, IChannel channel) {


        IGuild guildID = channel.getGuild();
        String guildName = guildID.getName();
        String iconUrl = guildID.getIconURL();

        if (iconUrl.contains("/null.jpg")) {
            try {
                File serverIcon = new File("src/images/serverIcons/null.jpg");
                channel.sendFile(serverIcon);
                Messages.send("\n```" + "\nServer Name: " + guildName + "\n"
                        + "Owner: " + channel.getGuild().getOwner().getName() + "\n"
                        + "Creation Date: " + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + " "
                        + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n"
                        + "Members: " + guildID.getUsers().size() + "\n"
                        + "Region: " + guildID.getRegion().getName() + "\n"
                        + "-------------------------------------------------------------\n"
                        + "```", channel);
                System.out.println("Server " + channel.getGuild().getName() + " has no Icon, provided one");
            } catch (FileNotFoundException | DiscordException | RateLimitException ex) {
                
                Messages.send("There was an error executing the `?serverinfo` command", channel);
            } catch (MissingPermissionsException ex) {
                Messages.send("Error executing the `?serverinfo` command. Bot has no permission to upload images in channel "+channel.getName()+".", channel);
            }

        } else {
            try {
                System.out.println("Server Icon Saved for " + channel.getGuild().getName());
                File serverIcon = new File("src/images/serverIcons/" + guildName + ".jpg");
                fileIO.saveImage(iconUrl, guildName + ".jpg", "src/images/serverIcons/");
                channel.sendFile(serverIcon);

                Messages.send("\n```" + "\nServer Name: " + guildName + "\n"
                        + "Owner: " + channel.getGuild().getOwner().getName() + "\n"
                        + "Creation Date: " + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + " "
                        + guildID.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n"
                        + "Members: " + guildID.getUsers().size() + "\n"
                        + "Region: " + guildID.getRegion().getName() + "\n"
                        + "-------------------------------------------------------------\n"
                        + "```",channel);
                fileIO.saveImage(iconUrl, guildName, "src/images/serverIcons/");
            } catch (IOException | DiscordException | RateLimitException ex) {
                Messages.send("There was an error executing the `?serverinfo` command", channel);
            }
            catch (MissingPermissionsException ex) {
                Messages.send("Error executing the `?serverinfo` command. Bot has no permission to upload images in channel "+channel.getName()+".", channel);
            }

        }
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

