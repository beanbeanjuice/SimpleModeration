package com.beanbeanjuice.simplemoderation.utility.handler;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import com.beanbeanjuice.simplemoderation.utility.Helper;
import com.beanbeanjuice.simplemoderation.utility.config.ConfigKey;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FreezeHandler implements Listener {

    private final SimpleModeration plugin;
    private final Set<Player> frozenPlayers = new HashSet<>();

    public FreezeHandler(final SimpleModeration plugin) {
        this.plugin = plugin;

        this.startTasks();
    }

    public void addPlayer(final Player player) {
        frozenPlayers.add(player);
    }

    public void removePlayer(final Player player) {
        frozenPlayers.remove(player);
    }

    public boolean isFrozen(final Player player) {
        return frozenPlayers.contains(player);
    }

    private void startTasks() {
        this.plugin.getFoliaLib().getScheduler().runTimerAsync(() -> this.frozenPlayers.forEach(this::handleSlowTasks), 0, 1, TimeUnit.SECONDS);
        this.plugin.getFoliaLib().getScheduler().runTimerAsync(() -> this.frozenPlayers.forEach(this::handleFastTasks), 0, 1, TimeUnit.MILLISECONDS);
    }

    private void handleSlowTasks(final Player player) {
        this.sendTitle(player);

        player.getWorld().spawnParticle(Particle.SNOWFLAKE, player.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.1);
    }

    private void handleFastTasks(final Player player) {
        player.setFreezeTicks(player.getMaxFreezeTicks() - 1);
    }

    private void sendTitle(final Player player) {
        if (!player.isOnline()) return;

        String title = this.plugin.getCustomConfig().get(ConfigKey.FREEZE_FROZEN_TITLE, String.class);
        String subtitle = this.plugin.getCustomConfig().get(ConfigKey.FREEZE_FROZEN_SUBTITLE, String.class);
        Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofSeconds(3), Duration.ofSeconds(2));

        Helper.sendTitle(this.plugin.getMiniMessage(), player, title, subtitle, times);
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        if (!frozenPlayers.contains(e.getPlayer())) return;

         /*
            Cancelling the event prevents head movement. Doing it this way
            ensures smooth head movement regardless.
         */
        Location from = e.getFrom();
        Location to = e.getTo();

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();

        if (!frozenPlayers.contains(player)) return;

        e.setCancelled(true);

        String commandFailure = this.plugin.getCustomConfig().get(ConfigKey.FREEZE_FROZEN_COMMAND_USE, String.class);
        Helper.sendMessage(this.plugin, player, null, commandFailure);
    }

    @EventHandler
    public void onPlayerDisconnect(final PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (!frozenPlayers.contains(player)) return;

        // TODO: Ban player (if enabled).
    }

    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent e) {
        if (frozenPlayers.contains(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onFrozenPlayerAttackReceived(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if (!frozenPlayers.contains(player)) return;

        if (e.getDamageSource().getCausingEntity() instanceof Player attacker) {
            String failureMessage = this.plugin.getCustomConfig().get(ConfigKey.FREEZE_ATTACK_RECEIVED, String.class);
            Helper.sendMessage(this.plugin, attacker, player, failureMessage);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onFrozenPlayerAttackSent(final EntityDamageEvent e) {
        if (!(e.getDamageSource().getCausingEntity() instanceof Player attacker)) return;
        if (!frozenPlayers.contains(attacker)) return;

        String failureMessage = this.plugin.getCustomConfig().get(ConfigKey.FREEZE_ATTACK_SENT, String.class);
        Helper.sendMessage(this.plugin, attacker, null, failureMessage);

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent e) {
        if (frozenPlayers.contains(e.getPlayer())) e.setCancelled(true);
    }

}
