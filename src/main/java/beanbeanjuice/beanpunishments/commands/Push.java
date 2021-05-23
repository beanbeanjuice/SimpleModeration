package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import beanbeanjuice.beanpunishments.utilities.usages.object.UsageType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Push implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            sender.sendMessage(helper.getPrefix() + "Only players may execute this command.");
            return false;
        }

        Player punisher = (Player) sender;

        if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
            if (isBlacklisted(args[0])) {
                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("push-not-allowed")
                .replace("%player%", punishee.getName()));
                return false;
            }

            if (Math.abs(Integer.parseInt(args[1])) > helper.getConfigInt("push-maximum")) {
                punisher.sendMessage(helper.getPrefix() + helper.getConfigString("push-above-maximum")
                        .replace("%max%", helper.getConfigString("push-maximum")));
                return false;
            }
            pushPlayer(punisher, punishee, Integer.parseInt(args[1]));
            punisher.sendMessage(helper.getPrefix() + helper.getConfigString("successful-push").replace("%player%", punishee.getName()));
            return true;
        } else {
            punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
            return false;
        }
    }

    @Override
    public String getName() {
        return "push";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.push"); // #0
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("player", UsageType.PLAYER, true);
        usage.addUsage("push amount", UsageType.NUMBER, true);
        return usage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }

    void pushPlayer(Player punisher, Player punishee, int strength) {
        Vector direction = punisher.getLocation().getDirection();
        punishee.setVelocity(direction.multiply(strength));
    }

    boolean isBlacklisted(String punishee) {
        return BeanPunishments.getHelper().getConfigStringList("push-blacklisted-players").contains(punishee);
    }
}
