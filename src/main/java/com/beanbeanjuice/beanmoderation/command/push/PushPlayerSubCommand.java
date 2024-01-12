package com.beanbeanjuice.beanmoderation.command.push;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PushPlayerSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length != 2) {
            Helper.sendMessageConfig(sender, "push-incorrect-syntax");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        int strength;

        try {
            strength = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            Helper.sendMessageConfig(sender, "push-incorrect-syntax");
            return false;
        }

        int maximumStrength = Helper.getPlugin().getConfig().getInt("push-maximum");
        if (strength > maximumStrength) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("push-above-maximum").replace("%max%", String.valueOf(maximumStrength)));
            return false;
        }

        if (player == null) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("player-not-found").replace("%player%", args[0]));
            return false;
        }

        List<String> blackListedPlayerNames = Helper.getPlugin().getConfig().getStringList("push-blacklisted-players");
        if (blackListedPlayerNames.contains(player.getName())) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("push-not-allowed").replace("%player%", player.getName()));
            return false;
        }

        Vector direction = (sender instanceof Player) ? ((Player) sender).getLocation().getDirection() : player.getLocation().getDirection();
        player.setVelocity(direction.multiply(strength));
        Helper.sendMessage(sender, Helper.getParsedConfigString("successful-push").replace("%player%", player.getName()));

        return true;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.push";
    }

}
