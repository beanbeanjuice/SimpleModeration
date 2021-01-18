package beanbeanjuice.beanpunishments.managers.freeze;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class FreezeManager {

    public static HashMap<UUID, FrozenPlayer> frozenplayers;

    public static void setupFreezeManager() {
        frozenplayers = new HashMap<>();
    }

    public static void freezePlayer(Player player, BeanPunishments plugin) {
        FrozenPlayer p = new FrozenPlayer(player.getLocation(), player);
        frozenplayers.put(player.getUniqueId(), p);
        p.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, p, 5L, 5L));
    }

    public static void unfreezePlayer(Player player) {
        FrozenPlayer p = frozenplayers.get(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(p.getID());
        frozenplayers.remove(player.getUniqueId());
    }

    public static boolean checkFrozen(Player player) {
        return frozenplayers.get(player.getUniqueId()) != null;
    }

    public static void frozenEffects(Player player, Location location) {
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.removePotionEffect(PotionEffectType.WEAKNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 50));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 50));
        player.sendTitle(GeneralHelper.translateColors("&bYou are frozen"), GeneralHelper.translateColors("&cDo not log out or you will be banned."),0, 40, 0);
        player.teleport(new Location(location.getWorld(), location.getX(), location.getY(), location.getZ()));
    }
}