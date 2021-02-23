package beanbeanjuice.beanpunishments;

import beanbeanjuice.beanpunishments.commands.*;
import beanbeanjuice.beanpunishments.managers.chat.ChatManager;
import beanbeanjuice.beanpunishments.managers.files.WarnManager;
import beanbeanjuice.beanpunishments.managers.freeze.FreezeManager;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsageHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanPunishments extends JavaPlugin {

    private static GeneralHelper helper;
    private static CommandUsageHandler commandHandler;
    private static FreezeManager freezeManager;
    private static ChatManager chatManager;
    private static WarnManager warnManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        helper = new GeneralHelper(this);
        commandHandler = new CommandUsageHandler(this);
        freezeManager = new FreezeManager(this);
        chatManager = new ChatManager();
        warnManager = new WarnManager();
        getLogger().info("BeanPunishments.jar has been enabled...");
        commandHandler.addCommand(new Punish());
        commandHandler.addCommand(new Annoy());
        commandHandler.addCommand(new ClearChat());
        commandHandler.addCommand(new FindWorld());
        commandHandler.addCommand(new Freeze());
        commandHandler.addCommand(new UnFreeze());
        commandHandler.addCommand(new MuteChat());
        commandHandler.addCommand(new Push());
        commandHandler.addCommand(new Warn());
        commandHandler.addCommand(new Reload());
        getServer().getPluginManager().registerEvents(chatManager, this);

        // Initialize the commands after you have added all the commands.
        commandHandler.initializeCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("BeanPunishments.jar has been disabled...");
    }

    public static GeneralHelper getHelper() {
        return helper;
    }

    public static CommandUsageHandler getCommandHandler() {
        return commandHandler;
    }

    public static FreezeManager getFreezeManager() {
        return freezeManager;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static WarnManager getWarnManager() {
        return warnManager;
    }
}
