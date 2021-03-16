package beanbeanjuice.beanpunishments.managers.freeze;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

/**
 * A class used for freezing {@link Player players}.
 */
public class FreezeManager {

    private HashMap<UUID, FrozenPlayer> frozenPlayers;
    private BeanPunishments plugin;

    /**
     * Create a new {@link FreezeManager} object.
     * @param plugin The actual {@link org.bukkit.plugin.Plugin Plugin} used.
     */
    public FreezeManager(BeanPunishments plugin) {
        frozenPlayers = new HashMap<>();
        this.plugin = plugin;
    }

    /**
     * Freeze a specified player.
     * @param player The {@link Player} to be frozen.
     */
    public void freezePlayer(Player player) {
        FrozenPlayer p = new FrozenPlayer(player.getLocation(), player);
        frozenPlayers.put(player.getUniqueId(), p);
        p.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, p, 5L, 5L));
    }

    /**
     * Unfreeze a specified player.
     * @param player The {@link Player} to be unfrozen.
     */
    public void unfreezePlayer(Player player) {
        FrozenPlayer p = frozenPlayers.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(p.getID());
        frozenPlayers.remove(player.getUniqueId());
    }

    /**
     * Checks if a specified player is frozen.
     * @param player The {@link Player} to be checked.
     * @return Whether or not the {@link Player} is frozen.
     */
    public boolean checkFrozen(Player player) {
        return frozenPlayers.get(player.getUniqueId()) != null;
    }

    /**
     * Applies frozen effects to a specified player and keeps them in place.
     * @param player The {@link Player} to have the effects applied on.
     * @param location The {@link Location} they were frozen at.
     */
    public void frozenEffects(Player player, Location location) {
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 50));
        player.sendTitle(BeanPunishments.getHelper().getConfigString("freeze-title-message"),
                BeanPunishments.getHelper().getConfigString("freeze-secondary-message"),0, 40, 0);
        player.teleport(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
    }
}