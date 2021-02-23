package beanbeanjuice.beanpunishments.utilities;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GeneralHelper {

    private final String prefix;
    private final String noPermission;
    private final BeanPunishments plugin;

    public GeneralHelper(BeanPunishments plugin) {
        this.plugin = plugin;
        prefix = translateColors(plugin.getConfig().getString("prefix")) + " ";
        noPermission = translateColors(plugin.getConfig().getString("no-permission"));
    }

    public String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    public BeanPunishments getPlugin() {
        return plugin;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String playerNotFound(String player) {
        return getConfigString("player-not-found").replace("%player%", player);
    }

    public String getConfigString(String identifier) {
        return translateColors(plugin.getConfig().getString(identifier));
    }

    public int getConfigInt(String identifier) {
        return plugin.getConfig().getInt(identifier);
    }

    public boolean getConfigBoolean(String identifier) {
        return plugin.getConfig().getBoolean(identifier);
    }

    public ArrayList<String> getConfigStringList(String identifier) {
        ArrayList<String> arrayList = new ArrayList<>();

        for (String string : plugin.getConfig().getStringList(identifier)) {
            arrayList.add(translateColors(string));
        }
        return arrayList;
    }

    public boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

}
