package com.cloudcraftgaming.hideandseekplus.game;

import com.cloudcraftgaming.hideandseekplus.Main;
import com.cloudcraftgaming.hideandseekplus.arena.Arena;
import com.cloudcraftgaming.hideandseekplus.arena.ArenaManager;
import com.cloudcraftgaming.hideandseekplus.data.ArenaDataManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

/**
 * Created by Nova Fox on 6/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: HideAndSeekPlus.
 */
public class TimeManager {
    private static TimeManager instance;
    private BukkitScheduler scheduler = Bukkit.getScheduler();

    private TimeManager() {} //Prevent initialization

    public static TimeManager getManager() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }

    public void startWaitDelay(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            if (arena.getWaitId() == 0 && arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS)) {
                Integer waitDelay = ArenaDataManager.getWaitTime(id);
                Random rn = new Random();
                final Integer waitId = rn.nextInt(99999998) + 1;
                arena.setStartId(0);
                arena.setWaitId(waitId);
                GameMessages.announceWaitDelay(id);
                scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (ArenaManager.getManager().arenaLoaded(id)) {
                            Arena arena1 = ArenaManager.getManager().getArena(id);
                            if (arena1.getWaitId() == waitId && arena1.getGameState().equals(GameState.WAITING_FOR_PLAYERS)) {
                                //Wait time over, begin start delay.
                                startStartDelay(id);
                            }
                        }
                    }
                }, 20L * waitDelay);
            }
        }
    }
    public void cancelWaitDelay(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.setWaitId(0);
            arena.setStartId(0);
            GameMessages.announceWaitCancel(id);
        }
    }
    public void startStartDelay(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            if (arena.getStartId() == 0 && arena.getGameState().equals(GameState.WAITING_FOR_PLAYERS)) {
                Integer startDelay = ArenaDataManager.getStartTime(id);
                Random rn = new Random();
                final Integer startId = rn.nextInt(99999998) + 1;
                arena.setWaitId(0);
                arena.setStartId(startId);
                arena.setGameState(GameState.STARTING);
                GameMessages.announceStartDelay(id);
                scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (ArenaManager.getManager().arenaLoaded(id)) {
                            Arena arena1 = ArenaManager.getManager().getArena(id);
                            if (arena1.getStartId() == startId && arena1.getGameState().equals(GameState.STARTING)) {
                                GameManager.startGame(id);

                            }
                        }
                    }
                }, 20L * startDelay);
            }
        }
    }
    public void cancelStartDelay(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.setWaitId(0);
            arena.setStartId(0);
            GameMessages.announceStartCancel(id);
        }
    }
    public void startGameTime(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            if (arena.getGameId() == 0 && arena.getGameState().equals(GameState.INGAME)) {
                Integer gameTime = ArenaDataManager.getGameTime(id);
                Random rn = new Random();
                final Integer gameId = rn.nextInt(99999998) + 1;
                arena.setStartId(0);
                arena.setGameId(id);
                scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (ArenaManager.getManager().arenaLoaded(id)) {
                            Arena arena1 = ArenaManager.getManager().getArena(id);
                            if (arena1.getGameId() == gameId && arena1.getGameState().equals(GameState.INGAME)) {
                                GameManager.endGame(id);
                            }
                        }
                    }
                }, 20L * 60 * gameTime);
            }
        }
    }
    public void startHideTime(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            Integer hideTime = ArenaDataManager.getHideTime(id);
            final Integer gameId = arena.getGameId();
            scheduler.scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (ArenaManager.getManager().arenaLoaded(id)) {
                        Arena arena1 = ArenaManager.getManager().getArena(id);
                        if (arena1.getGameId() == gameId && arena1.getGameState().equals(GameState.INGAME)) {
                            arena1.setCanTag(true);
                            GameMessages.announceHideTimeOver(id);
                        }
                    }
                }
            }, 20L * hideTime);
        }
    }
}