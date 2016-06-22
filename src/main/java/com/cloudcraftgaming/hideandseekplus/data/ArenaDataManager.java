package com.cloudcraftgaming.hideandseekplus.data;

import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.utils.Cuboid;
import com.cloudcraftgaming.hideandseekplus.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova Fox on 6/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class ArenaDataManager {
    //Functionals
    public static int createArena() {
        if (FileManager.verbose()) {
            Main.plugin.getLogger().info("Creating new arena...");
        }
        YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
        int id = pluginCache.getInt("NextArenaId");
        pluginCache.set("NextArenaId", id + 1);
        pluginCache.set("ArenaAmount", pluginCache.getInt("ArenaAmount" + 1));
        List<String> arenas = pluginCache.getStringList("Arenas.All");
        arenas.add(String.valueOf(id));
        pluginCache.set("Arenas.All", arenas);
        FileManager.savePluginCacheFile(pluginCache);

        createArenaConfig(id);

        if (FileManager.verbose()) {
            Main.plugin.getLogger().info("Created new arena with id: " + id);
        }
        return id;
    }
    public static void createArenaConfig(int id) {
        if (!arenaExists(id)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Generating config.yml for arena Id: " + id);
            }
            YamlConfiguration config = getArenaConfigYml(id);
            config.addDefault("Id", id);
            config.addDefault("Name", "Arena " + id);
            config.addDefault("DisplayName", "&6Arena " + id);
            config.addDefault("Players.Min", 4);
            config.addDefault("Players.Max", 16);
            config.addDefault("Time.Wait", 60);
            config.addDefault("Time.Start", 10);
            config.addDefault("Time.Hide", 60);
            config.addDefault("Time.Game", 8);
            config.addDefault("Rules.LateJoin", false);

            config.options().copyDefaults(true);
            saveArenaConfig(config, getArenaConfigFile(id));

            config.options().copyDefaults(true);
            saveArenaConfig(config, getArenaConfigFile(id));
        }
    }
    public static void saveArenaConfig(YamlConfiguration configYml, File configFile) {
        try {
            configYml.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enableArena(int id) {
        if (!arenaEnabled(id) && canBeLoaded(id)) {
            YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
            List<String> enabledArenas = pluginCache.getStringList("Arenas.Enabled");
            enabledArenas.add(String.valueOf(id));
            pluginCache.set("Arenas.Enabled", enabledArenas);
            FileManager.savePluginCacheFile(pluginCache);
            ArenaManager.getManager().safeLoadArena(id);
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Enabled arena id: " + id);
            }
        }
    }
    public static void disableArena(int id) {
        if (arenaEnabled(id)) {
            YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
            List<String> enabledArenas = pluginCache.getStringList("Arenas.Enabled");
            enabledArenas.remove(String.valueOf(id));
            pluginCache.set("Arenas.Enabled", enabledArenas);
            FileManager.savePluginCacheFile(pluginCache);
            ArenaManager.getManager().safeUnloadArena(id);
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Disabled arena id: " + id);
            }
        }
    }

    //Booleans/Checkers
    public static Boolean arenaExists(int i) {
        return FileManager.getPluginCacheYml().contains("Arenas.All") &&
                FileManager.getPluginCacheYml().getStringList("Arenas.All").contains(String.valueOf(i))
                && getArenaConfigFile(i).exists();
    }
    public static Boolean arenaEnabled(int id) {
        return FileManager.getPluginCacheYml().contains("Arenas.Enabled")
                && FileManager.getPluginCacheYml().getStringList("Arenas.Enabled").contains(String.valueOf(id));
    }
    public static Boolean canBeLoaded(int id) {
        if (!arenaExists(id)) {
            return false;
        }
        YamlConfiguration config = getArenaConfigYml(id);
        return config.contains("Locations.Quit") && config.contains("Locations.End")
                && config.contains("Locations.Lobby") && config.contains("Locations.Spawn")
                && config.contains("Locations.Regen");
    }
    //Getters
    public static File getArenaConfigFile(int id) {
        return new File(Main.plugin.getDataFolder() + "/Arenas/" + id + "/config.yml");
    }
    public static YamlConfiguration getArenaConfigYml(int id) {
        return YamlConfiguration.loadConfiguration(getArenaConfigFile(id));
    }

    public static ArrayList<Integer> getAllEnabledArenas() {
        ArrayList<Integer> enabledArenas = new ArrayList<>();
        if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
            for (String idString : FileManager.getPluginCacheYml().getStringList("Arenas.Enabled")) {
                enabledArenas.add(Integer.valueOf(idString));
            }
        }
        return enabledArenas;
    }

    public static String getName(int id) {
        return getArenaConfigYml(id).getString("Name");
    }
    public static String getDisplayName(int id) {
        return ChatColor.translateAlternateColorCodes('&', getArenaConfigYml(id).getString("DisplayName"));
    }
    public static String getChatPrefix(int id) {
        String prefixOr = Main.plugin.getConfig().getString("Chat.Prefix");
        String prefix = prefixOr.replaceAll("%Id%", String.valueOf(id));
        return ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.RESET;
    }
    public static int getMinPlayers(int id) {
        return getArenaConfigYml(id).getInt("Players.Min");
    }
    public static int getMaxPlayers(int id) {
        return getArenaConfigYml(id).getInt("Players.Max");
    }
    public static int getWaitTime(int id) {
        return getArenaConfigYml(id).getInt("Time.Wait");
    }
    public static int getStartTime(int id) {
        return getArenaConfigYml(id).getInt("Time.Start");
    }
    public static int getHideTime(int id) {
        return getArenaConfigYml(id).getInt("Time.Hide");
    }
    public static int getGameTime(int id) {
        return getArenaConfigYml(id).getInt("Time.game");
    }
    public static Boolean allowsLateJoin(int id) {
        return Boolean.valueOf(getArenaConfigYml(id).getString("Rules.LateJoin"));
    }
    public static Location getQuitLocation(int id) {
        YamlConfiguration config = getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Quit.world"));
        Double x = config.getDouble("Locations.Quit.x");
        Double y = config.getDouble("Locations.Quit.y");
        Double z = config.getDouble("Locations.Quit.z");
        Integer ya = config.getInt("Locations.Quit.yaw");
        Integer pi = config.getInt("Locations.Quit.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getEndLocation(int id) {
        YamlConfiguration config = getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.End.world"));
        Double x = config.getDouble("Locations.End.x");
        Double y = config.getDouble("Locations.End.y");
        Double z = config.getDouble("Locations.End.z");
        Integer ya = config.getInt("Locations.End.yaw");
        Integer pi = config.getInt("Locations.End.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getLobbyLocation(int id) {
        YamlConfiguration config = getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Lobby.world"));
        Double x = config.getDouble("Locations.Lobby.x");
        Double y = config.getDouble("Locations.Lobby.y");
        Double z = config.getDouble("Locations.Lobby.z");
        Integer ya = config.getInt("Locations.Lobby.yaw");
        Integer pi = config.getInt("Locations.Lobby.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Location getSpawnLocation(int id) {
        YamlConfiguration config = getArenaConfigYml(id);
        World world = Bukkit.getWorld(config.getString("Locations.Spawn.world"));
        Double x = config.getDouble("Locations.Spawn.x");
        Double y = config.getDouble("Locations.Spawn.y");
        Double z = config.getDouble("Locations.Spawn.z");
        Integer ya = config.getInt("Locations.Spawn.yaw");
        Integer pi = config.getInt("Locations.Spawn.pitch");
        return new Location(world, x, y, z, ya, pi);
    }
    public static Cuboid getRegenArea(int id) {
        YamlConfiguration config = getArenaConfigYml(id);
        World w1 = Bukkit.getWorld(config.getString("Locations.Regen.loc1.world"));
        Double x1 = config.getDouble("Locations.Regen.loc1.x");
        Double y1 = config.getDouble("Locations.Regen.loc1.y");
        Double z1 = config.getDouble("Locations.Regen.loc1.z");
        World w2 = Bukkit.getWorld(config.getString("Locations.Regen.loc2.world"));
        Double x2 = config.getDouble("Locations.Regen.loc2.x");
        Double y2 = config.getDouble("Locations.Regen.loc2.y");
        Double z2 = config.getDouble("Locations.Regen.loc2.z");
        Location loc1 = new Location(w1, x1, y1, z1);
        Location loc2 = new Location(w2, x2, y2, z2);
        return new Cuboid(loc1, loc2);
    }

    //Setters
    public static void setDisplayName(int id, String name) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("DisplayName", name);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setMinPlayers(int id, int minPlayers) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Players.Min", minPlayers);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setMaxPlayers(int id, int maxPlayers) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Players.Max", maxPlayers);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setWaitTime(int id, int waitTime) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Time.Wait", waitTime);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setStartTime(int id, int startTime) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Time.Start", startTime);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setHideTime(int id, int hideTime) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Time.Hide", hideTime);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setGameTime(int id, int gameTime) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Time.Game", gameTime);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setAllowLateJoin(int id, Boolean allow) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Rules.LateJoin", allow);
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setQuitLocation(int id, Location loc) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.Quit.world", loc.getWorld().getName());
        config.set("Locations.Quit.x", loc.getX());
        config.set("Locations.Quit.y", loc.getY());
        config.set("Locations.Quit.z", loc.getZ());
        config.set("Locations.Quit.yaw", loc.getYaw());
        config.set("Locations.Quit.pitch", loc.getPitch());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setEndLocation(int id, Location loc) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.End.world", loc.getWorld().getName());
        config.set("Locations.End.x", loc.getX());
        config.set("Locations.End.y", loc.getY());
        config.set("Locations.End.z", loc.getZ());
        config.set("Locations.End.yaw", loc.getYaw());
        config.set("Locations.End.pitch", loc.getPitch());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setLobbyLocation(int id, Location loc) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.Lobby.world", loc.getWorld().getName());
        config.set("Locations.Lobby.x", loc.getX());
        config.set("Locations.Lobby.y", loc.getY());
        config.set("Locations.Lobby.z", loc.getZ());
        config.set("Locations.Lobby.yaw", loc.getYaw());
        config.set("Locations.Lobby.pitch", loc.getPitch());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setSpectateLocation(int id, Location loc) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.Spectate.world", loc.getWorld().getName());
        config.set("Locations.Spectate.x", loc.getX());
        config.set("Locations.Spectate.y", loc.getY());
        config.set("Locations.Spectate.z", loc.getZ());
        config.set("Locations.Spectate.yaw", loc.getYaw());
        config.set("Locations.Spectate.pitch", loc.getPitch());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setSpawnLocation(int id, Location loc) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.Spawn.world", loc.getWorld().getName());
        config.set("Locations.Spawn.x", loc.getX());
        config.set("Locations.Spawn.y", loc.getY());
        config.set("Locations.Spawn.z", loc.getZ());
        config.set("Locations.Spawn.yaw", loc.getYaw());
        config.set("Locations.Spawn.pitch", loc.getPitch());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
    public static void setRegenArea(int id, Location loc1, Location loc2) {
        YamlConfiguration config = getArenaConfigYml(id);
        config.set("Locations.Regen.loc1.world", loc1.getWorld().getName());
        config.set("Locations.Regen.loc1.x", loc1.getX());
        config.set("Locations.Regen.loc1.y", loc1.getY());
        config.set("Locations.Regen.loc1.z", loc1.getZ());
        config.set("Locations.Regen.loc2.world", loc2.getWorld().getName());
        config.set("Locations.Regen.loc2.x", loc2.getX());
        config.set("Locations.Regen.loc2.y", loc2.getY());
        config.set("Locations.Regen.loc2.z", loc2.getZ());
        saveArenaConfig(config, getArenaConfigFile(id));
    }
}