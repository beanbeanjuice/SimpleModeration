package com.beanbeanjuice.simplemoderation.utility.handler.mute;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class MuteHandler implements Listener {

    private final SimpleModeration plugin;

    private final Set<UUID> mutedPlayers = new HashSet<>();

    public void mutePlayer(final UUID playerUUID) {
        this.mutePlayer(playerUUID, null, null);
    }

    public void mutePlayer(final UUID playerUUID, final String reason) {
        this.mutePlayer(playerUUID, null, reason);
    }

    public void mutePlayer(final UUID playerUUID, final Duration duration) {
        this.mutePlayer(playerUUID, duration, null);
    }

    public void mutePlayer(final UUID playerUUID, @Nullable final Duration duration, @Nullable final String reason) {
        mutedPlayers.add(playerUUID);

        Mute.MuteBuilder muteBuilder = Mute.builder()
                .playerUUID(playerUUID)
                .createdAt(Timestamp.from(Instant.now()));

        if (duration != null) muteBuilder.endsAt(Timestamp.from(Instant.now().plusNanos(duration.toNanos())));
        if (reason != null) muteBuilder.reason(reason);

        muteBuilder.build().startMuteTimer(this.plugin);
    }

    public void unmutePlayer(final UUID playerUUID) {
        mutedPlayers.remove(playerUUID);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if (!mutedPlayers.contains(player.getUniqueId())) return;

        event.setCancelled(true);
    }

}
