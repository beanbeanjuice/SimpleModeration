package com.beanbeanjuice.simplemoderation.utility.handler.mute;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mute {

    private UUID playerUUID;
    private Timestamp createdAt;
    private Timestamp endsAt;
    private String reason;

    public void startMuteTimer(final SimpleModeration plugin) {
        if (endsAt == null) return;

        plugin.getFoliaLib().getScheduler().runLaterAsync(() -> {
            plugin.getMuteHandler().unmutePlayer(playerUUID);

            // TODO: Unmute in database as well.
        }, endsAt.getTime() - createdAt.getTime(), TimeUnit.MILLISECONDS);
    }

}
