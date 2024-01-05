package com.beanbeanjuice.beanpunishments.utility;

import com.beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Helper {

    private static String prefix;
    private static BeanPunishments plugin;

    public static void initialize(BeanPunishments beanPunishments) {
        plugin = beanPunishments;
        prefix = translateColors(plugin.getConfig().getString("prefix"));
    }

    @NotNull
    public static String translateColors(@NotNull String string) {
        return string.replaceAll("&", "§");
    }


    @NotNull
    public static BeanPunishments getPlugin() {
        return plugin;
    }

    @NotNull
    public static String getParsedConfigString(@NotNull String string) {
        return translateColors(plugin.getConfig().getString(string));
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(prefix + translateColors(message));
    }

    public static void broadcastMessage(@NotNull String message) {
        Bukkit.broadcastMessage(prefix + translateColors(message));
    }

}
