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

import java.util.Collection;
import java.util.stream.Collectors;

public class FreezeCommand extends SMCommand implements CommandExecutor {

    @Getter
    @RequiredArgsConstructor
    public enum FreezeSubPermission {

        FREEZE_ALL ("simplemoderation.freeze.all"),
        FREEZE_BYPASS ("simplemoderation.freeze.bypass");

        private final String permission;

    }

    public FreezeCommand(final SimpleModeration plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            String usageString = this.config.get(ConfigKey.FREEZE_USAGE, String.class);
            Helper.sendMessage(this.plugin, sender, null, usageString);
            return false;
        }

        final String arg = args[0];

        if (arg.equalsIgnoreCase("all")) return handleFreezeAll(sender);
        return handleFreezeSingle(sender, arg);
    }

    private boolean handleFreezeSingle(final CommandSender sender, final String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            String failureMessage = this.config.get(ConfigKey.FREEZE_FAIL_OFFLINE, String.class);
            Helper.sendMessage(this.plugin, sender, null, failureMessage);
            return false;
        }

        if (player.hasPermission(FreezeSubPermission.FREEZE_BYPASS.getPermission())) {
            String failureMessage = this.config.get(ConfigKey.FREEZE_FAIL_BYPASS, String.class);
            Helper.sendMessage(this.plugin, sender, player, failureMessage);
            return false;
        }

        freezePlayer(player);

        String successMessage = this.config.get(ConfigKey.FREEZE_SUCCESS_SINGLE, String.class);
        Helper.sendMessage(this.plugin, sender, player, successMessage);

        return true;
    }

    private boolean handleFreezeAll(final CommandSender sender) {
        if (!sender.hasPermission(FreezeSubPermission.FREEZE_ALL.getPermission())) {
            String failureMessage = this.config.get(ConfigKey.FREEZE_NO_PERMISSION_ALL, String.class);
            Helper.sendMessage(this.plugin, sender, null, failureMessage);
            return false;
        }

        Collection<Player> players = Bukkit.getOnlinePlayers().stream()
                .filter((player) -> !player.hasPermission(FreezeSubPermission.FREEZE_BYPASS.getPermission()))
                .filter((player) -> !player.equals(sender))
                .collect(Collectors.toSet());

        players.forEach(this::freezePlayer);

        String successMessage = this.config.get(ConfigKey.FREEZE_SUCCESS_ALL, String.class);
        Helper.sendMessage(this.plugin, sender, null, successMessage);

        return true;
    }

    private void freezePlayer(final Player player) {
        plugin.getFreezeHandler().addPlayer(player);
        player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 0.35F, 1.0F);
    }

}
