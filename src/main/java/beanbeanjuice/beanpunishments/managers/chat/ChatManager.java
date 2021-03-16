package beanbeanjuice.beanpunishments.managers.chat;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * A class used for handling chat.
 */
public class ChatManager implements Listener {

    private volatile boolean chatEnabled = true;

    /**
     * Makes sure that if the chat is muted, it will absorb the {@link AsyncPlayerChatEvent Chat Event}.
     * @param event The {@link AsyncPlayerChatEvent CHat Event} to be absorbed.
     */
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

    /**
     * @return Whether or not the chat is currently enabled.
     */
    public boolean isChatEnabled() {
        return chatEnabled;
    }

    /**
     * Toggles the current chat.
     */
    public void toggleChat() {
        chatEnabled = !chatEnabled;
    }
}