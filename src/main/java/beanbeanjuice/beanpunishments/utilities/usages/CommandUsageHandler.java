package beanbeanjuice.beanpunishments.utilities.usages;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.CommandInterface;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandUsageHandler {

    private final BeanPunishments plugin;
    private ArrayList<CommandInterface> commands;

    public CommandUsageHandler(BeanPunishments plugin) {
        this.plugin = plugin;
        commands = new ArrayList<>();
    }

    public void addCommand(CommandInterface command) {
        commands.add(command);
    }

    public void initializeCommands() {
        for (CommandInterface command : commands) {
            plugin.getCommand(command.getName()).setExecutor(command);
        }
    }

    public boolean checkArguments(CommandInterface command, Player player, String[] arguments) {
        for (CommandInterface commandOriginal : commands) {
            if (command.equals(commandOriginal)) {
                int length = arguments.length;
                for (int i = 0; i < length; i++) {
                    boolean allowed = commandOriginal.getCommandUsage().checkUsage(arguments[0], i);
                    if (!allowed) {
                        player.sendMessage(BeanPunishments.getHelper().getPrefix() +
                                commandOriginal.getCommandUsage().getUsages().get(i)
                                        .getUsageType().getMessage().replaceAll("%argument%", arguments[i]));
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

}
