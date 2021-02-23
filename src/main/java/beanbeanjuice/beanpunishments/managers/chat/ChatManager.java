package beanbeanjuice.beanpunishments.managers.chat;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    private volatile boolean chatEnabled = true;

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!chatEnabled) {
            Player player = event.getPlayer();
            if (!player.hasPermission("beanpunishments.mutechat.bypass")) {
                player.sendMessage(BeanPunishments.getHelper().getPrefix() + " " + BeanPunishments.getHelper().getConfigString("mutechat-player-muted"));
                event.setCancelled(true);
            }
        }
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void toggleChat() {
        chatEnabled = !chatEnabled;
    }
}