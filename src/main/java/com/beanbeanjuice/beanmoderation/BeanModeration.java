package com.beanbeanjuice.beanmoderation;

import com.beanbeanjuice.beanmoderation.command.annoy.AnnoyCommand;
import com.beanbeanjuice.beanmoderation.command.clearchat.ClearChatCommand;
import com.beanbeanjuice.beanmoderation.command.find.FindCommand;
import com.beanbeanjuice.beanmoderation.command.freeze.FreezeCommand;
import com.beanbeanjuice.beanmoderation.command.mutechat.MuteChatCommand;
import com.beanbeanjuice.beanmoderation.command.push.PushCommand;
import com.beanbeanjuice.beanmoderation.command.warn.WarnCommand;
import com.beanbeanjuice.beanmoderation.utility.Helper;
import com.beanbeanjuice.beanmoderation.utility.chat.ChatManager;
import com.beanbeanjuice.beanmoderation.utility.chat.MovementManager;
import com.beanbeanjuice.beanmoderation.utility.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanModeration extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Helper.initialize(this);
        getServer().getPluginManager().registerEvents(new ChatManager(), this);
        getServer().getPluginManager().registerEvents(new MovementManager(), this);

        initializeCommands();

        getLogger().info("The plugin has been enabled.");
    }

    private void initializeCommands() {
        CommandHandler handler = new CommandHandler(this);

        handler.initializeCommands(
                new PushCommand(),
                new ClearChatCommand(),
                new FindCommand(),
                new AnnoyCommand(),
                new MuteChatCommand(),
                new FreezeCommand(),
                new WarnCommand()
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

}
