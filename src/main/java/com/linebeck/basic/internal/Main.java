package com.linebeck.basic.internal;

import com.linebeck.basic.commands.*;
import com.linebeck.basic.listeners.DeathListener;
import com.linebeck.basic.listeners.JoinListener;
import com.linebeck.basic.listeners.QuitListener;
import com.linebeck.basic.listeners.TeleportListener;
import com.linebeck.basic.managers.BasicPlayerManager;
import com.linebeck.basic.managers.HomeManager;
import com.linebeck.basic.managers.WarpManager;
import com.linebeck.basic.objects.BasicPlayer;
import com.linebeck.basic.objects.Home;
import com.linebeck.basic.objects.Warp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Main main;
    public static Main getInstance() {
        return main;
    }

    private File configFile;
    public File getConfigFile() {
        return configFile;
    }

    private boolean showJoinMessage;
    public boolean getShowJoinMessage() {
        return showJoinMessage;
    }

    private boolean showLeaveMessage;
    public boolean getShowLeaveMessage() {
        return showLeaveMessage;
    }

    private String mainHexColor;
    public String getMainHexColor() {
        return mainHexColor;
    }

    private String subHexColor;
    public String getSubHexColor() {
        return subHexColor;
    }

    private int teleportCoolDown;
    public int getTeleportCoolDown() {
        return teleportCoolDown;
    }

    private int warpCoolDown;
    public int getWarpCoolDown() {
        return warpCoolDown;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void onEnable() {
        this.getLogger().info("#########################");
        this.getLogger().info(getPluginMeta().getName());
        this.getLogger().info("Version: " + getPluginMeta().getVersion());
        this.getLogger().info("Author: " + getPluginMeta().getAuthors());
        this.getLogger().info("#########################");

        main = this;

        loadConfiguration();
        registerSerializers();

        // Managers
        BasicPlayerManager basicPlayerManager = new BasicPlayerManager();
        HomeManager homeManager = new HomeManager();
        WarpManager warpManager = new WarpManager();

        // Commands
        new BackCommand(basicPlayerManager);
        new BasicCommand();
        new ClearInventoryCommand();
        new DeleteHomeCommand(homeManager);
        new DeleteWarpCommand(warpManager);
        new EnchantmentCommand();
        new FeedCommand();
        new FlyCommand();
        new FlySpeedCommand();
        new GamemodeAdventureCommand();
        new GamemodeCommand();
        new GamemodeCreativeCommand();
        new GamemodeSpectatorCommand();
        new GamemodeSurvivalCommand();
        new HealCommand();
        new HomeCommand(homeManager);
        new KillAllCommand();
        new ListHomesCommand(homeManager);
        new MoreCommand();
        new PingCommand();
        new PlayerTimeCommand();
        new RepairCommand();
        new SeenCommand();
        new SetHomeCommand(homeManager);
        new SetWarpCommand(warpManager);
        new SuicideCommand();
        new TeleportAllCommand();
        new TeleportHereCommand();
        new TeleportOfflineCommand(basicPlayerManager);
        new TeleportRequestAcceptCommand();
        new TeleportRequestCommand();
        new TeleportRequestDenyCommand();
        new TimeCommand();
        new WalkSpeedCommand();
        new WarpCommand(warpManager);
        new WeatherCommand();
        new WhoIsCommand(basicPlayerManager);

        // Listeners
        Bukkit.getPluginManager().registerEvents(new DeathListener(basicPlayerManager), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(basicPlayerManager), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(basicPlayerManager), this);
        Bukkit.getPluginManager().registerEvents(new TeleportListener(basicPlayerManager), this);
    }

    // Default Configuration File.
    private void loadConfiguration() {
        try {
            this.configFile = new File(getDataFolder(), "config.yml");
            if (!this.configFile.exists()) {
                saveDefaultConfig();
            }

            this.mainHexColor = this.getConfig().getString("Main-HexColor");
            this.subHexColor = this.getConfig().getString("Sub-HexColor");

            this.teleportCoolDown = getConfig().getInt("Teleport-Cooldown");
            this.warpCoolDown = getConfig().getInt("Warp-Cooldown");

            this.showJoinMessage = this.getConfig().getBoolean("Join-Message");
            this.showLeaveMessage = this.getConfig().getBoolean("Leave-Message");

        } catch (Exception exception) {
            this.getLogger().info("Unable to load the configuration properly!");
        }
    }

    private void registerSerializers() {
        ConfigurationSerialization.registerClass(BasicPlayer.class);
        ConfigurationSerialization.registerClass(Home.class);
        ConfigurationSerialization.registerClass(Warp.class);
    }

    @Override
    public void onDisable() {}
}