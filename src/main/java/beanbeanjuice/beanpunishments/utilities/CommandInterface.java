package beanbeanjuice.beanpunishments.utilities;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface CommandInterface extends CommandExecutor {

    @Override
    boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    String getName();

    ArrayList<String> getPermissions();

    CommandUsage getCommandUsage();

    boolean checkArgs(CommandInterface command, CommandSender sender, String[] arguments);
}
