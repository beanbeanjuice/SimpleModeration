package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.freeze.FreezeManager;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UnFreeze implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            if (Bukkit.getPlayer(args[0]) != null && !args[0].equalsIgnoreCase("all")) {
                if (freezeCheck(punishee)) {
                    sender.sendMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-unfrozen")
                            .replace("%player%", punishee.getName()));
                    return true;
                } else {
                    sender.sendMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-already-unfrozen")
                            .replace("%player%", punishee.getName()));
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("all")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Bukkit.broadcastMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-unfreeze-all")
                                    .replace("%player%", "CONSOLE"));
                    if (freezeCheck(p)) {
                        sender.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-unfrozen").replace("%player%", p.getName()));
                    } else {
                        sender.sendMessage(helper.getPrefix() +
                                helper.getConfigString("freeze-already-unfrozen")
                        .replace("%player%", p.getName()));
                    }
                }
                return true;
            }

            else {
                sender.sendMessage(helper.getPrefix() + helper.playerNotFound(args[0]));
                return false;
            }
        }

        Player punisher = (Player) sender;

        if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
            if (punishee != null && !args[0].equalsIgnoreCase("all")) {
                if (freezeCheck(punishee)) {
                    punisher.sendMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-unfrozen").replace("%player%", punishee.getName()));
                    return true;
                } else {
                    punisher.sendMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-already-unfrozen").replace("%player%", punishee.getName()));
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("all")) {
                if (punisher.hasPermission(getPermissions().get(1)) || punisher.isOp()) {
                    Bukkit.broadcastMessage(helper.getPrefix() +
                            helper.getConfigString("freeze-unfreeze-all").replace("%player%", punisher.getName()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (freezeCheck(p)) {
                            punisher.sendMessage(helper.getPrefix() +
                                    helper.getConfigString("freeze-unfrozen").replace("%player%", p.getName()));
                        } else {
                            punisher.sendMessage(helper.getPrefix() +
                                    helper.getConfigString("freeze-already-unfrozen").replace("%player%", p.getName()));
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
        return "unfreeze";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.unfreeze"); // #0
        arrayList.add("beanpunishments.unfreeze.all"); // #1
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

    boolean freezeCheck(Player player) {
        if (BeanPunishments.getFreezeManager().checkFrozen(player)) {
            BeanPunishments.getFreezeManager().unfreezePlayer(player);
            return true;
        } else {
            return false;
        }
    }
}