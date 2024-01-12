package com.beanbeanjuice.beanpunishments;

import com.beanbeanjuice.beanpunishments.command.annoy.AnnoyCommand;
import com.beanbeanjuice.beanpunishments.command.clearchat.ClearChatCommand;
import com.beanbeanjuice.beanpunishments.command.find.FindCommand;
import com.beanbeanjuice.beanpunishments.command.push.PushCommand;
import com.beanbeanjuice.beanpunishments.utility.Helper;
import com.beanbeanjuice.beanpunishments.utility.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanPunishments extends JavaPlugin {

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
