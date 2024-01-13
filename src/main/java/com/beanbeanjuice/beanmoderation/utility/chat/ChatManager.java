package com.beanbeanjuice.beanmoderation.utility.chat;

import com.beanbeanjuice.beanmoderation.utility.Helper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    private static boolean isMuted;

    public ChatManager() {
        isMuted = false;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!isMuted) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("beanmoderation.mutechat.bypass") && !player.isOp()) {
            event.setCancelled(true);
            Helper.sendMessageConfig(player, "mutechat-player-muted");
        }
    }

    public static void toggleChat() {
        isMuted = !isMuted;
    }

    public static boolean isChatMuted() {
        return isMuted;
    }

}
