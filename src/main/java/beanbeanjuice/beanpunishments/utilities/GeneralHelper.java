package beanbeanjuice.beanpunishments.utilities;

import beanbeanjuice.beanpunishments.BeanPunishments;

public class GeneralHelper {

    private static String prefix;
    private static String nopermission;

    public GeneralHelper(BeanPunishments plugin) {
        prefix = translateColors(plugin.getConfig().getString("prefix")) + " ";
        nopermission = translateColors(plugin.getConfig().getString("no-permission"));
    }

    public static String translateColors(String string) {
        return string.replaceAll("&", "§");
    }

    public static String getConsolePrefix() {
        return "[beanPunishments] ";
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getNoPermission() {
        return nopermission;
    }

}
