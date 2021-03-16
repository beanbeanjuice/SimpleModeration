package beanbeanjuice.beanpunishments.managers.freeze;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Used for handling the {@link Player players} that are frozen.
 */
public class FrozenPlayer implements Runnable {

    private Player player;
    private Location location;
    private int id;

    /**
     * Create a new {@link FrozenPlayer} object.
     * @param location The {@link Location} the {@link Player} was frozen in.
     * @param player The {@link Player} that is frozen.
     */
    public FrozenPlayer(Location location, Player player) {
        this.location = location;
        this.player = player;
    }

    /**
     * @return The ID of the {@link FrozenPlayer}.
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the ID of the {@link FrozenPlayer}.
     * @param id The ID to be set.
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Things to run when the player is frozen.
     */
    @Override
    public void run() {
        BeanPunishments.getFreezeManager().frozenEffects(player, location);

        if (Bukkit.getPlayer(player.getName()) == null) {
            GeneralHelper helper = BeanPunishments.getHelper();
            BeanPunishments.getFreezeManager().unfreezePlayer(player);

            if (helper.getConfigBoolean("freeze-ban-on-leave")) {
                Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), helper.getConfigString("freeze-ban-message")
                        .replace("%discord%", helper.getConfigString("discord-link")), null, null);
                Bukkit.broadcastMessage(helper.getPrefix() + helper.getConfigString("freeze-ban-broadcast-message")
                        .replace("%player%", player.getName()));
            }
        }
    }
}