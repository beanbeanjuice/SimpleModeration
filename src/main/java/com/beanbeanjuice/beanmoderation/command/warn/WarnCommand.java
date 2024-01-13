package com.beanbeanjuice.beanmoderation.command.warn;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ICommand;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class WarnCommand implements ICommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("add", new WarnAddSubCommand());
        put("remove", new WarnRemoveSubCommand());
        put("list", new WarnListSubCommand());
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Helper.sendMessage(sender, Helper.translateColors("&cThis has not been implemented yet."));  // TODO: Implement
        return false;
    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
