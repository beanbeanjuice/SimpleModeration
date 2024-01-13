package com.beanbeanjuice.beanmoderation.command.warn;

import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WarnListSubCommand implements ISubCommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("self", new WarnListSelfSubCommand());
        put("others", new WarnListOthersSubCommand());
    }};

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        return false;
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
