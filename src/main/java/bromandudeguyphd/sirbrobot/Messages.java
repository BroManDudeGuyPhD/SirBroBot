package bromandudeguyphd.sirbrobot;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

import java.io.PrintWriter;
import java.io.StringWriter;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IMessage;

/**
 * Sends a message
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public class Messages {

    /**
     * Sends a message, respecting the RequestBuffer
     * @param msg The message to send
     * @param chan Tha channel receiving the message
     */
    public static void send(String msg, IChannel chan){
        RequestBuffer.request(() -> {
            try {
                new MessageBuilder(chan.getClient()).appendContent(msg.substring(0, Math.min(msg.length(), 1999))).withChannel(chan).send();
            } catch (DiscordException | MissingPermissionsException e) {
                sendException("Could not send message! ", e, chan);
            }
        });
    }
    
     /**
     * Sends a message with an embed, respecting the RequestBuffer
     * @param msg The message to send
     * @param eo Embed Object
     * @param bln TTS or not
     * @param chan Tha channel receiving the message
     */
    public static void sendWithEmbed(String msg, EmbedObject eo, boolean bln, IChannel chan){
        RequestBuffer.request(() -> {
            try {
                new MessageBuilder(chan.getClient()).withEmbed(eo).withChannel(chan).send();
            } catch (DiscordException | MissingPermissionsException e) {
                sendException("Could not send message! ", e, chan);
            }
        }); 
    }
    
    
     /**
     * Sends a message with an embed,  WITHOUT respecting the RequestBuffer (so it can be edited)
     * @param msg The message to send
     * @param eo Embed Object
     * @param bln TTS or not
     * @param chan Tha channel receiving the message
     * @return The embed object so it can be edited where it is called
     */
    public static IMessage sendWithUpdatableEmbed(String msg, EmbedObject eo, boolean bln, IChannel chan){
        IMessage message = null;
            try {
                message = new MessageBuilder(chan.getClient()).withEmbed(eo).withContent(msg).withChannel(chan).send();
                return message;
            } catch (DiscordException | MissingPermissionsException e) {
                sendException("Could not send message! ", e, chan);
            }
        return message;
    }

    /**
     * Sends a message, with an exception, respecting the RequestBuffer
     * @param msg The message to send
     * @param t The exception thrown
     * @param chan Tha channel receiving the message
     */
    public static void sendException(String msg, Throwable t, IChannel chan){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.close();
        msg = msg + ' ' + sw.toString();
        String finalMsg = msg;
        RequestBuffer.request(() -> {
            try {
                new MessageBuilder(chan.getClient()).appendContent(finalMsg).withChannel(chan).send();
            } catch (DiscordException | MissingPermissionsException e) {
                SirBroBot.LOGGER.error("Could not send message! It was: \n" + finalMsg + "\nError trace: ", e);
            }
        });
    }
}
