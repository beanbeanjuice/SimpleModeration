package com.beanbeanjuice.beanmoderation.command.push;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the permissions for pushing people.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class PushCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ISubCommand pushSubCommand = new PushPlayerSubCommand();

        if (!pushSubCommand.userCanRun(sender)) {
            Helper.sendNoPermission(sender);
            return false;
        }

        return pushSubCommand.handle(sender, args);
    }

}
