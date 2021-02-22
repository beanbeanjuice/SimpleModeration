package beanbeanjuice.beanpunishments;

import beanbeanjuice.beanpunishments.commands.*;
//import beanbeanjuice.beanpunishments.managers.chat.ChatManager;
//import beanbeanjuice.beanpunishments.managers.files.Warns;
//import beanbeanjuice.beanpunishments.managers.freeze.FreezeManager;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsage;
import beanbeanjuice.beanpunishments.utilities.usages.CommandUsageHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanPunishments extends JavaPlugin {

    private static GeneralHelper helper;
    private static CommandUsageHandler commandHandler;

    @Override
    public void onEnable() {
        helper = new GeneralHelper(this);
        commandHandler = new CommandUsageHandler(this);
        //FreezeManager.setupFreezeManager();
        saveDefaultConfig();
        //Warns.setupPlayerConfig();
        getLogger().info("BeanPunishments.jar has been enabled...");
//        new Reload(this);
//        new ClearChat(this);
//        new Freeze(this);
//        new UnFreeze(this);
//        new Warn(this);
//        new Annoy(this);
//        new MuteChat(this);
//        new FindWorld(this);
        commandHandler.addCommand(new Punish());
        commandHandler.addCommand(new Annoy());
        //getServer().getPluginManager().registerEvents(new ChatManager(this), this);

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
}
