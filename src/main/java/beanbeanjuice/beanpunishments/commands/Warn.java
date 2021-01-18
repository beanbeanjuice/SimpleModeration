package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.files.Warns;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Warn implements CommandExecutor {

    private BeanPunishments plugin;
    private Warns PlayerConfig;

    public Warn(BeanPunishments plugin) {
        this.plugin = plugin;
        plugin.getCommand("warn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Incorrect syntax. Please use /warn (add/remove/list) (player) (warning).");
                return false;
            }
            if (args[0].equals("add")) {
                if (args.length >= 3) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                        UUID UUID = getUUID(args[1]);

                        if (!plugin.getConfig().getStringList("warn-blacklisted-players").contains(Bukkit.getOfflinePlayer(UUID).getName())) {
                            String warning = PlayerConfig.addWarnPlayer(UUID, "CONSOLE", args);
                            String prefix = GeneralHelper.translateColors(plugin.getConfig().getString("prefix")) + " ";
                            String message = plugin.getConfig().getString("warn-successful").replace("{player}", args[1]);
                            message = message.replace("{warner}", "&l&oCONSOLE&r");
                            message = GeneralHelper.translateColors(message.replace("{warning}", warning));
                            Bukkit.broadcastMessage(prefix + message);
                            return true;
                        } else {
                            sender.sendMessage(GeneralHelper.getConsolePrefix() + ("You cannot warn %s.").replace("%s", args[1]));
                            return false;
                        }
                    } else {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not a player.").replace("%s", args[1]));
                        return false;
                    }
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Invalid syntax. To warn a player, do /warn add (player) (warning)"));
                    return false;
                }
            } else if (args[0].equals("remove")) {
                if (args.length == 3) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                        UUID UUID = getUUID(args[1]);

                        if (PlayerConfig.removeWarnPlayer(UUID, "CONSOLE", args[2])) {
                            sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Successfully removed %id from %s.").replace("%id", args[2]).replace("%s", args[1]));
                            return true;
                        } else {
                            sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s does not have any warns with the ID %id").replace("%s", args[1]).replace("%id", args[2]));
                            return false;
                        }
                    } else {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not a player.").replace("%s", args[1]));
                        return false;
                    }
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Invalid Syntax. To remove a warn, do /warn remove (player) (warnID)"));
                    return false;
                }
            } else if (args[0].equals("list")) {
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Here are a list of %s's warns.").replace("%s", args[1]));
                        UUID UUID = getUUID(args[1]);
                        warnList(sender, UUID);
                        return true;
                    } else {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not a player.").replace("%s", args[1]));
                        return false;
                    }
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + "Invalid Syntax. To list the warns of a player, please use /warn list (player)");
                    return false;
                }
            } else {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Invalid syntax. Please use /warn (add/remove/list) (player) (warning)"));
                return false;
            }
        }

        Player punisher = (Player) sender;

        if (args.length == 0) {
            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-incorrect-syntax")));
            return false;
        }

        if (args[0].equals("add")) {
            if (punisher.hasPermission("beanpunishments.warn.add")) {
                if (args.length >= 3) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                        UUID UUID = getUUID(args[1]);

                        if (!plugin.getConfig().getStringList("warn-blacklisted-players").contains(args[1])) {
                            String warning = PlayerConfig.addWarnPlayer(UUID, punisher.getName(), args);
                            String message = plugin.getConfig().getString("warn-successful").replace("{player}", args[1]);
                            message = message.replace("{warner}", punisher.getName());
                            message = GeneralHelper.translateColors(message.replace("{warning}", warning));
                            Bukkit.broadcastMessage(GeneralHelper.getPrefix() + message);
                            return true;
                        } else {
                            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-not-allowed").replace("{player}", args[1])));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("player-not-found").replace("{player}", args[1])));
                        return false;
                    }
                } else {
                    punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-add-incorrect-syntax")));
                    return false;
                }
            } else {
                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                return false;
            }
        } else if (args[0].equals("remove")) {
            if (punisher.hasPermission("beanpunishments.warn.remove")) {
                if (args.length == 3) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                        UUID UUID = getUUID(args[1]);

                        if (PlayerConfig.removeWarnPlayer(UUID, punisher.getName(), args[2])) {
                            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-removed").replace("{warnID}", args[2])).replace("{player}", args[1]));
                            return true;
                        } else {
                            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-no-id").replace("{player}", args[1]).replace("{warnID}", args[2])));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("player-not-found").replace("{player}", args[1])));
                        return false;
                    }
                } else {
                    punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-remove-incorrect-syntax")));
                    return false;
                }
            } else {
                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                return false;
            }
        } else if (args[0].equals("list")) {
            Player player = punisher;
            if (args.length == 1) {
                if (player.hasPermission("beanpunishments.warn.list.self")) {
                    if (PlayerConfig.getPlayerConfig(player.getUniqueId()).getString("warn-history.warn1") != null) {
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-list-get-self")));
                        warnList(player, player.getUniqueId());
                        return true;
                    } else {
                        player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-no-warns-self")));
                        return false;
                    }
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-no-permission-list-self")));
                    return false;
                }
            } else if (args.length == 2) {
                if (punisher.hasPermission("beanpunishments.warn.list.others") || (args[1].equals(punisher.getName()) && punisher.hasPermission("beanpunishments.warn.list.self"))) {
                    if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                        UUID UUID = getUUID(args[1]);

                        if (PlayerConfig.getPlayerConfig(UUID).getString("warn-history.warn1") != null) {
                            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-list-get-others")).replace("{player}", args[1]));
                            warnList(punisher, UUID);
                            return true;
                        } else {
                            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-no-warns-others").replace("{player}", args[1])));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("player-not-found").replace("{player}", args[1])));
                        return false;
                    }
                } else {
                    player.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-no-permission-list-others")));
                    return false;
                }
            } else {
                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-incorrect-syntax")));
                return false;
            }
        } else {
            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("warn-incorrect-syntax")));
            return false;
        }
    }

    void warnList(CommandSender toSend, UUID toGet) {
        int count = 1;
        boolean warnFound = false;
        while (PlayerConfig.getPlayerConfig(toGet).getString("warn-history.warn" + count) != null) {
            if (!PlayerConfig.getPlayerConfig(toGet).getString("warn-history.warn" + count + ".warn").equals("Removed")) {
                warnFound = true;
                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-separator")));
                String warnID = "warn" + count;
                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-id").replace("{ID}", warnID)));

                String warn = PlayerConfig.getPlayerConfig(toGet).getString("warn-history." + warnID + ".warn");
                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-warn-message").replace("{message}", warn)));


                String warnedby = PlayerConfig.getPlayerConfig(toGet).getString("warn-history." + warnID + ".warner");
                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-warn-punisher").replace("{player}", warnedby)));


                String timestamp = PlayerConfig.getPlayerConfig(toGet).getString("warn-history." + warnID + ".timestamp");
                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-warn-timestamp").replace("{timestamp}", timestamp)));

                toSend.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("warn-separator")));
            }
            count++;
        }
        if (!warnFound) {
            toSend.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors("&cNo active warns found."));
        }
    }

    UUID getUUID(String punishee) {
        UUID UUID;
        if (Bukkit.getPlayer(punishee) != null) {
            UUID = Bukkit.getPlayer(punishee).getUniqueId();
        } else {
            UUID = Bukkit.getOfflinePlayer(punishee).getUniqueId();
        }
        return UUID;
    }
}
