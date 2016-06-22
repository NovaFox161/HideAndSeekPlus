package com.cloudcraftgaming.hideandseekplus.utils;

/**
 * Created by Nova Fox on 11/15/2015.
 */
import com.cloudcraftgaming.hideandseekplus.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileManager {
    protected static Double conVersion = 1.0;
    protected static Double messageVersion = 1.0;

    public static void createConfig() {
        File file = new File(Main.plugin.getDataFolder() + File.separator + "config.yml");
        if (!(file.exists())) {
            Main.plugin.getLogger().info("Generating config.yml in folder /plugins/HideAndSeekPlus/");

            Main.plugin.getConfig().addDefault("DO NOT DELETE", "HideAndSeekPlus is developed and managed by Shades161");
            Main.plugin.getConfig().addDefault("Config Version", conVersion);
            Main.plugin.getConfig().addDefault("Check for Updates", true);
            Main.plugin.getConfig().addDefault("Language", "English");
            Main.plugin.getConfig().addDefault("Console.Verbose", true);

            Main.plugin.getConfig().addDefault("Game.BroadcastWinner", true);
            Main.plugin.getConfig().addDefault("Game.BroadcastStart", true);
            Main.plugin.getConfig().addDefault("Chat.PerGame", true);
            Main.plugin.getConfig().addDefault("Chat.Prefix", "&4[HnS-%Id%]");
            Main.plugin.getConfig().addDefault("Chat.PerWorldChatPlus.CompatibilityMode", true);
            List<String> list = Main.plugin.getConfig().getStringList("Game.BlockedCommands");
            list.add("warp");
            list.add("hub");
            list.add("tpa");
            Main.plugin.getConfig().set("Game.BlockedCommands", list);
            Main.plugin.getConfig().options().copyDefaults(true);
            Main.plugin.saveConfig();

            Main.plugin.getConfig().options().copyDefaults(true);
            Main.plugin.saveConfig();
        }
    }

    public static void createPluginCache() {
        if (!getPluginCacheFile().exists()) {
            Main.plugin.getLogger().info("Generating pluginCache.yml...");

            YamlConfiguration cache = getPluginCacheYml();

            cache.addDefault("DO NOT DELETE", "HideAndSeekPlus is developed and managed by Shades161");
            cache.addDefault("NextArenaId", 1);
            cache.addDefault("ArenaAmount", 0);

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, getPluginCacheFile());

            cache.options().copyDefaults(true);
            Main.plugin.saveCustomConfig(cache, getPluginCacheFile());

        }
    }

    public static void checkFileVersions() {
        if (Main.plugin.getConfig().getDouble("Config Version") != conVersion) {
            Main.plugin.getLogger().severe("Config Outdated! Please delete it and restart the server to get the new one!");
            Main.plugin.getLogger().severe("Disabling plugin to prevent future errors!");
            Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
        }
        if (MessageManager.getMessagesYml().getDouble("Messages Version") != messageVersion) {
            Main.plugin.getLogger().severe("Messages files outdated! Please delete it and restart the server to get the new one!");
            Main.plugin.getLogger().severe("Disabling plugin to prevent future errors!");
            Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
        }
    }

    public static File getPluginCacheFile() {
        return new File(Main.plugin.getDataFolder() + "/Cache/plugin.yml");
    }
    public static YamlConfiguration getPluginCacheYml() {
        return YamlConfiguration.loadConfiguration(getPluginCacheFile());
    }

    //Functionals
    public static void savePluginCacheFile(YamlConfiguration pluginCache) {
        try {
            pluginCache.save(getPluginCacheFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Other
    public static Boolean verbose() {
        return Main.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True");
    }
}