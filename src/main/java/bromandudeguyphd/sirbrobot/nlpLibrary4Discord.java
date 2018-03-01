package bromandudeguyphd.sirbrobot;

import DatabaseConnections.queries;
import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import static bromandudeguyphd.sirbrobot.DiscordListener.root;
import sx.blah.discord.handle.obj.IMessage;

/**
 * <br>
 * Created by BroManDudeGuyPhD on 10.3.2017
 */
public class nlpLibrary4Discord {

    public String processMessage(IMessage message) {
        AIConfiguration config = new AIConfiguration(tokens.apiAItoken());

                AIDataService ai = new AIDataService(config);
                String mcontent = message.getContent().replace(message.getClient().getUserByID(166913295457058817L).mention(), "");
                
                
                for(int i = 0; i < message.getMentions().size(); i++){
                    String toBeRemoved = message.getMentions().get(i).getStringID();
                    mcontent = mcontent.replace("<@"+toBeRemoved+">", "");
                }

                try {
                    AIRequest request = new AIRequest(mcontent);

                    AIResponse response = ai.request(request);
                    
                     
                    //In case of successful response
                    if (response.getStatus().getCode() == 200) {
                        String returnedMessage = response.getResult().getFulfillment().getSpeech();

                        if (response.getResult().getSource().equals("domains")) {
                            //Domain based results do not have an ID, so must return seperately
                            if (returnedMessage.contains("USERMENTION")) {
                                returnedMessage = returnedMessage.replace("USERMENTION", message.getAuthor().mention());
                            }

                            if (returnedMessage.contains("#username")) {
                                returnedMessage = returnedMessage.replace("#username", message.getAuthor().getName());
                            }

                            if (returnedMessage.contains("OWNERMENTION")) {
                                returnedMessage = returnedMessage.replace("OWNERMENTION", root.mention());
                            }

                            if (returnedMessage.contains("SERVERS")) {
                                returnedMessage = returnedMessage.replace("SERVERS", SirBroBot.client.getGuilds().size() + "");
                            }

                            if (returnedMessage.contains("SERVER")) {
                                returnedMessage = returnedMessage.replace("SERVER", "https://discord.gg/0wCCISzMcKMkfX88");
                            }

                            return returnedMessage;
                        }
                   
                        
                        //SocialMedia intent                                                                           
                        if (response.getResult().getMetadata().getIntentId().equals("6b5a215a-f980-48b5-a97f-3e66690fe6e9")) {
                            returnedMessage = "**These are my social media links and website**   \n"
                                    + "<:SirBroBot_website:415563293340729345> <https://sirbrobot.com>"
                                    + "<:SirBroBot_Twitter:415563272289517568> <https://twitter.com/sirbrobotthe1st?lang=en>\n"
                                    + "<:SirBroBot_Facebook:417717351664975877> <https://www.facebook.com/sirbrobot/>\n"
                                    + "<:SirBroBot_YouTube:415563282187943936> <https://www.youtube.com/channel/UCZi_pzKLVb5zvTmDOCEMbtQ>\n";
                         }
                        
                        //Set Nickname
                        if (response.getResult().getMetadata().getIntentId().equals("982a5a46-37e0-4ab4-a3f6-9f6d1c3cd0a9")) {
                            String nickname = DatabaseConnections.queries.getDiscordNicknameQuery(message.getAuthor().getStringID());
                            String editedResponse = response.getResult().getParameters().get("any").getAsString().replace("'", "");
                            
                            if (nickname.equals("None")) {
                                queries.sendDataDB("insert into discord_users (user_discord_id, nickname) "
                                        + "values ('"+ message.getAuthor().getStringID() +"','"+ editedResponse + "');");
                            }
                            else{
                                queries.sendDataDB("update discord_users set user_discord_id ='"+ message.getAuthor().getStringID() 
                                        +"', nickname ='"+ editedResponse 
                                        + "' Where user_discord_id ="+message.getAuthor().getStringID() +";");
                            }
                        }
                        
                        //Get Nickname
                        if (response.getResult().getMetadata().getIntentId().equals("ed6b83f3-c804-4e11-9295-3997ee38676e")) {
                            String nickname = DatabaseConnections.queries.getDiscordNicknameQuery(message.getAuthor().getStringID());
                            
                            if (nickname.equals("None")) {
                                returnedMessage = "You haven't asked me to call you anything yet!";
                            }
                            else{
                                returnedMessage = returnedMessage.replace("#username", queries.getDiscordNicknameQuery(message.getAuthor().getStringID()));
                            }
                        }
                        
                        
                       
                        if(returnedMessage.contains("USERMENTION")){
                            returnedMessage = returnedMessage.replace("USERMENTION", message.getAuthor().mention());
                        }
                        
                        if(returnedMessage.contains("#username")){
                            returnedMessage = returnedMessage.replace("#username", message.getAuthor().getName());
                        }
                        
                        if(returnedMessage.contains("OWNERMENTION")){
                            returnedMessage = returnedMessage.replace("OWNERMENTION", root.mention());
                        }
                        
                        if(returnedMessage.contains("SERVERS")){
                            returnedMessage = returnedMessage.replace("SERVERS", SirBroBot.client.getGuilds().size()+"");
                        }
                        
                        if(returnedMessage.contains("SERVER")){
                            returnedMessage = returnedMessage.replace("SERVER", "https://discord.gg/0wCCISzMcKMkfX88");
                        }
                        
                        
                        
                        
                        return returnedMessage;
                              
                    
                    } else {
                        System.err.println(response.getStatus().getErrorDetails());
                        return "I have experienced an error";
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return "I have experienced an error";
                }

    }

    
}
