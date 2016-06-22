package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMoveCheckGamemode(PlayerMoveEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMoveCheckIt(PlayerMoveEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            Player player = event.getPlayer();
            Arena arena = ArenaManager.getManager().getArena(player);
            if (arena.getGameState().equals(GameState.INGAME)) {
                if (arena.getSeeker().equals(player.getUniqueId())) {
                    if (!arena.canTag()) {
                        Location from = event.getFrom();
                        Location to = event.getTo();
                        if (!((to.getX() == from.getX()) && (to.getY() == from.getY()) && (to.getZ() == from.getZ()))) {
                            player.teleport(from);
                        }
                    }
                }
            }
        }
    }
}
