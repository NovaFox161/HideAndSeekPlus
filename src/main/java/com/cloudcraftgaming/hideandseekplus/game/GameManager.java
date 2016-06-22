package com.cloudcraftgaming.hideandseekplus.game;

import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import com.cloudcraftgaming.hideandseekplus.utils.Regenerator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by Nova Fox on 6/15/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class GameManager {
    public static void startGame(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);

            arena.setJoinable(false);
            arena.setGameState(GameState.INGAME);
            arena.setCanTag(false);
            determineSeeker(id);
            GameBoardManager.createScoreboard(id);
            GameBoardManager.addPlayers(id);
            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                if (uuid != arena.getSeeker()) {
                    p.teleport(ArenaDataManager.getSpawnLocation(id));
                    p.setGameMode(GameMode.SURVIVAL);
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    p.getInventory().clear();
                    p.setExp(0);
                    p.setLevel(0);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.setFireTicks(0);
                    p.setExhaustion(20);
                } else {
                    p.teleport(ArenaDataManager.getSpawnLocation(id));
                    p.setGameMode(GameMode.SURVIVAL);
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    p.getInventory().clear();
                    p.setExp(0);
                    p.setLevel(0);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.setFireTicks(0);
                    p.setExhaustion(20);
                    Integer hideTime = ArenaDataManager.getHideTime(id);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 *  hideTime, 100));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * hideTime, 100));
                }
            }
            GameBoardManager.updateBoards(id);
            TimeManager.getManager().startGameTime(id);
            TimeManager.getManager().startHideTime(id);
            GameMessages.announceGameStart(id);
            GameMessages.announceHideTimeStart(id);
        }
    }
    public static void endGame(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            determineWinners(id);
            GameBoardManager.removePlayers(id);
            for (UUID uuid : arena.getPlayers()) {
                Player p = Bukkit.getPlayer(uuid);
                p.setExhaustion(20f);
                p.getInventory().clear();
                p.setFoodLevel(20);
                p.setHealth(20);
                p.setFireTicks(0);
                p.removePotionEffect(PotionEffectType.SLOW);
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(ArenaDataManager.getEndLocation(id));
            }
            GameMessages.announceWinners(id);
            arena.getPlayers().clear();
            arena.setPlayerCount(0);
            Regenerator.regenArena(id);
        }
    }
    public static void determineWinners(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            WinType winType = WinType.NONE;
            for (UUID pId : arena.getPlayers()) {
                if (pId != arena.getSeeker()) {
                    winType = WinType.HIDERS;
                    break;
                } else if (pId == arena.getSeeker() && !winType.equals(WinType.HIDERS)) {
                    winType = WinType.SEEKERS;
                }
            }
            arena.setWinType(winType);
        }
    }

    public static void determineSeeker(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            ArrayList<UUID> players = arena.getPlayers();
            Collections.shuffle(players);
            UUID seekerId = players.get(0);
            arena.setSeeker(seekerId);
        }
    }
}
