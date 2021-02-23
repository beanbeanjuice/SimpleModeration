package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Annoy implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Checks to see if something is run in the console

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            if (helper.getConfigStringList("annoy-blacklisted-players").contains(punishee.getName())) {
                sender.sendMessage("[beanPunishments] Sorry, you cannot annoy that player.");
                return false;
            }
            annoy(punishee);
            sender.sendMessage(("[beanPunishments] %s has been successfully annoyed.").replace("%s", punishee.getName()));
            return true;
        }

        // If not a console user, runs as a player
        Player punisher = (Player) sender;

        if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
            if (helper.getConfigStringList("annoy-blacklisted-players").contains(punishee.getName())) {
                punisher.sendMessage(helper.getPrefix() +
                        helper.getConfigString("annoy-not-allowed").replace("%player%", punishee.getName()));
                return false;
            } else {
                annoy(punishee);
                punisher.sendMessage(helper.getPrefix() +
                        helper.getConfigString("annoy-successful").replaceAll("%player%", punishee.getName()));
                return true;
            }
        } else {
            punisher.sendMessage(helper.getPrefix() +
                    helper.getConfigString("no-permission"));
            return false;
        }
    }

    @Override
    public String getName() {
        return "annoy";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> permissionsArrayList = new ArrayList<>();
        permissionsArrayList.add("beanpunishments.annoy"); // #0
        return permissionsArrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage commandUsage = new CommandUsage();
        commandUsage.addUsage("player", CommandUsage.USAGE_TYPE.PLAYER, true);
        return commandUsage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }

    // Method for playing sounds to annoy player
    private void annoy(Player player) {
        for (int i = 0; i < 1000; i++) {
            Location location = player.getLocation();
            player.playSound(location, Sound.ENTITY_GHAST_SCREAM, 1.0F, 10F);
            player.playSound(location, Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0F, 10F);
            player.playSound(location, Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.0F, 10F);
            player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 10F);
        }
    }
}
