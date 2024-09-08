package com.beanbeanjuice.simplemoderation.utility.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {

    public static String placeholderAPIConversion(final Player player, String string) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return string.replace("%player_name%", player.getName());
        return PlaceholderAPI.setPlaceholders(player, string);
    }

    public static boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

}
