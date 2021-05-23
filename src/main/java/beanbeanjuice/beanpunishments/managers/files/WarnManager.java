package beanbeanjuice.beanpunishments.managers.files;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A manager used for keeping track of warns.
 */
public class WarnManager {

    private final String filepath = ("plugins/BeanPunishments/playerdata/Warn History");

    /**
     * Creates a new {@link WarnManager} object.
     */
    public WarnManager() {
        File file = new File(filepath);

        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * Creates a new file for the {@link org.bukkit.entity.Player Player} using their {@link UUID}.
     * @param UUID The {@link UUID} of the {@link org.bukkit.entity.Player Player}.
     */
    public void addPlayer(UUID UUID) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration f = YamlConfiguration.loadConfiguration(file);
                f.createSection("warn-history");
                f.save(file);
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * Gets the particular {@link FileConfiguration} for that {@link org.bukkit.entity.Player Player}'s {@link UUID}.
     * @param UUID The {@link UUID} of the {@link org.bukkit.entity.Player Player}.
     * @return The {@link FileConfiguration} for that {@link UUID}.
     */
    public FileConfiguration getPlayerConfig(UUID UUID) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Add a warn for that specified player.
     * @param UUID The {@link org.bukkit.entity.Player Player}'s {@link UUID} that you are warning.
     * @param punisher The person/object that warned the {@link org.bukkit.entity.Player Player}.
     * @param reason The reason for the warn.
     * @return The {@link org.bukkit.entity.Player Player}'s reason for being warned.
     */
    public String addWarnPlayer(UUID UUID, String punisher, String[] reason) {
        addPlayer(UUID);

        StringBuilder string = new StringBuilder();
        for (int i = 2; i < reason.length; i++) {
            string.append(reason[i]);
            if (i != reason.length-1) {
                string.append(" ");
            }
        }

        final FileConfiguration config = getPlayerConfig(UUID);
        int count = 1;

        while (config.getString("warn-history.warn" + count) != null) {
            count++;
        }

        config.set("warn-history.warn" + count + ".warn", string.toString());
        config.set("warn-history.warn" + count + ".warner", punisher);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        config.set("warn-history.warn" + count + ".timestamp", format.format(new Date()));

        try {
            config.save(new File(filepath + "/" + UUID.toString() + ".yml"));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return string.toString();
    }

    /**
     * Remove a warn from a particular {@link org.bukkit.entity.Player Player}'s {@link UUID}.
     * @param UUID The {@link UUID} of the {@link org.bukkit.entity.Player Player}.
     * @param punisher The person/object that issued the warn.
     * @param warn The ID of the particular warn.
     * @return Whether or not the warn was able to be removed.
     */
    public boolean removeWarnPlayer(UUID UUID, String punisher, String warn) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");
        if (!file.exists()) {
            return false;
        } else {
            if (getPlayerConfig(UUID).getString("warn-history." + warn) != null) {
                final FileConfiguration config = getPlayerConfig(UUID);
                config.set("warn-history." + warn + ".warn", BeanPunishments.getHelper().translateColors("Removed"));
                config.set("warn-history." + warn + ".warner", punisher);
                try {
                    config.save(new File(filepath + "/" + UUID.toString() + ".yml"));
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                return true;
            } else {
                return false;
            }
        }
    }
}