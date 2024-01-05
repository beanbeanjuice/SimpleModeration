package com.beanbeanjuice.beanpunishments.utility.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface ISubCommand {

    boolean handle(@NotNull CommandSender sender, @NotNull String[] args);
    String getPermission();

}
