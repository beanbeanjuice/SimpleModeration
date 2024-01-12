package com.beanbeanjuice.beanmoderation.command.clearchat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
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

    private final int CLEAR_COUNT = 100;

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        // On a separate thread to not cause hang ups.
        Bukkit.getScheduler().runTaskAsynchronously(Helper.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(this::clearIndividualPlayerChat);  // Bukkit#broadcastMessage spams console.
            Helper.broadcastMessage(Helper.getParsedConfigString("chat-cleared"));
        });
        return true;
    }

    private void clearIndividualPlayerChat(Player player) {
        for (int i = 0; i < CLEAR_COUNT; i++) player.sendMessage("");
    }

    @Override
    public String getPermission() {
        return "beanmoderation.clearchat";
    }

}
