package beanbeanjuice.beanpunishments.utilities;

import beanbeanjuice.beanpunishments.BeanPunishments;

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

    public String getConsolePrefix() {
        return "[beanPunishments] ";
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getConfigString(String identifier) {
        return translateColors(plugin.getConfig().getString(identifier));
    }

}
