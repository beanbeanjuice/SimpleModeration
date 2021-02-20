//package beanbeanjuice.beanpunishments.commands;
//
//import beanbeanjuice.beanpunishments.BeanPunishments;
//import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Sound;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
//public class Annoy implements CommandExecutor {
//
//    private BeanPunishments plugin;
//    private GeneralHelper helper;
//
//    public Annoy(BeanPunishments plugin) {
//        this.plugin = plugin;
//        plugin.getCommand("annoy").setExecutor(this);
//        helper = BeanPunishments.getHelper();
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        // Checks to see if something is run in the console
//        if (!(sender instanceof Player)) {
//            if (args.length == 1) {
//                if (Bukkit.getPlayer(args[0]) != null) {
//                    Player player = Bukkit.getPlayer(args[0]);
//                    if (plugin.getConfig().getStringList("annoy-blacklisted-players").contains(player.getName())) {
//                        sender.sendMessage("[beanPunishments] Sorry, you cannot annoy that player.");
//                        return false;
//                    }
//                    annoy(player);
//                    sender.sendMessage(("[beanPunishments] %s has been successfully annoyed.").replace("%s", player.getName()));
//                    return true;
//                } else {
//                    sender.sendMessage(("[beanPunishments] Sorry, player %s was not found.").replace("%s", args[0]));
//                    return false;
//                }
//            } else {
//                sender.sendMessage("[beanPunishments] Incorrect syntax. Please use /annoy (player)");
//                return false;
//            }
//        }
//
//        // If not a console user, runs as a player
//        Player punisher = (Player) sender;
//
//        if (punisher.hasPermission("beanpunishments.annoy")) {
//            if (args.length == 1) {
//                if (Bukkit.getPlayer(args[0]) != null) {
//                    Player punishee = Bukkit.getPlayer(args[0]);
//                    if (!plugin.getConfig().getStringList("annoy-blacklisted-players").contains(punishee.getName())) {
//                        annoy(punishee);
//                        punisher.sendMessage(helper.getPrefix() + helper.translateColors(plugin.getConfig().getString("annoy-successful").replace("{player}", punishee.getName())));
//                        return true;
//                    } else {
//                        punisher.sendMessage(helper.getPrefix() + helper.translateColors(plugin.getConfig().getString("annoy-not-allowed").replace("{player}", punishee.getName())));
//                        return false;
//                    }
//                } else {
//                    punisher.sendMessage(helper.getPrefix() + helper.translateColors(plugin.getConfig().getString("player-not-found").replace("{player}", args[0])));
//                    return false;
//                }
//            } else {
//                punisher.sendMessage(helper.getPrefix() + helper.translateColors(plugin.getConfig().getString("annoy-incorrect-syntax")));
//                return false;
//            }
//        } else {
//            punisher.sendMessage(helper.getPrefix() + helper.translateColors(plugin.getConfig().getString("no-permission")));
//            return false;
//        }
//    }
//
//    // Method for playing sounds to annoy player
//    private void annoy(Player player) {
//        for (int i = 0; i < 1000; i++) {
//            Location location = player.getLocation();
//            player.playSound(location, Sound.ENTITY_GHAST_SCREAM, 1.0F, 10F);
//            player.playSound(location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0F, 10F);
//            player.playSound(location, Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.0F, 10F);
//            player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 10F);
//        }
//    }
//}
