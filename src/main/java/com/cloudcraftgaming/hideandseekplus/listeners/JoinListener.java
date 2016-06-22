package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.utils.FileManager;
import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.data.PlayerDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Nova Fox on 6/16/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class JoinListener implements Listener {
    public JoinListener(Main instance) {
        plugin = instance;
    }
    Main plugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinUpdateData(PlayerJoinEvent event) {
        if (PlayerDataManager.hasPlayerData(event.getPlayer())) {
            PlayerDataManager.updatePlayerData(event.getPlayer());
        } else {
            PlayerDataManager.createPlayerData(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinCheckForUpdates(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("hasp.notify.update")) {
            Player player = event.getPlayer();
            if (plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
                if (FileManager.verbose()) {
                    plugin.getLogger().info("Checking for updates...");
                }
                plugin.updateChecker = new UpdateChecker(plugin, "http://dev.bukkit.org/bukkit-plugins/hideandseekplus/files.rss");
                if (plugin.updateChecker.UpdateNeeded()) {
                    player.sendMessage(ChatColor.GREEN + "A new version of HideAndSeekPlus is available! Version: "
                            + plugin.updateChecker.getVersion());
                    player.sendMessage(ChatColor.BLUE + "Download it from: " + plugin.updateChecker.getLink());
                } else {
                    if (FileManager.verbose()) {
                        plugin.getLogger().info("No new updates found... will check again later.");
                    }
                }
            }
        }
    }
}