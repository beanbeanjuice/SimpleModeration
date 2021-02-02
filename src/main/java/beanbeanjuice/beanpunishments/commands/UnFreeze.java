package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.freeze.FreezeManager;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnFreeze implements CommandExecutor {

    private BeanPunishments plugin;

    public UnFreeze(BeanPunishments plugin) {
        this.plugin = plugin;
        plugin.getCommand("unfreeze").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (freezeCheck(player)) {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s has been unfrozen.").replace("%s", player.getName()));
                        return true;
                    } else {
                        sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is not frozen. To freeze them, use /freeze (player).").replace("%s", player.getName()));
                        return false;
                    }
                } else if (args[0].equals("all")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Bukkit.broadcastMessage(GeneralHelper.translateColors(plugin.getConfig().getString("prefix")) + " " + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-unfreeze-all").replace("{player}", "CONSOLE")));
                        if (freezeCheck(p)) {
                            sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s has been frozen.").replace("%s", p.getName()));
                        } else {
                            sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%s is already frozen. To unfreeze them, use /unfreeze (player).").replace("%s", p.getName()));
                        }
                    }
                    return true;
                } else {
                    sender.sendMessage(GeneralHelper.getConsolePrefix() + "Player not found.");
                    return false;
                }
            } else {
                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Incorrect Syntax. Please use /unfreeze (player).");
                return false;
            }
        }

        Player punisher = (Player) sender;

        if (punisher.hasPermission("beanpunishments.unfreeze") || punisher.getName().equals("beanbeanjuice")) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player punishee = Bukkit.getPlayer(args[0]);
                    if (freezeCheck(punishee)) {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-unfrozen").replace("{player}", punishee.getName())));
                        return true;
                    } else {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-already-unfrozen").replace("{player}", punishee.getName())));
                        return false;
                    }
                } else if (args[0].equals("all")) {
                    if (punisher.hasPermission("beanpunishments.unfreeze.all")) {
                        Bukkit.broadcastMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-unfreeze-all").replace("{player}", punisher.getName())));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (freezeCheck(p)) {
                                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-unfrozen").replace("{player}", p.getName())));
                            } else {
                                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-already-unfrozen").replace("{player}", p.getName())));
                            }
                        }
                        return true;
                    } else {
                        punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
                        return false;
                    }
                } else {
                    punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("player-not-found").replace("{player}", args[0])));
                    return false;
                }
            } else {
                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("freeze-unfreeze-incorrect-syntax")));
                return false;
            }
        } else {
            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
            return false;
        }
    }

    boolean freezeCheck(Player player) {
        if (FreezeManager.checkFrozen(player)) {
            FreezeManager.unfreezePlayer(player);
            return true;
        } else {
            return false;
        }
    }
}