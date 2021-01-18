package beanbeanjuice.beanpunishments.managers.files;

import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Warns {

    private static String filepath = ("plugins/beanPunishments/playerdata/Warn History");

    public static void setupPlayerConfig() {
        File file = new File(filepath);

        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void addPlayer(UUID UUID) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration f = YamlConfiguration.loadConfiguration(file);
                f.createSection("warn-history");
                f.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileConfiguration getPlayerConfig(UUID UUID) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public static String addWarnPlayer(UUID UUID, String punisher, String[] reason) {
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
            e.printStackTrace();
        }
        return string.toString();
    }

    public static boolean removeWarnPlayer(UUID UUID, String punisher, String warn) {
        File file = new File(filepath + "/" + UUID.toString() + ".yml");
        if (!file.exists()) {
            return false;
        } else {
            if (getPlayerConfig(UUID).getString("warn-history." + warn) != null) {
                final FileConfiguration config = getPlayerConfig(UUID);
                config.set("warn-history." + warn + ".warn", GeneralHelper.translateColors("Removed"));
                config.set("warn-history." + warn + ".warner", punisher);
                try {
                    config.save(new File(filepath + "/" + UUID.toString() + ".yml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                return false;
            }
        }
    }
}