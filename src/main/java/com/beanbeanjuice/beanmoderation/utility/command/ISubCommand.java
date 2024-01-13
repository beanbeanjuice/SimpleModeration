package com.beanbeanjuice.beanmoderation.utility.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface ISubCommand {

    boolean handle(@NotNull CommandSender sender, @NotNull String[] args);
    String getPermission();
    default boolean userCanRun(CommandSender sender) {
        return sender.isOp() || sender.hasPermission(getPermission());
    }
    default HashMap<String, ISubCommand> getSubCommands() {
        return null;
    }

}
