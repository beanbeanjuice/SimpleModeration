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

import java.util.ArrayList;

public class FindWorld implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!checkArgs(this, sender, args)) {
            return false;
        }

        Player punishee = Bukkit.getPlayer(args[0]);
        GeneralHelper helper = BeanPunishments.getHelper();

        if (!(sender instanceof Player)) {
            sender.sendMessage(helper.getPrefix() +
                    String.format("%s is in %s.", punishee.getName(), punishee.getWorld().getName()));
            return false;
        }

        Player punisher = (Player) sender;

        if (punisher.hasPermission(getPermissions().get(0)) || punisher.isOp()) {
            String formattedString = helper.getConfigString("find-world");
            formattedString = formattedString.replace("%player%", punishee.getName());
            formattedString = formattedString.replace("%world%", punishee.getWorld().getName());
            punisher.sendMessage(helper.getPrefix() + formattedString);
            return true;
        } else {
            punisher.sendMessage(helper.getPrefix() + helper.getNoPermission());
            return false;
        }
    }

    @Override
    public String getName() {
        return "find";
    }

    @Override
    public ArrayList<String> getPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("beanpunishments.findworld");
        return arrayList;
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("player", UsageType.PLAYER, true);
        return usage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, sender, arguments);
    }
}
