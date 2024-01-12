package com.beanbeanjuice.beanmoderation.utility.command;

import com.beanbeanjuice.beanmoderation.BeanModeration;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class CommandHandler {

    private final BeanModeration plugin;

    public CommandHandler(@NotNull BeanModeration plugin) {
        this.plugin = plugin;
    }

    public void initializeCommand(@NotNull String name, @NotNull CommandExecutor command) {
        plugin.getCommand(name).setExecutor(command);
    }

}
