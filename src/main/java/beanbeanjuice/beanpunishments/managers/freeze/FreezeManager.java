package beanbeanjuice.beanpunishments.managers.freeze;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class FreezeManager {

    private HashMap<UUID, FrozenPlayer> frozenPlayers;
    private BeanPunishments plugin;

    public FreezeManager(BeanPunishments plugin) {
        frozenPlayers = new HashMap<>();
        this.plugin = plugin;
    }

    public void freezePlayer(Player player) {
        FrozenPlayer p = new FrozenPlayer(player.getLocation(), player);
        frozenPlayers.put(player.getUniqueId(), p);
        p.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, p, 5L, 5L));
    }

    public void unfreezePlayer(Player player) {
        FrozenPlayer p = frozenPlayers.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(p.getID());
        frozenPlayers.remove(player.getUniqueId());
    }

    public boolean checkFrozen(Player player) {
        return frozenPlayers.get(player.getUniqueId()) != null;
    }

    public void frozenEffects(Player player, Location location) {
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 50));
        player.sendTitle(BeanPunishments.getHelper().translateColors("&bYou are frozen"),
                BeanPunishments.getHelper().translateColors("&cDo not log out or you will be banned."),0, 40, 0);
        player.teleport(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
    }
}