package bromandudeguyphd.sirbrobot.commands;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import java.util.function.BiPredicate;

/**
 * <br>
 * Created by Arsen on 26.8.2016.
 */
public enum CommandTypes {
    MUSIC(">", (guild, user) -> true),
    OWNER("?", (guild, user) -> guild.getOwner().equals(user)),
    NORMAL("?", (guild, user) -> true);

    private String trigger;
    private BiPredicate<IGuild, IUser> filter;

    CommandTypes(String trigger, BiPredicate<IGuild, IUser> filter){
        this.trigger = trigger;
        this.filter = filter;
    }

    public String getTrigger() {
        return trigger;
    }

    public BiPredicate<IGuild, IUser> getFilter() {
        return filter;
    }
}
