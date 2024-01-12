package com.beanbeanjuice.beanmoderation.utility.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ISubCommand {

    boolean handle(@NotNull CommandSender sender, @NotNull String[] args);
    String getPermission();
    default boolean userCanRun(CommandSender sender) {
        return sender.isOp() || sender.hasPermission(getPermission());
    }

}
