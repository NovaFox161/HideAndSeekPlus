package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            event.setCancelled(true);
            String msg = MessageManager.getMessagesYml().getString("Rules.Block.Break");
            event.getPlayer().sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
        }
    }
}
