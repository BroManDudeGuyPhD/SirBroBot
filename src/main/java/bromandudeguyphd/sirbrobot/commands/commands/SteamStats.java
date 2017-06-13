/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.sirbrobot.MainListener;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
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
 * Created by BroManDudeGuyPhD on 6.11.2017.
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
        
        // Get the first page
        HtmlPage steamAccountInfo = null;
        try {
            steamAccountInfo = webClient.getPage("http://steamcommunity.com/id/"+args[0]);
            
        } catch (IOException | FailingHttpStatusCodeException ex) {    
        }
 

        HtmlImage avatarURL = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[1]/div/div/div/div[2]/div/img");
        HtmlElement recentActivity = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[2]/div/div[2]/div[1]/div[1]/h2");
        HtmlElement status = steamAccountInfo.getFirstByXPath("html/body/div[1]/div[7]/div[2]/div/div[2]/div/div[1]/div[1]/div/div");
        
        //Account Info
        String accountInfo = "Error: Timed Out";
        
        //Account URL
        String imageURL = avatarURL.toString().replace("HtmlImage[<img src=\"", "").replace("\">]", "");
        
        if(recentActivity.asText().isEmpty()){
            
        }
        
        else if (!recentActivity.asText().isEmpty()){
        //Embed stats
        EmbedBuilder embed = new EmbedBuilder().ignoreNullEmptyFields();
        embed.appendField("Recent Activity:", recentActivity.asText(), false);
        embed.withTitle("Steam stats for "+args[0]+"\n");
        embed.withUrl("http://steamcommunity.com/id/"+args[0]);
        embed.withThumbnail(imageURL);
        embed.appendField("Status: ", status.asText(), false);          
        embed.withFooterText(" ?steamstats ");
        embed.withFooterIcon("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Steam_icon_logo.svg/1024px-Steam_icon_logo.svg.png");
        embed.withColor(Color.red);
        embed.withTimestamp(LocalDateTime.now());

        IMessage finalInfo = Messages.sendWithUpdatableEmbed("Getting account info...This can take a while so be patient :slight_smile:", embed.build(), false, channel);

        try {Thread.sleep(1000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
        
        
        webClient.getOptions().setJavaScriptEnabled(true);
            try {
                steamAccountInfo = webClient.getPage("https://www.mysteamgauge.com/account?username="+args[0]);
            } catch (IOException | FailingHttpStatusCodeException ex) {
                Logger.getLogger(SteamStats.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        HtmlElement element = null;
        
        for(int i = 0; i < 7; i++){
        try{
            webClient.waitForBackgroundJavaScript(2000);
            element = steamAccountInfo.getFirstByXPath("html/body/div[2]/div/div[1]/table/tbody/tr/td[2]/div[2]");
            accountInfo = element.asText().replace("you've", args[0]);
        }
            
        catch(NullPointerException | FailingHttpStatusCodeException e)
        { System.out.println("=========================================================TRY"+i); try {Thread.sleep(100);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}}
        
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
