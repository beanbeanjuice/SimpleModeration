//package beanbeanjuice.beanpunishments.commands;
//
//import beanbeanjuice.beanpunishments.BeanPunishments;
//import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
//import org.bukkit.Bukkit;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
//public class FindWorld implements CommandExecutor {
//
//    private BeanPunishments plugin;
//
//    public FindWorld(BeanPunishments plugin) {
//        this.plugin = plugin;
//        plugin.getCommand("find").setExecutor(this);
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//
//        if (!(sender instanceof Player)) {
//            if (args.length == 1) {
//                if (Bukkit.getPlayer(args[0]) != null) {
//                    sender.sendMessage(GeneralHelper.getConsolePrefix() + ("%p is in %w.").replace("%p", args[0]).replace("%w", Bukkit.getPlayer(args[0]).getWorld().getName()));
//                    return true;
//                } else {
//                    sender.sendMessage(GeneralHelper.getConsolePrefix() + ("Sorry, %s is not a player.").replace("%s", args[0]));
//                    return false;
//                }
//            } else {
//                sender.sendMessage(GeneralHelper.getConsolePrefix() + "Sorry, that syntax is incorrect. Please use /find (player).");
//                return false;
//            }
//        }
//
//        Player punisher = (Player) sender;
//
//        if (punisher.hasPermission("beanpunishments.findworld") || punisher.getName().equals("beanbeanjuice")) {
//            if (args.length == 1) {
//                if (Bukkit.getPlayer(args[0]) != null) {
//                    Player punishee = Bukkit.getPlayer(args[0]);
//                    punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("findworld").replace("{player}", punishee.getName()).replace("{world}", punishee.getWorld().getName())));
//                    return true;
//                } else {
//                    punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("not-a-player").replace("{player}", args[0])));
//                    return false;
//                }
//            } else {
//                punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.translateColors(plugin.getConfig().getString("findworld-incorrect-syntax")));
//                return false;
//            }
//        } else {
//            punisher.sendMessage(GeneralHelper.getPrefix() + GeneralHelper.getNoPermission());
//            return false;
//        }
//    }
//}
