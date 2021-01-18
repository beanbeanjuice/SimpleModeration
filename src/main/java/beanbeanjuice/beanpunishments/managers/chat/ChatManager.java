package beanbeanjuice.beanpunishments.managers.chat;

import beanbeanjuice.beanpunishments.BeanPunishments;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

    private static volatile boolean chatEnabled = true;
    private BeanPunishments plugin;

    public ChatManager(BeanPunishments plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (!chatEnabled) {
            Player player = event.getPlayer();
            if (!player.hasPermission("beanpunishments.mutechat.bypass")) {
                player.sendMessage(GeneralHelper.translateColors(plugin.getConfig().getString("prefix")) + " " + GeneralHelper.translateColors(plugin.getConfig().getString("mutechat-player-muted")));
                event.setCancelled(true);
            }
        }
    }

    public static boolean isChatEnabled() {
        return chatEnabled;
    }

    public static void toggleChat() {
        chatEnabled = !chatEnabled;
    }
}