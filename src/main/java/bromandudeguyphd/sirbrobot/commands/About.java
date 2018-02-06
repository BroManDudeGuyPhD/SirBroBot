package bromandudeguyphd.sirbrobot.commands;
import bromandudeguyphd.sirbrobot.DiscordListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

/**
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class About implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        EmbedBuilder embed = new EmbedBuilder().setLenient(true);
                
                embed.withTitle("About Me\n");
                embed.withUrl("https://sirbrobot.com");
                embed.appendField("Servers:  ", ""+SirBroBot.client.getGuilds().size(), false);
                int totalChannels = SirBroBot.client.getVoiceChannels().size()+SirBroBot.client.getChannels(false).size();
                embed.appendField("Total Channels: "+ totalChannels," Voice: "+ SirBroBot.client.getVoiceChannels().size()+"  |  Text:"+SirBroBot.client.getChannels(false).size() ,false);
                
                long totalUsers = DiscordListener.getUsers();
                
                double guildUsers = channel.getGuild().getUsers().size(); 
                double guildPercent = guildUsers/totalUsers; 
                String guildUsersString = ""+guildUsers;
                DecimalFormat decimalFormat = new DecimalFormat("0.000");
                
                embed.appendField("Total Users: "+ DiscordListener.getUsers(), channel.getGuild().getName()+": "+ guildUsersString.replace(".0", "") + " users  |  "+decimalFormat.format(guildPercent*100)+"% of userbase", false);
                embed.appendField("Messages Seen: ", "" + DiscordListener.getMessagesSeen(), false);
                embed.appendField("Uptime: ", "" + SirBroBot.getUptime(), false);
                embed.appendField("Commands Executed: ", "" + DiscordListener.getUseCounter(), false);
                embed.appendField("Programmer: ",  DiscordListener.root.mention(), false);
                embed.appendField("Links: ", "" + "Website: https://sirbrobot.com \nYouTube https://www.youtube.com/channel/UCZi_pzKLVb5zvTmDOCEMbtQ \nTwitter: https://twitter.com/SirBroBotThe1st", false);
                embed.withFooterText(" ?about ");
                embed.withFooterIcon(sender.getAvatarURL());
                embed.withColor(Color.red);
                embed.withTimestamp(LocalDateTime.now());
                
                Messages.sendWithEmbed("", embed.build(), false, channel);
        
           
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Some info about me";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
