package com.beanbeanjuice.beanmoderation.command.find;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
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
