package com.beanbeanjuice.simplemoderation.commands.freeze;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import com.beanbeanjuice.simplemoderation.utility.Helper;
import com.beanbeanjuice.simplemoderation.utility.SMCommand;
import com.beanbeanjuice.simplemoderation.utility.config.ConfigKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnfreezeCommand extends SMCommand implements CommandExecutor {

    @Getter
    @RequiredArgsConstructor
    public enum UnfreezeSubPermission {

        UNFREEZE_ALL ("simplemoderation.unfreeze.all");

        private final String permission;

    }

    public UnfreezeCommand(final SimpleModeration plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            String failureMessage = this.config.get(ConfigKey.UNFREEZE_USAGE, String.class);
            Helper.sendMessage(this.plugin, sender, null, failureMessage);
            return false;
        }

        String arg = args[0];

        if (arg.equalsIgnoreCase("all")) return handleUnfreezeAll(sender);
        return handleUnfreezeSingle(sender, arg);
    }

    private boolean handleUnfreezeSingle(final CommandSender sender, final String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            String failureMessage = this.config.get(ConfigKey.UNFREEZE_FAIL_OFFLINE, String.class);
            Helper.sendMessage(this.plugin, sender, null, failureMessage);
            return false;
        }

        unfreezePlayer(player);

        String successMessage = this.config.get(ConfigKey.UNFREEZE_SUCCESS_SINGLE, String.class);
        Helper.sendMessage(this.plugin, sender, player, successMessage);

        return true;
    }

    private boolean handleUnfreezeAll(final CommandSender sender) {
        if (!(sender.hasPermission(UnfreezeSubPermission.UNFREEZE_ALL.getPermission()))) {
            String failureMessage = this.config.get(ConfigKey.UNFREEZE_NO_PERMISSION_ALL, String.class);
            Helper.sendMessage(this.plugin, sender, null, failureMessage);
            return false;
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(this.plugin.getFreezeHandler()::isFrozen)
                .forEach(this::unfreezePlayer);

        String successMessage = this.config.get(ConfigKey.UNFREEZE_SUCCESS_ALL, String.class);
        Helper.sendMessage(this.plugin, sender, null, successMessage);

        return true;
    }

    private void unfreezePlayer(final Player player) {
        this.plugin.getFreezeHandler().removePlayer(player);
        player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 0.25F, 1F);
    }

}
