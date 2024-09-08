package com.beanbeanjuice.simplemoderation;

import com.beanbeanjuice.simplemoderation.utility.handler.mute.Mute;
import com.beanbeanjuice.simplemoderation.utility.handler.mute.MuteHandler;
import com.tcoded.folialib.FoliaLib;
import com.beanbeanjuice.simplemoderation.commands.PingCommand;
import com.beanbeanjuice.simplemoderation.commands.freeze.FreezeCommand;
import com.beanbeanjuice.simplemoderation.commands.freeze.UnfreezeCommand;
import com.beanbeanjuice.simplemoderation.database.PunishmentDatabase;
import com.beanbeanjuice.simplemoderation.utility.config.CustomConfig;
import com.beanbeanjuice.simplemoderation.utility.handler.FreezeHandler;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;

public final class SimpleModeration extends JavaPlugin {

    @Getter private CustomConfig customConfig;

    // Paper/Folia
    private BukkitAudiences miniMessage;
    @Getter private FoliaLib foliaLib;

    // Handlers
    @Getter private FreezeHandler freezeHandler;
    @Getter private MuteHandler muteHandler;

    // Database
    @Getter private PunishmentDatabase database;

    public BukkitAudiences getMiniMessage() {
        if (this.miniMessage == null) throw new NullPointerException("Accessing mini-message API while plugin is disabled...");

        return this.miniMessage;
    }

    private void sendStartupString() {
        String pluginVersion = this.getDescription().getVersion();
        String serverName = this.getServer().getName();
        String serverVersion = this.getServer().getBukkitVersion();

        Bukkit.getLogger().info("  \033[0;91m __");
        Bukkit.getLogger().info(String.format("  \033[0;91m|__  \033[0;93m|\\/|  \033[0;96mSimple Moderation \033[0;36mv%s", pluginVersion));
        Bukkit.getLogger().info(String.format("  \033[0;91m __| \033[0;93m|  | \033[0;97mRunning on \033[0;95m%s %s", serverName, serverVersion));
        Bukkit.getLogger().info("");
    }

    @Override
    public void onLoad() {
        this.foliaLib = new FoliaLib(this);
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        this.sendStartupString();
        this.getLogger().log(Level.INFO, "Plugin starting...");

        this.customConfig = new CustomConfig(this.getDataFolder());
        this.customConfig.initialize();
        this.getLogger().log(Level.INFO, "Configuration initialized.");

        this.miniMessage = BukkitAudiences.create(this);
        this.getLogger().log(Level.INFO, "Minimessage Enabled.");

        this.createDatabaseConnection();
        this.hookPlugins();
        this.initializeHandlers();

        this.getLogger().log(Level.INFO, "Registering commands...");
        this.registerCommands();
        this.getLogger().log(Level.INFO, "Commands registered.");

        this.getLogger().log(Level.INFO, String.format("Plugin started! (%dms)", System.currentTimeMillis() - startTime));
    }

    private void hookPlugins() {
        boolean hookFound = false;
        this.getLogger().log(Level.INFO, "Casting hooks!");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().log(Level.INFO, "Caught PlaceholderAPI!");
            hookFound = true;
        }

        if (!hookFound) this.getLogger().log(Level.INFO, "Nothing was caught.");
    }

    private void initializeHandlers() {
        this.freezeHandler = new FreezeHandler(this);
        this.muteHandler = new MuteHandler(this);

        this.getServer().getPluginManager().registerEvents(this.freezeHandler, this);
        this.getServer().getPluginManager().registerEvents(this.muteHandler, this);
    }

    private void registerCommands() {
        try {
            this.getCommand("ping").setExecutor(new PingCommand(this));
            this.getCommand("freeze").setExecutor(new FreezeCommand(this));
            this.getCommand("unfreeze").setExecutor(new UnfreezeCommand(this));
        } catch (NullPointerException e) {
            this.getLogger().log(Level.SEVERE, "Failed to register commands: ", e);
        }
    }

    private void createDatabaseConnection() {
        this.getLogger().info("Attemping to connect to database...");
        try {
            this.database = new PunishmentDatabase(this.getDataFolder().getPath() + "/punishments.db");
            this.getLogger().info("Database connected!");
        } catch (SQLException e) {
            this.getLogger().log(Level.SEVERE, "Unable to connect to database: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Plugin shutting down...");

        if (this.miniMessage != null) {
            this.miniMessage.close();
            this.miniMessage = null;
            this.getLogger().log(Level.INFO, "Minimessage disabled.");
        }

        try {
            this.getLogger().info("Closing database connection...");
            this.database.closeConnection();
            this.getLogger().info("Database connection closed.");
        } catch (SQLException e) {
            this.getLogger().severe("Unable to close database connection: " + e.getMessage());
        }
    }

}
