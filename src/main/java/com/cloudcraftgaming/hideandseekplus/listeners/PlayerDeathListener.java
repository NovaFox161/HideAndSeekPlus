package com.cloudcraftgaming.hideandseekplus.listeners;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.game.GameMessages;
import com.cloudcraftgaming.hideandseekplus.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Nova Fox on 6/17/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        if (ArenaManager.getManager().isInGame(event.getEntity())) {
            Player player = event.getEntity();
            Arena arena = ArenaManager.getManager().getArena(player);
            if (arena.getGameState().equals(GameState.INGAME)) {
                if (arena.getSeeker() != player.getUniqueId()) {
                    if (event.getDeathMessage() != null) {
                        event.setDeathMessage(null);
                    }
                    event.setKeepLevel(true);
                    event.setDroppedExp(0);
                    event.setKeepInventory(true);
                    GameMessages.announceHiderDeath(player);
                    //Hider died. Respawn listener will handle the rest.
                } else {
                    if (event.getDeathMessage() != null) {
                        event.setDeathMessage(null);
                    }
                    event.setKeepLevel(true);
                    event.setDroppedExp(0);
                    event.setKeepInventory(true);
                    GameMessages.announceSeekerDeath(player);
                    //Seeker died. Respawn listener will handle the rest.
                }
            } else {
                //They must be waiting to start, just say they died.
                GameMessages.announcePlayerDeathLobby(player);
                if (event.getDeathMessage() != null) {
                    event.setDeathMessage(null);
                }
                event.setKeepLevel(true);
                event.setDroppedExp(0);
                event.setKeepInventory(true);
            }
        }
    }
}
