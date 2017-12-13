package bromandudeguyphd.sirbrobot.commands.commands;
import bromandudeguyphd.sirbrobot.Messages;
import bromandudeguyphd.sirbrobot.commands.Command;
import bromandudeguyphd.sirbrobot.commands.CommandTypes;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

/**
 * <br>
 * Created by BroManDudeGuyPhD on 10.3.2017
 */
public class Github implements Command {
    @Override
    public void execute(String[] args, IUser sender, IChannel channel) {
        if(channel.getGuild().getStringID().equals("168043804790751232")){
            
            try{
            IUser user;
            user = sender;

            IRole[] roles = new IRole[user.getRolesForGuild(channel.getGuild()).size() + 1];

            for (int i = 0; i < user.getRolesForGuild(channel.getGuild()).size(); i++) {
                roles[i] = user.getRolesForGuild(channel.getGuild()).get(i);

            }

            IRole role2 = channel.getGuild().getRoleByID(388549537159053312L);

            roles[user.getRolesForGuild(channel.getGuild()).size()] = role2;

            channel.getGuild().editUserRoles(user, roles);

            Messages.send("Operation successful: " + role2 + " role added to " + sender.mention() + "\n", channel);
            }
            
            catch (Exception ex){
            ex.printStackTrace();
                   
            }
        }
        
        
       else{
            Messages.send("This command is for SirBroBot's server! Join here https://discord.gg/0wCCISzMcKMkfX88 ", channel);
        }


    }

    @Override
    public String getName() {
        return "github";
    }

    @Override
    public String getDescription() {
        return "Givess access to github-updates role on SirBroBot's Discord";
    }

    @Override
    public CommandTypes getType() {
        return CommandTypes.NORMAL;
    }
}
