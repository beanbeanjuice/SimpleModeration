package com.beanbeanjuice.beanmoderation.command.freeze;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.chat.MovementManager;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class FreezeAllPlayersSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        MovementManager.toggleFrozen();

        if (MovementManager.isAllFrozen()) {
            Helper.broadcastMessage(
                    Helper.getParsedConfigString("freeze-all")
                            .replace("%player%", sender.getName())
            );
            return true;
        }

        Helper.broadcastMessage(
                Helper.getParsedConfigString("unfreeze-all")
                        .replace("%player%", sender.getName())
        );
        return true;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.freeze.all";
    }

}
