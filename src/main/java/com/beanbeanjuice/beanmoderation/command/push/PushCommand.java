package com.beanbeanjuice.beanmoderation.command.push;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ICommand;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Handles the permissions for pushing people.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class PushCommand implements ICommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("", new PushPlayerSubCommand());
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
        return "push";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
