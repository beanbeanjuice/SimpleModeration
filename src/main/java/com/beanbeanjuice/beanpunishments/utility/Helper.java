package com.beanbeanjuice.beanpunishments.utility;

import com.beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Helper {

    private static BeanPunishments plugin;

    public static void initialize(BeanPunishments beanPunishments) {
        plugin = beanPunishments;
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

    @NotNull
    public static String getPrefix() {
        return getParsedConfigString("prefix");
    }

    public static void sendMessage(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(getPrefix() + translateColors(message));
    }

    public static void sendMessageConfig(@NotNull CommandSender sender, @NotNull String configString) {
        sendMessage(sender, getParsedConfigString(configString));
    }

    public static void sendNoPermission(@NotNull CommandSender sender) {
        sendMessage(sender, Helper.getParsedConfigString("no-permission"));
    }

    public static void broadcastMessage(@NotNull String message) {
        Bukkit.broadcastMessage(getPrefix() + translateColors(message));
    }

}
