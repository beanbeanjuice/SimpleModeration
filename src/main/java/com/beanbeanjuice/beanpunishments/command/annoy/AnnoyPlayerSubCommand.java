package com.beanbeanjuice.beanpunishments.command.annoy;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnnoyPlayerSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 0) {
            Helper.sendMessageConfig(sender, "annoy-incorrect-syntax");
            return false;
        }

        List<String> blacklistedNames = Helper.getPlugin().getConfig().getStringList("annoy-blacklisted-players");
        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("player-not-found").replace("%player%", args[0]));
            return false;
        }

        if (blacklistedNames.contains(player.getName())) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("annoy-not-allowed").replace("%player%", args[0]));
            return false;
        }

        annoy(player);
        Helper.sendMessage(sender, Helper.getParsedConfigString("annoy-successful").replace("%player%", player.getName()));
        return true;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.annoy";
    }

    private void annoy(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Helper.getPlugin(), () -> {
            for (int i = 0; i < 1000; i++) {
                Location location = player.getLocation();
                player.playSound(location, Sound.ENTITY_GHAST_SCREAM, 1.0F, 10F);
                player.playSound(location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0F, 10F);
                player.playSound(location, Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.0F, 10F);
                player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 10F);
            }
        });
    }

}
