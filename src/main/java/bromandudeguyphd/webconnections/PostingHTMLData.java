/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.webconnections;

import bromandudeguyphd.sirbrobot.SirBroBot;
import bromandudeguyphd.sirbrobot.SirBroBot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PostingHTMLData {

    public void sendReq(int serverCount) throws MalformedURLException, IOException {

        String description ="<p>Currently on <b>"+SirBroBot.client.getGuilds().size()+" servers </b>\nSirBroBot is the most Chivalrous, java based Discord API compliant bot for <b>MUSIC</b> and Discord Server administration you will ever encounter. "
                + "NOTE: Main development occuring by BroManDudeGuyPhD, who is in college. Service will be spotty here and there. I promise when the bot can have my full priority, it will, but ultimately I am paying for college so that comes first :) <b> Much love for the support </b></p> \n"
                + "\n"
                + "<a class=\"btn btn-primary\" href=\"http://bootswithdefer.tumblr.com/sirbrobot\" target=\"_blank\">SirBroBot Website</a>\n"
                + "\n"
                + "<a class=\"btn btn-primary\" href=\"https://discord.gg/0wCCISzMcKMkfX88\" target=\"_blank\">SirBroBot's Server</a>\n"
                + "\n" 
                + "<p>\n"
                + "<ul>\n"
                + "<li>Music Streaming + Youtube Search</li>\n"
                + "<li>Image utilities, including Microsoft's image recognition</li>\n"
                + "<li>Role Management</li>\n"
                + "<li>D&D Dice + Rand # Generator</li>\n"
                + "<li>Twitter search</li>\n"
                + "<li>Voice Channel Join/Switch/Leave Announcements via TTS (opt-in)</li>\n"
                + "<li>Customizable New User Join message (opt-in)</li>\n"
                + "<li>Get your personal ID or Token (returned via PM)</li>\n"
                + "<li>Server Info </li>\n"
                + "<li>User Info</li>\n"
                + "</ul>\n"
                + "<p><b>Coming Soon: </b></p>\n"
                + "<ul>\n"
                + "<li>Google search</li>\n"
                + "<li>Tagging (expand on current image return format)</li>\n"
                + "<li>Math stuff</li>\n"
                + "<li>Voice Channel Blacklisting</li>\n"
                + "<li>Periodic server announce messages</li>\n"
                + "</ul>\n"
                + "</p>";

        String features = 
                "<p>There are a few things to keep in mind:</p>\n"
                + "<p>1. <span style=\"text-decoration: underline;\"><strong>He is still in development</strong></span>, so there will be some bugs! Probably more noticeably on my end than yours. If you ever encounter a command that does not work, SirBroBot may be offline (and the status hasn’t updated yet), I messed something up, or you dont have the right permissions.</p>\n"
                + "<p>2.<span style=\"text-decoration: underline;\"><strong> Permissions Structure</strong></span>:</p>\n"
                + "\n"
                + "<ul>\n"
                + "<li>There are some commands that only I have (to take him offline, broadcast messages)</li>\n"
                + "<li>There are Server Owner only commands (this is NOT a “role”, discord knows who the server owner is)</li>\n"
                + "<li>There are commands that only work for people with an “Admin role” (this IS a role you need to make for access to the commands for people <em>other than</em> the owner).</li>\n"
                + "<li>There are general use commands, that anyone can use</li>\n"
                + "<li>There will be the ability to add a role to a user that disallows them from accessing SirBroBot if they spam, or if you don’t want them to access him for whatever reason. I will post this when I post all the command documentation.</li>\n"
                + "</ul>\n"
                + "\n"
                + "<p>3. <span style=\"text-decoration: underline;\"><strong>Commands:</strong></span></p>\n"
                + "<p>Access the commands in Discord via <em><strong>?commands </strong></em>or <strong>?pmcommands</strong></p>\n"
                + "Owner commands are available using ?ocommands"
                + "\n"
                + "";

        String howtouse = "<b>General Use Commands</b>\n"
                + "<ol>\n"
                + "<li>?commands   :See this list </li>\n"
                + "<li>?pmcommands :returns commands via PM</li>\n"
                + "<li>?servers    :Number of Servers he is Knight of</li>\n"
                + "<li>?serverinfo :Info on this server</li>\n"
                + "<li>?about      :Learn about SirBroBot</li>\n"
                + "<li>?tsearch <username> :Searches for a Twitter user and returns their info.</li>\n"
                + "<li>?gettoken   :Sends user PM with their current token. Should be kept SECRET</li>\n"
                + "<li>?myID       :Sends user PM with their Discord unique ID</li>\n"
                + "<li>?whois @UserMention :Returns info about mentioned user</li>\n"
                + "</ol>\n"
                + "<br></br>\n"
                + "<b>Owner Only Commands</b>\n"
                + "<ol>\n"
                + "<li>?roles       :See the roles on the server</li>\n"
                + "<li>?rolestxt    :Generates a text file with the roles on the current server that can be downloaded</li>\n"
                + "<li>?setrole <role#> @MentionUser :Gives the role to the user</li>\n"
                + "</ol>\n"
                + "\n"
                + "<b>Voice Join Announce Managment</b>\n"
                + "<ol>\n"
                + "<li>?VJAon   :Cast in channel you wish to be the Voice Join ANNOUNCE channel</li>\n"
                + "<li>?VJAoff  :Cast in VOICE Join Announce channel to disable VJA</li>\n"
                + "<li>?VLAon   :Cast in channel you wish to be the Voice Join LEAVE channel</li>\n"
                + "<li>?VLAoff  :Cast in VOICE Join Announce channel to disable VJA</li>\n"
                + "</ol>\n"
                + "\n"
                + "<b>New User Join Announce Managment</b>\n"
                + "<ol>\n"
                + "<li>?welcomeON <Welcome Message> :Welcomes NEW users to the server in the channel command is cast in</li>\n"
                + "<li>?welcomeOFF :Turns off Welcome Message</li>\n"
                + "<li>?welcomeedit<Welcome Message> :Edit currently set Welcome message</li>\n"
                + "</ol>";
        
        
        URL url = new URL("https://www.carbonitex.net/discord/data/botdata.php");
        Map<String,Object> params = new LinkedHashMap<>();
        
        params.put("key", "broman289apcadq87wcnaw");
        params.put("servercount",serverCount);
        params.put("botname", "SirBroBot");
        params.put("botid", "166913295457058817");
        params.put("logoid", "e5b1c2fc9dac54ce6d92225b8b274d0d");
        params.put("ownername", "BroManDudeGuyPhD");
        params.put("ownerid", "150074847546966017");
        params.put("features", features);
        params.put("description", description);
        params.put("howtouse", howtouse);

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
    }
}