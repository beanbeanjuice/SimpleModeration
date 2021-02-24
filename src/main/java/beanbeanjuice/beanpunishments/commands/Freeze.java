package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Freeze implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {

            if (punishee != null && !args[0].equalsIgnoreCase("all")) {
                // Freeze all players
                if (!helper.getConfigStringList("freeze-blacklisted-players").contains(punishee.getName())) {

                    if (isNotFrozen(punishee)) { // Checks if the player is frozen, if not, freezes them.
                        sender.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-frozen")
                                        .replace("%player%", punishee.getName()));
                        return true;
                    } else { // The player is already frozen
                        sender.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-already-frozen")
                                        .replace("%player%", punishee.getName()));
                        return false;
                    }
                } else {
                    sender.sendMessage(helper.getPrefix() +
                            helper
                                    .getConfigString("freeze-not-allowed")
                                    .replace("%player%", punishee.getName()));
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("all")) {
                Bukkit.broadcastMessage(helper.getPrefix() +
                        helper.getConfigString("freeze-freeze-all").replace("%player%", "CONSOLE"));
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!helper.getConfigStringList("freeze-blacklisted-players").contains(player.getName())) {
                        if (isNotFrozen(player)) {
                            sender.sendMessage(helper.getPrefix() +
                                    helper.getConfigString("freeze-frozen")
                                            .replace("%player%", player.getName()));
                        } else {
                            sender.sendMessage(helper.getPrefix() +
                                    helper.getConfigString("freeze-already-frozen")
                                            .replace("%player%", player.getName()));
                        }
                    }
                }
                return true;
            } else {
                sender.sendMessage(helper.getPrefix() + helper.playerNotFound(args[0]));
                return false;
            }
        }

        Player punisher = (Player) sender;

        if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
            if (punishee != null && !args[0].equalsIgnoreCase("all")) {
                if (!helper.getConfigStringList("freeze-blacklisted-players").contains(punishee.getName())) {

                    if (isNotFrozen(punishee)) { // Checks if the player is frozen, if not, freezes them.
                        punisher.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-frozen")
                                        .replace("%player%", punishee.getName()));
                        return true;
                    } else { // The player is already frozen
                        punisher.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-already-frozen")
                                        .replace("%player%", punishee.getName()));
                        return false;
                    }
                } else {
                    punisher.sendMessage(helper.getPrefix() +
                            helper
                                    .getConfigString("freeze-not-allowed")
                                    .replace("%player%", punishee.getName()));
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("all")) {
                if (punisher.hasPermission(getPermissions().get(1))) {
                    Bukkit.broadcastMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-freeze-all").replace("%player%", "CONSOLE"));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (!helper.getConfigStringList("freeze-blacklisted-players").contains(player.getName())) {
                            if (isNotFrozen(player)) {
                                punisher.sendMessage(helper.getPrefix() +
                                        helper.getConfigString("freeze-frozen")
                                                .replace("%player%", player.getName()));
                            } else {
                                punisher.sendMessage(helper.getPrefix() +
                                        helper.getConfigString("freeze-already-frozen")
                                                .replace("%player%", player.getName()));
                            }
                        }
                    }
                    return true;
                } else {
                    punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
                    return false;
                }
            } else {
                punisher.sendMessage(helper.getPrefix() + helper.playerNotFound(args[0]));
                return false;
            }
        } else {
            punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
            return false;
        }
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.freeze"); // #0
        arrayList.add("beanpunishments.freeze.all"); // #1
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("player name/all", CommandUsage.USAGE_TYPE.TEXT, true);
        return usage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }

    boolean isNotFrozen(Player player) {
        if (!BeanPunishments.getFreezeManager().checkFrozen(player)) {
            BeanPunishments.getFreezeManager().freezePlayer(player);
            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 10);
            return true;
        } else {
            return false;
        }
    }
}