package com.beanbeanjuice.beanmoderation.command.freeze;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ICommand;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class FreezeCommand implements ICommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("", new FreezeIndividualPlayerSubCommand());
        put("all", new FreezeAllPlayersSubCommand());
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            Helper.sendMessageConfig(sender, "freeze-incorrect-syntax");
            return false;
        }

        ISubCommand subCommand = subCommands.get(args[0]);

        if (subCommand == null) return subCommands.get("").handle(sender, args);
        return subCommand.handle(sender, args);
    }

    @Override
    public String getName() {
        return "freeze";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
