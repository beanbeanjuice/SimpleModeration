package com.beanbeanjuice.beanmoderation.command.annoy;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A class that deals with the permission logic for the annoy command.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class AnnoyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ISubCommand annoyPlayerSubCommand = new AnnoyPlayerSubCommand();
        if (!annoyPlayerSubCommand.userCanRun(sender)) {
            Helper.sendNoPermission(sender);
            return false;
        }
        return annoyPlayerSubCommand.handle(sender, args);
    }

}
