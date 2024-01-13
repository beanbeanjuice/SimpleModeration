package com.beanbeanjuice.beanmoderation.command.warn;

import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WarnListSelfSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.warn.list.self";
    }

}
