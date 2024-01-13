package com.beanbeanjuice.beanmoderation.utility.chat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MovementManager implements Listener {

    private static boolean allFrozen;
    private static final ArrayList<UUID> frozenPlayers = new ArrayList<>();

    public MovementManager() {
        allFrozen = false;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!allFrozen && frozenPlayers.isEmpty()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("beanmoderation.freeze.bypass") || player.isOp()) return;

        if (allFrozen || frozenPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!Helper.getPlugin().getConfig().getBoolean("freeze-ban-on-leave")) return;

        Player player = event.getPlayer();

        if (!frozenPlayers.contains(player.getUniqueId())) return;

        frozenPlayers.remove(player.getUniqueId());
        player.ban(
                Helper.getParsedConfigString("freeze-ban-message").replace(
                        "%discord%",
                        Helper.getParsedConfigString("discord-link")
                ),
                (Date) null,
                null,
                true
        );
        Bukkit.broadcastMessage(Helper.getParsedConfigString("freeze-ban-broadcast-message").replace("%player%", player.getName()));
    }

    public static boolean isPlayerFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    public static void toggleFreezeOnPlayer(Player player) {
        if (isPlayerFrozen(player)) {
            frozenPlayers.remove(player.getUniqueId());
            return;
        }
        frozenPlayers.add(player.getUniqueId());
    }

    public static void toggleFrozen() {
        allFrozen = !allFrozen;
    }

    public static boolean isAllFrozen() {
        return allFrozen;
    }

}
