package com.beanbeanjuice.beanpunishments.command.push;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
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

        ISubCommand pushSubCommand = new PushOtherPlayerSubCommand();

        if (!sender.hasPermission(pushSubCommand.getPermission()) || !sender.isOp()) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("no-permission"));
            return false;
        }

        pushSubCommand.handle(sender, args);

        return false;
    }

}
