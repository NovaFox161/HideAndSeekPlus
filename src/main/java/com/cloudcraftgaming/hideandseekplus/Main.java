package com.cloudcraftgaming.hideandseekplus;

import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.commands.BaseCommand;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.listeners.*;
import com.cloudcraftgaming.hideandseekplus.utils.FileManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import com.cloudcraftgaming.hideandseekplus.utils.PluginChecker;
import com.cloudcraftgaming.hideandseekplus.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nova Fox on 11/15/2015.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus
 */
public class Main extends JavaPlugin {
    public static Main plugin;
    public UpdateChecker updateChecker;
    public Plugin perWorldChatPlus;

    public void onDisable() {
        unloadArenasDisable();
    }

    public void onEnable() {
        plugin = this;

        //Register listeners.
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);

        //Register commands.
        getCommand("has").setExecutor(new BaseCommand(this));
        getCommand("hasp").setExecutor(new BaseCommand(this));
        getCommand("hideandseek").setExecutor(new BaseCommand(this));


        FileManager.createConfig();
        FileManager.createPluginCache();
        MessageManager.createMessagesFiles();

        FileManager.checkFileVersions();

        PluginChecker.checkForPerWorldChatPlus();

        checkForUpdatesOnStart();

        loadArenasStartUp();
    }
    private void loadArenasStartUp() {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
                    if (FileManager.verbose()) {
                        getLogger().info("Loading all enabled arenas...");
                    }
                    for (Integer id : ArenaDataManager.getAllEnabledArenas()) {
                        if (!(ArenaManager.getManager().arenaLoaded(id))) {
                            ArenaManager.getManager().safeLoadArena(id);
                        }
                    }
                    if (FileManager.verbose()) {
                        getLogger().info("All enabled arenas loaded!");
                    }
                }
            }
        }, 20L * 5);
    }
    private void unloadArenasDisable() {
        if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
            if (FileManager.verbose()) {
                getLogger().info("Safely unloading all loaded and/or active arenas... Plugin will be disabled shortly.");
            }
            for (Integer id : ArenaDataManager.getAllEnabledArenas()) {
                if (ArenaManager.getManager().arenaLoaded(id)) {
                    ArenaManager.getManager().safeUnloadArena(id);
                }
            }
            if (FileManager.verbose()) {
                getLogger().info("Unloaded all loaded/active arenas... Plugin will now be disabled.");
            }
        }
    }

    private void checkForUpdatesOnStart() {
        if (getConfig().getString("Check for Updates").equalsIgnoreCase("True")) { //Checks for a plugin update upon enable.
            if (FileManager.verbose()) {
                getLogger().info("Checking for updates...");
            }
            this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/hideandseekplus/files.rss");
            if (this.updateChecker.UpdateNeeded()) {
                getLogger().info("A new version of HideAndSeekPlus is available! Version: " + updateChecker.getVersion());
                getLogger().info("Download it from: " + updateChecker.getLink());
            } else {
                if (FileManager.verbose()) {
                    getLogger().info("No new updates found... will check again later.");
                }
            }
        }
    }

    public void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}