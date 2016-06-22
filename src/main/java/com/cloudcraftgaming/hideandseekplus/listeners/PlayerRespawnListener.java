package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import com.cloudcraftgaming.hideandseekplus.utils.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerRespawnListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {
        if (ArenaManager.getManager().isInGame(event.getPlayer())) {
            Arena arena = ArenaManager.getManager().getArena(event.getPlayer());
            Player player = event.getPlayer();
            if (arena.getGameState().equals(GameState.INGAME)) {
                if (arena.getSeeker() != player.getUniqueId()) {
                    PlayerHandler.hiderOutDeath(player);
                    event.setRespawnLocation(ArenaDataManager.getEndLocation(arena.getId()));
                } else {
                    PlayerHandler.seekerOutDeath(player);
                    event.setRespawnLocation(ArenaDataManager.getEndLocation(arena.getId()));
                }
            } else {
                event.setRespawnLocation(ArenaDataManager.getLobbyLocation(arena.getId()));
            }
        }
    }
}
