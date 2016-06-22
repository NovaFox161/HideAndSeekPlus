package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class CommandListener implements Listener {
    Main plugin;
    public CommandListener(Main instance) {
        plugin = instance;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!(event.isCancelled())) {
            Player player = event.getPlayer();
            if (ArenaManager.getManager().isInGame(player) || ArenaManager.getManager().isSpectating(player)) {
                String command = event.getMessage();
                for (String blockedCommand : plugin.getConfig().getStringList("Game.BlockedCommands")) {
                    if (command.startsWith(blockedCommand) || command.contains(blockedCommand)) {
                        event.setCancelled(true);
                        event.setMessage("/");
                        String msgOr = MessageManager.getMessagesYml().getString("Notifications.Player.BlockedCommand");
                        player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msgOr));
                    }
                }
            }
        }
    }
}