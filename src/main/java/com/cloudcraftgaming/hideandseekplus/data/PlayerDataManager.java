package com.cloudcraftgaming.hideandseekplus.data;

import com.cloudcraftgaming.hideandseekplus.utils.FileManager;
import com.cloudcraftgaming.hideandseekplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nova Fox on 6/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerDataManager {

    //Booleans/Checkers
    public static Boolean hasPlayerData(Player player) {
        return getPlayerDataFile(player).exists();
    }

    public static Boolean hasLocationOneSaved(Player player) {
        return getPlayerDataYml(player).contains("Loc.Loc1");
    }
    public static Boolean hasLocationTwoSaved(Player player) {
        return getPlayerDataYml(player).contains("Loc.Loc2");
    }

    //Data getters
    public static File getPlayerDataFile(Player player) {
        return new File(Main.plugin.getDataFolder() + "/Data/PlayerData/" + player.getUniqueId() + ".yml");
    }
    public static YamlConfiguration getPlayerDataYml(Player player) {
        return YamlConfiguration.loadConfiguration(getPlayerDataFile(player));
    }

    public static Boolean hasArenaToolEnabled(Player player) {
        return Boolean.valueOf(getPlayerDataYml(player).getString("ArenaTool"));
    }
    public static Location getSaveLocationOne(Player player) {
        YamlConfiguration data = getPlayerDataYml(player);
        World world = Bukkit.getWorld(data.getString("Loc.Loc1.world"));
        Double x = data.getDouble("Loc.Loc1.x");
        Double y = data.getDouble("Loc.Loc1.y");
        Double z = data.getDouble("Loc.Loc1.z");
        return new Location(world, x, y, z);
    }
    public static Location getSaveLocationTwo(Player player) {
        YamlConfiguration data = getPlayerDataYml(player);
        World world = Bukkit.getWorld(data.getString("Loc.Loc2.world"));
        Double x = data.getDouble("Loc.Loc2.x");
        Double y = data.getDouble("Loc.Loc2.y");
        Double z = data.getDouble("Loc.Loc2.z");
        return new Location(world, x, y, z);
    }

    //Data setters
    public static void setArenaToolEnabled(Player player, Boolean enabled) {
        YamlConfiguration data = getPlayerDataYml(player);
        data.set("ArenaTool", enabled);
        savePlayerData(data, getPlayerDataFile(player));
    }

    public static void saveLocationOne(Player player, Location loc) {
        YamlConfiguration data = getPlayerDataYml(player);
        data.set("Loc.Loc1.world", loc.getWorld().getName());
        data.set("Loc.Loc1.x", loc.getX());
        data.set("Loc.Loc1.y", loc.getY());
        data.set("Loc.Loc1.z", loc.getZ());
        savePlayerData(data, getPlayerDataFile(player));
    }
    public static void saveLocationTwo(Player player, Location loc) {
        YamlConfiguration data = getPlayerDataYml(player);
        data.set("Loc.Loc2.world", loc.getWorld().getName());
        data.set("Loc.Loc2.x", loc.getX());
        data.set("Loc.Loc2.y", loc.getY());
        data.set("Loc.Loc2.z", loc.getZ());
        savePlayerData(data, getPlayerDataFile(player));
    }

    //Functionals
    public static void createPlayerData(Player player) {
        if (!hasPlayerData(player)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Generating player data for: " + player.getName());
            }
            YamlConfiguration data = getPlayerDataYml(player);
            data.addDefault("DO NOT DELETE", "HideAndSeekPlus is developed and managed by Shades161");

            data.addDefault("ArenaTool", false);

            data.options().copyDefaults(true);
            savePlayerData(data, getPlayerDataFile(player));

            data.options().copyDefaults(true);
            savePlayerData(data, getPlayerDataFile(player));
        }

    }
    public static void savePlayerData(YamlConfiguration dataYml, File dataFile) {
        try {
            dataYml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updatePlayerData(Player player) {
        if (hasPlayerData(player)) {
            if (FileManager.verbose()) {
                Main.plugin.getLogger().info("Updating player data for: " + player.getName());
            }
            YamlConfiguration data = getPlayerDataYml(player);
            data.set("ArenaTool", false);
            savePlayerData(data, getPlayerDataFile(player));
            if (hasLocationOneSaved(player)) {
                deleteSaveLocationOne(player);
            }
            if (hasLocationTwoSaved(player)) {
                deleteSaveLocationTwo(player);
            }
        }
    }

    public static void deleteSaveLocationOne(Player player) {
        if (hasLocationOneSaved(player)) {
            YamlConfiguration data = getPlayerDataYml(player);
            data.set("Loc.Loc1", null);
            savePlayerData(data, getPlayerDataFile(player));
        }
    }
    public static void deleteSaveLocationTwo(Player player) {
        if (hasLocationTwoSaved(player)) {
            YamlConfiguration data = getPlayerDataYml(player);
            data.set("Loc.Loc2", null);
            savePlayerData(data, getPlayerDataFile(player));
        }
    }
}