package com.beanbeanjuice.simplemoderation.utility.config;

import com.beanbeanjuice.simplemoderation.database.DatabaseType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfigKey {

    // config.yml
    PREFIX (ConfigType.GENERAL, "prefix", String.class),
    DATABASE_TYPE (ConfigType.GENERAL, "database.type", DatabaseType.class),

    // messages.yml
    PING_WITH_PLACEHOLDER_API_SUPPORT (ConfigType.MESSAGES, "ping.placeholder-api-found", String.class),
    PING_WITHOUT_PLACEHOLDER_API_SUPPORT (ConfigType.MESSAGES, "ping.placeholder-api-not-found", String.class),

    FREEZE_SUCCESS_SINGLE (ConfigType.MESSAGES, "freeze.success.single", String.class),
    FREEZE_SUCCESS_ALL (ConfigType.MESSAGES, "freeze.success.all", String.class),
    FREEZE_FAIL_OFFLINE (ConfigType.MESSAGES, "freeze.fail.offline", String.class),
    FREEZE_FAIL_BYPASS (ConfigType.MESSAGES, "freeze.fail.bypass", String.class),
    FREEZE_FROZEN_TITLE (ConfigType.MESSAGES, "freeze.frozen.title", String.class),
    FREEZE_FROZEN_SUBTITLE (ConfigType.MESSAGES, "freeze.frozen.subtitle", String.class),
    FREEZE_FROZEN_COMMAND_USE (ConfigType.MESSAGES, "freeze.frozen.command-use", String.class),
    FREEZE_ATTACK_SENT (ConfigType.MESSAGES, "freeze.attack.sent", String.class),
    FREEZE_ATTACK_RECEIVED (ConfigType.MESSAGES, "freeze.attack.received", String.class),
    FREEZE_BAN_ON_DISCONNECT (ConfigType.MESSAGES, "freeze.ban.ban-on-disconnect", Boolean.class),
    FREEZE_BAN_MESSAGE (ConfigType.MESSAGES, "freeze.ban.message", String.class),
    FREEZE_USAGE (ConfigType.MESSAGES, "freeze.usage", String.class),
    FREEZE_NO_PERMISSION_ALL (ConfigType.MESSAGES, "freeze.no-permission.all", String.class),

    UNFREEZE_SUCCESS_SINGLE (ConfigType.MESSAGES, "unfreeze.success.single", String.class),
    UNFREEZE_SUCCESS_ALL (ConfigType.MESSAGES, "unfreeze.success.all", String.class),
    UNFREEZE_FAIL_OFFLINE (ConfigType.MESSAGES, "unfreeze.fail.offline", String.class),
    UNFREEZE_USAGE (ConfigType.MESSAGES, "unfreeze.usage", String.class),
    UNFREEZE_NO_PERMISSION_ALL (ConfigType.MESSAGES, "unfreeze.no-permission.all", String.class),

    MUTE_SUCCESS_WITHOUT_REASON_NO_LENGTH (ConfigType.MESSAGES, "mute.success.without-reason.no-length", String.class),
    MUTE_SUCCESS_WITHOUT_REASON_WITH_LENGTH (ConfigType.MESSAGES, "mute.success.without-reason.with-length", String.class),
    MUTE_SUCCESS_WITH_REASON_NO_LENGTH (ConfigType.MESSAGES, "mute.success.with-reason.no-length", String.class),
    MUTE_SUCCESS_WITH_REASON_WITH_LENGTH (ConfigType.MESSAGES, "mute.success.with-reason.with-length", String.class),
    MUTE_MUTED (ConfigType.MESSAGES, "mute.muted", String.class);

    private final ConfigType type;
    private final String key;
    private final Class<?> classType;

}
