package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.chat.ChatManager;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MuteChat implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        ChatManager chatManager = BeanPunishments.getChatManager();
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            if (chatManager.isChatEnabled()) {
                chatManager.toggleChat();
                Bukkit.broadcastMessage(helper.getPrefix() + helper.getConfigString("mutechat-broadcast-muted"));
                return true;
            } else {
                chatManager.toggleChat();
                Bukkit.broadcastMessage(helper.getPrefix() + helper.getConfigString("mutechat-broadcast-unmuted"));
                return true;
            }
        }

        Player player = (Player) sender;

        if (player.hasPermission(getPermissions().get(0)) || player.isOp()) {
            if (chatManager.isChatEnabled()) {
                chatManager.toggleChat();
                Bukkit.broadcastMessage(helper.getPrefix() + helper.getConfigString("mutechat-broadcast-muted"));
                return true;
            } else {
                chatManager.toggleChat();
                Bukkit.broadcastMessage(helper.getPrefix() + helper.getConfigString("mutechat-broadcast-unmuted"));
                return true;
            }
        } else {
            player.sendMessage(helper.getPrefix() + helper.getNoPermission());
            return false;
        }
    }

    @Override
    public String getName() {
        return "mutechat";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.mutechat"); // #0
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        return new CommandUsage();
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }
}
