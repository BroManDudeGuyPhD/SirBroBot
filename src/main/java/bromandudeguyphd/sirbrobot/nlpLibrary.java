/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bromandudeguyphd.sirbrobot;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import static bromandudeguyphd.sirbrobot.DiscordListener.root;
import sx.blah.discord.handle.obj.IMessage;

/**
 *
 * @author Andrew
 */
public class nlpLibrary {

    public String processMessage(IMessage message) {
        AIConfiguration config = new AIConfiguration(tokens.apiAItoken());

                AIDataService ai = new AIDataService(config);

                try {
                    AIRequest request = new AIRequest(message.getContent());

                    AIResponse response = ai.request(request);
                    
                     
                    //In case of successful response
                    if (response.getStatus().getCode() == 200) {
                        String returnedMessage = response.getResult().getFulfillment().getSpeech();
                        
                   
                        //SocialMedia intent                                         6b5a215a-f980-48b5-a97f-3e66690fe6e9                                     
                        if (response.getResult().getMetadata().getIntentId().equals("6b5a215a-f980-48b5-a97f-3e66690fe6e9")) {
                            returnedMessage = "**These are my social media links and website**   \n"
                                    + "`Twitter`: <https://twitter.com/sirbrobotthe1st?lang=en>\n"
                                    + "`Facebook`: <https://www.facebook.com/sirbrobot/>\n"
                                    + "`YouTube`:<https://www.youtube.com/channel/UCZi_pzKLVb5zvTmDOCEMbtQ>\n"
                                    + "`Website`: <http://bootswithdefer.tumblr.com/SirBroBot>";
                        }
                       
                        if(returnedMessage.contains("USERMENTION")){
                            returnedMessage = returnedMessage.replace("USERMENTION", message.getAuthor().mention());
                        }
                        
                        if(returnedMessage.contains("#getusername.user-name")){
                            returnedMessage = returnedMessage.replace("#getusername.user-name", message.getAuthor().getName());
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
    
    public String processMessageWithMentions(IMessage message) {
        AIConfiguration config = new AIConfiguration(tokens.apiAItoken());

                AIDataService ai = new AIDataService(config);
                String mcontent = message.getContent().replace(message.getClient().getUserByID("166913295457058817").mention(), "");
                
                
                for(int i = 0; i < message.getMentions().size(); i++){
                    String toBeRemoved = message.getMentions().get(i).getID();
                    mcontent = mcontent.replace("<@"+toBeRemoved+">", "");
                }
                
                try {
                    
                    AIRequest request = new AIRequest(mcontent);

                    AIResponse response = ai.request(request);
                    
                     
                    //In case of successful response
                    if (response.getStatus().getCode() == 200) {
                        String returnedMessage = response.getResult().getFulfillment().getSpeech();
                        
                   
                        //What are you Intent
//                        if(response.getResult().getMetadata().getIntentId().equals("8dc8beee-1503-4973-aec9-40994541c896")){
//                            
//                        }
                        
                        if(returnedMessage.contains("USERMENTION")){
                            returnedMessage = returnedMessage.replace("USERMENTION", message.getAuthor().mention());
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
