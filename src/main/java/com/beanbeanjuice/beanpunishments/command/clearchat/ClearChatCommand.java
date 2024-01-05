package com.beanbeanjuice.beanpunishments.command.clearchat;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * An initial class that handles who should be able to run the command.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ISubCommand subCommand = new ClearChatSubCommand();
        if (!sender.hasPermission(subCommand.getPermission()) || sender.isOp()) {
            Helper.sendMessage(sender, Helper.getParsedConfigString("no-permission"));
            return false;
        }
        return subCommand.handle(sender, args);
    }

}
