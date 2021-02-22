package beanbeanjuice.beanpunishments.commands;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Punish implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            // TODO: Not a player
            return false;
        }

        if (!checkArgs(this, (Player) sender, args)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            player.sendMessage(args[0]);
            return true;
        }

        player.sendMessage(args[0] + " " + args[1]);
        return true;
    }

    @Override
    public String getName() {
        return "punish";
    }

    @Override
    public ArrayList<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public CommandUsage getCommandUsage() {
        CommandUsage usage = new CommandUsage();
        usage.addUsage("player", CommandUsage.USAGE_TYPE.PLAYER, true);
        usage.addUsage("number", CommandUsage.USAGE_TYPE.NUMBER, false);
        return usage;
    }

    @Override
    public boolean checkArgs(CommandInterface command, Player player, String[] arguments) {
        return BeanPunishments.getCommandHandler().checkArguments(command, player, arguments);
    }
}
