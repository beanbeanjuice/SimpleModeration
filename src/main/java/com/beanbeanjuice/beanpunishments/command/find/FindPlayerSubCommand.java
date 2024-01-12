package com.beanbeanjuice.beanpunishments.command.find;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FindPlayerSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            Helper.sendMessageConfig(sender, "findworld-incorrect-syntax");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("player-not-found").replace("%player%", args[0]));
            return true;
        }

        Helper.sendMessage(
                sender,
                Helper.getParsedConfigString("find-world")
                        .replace("%player%", player.getName())
                        .replace("%world%", player.getWorld().getName())
        );
        return true;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.findworld";
    }

}
