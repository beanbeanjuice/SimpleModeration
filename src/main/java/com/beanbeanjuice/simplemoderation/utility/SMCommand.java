package com.beanbeanjuice.simplemoderation.utility;

import com.beanbeanjuice.simplemoderation.SimpleModeration;
import com.beanbeanjuice.simplemoderation.utility.config.CustomConfig;

public abstract class SMCommand {

    protected final SimpleModeration plugin;
    protected final CustomConfig config;

    public SMCommand(final SimpleModeration plugin) {
        this.plugin = plugin;
        this.config = plugin.getCustomConfig();
    }

}
