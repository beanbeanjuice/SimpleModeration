package com.beanbeanjuice.beanmoderation;

import com.beanbeanjuice.beanmoderation.command.annoy.AnnoyCommand;
import com.beanbeanjuice.beanmoderation.command.clearchat.ClearChatCommand;
import com.beanbeanjuice.beanmoderation.command.find.FindCommand;
import com.beanbeanjuice.beanmoderation.command.push.PushCommand;
import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanModeration extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Helper.initialize(this);
        initializeCommands();

        getLogger().info("The plugin has been enabled.");
    }

    private void initializeCommands() {
        CommandHandler handler = new CommandHandler(this);

        handler.initializeCommand("push", new PushCommand());
        handler.initializeCommand("clearchat", new ClearChatCommand());
        handler.initializeCommand("find", new FindCommand());
        handler.initializeCommand("annoy", new AnnoyCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

}
