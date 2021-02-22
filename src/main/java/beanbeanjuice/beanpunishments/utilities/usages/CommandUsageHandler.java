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

                CommandUsage.ARGUMENT_AMOUNT argumentAmount = commandOriginal.getCommandUsage().checkTotal(arguments);

                switch (argumentAmount) {
                    case CORRECT_AMOUNT: {
                        break;
                    }

                    case NOT_ENOUGH:

                    case TOO_MANY: {
                        player.sendMessage(BeanPunishments.getHelper().getPrefix() +
                                argumentAmount.getMessage());
                        return false;
                    }
                }

                int length = arguments.length;
                for (int i = 0; i < length; i++) {
                    boolean allowed = commandOriginal.getCommandUsage().checkUsage(arguments[i], i);
                    if (!allowed) {
                        String message = BeanPunishments.getHelper().getPrefix() +
                                commandOriginal.getCommandUsage().getUsages().get(i)
                                        .getUsageType().getMessage().replaceAll("%argument%", arguments[i]);

                        message = message.replaceAll("%help%", command.getCommandUsage().getUsages().get(i).getHelp());
                        player.sendMessage(message);
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }

}
