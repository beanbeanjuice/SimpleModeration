package com.beanbeanjuice.beanmoderation.command.clearchat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.ICommand;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * An initial class that handles who should be able to run the command.
 *
 * @since 3.0.0
 * @author beanbeanjuice
 */
public class ClearChatCommand implements ICommand {

    private final HashMap<String, ISubCommand> subCommands = new HashMap<>() {{
        put("", new ClearChatSubCommand());
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0) {
            Helper.sendMessageConfig(sender, "clear-chat-incorrect-syntax");
            return false;
        }

        if (!subCommands.get("").userCanRun(sender)) {
            Helper.sendNoPermission(sender);
            return false;
        }

        return subCommands.get("").handle(sender, args);
    }

    @Override
    public String getName() {
        return "clear-chat";
    }

    @Override
    public HashMap<String, ISubCommand> getSubCommands() {
        return subCommands;
    }

}
