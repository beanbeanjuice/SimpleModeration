package beanbeanjuice.beanpunishments;

import beanbeanjuice.beanpunishments.commands.*;
import beanbeanjuice.beanpunishments.managers.chat.ChatManager;
import beanbeanjuice.beanpunishments.managers.files.Warns;
import beanbeanjuice.beanpunishments.managers.freeze.FreezeManager;
import beanbeanjuice.beanpunishments.utilities.GeneralHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeanPunishments extends JavaPlugin {

    @Override
    public void onEnable() {
        FreezeManager.setupFreezeManager();
        saveDefaultConfig();
        Warns.setupPlayerConfig();
        getLogger().info("BeanPunishments.jar has been enabled...");
        new GeneralHelper(this);
        new Push(this);
        new Reload(this);
        new ClearChat(this);
        new Freeze(this);
        new UnFreeze(this);
        new Warn(this);
        new Annoy(this);
        new MuteChat(this);
        new FindWorld(this);
        getServer().getPluginManager().registerEvents(new ChatManager(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("BeanPunishments.jar has been disabled...");
    }
}
