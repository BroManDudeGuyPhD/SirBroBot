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
 * Updated bt BroMan Feb.20.2018
 */
public class About implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        EmbedBuilder embed = new EmbedBuilder().setLenient(true);
                
                embed.withTitle("About Me\n");
                embed.withUrl("https://sirbrobot.com");
                embed.appendField("Servers:  ", ""+SirBroBot.client.getGuilds().size(), true);
                int totalChannels = SirBroBot.client.getVoiceChannels().size()+SirBroBot.client.getChannels(false).size();
                
                embed.withDesc("The most chivalrous Java Chatbot");
                embed.appendDesc("\n---------------------------------------------------------");
                long totalUsers = DiscordListener.getUsers();
                double guildUsers = channel.getGuild().getUsers().size(); 
                double guildPercent = guildUsers/totalUsers; 
                String guildUsersString = ""+guildUsers;
                DecimalFormat decimalFormat = new DecimalFormat("0.000");
                
                embed.appendField("Total Users: "+ DiscordListener.getUsers(), channel.getGuild().getName()+": "+ guildUsersString.replace(".0", "") + "\n"+decimalFormat.format(guildPercent*100)+"% of userbase \n   ", true);
                embed.appendField("Total Channels: "+ totalChannels," Voice: "+ SirBroBot.client.getVoiceChannels().size()+"  |  Text:"+SirBroBot.client.getChannels(false).size() ,true);
                embed.appendField("Messages Seen: ", "" + DiscordListener.getMessagesSeen(), true);
                embed.appendField("Uptime: ", "" + SirBroBot.getUptime(), true);
                embed.appendField("Commands Executed: ", "" + DiscordListener.getUseCounter(), true);
                embed.appendField("Programmer: ",  DiscordListener.root.mention(), false);
                embed.appendField("Links", "" + ":earth_americas:[ Website](https://sirbrobot.com) \n<:youtube1:415519970001158146>[ YouTube](https://www.youtube.com/channel/UCZi_pzKLVb5zvTmDOCEMbtQ) \n<:twitter:415532664850874379>[ Twitter](https://twitter.com/SirBroBotThe1st)", false);
                embed.withFooterText(" ?about ");
                embed.withFooterIcon("https://www.sitewelder.com/art2012/logo-big-information.png");
                embed.withColor(Color.red);
                //embed.withThumbnail("https://www.sitewelder.com/art2012/logo-big-information.png");
                
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
