package com.beanbeanjuice.beanpunishments.utility.command;

import com.beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.command.CommandExecutor;
import org.jetbrains.annotations.NotNull;

public class CommandHandler {

    private final BeanPunishments plugin;

    public CommandHandler(@NotNull BeanPunishments plugin) {
        this.plugin = plugin;
    }

    public void initializeCommand(@NotNull String name, @NotNull CommandExecutor command) {
        plugin.getCommand(name).setExecutor(command);
    }

}
