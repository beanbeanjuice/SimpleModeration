package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.chat.ChatManager;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChat implements CommandExecutor {

    private BeanPunishments plugin;

    public MuteChat(BeanPunishments plugin) {
        this.plugin = plugin;
        plugin.getCommand("mutechat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                if (ChatManager.isChatEnabled()) {
                    ChatManager.toggleChat();
                    Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-broadcast-muted")));
                    return true;
                } else {
                    ChatManager.toggleChat();
                    Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-broadcast-unmuted")));
                    return true;
                }
            } else {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Incorrect syntax. Please use /mutechat.");
                return false;
            }
        }

        Player player = (Player) sender;

        if (player.hasPermission("beanpunishments.mutechat")) {
            if (args.length == 0) {
                if (ChatManager.isChatEnabled()) {
                    ChatManager.toggleChat();
                    Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-broadcast-muted")));
                    return true;
                } else {
                    ChatManager.toggleChat();
                    Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-broadcast-unmuted")));
                    return true;
                }
            } else {
                player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-incorrect-syntax")));
                return false;
            }
        } else {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("no-permission")));
            return false;
        }
    }
}
