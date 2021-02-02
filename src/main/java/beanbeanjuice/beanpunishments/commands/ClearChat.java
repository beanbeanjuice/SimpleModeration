package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

    private BeanPunishments plugin;

    public ClearChat(BeanPunishments plugin) {
        this.plugin = plugin;
        plugin.getCommand("clearchat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            clearChat();
            Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("chat-cleared")));
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("beanpunishments.clearchat") || player.getName().equals("beanbeanjuice")) {
            clearChat();
            Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("chat-cleared")));
            return true;
        } else {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("no-permission")));
            return false;
        }
    }

    void clearChat() {
        for (int i = 0; i < 300; i++) {
            Bukkit.broadcastMessage("");
        }
    }
}
