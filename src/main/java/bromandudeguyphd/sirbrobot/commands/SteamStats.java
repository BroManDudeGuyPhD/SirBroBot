package bromandudeguyphd.sirbrobot.commands;
import bromandudeguyphd.sirbrobot.DiscordListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commandprep.Command;
import bromandudeguyphd.sirbrobot.commandprep.CommandTypes;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

/**
 * <br>
 * Created by BroManDudeGuyPhD on Nov.6.2017.
 */
public class SteamStats implements Command{
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);


        //HTML variables
        HtmlImage avatarURL = null;
        HtmlElement recentActivity = null;
        HtmlElement status = null;

        //String counterparts
        String STRavatarURL = null;
        String STRrecentActivity = null;
        String STRstatus = null;

        
        boolean failStatus = false;
        
        
          
    
        HtmlPage steamAccountInfo = null;
        try {
            steamAccountInfo = webClient.getPage("http://steamcommunity.com/id/" + args[0]);
            
            try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
            
            avatarURL = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[1]/div/div/div/div[2]/div/img");
        } catch (IOException | FailingHttpStatusCodeException ex) {
        } catch (NullPointerException e) {
            Messages.send("No user by the name " + args[0] + " found. " + "http://steamcommunity.com/id/" + args[0], channel);
            failStatus = true;
        }

        if (failStatus == false) {
            EmbedBuilder embed = new EmbedBuilder().setLenient(true);
            String accountInfo = null;
            
            
            try {
                
                recentActivity = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/h2");
                status = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[2]/div/div[1]/div[1]/div/div");

                //Set values to string within try catch
                STRavatarURL = avatarURL.toString();
                STRrecentActivity = recentActivity.toString();
                STRstatus = status.asText();

           } catch (NullPointerException e) {
                e.printStackTrace();
            }


                //Account Info
                accountInfo = "Error: Timed Out";

                //Avatar URL
                String imageURL = STRavatarURL.replace("HtmlImage[<img src=\"", "").replace("\">]", "");

                //Embed stats
                
                embed.appendField("Recent Activity:", STRrecentActivity, false);
                embed.withTitle("Steam stats for " + args[0] + "\n");
                embed.withUrl("http://steamcommunity.com/id/" + args[0]);
                embed.withThumbnail(imageURL);
                embed.appendField("Status: ", STRstatus, false);
                embed.withFooterText(" ?steamstats ");
                embed.withFooterIcon("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1024px-Steam_icon_logo.svg.png");
                embed.withColor(Color.red);
                embed.withTimestamp(LocalDateTime.now());

                
            
                IMessage finalInfo = Messages.sendWithUpdatableEmbed("Getting account info...This can take a while so be patient :slight_smile:", embed.build(), false, channel);

               try {Thread.sleep(50);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}

                webClient.getOptions().setJavaScriptEnabled(true);
                try {
                    steamAccountInfo = webClient.getPage("https://www.mysteamgauge.com/account?username=" + args[0]);
                } catch (IOException | FailingHttpStatusCodeException ex) {
                    Logger.getLogger(SteamStats.class.getName()).log(Level.SEVERE, null, ex);
                }

                HtmlElement element = null;

                for (int i = 0; i < 7; i++) {
                    try {
                        webClient.waitForBackgroundJavaScript(1500);
                        element = steamAccountInfo.getFirstByXPath("html/body/div[2]/div/div[1]/table/tbody/tr/td[2]/div[2]");
                        accountInfo = element.asText().replace("you've", args[0]);
                    } catch (NullPointerException | FailingHttpStatusCodeException e) {
                        System.out.println("=========================================================TRY" + i);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                    }

                }

                embed.appendField("Account Stats:", accountInfo, false);
                finalInfo.edit("All Data retrieved", embed.build());

            
        }
    }

    @Override
    public String getName() {
        return "steamstats";
    }

    @Override
    public String getDescription() {
        return "Get info about steam account";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }

    
}
