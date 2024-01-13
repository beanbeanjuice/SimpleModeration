package com.beanbeanjuice.beanmoderation.command.mutechat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.chat.ChatManager;
import com.beanbeanjuice.beanmoderation.utility.command.ISubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MuteChatSubCommand implements ISubCommand {

    @Override
    public boolean handle(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length != 0) {
            Helper.sendMessageConfig(sender, "mutechat-incorrect-syntax");
            return false;
        }

        ChatManager.toggleChat();

        if (ChatManager.isChatMuted()) {
            Helper.broadcastMessage(Helper.getParsedConfigString("mutechat-broadcast-muted"));
            return true;
        }

        Helper.broadcastMessage(Helper.getParsedConfigString("mutechat-broadcast-unmuted"));
        return true;
    }

    @Override
    public String getPermission() {
        return "beanmoderation.mutechat";
    }

}
