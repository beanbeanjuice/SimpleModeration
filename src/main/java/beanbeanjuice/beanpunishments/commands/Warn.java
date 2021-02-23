package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.managers.files.WarnManager;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Warn implements CommandInterface {

    // TODO: Exception occurs when trying to retrieve a warn list from a player that doesn't exist.

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();
        WarnManager warnManager = BeanPunishments.getWarnManager();
        String command = args[0].toLowerCase();

        if (!(sender instanceof Player)) {

            switch (command) {

                case "add": {
                    if (args.length >= 3) {

                        if (punishee != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                            UUID UUID = getUUID(args[1]);

                            if (!helper.getConfigStringList("warn-blacklisted-players").contains(Bukkit.getOfflinePlayer(UUID).getName())) {
                                String warning = warnManager.addWarnPlayer(UUID, "CONSOLE", args);
                                String message = helper.getConfigString("warn-successful").replace("%player%", args[1]);
                                message = message.replace("%warner%", "&l&oCONSOLE&r");
                                message = helper.translateColors(message.replace("%warning%", warning));
                                Bukkit.broadcastMessage(helper.getPrefix() + message);
                                return true;
                            } else {
                                sender.sendMessage(helper.getPrefix() +
                                        helper.getConfigString("warn-not-allowed").replaceAll("%player%", args[1]));
                                return false;
                            }

                        } else {
                            sender.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }

                    } else {
                        sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-add-incorrect-syntax"));
                        return false;
                    }
                }

                case "remove": {
                    if (args.length == 3) {
                        if (punishee != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                            UUID UUID = getUUID(args[1]);

                            if (warnManager.removeWarnPlayer(UUID, "CONSOLE", args[2])) {
                                sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-removed")
                                        .replace("%warnID%", args[2]).replace("%player%", args[1]));
                                return true;
                            } else {
                                sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-id")
                                        .replace("%player%", args[1]).replace("%warnID%", args[2]));
                                return false;
                            }
                        } else {
                            sender.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }
                    } else {
                        sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-remove-incorrect-syntax"));
                        return false;
                    }
                }

                case "list": {
                    if (args.length == 2) {
                        if (punishee != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {
                            sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-list-get-others").replace("%player%", args[1]));
                            UUID UUID = getUUID(args[1]);
                            warnList(sender, UUID);
                            return true;
                        } else {
                            sender.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }
                    } else {
                        sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-list-incorrect-syntax"));
                        return false;
                    }
                }

                default: {
                    sender.sendMessage(helper.getPrefix() + helper.getConfigString("warn-incorrect-syntax"));
                    return false;
                }
            }
        }

        Player punisher = (Player) sender;

        switch (command) {
            case "add": {
                if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
                    if (args.length >= 3) {

                        if (punishee != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                            UUID UUID = getUUID(args[1]);

                            if (!helper.getConfigStringList("warn-blacklisted-players").contains(Bukkit.getOfflinePlayer(UUID).getName())) {
                                String warning = warnManager.addWarnPlayer(UUID, "CONSOLE", args);
                                String message = helper.getConfigString("warn-successful").replace("%player%", args[1]);
                                message = message.replace("%warner%", "&l&oCONSOLE&r");
                                message = helper.translateColors(message.replace("%warning%", warning));
                                Bukkit.broadcastMessage(helper.getPrefix() + message);
                                return true;
                            } else {
                                punisher.sendMessage(helper.getPrefix() +
                                        helper.getConfigString("warn-not-allowed").replaceAll("%player%", args[1]));
                                return false;
                            }

                        } else {
                            punisher.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }

                    } else {
                        punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-add-incorrect-syntax"));
                        return false;
                    }
                } else {
                    punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
                    return false;
                }
            }

            case "remove": {
                if (punisher.hasPermission(getPermissions().get(1)) || punisher.isOp()) {
                    if (args.length == 3) {
                        if (punishee != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                            UUID UUID = getUUID(args[1]);

                            if (warnManager.removeWarnPlayer(UUID, "CONSOLE", args[2])) {
                                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-removed")
                                        .replace("%warnID%", args[2]).replace("%player%", args[1]));
                                return true;
                            } else {
                                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-id")
                                        .replace("%player%", args[1]).replace("%warnID%", args[2]));
                                return false;
                            }
                        } else {
                            punisher.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-remove-incorrect-syntax"));
                        return false;
                    }
                } else {
                    punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
                    return false;
                }
            }

            case "list": {
                if (args.length == 1) {
                    punishee = punisher;
                    if (punisher.hasPermission(getPermissions().get(2)) || punisher.isOp()) {
                        if (warnManager.getPlayerConfig(punishee.getUniqueId()).getString("warn-history.warn1") != null) {
                            punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-list-get-self"));
                            warnList(punisher, punishee.getUniqueId());
                            return true;
                        } else {
                            punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-warns-self"));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-permission-list-self"));
                        return false;
                    }
                } else if (args.length == 2) {
                    if (punisher.hasPermission(getPermissions().get(3)) || (args[1].equals(punisher.getName()) && punisher.hasPermission("beanpunishments.warn.list.self")) || punisher.isOp()) {
                        if (Bukkit.getPlayer(args[1]) != null || Bukkit.getOfflinePlayer(args[1]).hasPlayedBefore()) {

                            UUID UUID = getUUID(args[1]);

                            if (warnManager.getPlayerConfig(UUID).getString("warn-history.warn1") != null) {
                                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-list-get-others").replace("%player%", args[1]));
                                warnList(punisher, UUID);
                                return true;
                            } else {
                                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-warns-others").replace("%player%", args[1]));
                                return false;
                            }
                        } else {
                            punisher.sendMessage(helper.getPrefix() + helper.playerNotFound(args[1]));
                            return false;
                        }
                    } else {
                        punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-no-permission-list-others"));
                        return false;
                    }
                } else {
                    punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-list-incorrect-syntax"));
                    return false;
                }
            }

            default: {
                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("warn-incorrect-syntax"));
                return false;
            }
        }
    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.warn.add"); // #0
        arrayList.add("beanpunishments.warn.remove"); // #1
        arrayList.add("beanpunishments.warn.list.self"); // #2
        arrayList.add("beanpunishments.warn.list.others"); // #3
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("list/remove/add", CommandUsage.USAGE_TYPE.TEXT, true);
        usage.addUsage("player", CommandUsage.USAGE_TYPE.TEXT, false);
        for (int i = 0; i < 100; i++) {
            usage.addUsage("extra arg", CommandUsage.USAGE_TYPE.TEXT, false);
        }
        return usage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }

    private void warnList(CommandSender toSend, UUID toGet) {
        int count = 1;
        boolean warnFound = false;
        while (BeanPunishments.getWarnManager().getPlayerConfig(toGet).getString("warn-history.warn" + count) != null) {
            if (!BeanPunishments.getWarnManager().getPlayerConfig(toGet).getString("warn-history.warn" + count + ".warn").equals("Removed")) {
                warnFound = true;
                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-separator"));
                String warnID = "warn" + count;
                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-id").replace("%ID%", warnID));

                String warn = BeanPunishments.getWarnManager().getPlayerConfig(toGet).getString("warn-history." + warnID + ".warn");
                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-warn-message").replace("%message%", warn));


                String warnedby = BeanPunishments.getWarnManager().getPlayerConfig(toGet).getString("warn-history." + warnID + ".warner");
                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-warn-punisher").replace("%player%", warnedby));


                String timestamp = BeanPunishments.getWarnManager().getPlayerConfig(toGet).getString("warn-history." + warnID + ".timestamp");
                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-warn-timestamp").replace("%timestamp%", timestamp));

                toSend.sendMessage(BeanPunishments.getHelper().getConfigString("warn-separator"));
            }
            count++;
        }
        if (!warnFound) {
            toSend.sendMessage(BeanPunishments.getHelper().getPrefix() + BeanPunishments.getHelper().translateColors("&cNo active warns found."));
        }
    }

    private UUID getUUID(String punishee) {
        UUID UUID;
        if (Bukkit.getPlayer(punishee) != null) {
            UUID = Bukkit.getPlayer(punishee).getUniqueId();
        } else {
            UUID = Bukkit.getOfflinePlayer(punishee).getUniqueId();
        }
        return UUID;
    }
}
