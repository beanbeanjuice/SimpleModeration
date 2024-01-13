package com.beanbeanjuice.beanmoderation.utility.command;

import com.beanbeanjuice.beanmoderation.BeanModeration;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandHandler {

    private final BeanModeration plugin;
    private final HashMap<String, ICommand> commands = new HashMap<>();

    public CommandHandler(@NotNull BeanModeration plugin) {
        this.plugin = plugin;
    }

    public void initializeCommands(@NotNull ICommand... newCommands) {
        for (ICommand command : newCommands) {
            commands.put(command.getName(), command);
            plugin.getCommand(command.getName()).setExecutor(command);
        }
    }

}
