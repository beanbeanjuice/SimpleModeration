package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {

    private BeanPunishments plugin;

    public Reload(BeanPunishments plugin) {
        this.plugin = plugin;
        plugin.getCommand("bpreload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(GeneralHelper.getConsolePrefix() + "Successfully reloaded the config!");
            plugin.reloadConfig();
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("beanpunishments.reload") || player.getName().equals("beanbeanjuice")) {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("successful-reload")));
            plugin.reloadConfig();
            return true;
        } else {
            player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("no-permission")));
            return false;
        }
    }
}
