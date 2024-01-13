package com.beanbeanjuice.beanmoderation.command.mutechat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ICommand;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MuteChatCommand implements ICommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("", new MuteChatSubCommand());
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!subCommands.get("").userCanRun(sender)) {
            Helper.sendNoPermission(sender);
            return false;
        }
        return subCommands.get("").handle(sender, args);
    }

    @Override
    public String getName() {
        return "mute-chat";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
