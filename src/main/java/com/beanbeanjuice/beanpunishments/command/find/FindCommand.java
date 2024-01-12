package com.beanbeanjuice.beanpunishments.command.find;

import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FindCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ISubCommand findPlayerSubCommand = new FindPlayerSubCommand();
        if (!findPlayerSubCommand.userCanRun(sender)) {
            Helper.sendNoPermission(sender);
            return false;
        }
        return findPlayerSubCommand.handle(sender, args);
    }

}
