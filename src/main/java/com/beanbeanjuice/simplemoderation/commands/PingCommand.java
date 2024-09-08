package com.beanbeanjuice.simplemoderation.commands;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import com.beanbeanjuice.simplemoderation.utility.Helper;
import com.beanbeanjuice.simplemoderation.utility.SMCommand;
import com.beanbeanjuice.simplemoderation.utility.config.ConfigKey;
import com.beanbeanjuice.simplemoderation.utility.hooks.PlaceholderAPIHook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand extends SMCommand implements CommandExecutor {

    public PingCommand(final SimpleModeration plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        ConfigKey key = (PlaceholderAPIHook.isPlaceholderAPIEnabled()) ? ConfigKey.PING_WITH_PLACEHOLDER_API_SUPPORT : ConfigKey.PING_WITHOUT_PLACEHOLDER_API_SUPPORT;
        String pingConfigString = this.config.get(key, String.class);

        Helper.sendMessage(this.plugin, sender, player, pingConfigString);
        return true;
    }



}
