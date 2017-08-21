/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

/**
 *
 * @author Andrew
 */
public class commandsList {
    String commandListNormal = "**General Use commands:** \n"
                        + "```xl\n"
            + "<required paramater> | {optional paramater}"
            + "```"
            + "```"
            + " 1. ?commands            :See this list \n"
            + " 2. ?ocommands           :List Owner commands (Commands listed only work if you're the server owner)\n"
            + " 3. ?pmcommands          :Same as above command, but returns via PM\n"
            + " 4. ?mcommands           :List Music commands\n"
            + " 4. ?invite              :Get invite link for SirBroBo\n"
            + " 5. ?usecounter          :How many times I've been commanded\n"
            + " 6. ?servers             :Number of Servers I am Knight of\n"
            + " 7. ?serverinfo          :Info on this server\n"
            + " 8. ?about               :Learn about SirBroBot\n"
            + " 9. ?tsearch: <username> :Searches for a Twitter user and returns their info.\n"
            + "10. ?whois @UserMention  :Returns info about mentioned user\n"
            + "11. ?d20                 :Returns random # (1-20)\n"
            + "12. ?randnum <#> <#>     :Generates # between the 2 given\n"
            + "13. ?img-negative <url>  :Accepts URL or an attatchment. Comment on the attatched image\n"
            + "14. ?img-mirror <url>    :Same as above\n"
            + "15. ?img-id <url>        :Identify what an image is\n"
            + "16. ?google <term>       :Search Google for term\n"
            + "17. ?ping                :Pings Google\n"
            + "18. ?ascii <message>     :Replies with ASCII message\n"
            + ""
            + "```"
            + "\nThings I will respond to with an @SirBroBot mention behind it: \n"
            + "```Hello, Dance, Joust, Wake up, Insult, Taunt, Who is the fairest of them all, Who is your foe```"
            + "";
        
    String commandListOwner = 
             "\n**Owner Commands:**\n"
            + "```xl\n"
            + "<required paramater> | {optional paramaters}"
            + "```"
            
            + "Role Managment\n"
            +"```"
            + " 1. ?roles                           :See the roles on the server\n"
            + " 2. ?rolestxt                        :Generates a text file with the roles on the current server that can be downloaded\n"
            + " 3. ?setrole <role#> @Mention        :SETS the role to the user (replaces all roles and gives this one)\n"
            + " 4. ?addrole <role#> @Mention        :ADDS the roles to the user (maintains current roles, adding this one)\n"
            +"\n"
            +"```"
            + "Voice Join Announce Managment\n"
            +"```"
            
            + " 4. ?VJAon {persist OR silent}       :Makes text-channel Voice JOIN Announce channel\n"
            + " 5. ?VJAoff                          :Disable VJA\n"
            + " 6. ?VLAon {persist OR silent}       :Makes text-channel Voice LEAVE Announce channel\n"
            + " 7. ?VLAoff                          :Disable VLA\n"
            + " 8. ?VMAon {persist OR silent}       :Makes text-channel Voice MOVE Announce channel\n"
            + " 9. ?VMAoff                          :Disable VMA\n\n"
            + "persist: Announce text will stay  silent: Text only announce with not TTS"
            +"```"
            +"\n"
            
            + "New User Join Announce Managment\n"
            +"```"
            + "10. ?welcomeON <Welcome Message>     :Welcomes NEW users in cast text channel. USERNAME or USERMENTION include these in welcome message.\n"
            + "11. ?welcomeOFF                      :Turns off Welcome Message\n"
            + "12. ?welcomeedit <Welcome Message>    :Edit currently set Welcome message\n"
            + "13. ?welcomeview                     :Displays current welcome Message, showing USERNAME or USERMENTION (if youve utilized them)\n"
            + "14. ?purgechannel                    :Purge ALL messages in channel after 10 sec. Say abort to stop. \n"
            +"```"
            + "\n"

            
            + "";
    
    
    String mcommands = 
            "**Music Commands**:musical_note:\n"
          + "```xl\n"
          + " 1. >Join(MANDATORY for music):joins you in the voice channel you are currently in\n"
          + " 2. >Autojoin                 :auto join this voice channel in case of a reboot\n" +
            " 3. >Leave                    :leaves the voice channel\n" +
            " 4. >Pause                    :pauses the music\n" +
            " 5. >Resume                   :unpauses the music\n" +
            " 6. >Skip                     :skips to the next song\n" +
            " 7  >Stop                     :stops playing music\n" +
            " 8. >Playlist                 :shows songs in playlist\n" +
            " 9. >Stream url               :plays the audio of youtube or soundcloud video after short delay\n" +
            "10. >Search keywords         :searches youtube and plays audio matching query\n"  +
            "11. >Play                     :plays a pre-set list of music"
            + "```";
}
