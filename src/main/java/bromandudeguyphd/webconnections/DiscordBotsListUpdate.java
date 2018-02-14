package bromandudeguyphd.webconnections;

import static bromandudeguyphd.sirbrobot.SirBroBot.client;
import bromandudeguyphd.sirbrobot.tokens;
import java.util.TimerTask;
import org.discordbots.api.client.DiscordBotListAPI;


/**
 *
 * @author BroManDudeGuyPhD
 * created on Feb.14.2018
 */
public class DiscordBotsListUpdate extends TimerTask{
    
    public void run() {
        
        DiscordBotListAPI api = new DiscordBotListAPI.Builder().token(tokens.DiscordBotsListtoken()).build();
        String botId = "166913295457058817";
        int serverCount = client.getGuilds().size();

        api.setStats(botId, serverCount);
    
    }
    
}
