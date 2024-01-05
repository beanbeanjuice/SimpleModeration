package com.beanbeanjuice.beanpunishments.command.clearchat;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Clears the chat.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class ClearChatSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        Bukkit.getOnlinePlayers().forEach(this::clearIndividualPlayerChat);
        Helper.broadcastMessage(Helper.getParsedConfigString("chat-cleared"));
        return true;
    }

    private void clearIndividualPlayerChat(Player player) {
        for (int i = 0; i < 100; i++) player.sendMessage("");
    }

    @Override
    public String getPermission() {
        return "beanPunishments.clearchat";
    }

}
