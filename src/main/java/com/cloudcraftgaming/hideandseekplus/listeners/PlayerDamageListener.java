package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import com.cloudcraftgaming.hideandseekplus.utils.MessageManager;
import com.cloudcraftgaming.hideandseekplus.utils.PlayerHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (ArenaManager.getManager().isInGame(player)) {
                if (event.getDamager() instanceof Player) {
                    Player damager = (Player) event.getDamager();
                    Arena arena = ArenaManager.getManager().getArena(player);
                    if (arena.getGameState().equals(GameState.INGAME) && arena.canTag()) {
                        if (arena.getSeeker() == damager.getUniqueId() && arena.getPlayers().contains(player.getUniqueId())) {
                            //Seeker hit hider, hider is now out.
                            event.setCancelled(true);
                            PlayerHandler.hiderOutTagged(player, damager);
                        } else if (arena.getSeeker() != damager.getUniqueId() && arena.getSeeker() == player.getUniqueId()) {
                            //Hider hit seeker... do stuff...
                            event.setCancelled(true);
                            String hiderMsg = MessageManager.getMessagesYml().getString("Game.Event.HiderHitSeeker");
                            damager.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', hiderMsg));
                        } else if (arena.getSeeker() != damager.getUniqueId() && arena.getSeeker() != player.getUniqueId()) {
                            //Hider hit another hider... do other stuff.
                            event.setCancelled(true);
                            String damagerMsg = MessageManager.getMessagesYml().getString("Game.Event.HiderHitHider.Damager");
                            damager.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', damagerMsg));
                            String hitieMsg = MessageManager.getMessagesYml().getString("Game.Event.HiderHitHider.Hitie");
                            player.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', hitieMsg));
                        }
                    } else {
                        event.setCancelled(true);
                        String msg = MessageManager.getMessagesYml().getString("Game.Event.PlayerHitPlayer");
                        damager.sendMessage(MessageManager.getPrefix() + ChatColor.translateAlternateColorCodes('&', msg));
                    }
                }
            }
        }
    }
}